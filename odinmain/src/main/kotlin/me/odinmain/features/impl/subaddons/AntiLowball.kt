package me.odinmain.features.impl.subaddons

import me.odinmain.config.Config
import me.odinmain.features.Category
import me.odinmain.features.Module
import me.odinmain.features.impl.subaddons.SubUtils.subMessage
import me.odinmain.features.settings.Setting.Companion.withDependency
import me.odinmain.features.settings.impl.*
import me.odinmain.font.OdinFont
import me.odinmain.ui.hud.HudElement
import me.odinmain.utils.render.Color
import me.odinmain.utils.render.getTextWidth
import me.odinmain.utils.render.text
import me.odinmain.utils.runIn
import me.odinmain.utils.skyblock.sendCommand

object AntiLowball : Module(
    name = "Anti-Lowball",
    category = Category.SUBADDONS,
    description = "Automatically reports lowballers.",
) {
    private val isHidden: Boolean by BooleanSetting("Hidden", default = false, description = "Stops showing mod messages and does everything in the background")
    private val hidelowballers: Boolean by BooleanSetting("Hide Lowballers", default = false, description = "Hides lowballing chat messages")
    private val lowballersReported = +NumberSetting("Number of lowballers reported", 0, increment = 0.01, hidden = true)
    private val reportCD: Int by NumberSetting("Report CD", default = 10, min = 0, max = 120, increment = 1, description = "Cooldown triggered after starting a report, time is in Seconds.")
    private val rateLimitCD: Int by NumberSetting("Rate Limit CD", default = 30, min = 0, max = 240, increment = 1, description = "Cooldown triggered after getting rate limited, time is in Seconds.")
    private val alwaysShowCd: Boolean by BooleanSetting("Always show cd", default = false, description = "Always show cd/people reported")
    private val hideHudText: Boolean by BooleanSetting("Show Prefix", default = true, description = "Whether or not to show the Prefix")
    private val informAdd: Boolean by BooleanSetting("Notify Que Add", default = true, description = "Notifies you when a player has been added to the que.")
    private val statusText: Boolean by DropdownSetting("Status Text")
    private val reportingText: String by StringSetting("Reporting Text", "reporting #name...", 128, description = "Message sent when reporting a user. '#name' will be replaced with their name.").withDependency { statusText }
    private val confirmingText: String by StringSetting("Confirming Text", "Confirming report...", 128, description = "Message sent when confirming a report").withDependency { statusText }
    private val confirmedText: String by StringSetting("Confirmed Text", "I hate lowballers", 128, description = "Message sent when a report is confirmed").withDependency { statusText }
    private val failureText: String by StringSetting("Failure Text", "Hypixel Hates fun", 128, description = "Message sent when a report has failed").withDependency { statusText }
    private val queAddText: String by StringSetting("Add Que Text", "Adding #name to que...", 128, description = "Message sent when a user is added to the que. '#name' will be replaced with their name").withDependency { statusText }
    private val inQueText: String by StringSetting("In Que Text", "#name is already in the que...", 128, description = "Message sent when a user is already in the que. '#name' will be replaced with their name").withDependency { statusText }
    private val hud: HudElement by HudSetting("Anti Lowballer hud", 10f, 10f, 1f, true) {
        if (it) {
            text("§7AntiLB: §a59t  §7//  §c1 // §68", 1f, 9f, Color.RED, 12f, OdinFont.REGULAR, shadow = true)
            getTextWidth("AntiLB: 59  //  1 //  8", 12f) + 2f to 16f
        } else {
            val hudText = if(hideHudText) { "§7AntiLB: " } else { "" }
            if (!alwaysShowCd && waitTime <= 0) return@HudSetting 0f to 0f
            val displayCD = String.format("%.2f", waitTime.toFloat() / 20)

            text("$hudText§e${displayCD}s  §7//  §c${lowballersReported.value} §7//  §6${reportQueue.size}", 1f, 9f, Color.WHITE, 12f, OdinFont.REGULAR, shadow = true)
            getTextWidth("AntiLB: 59  //  1 //  8", 12f) + 2f to 12f
        }
    }

    private val rateLimitRegex = Regex("Please wait before trying to run this command again!")
    private val noRecentRegex = Regex("That player hasn't sent any messages recently!")
    private val confirmRegex = Regex("Please type /report confirm to log your report for staff review.")
    private val confirmedRegex = Regex("Thanks for your Public Chat report. We understand your concerns and it will be reviewed as soon as possible.")
    private val chatRegex = Regex("\\[(\\d+)] ?(.+)? (.+): (.+)")
    private val lowballRegex = Regex("(?i)(l.?bing|l.?o.?w.?b.?a.?l.?l)")
    private var waitTime = 0

    private var reportQueue = mutableListOf<String>()
    
    init {
        execute(50) {
            if (waitTime > 0) waitTime--
            if (reportQueue.size >= 1 && waitTime <= 0) {
                waitTime = reportCD * 20
                val ign = reportQueue.firstOrNull() ?: return@execute subMessage("Report Que size Greater than one, but returned null.")
                if (!isHidden) subMessage("§d${reportingText.replace("#name", ign)}")
                runIn(6) {
                    sendCommand("cr $ign")
                }
            }
        }

        onMessageCancellable(Regex("(?s).*")) {
            if (it.message.matches(confirmRegex)) {
                runIn(20) {
                    if (!isHidden) subMessage("§e$confirmingText")
                    sendCommand("report confirm")
                }
                it.isCanceled = true
            }
            if (it.message.matches(rateLimitRegex)) {
                if (!isHidden) subMessage("§c$failureText (Report failed: Wait a bit)")
                waitTime = rateLimitCD * 20
                it.isCanceled = true
            }

            if (it.message.matches(confirmedRegex)) {
                if (!isHidden) subMessage("§z$confirmedText (Report on ${reportQueue.firstOrNull()} confirmed!)")
                reportQueue.remove(reportQueue.firstOrNull())
                lowballersReported.value += 1
                Config.save()
                it.isCanceled = true
            }

            if (it.message.matches(noRecentRegex)) {
                if (!isHidden) subMessage("§c$failureText (Report failed: No Recent Messages.)")
                waitTime = 20
                reportQueue.remove(reportQueue.firstOrNull())
                it.isCanceled = true
            }

            val ign = chatRegex.matchEntire(it.message)?.groups?.get(3)?.value ?: return@onMessageCancellable
            val msg = chatRegex.matchEntire(it.message)?.groups?.get(4)?.value ?: return@onMessageCancellable

            if (msg.contains(lowballRegex)) {
                if (hidelowballers) { it.isCanceled = true }
                if (ign == mc.thePlayer.name) return@onMessageCancellable subMessage("§c§lSTOP LOWBALLING, YOURE THE PROBLEM")
                if (reportQueue.contains(ign) && informAdd) return@onMessageCancellable subMessage("§c${inQueText.replace("#name", ign)}")
                if (informAdd) subMessage("§6${queAddText.replace("#name", ign)} (${reportQueue.size +1} players before them.)")
                reportQueue.add(ign)
            }
        }
    }
}