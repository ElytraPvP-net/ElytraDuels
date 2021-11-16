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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyFFAGUI extends CustomGUI {

    public PartyFFAGUI(ElytraDuels plugin) {
        super(18, "Party FFA");

        int i = 0;
        for(Kit kit : plugin.getKitManager().getKits()) {
            int playing = plugin.getQueueManager().getPlaying(kit);
            int queue = plugin.getQueueManager().getQueueing(kit, GameType.UNRANKED);

            int count = playing + queue;
            if(count == 0) count = 1;

            ItemStack item = new ItemBuilder(kit.getIconMaterial(), count)
                    .setDisplayName("&a" + kit.getName())
                    .build();

            setItem(i, item, (p, a) -> {
                p.closeInventory();

                Party party = plugin.getPartyManager().getParty(p);
                if(party.getPlayers().size() == 1) {
                    ChatUtils.chat(p, "&cYou cannot duel yourself!");
                    return;
                }

                Game game = plugin.getGameManager().createGame(kit, GameType.FFA);

                List<Player> players = new ArrayList<>(party.getPlayers());
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