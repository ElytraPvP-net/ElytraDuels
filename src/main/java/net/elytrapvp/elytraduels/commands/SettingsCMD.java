package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.gui.SettingsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCMD extends AbstractCommand {
    private ElytraDuels plugin;

    public SettingsCMD(ElytraDuels plugin) {
        super("settings", "", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new SettingsGUI(plugin, player).open(player);
    }
}