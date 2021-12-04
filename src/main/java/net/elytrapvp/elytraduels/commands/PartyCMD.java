package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PartyCMD extends AbstractCommand {
    private final ElytraDuels plugin;

    /**
     * Executes the command.
     */
    public PartyCMD(ElytraDuels plugin) {
        super("party", "", false);
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            ChatUtils.chat(sender, "&a&m---------------------------------------------------");
            ChatUtils.centeredChat(sender, "&a&lParty Commands");
            ChatUtils.chat(sender, "&a  /party chat");
            ChatUtils.chat(sender, "&a  /party create");
            ChatUtils.chat(sender, "&a  /party disband");
            ChatUtils.chat(sender, "&a  /party invite [player]");
            ChatUtils.chat(sender, "&a  /party leave");
            ChatUtils.chat(sender, "&a&m---------------------------------------------------");
            return;
        }

        Player player = (Player) sender;
        Party party = plugin.getPartyManager().getParty(player);

        switch(args[0]) {
            case "create":
                if(plugin.getPartyManager().getParty(player) != null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are already in a party.");
                    return;
                }

                if(plugin.getGameManager().getGame(player) != null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are already in a game.");
                    return;
                }

                plugin.getPartyManager().createParty(player);
                ChatUtils.chat(sender, "&a&lParty &8» &aParty as been created.");
                ItemUtils.givePartyItems(plugin.getPartyManager(), player);
                break;
            case "help":
                ChatUtils.chat(sender, "&a&m---------------------------------------------------");
                ChatUtils.centeredChat(sender, "&a&lParty Commands");
                ChatUtils.chat(sender, "&a  /party chat");
                ChatUtils.chat(sender, "&a  /party create");
                ChatUtils.chat(sender, "&a  /party disband");
                ChatUtils.chat(sender, "&a  /party invite [player]");
                ChatUtils.chat(sender, "&a  /party leave");
                ChatUtils.chat(sender, "&a  /party list");
                ChatUtils.chat(sender, "&a  /party promote");
                ChatUtils.chat(sender, "&a&m---------------------------------------------------");
                break;

            case "invite":
                if(args.length != 2) {
                    ChatUtils.chat(sender, "&cUsage &8» /party invite [player]");
                    return;
                }

                Player t = Bukkit.getPlayer(args[1]);
                if(t == null) {
                    ChatUtils.chat(sender, "&cError &8» &cThat person is not online.");
                    return;
                }

                if(t.equals(player)) {
                    ChatUtils.chat(sender, "&cError &8» &cYou cannot invite yourself.");
                    return;
                }

                if(plugin.getPartyManager().getParty(t) != null) {
                    ChatUtils.chat(sender, "&cError &8» &cThey are already in a party.");
                    return;
                }

                if(plugin.getPartyManager().getParty(player) == null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are not in a party! /party create.");
                    return;
                }

                if(plugin.getGameManager().getGame(t) != null) {
                    ChatUtils.chat(sender, "&cError &8» &cThat person is currently in a game.");
                    return;
                }

                if(party.getInvites().contains(t)) {
                    ChatUtils.chat(sender, "&cError &8» &cYou already have a pending invite to that person.");
                    return;
                }

                CustomPlayer customPlayer = plugin.getCustomPlayerManager().getPlayer(t);
                if(!customPlayer.getPartyInvites()) {
                    ChatUtils.chat(sender, "&cError &8» &cYou cannot invite that player to a party.");
                    return;
                }

                party.addInvite(t);
                ChatUtils.chat(t, "&a&m---------------------------------------------------");
                ChatUtils.chat(t, "&aYou have been invited to join &f" + player.getName() + "&a's party!");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + t.getName() +
                        " [{\"text\":\"[Accept]\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/party accept "  + player.getName() + "\"}},{\"text\":\" / \",\"color\":\"gray\"},{\"text\":\"[Decline]\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/party decline " +  player.getName() + "\"}}]");
                ChatUtils.chat(t, "&a&m---------------------------------------------------");
                party.broadcast("&a&lParty &8» &f" + t.getName() + " &ahas been invited to the party.");
                break;

            case "disband":
                party.broadcast("&a&lParty &8» &aParty has been disbanded.");
                plugin.getPartyManager().getParty(player).disband();
                break;
            case "leave":
                if(party == null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are not in a party! /party create.");
                    return;
                }

                party.removePlayer(player);
                party.broadcast("&a&lParty &8» &f" + player.getName() + " &ahas left the party.");
                break;

            case "chat":
                if(party == null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are not in a party! /party create.");
                    return;
                }

                if(args.length < 2) {
                    party.togglePartyChat(player);

                    if(party.hasPartyChatToggled(player)) {
                        ChatUtils.chat(sender, "&a&lParty &8» &aParty chat enabled.");
                    }
                    else {
                        ChatUtils.chat(sender, "&a&lParty &8» &aParty chat disabled.");
                    }

                    return;
                }

                args[0] = "";
                party.broadcast("&a&lParty &8» &f" + player.getName() + "&8: &a" + StringUtils.join(args, " "));

                break;
            case "list":
                if(party == null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are not in a party! /party create.");
                    return;
                }

                List<String> members = new ArrayList<>();
                for(Player member : party.getMembers()) {
                    members.add(member.getName());
                }

                ChatUtils.chat(sender, "&a&m---------------------------------------------------");
                ChatUtils.centeredChat(sender, "&a&lParty Members");
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "&aLeader &7» &f" + party.getLeader().getName());
                ChatUtils.chat(sender, "&aMembers &7[" + members.size() + "] » &f" + StringUtils.join(members, ", "));
                ChatUtils.chat(sender, "");
                ChatUtils.chat(sender, "&a&m---------------------------------------------------");
                break;
            case "promote":
                if(party == null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are not in a party! /party create.");
                    return;
                }

                if(args.length == 1) {
                    ChatUtils.chat(sender, "&cUsage &8» &c/party promote [player]");
                    return;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if(target == null || !party.getPlayers().contains(target)) {
                    ChatUtils.chat(sender, "&cError &8» &cThat player is not in your party!");
                    return;
                }

                if(target.equals(player)) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are already the party leader!");
                    return;
                }

                party.setLeader(target);
                party.broadcast("&a&lParty &8» &f" + target.getName() + " &ahas been promoted to party leader.");

                break;
            case "accept":
                if(args.length == 1) {
                    ChatUtils.chat(sender, "&cUsage &8» &c/party accept [player]");
                    return;
                }

                Player ta = Bukkit.getPlayer(args[1]);
                if(ta == null) {
                    ChatUtils.chat(sender, "&cError &8» &cThat player is not online");
                    return;
                }

                Party party1 = plugin.getPartyManager().getParty(ta);
                if(party1 == null) {
                    ChatUtils.chat(sender, "&cError &8» &cThat player is not in a party");
                    return;
                }

                if(!party1.getInvites().contains(player)) {
                    ChatUtils.chat(sender, "&cError &8» &cYou do not have an invite to that party.");
                    return;
                }

                party1.addPlayer(player);
                party1.broadcast("&a&lParty &8» &f" + player.getName() + " &ahas joined the party.");
                break;
            case "decline":
                if(args.length == 1) {
                    ChatUtils.chat(sender, "&cUsage &8» &c/party decline [player]");
                    return;
                }

                Player tar = Bukkit.getPlayer(args[1]);
                if(tar == null) {
                    ChatUtils.chat(sender, "&cError &8» &cThat player is not online");
                    return;
                }

                Party party2 = plugin.getPartyManager().getParty(tar);
                if(party2 == null) {
                    ChatUtils.chat(sender, "&cError &8» &cThat player is not in a party");
                    return;
                }

                if(!party2.getInvites().contains(player)) {
                    ChatUtils.chat(sender, "&cError &8» &cYou do not have an invite to that party.");
                    return;
                }

                party2.getInvites().remove(player);
                party2.broadcast("&a&lParty &8» &f" + player.getName() + " &ahas declined the invite.");
                ChatUtils.chat(sender, "&cYou have declined the invite.");
                break;
        }
    }
}