package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.SQLException;
import java.sql.Statement;

public class MiningEvent implements Listener {

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Material materialInteraction = player.getInventory().getItemInMainHand().getType();

        // Checks that the player has a pickaxe equipped and is mining (or hitting) a block.
        if(materialInteraction.equals(Material.WOODEN_PICKAXE) || materialInteraction.equals(Material.STONE_PICKAXE) ||
                materialInteraction.equals(Material.IRON_PICKAXE) || materialInteraction.equals(Material.GOLDEN_PICKAXE) ||
                materialInteraction.equals(Material.DIAMOND_PICKAXE)) {

            String blockName = block.getType().toString();
            player.sendMessage(ChatColor.WHITE + "You have mined some " + ChatColor.RED + blockName + ".");


            try {
                Statement statement = RPGSkills.con.createStatement();

                int miningExperience = 0;
                statement.execute("INSERT INTO statistics VALUES('" + player.getDisplayName() + "'", miningExperience);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
