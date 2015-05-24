package jp.dip.socialbuild.model

import jp.dip.socialbuild.repository.SignRepository.SignParams
import org.bukkit.entity.Player
import jp.dip.socialbuild.repository.SignRepository
import org.bukkit.ChatColor
import org.bukkit.Location
import java.sql.Date
import jp.dip.socialbuild.repository.GoodRepository
import org.bukkit.event.block.SignChangeEvent
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
        public fun create(player: Player, location: Location, lines: List<String>): SocialBuildSign {
            val params = SignParams(
                    id = 0,
                    name = lines[3],
                    ownerId = player.uuid(),
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
            val params = SignRepository.where(location.getBlockX(), location.getBlockY(), location.getBlockZ())
            return if (params == null) null else SocialBuildSign(params)
        }
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

    /**
     * update sign count
     */
    public fun updateGoodCount(sign: org.bukkit.block.Sign) {
        val count = GoodRepository.count(params.id)
        sign.setLine(3, goodLine(count))
        sign.update()
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun signLines(index: Int, e: SignChangeEvent): String {
        return listOf(
                titleLine(),
                nameLine(e.getLine(1)),
                playerLine(e.getPlayer().getName()),
                goodLine(0)

        ).get(index)
    }

    private fun titleLine(): String {
        return ChatColor.BLUE.toString() + "SocialBuild"
    }

    private fun nameLine(name: String): String {
        return ChatColor.GREEN.toString() + name
    }

    private fun playerLine(playerName: String): String {
        return playerName
    }

    private fun goodLine(count: Int): String {
        return ChatColor.DARK_AQUA.toString() + "good! : ${count}"
    }
}