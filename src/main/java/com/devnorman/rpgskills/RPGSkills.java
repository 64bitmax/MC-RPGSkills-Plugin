package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;


public final class RPGSkills extends JavaPlugin {
    static Driver sqlDriver;
    static Connection con;

    @Override
    public void onEnable() {
        try {
            sqlDriver = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
            DriverManager.registerDriver(sqlDriver);
            con = DriverManager.getConnection("jdbc:sqlite:jobs.sqlite.db");
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            getLogger().info(ChatColor.RED + "Error connecting to the SQLITE database");
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "RPGSkills is running");
        getServer().getPluginManager().registerEvents(new MiningEvent(), this);
        getCommand("rpgskills").setExecutor(new InfoCommand());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "RPGSkills has shut down");
    }
}
