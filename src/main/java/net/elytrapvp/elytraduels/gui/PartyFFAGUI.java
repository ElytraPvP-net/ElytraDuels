package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyFFAGUI extends CustomGUI {

    public PartyFFAGUI(ElytraDuels plugin, Player opener) {
        super(36, "Party FFA");

        int i = 0;
        for(Kit kit : plugin.kitManager().kits()) {
            int playing = plugin.queueManager().getPlaying(kit);
            int queue = plugin.queueManager().getQueueing(kit, GameType.UNRANKED);

            int count = playing + queue;
            if(count == 0) count = 1;

            ItemStack item = new ItemBuilder(kit.getIconMaterial(), count)
                    .setDisplayName("&a" + kit.getName())
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();

            setItem(i, item, (p, a) -> {
                p.closeInventory();

                Party party = plugin.partyManager().getParty(p);
                if(party.getMembers().size() == 1) {
                    ChatUtils.chat(p, "&cError &8» &cYou cannot duel yourself!");
                    return;
                }

                Game game = plugin.gameManager().createGame(kit, GameType.FFA);

                List<Player> players = new ArrayList<>(party.getMembers());
                Collections.shuffle(players);

                for(Player player : players) {
                    game.addPlayer(player);
                }
                game.start();
            });
            i++;
        }

        if(opener.hasPermission("duels.disabled")) {
            for(Kit kit : plugin.kitManager().disabledKits()) {
                int playing = plugin.queueManager().getPlaying(kit);
                int queue = plugin.queueManager().getQueueing(kit, GameType.UNRANKED);

                int count = playing + queue;
                if(count == 0) count = 1;

                ItemStack item = new ItemBuilder(kit.getIconMaterial(), count)
                        .setDisplayName("&a" + kit.getName())
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .build();

                setItem(i, item, (p, a) -> {
                    p.closeInventory();

                    Party party = plugin.partyManager().getParty(p);
                    if(party.getMembers().size() == 1) {
                        ChatUtils.chat(p, "&cError &8» &cYou cannot duel yourself!");
                        return;
                    }

                    Game game = plugin.gameManager().createGame(kit, GameType.FFA);

                    List<Player> players = new ArrayList<>(party.getMembers());
                    Collections.shuffle(players);

                    for(Player player : players) {
                        game.addPlayer(player);
                    }
                    game.start();
                });
                i++;
            }
        }
    }

}