package com.devnorman.rpgskills;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntityKillListener implements Listener {

    @EventHandler
    public void damageTakeEvent(EntityDeathEvent e) {
        Player player = e.getEntity().getKiller();

        if(player != null && player.getGameMode() != GameMode.CREATIVE) {
            EntityType deadEntity = e.getEntity().getType();
            int combat_experience = 0;

            switch(deadEntity) {
                case BLAZE:
                    combat_experience = 25;
                    break;
                case CREEPER:
                    combat_experience = 12;
                    break;
                case GHAST:
                    combat_experience = 20;
                    break;
                case MAGMA_CUBE:
                    combat_experience = 14;
                    break;
                case SILVERFISH:
                    combat_experience = 5;
                    break;
                case SKELETON:
                    combat_experience = 15;
                    break;
                case SLIME:
                    combat_experience = 12;
                    break;
                case ZOMBIE:
                    combat_experience = 12;
                    break;
                case ZOMBIE_VILLAGER:
                    combat_experience = 15;
                    break;
                case DROWNED:
                    combat_experience = 15;
                    break;
                case WITHER_SKELETON:
                    combat_experience = 15;
                    break;
                case WITCH:
                    combat_experience = 50;
                    break;
                case EVOKER:
                    combat_experience = 25;
                    break;
                case VINDICATOR:
                    combat_experience = 25;
                    break;
                case PILLAGER:
                    combat_experience = 25;
                    break;
                case RAVAGER:
                    combat_experience = 100;
                    break;
                case VEX:
                    combat_experience = 18;
                    break;
                case ENDERMITE:
                    combat_experience = 3;
                    break;
                case GUARDIAN:
                    combat_experience = 20;
                    break;
                case ELDER_GUARDIAN:
                    combat_experience = 80;
                    break;
                case SHULKER:
                    combat_experience = 20;
                    break;
                case HUSK:
                    combat_experience = 15;
                    break;
                case STRAY:
                    combat_experience = 15;
                    break;
                case PHANTOM:
                    combat_experience = 20;
                    break;
                case ENDER_DRAGON:
                    combat_experience = 500;
                    break;
                case WITHER:
                    combat_experience = 500;
                    break;
                case ENDERMAN:
                    combat_experience = 30;
                    break;
                case PIG_ZOMBIE:
                    combat_experience = 15;
                    break;
                case DOLPHIN:
                    combat_experience = 5;
                    break;
                case WOLF:
                    combat_experience = 5;
                    break;
                case SPIDER:
                    combat_experience = 10;
                    break;
                case CAVE_SPIDER:
                    combat_experience = 12;
                    break;
                case POLAR_BEAR:
                    combat_experience = 10;
                    break;
                case LLAMA:
                    combat_experience = 15;
                    break;
                case IRON_GOLEM:
                    combat_experience = 100;
                    break;
                case PANDA:
                    combat_experience = 40;
                    break;
                case CHICKEN:
                    combat_experience = 1;
                    break;
                case COW:
                    combat_experience = 3;
                    break;
                case MUSHROOM_COW:
                    combat_experience = 10;
                    break;
                case PIG:
                    combat_experience = 3;
                    break;
                case SHEEP:
                    combat_experience = 2;
                    break;
                case SQUID:
                    combat_experience = 4;
                    break;
                case VILLAGER:
                    combat_experience = 15;
                    break;
                case WANDERING_TRADER:
                    combat_experience = 15;
                    break;
                case BAT:
                    combat_experience = 2;
                    break;
                case OCELOT:
                    combat_experience = 3;
                    break;
                case CAT:
                    combat_experience = 3;
                    break;
                case HORSE:
                    combat_experience = 15;
                    break;
                case DONKEY:
                    combat_experience = 15;
                    break;
                case MULE:
                    combat_experience = 15;
                    break;
                case SKELETON_HORSE:
                    combat_experience = 15;
                    break;
                case FOX:
                    combat_experience = 5;
                    break;
                case RABBIT:
                    combat_experience = 2;
                    break;
                case PARROT:
                    combat_experience = 2;
                    break;
                case TURTLE:
                    combat_experience = 20;
                    break;
                case COD:
                    combat_experience = 2;
                    break;
                case SALMON:
                    combat_experience = 2;
                    break;
                case PUFFERFISH:
                    combat_experience = 4;
                    break;
                case TROPICAL_FISH:
                    combat_experience = 2;
                    break;
            }

            Statement statement;
            try {
                statement = RPGSkills.dbConnector.getConnection().createStatement();

                statement.execute("INSERT INTO rpgskills_player_data(player_name, combat_exp) VALUES('" +
                        player.getName() + "'," + combat_experience + ") ON DUPLICATE KEY UPDATE " +
                        "combat_exp=combat_exp+" + combat_experience);

                statement.close();

                statement = RPGSkills.dbConnector.getConnection().createStatement();
                ResultSet playerData = statement.executeQuery("SELECT combat_exp, combat_level FROM rpgskills_player_data WHERE player_name='" + player.getName() + "'");

                playerData.next();

                int currentCombatExp = playerData.getInt("combat_exp");
                int currentCombatLevel = playerData.getInt("combat_level");

                double levelUpExp = 200 * Math.pow((currentCombatLevel + 1), 1.9);

                if(currentCombatExp >= levelUpExp) {
                    currentCombatLevel++;
                    player.sendMessage(ChatColor.BLUE + "RPG" + ChatColor.RED + "Skills: " + ChatColor.YELLOW
                            + "Your combat level has " + ChatColor.GREEN + "LEVELED UP" + ChatColor.YELLOW + " to "
                            + ChatColor.GREEN + currentCombatLevel + ChatColor.YELLOW + ".");
                    double prevHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                    if(prevHealth < 40) {
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(prevHealth + 1);
                    }
                    currentCombatExp = (int)(currentCombatExp - levelUpExp);
                }

                int percentageOfCurrentLevel = (int)((currentCombatExp / levelUpExp) * 100);

                statement.close();

                statement = RPGSkills.dbConnector.getConnection().createStatement();

                statement.execute("UPDATE rpgskills_player_data SET combat_exp=" + currentCombatExp + ", " +
                        "combat_level=" + currentCombatLevel + ", combat_percentage=" + percentageOfCurrentLevel + " " +
                        "WHERE player_name='" + player.getName() + "'");

                statement.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
