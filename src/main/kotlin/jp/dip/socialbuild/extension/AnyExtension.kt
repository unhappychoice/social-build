package jp.dip.socialbuild.extension

import java.sql.Timestamp

/**
 * Created by unhappychoice on 2015/05/24.
 */

public fun Any.currentDate(): Timestamp = Timestamp(java.util.Date().getTime())