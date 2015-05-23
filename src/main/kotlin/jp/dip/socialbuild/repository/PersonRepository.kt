package jp.dip.socialbuild.repository

import jp.dip.socialbuild.Database
import java.sql.ResultSet
import java.sql.Time
import java.sql.Date


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
        public fun setupTable() {
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
        public fun get(id: String): PersonParams {
            val result = Database().query(selectSql(), { params ->
                params.setString(1, id)
                params
            })
            val name = (result?.getString("name") ?: "")
            val createdAt = (result?.getDate("created_at") ?: Date(0))
            val updatedAt = (result?.getDate("updated_at") ?: Date(0))
            return PersonParams(id, name, createdAt, updatedAt)
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

        private fun insertSql() = " INSERT INTO people ( id, name, created_at, updated_at ) VALUES ( ?, ?, ?, ? ); "
        private fun selectSql() = " SELECT * FROM people WHERE id = ? ; "
        private fun updateSql() = " UPDATE people SET name = ? , updated_at = ? WHERE id = ? "
    }
}