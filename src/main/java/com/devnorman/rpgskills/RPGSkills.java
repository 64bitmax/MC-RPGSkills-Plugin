package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;


public final class RPGSkills extends JavaPlugin {
    DatabaseConnector dbConnector;
    FileConfiguration configuration;
    static Connection con;

    @Override
    public void onEnable() {
        configuration = getConfig();
        configuration.options().copyDefaults(true);
        saveDefaultConfig();

        dbConnector = new DatabaseConnector(configuration);
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills " + ChatColor.GREEN + "is running");

        getServer().getPluginManager().registerEvents(new MiningEvent(), this);
        getCommand("rpgskills").setExecutor(new InfoCommand());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "RPGSkills has shut down");
    }
}
