package jp.dip.socialbuild.model

import jp.dip.socialbuild.repository.SignRepository.SignParams
import org.bukkit.entity.Player
import jp.dip.socialbuild.repository.SignRepository
import org.bukkit.ChatColor
import org.bukkit.Location
import java.sql.Date

/**
 * Created by unhappychoice on 2015/05/24.
 */

public class Sign {
    class object {
        public fun save(player: Player, location: Location, lines: List<String>) {
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
            SignRepository.save(params)
        }

        // ---------------------------------------------------------------------------------------------
        // private

        private fun currentDate(): Date = Date(java.util.Date().getTime())
    }
}