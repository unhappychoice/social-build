package jp.dip.socialbuild.repository

import jp.dip.socialbuild.Database
import java.sql.ResultSet
import java.sql.Date
import jp.dip.socialbuild.DatabaseConfig


/**
 * Created by yueki on 2015/05/23.
 */

public class PersonRepository {

    /**
     * person parameters data class
     */
    public data class PersonParams(
            val id: String, val name: String, val createdAt: Date, val updatedAt: Date
    )

    class object {

        /**
         * setup person table
         */
        public fun setupTable(_: DatabaseConfig) {
            Database().execute(peopleTableCreateSQL(), { it })
        }

        /**
         * save a person by person parameter
         */
        public fun save(person: PersonParams): Boolean {
            return Database().execute(insertSql(), { params ->
                params.setString(1, person.id)
                params.setString(2, person.name)
                params.setDate(3, person.createdAt)
                params.setDate(4, person.updatedAt)
                params
            })
        }

        /**
         * update the person by person parameter
         */
        public fun update(person: PersonParams): Boolean {
            return Database().update(updateSql(), { params ->
                params.setString(1, person.name)
                params.setDate(2, person.updatedAt)
                params.setString(3, person.id)
                params
            })
        }

        /**
         * get a person by id
         */
        public fun get(id: String): PersonParams? {
            val result = Database().query(selectSql(), { params ->
                params.setString(1, id)
                params
            })
            if (result?.next() ?: false) {
                return PersonParams(
                        id = id,
                        name = (result?.getString("name") ?: ""),
                        createdAt = (result?.getDate("created_at") ?: Date(0)),
                        updatedAt = (result?.getDate("updated_at") ?: Date(0))
                )
            } else {
                return null
            }
        }

        public fun where(name: String): PersonParams? {
            val result = Database().query(whereSql(), { params ->
                params.setString(1, name)
                params
            })

            if (result == null) {
                return null
            }

            when(result.next()) {
                true -> return paramsFromResult(result)
                else -> return null
            }
        }

        // private

        private fun peopleTableCreateSQL() = """
            CREATE TABLE IF NOT EXISTS people (
                id VARCHAR(127) NOT NULL PRIMARY KEY,
                name VARCHAR(56) NOT NULL,
                created_at DATETIME,
                updated_at DATETIME
            )
        """

        private fun paramsFromResult(result: ResultSet): PersonParams {
            return PersonParams(
                    id = result.getString("id"),
                    name = result.getString("name"),
                    createdAt = result.getDate("created_at"),
                    updatedAt = result.getDate("updated_at")
            )
        }
        private fun insertSql() = " INSERT INTO people ( id, name, created_at, updated_at ) VALUES ( ?, ?, ?, ? ); "
        private fun selectSql() = " SELECT * FROM people WHERE id = ? ; "
        private fun whereSql()  = " SELECT * FROM people WHERE name = ? ; "
        private fun updateSql() = " UPDATE people SET name = ? , updated_at = ? WHERE id = ? ; "
    }
}