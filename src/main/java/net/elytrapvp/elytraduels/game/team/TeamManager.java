package net.elytrapvp.elytraduels.game.team;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {
    private final List<Team> teams = new ArrayList<>();
    private final List<Team> aliveTeams = new ArrayList<>();
    private final List<TeamColor> availableColors = new ArrayList<>();

    /**
     * Creates the team manager.
     */
    public TeamManager() {
        availableColors.add(TeamColor.RED);
        availableColors.add(TeamColor.BLUE);
        availableColors.add(TeamColor.YELLOW);
        availableColors.add(TeamColor.GREEN);
        availableColors.add(TeamColor.PURPLE);
        availableColors.add(TeamColor.ORANGE);
        availableColors.add(TeamColor.AQUA);
        availableColors.add(TeamColor.PINK);
        availableColors.add(TeamColor.DARK_GREEN);
        availableColors.add(TeamColor.BLACK);
        availableColors.add(TeamColor.WHITE);
    }

    /**
     * Create a new team.
     * @param players Players to add to the team.
     * @return The new team.
     */
    public Team createTeam(List<Player> players) {
        Team team = new Team(players, availableColors.get(0));
        availableColors.remove(availableColors.get(0));
        getTeams().add(team);
        aliveTeams.add(team);
        return team;
    }

    /**
     * Delete a team.
     * @param team Team to delete.
     */
    public void deleteTeam(Team team) {
        getTeams().remove(team);
        aliveTeams.remove(team);
    }

    /**
     * Get all teams that are still alive.
     * @return All alive teams.
     */
    @Deprecated
    public List<Team> getAliveTeams() {
        return aliveTeams;
    }

    /**
     * Get all teams that are still alive.
     * @return All alive teams.
     */
    public List<Team> aliveTeams() {
        return aliveTeams;
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
    @Deprecated
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Get all existing teams in the manager.
     * @return All existing teams.
     */
    public List<Team> teams() {
        return teams;
    }

    /**
     * Kill a team.
     */
    public void killTeam(Team team) {
        aliveTeams.remove(team);
    }
}