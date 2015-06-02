package jp.dip.socialbuild

/**
 * Created by unhappychoice on 2015/06/02.
 */

public object Message {
    public fun get(key: String): String {
        return messages[key] as String? ?: ""
    }

    public var messages: Map<String, Any> = mapOf()
}
