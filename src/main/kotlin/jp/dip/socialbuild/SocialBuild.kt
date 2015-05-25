package jp.dip.socialbuild

import org.bukkit.plugin.java.JavaPlugin

import java.util.logging.Level
import jp.dip.socialbuild.repository.PersonRepository
import jp.dip.socialbuild.repository.PersonRepository.PersonParams
import java.sql.Time
import java.sql.Date
import jp.dip.socialbuild.repository.SignRepository
import jp.dip.socialbuild.repository.GoodRepository
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.ChatColor
import jp.dip.socialbuild.repository.SignRepository.SignParams
import jp.dip.socialbuild.controller.SignEventsController
import jp.dip.socialbuild.command.CommandDispatcher


/**
 * Created by yueki on 2015/05/23.
 */

public class SocialBuild : JavaPlugin() {

    override fun onEnable() {
        getLogger().log(Level.INFO, "SocialBuild Enabled !!")
        setupDatabase()
        getServer().getPluginManager().registerEvents(SignEventsController(), this)
        getCommand("sb").setExecutor(CommandDispatcher())
    }

    override fun onDisable() {
        getLogger().log(Level.INFO, "SocialBuild Disabled !!")
    }

    private fun setupDatabase() {
        Database.connect("jdbc:sqlite", getDataFolder().toString(), "user", "pass")

        PersonRepository.setupTable()
        SignRepository.setupTable()
        GoodRepository.setupTable()
    }
}
