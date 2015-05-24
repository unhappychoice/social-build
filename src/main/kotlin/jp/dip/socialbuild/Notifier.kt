package jp.dip.socialbuild

import org.bukkit.entity.Player
import jp.dip.socialbuild.extension.blue
import org.bukkit.Bukkit
import jp.dip.socialbuild.extension.green
import jp.dip.socialbuild.extension.red
import jp.dip.socialbuild.extension.darkAqua
import jp.dip.socialbuild.extension.yellow

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class Notifier {
    class object {
        public fun createSign(player: Player) {
            player.notice("created a social build sign !!".blue())
            broadcast(player.getName().green() + " created a social build sign !!".blue())
        }

        public fun failToCreateSign(player: Player) {
            player.notice("failed to creat a social build sign".red())
        }

        public fun destroySign(player: Player) {
            player.notice("destroyed the social build sign".blue())
        }

        public fun failToDestroySign(player: Player) {
            player.notice("fail to destroyed the social build sign".red())
        }

        public fun sendGood(player: Player) {
            player.notice("sent good !!".blue())
        }

        public fun failToSendGood(player: Player) {
            player.notice("fail to sent good".red())
        }

        public fun cancelGood(player: Player) {
            player.notice("cancel good".yellow())
        }

        public fun failToCancelGood(player: Player) {
            player.notice("fail to cancel good".red())
        }
    }
}

public fun Player.notice(message: String) {
    sendMessage(header() + message)
}

public fun Any.broadcast(message: String) {
    Bukkit.broadcastMessage(header() + message)
}

// -------------------------------------------------------------------------------------------------
// private

private fun header(): String {
    return "[SB]".darkAqua()
}