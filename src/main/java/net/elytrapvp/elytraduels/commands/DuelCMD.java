package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
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
            ChatUtils.chat(sender, "&cUsage &8» /duel [player] <kit>");
            return;
        }

        Player p = (Player) sender;
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

        if(args.length < 2) {
            new DuelGUI(plugin, t).open(p);
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

        ChatUtils.chat(p, "&a&l(&7!&a&l) &aDuel request sent.");
        new DuelRequest(p, t, k).open(t);
    }

    private class DuelRequest extends CustomGUI {
        private final Player sender;
        private final Player target;
        private final Kit kit;
        private boolean denied;

        public DuelRequest(Player sender, Player target, Kit kit) {
            super(9, kit.getName() + " Duel Request - " + sender.getName());
            denied = false;
            this.sender = sender;
            this.target = target;
            this.kit = kit;

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
            ChatUtils.chat(sender, "&aDuel request has been accepted.");

            Party senderParty = plugin.getPartyManager().getParty(sender);

            Game game = plugin.getGameManager().createGame(kit, GameType.UNRANKED);

            if(senderParty == null) {
                game.addPlayer(sender);
            }
            else {
                game.addPlayers(senderParty.getPlayers());
            }

            Party targetParty = plugin.getPartyManager().getParty(target);
            if(targetParty == null) {
                game.addPlayer(target);
            }
            else {
                game.addPlayers(targetParty.getPlayers());
            }

            // Remove players from queue.
            plugin.getQueueManager().removePlayer(target);

            denied = true;
            game.start();
        }

        private void deny() {
            if(denied) {
                return;
            }

            denied = true;
            target.closeInventory();
            ChatUtils.chat(sender, "&cDuel request has been declined.");
        }

        @Override
        public void onClose(Player p) {
            deny();
        }
    }
}