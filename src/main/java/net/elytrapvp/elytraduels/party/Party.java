package net.elytrapvp.elytraduels.party;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a group of players
 * outside of a Game.
 */
public class Party {
    private final PartyManager partyManager;
    private Player leader;
    private final Set<Player> members = new HashSet<>();

    /**
     * Create a new party.
     * @param partyManager PartyManager used.
     * @param leader Leader of the party.
     */
    public Party(PartyManager partyManager, Player leader) {
        this.partyManager = partyManager;
        this.leader = leader;
    }

    /**
     * Disband the party.
     */
    public void disband() {
        // TODO: Properly handle players when disbanded.
        partyManager.disbandParty(this);
    }

    /**
     * Get the current leader of the party.
     * @return Current party leader.
     */
    public Player getLeader() {
        return leader;
    }

    /**
     * Get all party members.
     * @return Members of the party.
     */
    public Set<Player> getMembers() {
        return members;
    }

    /**
     * Get all players in the party.
     * @return All players.
     */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>(getMembers());
        players.add(getLeader());
        return players;
    }

    /**
     * Change the leader of the party.
     * @param leader New party leader.
     */
    public void setLeader(Player leader) {
        this.leader = leader;
    }
}