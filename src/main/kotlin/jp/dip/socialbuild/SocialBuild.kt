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
        setupMessage()
        setupDatabase()
        registerClasses()
    }

    override fun onDisable() {
        getLogger().log(Level.INFO, "SocialBuild Disabled !!")
    }

    // ---------------------------------------------------------------------------------------------
    // private

    private fun setupMessage() {
        Message.messages = messageConfig()
    }

    private fun setupDatabase() {
        val config = DatabaseConfig(databaseConfig(), getDataFolder().toString())
        Database.connect(config)

        PersonRepository.setupTable(config)
        SignRepository.setupTable(config)
        GoodRepository.setupTable(config)
    }

    private fun registerClasses() {
        getServer().getPluginManager().registerEvents(SignEventsController(), this)
        getCommand("sb").setExecutor(CommandDispatcher())
    }

    private fun databaseConfig(): Map<String, Any> {
        return getConfig().getConfigurationSection("database").getValues(false)
    }

    private fun messageConfig(): Map<String, Any> {
        return getConfig().getConfigurationSection("message").getValues(false)
    }
}
