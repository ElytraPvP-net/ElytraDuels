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

public class PartyDuelGUI extends CustomGUI {

    public PartyDuelGUI(ElytraDuels plugin) {
        super(18, "Party Duel");

        int i = 0;
        for(Kit kit : plugin.getKitManager().getKits()) {
            int playing = kit.getPlaying();
            int queue = plugin.getQueueManager().getQueueing(kit, GameType.UNRANKED);

            int count = playing + queue;
            if(count == 0) count = 1;

            ItemStack item = new ItemBuilder(kit.getIconMaterial(), count)
                    .setDisplayName("&a" + kit.getName())
                    .build();

            setItem(i, item, (p, a) -> {
                p.closeInventory();

                Game game = plugin.getGameManager().createGame(kit, GameType.UNRANKED);

                Party party = plugin.getPartyManager().getParty(p);
                List<Player> players = new ArrayList<>(party.getPlayers());
                Collections.shuffle(players);

                List<Player> team1 = new ArrayList<>();
                List<Player> team2 = new ArrayList<>();

                int team = 1;
                for(Player player : players) {

                    if(team == 1) {
                        team1.add(player);
                        team = 2;
                    }
                    else {
                        team2.add(player);
                        team = 1;
                    }
                }

                game.addPlayers(team1);
                game.addPlayers(team2);
                game.start();
            });
            i++;
        }
    }

}