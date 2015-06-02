package jp.dip.socialbuild.command

import org.bukkit.entity.Player
import jp.dip.socialbuild.Notifier

/**
 * Created by unhappychoice on 2015/06/02.
 */

public class HelpCommand {
    class object {
        public fun execute(sender: Player, args: Array<out String>) {
            Notifier.help(sender)
        }
    }
}