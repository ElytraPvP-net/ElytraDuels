package net.elytrapvp.elytraduels.game.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

/**
 * Represents a color option for a team.
 */
public enum TeamColor {
    RED(ChatColor.RED, Color.RED, DyeColor.RED),
    ORANGE(ChatColor.GOLD, Color.ORANGE, DyeColor.ORANGE),
    YELLOW(ChatColor.YELLOW, Color.YELLOW, DyeColor.YELLOW),
    GREEN(ChatColor.GREEN, Color.LIME, DyeColor.LIME),
    DARK_GREEN(ChatColor.DARK_GREEN, Color.GREEN, DyeColor.GREEN),
    AQUA(ChatColor.AQUA, Color.AQUA, DyeColor.LIGHT_BLUE),
    BLUE(ChatColor.BLUE, Color.BLUE, DyeColor.BLUE),
    PURPLE(ChatColor.DARK_PURPLE, Color.PURPLE, DyeColor.PURPLE),
    PINK(ChatColor.LIGHT_PURPLE, Color.FUCHSIA, DyeColor.PINK),
    BLACK(ChatColor.BLACK, Color.BLACK, DyeColor.BLACK),
    WHITE(ChatColor.WHITE, Color.WHITE, DyeColor.WHITE);

    private final ChatColor chatColor;
    private final Color leatherColor;
    private final DyeColor woolColor;

    TeamColor(ChatColor chatColor, Color leatherColor, DyeColor woolColor) {
        this.chatColor = chatColor;
        this.leatherColor = leatherColor;
        this.woolColor = woolColor;
    }

    /**
     * Gets the chat color of a team.
     * @return Chat color of the team.
     */
    public ChatColor getChatColor() {
        return chatColor;
    }

    /**
     * Gets the leather color of a team.
     * @return Leather color of the team.
     */
    public Color getLeatherColor() {
        return leatherColor;
    }

    /**
     * Gets the Wool color of the team.
     * @return Color of the wool the team uses.
     */
    public DyeColor getWoolColor() {
        return woolColor;
    }
}