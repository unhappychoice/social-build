package jp.dip.socialbuild

import org.bukkit.plugin.java.JavaPlugin

import java.util.logging.Level

/**
 * Created by yueki on 2015/05/23.
 */

public class SocialBuild : JavaPlugin() {

    override fun onEnable() {
        getLogger().log(Level.INFO, "SocialBuild Enabled !!")
        Database.connect("jdbc:sqlite", getDataFolder().toString(), "user", "pass")
        Database().initializeTables()
    }

    override fun onDisable() {
        getLogger().log(Level.INFO, "SocialBuild Disabled !!")
    }
}
