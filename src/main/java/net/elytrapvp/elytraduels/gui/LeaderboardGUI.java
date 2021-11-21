package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.Map;

public class LeaderboardGUI extends CustomGUI {
    private final ElytraDuels plugin;

    public LeaderboardGUI(ElytraDuels plugin) {
        super(27, "ELO Leaderboard");
        this.plugin = plugin;

        ItemBuilder global = new ItemBuilder(Material.NETHER_STAR)
                .setDisplayName("&aGlobal");
        addLore(global, "global", "elo");
        setItem(4, global.build());

        int i = 9;
        for(Kit kit : plugin.getKitManager().getRankedKits()) {

            ItemBuilder item = new ItemBuilder(kit.getIconMaterial(), 1)
                    .setDisplayName("&a" + kit.getName())
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES);
            addLore(item, kit.getName().toLowerCase(), "elo");

            setItem(i, item.build());
            i++;
        }
    }

    private void addLore(ItemBuilder builder, String kit, String type) {
        Map<String, Integer> leaderboard = plugin.getLeaderboardManager().getLeaderboard(kit, type);

        int i = 1;
        for(String player : leaderboard.keySet()) {
            builder.addLore("&a" + i + ". &f" + player + " &a- &f" + leaderboard.get(player));
            i++;
        }
    }
}