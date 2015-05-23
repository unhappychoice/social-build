package jp.dip.socialbuild.repository

import java.sql.Date
import jp.dip.socialbuild.Database

/**
 * Created by yueki on 2015/05/23.
 */

public class SignRepository {

    /**
     * sign parameters data class
     */
    public data class SignParams(
            val id: Int, val ownerId: String, val name: String,
            val x: Int, val y: Int, val z: Int,
            val createdAt: Date, val updatedAt: Date
    )

    class object {

        /**
         * setup sign table
         */
        public fun setupTable() {
            Database().execute(signsTableCreateSQL(), { it })
        }

        /**
         * save a sign by sign parameter
         */
        public fun save(sign: SignParams): Boolean {
            return Database().execute(insertSql(), { params ->
                params.setString(1, sign.ownerId)
                params.setString(2, sign.name)
                params.setInt(3, sign.x)
                params.setInt(4, sign.y)
                params.setInt(5, sign.z)
                params.setDate(6, sign.createdAt)
                params.setDate(7, sign.updatedAt)
                params
            })
        }

        /**
         * update the sign by sign parameter
         */
        public fun update(sign: SignParams): Boolean {
            return Database().update(updateSql(), { params ->
                params.setString(1, sign.ownerId)
                params.setString(2, sign.name)
                params.setInt(3, sign.x)
                params.setInt(4, sign.y)
                params.setInt(5, sign.z)
                params.setDate(6, sign.updatedAt)
                params.setInt(7, sign.id)
                params
            })
        }

        /**
         * get the sign by id
         */
        public fun get(id: Int): SignParams {
            val result = Database().query(selectSql(), { params ->
                params.setInt(1, id)
                params
            })
            return SignParams(
                    id = id,
                    ownerId = result?.getString("owner_id") ?: "",
                    name = result?.getString("name") ?: "",
                    x = result?.getInt("x") ?: 0,
                    y = result?.getInt("y") ?: 0,
                    z = result?.getInt("z") ?: 0,
                    createdAt = result?.getDate("created_at") ?: Date(0),
                    updatedAt = result?.getDate("updated_at") ?: Date(0)
            )
        }

        /**
         * delete the sign by id
         */
        public fun delete(id: Int): Boolean {
            return Database().execute(deleteSql(), { params ->
                params.setInt(1, id)
                params
            })
        }

        // -----------------------------------------------------------------------------------------
        // private

        private fun signsTableCreateSQL() = """
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

        private fun insertSql() = " INSERT INTO signs ( owner_id, name, x, y, z, created_at, updated_at ) VALUES ( ?, ?, ?, ?, ?, ?, ? ); "
        private fun updateSql() = " UPDATE signs SET owner_id = ?, name = ?, x = ?, y = ?, z = ?, updated_at = ? WHERE id = ? "
        private fun selectSql() = " SELECT * FROM signs WHERE id = ? ; "
        private fun deleteSql() = " DELETE FROM signs WHERE id = ? "
    }
}
