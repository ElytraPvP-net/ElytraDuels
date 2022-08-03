package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.gui.SpectateGUI;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCMD extends AbstractCommand {
    private final ElytraDuels plugin;

    public SpectateCMD(ElytraDuels plugin) {
        super("spectate", "", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            if(sender.hasPermission("spectate.gui")) {
                new SpectateGUI(plugin).open((Player) sender);
                return;
            }

            ChatUtils.chat(sender, "&cUsage &8» &c/spec [player]");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            ChatUtils.chat(sender, "&cError &8» &cThat player is not online.");
            return;
        }

        Game game = plugin.gameManager().getGame(target);
        if(game == null) {
            ChatUtils.chat(sender, "&cError &8» &cThat player is not in a game.");
            return;
        }

        Player player = (Player) sender;

        if(plugin.gameManager().getGame(player) != null) {
            ChatUtils.chat(sender, "&cError &8» &cYou are already spectating someone!");
            return;
        }

        game.addSpectator(player);
        game.broadcast("&a" + player.getName() + " is now spectating.");
    }
}