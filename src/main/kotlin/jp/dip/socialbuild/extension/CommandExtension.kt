package jp.dip.socialbuild.extension

import org.bukkit.command.Command

/**
 * Created by unhappychoice on 2015/05/25.
 */

public fun Command.isSocialBuildCommand(): Boolean {
    return getName().equalsIgnoreCase("sb")
}