package net.elytrapvp.elytraduels.utils;

import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.party.PartyManager;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import net.elytrapvp.elytraduels.utils.item.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    public static void giveLobbyItems(Player player) {
        player.getInventory().clear();

        player.setHealth(20);
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

        ItemStack otherModes = new ItemBuilder(Material.BED)
                .setDisplayName("&cBack to Lobby")
                .build();
        player.getInventory().setItem(8, otherModes);
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

            ItemStack spectate = new ItemBuilder(Material.NETHER_STAR)
                    .setDisplayName("&aSpectate")
                    .build();
            player.getInventory().setItem(2, spectate);
        }

        ItemStack leave = new ItemBuilder(Material.REDSTONE)
                .setDisplayName("&cLeave Party")
                .build();
        player.getInventory().setItem(8, leave);
    }
}