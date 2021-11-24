package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.Map;

public class LeaderboardGUI extends CustomGUI {
    private final ElytraDuels plugin;

    public LeaderboardGUI(ElytraDuels plugin) {
        super(27, "Leaderboards");
        this.plugin = plugin;

        ItemBuilder elo = new ItemBuilder(Material.EMERALD)
                .setDisplayName("&a&lELO Leaderboards");
        setItem(10, elo.build(), (p,a) -> {
            new LeaderboardGUI(plugin, "elo").open(p);
        });

        ItemBuilder wins = new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName("&a&lWins Leaderboards");
        setItem(13, wins.build(), (p,a) -> {
            new LeaderboardGUI(plugin, "wins").open(p);
        });

        ItemBuilder winstreak = new ItemBuilder(Material.PAPER)
                .setDisplayName("&a&lWin Streak Leaderboards");
        setItem(16, winstreak.build(), (p,a) -> {
            new LeaderboardGUI(plugin, "bestWinStreak").open(p);
        });
    }

    public LeaderboardGUI(ElytraDuels plugin, String type) {
        super(27, "Leaderboards");
        this.plugin = plugin;

        ItemBuilder global = new ItemBuilder(Material.NETHER_STAR)
                .setDisplayName("&aGlobal");
        addLore(global, "global", type);
        setItem(4, global.build());

        int i = 9;
        for(Kit kit : plugin.getKitManager().getRankedKits()) {

            ItemBuilder item = new ItemBuilder(kit.getIconMaterial(), 1)
                    .setDisplayName("&a" + kit.getName())
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES);
            addLore(item, kit.getName().toLowerCase(), type);

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