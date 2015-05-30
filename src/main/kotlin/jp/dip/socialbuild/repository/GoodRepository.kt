package jp.dip.socialbuild.repository

import java.sql.Date
import jp.dip.socialbuild.Database
import java.sql.ResultSet
import jp.dip.socialbuild.DatabaseConfig

/**
 * Created by yueki on 2015/05/23.
 */

public class GoodRepository {

    /**
     * good parameters data class
     */
    public data class GoodParams(
            val id: Int,
            val personId: String,
            val signId: Int,
            val createdAt: Date,
            val updatedAt: Date
    )

    class object {

        /**
         * setup person table
         */
        public fun setupTable(config: DatabaseConfig) {
            Database().execute(goodTableCreateSQL(config), { it })
        }

        /**
         * save a good by good parameter
         */
        public fun save(good: GoodParams): Boolean {
            return Database().execute(insertSql(), { params ->
                params.setString(1, good.personId)
                params.setInt(2, good.signId)
                params.setDate(3, good.createdAt)
                params.setDate(4, good.updatedAt)
                params
            })
        }

        /**
         * update the good by good parameter
         */
        public fun update(good: GoodParams): Boolean {
            return Database().update(updateSql(), { params ->
                params.setString(1, good.personId)
                params.setInt(2, good.signId)
                params.setString(3, good.personId)
                params
            })
        }

        /**
         * get the good by id
         */
        public fun get(id: Int): GoodParams? {
            val result = Database().query(selectSql(), { params ->
                params.setInt(1, id)
                params
            })
            return goodParamsFromResult(result)
        }

        /**
         * where by player id and sign id
         */
        public fun where(personId: String, signId: Int): GoodParams? {
            val result = Database().query(whereSql(), { params ->
                params.setString(1, personId)
                params.setInt(2, signId)
                params
            })
            return goodParamsFromResult(result)
        }

        /**
         * good count by sign id
         */
        public fun count(signId: Int): Int {
            val result = Database().query(countSql(), { params ->
                params.setInt(1, signId)
                params
            })
            if (result?.next() ?: false) {
                return result?.getInt("count") ?: 0
            } else {
                return 0
            }
        }

        /**
         * delete the good by id
         */
        public fun delete(id: Int): Boolean {
            return Database().execute(deleteSql(), { params ->
                params.setInt(1, id)
                params
            })
        }

        /**
         * delete the good by sign id
         */
        public fun deleteBySignId(signId: Int): Boolean {
            return Database().execute(deleteBySignIdSql(), { params ->
                params.setInt(1, signId)
                params
            })
        }

        // -----------------------------------------------------------------------------------------
        // private

        private fun goodTableCreateSQL(config: DatabaseConfig) = """
            CREATE TABLE IF NOT EXISTS goods (
                id INTEGER NOT NULL PRIMARY KEY ${config.autoIncrement()},
                person_id VARCHAR(127) NOT NULL,
                sign_id INTEGER NOT NULL,
                created_at DATETIME,
                updated_at DATETIME
            )
        """

        private fun goodParamsFromResult(result: ResultSet?): GoodParams? {
            if (result == null || !result.next()) {
                return null
            }

            return GoodParams(
                    id = result.getInt("id"),
                    personId = result.getString("person_id") ?: "",
                    signId = result.getInt("sign_id"),
                    createdAt = (result.getDate("created_at") ?: Date(0)),
                    updatedAt = (result.getDate("updated_at") ?: Date(0))
            )
        }

        private fun insertSql() = " INSERT INTO goods ( person_id, sign_id, created_at, updated_at ) VALUES ( ?, ?, ?, ? ); "
        private fun updateSql() = " UPDATE goods SET person_id = ?, sign_id = ?, updated_at = ? WHERE id = ? ; "
        private fun selectSql() = " SELECT * FROM goods WHERE id = ? ; "
        private fun whereSql()  = " SELECT * FROM goods WHERE person_id = ? AND sign_id = ? ; "
        private fun countSql()  = " SELECT COUNT(*) AS count FROM goods WHERE sign_id = ? ; "
        private fun deleteSql() = " DELETE FROM goods WHERE id = ? ; "
        private fun deleteBySignIdSql() = " DELETE FROM goods WHERE sign_id = ? ; "
    }
}