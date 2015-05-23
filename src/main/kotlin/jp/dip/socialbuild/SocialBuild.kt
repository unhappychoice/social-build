package jp.dip.socialbuild

import org.bukkit.plugin.java.JavaPlugin

import java.util.logging.Level
import jp.dip.socialbuild.repository.PersonRepository
import jp.dip.socialbuild.repository.PersonRepository.PersonParams
import java.sql.Time
import java.sql.Date


/**
 * Created by yueki on 2015/05/23.
 */

public class SocialBuild : JavaPlugin() {

    override fun onEnable() {
        getLogger().log(Level.INFO, "SocialBuild Enabled !!")

    }

    override fun onDisable() {
        getLogger().log(Level.INFO, "SocialBuild Disabled !!")
    }

    private fun setupDatabase() {
        Database.connect("jdbc:sqlite", getDataFolder().toString(), "user", "pass")

        PersonRepository.setupTable()
        Database().initializeTables()
    }
}
