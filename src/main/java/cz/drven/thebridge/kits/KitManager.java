package cz.drven.thebridge.kits;

import cz.drven.thebridge.Main;
import cz.drven.thebridge.enums.LogType;
import cz.drven.thebridge.utils.Colors;
import cz.drven.thebridge.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class KitManager implements Listener {


    private List<Kit> kits = new ArrayList<Kit>();

    private Main plugin;

    public KitManager(Main plugin) {
        this.plugin = plugin;
    }

    public void openMenu(final Player player) {
        final int invSize = ((this.kits.size() + 9) / 9) * 9;
        final Inventory inv = Bukkit.createInventory(null, invSize, "Select kit");

        int i = 0;
        for (Kit kit : kits) {
            ItemStack itemStack = new ItemStack(kit.getIcon(), 1, kit.getIconData());
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
            List<String> lore = new ArrayList<>();
            lore.add(Colors.format("&8---------------"));
            if (getKitByPlayer(player) != null && getKitByPlayer(player).equals(kit)) {
                itemMeta.setDisplayName(Colors.format("&a" + kit.getName()));
                lore.add(Colors.format("&e&lSelected!"));
                lore.add(Colors.format("&7(Click to unselect)"));
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
            } else {
                itemMeta.setDisplayName(Colors.format("&c" + kit.getName()));
                if (kit.isFree() || player.hasPermission("midecon.freekits")) {
                    lore.add(Colors.format("&a&lFree"));
                    lore.add(Colors.format("&a&lUnlocked"));
                } else {
                    lore.add(Colors.format("&c&lLocked"));
                }
                lore.add(Colors.format("&7(Click to select)"));
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inv.setItem(i, itemStack);
            i++;
        }
        player.openInventory(inv);
    }

    public void registerKit(Kit kit) {
        if (getKitByName(kit.getName()) != null) {
            Logger.log("ClassKit " + kit.getName() + " is already exist this name!", LogType.ERROR, "KITS");
            return;
        }
        kits.add(kit);
        plugin.getServer().getPluginManager().registerEvents(kit, plugin);
    }

    public Kit getKitByName(String name) {
        for (Kit k : kits) {
            if (k.getName().equals(name)) {
                return k;
            }
        }
        return null;
    }

    public Kit getKitByPlayer(Player player) {
        for (Kit kit : kits) {
            if (kit.getPlayers().contains(player)) {
                return kit;
            }
        }
        return null;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        if (e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("Select kit")) {
            e.setCancelled(true);
            final Player player = (Player) e.getWhoClicked();
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem == null) {
                return;
            }
            if (currentItem.getType() == Material.AIR) {
                return;
            }
            if (!currentItem.hasItemMeta()) {
                return;
            }
            if (!currentItem.getItemMeta().hasDisplayName()) {
                return;
            }
            String kitName = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName());
            if (getKitByName(kitName) != null) {
                final Kit kit = getKitByName(kitName);
                if (getKitByPlayer(player) != null && getKitByPlayer(player).equals(kit)) {
                    if (!getKitByPlayer(player).isDef()) {
                        kit.remove(player);
                        if (kit.getKitUnselectRunnable() != null) {
                            kit.getKitUnselectRunnable().unselect(kit, player);
                        }
                        player.sendMessage(Colors.format("&cUnselected kit §e" + kit.getName()));
                    }
                    player.closeInventory();
                    return;
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        kit.select(player);
                    }
                }.runTaskAsynchronously(plugin);
                player.closeInventory();
                return;
            }
            return;
        }
    }

}
