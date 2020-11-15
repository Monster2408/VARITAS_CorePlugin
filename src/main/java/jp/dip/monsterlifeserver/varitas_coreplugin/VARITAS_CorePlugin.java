package jp.dip.monsterlifeserver.varitas_coreplugin;

import jp.dip.monsterlifeserver.varitas_coreplugin.API.ConfigAPI;
import jp.dip.monsterlifeserver.varitas_coreplugin.Listener.onInventoryClick;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static jp.dip.monsterlifeserver.varitas_coreplugin.API.MainAPI.debugMode;
import static jp.dip.monsterlifeserver.varitas_coreplugin.API.MainAPI.loadPlugin;

public final class VARITAS_CorePlugin extends JavaPlugin {

    public static ConfigAPI config;

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        loadPlugin(this);

        getCommand("gexp").setExecutor(new MainCommand());
        if (debugMode) getCommand("test").setExecutor(new MainCommand());

        getServer().getPluginManager().registerEvents(new onInventoryClick(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
