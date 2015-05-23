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

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class SignEventsController : Listener {

    /**
     * an event handler for placing sign
     */
    EventHandler
    public fun onSignPlace(e: SignChangeEvent) {
        if (e.getLine(0) != "[sb]") {
            return
        }

        Sign.save(e.getPlayer(), e.getBlock().getLocation(), e.getLines().toList())
        replaceSignText(e)
    }

    /**
     * an event handler for breaking sign
     */
    EventHandler
    public fun onSignBreak(e: BlockBreakEvent) {
        if (!isSignItem(e.getBlock())) { return }

        val sign = Sign.find(e.getBlock().getLocation())
        val player = e.getPlayer()!!

        if (sign == null || !canBreak(sign, player)) {
            e.setCancelled(true)
            print("canceled!")
            print("sign is ${sign?.params.toString()}")
        } else {
            sign.destroy()
            print("destoroyed!")
        }
    }

    /**
     * an event handler for clicking sign
     */
    EventHandler
    public fun onClickSign(e: PlayerInteractEvent) {

    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun replaceSignText(e: SignChangeEvent) {
        for ( i in 0..3) {
            e.setLine(i, signLines(i, e))
        }
    }

    private fun signLines(index: Int, e: SignChangeEvent): String {
        return listOf(
                ChatColor.BLUE.toString() + "SocialBuild",    // title
                ChatColor.GREEN.toString() + e.getLine(1),    // sign name
                e.getPlayer().getName(),                      // player name
                ChatColor.DARK_AQUA.toString() + "good! : 0"  // good count
        ).get(index)
    }

    private fun isSignItem(block: Block): Boolean {
        return block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST
    }

    private fun canBreak(sign: Sign, player: Player): Boolean {
        return isOwner(player, sign) && isSurvival(player)
    }

    private fun isOwner(player: Player, sign: Sign): Boolean {
        return sign.params.ownerId.equals(player.getUniqueId().toString())
    }

    private fun isSurvival(player: Player): Boolean {
        return player.getGameMode().equals(GameMode.SURVIVAL)
    }
}