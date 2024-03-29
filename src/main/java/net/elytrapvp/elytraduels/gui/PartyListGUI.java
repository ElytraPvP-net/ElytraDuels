package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import net.elytrapvp.elytraduels.utils.item.SkullBuilder;
import org.bukkit.entity.Player;

public class PartyListGUI extends CustomGUI {

    public PartyListGUI(ElytraDuels plugin, Player player) {
        super(54, "All Parties");

        for(int i = 0; i < plugin.partyManager().getParties().size(); i++) {
            Party party = plugin.partyManager().getParties().get(i);

            ItemBuilder item = new SkullBuilder(party.getLeader())
                    .setDisplayName("&a" + party.getLeader().getName() + "'s party");

            for(Player member : party.getMembers()) {
                item.addLore("&7 - " + member.getName());
            }

            setItem(i, item.build(), (pl, a) -> {
                if(party.getLeader().equals(pl)) {
                    pl.closeInventory();
                    ChatUtils.chat(pl, "&cError &8» &cYou cannot duel yourself.");
                    return;
                }

                new DuelGUI(plugin, player, party.getLeader()).open(player);
            });
        }
    }
}