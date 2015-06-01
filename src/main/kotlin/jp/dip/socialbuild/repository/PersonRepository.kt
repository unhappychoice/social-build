package jp.dip.socialbuild.repository

import jp.dip.socialbuild.Database
import java.sql.ResultSet
import jp.dip.socialbuild.DatabaseConfig
import java.sql.Timestamp


/**
 * Created by yueki on 2015/05/23.
 */

public class PersonRepository {

    /**
     * person parameters data class
     */
    public data class PersonParams(
            val id: String, val name: String, val createdAt: Timestamp, val updatedAt: Timestamp
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
                params.setTimestamp(3, person.createdAt)
                params.setTimestamp(4, person.updatedAt)
                params
            })
        }

        /**
         * update the person by person parameter
         */
        public fun update(person: PersonParams): Boolean {
            return Database().update(updateSql(), { params ->
                params.setString(1, person.name)
                params.setTimestamp(2, person.updatedAt)
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
            return personParams(result)
        }

        public fun where(name: String): PersonParams? {
            val result = Database().query(whereSql(), { params ->
                params.setString(1, name)
                params
            })

            return personParams(result)
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

        private fun personParams(result: ResultSet?): PersonParams? {
            if (result == null || !result.next()) {
                return null
            }
            return PersonParams(
                    id = result.getString("id"),
                    name = result.getString("name"),
                    createdAt = result.getTimestamp("created_at"),
                    updatedAt = result.getTimestamp("updated_at")
            )
        }

        private fun insertSql() = " INSERT INTO people ( id, name, created_at, updated_at ) VALUES ( ?, ?, ?, ? ); "
        private fun selectSql() = " SELECT * FROM people WHERE id = ? ; "
        private fun whereSql()  = " SELECT * FROM people WHERE name = ? ; "
        private fun updateSql() = " UPDATE people SET name = ? , updated_at = ? WHERE id = ? ; "
    }
}