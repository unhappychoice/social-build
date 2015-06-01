package jp.dip.socialbuild.model

import jp.dip.socialbuild.repository.PersonRepository.PersonParams
import jp.dip.socialbuild.repository.PersonRepository
import org.bukkit.entity.Player
import jp.dip.socialbuild.extension.uuid
import jp.dip.socialbuild.extension.currentDate

/**
 * Created by unhappychoice on 2015/05/25.
 */

public class Person(val params: PersonParams) {

    class object {
        public fun build(player: Player): Person {
            val params = PersonParams(
                    id = player.uuid(),
                    name = player.getName(),
                    createdAt = currentDate(),
                    updatedAt = currentDate()
            )
            return Person(params)
        }

        public fun create(player: Player): Person {
            val person = build(player)
            person.save()
            return person
        }

        public fun find(uuid: String): Person? {
            val params = PersonRepository.get(uuid)
            when(params) {
                null -> return null
                else -> return Person(params)
            }
        }

        public fun findOrCreate(player: Player): Person {
            val params = PersonRepository.get(player.uuid())
            when(params) {
                null -> return create(player)
                else -> return Person(params)
            }
        }

        public fun where(name: String): Person? {
            val params = PersonRepository.where(name)
            when(params) {
                null -> return null
                else -> return Person(params)
            }
        }
    }

    public fun save(): Boolean {
        return PersonRepository.save(params)
    }

    public fun goodCount(): Int {
        return SocialBuildSign
                .where(params.id)
                .fold(0) { acc, sign -> acc + sign.goodCount() }
    }
}