package net.elytrapvp.elytraduels;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * This class will be registered through the register-method in the
 * plugins onEnable-method.
 */
class Placeholders extends PlaceholderExpansion {
    private final ElytraDuels plugin;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin
     *        The instance of our plugin.
     */
    public Placeholders(ElytraDuels plugin){
        this.plugin = plugin;
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "duels";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        // Level
        if(identifier.equals("prefix")) {
            Game game = plugin.gameManager().getGame(player);

            if(game == null) {
                return PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%&7");
            }

            return game.getTeamManager().getTeam(player).teamColor().chatColor() + "";
        }

        if(identifier.contains("elo_top_name_")) {
            ArrayList<String> temp = new ArrayList<>(plugin.leaderboardManager().getLeaderboard("global", "elo").keySet());

            int place = Integer.parseInt(identifier.replaceAll("\\D+","")) - 1;
            return temp.get(place);
        }

        if(identifier.contains("elo_top_amount_")) {
            ArrayList<Integer> temp = new ArrayList<>(plugin.leaderboardManager().getLeaderboard("global", "elo").values());

            int place = Integer.parseInt(identifier.replaceAll("\\D+","")) - 1;
            return temp.get(place) + "";
        }

        if(identifier.contains("wins_top_name_")) {
            ArrayList<String> temp = new ArrayList<>(plugin.leaderboardManager().getLeaderboard("global", "wins").keySet());

            int place = Integer.parseInt(identifier.replaceAll("\\D+","")) - 1;
            return temp.get(place);
        }

        if(identifier.contains("wins_top_amount_")) {
            ArrayList<Integer> temp = new ArrayList<>(plugin.leaderboardManager().getLeaderboard("global", "wins").values());

            int place = Integer.parseInt(identifier.replaceAll("\\D+","")) - 1;
            return temp.get(place) + "";
        }

        if(identifier.contains("winstreak_top_name_")) {
            ArrayList<String> temp = new ArrayList<>(plugin.leaderboardManager().getLeaderboard("global", "bestWinStreak").keySet());

            int place = Integer.parseInt(identifier.replaceAll("\\D+","")) - 1;
            return temp.get(place);
        }

        if(identifier.contains("winstreak_top_amount_")) {
            ArrayList<Integer> temp = new ArrayList<>(plugin.leaderboardManager().getLeaderboard("global", "bestWinStreak").values());

            int place = Integer.parseInt(identifier.replaceAll("\\D+","")) - 1;
            return temp.get(place) + "";
        }

        return null;
    }
}