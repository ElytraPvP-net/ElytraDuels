package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import net.elytrapvp.elytraduels.scoreboards.LobbyScoreboard;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsGUI extends CustomGUI {

    public SettingsGUI(ElytraDuels plugin, Player player) {
        super(54, "Settings");

        int[] fillers = {0,1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53};
        for(int i : fillers) {
            ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
            ItemMeta meta = filler.getItemMeta();
            meta.setDisplayName(" ");
            filler.setItemMeta(meta);
            setItem(i, filler);
        }

        setItem(40, new ItemBuilder(Material.ANVIL).setDisplayName("&a&lKit Editor").build(), (p, a) -> new KitEditorGUI(plugin).open(p));

        CustomPlayer customPlayer = plugin.getCustomPlayerManager().getPlayer(player);
        ItemBuilder scoreboard = new ItemBuilder(Material.SIGN).setDisplayName("&a&lShow Scoreboard");
        if(customPlayer.getShowScoreboard()) {
            scoreboard.addLore("&aEnabled");
        }
        else {
            scoreboard.addLore("&cDisabled");
        }
        setItem(20, scoreboard.build(), (p,a) -> {
            customPlayer.setShowScoreboard(!customPlayer.getShowScoreboard());
            new SettingsGUI(plugin, player).open(player);

            if(customPlayer.getShowScoreboard()) {
                new LobbyScoreboard(plugin, p);
            }
        });

        ItemBuilder partyInvites = new ItemBuilder(Material.CAKE).setDisplayName("&a&lParty Invites");
        if(customPlayer.getPartyInvites()) {
            partyInvites.addLore("&aEnabled");
        }
        else {
            partyInvites.addLore("&cDisabled");
        }
        setItem(22, partyInvites.build(), (p,a) -> {
            customPlayer.setPartyInvites(!customPlayer.getPartyInvites());
            new SettingsGUI(plugin, player).open(player);
        });

        ItemBuilder duelRequests = new ItemBuilder(Material.IRON_SWORD).setDisplayName("&a&lDuel Requests").addFlag(ItemFlag.HIDE_ATTRIBUTES);
        if(customPlayer.getShowScoreboard()) {
            duelRequests.addLore("&aEnabled");
        }
        else {
            duelRequests.addLore("&cDisabled");
        }
        setItem(24, duelRequests.build(), (p,a) -> {
            customPlayer.setDuelRequests(!customPlayer.getDuelRequests());
            new SettingsGUI(plugin, player).open(player);
        });
    }
}