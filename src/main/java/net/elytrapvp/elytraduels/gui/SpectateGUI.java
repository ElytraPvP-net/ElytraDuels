package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class SpectateGUI extends CustomGUI {

    public SpectateGUI(ElytraDuels plugin) {
        super(54, "Current Matches");

        for(int i = 0; i < plugin.gameManager().activeGames().size(); i++) {
            Game game = plugin.gameManager().activeGames().get(i);


            ItemBuilder item = new ItemBuilder(game.getKit().getIconMaterial())
                    .setDisplayName("&a" + game.getKit().getName() + " &f(" + game.getArena().map().name() + ")")
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES);

            for(Player p : game.getAlivePlayers()) {
                item.addLore("&7  - " + p.getName());
            }

            setItem(i, item.build(), (p, a) -> {
                p.closeInventory();
                if(game.getGameState() == GameState.WAITING || game.getGameState() == GameState.END) {
                    ChatUtils.chat(p, "&cError &8» &cThat match has ended.");
                    return;
                }

                game.addSpectator(p);
                game.broadcast("&a" + p.getName() + " is now spectating.");
            });
        }
    }
}