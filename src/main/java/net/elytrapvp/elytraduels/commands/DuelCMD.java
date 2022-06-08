package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.gui.DuelGUI;
import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DuelCMD extends AbstractCommand {
    private ElytraDuels plugin;

    /**
     * Registers the command.
     */
    public DuelCMD(ElytraDuels plugin) {
        super("duel", "", false);
        this.plugin = plugin;
    }

    /**
     * Executes the command.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            ChatUtils.chat(sender, "&cUsage &8» &c/duel [player] <kit>");
            return;
        }

        Player p = (Player) sender;

        if(args[0].equalsIgnoreCase("accept")) {
            Player opponent = Bukkit.getPlayer(args[1]);
            if(opponent == null) {
                ChatUtils.chat(sender, "&cError &8» &cThat player is not online.");
                return;
            }

            if(!plugin.getDuelManager().getDuelRequests().containsKey(opponent) || !plugin.getDuelManager().getDuelRequests().get(opponent).equals(p)) {
                ChatUtils.chat(sender, "&cError &8» &cThat person has not sent a duel request.");
                return;
            }

            Game game = plugin.getGameManager().getGame(p);
            if(game != null) {
                ChatUtils.chat(sender, "&cError &8» &cYou are in a match already.");
                return;
            }

            Game game2 = plugin.getGameManager().getGame(opponent);
            if(game2 != null) {
                ChatUtils.chat(sender, "&cError &8» &cThey are in a match already.");
                return;
            }

            Party targetParty = plugin.getPartyManager().getParty(opponent);
            if(targetParty != null && targetParty.getMembers().contains(opponent)) {
                ChatUtils.chat(sender, "&cError &8» &cThat player is in a party.");
                return;
            }

            ChatUtils.chat(sender, "&aDuel request has been accepted.");

            Party senderParty = plugin.getPartyManager().getParty(p);

            Kit kit = plugin.getDuelManager().getDuelKit(p);
            Game game3 = plugin.getGameManager().createGame(kit, GameType.DUEL);

            if(senderParty == null) {
                game3.addPlayer(p);
            }
            else {
                game3.addPlayers(senderParty.getMembers());
            }

            Party targetParty2 = plugin.getPartyManager().getParty(opponent);
            if(targetParty == null) {
                game3.addPlayer(opponent);
            }
            else {
                game3.addPlayers(targetParty.getMembers());
            }

            // Remove players from queue.
            plugin.getQueueManager().removePlayer(opponent);

            game3.start();
            return;
        }
        else if(args[0].equalsIgnoreCase("decline")) {
            Player opponent = Bukkit.getPlayer(args[1]);
            if(opponent == null) {
                ChatUtils.chat(sender, "&cError &8» &cThat player is not online.");
                return;
            }

            if(!plugin.getDuelManager().getDuelRequests().containsKey(opponent) || !plugin.getDuelManager().getDuelRequests().get(opponent).equals(p)) {
                ChatUtils.chat(sender, "&cError &8» &cThat person has not sent a duel request.");
                return;
            }

            for(Player pl : plugin.getDuelManager().getDuelRequests().keySet()) {
                if(plugin.getDuelManager().getDuelRequests().get(pl).equals(p)) {
                    plugin.getDuelManager().getDuelRequests().remove(p);
                    ChatUtils.chat(pl, "&c&l(&c!&c&l) &f" + p.getName() + " &cdeclined your duel request.");
                    ChatUtils.chat(p, "&aYou have declined &f" + pl.getName() + "&a's duel request.");
                }
            }
            return;
        }

        Player t = Bukkit.getPlayerExact(args[0]);

        if(t == null) {
            ChatUtils.chat(sender, "&cError &8» &cThat player is not online.");
            return;
        }

        if(p.equals(t)) {
            ChatUtils.chat(sender, "&cError &8» &cYou cannot duel yourself.");
            return;
        }

        Game game = plugin.getGameManager().getGame(p);
        if(game != null) {
            ChatUtils.chat(sender, "&cError &8» &cYou are in a match already.");
            return;
        }

        Game game2 = plugin.getGameManager().getGame(t);
        if(game2 != null) {
            ChatUtils.chat(sender, "&cError &8» &cThey are in a match already.");
            return;
        }

        Party targetParty = plugin.getPartyManager().getParty(t);
        if(targetParty != null && targetParty.getMembers().contains(t)) {
            ChatUtils.chat(sender, "&cError &8» &cThat player is in a party.");
            return;
        }

        CustomPlayer customPlayer = plugin.getCustomPlayerManager().getPlayer(t);
        if(!customPlayer.getDuelRequests()) {
            ChatUtils.chat(sender, "&cError &8» &cYou cannot request to duel that player.");
            return;
        }

        if(args.length < 2) {
            new DuelGUI(plugin, p, t).open(p);
            return;
        }

        String kit = args[1];

        if(args.length == 3) {
            kit += " " + args[2];
        }

        Kit k = plugin.getKitManager().getKit(kit);
        if(k == null) {
            ChatUtils.chat(sender, "&cError &8» &cThat kit does not exist.");
            return;
        }

        plugin.getDuelManager().addDuelRequest(p, t, k);
        ChatUtils.chat(p, "&a&l(&7!&a&l) &aDuel request sent.");
        ChatUtils.chat(t, "&a&m---------------------------------------------------");
        ChatUtils.chat(t, "&f" + p.getName() + " &awants to duel you in &f" + k.getName() + "&a!");
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + t.getName() +
                " [{\"text\":\"[Accept]\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/duel accept "  + p.getName() + "\"}},{\"text\":\" / \",\"color\":\"gray\"},{\"text\":\"[Decline]\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/duel decline " +  p.getName() + "\"}}]");
        ChatUtils.chat(t, "&a&m---------------------------------------------------");
    }
}