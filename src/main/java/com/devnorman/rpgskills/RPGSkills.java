package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPGSkills extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "RPGSkills is now running.");
        getCommand("rpgskills").setExecutor(new InfoCommand());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "RPGSkills is now shut down.");
    }
}
