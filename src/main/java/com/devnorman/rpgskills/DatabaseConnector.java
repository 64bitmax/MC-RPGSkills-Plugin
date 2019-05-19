package com.devnorman.rpgskills;

import java.sql.PreparedStatement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {
    private Connection connection;
    private String host, database, username, password;
    private int port;

    public DatabaseConnector() {
        FileConfiguration configuration = RPGSkills.getPlugin(RPGSkills.class).getConfig();
        configuration.options().copyDefaults(true);
        RPGSkills.getPlugin(RPGSkills.class).saveDefaultConfig();

        host = configuration.getString("host");
        database = configuration.getString("database");
        username =  configuration.getString("username");
        password =  configuration.getString("password");
        port = Integer.valueOf(configuration.getString("port"));

        synchronized (this) {
            try {
                if(getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
                Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills: " + ChatColor.GREEN + "MYSQL Connected");

                PreparedStatement creationStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS rpgskills_player_data (" +
                        "ID int(5) UNSIGNED NOT NULL AUTO_INCREMENT," +
                        "player_name varchar(30) NOT NULL," +
                        "mining_exp int(9) NOT NULL," +
                        "woodcutting_exp int(9) NOT NULL," +
                        "endurance_exp int(9) NOT NULL," +
                        "farming_exp int(9) NOT NULL," +
                        "PRIMARY KEY (id));");

                creationStatement.execute();
                creationStatement.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
