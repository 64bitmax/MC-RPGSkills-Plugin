package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MiningEvent implements Listener {

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Material materialInteraction = player.getInventory().getItemInMainHand().getType();

        int miningExperience = 0;
        boolean mined = true;

        if(block.getType().equals(Material.DIAMOND_ORE) && (materialInteraction.equals(Material.IRON_PICKAXE) || materialInteraction.equals(Material.DIAMOND_PICKAXE))) {
            miningExperience = 100;
        } else if(block.getType().equals(Material.GOLD_ORE) && (materialInteraction.equals(Material.IRON_PICKAXE) || materialInteraction.equals(Material.DIAMOND_PICKAXE))) {
            miningExperience = 70;
        } else if(block.getType().equals(Material.IRON_ORE) && (materialInteraction.equals(Material.STONE_PICKAXE) || materialInteraction.equals(Material.IRON_PICKAXE) ||
                materialInteraction.equals(Material.DIAMOND_PICKAXE))) {
            miningExperience = 50;
        } else if(block.getType().equals(Material.COAL_ORE) && (materialInteraction.equals(Material.WOODEN_PICKAXE) ||
                materialInteraction.equals(Material.GOLDEN_PICKAXE) || materialInteraction.equals(Material.STONE_PICKAXE) ||
                materialInteraction.equals(Material.IRON_PICKAXE) || materialInteraction.equals(Material.DIAMOND_PICKAXE))) {
            miningExperience = 20;
        } else if(block.getType().equals(Material.REDSTONE_ORE) && ( materialInteraction.equals(Material.IRON_PICKAXE) ||
                materialInteraction.equals(Material.DIAMOND_PICKAXE))) {
            miningExperience = 60;
        } else if(block.getType().equals(Material.LAPIS_ORE) && (materialInteraction.equals(Material.STONE_PICKAXE) || materialInteraction.equals(Material.IRON_PICKAXE) ||
                materialInteraction.equals(Material.DIAMOND_PICKAXE))) {
            miningExperience = 70;
        } else if(block.getType().equals(Material.EMERALD_ORE) && (materialInteraction.equals(Material.IRON_PICKAXE) ||
                materialInteraction.equals(Material.DIAMOND_PICKAXE))) {
            miningExperience = 100;
        } else if(block.getType().equals(Material.NETHER_QUARTZ_ORE) && (materialInteraction.equals(Material.WOODEN_PICKAXE) ||
                materialInteraction.equals(Material.GOLDEN_PICKAXE) || materialInteraction.equals(Material.STONE_PICKAXE) ||
                materialInteraction.equals(Material.IRON_PICKAXE) || materialInteraction.equals(Material.DIAMOND_PICKAXE))) {
            miningExperience = 10;
        } else {
            mined = false;
        }

        if(mined) {
            player.sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills: " + ChatColor.YELLOW + "You have gained " + ChatColor.GREEN + miningExperience + " mining experience.");
            try {
                Statement statement = RPGSkills.dbConnector.getConnection().createStatement();
                statement.execute("INSERT INTO rpgskills_player_data(player_name, mining_exp) VALUES('" +
                        player.getName() + "'," + miningExperience + ") ON DUPLICATE KEY UPDATE " +
                        "mining_exp=mining_exp+" + miningExperience);

                ResultSet playerData = statement.executeQuery("SELECT mining_exp, mining_level FROM rpgskills_player_data WHERE player_name='" + player.getName() + "'");

                playerData.next();

                int currentMiningExp = playerData.getInt("mining_exp");
                int currentMiningLevel = playerData.getInt("mining_level");

                // Leveling Formula (Exp req. for level up): 100 * (currentLevel + 1)^1.5


                // TODO: Percentage is wrong and does it based on previous level exp cap

                double levelUpExp = 100 * Math.pow((currentMiningLevel + 1), 1.5);

                if(currentMiningExp >= levelUpExp) {
                    currentMiningLevel++;
                    player.sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills: " + ChatColor.YELLOW
                            + "Your mining level has " + ChatColor.RED + "LEVELED UP" + ChatColor.YELLOW + " to "
                            + ChatColor.RED + currentMiningLevel + ChatColor.YELLOW + ".");
                    currentMiningExp = (int)(currentMiningExp - levelUpExp);
                }

                int percentageOfCurrentLevel = (int)((currentMiningExp / levelUpExp) * 100);

                statement.execute("UPDATE rpgskills_player_data SET mining_exp=" + currentMiningExp + ", " +
                        "mining_level=" + currentMiningLevel + ", mining_percentage=" + percentageOfCurrentLevel + " " +
                        "WHERE player_name='" + player.getName() + "'");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }



    }
}
