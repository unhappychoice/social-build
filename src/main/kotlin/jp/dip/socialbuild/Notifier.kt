package jp.dip.socialbuild

import org.bukkit.entity.Player
import jp.dip.socialbuild.extension.blue
import org.bukkit.Bukkit
import jp.dip.socialbuild.extension.green
import jp.dip.socialbuild.extension.red

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class Notifier {
    class object {
        public fun createSign(player: Player) {
            player.sendMessage("created a social build sign !!".blue())
            Bukkit.broadcastMessage(player.getName().green() + " created a social build sign !!".blue())
        }

        public fun failToCreateSign(player: Player) {
            player.sendMessage("failed to creat a social build sign".red())
        }
    }
}