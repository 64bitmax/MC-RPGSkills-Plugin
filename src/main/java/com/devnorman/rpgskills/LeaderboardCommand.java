package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaderboardCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

        if(strings.length == 0) {
            return false;
        }
        Statement statement;
        try {
            statement = RPGSkills.dbConnector.getConnection().createStatement();
            ResultSet data;

            if(strings[0].toLowerCase().equals("mining")) {
                data = statement.executeQuery("SELECT player_name, mining_level FROM rpgskills_player_data ORDER BY mining_level DESC");

                player.sendMessage("=========[ " + ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills" + ChatColor.GOLD + "Mining" + ChatColor.YELLOW + "Leaderboard " + ChatColor.WHITE + "]=========");
                int count = 1;

                while(data.next() && count <= 10) {
                    String playerName = data.getString("player_name");
                    int miningLevel = data.getInt("mining_level");

                    switch(count) {
                        case 1:
                            player.sendMessage(ChatColor.GREEN + "1. " + playerName + " - "  + miningLevel);
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GOLD + "2. " + playerName + " - "  + miningLevel);
                            break;
                        case 3:
                            player.sendMessage(ChatColor.RED + "3. " + playerName + " - "  + miningLevel);
                            break;
                        default:
                            player.sendMessage(ChatColor.YELLOW + "" + count + ". " + playerName + " - "  + miningLevel);
                            break;
                    }
                    count++;
                }
                return true;
            } else if(strings[0].toLowerCase().equals("woodcutting") || strings[0].toLowerCase().equals("wc")) {
                data = statement.executeQuery("SELECT player_name, woodcutting_level FROM rpgskills_player_data ORDER BY woodcutting_level DESC");
                player.sendMessage("=========[ " + ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills" + ChatColor.GOLD + "Woodcutting" + ChatColor.YELLOW + "Leaderboard ]=========");

                int count = 1;

                while(data.next() && count <= 10) {
                    String playerName = data.getString("player_name");
                    int wcLevel = data.getInt("woodcutting_level");

                    switch(count) {
                        case 1:
                            player.sendMessage(ChatColor.GREEN + "1. " + playerName + " - "  + wcLevel);
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GOLD + "2. " + playerName + " - "  + wcLevel);
                            break;
                        case 3:
                            player.sendMessage(ChatColor.RED + "3. " + playerName + " - "  + wcLevel);
                            break;
                        default:
                            player.sendMessage(ChatColor.YELLOW + "" + count + ". " + playerName + " - "  + wcLevel);
                            break;
                    }
                    count++;
                }
                return true;
            } else if(strings[0].toLowerCase().equals("farming") || strings[0].toLowerCase().equals("farm")) {
                data = statement.executeQuery("SELECT player_name, farming_level FROM rpgskills_player_data ORDER BY farming_level DESC");
                player.sendMessage("=========[ " + ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills" + ChatColor.GOLD + "Farming" + ChatColor.YELLOW + "Leaderboard ]=========");
                int count = 1;

                while(data.next() && count <= 10) {
                    String playerName = data.getString("player_name");
                    int farmingLevel = data.getInt("farming_level");

                    switch(count) {
                        case 1:
                            player.sendMessage(ChatColor.GREEN + "1. " + playerName + " - "  + farmingLevel);
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GOLD + "2. " + playerName + " - "  + farmingLevel);
                            break;
                        case 3:
                            player.sendMessage(ChatColor.RED + "3. " + playerName + " - "  + farmingLevel);
                            break;
                        default:
                            player.sendMessage(ChatColor.YELLOW + "" + count + ". " + playerName + " - "  + farmingLevel);
                            break;
                    }
                    count++;
                }
                return true;
            } else if(strings[0].toLowerCase().equals("combat") || strings[0].toLowerCase().equals("cbt")) {
                data = statement.executeQuery("SELECT player_name, combat_level FROM rpgskills_player_data ORDER BY combat_level DESC");
                player.sendMessage("=========[ " + ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills" + ChatColor.GOLD + "Combat" + ChatColor.YELLOW + "Leaderboard ]=========");

                int count = 1;

                while(data.next() && count <= 10) {
                    String playerName = data.getString("player_name");
                    int combatLevel = data.getInt("combat_level");

                    switch(count) {
                        case 1:
                            player.sendMessage(ChatColor.GREEN + "1. " + playerName + " - "  + combatLevel);
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GOLD + "2. " + playerName + " - "  + combatLevel);
                            break;
                        case 3:
                            player.sendMessage(ChatColor.RED + "3. " + playerName + " - "  + combatLevel);
                            break;
                        default:
                            player.sendMessage(ChatColor.YELLOW + "" + count + ". " + playerName + " - "  + combatLevel);
                            break;
                    }
                    count++;
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }
}
