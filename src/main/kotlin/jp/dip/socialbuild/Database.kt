package jp.dip.socialbuild

import java.sql.DriverManager
import java.sql.Connection
import java.util.logging.Logger
import java.sql.ResultSet
import jp.dip.socialbuild.repository.PersonRepository
import java.sql.Statement
import java.sql.PreparedStatement

/**
 * Created by yueki on 2015/05/23.
 */

public class Database {
    class object {
        /**
         * creates and holds new connection
         */
        fun connect(driver: String, url: String, user: String, password: String) {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection(driver + ":" + url + "/database.db", user, password)
        }
        private var connection: Connection? = null
    }

    /**
     * creates tables if not created
     */
    public fun initializeTables() {
        connection?.createStatement()?.execute(goodsTableCreateSQL)
    }

    /**
     * executes a update
     */
    public fun update(sql: String, blk: (statement :PreparedStatement) -> PreparedStatement): Boolean {
        return blk(preparedStatement(sql)).executeUpdate() > 0
    }

    /**
     * executes a query
     */
    public fun query(sql: String, blk: (statement :PreparedStatement) -> PreparedStatement): ResultSet? {
        return blk(preparedStatement(sql)).executeQuery()
    }

    /**
     * execute
     */
    public fun execute(sql: String, blk: (statement :PreparedStatement) -> PreparedStatement): Boolean {
        return blk(preparedStatement(sql)).execute()
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun preparedStatement(sql: String) = connection?.prepareStatement(sql)!!

    private val goodsTableCreateSQL = """
        CREATE TABLE IF NOT EXISTS goods (
            person_id VARCHAR(127) NOT NULL,
            sign_id INTEGER NOT NULL,
            created_at DATETIME,
            updated_at DATETIME
        )
    """
}