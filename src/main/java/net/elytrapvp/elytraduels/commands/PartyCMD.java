package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.party.PartyRank;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class runs the /party command, which controls everything about parties.
 */
public class PartyCMD extends AbstractCommand {
    private final ElytraDuels plugin;

    /**
     * Executes the command.
     */
    public PartyCMD(ElytraDuels plugin) {
        super("party", "", false);
        this.plugin = plugin;
    }

    /**
     * Executes the command.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            helpCMD(player);
            return;
        }

        switch(args[0]) {
            case "create":
                createCMD(player);
                break;
            case "disband":
                disbandCMD(player);
                break;
            case "invite":
                inviteCMD(player, args);
                break;
            case "help":
                helpCMD(player);
                break;
            case "leave":
                leaveCMD(player);
                break;
            case "chat":
                chatCMD(player, args);
                break;
            case "list":
                listCMD(player);
                break;
            case "promote":
                promoteCMD(player, args);
                break;
            case "accept":
                acceptCMD(player, args);
                break;
            case "decline":
                declineCMD(player, args);
                break;
            default:
                helpCMD(player);
                break;
        }
    }

    /**
     * Runs the /party chat command.
     * @param player Player running the command.
     * @param args Command arguments.
     */
    private void chatCMD(Player player, String[] args) {
        // Makes sure the player is in a party.
        if(plugin.partyManager().getParty(player) == null) {
            ChatUtils.chat(player, "&cError &8» &cYou are not in a party! /party create.");
            return;
        }

        // Gets the player's party.
        Party party = plugin.partyManager().getParty(player);

        // Checks if the player is just toggling party chat.
        if(args.length < 2) {
            party.togglePartyChat(player);

            if(party.hasPartyChatToggled(player)) {
                ChatUtils.chat(player, "&a&lParty &8» &aParty chat enabled.");
            }
            else {
                ChatUtils.chat(player, "&a&lParty &8» &aParty chat disabled.");
            }

            return;
        }

        // Sends the chat message to the party.
        args[0] = "";
        party.sendMessage("&a&lParty &8» &f" + player.getName() + "&8: &a" + StringUtils.join(args, " "));
    }

    /**
     * Runs the /party accept command.
     * @param player Player who ran the command.
     * @param args Command arguments.
     */
    private void acceptCMD(Player player, String[] args) {
        // Makes sure the player is using the command correctly.
        if(args.length == 1) {
            ChatUtils.chat(player, "&cUsage &8» &c/party accept [player]");
            return;
        }

        // Gets the target player.
        Player target = Bukkit.getPlayer(args[1]);

        // Makes sure the target player is online.
        if(target == null) {
            ChatUtils.chat(player, "&cError &8» &cThat player is not online");
            return;
        }

        // Get's the target party.
        Party party = plugin.partyManager().getParty(target);

        // Makes sure the player is in a party.
        if(party == null) {
            ChatUtils.chat(player, "&cError &8» &cThat player is not in a party");
            return;
        }

        // Makes sure the player is not playing a game at the moment.
        if(plugin.gameManager().getGame(player) != null) {
            ChatUtils.chat(player, "&cError &8» &cYou are already in a game.");
            return;
        }

        // Makes sure the player has an invitation to the party.
        if(!party.getInvites().contains(player)) {
            ChatUtils.chat(player, "&cError &8» &cYou do not have an invite to that party.");
            return;
        }

        party.addPlayer(player);
        party.sendMessage("&a&lParty &8» &f" + player.getName() + " &ahas joined the party.");
        ItemUtils.givePartyItems(plugin.partyManager(), player);
    }

    /**
     * Runs the /party create command.
     * @param player Player who ran the command.
     */
    private void createCMD(Player player) {
        // Makes sure the player is not already in a party.
        if(plugin.partyManager().getParty(player) != null) {
            ChatUtils.chat(player, "&cError &8» &cYou are already in a party.");
            return;
        }

        // Makes sure the player is not playing a game at the moment.
        if(plugin.gameManager().getGame(player) != null) {
            ChatUtils.chat(player, "&cError &8» &cYou are already in a game.");
            return;
        }

        // Creates the party.
        plugin.partyManager().createParty(player);
        ChatUtils.chat(player, "&a&lParty &8» &aParty as been created.");
        ItemUtils.givePartyItems(plugin.partyManager(), player);
    }

    /**
     * Runs the /party decline command.
     * @param player Player running the command.
     * @param args Command arguments.
     */
    private void declineCMD(Player player, String[] args) {
        // Makes sure the player is using the command correctly.
        if(args.length == 1) {
            ChatUtils.chat(player, "&cUsage &8» &c/party decline [player]");
            return;
        }

        // Gets the target player.
        Player target = Bukkit.getPlayer(args[1]);

        // Makes sure the target player is online.
        if(target == null) {
            ChatUtils.chat(player, "&cError &8» &cThat player is not online");
            return;
        }

        // Gets the target player's party.
        Party party = plugin.partyManager().getParty(target);

        // Makes sure the party exists.
        if(party == null) {
            ChatUtils.chat(player, "&cError &8» &cThat player is not in a party");
            return;
        }

        // Makes sure the player has an invite to the party.
        if(!party.getInvites().contains(player)) {
            ChatUtils.chat(player, "&cError &8» &cYou do not have an invite to that party.");
            return;
        }

        // Declines the invite.
        party.removeInvite(player);
        party.sendMessage("&a&lParty &8» &f" + player.getName() + " &ahas declined the invite.");
        ChatUtils.chat(player, "&a&lParty &8» &aYou have declined the invite.");
    }

    /**
     * Runs the /party disband command.
     * @param player Player who ran the command.
     */
    private void disbandCMD(Player player) {
        // Makes sure the player is in a party.
        if(plugin.partyManager().getParty(player) == null) {
            ChatUtils.chat(player, "&cError &8» &cYou are not in a party! /party create.");
            return;
        }

        // Gets the player's party.
        Party party = plugin.partyManager().getParty(player);

        // Makes sure the player has permission to disband the party.
        if(party.getRank(player) != PartyRank.LEADER) {
            ChatUtils.chat(player, "&cError &8» &cOnly the party leader can disband the party!");
            return;
        }

        // Disbands the party.
        party.sendMessage("&a&lParty &8» &aParty has been disbanded.");
        plugin.partyManager().disbandParty(party);
    }

    /**
     * Runs the /party help command.
     * @param player Player who ran the command.
     */
    private void helpCMD(Player player) {
        ChatUtils.chat(player, "&8&m+-----------------------***-----------------------+");
        ChatUtils.centeredChat(player, "&a&lParty Commands");
        ChatUtils.chat(player, "&a  /party chat");
        ChatUtils.chat(player, "&a  /party create");
        ChatUtils.chat(player, "&a  /party disband");
        ChatUtils.chat(player, "&a  /party invite [player]");
        ChatUtils.chat(player, "&a  /party leave");
        ChatUtils.chat(player, "&a  /party list");
        ChatUtils.chat(player, "&a  /party promote");
        ChatUtils.chat(player, "&8&m+-----------------------***-----------------------+");
    }

    /**
     * Runs the /player invite command.
     * @param player Player who ran the command.
     * @param args Command arguments.
     */
    private void inviteCMD(Player player, String[] args) {
        // Makes sure the player has enterted a username to invite.
        if(args.length != 2) {
            ChatUtils.chat(player, "&c&lUsage &8» &c/party invite [player]");
            return;
        }

        // Makes sure the target is online.
        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {
            ChatUtils.chat(player, "&cError &8» &cThat person is not online.");
            return;
        }

        // Makes sure they are not already in a party.
        if(plugin.partyManager().getParty(target) != null) {
            ChatUtils.chat(player, "&cError &8» &cThey are already in a party.");
            return;
        }

        // Makes sure the player is not trying to invite themselves.
        if(target.equals(player)) {
            ChatUtils.chat(player, "&cError &8» &cYou cannot invite yourself.");
            return;
        }

        // Checks if the player can receive party invites.
        CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(target);
        if(!customPlayer.getPartyInvites()) {
            ChatUtils.chat(player, "&cError &8» &cYou cannot invite that player to a party.");
            return;
        }

        // Makes sure the player is not in a game.
        if(plugin.gameManager().getGame(player) != null) {
            ChatUtils.chat(player, "&cError &8» &cThat person is currently in a game.");
            return;
        }

        // Makes sure the player is in a party.
        if(plugin.partyManager().getParty(player) == null) {
            player.chat("/party create");
        }

        // Gets the player's party,
        Party party = plugin.partyManager().getParty(player);

        // Makes sure the player has permission to invite someone to the party.
        if(!(party.getRank(player) == PartyRank.LEADER || party.getRank(player) == PartyRank.MODERATOR)) {
            ChatUtils.chat(player, "&cError &8» &cYou are not allowed to invite people to the party.");
            return;
        }

        // Makes sure the player wasn't already invited.
        if(party.getInvites().contains(target)) {
            ChatUtils.chat(player, "&cError &8» &cYou already have a pending invite to that person.");
            return;
        }

        party.invitePlayer(target);

        ChatUtils.chat(target, "&8&m+-----------------------***-----------------------+");
        ChatUtils.chat(target, "&aYou have been invited to join &f" + player.getName() + "&a's party!");
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + target.getName() +
                " [{\"text\":\"[Accept]\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/party accept "  + player.getName() + "\"}},{\"text\":\" / \",\"color\":\"gray\"},{\"text\":\"[Decline]\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/party decline " +  player.getName() + "\"}}]");
        ChatUtils.chat(target, "&8&m+-----------------------***-----------------------+");
        party.sendMessage("&a&lParty &8» &f" + target.getName() + " &ahas been invited to the party.");
    }

    /**
     * Runs the /party leave command.
     * @param player Player who ran the command.
     */
    private void leaveCMD(Player player) {
        // Makes sure the player is in a party.
        if(plugin.partyManager().getParty(player) == null) {
            ChatUtils.chat(player, "&cError &8» &cYou are not in a party! /party create.");
            return;
        }

        // Gets the player's party.
        Party party = plugin.partyManager().getParty(player);

        // Removes the player
        party.removePlayer(player);
        party.sendMessage("&a&lParty &8» &f" + player.getName() + " &ahas left the party.");
        ChatUtils.chat(player, "&a&lParty &8» &aYou have left the party.");
        ItemUtils.giveLobbyItems(player);
    }

    /**
     * Runs the /party list command.
     * @param player Player who ran the command.
     */
    private void listCMD(Player player) {
        // Makes sure the player is in a party.
        if(plugin.partyManager().getParty(player) == null) {
            ChatUtils.chat(player, "&cError &8» &cYou are not in a party! /party create.");
            return;
        }

        // Gets the player's party.
        Party party = plugin.partyManager().getParty(player);

        // Gets all the party members and their roles.
        String leader = "";
        List<String> moderators = new ArrayList<>();
        List<String> members = new ArrayList<>();
        for(Player member : party.getMembers()) {
            switch (party.getRank(member)) {
                case LEADER:
                    leader = member.getName();
                    break;
                case MODERATOR:
                    moderators.add(member.getName());
                    break;
                case MEMBER:
                    members.add(member.getName());
                    break;
            }
        }

        // Displays the list
        ChatUtils.chat(player, "&8&m+-----------------------***-----------------------+");
        ChatUtils.centeredChat(player, "&a&lParty Members");
        ChatUtils.chat(player, "");
        ChatUtils.chat(player, "&aLeader &7» &f" + leader);

        // Only show moderators if there are any.
        if(moderators.size() > 0) {
            ChatUtils.chat(player, "&aModerators &7[" + moderators.size() + "] » &f" + org.apache.commons.lang.StringUtils.join(moderators, ", "));
        }

        ChatUtils.chat(player, "&aMembers &7[" + members.size() + "] » &f" + StringUtils.join(members, ", "));
        ChatUtils.chat(player, "");
        ChatUtils.chat(player, "&8&m+-----------------------***-----------------------+");
    }

    /**
     * Runs the /party promote command.
     * @param player Player to promote.
     */
    private void promoteCMD(Player player, String[] args) {
        // Makes sure the player is in a party.
        if(plugin.partyManager().getParty(player) == null) {
            ChatUtils.chat(player, "&cError &8» &cYou are not in a party! /party create.");
            return;
        }

        // Makes sure the player is using the command correctly.
        if(args.length == 1) {
            ChatUtils.chat(player, "&cUsage &8» &c/party promote [player]");
            return;
        }

        // Gets the player's party.
        Party party = plugin.partyManager().getParty(player);

        // Makes sure they have permission.
        if(party.getRank(player) != PartyRank.LEADER) {
            ChatUtils.chat(player, "&cError &8» &cOnly the party leader can promote members!");
            return;
        }

        // Get the player who the player is trying to promote.
        Player target = Bukkit.getPlayer(args[1]);

        // Makes sure the target is in the party.
        if(target == null || !party.getMembers().contains(target)) {
            ChatUtils.chat(player, "&cError &8» &cThat player is not in your party!");
            return;
        }

        // Makes sure the player isn't trying to promote themselves.
        if(target.equals(player)) {
            ChatUtils.chat(player, "&cError &8» &cYou are already the party leader!");
            return;
        }

        // Promotes the player.
        if(party.getRank(target) == PartyRank.MEMBER) {
            party.setRank(target, PartyRank.MODERATOR);
            party.sendMessage("&a&lParty &8» &f" + target.getName() + " &ahas been promoted to moderator.");
        }
        else {
            party.setRank(player, PartyRank.MODERATOR);
            party.setRank(target, PartyRank.LEADER);
            party.sendMessage("&a&lParty &8» &f" + target.getName() + " &ahas been promoted to party leader.");
        }
    }
}