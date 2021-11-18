package net.elytrapvp.elytraduels.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyChatCMD extends AbstractCommand {

    public PartyChatCMD() {
        super("partychat", "", false);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.performCommand("party chat " + StringUtils.join(args, " "));
    }
}
