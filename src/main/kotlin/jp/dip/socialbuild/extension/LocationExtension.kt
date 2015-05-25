package jp.dip.socialbuild.extension

import org.bukkit.Location

/**
 * Created by unhappychoice on 2015/05/25.
 */

public fun Location.prettyString(): String {
    return "x: ${Math.round(getX())} y: ${Math.round(getY())} z: ${Math.round(getZ())}"
}