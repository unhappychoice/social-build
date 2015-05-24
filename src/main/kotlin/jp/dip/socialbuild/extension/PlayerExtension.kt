package jp.dip.socialbuild.extension

import org.bukkit.entity.Player
import org.bukkit.GameMode
import jp.dip.socialbuild.model.Sign

/**
 * Created by unhappychoice on 2015/05/24.
 */

public fun Player.isSurvival(): Boolean {
    return getGameMode().equals(GameMode.SURVIVAL)
}

public fun Player.owns(sign: Sign): Boolean {
    return sign.params.ownerId.equals(getUniqueId().toString())
}