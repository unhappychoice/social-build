package jp.dip.socialbuild

import org.bukkit.entity.Player
import jp.dip.socialbuild.extension.blue
import org.bukkit.Bukkit
import jp.dip.socialbuild.extension.green
import jp.dip.socialbuild.extension.red
import jp.dip.socialbuild.extension.darkAqua
import jp.dip.socialbuild.extension.yellow
import jp.dip.socialbuild.model.Person
import jp.dip.socialbuild.extension.prettyString

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class Notifier {
    class object {
        public fun noPermission(player: Player, permission: String) {
            player.notice("you don't have ${permission} permission")
        }

        public fun createSign(player: Player) {
            player.notice("Created a social build sign !!".blue())
            broadcast(player.getName().green() + " Created a social build sign at".blue() + " ${player.getLocation().prettyString()}".yellow() + " !!".blue())
        }

        public fun failToCreateSign(player: Player) {
            player.notice("Failed to creat a social build sign".red())
        }

        public fun destroySign(player: Player) {
            player.notice("Destroyed the social build sign".blue())
        }

        public fun failToDestroySign(player: Player) {
            player.notice("Fail to destroyed the social build sign".red())
        }

        public fun sendGood(player: Player) {
            player.notice("Sent good !!".blue())
        }

        public fun failToSendGood(player: Player) {
            player.notice("Fail to sent good".red())
        }

        public fun cancelGood(player: Player) {
            player.notice("Cancel good".yellow())
        }

        public fun failToCancelGood(player: Player) {
            player.notice("Fail to cancel good".red())
        }

        public fun goodCount(player: Player, count: Int) {
            player.notice("You have ${count} goods !!".blue())
        }

        public fun othersGoodCount(player: Player, other: Person, count: Int) {
            player.notice("${other.params.name} has ${count} goods !!".blue())
        }

        public fun noPlayer(player: Player, name: String) {
            player.notice("${name} does not exists".red())
        }

        public fun fillInName(player: Player) {
            player.notice("fill in the sign name on second line !!".yellow())
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
    return "[SB] ".darkAqua()
}