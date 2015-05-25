package jp.dip.socialbuild.extension

import org.bukkit.entity.Player
import org.bukkit.GameMode
import jp.dip.socialbuild.model.SocialBuildSign
import jp.dip.socialbuild.model.Good
import jp.dip.socialbuild.Notifier

/**
 * Created by unhappychoice on 2015/05/24.
 */

/**
 * whether player can place social build sign
 */
public fun Player.canPlace(): Boolean {
    return checkPermission("sb.place")
}

/**
 * whether breaking social build signs
 */
public fun Player.canBreak(sign: SocialBuildSign): Boolean {
    return checkPermission("sb.break") && this.owns(sign) && this.isSurvival()
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
    return checkPermission("sb.vote") && !Good.exists(uuid(), sign.params.id)
}

/**
 * whether delete good
 */
public fun Player.canUnGood(sign: SocialBuildSign): Boolean {
    return checkPermission("sb.cancel") && Good.exists(uuid(), sign.params.id)
}

/**
 * whether player owns sign
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

/**
 * check permission
 */
public fun Player.checkPermission(permission: String): Boolean {
    if (hasPermission(permission)) {
        return true
    } else {
        Notifier.noPermission(this, permission)
        return false
    }
}