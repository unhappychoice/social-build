package jp.dip.socialbuild.model

import jp.dip.socialbuild.repository.SignRepository.SignParams
import org.bukkit.entity.Player
import jp.dip.socialbuild.repository.SignRepository
import org.bukkit.ChatColor
import org.bukkit.Location
import java.sql.Date
import jp.dip.socialbuild.repository.GoodRepository
import org.bukkit.event.block.SignChangeEvent

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class Sign(val params: SignParams) {
    class object {

        /**
         * factory
         */
        public fun create(player: Player, location: Location, lines: List<String>): Sign {
            val params = SignParams(
                    id = 0,
                    name = lines[3],
                    ownerId = player.getUniqueId().toString(),
                    x = location.getBlockX(),
                    y = location.getBlockY(),
                    z = location.getBlockZ(),
                    createdAt = currentDate(),
                    updatedAt = currentDate()
            )
            return Sign(params)
        }

        /**
         * find a sign by location
         */
        public fun find(location: Location): Sign? {
            val params = SignRepository.where(location.getBlockX(), location.getBlockY(), location.getBlockZ())
            return if (params == null) null else Sign(params)
        }

        // -----------------------------------------------------------------------------------------
        // private

        private fun currentDate(): Date = Date(java.util.Date().getTime())
    }

    /**
     * save to database
     */
    public fun save() {
        SignRepository.save(params)
    }

    /**
     * delete from database
     */
    public fun destroy() {
        SignRepository.delete(params.id)
        GoodRepository.deleteBySignId(params.id)
    }

    /**
     * make social build sign
     */
    public fun replaceSignText(e: SignChangeEvent) {
        for ( i in 0..3) {
            e.setLine(i, signLines(i, e))
        }
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun signLines(index: Int, e: SignChangeEvent): String {
        return listOf(
                ChatColor.BLUE.toString() + "SocialBuild",    // title
                ChatColor.GREEN.toString() + e.getLine(1),    // sign name
                e.getPlayer().getName(),                      // player name
                ChatColor.DARK_AQUA.toString() + "good! : 0"  // good count
        ).get(index)
    }
}