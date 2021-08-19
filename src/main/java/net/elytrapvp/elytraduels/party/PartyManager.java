package net.elytrapvp.elytraduels.party;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages all existing party.
 */
public class PartyManager {
    private final Set<Party> parties = new HashSet<>();

    /**
     * Create a new party.
     * @param leader Leader of the party,
     * @return Party that was created.
     */
    public Party createParty(Player leader) {
        Party party = new Party(this, leader);
        getParties().add(party);
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
     * @return All current paries.
     */
    public Set<Party> getParties() {
        return parties;
    }
}