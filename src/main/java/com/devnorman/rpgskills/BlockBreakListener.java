package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlockBreakListener implements Listener {
    Player player;
    Block block;
    Material materialInteraction;

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) throws IOException, SQLException {
        player = e.getPlayer();
        block = e.getBlock();
        materialInteraction = player.getInventory().getItemInMainHand().getType();

        configureMining();
        configureWoodcutting();
        configureFarming();
    }

    private void configureWoodcutting() throws IOException, SQLException {
        int woodcuttingExperience = 0;

        if(block.getType().equals(Material.ACACIA_LOG) || block.getType().equals(Material.BIRCH_LOG) ||
                block.getType().equals(Material.DARK_OAK_LOG) || block.getType().equals(Material.JUNGLE_LOG) ||
                block.getType().equals(Material.OAK_LOG) || block.getType().equals(Material.SPRUCE_LOG)) {
            woodcuttingExperience = 10;
        }

        Statement statement;
        statement = RPGSkills.dbConnector.getConnection().createStatement();

        statement.execute("INSERT INTO rpgskills_player_data(player_name, woodcutting_exp) VALUES('" +
                player.getName() + "'," + woodcuttingExperience + ") ON DUPLICATE KEY UPDATE " +
                "woodcutting_exp=woodcutting_exp+" + woodcuttingExperience);

        ResultSet playerData = statement.executeQuery("SELECT woodcutting_exp, woodcutting_level FROM rpgskills_player_data WHERE player_name='" + player.getName() + "'");

        playerData.next();

        int currentWoodcuttingExp = playerData.getInt("woodcutting_exp");
        int currentWoodcuttingLevel = playerData.getInt("woodcutting_level");

        double levelUpExp = 80 * Math.pow((currentWoodcuttingLevel + 1), 1.5);

        if(currentWoodcuttingExp >= levelUpExp) {
            currentWoodcuttingLevel++;
            player.sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills: " + ChatColor.YELLOW
                    + "Your woodcutting level has " + ChatColor.GREEN + "LEVELED UP" + ChatColor.YELLOW + " to "
                    + ChatColor.GREEN + currentWoodcuttingLevel + ChatColor.YELLOW + ".");
            currentWoodcuttingExp = (int)(currentWoodcuttingExp - levelUpExp);
        }

        int percentageOfCurrentLevel = (int)((currentWoodcuttingExp / levelUpExp) * 100);

        statement.execute("UPDATE rpgskills_player_data SET woodcutting_exp=" + currentWoodcuttingExp + ", " +
                "woodcutting_level=" + currentWoodcuttingLevel + ", woodcutting_percentage=" + percentageOfCurrentLevel + " " +
                "WHERE player_name='" + player.getName() + "'");

    }

    private void configureFarming() throws IOException, SQLException {
        int farmingExperience = 0;

        byte blockData = block.getData();
        boolean isFullyGrown = blockData == 0x7;

        if(block.getType().equals(Material.WHEAT) && isFullyGrown) {
            farmingExperience = 10;
        } else if(block.getType().equals(Material.BEETROOTS) || block.getType().equals(Material.CARROTS) || block.getType().equals(Material.POTATOES) && isFullyGrown) {
            farmingExperience = 20;
        } else if(block.getType().equals(Material.MELON) || block.getType().equals(Material.LEGACY_PUMPKIN) ||
                block.getType().equals(Material.PUMPKIN) && isFullyGrown) {
            farmingExperience = 75;
        } else if(block.getType().equals(Material.COCOA) && isFullyGrown) {
            farmingExperience = 100;
        } else if(block.getType().equals(Material.SUGAR_CANE) || block.getType().equals(Material.RED_MUSHROOM) ||
                block.getType().equals(Material.BROWN_MUSHROOM) && isFullyGrown) {
            farmingExperience = 1;
        }

        Statement statement;
        try {
            statement = RPGSkills.dbConnector.getConnection().createStatement();

            statement.execute("INSERT INTO rpgskills_player_data(player_name, farming_exp) VALUES('" +
                    player.getName() + "'," + farmingExperience + ") ON DUPLICATE KEY UPDATE " +
                    "farming_exp=farming_exp+" + farmingExperience);

            ResultSet playerData = statement.executeQuery("SELECT farming_exp, farming_level FROM rpgskills_player_data WHERE player_name='" + player.getName() + "'");

            playerData.next();

            int currentFarmingExp = playerData.getInt("farming_exp");
            int currentFarmingLevel = playerData.getInt("farming_level");

            double levelUpExp = 150 * Math.pow((currentFarmingLevel + 1), 1.7);

            if(currentFarmingExp >= levelUpExp) {
                currentFarmingLevel++;
                player.sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills: " + ChatColor.YELLOW
                        + "Your farming level has " + ChatColor.GREEN + "LEVELED UP" + ChatColor.YELLOW + " to "
                        + ChatColor.GREEN + currentFarmingLevel + ChatColor.YELLOW + ".");
                currentFarmingExp = (int)(currentFarmingExp - levelUpExp);
            }

            int percentageOfCurrentLevel = (int)((currentFarmingExp / levelUpExp) * 100);

            statement.execute("UPDATE rpgskills_player_data SET farming_exp=" + currentFarmingExp + ", " +
                    "farming_level=" + currentFarmingLevel + ", farming_percentage=" + percentageOfCurrentLevel + " " +
                    "WHERE player_name='" + player.getName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configureMining() throws IOException, SQLException {
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
        } else if(block.getType().equals(Material.STONE) || block.getType().equals(Material.ANDESITE) ||
                block.getType().equals(Material.DIORITE) || block.getType().equals(Material.GRANITE) ||
                block.getType().equals(Material.COBBLESTONE) || block.getType().equals(Material.MOSSY_COBBLESTONE) ||
                block.getType().equals(Material.SANDSTONE) || block.getType().equals(Material.RED_SANDSTONE) ||
                block.getType().equals(Material.COBBLESTONE) || block.getType().equals(Material.MOSSY_COBBLESTONE) ||
                block.getType().equals(Material.QUARTZ_BLOCK) || block.getType().equals(Material.PRISMARINE) ||
                block.getType().equals(Material.DARK_PRISMARINE) || block.getType().equals(Material.BRICK) ||
                block.getType().equals(Material.STONE_BRICKS) || block.getType().equals(Material.MOSSY_STONE_BRICKS) ||
                block.getType().equals(Material.CHISELED_STONE_BRICKS) || block.getType().equals(Material.NETHER_BRICK) ||
                block.getType().equals(Material.COBBLESTONE) || block.getType().equals(Material.MOSSY_COBBLESTONE)){
            miningExperience = 1;
            // TODO: Batch processing this and make this a command to see experience rates
        }
        else {
            mined = false;
        }

        if(mined) {
            try {
                Statement statement = RPGSkills.dbConnector.getConnection().createStatement();
                statement.execute("INSERT INTO rpgskills_player_data(player_name, mining_exp) VALUES('" +
                        player.getName() + "'," + miningExperience + ") ON DUPLICATE KEY UPDATE " +
                        "mining_exp=mining_exp+" + miningExperience);

                ResultSet playerData = statement.executeQuery("SELECT mining_exp, mining_level FROM rpgskills_player_data WHERE player_name='" + player.getName() + "'");

                playerData.next();

                int currentMiningExp = playerData.getInt("mining_exp");
                int currentMiningLevel = playerData.getInt("mining_level");

                // Leveling Formula (Exp req. for level up): 100 * (currentLevel + 1)^1.8

                double levelUpExp = 100 * Math.pow((currentMiningLevel + 1), 1.8);

                if(currentMiningExp >= levelUpExp) {
                    currentMiningLevel++;
                    player.sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills: " + ChatColor.YELLOW
                            + "Your mining level has " + ChatColor.GREEN + "LEVELED UP" + ChatColor.YELLOW + " to "
                            + ChatColor.GREEN + currentMiningLevel + ChatColor.YELLOW + ".");
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
