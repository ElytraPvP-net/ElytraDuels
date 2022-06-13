package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import net.elytrapvp.elytraduels.utils.item.SkullBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class LeaderboardGUI extends CustomGUI {
    private final ElytraDuels plugin;

    public LeaderboardGUI(ElytraDuels plugin) {
        super(45, "Leaderboards");
        this.plugin = plugin;

        ItemBuilder wins = new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName("&a&lWins Leaderboards")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        setItem(21, wins.build(), (p,a) -> new LeaderboardGUI(plugin, "wins").open(p));

        ItemBuilder winstreak = new ItemBuilder(Material.PAPER)
                .setDisplayName("&a&lWin Streak Leaderboards");
        setItem(23, winstreak.build(), (p,a) -> new LeaderboardGUI(plugin, "bestWinStreak").open(p));

        int[] fillers = {0,1,2,3,4,5,6,7,8,36,37,38,39,40,41,42,43,44};
        for(int j : fillers) {
            ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
            ItemMeta meta = filler.getItemMeta();
            meta.setDisplayName(" ");
            filler.setItemMeta(meta);
            setItem(j, filler);
        }
    }

    public LeaderboardGUI(ElytraDuels plugin, String type) {
        super(45, "Leaderboards");
        this.plugin = plugin;

        ItemStack back = new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6")
                .setDisplayName("&cBack")
                .build();
        setItem(0, back, (p, a) -> new LeaderboardGUI(plugin).open(p));

        ItemBuilder global = new ItemBuilder(Material.NETHER_STAR)
                .setDisplayName("&a&lGlobal");
        addLore(global, "global", type);
        setItem(13, global.build());

        int i = 18;
        for(Kit kit : plugin.getKitManager().getKits()) {

            ItemBuilder item = new ItemBuilder(kit.getIconMaterial(), 1)
                    .setDisplayName("&a&l" + kit.getName())
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES);
            addLore(item, kit.getName().toLowerCase(), type);

            setItem(i, item.build());
            i++;
        }

        int[] fillers = {1,2,3,4,5,6,7,8,36,37,38,39,40,41,42,43,44};
        for(int j : fillers) {
            ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
            ItemMeta meta = filler.getItemMeta();
            meta.setDisplayName(" ");
            filler.setItemMeta(meta);
            setItem(j, filler);
        }

    }

    private void addLore(ItemBuilder builder, String kit, String type) {
        Map<String, Integer> leaderboard = plugin.getLeaderboardManager().getLeaderboard(kit, type);

        int i = 1;
        for(String player : leaderboard.keySet()) {
            builder.addLore("&a#" + i + ". &f" + player + "&a: " + leaderboard.get(player));
            i++;
        }
    }
}