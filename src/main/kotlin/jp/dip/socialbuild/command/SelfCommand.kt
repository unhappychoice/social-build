package jp.dip.socialbuild.command

import org.bukkit.entity.Player
import jp.dip.socialbuild.model.Person
import jp.dip.socialbuild.Notifier

/**
 * Created by unhappychoice on 2015/05/25.
 */

public class SelfCommand() {
    class object {
        fun execute(sender: Player, args: Array<out String>) {
            val person = Person.findOrSave(sender)
            Notifier.goodCount(sender, person.goodCount())
        }
    }
}