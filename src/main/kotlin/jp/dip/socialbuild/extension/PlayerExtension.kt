package jp.dip.socialbuild.extension

import org.bukkit.entity.Player
import org.bukkit.GameMode
import jp.dip.socialbuild.model.SocialBuildSign
import jp.dip.socialbuild.model.Good

/**
 * Created by unhappychoice on 2015/05/24.
 */

/**
 * whether breaking social build signs
 */
public fun Player.canBreak(sign: SocialBuildSign): Boolean {
    return this.owns(sign) && this.isSurvival()
}

/**
 * whether not breaking social build signs
 */
public fun Player.cannotBreak(sign: SocialBuildSign): Boolean {
    return !canBreak(sign)
}

/**
 * whether not creating good
 */
public fun Player.canGood(sign: SocialBuildSign): Boolean {
    return true
    //return !owns(sign) && !Good.exists(uuid(), sign.params.id)
}

/**
 * whether delete good
 */
public fun Player.canUnGood(sign: SocialBuildSign): Boolean {
    return true
    //return !owns(sign) && Good.exists(uuid(), sign.params.id)
}

/**
 * whether the player has social build sign
 */
public fun Player.owns(sign: SocialBuildSign): Boolean {
    return sign.params.ownerId.equals(getUniqueId().toString())
}

/**
 * player's uuid
 */
public fun Player.uuid(): String {
    return getUniqueId().toString()
}

/**
 * whether player is survival
 */
public fun Player.isSurvival(): Boolean {
    return getGameMode().equals(GameMode.SURVIVAL)
}

/**
 * whether player is creative
 */
public fun Player.isCreative(): Boolean {
    return getGameMode().equals(GameMode.CREATIVE)
}
