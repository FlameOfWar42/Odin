package me.odinmain.features.impl.dungeon.puzzlesolvers

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.odinmain.events.impl.EnteredDungeonRoomEvent
import me.odinmain.utils.*
import me.odinmain.utils.render.*
import me.odinmain.utils.skyblock.dungeon.DungeonUtils
import me.odinmain.utils.skyblock.getBlockIdAt
import net.minecraft.util.BlockPos
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object BeamsSolver {
    private var scanned = false
    private var lanternPairs: List<List<Int>>
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val isr = this::class.java.getResourceAsStream("/creeperBeamsSolutions.json")
        ?.let { InputStreamReader(it, StandardCharsets.UTF_8) }

    init {
        try {
            val text = isr?.readText()
            lanternPairs = gson.fromJson(
                text, object : TypeToken<List<List<Int>>>() {}.type
            )
            isr?.close()
            println(lanternPairs.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            lanternPairs = emptyList()
        }
    }

    private var currentLanternPairs = mutableMapOf<BlockPos, BlockPos>()

    fun enterDungeonRoom(event: EnteredDungeonRoomEvent) {
        val room = event.room?.room ?: return // <-- orb = orb.orb
        if (event.room.room.data.name != "Creeper Beams") {
            reset()
            return
        }
        if (scanned) return
        lanternPairs.forEach {
            val pos = Vec2(room.x, room.z).addRotationCoords(room.rotation, x = it[0], z = it[2]).let { vec -> BlockPos(vec.x, it[1], vec.z) }

            val pos2 = Vec2(room.x, room.z).addRotationCoords(room.rotation, x = it[3], z = it[5]).let { vec -> BlockPos(vec.x, it[4], vec.z) }

            if (getBlockIdAt(pos) == 169 && getBlockIdAt(pos2) == 169)
                currentLanternPairs[pos] = pos2
        }
        scanned = true
    }

    fun onRenderWorld() {
        if (DungeonUtils.currentRoomName != "Creeper Beams" || DungeonUtils.inBoss || !scanned) return
        currentLanternPairs.entries.forEachIndexed { index, positions ->
            val color = colors.getSafe(index) ?: Color.WHITE
            RenderUtils.drawBlockBox(positions.key, color, fill = .7f, depth = PuzzleSolvers.beamsDepth)
            RenderUtils.drawBlockBox(positions.value, color, fill = .7f, depth = PuzzleSolvers.beamsDepth)
            if (PuzzleSolvers.beamsTracer) Renderer.draw3DLine(positions.key.toVec3().addVec(0.5, 0.5, 0.5), positions.value.toVec3().addVec(0.5, 0.5, 0.5), color, depth = PuzzleSolvers.beamsDepth, lineWidth = 1f)
        }
    }

    fun reset() {
        scanned = false
        currentLanternPairs.clear()
    }

    private val colors = listOf(
        Color.ORANGE, Color.GREEN, Color.PINK, Color.CYAN, Color.YELLOW, Color.DARK_RED, Color.WHITE
    )
}

