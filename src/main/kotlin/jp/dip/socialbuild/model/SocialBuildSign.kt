package jp.dip.socialbuild.model

import jp.dip.socialbuild.repository.SignRepository.SignParams
import org.bukkit.entity.Player
import jp.dip.socialbuild.repository.SignRepository
import org.bukkit.Location
import jp.dip.socialbuild.repository.GoodRepository
import jp.dip.socialbuild.extension.currentDate
import jp.dip.socialbuild.extension.uuid

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class SocialBuildSign(val params: SignParams) {
    class object {

        /**
         * factory
         */
        public fun build(player: Player, location: Location, lines: List<String>): SocialBuildSign {
            val params = SignParams(
                    id = 0,
                    name = lines[1],
                    ownerId = player.uuid(),
                    world = location.getWorld().getName(),
                    x = location.getBlockX(),
                    y = location.getBlockY(),
                    z = location.getBlockZ(),
                    createdAt = currentDate(),
                    updatedAt = currentDate()
            )
            return SocialBuildSign(params)
        }

        /**
         * find a sign by location
         */
        public fun find(location: Location): SocialBuildSign? {
            val params = SignRepository.where(
                    location.getWorld().getName(),
                    location.getBlockX(),
                    location.getBlockY(),
                    location.getBlockZ()
            )
            return if (params == null) null else SocialBuildSign(params)
        }

        /**
         * where by owner id
         */
        public fun where(ownerId: String): List<SocialBuildSign> {
            return SignRepository.where(ownerId).map { SocialBuildSign(it) }
        }
    }

    /**
     * save to database
     */
    public fun save(): Boolean {
        return SignRepository.save(params)
    }

    /**
     * delete from database
     */
    public fun destroy(): Boolean {
        return SignRepository.delete(params.id) && GoodRepository.deleteBySignId(params.id)
    }

    /**
     * update sign count
     */
    public fun updateGoodCount(sign: org.bukkit.block.Sign) {
        sign.setLine(3, SignText.goodLine(goodCount()))
        sign.update()
    }

    /**
     * good count
     */
    public fun goodCount(): Int {
        return GoodRepository.count(params.id)
    }
}