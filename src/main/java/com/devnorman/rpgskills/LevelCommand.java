package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LevelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            Statement statement;
            try {
                statement = RPGSkills.dbConnector.getConnection().createStatement();

                ResultSet result = statement.executeQuery("SELECT * FROM rpgskills_player_data WHERE player_name='" + player.getName() + "'");

                if(!result.next()) {
                    statement.execute("INSERT INTO rpgskills_player_data(player_name) VALUES('" + player.getName() + "')");
                }

                ResultSet playerData = statement.executeQuery("SELECT mining_level, mining_exp, mining_percentage," +
                        "woodcutting_level, woodcutting_exp, woodcutting_percentage," +
                        "farming_level, farming_exp, farming_percentage," +
                        "combat_level, combat_exp, combat_percentage FROM rpgskills_player_data WHERE player_name='" + player.getName() + "'");

                playerData.next();

                int miningLevelUpExp = (int) (100 * Math.pow((playerData.getInt("mining_level") + 1), 1.8));
                int wcLevelUpExp = (int) (80 * Math.pow((playerData.getInt("woodcutting_level") + 1), 1.5));
                int farmingLevelUpExp = (int)(150 * Math.pow((playerData.getInt("farming_level") + 1), 1.7));
                int combatLevelUpExp = (int) (200 * Math.pow((playerData.getInt("combat_level") + 1), 1.9));


                player.sendMessage("=========[ " + ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills" + ChatColor.YELLOW + "Levels" + ChatColor.WHITE + " ]=========");
                player.sendMessage(ChatColor.WHITE + "<" + ChatColor.YELLOW + "Mining" + ChatColor.WHITE + ">");
                player.sendMessage(ChatColor.WHITE + "Mining Level: " + ChatColor.YELLOW + playerData.getInt("mining_level"));
                player.sendMessage(ChatColor.WHITE + "Mining Exp: " + ChatColor.YELLOW + playerData.getInt("mining_exp") + "/" + miningLevelUpExp);
                player.sendMessage(" ");
                player.sendMessage(ChatColor.WHITE + "<" + ChatColor.YELLOW + "Woodcutting" + ChatColor.WHITE + ">");
                player.sendMessage(ChatColor.WHITE + "Woodcutting Level: " + ChatColor.YELLOW + playerData.getInt("woodcutting_level"));
                player.sendMessage(ChatColor.WHITE + "Woodcutting Exp: " + ChatColor.YELLOW + playerData.getInt("woodcutting_exp") + "/" + wcLevelUpExp);
                player.sendMessage(" ");
                player.sendMessage(ChatColor.WHITE + "<" + ChatColor.YELLOW + "Farming" + ChatColor.WHITE + ">");
                player.sendMessage(ChatColor.WHITE + "Farming Level: " + ChatColor.YELLOW + playerData.getInt("farming_level"));
                player.sendMessage(ChatColor.WHITE + "Farming Exp: " + ChatColor.YELLOW + playerData.getInt("farming_exp") + "/" + farmingLevelUpExp);
                player.sendMessage(" ");
                player.sendMessage(ChatColor.WHITE + "<" + ChatColor.YELLOW + "Combat" + ChatColor.WHITE + ">");
                player.sendMessage(ChatColor.WHITE + "Combat Level: " + ChatColor.YELLOW + playerData.getInt("combat_level"));
                player.sendMessage(ChatColor.WHITE + "Combat Exp: " + ChatColor.YELLOW + playerData.getInt("combat_exp") + "/" + combatLevelUpExp);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return true;
    }
}
