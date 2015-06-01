package jp.dip.socialbuild.repository

import java.sql.Date
import jp.dip.socialbuild.Database
import java.sql.ResultSet
import jp.dip.socialbuild.DatabaseConfig

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
        public fun setupTable(config: DatabaseConfig) {
            Database().execute(signsTableCreateSQL(config), { it })
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
        public fun get(id: Int): SignParams? {
            val result = Database().query(selectSql(), { params ->
                params.setInt(1, id)
                params
            })

            if (result != null && result.next()) {
                return signParams(result)
            } else {
                return null
            }
        }

        /**
         * where by location
         */
        public fun where(x: Int, y: Int, z: Int): SignParams? {
            val result = Database().query(whereSql(), { params ->
                params.setInt(1, x)
                params.setInt(2, y)
                params.setInt(3, z)
                params
            })

            if (result != null && result.next()) {
                return signParams(result)
            } else {
                return null
            }
        }

        /**
         * where by owner id
         */
        public fun where(ownerId: String): List<SignParams> {
            val result = Database().query(whereByOwnerSql(), { params ->
                params.setString(1, ownerId)
                params
            })

            if (result == null) {
                return listOf()
            }

            val paramsList = arrayListOf<SignParams>()
            while(result.next()) {
                paramsList.add(signParams(result))
            }

            return paramsList.toList()
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

        private fun signsTableCreateSQL(config: DatabaseConfig) = """
            CREATE TABLE IF NOT EXISTS signs (
                id INTEGER NOT NULL PRIMARY KEY ${config.autoIncrement()},
                owner_id VARCHAR(127) NOT NULL,
                x INTEGER NOT NULL,
                y INTEGER NOT NULL,
                z INTEGER NOT NULL,
                name VARCHAR(255) NOT NULL,
                created_at DATETIME,
                updated_at DATETIME
            )
        """

        private fun signParams(result: ResultSet): SignParams {
            return SignParams(
                    id = result.getInt("id"),
                    ownerId = result.getString("owner_id") ?: "",
                    name = result.getString("name") ?: "",
                    x = result.getInt("x"),
                    y = result.getInt("y"),
                    z = result.getInt("z"),
                    createdAt = result.getDate("created_at") ?: Date(0),
                    updatedAt = result.getDate("updated_at") ?: Date(0)
            )
        }

        private fun insertSql() = " INSERT INTO signs ( owner_id, name, x, y, z, created_at, updated_at ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ; "
        private fun updateSql() = " UPDATE signs SET owner_id = ?, name = ?, x = ?, y = ?, z = ?, updated_at = ? WHERE id = ? ; "
        private fun selectSql() = " SELECT * FROM signs WHERE id = ? ; "
        private fun whereSql()  = " SELECT * FROM signs WHERE x = ? AND y = ? AND z = ? ; "
        private fun whereByOwnerSql() = " SELECT * FROM signs WHERE owner_id = ? ; "
        private fun deleteSql() = " DELETE FROM signs WHERE id = ? ; "
    }
}
