package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
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
            ChatUtils.chat(sender, "&a  /party create");
            ChatUtils.chat(sender, "&a  /party disband");
            ChatUtils.chat(sender, "&a  /party invite [player]");
            ChatUtils.chat(sender, "&a  /party leave");
            ChatUtils.chat(sender, "&a&m---------------------------------------------------");
            return;
        }

        Player p = (Player) sender;

        switch(args[0]) {
            case "create":
                if(plugin.getPartyManager().getParty(p) != null) {
                    ChatUtils.chat(sender, "&c&l(&7!&c&l) &cYou are already in a party.");
                    return;
                }

                plugin.getPartyManager().createParty(p);
                ChatUtils.chat(sender, "&a&l(&7!&a&l) &aParty as been created.");
                ItemUtils.givePartyItems(plugin.getPartyManager(), p);
                break;
            case "help":
                ChatUtils.chat(sender, "&a&m---------------------------------------------------");
                ChatUtils.centeredChat(sender, "&a&lParty Commands");
                ChatUtils.chat(sender, "&a  /party create");
                ChatUtils.chat(sender, "&a  /party disband");
                ChatUtils.chat(sender, "&a  /party invite [player]");
                ChatUtils.chat(sender, "&a  /party leave");
                ChatUtils.chat(sender, "&a&m---------------------------------------------------");
                break;

            case "invite":
                if(args.length != 2) {
                    ChatUtils.chat(sender, "&c&l(&7!&c&l) &cUsage: /party invite [player]");
                    return;
                }

                Player t = Bukkit.getPlayer(args[1]);
                if(t == null) {
                    ChatUtils.chat(sender, "&c&l(&7!&c&l) &cThat person is not online.");
                    return;
                }

                if(t.equals(p)) {
                    ChatUtils.chat(sender, "&c&l(&7!&c&l) &cYou cannot invite yourself.");
                    return;
                }

                if(plugin.getPartyManager().getParty(t) != null) {
                    ChatUtils.chat(sender, "&c&l(&7!&c&l) &cThey are already in a party.");
                    return;
                }

                if(plugin.getPartyManager().getParty(p) == null) {
                    ChatUtils.chat(sender, "&c&l(&7!&c&l) &cYou are not in a party! /party create.");
                    return;
                }

                new PartyRequest(plugin, p, t).open(t);
                break;

            case "disband":
                ChatUtils.chat(sender, "&a&l(&7!&a&l) &aParty has been disbanded.");
                plugin.getPartyManager().getParty(p).disband();
                break;
            case "leave":
                ChatUtils.chat(sender, "");

                if(plugin.getPartyManager().getParty(p) == null) {
                    ChatUtils.chat(sender, "&c&l(&7!&c&l) &cYou are not in a party! /party create.");
                    return;
                }

                plugin.getPartyManager().getParty(p).removePlayer(p);
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
            ChatUtils.chat(sender, "&aParty request has been accepted.");
            Party party = plugin.getPartyManager().getParty(sender);
            party.addPlayer(target);
            denied = true;
            target.closeInventory();
        }

        private void deny() {
            if(denied) {
                return;
            }

            denied = true;
            target.closeInventory();
            ChatUtils.chat(sender, "&cParty request has been declined.");
        }

        @Override
        public void onClose(Player p) {
            deny();
        }
    }
}