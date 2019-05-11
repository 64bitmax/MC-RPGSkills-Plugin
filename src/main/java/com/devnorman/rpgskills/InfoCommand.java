package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.WHITE + "------------------------------");
            player.sendMessage("--------- " + ChatColor.AQUA + "RPGSkills Info" + ChatColor.WHITE + "---------");
            player.sendMessage(ChatColor.WHITE + "------------------------------");
        }

        return true;
    }
}