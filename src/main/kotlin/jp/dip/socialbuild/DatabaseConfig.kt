package jp.dip.socialbuild

/**
 * Created by unhappychoice on 2015/05/29.
 */

public class DatabaseConfig(val settings: Map<String, Any>, val dataFolder: String) {

    public fun adapter(): String = settings["adapter"] as String? ?: ""
    public fun user(): String = settings["username"] as String? ?: ""
    public fun password(): String = settings["password"] as String? ?: ""

    public fun url(): String {
        return when(adapter()) {
            "sqlite" -> "jdbc:sqlite:" + dataFolder + "/database.db"
            "mysql"  -> "jdbc:" + settings["url"] as String
            else     -> ""
        }
    }

    public fun driver(): String {
        return when(adapter()) {
            "sqlite" -> "org.sqlite.JDBC"
            "mysql"  -> "com.mysql.jdbc.Driver"
            else     -> ""
        }
    }

    public fun autoIncrement(): String {
        return if (adapter().equals("mysql")) "AUTO_INCREMENT" else "AUTOINCREMENT"
    }
}