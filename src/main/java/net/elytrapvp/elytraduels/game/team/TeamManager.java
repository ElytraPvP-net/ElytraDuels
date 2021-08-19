package net.elytrapvp.elytraduels.game.team;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {
    private final Set<Team> teams = new HashSet<>();

    /**
     * Create a new team.
     * @param players Players to add to the team.
     * @return The new team.
     */
    public Team createTeam(Set<Player> players) {
        Team team = new Team(players);
        getTeams().add(team);
        return team;
    }

    /**
     * Delete a team.
     * @param team Team to delete.
     */
    public void deleteTeam(Team team) {
        getTeams().remove(team);
    }

    /**
     * Get the team of a specific player.
     * Returns null if no team.
     * @param player Player to get team of.
     * @return Team the player is in.
     */
    public Team getTeam(Player player) {
        for(Team team : getTeams()) {
            if(team.getPlayers().contains(player)) {
                return team;
            }
        }

        return null;
    }

    /**
     * Get all existing teams in the manager.
     * @return All existing teams.
     */
    public Set<Team> getTeams() {
        return teams;
    }
}