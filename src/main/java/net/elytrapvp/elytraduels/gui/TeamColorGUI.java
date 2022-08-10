package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import net.elytrapvp.elytraduels.game.team.TeamColor;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import net.elytrapvp.elytraduels.utils.item.SkullBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamColorGUI extends CustomGUI {
    private final ElytraDuels plugin;

    public TeamColorGUI(ElytraDuels plugin) {
        super(54, "Team Color Selector");
        this.plugin = plugin;

        int[] fillers = {1,2,3,4,5,6,7,8,45,46,47,48,50,51,52,53};
        for(int i : fillers) {
            ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
            ItemMeta meta = filler.getItemMeta();
            meta.setDisplayName(" ");
            filler.setItemMeta(meta);
            setItem(i, filler);
        }

        ItemStack back = new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6")
                .setDisplayName("&cBack")
                .build();
        setItem(0, back, (p, a) -> {
            p.closeInventory();
            new SettingsGUI(plugin, p).open(p);
        });

        int slot = 9;
        for(TeamColor teamColor : TeamColor.values()) {
            ItemBuilder builder = new ItemBuilder(Material.LEATHER_CHESTPLATE).dye(teamColor.leatherColor()).setDisplayName(teamColor.chatColor() + teamColor.name());
            setItem(slot, builder.build(), (player, clicktype) -> {
                CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(player);
                customPlayer.setTeamColor(teamColor.name());
                ChatUtils.chat(player, "&aPreferred team color set to " + teamColor.chatColor() + teamColor.name() + "&a.");
            });
            slot++;
        }

        setItem(49, new ItemBuilder(Material.BARRIER).setDisplayName("&cReset").build(), (player,action) -> {
            CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(player);
            customPlayer.setTeamColor("NONE");
            ChatUtils.chat(player, "&aPreferred team color reset.");
        });
    }
}
