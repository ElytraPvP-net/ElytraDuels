package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;

public class SettingsGUI extends CustomGUI {

    public SettingsGUI(ElytraDuels plugin) {
        super(9, "Settings");

        setItem(4, new ItemBuilder(Material.ANVIL).setDisplayName("&a&lKit Editor").build(), (p, a) -> new KitEditorGUI(plugin, p).open(p));
    }

}
