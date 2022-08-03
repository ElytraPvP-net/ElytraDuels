package net.elytrapvp.elytraduels.party;

import net.elytrapvp.elytraduels.ElytraDuels;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all existing party.
 */
public class PartyManager {
    private final ElytraDuels plugin;
    private final List<Party> parties = new ArrayList<>();

    /**
     * Creates the party manager.
     * @param plugin Instance of the plugin.
     */
    public PartyManager(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    /**
     * Create a new party.
     * @param leader Leader of the party,
     * @return Party that was created.
     */
    public Party createParty(Player leader) {
        Party party = new Party(plugin, leader);
        parties.add(party);
        return party;
    }

    /**
     * Disbands an active party.
     * @param party Party to disband.
     */
    public void disbandParty(Party party) {
        getParties().remove(party);
    }

    /**
     * Get the party a player is in.
     * Returns null if not in a party.
     * @param player Player to get party of.
     * @return Party the player is in.
     */
    public Party getParty(Player player) {
        for(Party party : getParties()) {
            if(party.getMembers().contains(player)) {
                return party;
            }
        }

        return null;
    }

    /**
     * Get all current parties.
     * @return All current parties.
     */
    @Deprecated
    public List<Party> getParties() {
        return parties;
    }

    /**
     * Get all current parties.
     * @return All current parties.
     */
    public List<Party> parties() {
        return parties;
    }
}