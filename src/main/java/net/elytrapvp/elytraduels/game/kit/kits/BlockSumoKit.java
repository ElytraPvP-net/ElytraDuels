package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.MathUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import java.util.Random;

public class BlockSumoKit extends Kit {

    public BlockSumoKit(ElytraDuels plugin) {
        super(plugin, "Block Sumo");
        setIconMaterial(Material.WOOL);
        setDoDamage(false);
        setGameMode(GameMode.SURVIVAL);

        ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                .setUnbreakable(true)
                .build();

        addItem(39, helmet);
        addItem(38, chestplate);
        addItem(37, leggings);
        addItem(36, boots);

        addItem(0,new ItemBuilder(Material.WOOL, 64).build());
        addItem(1, new ItemBuilder(Material.SHEARS).setUnbreakable(true).build());
    }

    @Override
    public void onBlockPlace(Game game, BlockPlaceEvent event) {
        Player player = event.getPlayer();
        player.getInventory().getItemInHand().setAmount(64);

        if(MathUtils.distance(game.getArena().getCenter(), event.getBlock().getLocation()) > 22) {
            ChatUtils.chat(player, "&cError &8» &cYou cannot place blocks here!");
            event.setCancelled(true);
            return;
        }

        DyeColor[] colors = DyeColor.values();
        Random random = new Random();

        Block block = event.getBlock();
        BlockState state = block.getState();
        Wool w = (Wool) state.getData();
        w.setColor(colors[random.nextInt(colors.length)]);
        state.setData(w);
        state.update();
    }
}
