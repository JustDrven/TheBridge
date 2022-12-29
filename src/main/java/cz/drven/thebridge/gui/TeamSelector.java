package cz.drven.thebridge.gui;

import cz.drven.thebridge.enums.TeamType;
import cz.drven.thebridge.game.GamePlayer;
import cz.drven.thebridge.manager.PlayerManager;
import cz.drven.thebridge.utils.Colors;
import cz.drven.thebridge.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class TeamSelector {

    public static void open(Player player) {
        GamePlayer gp = PlayerManager.getGamePlayer(player);
        gp.setPlayer(player);
        Inventory inv = Bukkit.createInventory(null, 27, "Team Selector");

        for (int i = 0; i < 27; i++) {
            if (i < 10 || i > 16) {
                inv.setItem(i, ItemBuilder.setItem(Material.STAINED_GLASS_PANE, "&c", 1, null, (byte)7));
            }
        }

        inv.setItem(10, ItemBuilder.setItem(Material.WOOL, "&cRed", 1, Arrays.asList(Colors.format("&7Players: &a0&8/&c0"), Colors.format("&8+-----------+"), Colors.format("&e&nSelect this team!")), (byte) 14));
        inv.setItem(10, ItemBuilder.setItem(Material.WOOL, "&9Blue", 1, Arrays.asList(Colors.format("&7Players: &a0&8/&c0"), Colors.format("&8+-----------+"), Colors.format("&e&nSelect this team!")), (byte) 11));

        player.openInventory(inv);
    }

}
