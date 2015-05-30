package jp.dip.socialbuild

import java.sql.DriverManager
import java.sql.Connection
import java.util.logging.Logger
import java.sql.ResultSet
import jp.dip.socialbuild.repository.PersonRepository
import java.sql.Statement
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 * Created by yueki on 2015/05/23.
 */

public class Database {
    class object {

        /**
         * creates and holds new connection
         */
        public fun connect(config: DatabaseConfig) {
            println("connect to database")
            println("driver: ${config.driver()}")
            println("url: ${config.url()}")
            println("user: ${config.user()}")
            println("password: ${config.password()}")

            initializeDriver(config)
            connection = DriverManager.getConnection(
                    config.url(),
                    config.user(),
                    config.password()
            )
            println("connected")
        }

        private fun initializeDriver(config: DatabaseConfig) {
            when(config.adapter()) {
                "sqlite" -> Class.forName(config.driver())
                "mysql"  -> Class.forName(config.driver()).newInstance()
            }
        }

        private var connection: Connection? = null
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
        try {
            blk(preparedStatement(sql)).execute()
        } catch(e: SQLException) {
            return false
        }
        return true
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun preparedStatement(sql: String) = connection?.prepareStatement(sql)!!
}