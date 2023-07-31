package me.odinclient.features.impl.qol

import me.odinclient.OdinClient.Companion.config
import me.odinclient.OdinClient.Companion.mc
import me.odinclient.features.Category
import me.odinclient.features.Module
import me.odinclient.utils.skyblock.ChatUtils.unformattedText
import me.odinclient.utils.skyblock.dungeon.DungeonUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.network.play.client.C02PacketUseEntity
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object RelicAura : Module(
    name = "Relic Aura",
    category = Category.M7,
    description = ""
){
    private var disabler = false

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) {
        disabler = false
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        val message = event.unformattedText
        if (message == "[BOSS] Wither King: You.. again?") disabler = true
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (disabler || !DungeonUtils.inDungeons) return
        val armorStands = mc.theWorld?.loadedEntityList?.filterIsInstance<EntityArmorStand>() ?: return
        for (armorStand in armorStands) {
            if (armorStand.inventory?.get(4)?.displayName?.contains("Relic") != true || mc.thePlayer.getDistanceToEntity(armorStand) > 4) continue
            interactWithEntity(armorStand)
        }
    }

    private fun interactWithEntity(entity: Entity) {
        val objectMouseOver = mc.objectMouseOver.hitVec
        val dx = objectMouseOver.xCoord - entity.posX
        val dy = objectMouseOver.yCoord - entity.posY
        val dz = objectMouseOver.zCoord - entity.posX
        mc.netHandler.addToSendQueue(C02PacketUseEntity(entity, Vec3(dx, dy, dz)))
    }
}