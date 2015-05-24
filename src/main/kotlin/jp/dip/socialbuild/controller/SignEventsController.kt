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
import jp.dip.socialbuild.model.SocialBuildSign
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.GameMode
import org.bukkit.entity.Player
import jp.dip.socialbuild.extension.isSignItem
import jp.dip.socialbuild.extension.canBreak
import jp.dip.socialbuild.extension.cannotBreak
import org.bukkit.event.block.Action
import jp.dip.socialbuild.extension.owns
import jp.dip.socialbuild.model.Good
import jp.dip.socialbuild.extension.uuid
import org.bukkit.block.Sign
import jp.dip.socialbuild.extension.canGood
import jp.dip.socialbuild.extension.canUnGood
import jp.dip.socialbuild.extension.isCreative

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

        val sign = SocialBuildSign.create(e.getPlayer(), e.getBlock().getLocation(), e.getLines().toList())
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

        if (!e.getBlock().isSignItem()) {
            return
        }

        val sign = SocialBuildSign.find(e.getBlock().getLocation())
        if (sign == null) {
            return
        }

        if (e.getPlayer().canBreak(sign)) sign.destroy() else e.setCancelled(true)
    }

    /**
     * an event handler for clicking sign
     */
    EventHandler
    public fun onClickSign(e: PlayerInteractEvent) {
        // TODO: check permission
        // TODO: broadcast

        if (!e.getClickedBlock().isSignItem()) {
            return
        }

        val sign = SocialBuildSign.find(e.getClickedBlock().getLocation())
        val player = e.getPlayer()

        if (sign == null) {
            return
        }

        when(e.getAction()) {
            Action.LEFT_CLICK_BLOCK -> leftClick(player, sign, e)
            Action.RIGHT_CLICK_BLOCK -> rightClick(player, sign, e)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun leftClick(player: Player, sign: SocialBuildSign, e: PlayerInteractEvent) {
        if (player.canGood(sign)) {
            Good.create(player.uuid(), sign.params.id).save()
            sign.updateGoodCount(e.getClickedBlock().getState() as Sign)
        }
        if (player.isCreative()) {
            e.setCancelled(true)
        }
    }

    private fun rightClick(player: Player, sign: SocialBuildSign, e: PlayerInteractEvent) {
        if (!player.canUnGood(sign)) {
            return
        }
        Good.where(player.uuid(), sign.params.id)?.destroy()
        sign.updateGoodCount(e.getClickedBlock().getState() as Sign)
    }
}
