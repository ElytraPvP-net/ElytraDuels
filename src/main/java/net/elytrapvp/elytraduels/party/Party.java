package net.elytrapvp.elytraduels.party;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Represents a group of players outside the game.
 */
public class Party {
    private final ElytraDuels plugin;
    private final Map<UUID, PartyRank> members = new HashMap<>();
    private final Set<UUID> partyChatToggled = new HashSet<>();
    private final Map<UUID, String> invites = new HashMap<>();

    /**
     * Creates the party object.
     * @param plugin Instance of the main plugin.
     * @param leader Leader of the party.
     */
    public Party(ElytraDuels plugin, Player leader) {
        this.plugin = plugin;
        members.put(leader.getUniqueId(), PartyRank.LEADER);
    }

    /**
     * Add a player to the party.
     * @param player Player to add.
     */
    public void addPlayer(Player player) {
        members.put(player.getUniqueId(), PartyRank.MEMBER);
        invites.remove(player.getUniqueId());
    }

    /**
     * Broadcast a message to all party members.
     * @param message Message to broadcast.
     */
    @Deprecated
    public void broadcast(String message) {
        for(Player player : getMembers()) {
            ChatUtils.chat(player, message);
        }
    }

    /**
     * Disband the party.
     */
    @Deprecated
    public void disband() {
        for(Player player : getMembers()) {
            ItemUtils.giveLobbyItems(player);
        }

        plugin.getPartyManager().disbandParty(this);
    }


    /**
     * Invites a player to the party.
     * @param player Player being invited to the party.
     */
    public void invitePlayer(Player player) {
        invites.put(player.getUniqueId(), player.getName());

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if(!invites.keySet().contains(player.getUniqueId())) {
                return;
            }

            sendMessage("&a&lParty &8» &f" + invites.get(player.getUniqueId()) + "&a's invite has expired.");
            invites.remove(player.getUniqueId());
        }, 20*60);
    }

    /**
     * Get all current invites.
     * @return All current invites.
     */
    public Collection<Player> getInvites() {
        Collection<Player> partyInvites = new ArrayList<>();

        invites.keySet().forEach(uuid -> {
            if(Bukkit.getPlayer(uuid) != null) {
                partyInvites.add(Bukkit.getPlayer(uuid));
            }
        });

        return partyInvites;
    }

    /**
     * Gets the leader of the party.
     * @return Party leader.
     */
    public Player getLeader() {
        // Loops through all party members.
        for(Player player : getMembers()) {
            // Checks if they are party leader.
            if(getRank(player) == PartyRank.LEADER) {
                return player;
            }
        }

        // If no party leader found, returns null.
        return null;
    }

    /**
     * Get all members in the party.
     * @return List of all party members.
     */
    public List<Player> getMembers() {
        List<Player> partyMembers = new ArrayList<>();

        members.keySet().forEach(uuid -> {
            if(Bukkit.getPlayer(uuid) != null) {
                partyMembers.add(Bukkit.getPlayer(uuid));
            }
        });

        return partyMembers;
    }

    /**
     * Get the rank of a player in the party.
     * Returns null if they are not in the party.
     * @param player Player to get rank of.
     * @return PartyRank of the player.
     */
    public PartyRank getRank(Player player) {
        if(members.containsKey(player.getUniqueId())) {
            return members.get(player.getUniqueId());
        }

        return null;
    }

    /**
     * Get if a player has party chat toggled.
     * @param player Player to check.
     * @return If they have party chat toggled.
     */
    public boolean hasPartyChatToggled(Player player) {
        return partyChatToggled.contains(player.getUniqueId());
    }

    /**
     * Removes the invite to a player.
     * @param player Player to remove invite to.
     */
    public void removeInvite(Player player) {
        invites.remove(player.getUniqueId());
    }

    /**
     * Removes a player from the party.
     * @param player Player to remove.
     */
    public void removePlayer(Player player) {
        if(getRank(player) == PartyRank.LEADER) {
            plugin.getPartyManager().disbandParty(this);
            return;
        }

        members.remove(player.getUniqueId());
    }

    /**
     * Sends a chat message to all party members
     * @param message Message to send to party members.
     */
    public void sendMessage(String message) {
        for(Player player : getMembers()) {
            ChatUtils.chat(player, message);
        }
    }

    /**
     * Chance a player's rank in the party.
     * @param player Player to change the rank of.
     * @param rank Rank to set the player to.
     */
    public void setRank(Player player, PartyRank rank) {
        members.put(player.getUniqueId(), rank);
    }

    /**
     * Toggle party chat for a player.
     * @param player Player to toggle party chat for.
     */
    public void togglePartyChat(Player player) {
        if(!partyChatToggled.contains(player.getUniqueId())) {
            partyChatToggled.add(player.getUniqueId());
        }
        else {
            partyChatToggled.remove(player.getUniqueId());
        }
    }
}