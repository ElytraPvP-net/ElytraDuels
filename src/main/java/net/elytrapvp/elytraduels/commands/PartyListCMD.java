package net.elytrapvp.elytraduels.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyListCMD extends AbstractCommand {

    public PartyListCMD() {
        super("partylist", "", false);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.performCommand("party list");
    }
}