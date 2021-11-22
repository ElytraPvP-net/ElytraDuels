package net.elytrapvp.elytraduels.utils;

import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.party.PartyManager;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import net.elytrapvp.elytraduels.utils.item.SkullBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    public static void giveLobbyItems(Player player) {
        player.getInventory().clear();

        player.setMaxHealth(20.0);
        player.setHealth(20.0);
        player.setFoodLevel(20);

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        ItemStack spectate = new ItemBuilder(Material.NETHER_STAR)
                .setDisplayName("&aSpectate")
                .build();
        player.getInventory().setItem(0, spectate);

        ItemStack unranked = new ItemBuilder(Material.STONE_SWORD)
                .setDisplayName("&aKits")
                .build();
        player.getInventory().setItem(3, unranked);

        ItemStack team = new SkullBuilder(player)
                .setDisplayName("&aCreate a party")
                .build();
        player.getInventory().setItem(5, team);

        ItemStack otherModes = new ItemBuilder(Material.REDSTONE_COMPARATOR)
                .setDisplayName("&aSettings")
                .build();
        player.getInventory().setItem(8, otherModes);

        ItemStack leaderboard = new ItemBuilder(Material.EMERALD)
                .setDisplayName("&aLeaderboards")
                .build();
        player.getInventory().setItem(7, leaderboard);
    }

    public static void givePartyItems(PartyManager partyManager, Player player) {
        Party party = partyManager.getParty(player);

        if(party == null) {
            giveLobbyItems(player);
            return;
        }

        player.getInventory().clear();

        if(party.getLeader().equals(player)) {
            ItemStack duel = new ItemBuilder(Material.STONE_SWORD)
                    .setDisplayName("&aDuel another party")
                    .build();
            player.getInventory().setItem(0, duel);

            ItemStack partyDuel = new ItemBuilder(Material.IRON_SWORD)
                    .setDisplayName("&aDuel Party Members")
                    .build();
            player.getInventory().setItem(1, partyDuel);

            ItemStack ffa = new ItemBuilder(Material.GOLD_SWORD)
                    .setDisplayName("&aFFA Duel")
                    .build();
            player.getInventory().setItem(2, ffa);

            ItemStack spectate = new ItemBuilder(Material.NETHER_STAR)
                    .setDisplayName("&aSpectate")
                    .build();
            player.getInventory().setItem(3, spectate);
        }
        else {
            ItemStack spectate = new ItemBuilder(Material.NETHER_STAR)
                    .setDisplayName("&aSpectate Current Game")
                    .build();
            player.getInventory().setItem(0, spectate);
        }

        ItemStack leave = new ItemBuilder(Material.REDSTONE)
                .setDisplayName("&cLeave Party")
                .build();
        player.getInventory().setItem(8, leave);
    }

    public static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR+""+c;
        return hidden;
    }
}