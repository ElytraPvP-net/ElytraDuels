package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
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
                ChatUtils.chat(sender, "&aParty &8» &aParty as been created.");
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

                new PartyRequest(plugin, player, t).open(t);
                party.broadcast("&aParty &8» &f" + t.getName() + " &ahas been invited to the party.");
                break;

            case "disband":
                party.broadcast("&aParty &8» &aParty has been disbanded.");
                plugin.getPartyManager().getParty(player).disband();
                break;
            case "leave":
                if(party == null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are not in a party! /party create.");
                    return;
                }

                party.removePlayer(player);
                party.broadcast("&aParty &8» &f" + player.getName() + " &ahas left the party.");
                break;

            case "chat":
                if(party == null) {
                    ChatUtils.chat(sender, "&cError &8» &cYou are not in a party! /party create.");
                    return;
                }

                if(args.length < 2) {
                    return;
                }

                args[0] = "";
                party.broadcast("&aParty &8» &f" + player.getName() + "&8: &a" + StringUtils.join(args, " "));

                break;
        }
    }

    private class PartyRequest extends CustomGUI {
        private final ElytraDuels plugin;
        private final Player sender;
        private final Player target;
        private boolean denied;


        public PartyRequest(ElytraDuels plugin, Player sender, Player target) {
            super(9, "Party Request - " + sender.getName());
            this.plugin = plugin;
            denied = false;
            this.sender = sender;
            this.target = target;

            ItemStack accept = new ItemBuilder(Material.EMERALD_BLOCK)
                    .setDisplayName("&aAccept")
                    .build();
            int[] acceptSlots = {0,1,2,3};

            for(int i : acceptSlots) {
                setItem(i, accept, (p, a) -> accept());
            }

            ItemStack deny = new ItemBuilder(Material.REDSTONE_BLOCK)
                    .setDisplayName("&cDecline")
                    .build();
            int[] denySlots = {5,6,7,8};

            for(int i : denySlots) {
                setItem(i, deny, (p, a) -> deny());
            }
        }

        private void accept() {
            Party party = plugin.getPartyManager().getParty(sender);
            party.addPlayer(target);
            party.broadcast("&aParty &8» &f" + target.getName() + " &ahas joined the party.");
            denied = true;
            target.closeInventory();
        }

        private void deny() {
            if(denied) {
                return;
            }

            denied = true;
            target.closeInventory();
            Party party = plugin.getPartyManager().getParty(sender);
            party.broadcast("&aParty &8» &f" + target.getName() + " &ahas declined the invitation.");
        }

        @Override
        public void onClose(Player p) {
            deny();
        }
    }
}