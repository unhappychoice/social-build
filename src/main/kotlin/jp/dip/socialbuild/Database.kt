package jp.dip.socialbuild

import java.sql.DriverManager
import java.sql.Connection
import java.util.logging.Logger

/**
 * Created by yueki on 2015/05/23.
 */

public class Database {
    class object {
        fun connect(driver: String, url: String, user: String, password: String) {
            Class.forName("org.sqlite.JDBC")
            _connection = DriverManager.getConnection(driver + ":" + url + "/database.db", user, password)
        }
        private var _connection : Connection? = null
    }

    /**
     * creates tables if not created
     */
    public fun initializeTables() {
        _connection?.createStatement()?.execute(peopleTableCreateSQL)
        _connection?.createStatement()?.execute(signsTableCreateSQL)
        _connection?.createStatement()?.execute(goodsTableCreateSQL)
    }

    private val peopleTableCreateSQL = """
        CREATE TABLE IF NOT EXISTS people (
            id VARCHAR(127) NOT NULL PRIMARY KEY,
            name VARCHAR(56) NOT NULL,
            created_at DATETIME,
            updated_at DATETIME
        )
    """

    private val signsTableCreateSQL = """
        CREATE TABLE IF NOT EXISTS signs (
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            owner_id VARCHAR(127) NOT NULL,
            x INTEGER NOT NULL,
            y INTEGER NOT NULL,
            z INTEGER NOT NULL,
            name VARCHAR(255) NOT NULL,
            created_at DATETIME,
            updated_at DATETIME
        )
    """

    private val goodsTableCreateSQL = """
        CREATE TABLE IF NOT EXISTS goods (
            person_id VARCHAR(127) NOT NULL,
            sign_id INTEGER NOT NULL,
            created_at DATETIME,
            updated_at DATETIME
        )
    """
}