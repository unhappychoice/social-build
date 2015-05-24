package jp.dip.socialbuild.extension

import org.bukkit.block.Block
import org.bukkit.Material

/**
 * Created by unhappychoice on 2015/05/24.
 */

public fun Block.isSignItem(): Boolean {
    return getType() == Material.WALL_SIGN || getType() == Material.SIGN_POST
}
