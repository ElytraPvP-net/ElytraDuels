package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class DiscordCMD extends AbstractCommand {
    private ElytraDuels plugin;

    public DiscordCMD(ElytraDuels plugin) {
        super("discord", "", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        ChatUtils.chat(player, "https://discord.gg/jy28s9cdrA");
        return;
    }


}
