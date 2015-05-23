package jp.dip.socialbuild;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by yueki on 2015/05/23.
 */

public class SocialBuild extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "SocialBuild Enabled !!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "SocialBuild Disabled !!");
    }
}
