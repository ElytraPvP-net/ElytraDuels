package net.elytrapvp.elytraduels.party;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
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
    private final Set<Player> partyChatToggled = new HashSet<>();

    /**
     * Create a new party.
     * @param plugin Plugin instance.
     * @param leader Leader of the party.
     */
    public Party(ElytraDuels plugin, Player leader) {
        this.plugin = plugin;
        this.leader = leader;
    }

    /**
     * Add a player to the party.
     * @param player Player to add.
     */
    public void addPlayer(Player player) {
        members.add(player);
        ItemUtils.givePartyItems(plugin.getPartyManager(), player);
    }

    /**
     * Broadcast a message to all party members.
     * @param message Message to broadcast.
     */
    public void broadcast(String message) {
        for(Player player : getPlayers()) {
            ChatUtils.chat(player, message);
        }
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
     * Get if a player has party chat toggled.
     * @param player Player to check.
     * @return If they have party chat toggled.
     */
    public boolean hasPartyChatToggled(Player player) {
        return partyChatToggled.contains(player);
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
        members.add(getLeader());
        this.leader = leader;
        members.remove(leader);
    }

    /**
     * Toggle party chat for a player.
     * @param player Player to toggle party chat for.
     */
    public void togglePartyChat(Player player) {
        if(!partyChatToggled.contains(player)) {
            partyChatToggled.add(player);
        }
        else {
            partyChatToggled.remove(player);
        }
    }
}