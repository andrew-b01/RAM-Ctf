package me.average.ramctf;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Setup setup = new Setup();
        Teams teams = new Teams();
        getCommand("setup").setExecutor(setup);
        getCommand("team").setExecutor(teams);
        getCommand("clearmiddle").setExecutor(setup);
        getServer().getPluginManager().registerEvents(new FlagLogic(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "RamCTF is enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
