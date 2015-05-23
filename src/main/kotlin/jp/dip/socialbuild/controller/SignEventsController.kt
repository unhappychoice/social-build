package jp.dip.socialbuild.controller

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.block.SignChangeEvent
import jp.dip.socialbuild.repository.SignRepository.SignParams
import jp.dip.socialbuild.repository.SignRepository
import org.bukkit.ChatColor
import org.bukkit.event.block.BlockBreakEvent
import java.sql.Date
import java.util.logging.Logger

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class SignEventsController : Listener {

    /**
     * an event handler for placing sign
     */
    EventHandler
    public fun onSignPlace(e: SignChangeEvent) {
        if (e.getLine(0) == "[sb]") {
            return
        }

        // Persist
        val params = SignParams(
                id = 0,
                name = e.getLine(3),
                ownerId = e.getPlayer().getUniqueId().toString(),
                x = e.getBlock().getLocation().getBlockX(),
                y = e.getBlock().getLocation().getBlockY(),
                z = e.getBlock().getLocation().getBlockZ(),
                createdAt = currentDate(),
                updatedAt = currentDate()
        )
        SignRepository.save(params)

        // 内容書き換え
        e.setLine(0, ChatColor.BLUE.toString() + "SocialBuild")
        e.setLine(1, ChatColor.GREEN.toString() + e.getLine(1))
        e.setLine(2, e.getPlayer().getName())
        e.setLine(3, ChatColor.DARK_AQUA.toString() + "good! : 0")
        print("update sign !")
    }

    /**
     * an event handler for breaking sign
     */
    EventHandler
    public fun onSignBreak(e: BlockBreakEvent) {

    }

    /**
     * an event handler for clicking sign
     */
    EventHandler
    public fun onClickSign(e: PlayerInteractEvent) {

    }

    private fun currentDate(): Date = Date(java.util.Date().getTime())
}