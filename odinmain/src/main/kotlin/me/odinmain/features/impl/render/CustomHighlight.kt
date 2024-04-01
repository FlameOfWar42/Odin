
    package me.odinmain.features.impl.render

    import me.odinmain.OdinMain.onLegitVersion
    import me.odinmain.events.impl.PostEntityMetadata
    import me.odinmain.features.Category
    import me.odinmain.features.Module
    import me.odinmain.features.settings.Setting.Companion.withDependency
    import me.odinmain.features.settings.impl.*
    import me.odinmain.ui.util.shader.FramebufferShader
    import me.odinmain.ui.util.shader.OutlineShader
    import me.odinmain.utils.ServerUtils.getPing
    import me.odinmain.utils.equalsOneOf
    import me.odinmain.utils.getPositionEyes
    import me.odinmain.utils.profile
    import me.odinmain.utils.render.Color
    import me.odinmain.utils.render.RenderUtils
    import me.odinmain.utils.render.RenderUtils.renderVec
    import me.odinmain.utils.render.Renderer
    import net.minecraft.client.renderer.GlStateManager
    import net.minecraft.client.renderer.RenderHelper
    import net.minecraft.client.shader.ShaderGroup
    import net.minecraft.entity.Entity
    import net.minecraft.entity.boss.EntityWither
    import net.minecraft.entity.item.EntityArmorStand
    import net.minecraftforge.client.event.RenderGameOverlayEvent
    import net.minecraftforge.client.event.RenderLivingEvent
    import net.minecraftforge.client.event.RenderWorldLastEvent
    import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
    import org.lwjgl.opengl.GL11


    object CustomHighlight : Module(
        name = "Custom Highlight",
        category = Category.RENDER,
        tag = TagType.FPSTAX,
        description = "Allows you to highlight selected mobs. (/highlight)"
    ) {
        private val scanDelay: Long by NumberSetting("Scan Delay", 500L, 10L, 2000L, 100L)
        private val starredMobESP: Boolean by BooleanSetting("Starred Mob Highlight", true, description = "Highlights mobs with a star in their name (remove star from the separate list).")
        val color: Color by ColorSetting("Color", Color.RED, true)
        val mode: Int by SelectorSetting("Mode", "Outline", arrayListOf("Outline", "Overlay", "Boxes", "2D", "Glow"))
        val thickness: Float by NumberSetting("Line Width", 5f, .5f, 20f, .1f, description = "The line width of Outline/ Boxes/ 2D Boxes").withDependency { mode.equalsOneOf(0, 2, 3, 4) }
        private val tracerLimit: Int by NumberSetting("Tracer Limit", 0, 0, 15, description = "Highlight will draw tracer to all mobs when you have under this amount of mobs marked, set to 0 to disable. Helpful for finding lost mobs.").withDependency { !onLegitVersion }

        private val xray: Boolean by BooleanSetting("Through Walls", true).withDependency { !onLegitVersion }
        private val cancelHurt: Boolean by BooleanSetting("Cancel Hurt", true).withDependency { mode != 1 }
        private val witherHighlight: Boolean by BooleanSetting("Highlights Withers", false, description = "Highlights Goldor.")
        val highlightList: MutableList<String> by ListSetting("List", mutableListOf())

        val renderThrough: Boolean get() = if (onLegitVersion) false else xray

        var currentEntities = mutableSetOf<Entity>()
        var clearAndBindFrameBufferShader: () -> Unit = {}
        var entityOutlineShader: ShaderGroup? = null

        init {
            execute({ scanDelay }) {
                currentEntities.removeAll { it.isDead }
                getEntities()
            }

            execute(30_000) {
                currentEntities.clear()
                getEntities()
            }

            onWorldLoad { currentEntities.clear() }
        }

        @SubscribeEvent
        fun onRenderWorldLast(event: RenderWorldLastEvent) {
            profile("ESP") { currentEntities.forEach {
                if (currentEntities.size < tracerLimit && !onLegitVersion)
                    RenderUtils.draw3DLine(getPositionEyes(mc.thePlayer.renderVec), getPositionEyes(it.renderVec), color, 2, false)

                if (mode == 2)
                    Renderer.drawBox(it.entityBoundingBox, color, thickness, depth = !renderThrough, fillAlpha = 0)
                else if (mode == 3 && (mc.thePlayer.canEntityBeSeen(it) || renderThrough))
                    Renderer.draw2DEntity(it, thickness, color)
            }}
        }

        @SubscribeEvent
        fun on2d(event: RenderGameOverlayEvent.Pre) {
            if (event.type != RenderGameOverlayEvent.ElementType.HOTBAR || !mode.equalsOneOf(0, 4)) return
            val entityShadows = mc.gameSettings.entityShadows
            if (mode == 0) OutlineShader.startDraw(RenderUtils.partialTicks)
            else {
                GlStateManager.enableAlpha()

                GlStateManager.pushMatrix()
                GlStateManager.pushAttrib()
                GlStateManager.depthFunc(519)
                GlStateManager.disableFog()
                clearAndBindFrameBufferShader.invoke()
                RenderHelper.disableStandardItemLighting()
                mc.renderManager.setRenderOutlines(true)

                mc.gameSettings.entityShadows = false
                FramebufferShader.setupCameraTransform.invoke()
            }

            currentEntities.forEach {
                mc.renderManager.renderEntityStatic(it, RenderUtils.partialTicks, true)
            }

            if (mode == 0) OutlineShader.stopDraw(color, thickness, 1f)
            else {
                mc.gameSettings.entityShadows = entityShadows
                GL11.glEnable(GL11.GL_BLEND)
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
                mc.renderManager.setRenderOutlines(false)
                RenderHelper.enableStandardItemLighting()
                GlStateManager.depthMask(false)
                entityOutlineShader?.loadShaderGroup(RenderUtils.partialTicks)
                GlStateManager.enableLighting()
                GlStateManager.depthMask(true)
                mc.framebuffer.bindFramebuffer(false)
                GlStateManager.enableFog()
                GlStateManager.enableBlend()
                GlStateManager.enableColorMaterial()
                GlStateManager.depthFunc(515)
                GlStateManager.enableDepth()
                GlStateManager.enableAlpha()
                GlStateManager.popAttrib()
                GlStateManager.popMatrix()
            }
        }

        @SubscribeEvent
        fun postMeta(event: PostEntityMetadata) {
            val entity = mc.theWorld.getEntityByID(event.packet.entityId) ?: return
            checkEntity(entity)
            if (starredMobESP) checkStarred(entity)
        }

        private fun getEntities() {
            mc.theWorld?.loadedEntityList?.forEach {
                checkEntity(it)
                if (starredMobESP) checkStarred(it)
            }
        }

        private fun checkEntity(entity: Entity) {
            if (entity !is EntityArmorStand || highlightList.none { entity.name.contains(it, true) } || entity in currentEntities || !entity.alwaysRenderNameTag && !renderThrough) return
            currentEntities.add(getMobEntity(entity) ?: return)
        }

        private fun checkStarred(entity: Entity) {
            if (entity !is EntityArmorStand || !entity.name.startsWith("§6✯ ") || !entity.name.endsWith("§c❤") || entity in currentEntities || !entity.alwaysRenderNameTag && !renderThrough) return
            currentEntities.add(getMobEntity(entity) ?: return)
        }

        private fun getMobEntity(entity: Entity): Entity? {
            return mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity, entity.entityBoundingBox.offset(0.0, -1.0, 0.0))
                .filter { it != null && it !is EntityArmorStand && it.getPing() != 1 && it != mc.thePlayer}
                .minByOrNull { entity.getDistanceToEntity(it) }
                .takeIf { !(it is EntityWither && it.isInvisible) }
        }

        @SubscribeEvent
        fun onRender(event: RenderLivingEvent.Pre<*>) {
            if (!witherHighlight || event.entity !is EntityWither || event.entity.isInvisible) return
            currentEntities.add(event.entity)
        }
    }


