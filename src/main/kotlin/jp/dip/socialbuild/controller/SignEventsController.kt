package jp.dip.socialbuild.controller

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.block.BlockBreakEvent
import jp.dip.socialbuild.model.SocialBuildSign
import org.bukkit.block.Block
import org.bukkit.entity.Player
import jp.dip.socialbuild.extension.isSignItem
import jp.dip.socialbuild.extension.canBreak
import org.bukkit.event.block.Action
import jp.dip.socialbuild.model.Good
import jp.dip.socialbuild.extension.uuid
import org.bukkit.block.Sign
import jp.dip.socialbuild.extension.canGood
import jp.dip.socialbuild.extension.canUnGood
import jp.dip.socialbuild.extension.isCreative
import jp.dip.socialbuild.model.SignText
import jp.dip.socialbuild.Notifier
import jp.dip.socialbuild.extension.canPlace

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class SignEventsController : Listener {

    /**
     * an event handler for placing sign
     */
    EventHandler
    public fun onSignPlace(e: SignChangeEvent) {
        val player = e.getPlayer()
        val location = e.getBlock().getLocation()
        val lines = e.getLines().toList()

        if (lines[0] != "[sb]") {
            return
        }

        val sign = SocialBuildSign.create(player, location, lines)

        if (player.canPlace() && sign.save()) {
            SignText.replaceSignText(e)
            Notifier.createSign(player)
        } else {
            Notifier.failToCreateSign(player)
        }
    }

    /**
     * an event handler for breaking sign
     */
    EventHandler
    public fun onSignBreak(e: BlockBreakEvent) {
        // TODO: reconsider breaking process

        val block = e.getBlock()
        val player = e.getPlayer()

        if (!block.isSignItem()) {
            return
        }

        val sign = SocialBuildSign.find(block.getLocation())
        if (sign == null) {
            return
        }

        if (player.canBreak(sign) && sign.destroy()) {
            Notifier.destroySign(player)
        } else {
            e.setCancelled(true)
            Notifier.failToDestroySign(player)
        }
    }

    /**
     * an event handler for clicking sign
     */
    EventHandler
    public fun onClickSign(e: PlayerInteractEvent) {
        val block = e.getClickedBlock()
        val player = e.getPlayer()

        if (!block.isSignItem()) {
            return
        }

        val sign = SocialBuildSign.find(block.getLocation())
        if (sign == null) {
            return
        }

        if (player.isCreative()) {
            e.setCancelled(true)
        }

        when(e.getAction()) {
            Action.LEFT_CLICK_BLOCK -> leftClick(player, sign, block)
            Action.RIGHT_CLICK_BLOCK -> rightClick(player, sign, block)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun leftClick(player: Player, sign: SocialBuildSign, block: Block) {
        if (player.canGood(sign)) {
            Good.create(player.uuid(), sign.params.id).save()
            sign.updateGoodCount(block.getState() as Sign)
            Notifier.sendGood(player)
        } else {
            Notifier.failToSendGood(player)
        }
    }

    private fun rightClick(player: Player, sign: SocialBuildSign, block: Block) {
        if (player.canUnGood(sign)) {
            Good.where(player.uuid(), sign.params.id)?.destroy()
            sign.updateGoodCount(block.getState() as Sign)
            Notifier.cancelGood(player)
        } else {
            Notifier.failToCancelGood(player)
        }
    }
}
