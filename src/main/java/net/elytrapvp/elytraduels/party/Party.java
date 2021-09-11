package net.elytrapvp.elytraduels.party;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.utils.ItemUtils;
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
    private final ElytraDuels plugin;
    private Player leader;
    private final Set<Player> members = new HashSet<>();

    /**
     * Create a new party.
     * @param plugin Plugin instance.
     * @param leader Leader of the party.
     */
    public Party(ElytraDuels plugin, Player leader) {
        this.plugin = plugin;
        this.leader = leader;
    }

    public void addPlayer(Player player) {
        members.add(player);
        ItemUtils.givePartyItems(plugin.getPartyManager(), player);
    }

    /**
     * Disband the party.
     */
    public void disband() {
        for(Player player : getPlayers()) {
            ItemUtils.giveLobbyItems(player);
        }

        plugin.getPartyManager().disbandParty(this);
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
     * Remove a player from the party.
     * @param p Player to remove.
     */
    public void removePlayer(Player p) {
        if(leader.equals(p)) {
            disband();
            return;
        }

        Game game = plugin.getGameManager().getGame(p);
        if(game == null) {
            ItemUtils.giveLobbyItems(p);
        }

        members.remove(p);
    }

    /**
     * Change the leader of the party.
     * @param leader New party leader.
     */
    public void setLeader(Player leader) {
        this.leader = leader;
    }
}