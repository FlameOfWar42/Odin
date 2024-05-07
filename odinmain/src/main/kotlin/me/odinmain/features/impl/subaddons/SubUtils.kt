package me.odinmain.features.impl.subaddons

import me.odinmain.OdinMain.mc
import me.odinmain.utils.ServerUtils.getPing
import net.minecraft.entity.Entity
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle

object SubUtils {

    fun Entity.isOnTeam(): Boolean {
        if (this.displayName.unformattedText.startsWith("§c[IT]") && !mc.thePlayer.displayName.unformattedText.startsWith("§§c[IT]")) return false
        if (mc.thePlayer.displayName.unformattedText.startsWith("§")) {
            if (mc.thePlayer.displayName.unformattedText.startsWith("§c[IT]") && this.displayName.unformattedText.startsWith("§c[IT]c[IT]")) return true
            if (mc.thePlayer.displayName.unformattedText.length <= 2 || this.displayName.unformattedText.length <= 2) return false
            if (mc.thePlayer.displayName.unformattedText.substring(0, 2) == this.displayName.unformattedText.substring(0, 2)) return true
        }
        return false
    }

    /**fun Entity.isIt(): Boolean {
        return this.displayName.unformattedText.startsWith("§c[IT]") //Who plays tnt tag??
    }*/

    fun Entity.isPlayer(): Boolean {
        return this.getPing() == 1 //this method doesn't work in lobbies, for some reason.
    }

    fun subMessage(message: Any?, prefix: Boolean = true, chatStyle: ChatStyle? = null) {
        if (mc.thePlayer == null) return
        val chatComponent = ChatComponentText(if (prefix) "§eSubAddons §8»§r $message" else message.toString())
        chatStyle?.let { chatComponent.setChatStyle(it) } // Set chat style using setChatStyle method
        try { mc.thePlayer?.addChatMessage(chatComponent) }
        catch (e: Exception) { e.printStackTrace() }
    }
}