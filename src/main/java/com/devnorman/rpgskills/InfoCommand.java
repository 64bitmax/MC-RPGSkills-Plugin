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
            player.sendMessage("=========[ " + ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills" + ChatColor.WHITE + "Info ]=========");

            player.sendMessage("/rpgskills");
            player.sendMessage("/rpglvl");
        }

        return true;
    }
}