package me.odinmain.utils.skyblock.dungeon.tiles

import me.odinmain.utils.render.Color

class Unknown(override val x: Int, override val z: Int) : Tile {
    override var state: RoomState = RoomState.UNDISCOVERED
}