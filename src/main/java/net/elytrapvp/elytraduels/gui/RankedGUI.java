package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class RankedGUI extends CustomGUI {

    public RankedGUI(ElytraDuels plugin, Player player) {
        super(45, "Kits");
        filler();

        CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(player);

        int[] iconSlots = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,21,23};
        int i = 0;
        for(Kit kit : plugin.kitManager().getRankedKits()) {

            int playing = plugin.queueManager().getPlaying(kit) + plugin.queueManager().getQueueing(kit, GameType.RANKED);
            if(playing == 0) {
                playing = 1;
            }

            String elo;

            try {
                elo = customPlayer.getElo(kit.getName().toLowerCase()) + "";
            }
            catch (NullPointerException e) {
                elo = "Loading";
            }


            ItemBuilder item = new ItemBuilder(kit.getIconMaterial(), playing)
                    .setDisplayName("&a&l" + kit.getName())
                    .addLore("&aPlaying: &f" + plugin.queueManager().getPlaying(kit))
                    .addLore("&aQueuing: &f" + plugin.queueManager().getQueueing(kit, GameType.RANKED))
                    .addLore("")
                    .addLore("&aYour Elo: &f" + elo)
                    .addLore("")
                    .addLore("&aLeaderboard:");

            Map<String, Integer> lb = plugin.leaderboardManager().getLeaderboard(kit.getName().toLowerCase(), "elo");

            int place = 0;
            for(String lbPlayer : lb.keySet()) {
                if(place >= 3) {
                    break;
                }

                item.addLore(" &a" + (place + 1) + ". " + lbPlayer + ": &f" + lb.get(lbPlayer));
                item.addFlag(ItemFlag.HIDE_ATTRIBUTES);
                place++;
            }

            setItem(iconSlots[i], item.build(), (pl, a) -> {
                pl.closeInventory();
                plugin.queueManager().addPlayer(pl, kit, GameType.RANKED);
            });
            i++;
        }
    }

    private void filler() {
        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(" ");
        filler.setItemMeta(meta);

        int[] fillerSlots = new int[]{0,1,2,3,4,5,6,7,8,9,10,16,17,18,26,27,35,36,37,38,39,40,41,42,43,44};

        for(int slot : fillerSlots) {
            setItem(slot, filler);
        }
    }
}