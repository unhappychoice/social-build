package jp.dip.socialbuild.command

import org.bukkit.entity.Player
import jp.dip.socialbuild.model.Person
import jp.dip.socialbuild.Notifier

/**
 * Created by unhappychoice on 2015/05/25.
 */

public class OthersCommand {
    class object {
        public fun execute(sender: Player, args: Array<out String>) {
            val person = Person.where(args.first())
            when(person) {
                null -> Notifier.noPlayer(sender, args.first())
                else -> Notifier.othersGoodCount(sender, person ,person.goodCount())
            }
        }
    }
}