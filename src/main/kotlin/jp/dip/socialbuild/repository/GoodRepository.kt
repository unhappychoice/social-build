package jp.dip.socialbuild.repository

import java.sql.Date
import jp.dip.socialbuild.Database

/**
 * Created by yueki on 2015/05/23.
 */

public class GoodRepository {

    /**
     * good parameters data class
     */
    public data class GoodParams(
            val id: Int,
            val playerId: String,
            val signId: Int,
            val createdAt: Date,
            val updatedAt: Date
    )

    class object {

        /**
         * setup person table
         */
        public fun setupTable() {
            Database().execute(goodTableCreateSQL(), { it })
        }

        /**
         * save a person by person parameter
         */
        public fun save(good: GoodParams): Boolean {
            return Database().execute(insertSql(), { params ->
                params.setString(1, good.playerId)
                params.setInt(2, good.signId)
                params.setDate(3, good.createdAt)
                params.setDate(4, good.updatedAt)
                params
            })
        }

        /**
         * update the person by person parameter
         */
        public fun update(good: GoodParams): Boolean {
            return Database().update(updateSql(), { params ->
                params.setString(1, good.playerId)
                params.setInt(2, good.signId)
                params.setString(3, good.playerId)
                params
            })
        }

        /**
         * get a person by id
         */
        public fun get(id: Int): GoodParams {
            val result = Database().query(selectSql(), { params ->
                params.setInt(1, id)
                params
            })
            return GoodParams(
                    id = result?.getInt("id") ?: 0,
                    playerId = result?.getString("player_id") ?: "",
                    signId = result?.getInt("sign_id") ?: 0,
                    createdAt = (result?.getDate("created_at") ?: Date(0)),
                    updatedAt = (result?.getDate("updated_at") ?: Date(0))
            )
        }

        // private

        private fun goodTableCreateSQL() = """
            CREATE TABLE IF NOT EXISTS goods (
                id INTEGER NOT NULL PRIMARY_KEY AUTOINCREMENT,
                person_id VARCHAR(127) NOT NULL,
                sign_id INTEGER NOT NULL,
                created_at DATETIME,
                updated_at DATETIME
            )
        """

        private fun insertSql() = " INSERT INTO goods ( player_id, sign_id, created_at, updated_at ) VALUES ( ?, ?, ?, ? ); "
        private fun updateSql() = " UPDATE goods SET player_id = ?, sign_id = ?, updated_at = ? WHERE id = ? "
        private fun selectSql() = " SELECT * FROM goods WHERE id = ? ; "
    }
}