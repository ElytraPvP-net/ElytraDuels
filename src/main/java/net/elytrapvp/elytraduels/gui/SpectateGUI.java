package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.entity.Player;

public class SpectateGUI extends CustomGUI {

    public SpectateGUI(ElytraDuels plugin) {
        super(54, "Current Matches");

        for(int i = 0; i < plugin.getGameManager().getActiveGames().size(); i++) {
            Game game = plugin.getGameManager().getActiveGames().get(i);


            ItemBuilder item = new ItemBuilder(game.getKit().getIconMaterial())
                    .setDisplayName("&a" + game.getKit().getName() + " &f(" + game.getArena().getMap().getName() + ")");

            for(Player p : game.getAlivePlayers()) {
                item.addLore("&7  - " + p.getName());
            }

            setItem(i, item.build(), (p, a) -> {
                if(game.getGameState() == GameState.WAITING || game.getGameState() == GameState.END) {
                    p.closeInventory();
                    ChatUtils.chat(p, "&c&l(&7!&c&l) &cThat match has ended.");
                    return;
                }

                game.addSpectator(p);
                game.broadcast("&a" + p.getName() + " is now spectating.");
            });
        }
    }
}