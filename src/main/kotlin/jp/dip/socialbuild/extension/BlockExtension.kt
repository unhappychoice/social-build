package jp.dip.socialbuild.extension

import org.bukkit.block.Block
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.Sign

/**
 * Created by unhappychoice on 2015/05/24.
 */

public fun Block.isSignItem(): Boolean {
    return getType().equals(Material.WALL_SIGN)
            || getType().equals(Material.SIGN_POST)
            || getType().equals(Material.SIGN)
}

public fun Block.isAttachedSign(): Boolean {
    faces().forEach {
        if (getRelative(it).isSignItem()) {
            return true
        }
    }
    return false
}

public fun Block.attachedSign(): List<Block> {
    return faces().filter({
        getRelative(it).isSignItem()
    }).map({
        getRelative(it)
    })
}

private fun faces(): List<BlockFace> {
    return listOf(
            BlockFace.EAST,
            BlockFace.WEST,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.UP,
            BlockFace.DOWN
    )
}