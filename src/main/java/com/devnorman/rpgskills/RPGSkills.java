package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public final class RPGSkills extends JavaPlugin {
    static DatabaseConnector dbConnector;

    @Override
    public void onEnable() {
        // Establishes connection the SQL database
        dbConnector = new DatabaseConnector();

        // Console confirmation message
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills " + ChatColor.GREEN + "is running");

        // Events
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new EntityKillListener(), this);

        // Commands
        getCommand("rpgskills").setExecutor(new InfoCommand());
        getCommand("rpglvl").setExecutor(new LevelCommand());
        getCommand("rpgleaderboard").setExecutor(new LeaderboardCommand());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills: " + ChatColor.DARK_RED + "Shutting down");
        try {
            dbConnector.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
