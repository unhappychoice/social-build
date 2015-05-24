package jp.dip.socialbuild.extension

import java.sql.Date

/**
 * Created by unhappychoice on 2015/05/24.
 */

// -----------------------------------------------------------------------------------------
// private

public fun Any.currentDate(): Date = Date(java.util.Date().getTime())