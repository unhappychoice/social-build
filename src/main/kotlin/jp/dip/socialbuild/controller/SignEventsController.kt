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
import jp.dip.socialbuild.model.Sign
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.GameMode
import org.bukkit.entity.Player
import jp.dip.socialbuild.extension.isSurvival
import jp.dip.socialbuild.extension.owns

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class SignEventsController : Listener {

    /**
     * an event handler for placing sign
     */
    EventHandler
    public fun onSignPlace(e: SignChangeEvent) {
        // TODO: check permission
        // TODO: broadcast

        if (e.getLine(0) != "[sb]") {
            return
        }

        val sign = Sign.create(e.getPlayer(), e.getBlock().getLocation(), e.getLines().toList())
        sign.save()
        sign.replaceSignText(e)
    }

    /**
     * an event handler for breaking sign
     */
    EventHandler
    public fun onSignBreak(e: BlockBreakEvent) {
        // TODO: check permission
        // TODO: reconsider breaking process

        if (!isSignItem(e.getBlock())) { return }

        val sign = Sign.find(e.getBlock().getLocation())
        val player = e.getPlayer()!!

        if (sign == null || !canBreak(sign, player)) e.setCancelled(true) else sign.destroy()
    }

    /**
     * an event handler for clicking sign
     */
    EventHandler
    public fun onClickSign(e: PlayerInteractEvent) {
        // TODO: implement
        // TODO: check permission
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun isSignItem(block: Block): Boolean {
        return block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST
    }

    private fun canBreak(sign: Sign, player: Player): Boolean {
        return player.owns(sign) && player.isSurvival()
    }
}