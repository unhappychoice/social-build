package jp.dip.socialbuild

import org.bukkit.entity.Player
import jp.dip.socialbuild.extension.blue
import org.bukkit.Bukkit
import jp.dip.socialbuild.extension.red
import jp.dip.socialbuild.extension.darkAqua
import jp.dip.socialbuild.extension.yellow
import jp.dip.socialbuild.model.Person
import jp.dip.socialbuild.extension.prettyString
import jp.dip.socialbuild.extension.green

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class Notifier {
    class object {
        public fun noPermission(player: Player, permission: String) {
            player.notice(Message["noPermission"].yellow())
        }

        public fun createSign(player: Player) {
            player.notice(Message["createdSign"].blue())

            val message = Message["createdSignBroadcast"].blue()
                    .replace("{{player}}", player.getName())
                    .replace("{{location}}", player.getLocation().prettyString())
            broadcast(message)
        }

        public fun failToCreateSign(player: Player) {
            player.notice(Message["failToCreateSign"].red())
        }

        public fun destroySign(player: Player) {
            player.notice(Message["destroyedSign"].blue())
        }

        public fun failToDestroySign(player: Player) {
            player.notice(Message["failToDestroySign"].red())
        }

        public fun sentGood(player: Player) {
            player.notice(Message["sentGood"].blue())
        }

        public fun failToSendGood(player: Player) {
            player.notice(Message["failToSendGood"].red())
        }

        public fun cancelGood(player: Player) {
            player.notice(Message["cancelGood"].yellow())
        }

        public fun failToCancelGood(player: Player) {
            player.notice(Message["failToCancelGood"].red())
        }

        public fun goodCount(player: Player, count: Int) {
            val message = Message["goodCount"].blue()
                    .replace("{{count}}", count.toString())
            player.notice(message)
        }

        public fun othersGoodCount(player: Player, other: Person, count: Int) {
            val message = Message["othersGoodCount"].blue()
                    .replace("{{other}}", other.params.name)
                    .replace("{{count}}", count.toString())

            player.notice(message)
        }

        public fun noPlayer(player: Player, name: String) {
            val message = Message["noPlayer"].red()
                    .replace("{{player}}", name)
            player.notice(message)
        }

        public fun fillInName(player: Player) {
            player.notice(Message["fillInName"].yellow())
        }

        public fun help(player: Player) {
            player.notice("=========================".yellow())
            player.notice("/sb - ".blue() + Message["helpSB"].blue())
            player.notice("/sb <name> - ".blue() + Message["helpOthers"].blue())
            player.notice("/sb help - ".blue() + Message["helpHelp"].blue())
            player.notice("=========================".yellow())
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
    return "[SocialBuild] ".darkAqua()
}