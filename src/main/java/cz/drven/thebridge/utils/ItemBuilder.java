package cz.drven.thebridge.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    public static ItemStack setItem(Material material, String displayname, int amount, List<String> lores, Byte data) {
        ItemStack is = new ItemStack(material, amount, data);
        ItemMeta ismeta = is.getItemMeta();

        ismeta.setDisplayName(Colors.format(displayname));
        ismeta.setLore(lores);

        is.setItemMeta(ismeta);

        return is;
    }

}
