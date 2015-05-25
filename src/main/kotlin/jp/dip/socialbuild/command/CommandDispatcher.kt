package jp.dip.socialbuild.command

import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.Command
import jp.dip.socialbuild.extension.isSocialBuildCommand

/**
 * Created by unhappychoice on 2015/05/25.
 */

public class CommandDispatcher : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (command?.isSocialBuildCommand() ?: false && sender is Player) {
            try {
                dispatch(sender, args)
                return true
            } catch(e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            return false
        }
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun dispatch(sender: Player?, args: Array<out String>?) {
        // TODO: check permission

        if (sender == null || args == null) {
            throw IllegalArgumentException()
        }

        if (isSelfCommand(args)) {
            SelfCommand.execute(sender, args)
        } else if (isTopCommand(args)) {

        } else if (isOthersCommand(args)) {
            OthersCommand.execute(sender, args)
        } else {
            throw IllegalArgumentException()
        }
    }

    private fun isSelfCommand(args: Array<out String>): Boolean {
        return args.size() == 0
    }

    private fun isTopCommand(args: Array<out String>): Boolean {
        return args.size() > 0 && args[0].equalsIgnoreCase("top")
    }

    private fun isOthersCommand(args: Array<out String>): Boolean {
        return args.size() > 0
    }
}