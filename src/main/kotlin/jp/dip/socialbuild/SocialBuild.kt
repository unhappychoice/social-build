package jp.dip.socialbuild

import org.bukkit.plugin.java.JavaPlugin

import java.util.logging.Level
import jp.dip.socialbuild.repository.PersonRepository
import jp.dip.socialbuild.repository.SignRepository
import jp.dip.socialbuild.repository.GoodRepository
import jp.dip.socialbuild.controller.SignEventsController
import jp.dip.socialbuild.command.CommandDispatcher


/**
 * Created by yueki on 2015/05/23.
 */

public class SocialBuild : JavaPlugin() {

    override fun onEnable() {
        getLogger().log(Level.INFO, "SocialBuild Enabled !!")
        saveDefaultConfig()
        setupDatabase()
        getServer().getPluginManager().registerEvents(SignEventsController(), this)
        getCommand("sb").setExecutor(CommandDispatcher())
    }

    override fun onDisable() {
        getLogger().log(Level.INFO, "SocialBuild Disabled !!")
    }

    private fun setupDatabase() {
        val config = DatabaseConfig(databaseConfig(), getDataFolder().toString())
        Database.connect(config)

        PersonRepository.setupTable(config)
        SignRepository.setupTable(config)
        GoodRepository.setupTable(config)
    }

    private fun databaseConfig(): Map<String, Any> {
        return getConfig().getConfigurationSection("database").getValues(false)
    }
}
