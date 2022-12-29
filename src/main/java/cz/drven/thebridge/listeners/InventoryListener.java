package cz.drven.thebridge.listeners;

import cz.drven.thebridge.Main;
import cz.drven.thebridge.enums.GameStatus;
import cz.drven.thebridge.enums.TeamType;
import cz.drven.thebridge.game.GamePlayer;
import cz.drven.thebridge.gui.TeamSelector;
import cz.drven.thebridge.kits.KitManager;
import cz.drven.thebridge.manager.GameManager;
import cz.drven.thebridge.manager.PlayerManager;
import cz.drven.thebridge.utils.Colors;
import cz.drven.thebridge.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListener implements Listener {

    @EventHandler
    public void ongiveItems(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        GamePlayer gp = PlayerManager.getGamePlayer(p);
        gp.setPlayer(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                giveItems(p);
            }
        }.runTaskLater(Main.getInstance(), 30L);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        GamePlayer gp = PlayerManager.getGamePlayer(p);
        new BukkitRunnable() {
            @Override
            public void run() {

                p.spigot().respawn();

                gp.setSpectator(true);
                GameManager.update(p);

                giveItems(p);
            }
        }.runTaskLater(Main.getInstance(), 2L);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        GamePlayer gp = PlayerManager.getGamePlayer(p);
        gp.setPlayer(p);
        if (GameStatus.status == GameStatus.WAITING) {
            e.setCancelled(true);
        }
        if (e.getInventory().getTitle().equalsIgnoreCase(Colors.format("Team Selector"))) {
            if (e.getCurrentItem().getType().equals(Material.WOOL)) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Colors.format("&cRed"))) {
                    PlayerManager.selectTeam(gp.getPlayer(), TeamType.RED);
                    p.closeInventory();
                    e.setCancelled(true);
                    return;
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Colors.format("&9Blue"))) {
                    PlayerManager.selectTeam(gp.getPlayer(), TeamType.BLUE);
                    p.closeInventory();
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onClickItem(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType().equals(Material.WOOL) && p.getItemInHand().getItemMeta().getDisplayName().equals(Colors.format("&7Team Selector"))) {
                TeamSelector.open(p);
                e.setCancelled(true);
                return;
            }
            if (p.getItemInHand().getType().equals(Material.MAGMA_CREAM) && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Colors.format("&eKit Selector"))) {
                Main.getInstance().getKitManager().openMenu(p);
                e.setCancelled(true);
                return;
            }
        }


    }

    /*
    *
    * Methods
    *
    * */

    public void giveItems(Player p) {
        GamePlayer gp = PlayerManager.getGamePlayer(p);
        gp.setPlayer(p);

        gp.InventoryClear();

        if (GameStatus.status == GameStatus.WAITING) {
            p.getInventory().setItem(0, ItemBuilder.setItem(Material.WOOL, "&7Team Selector", 1, null, (byte)0));
            p.getInventory().setItem(1, ItemBuilder.setItem(Material.MAGMA_CREAM, "&eKit Selector", 1, null, (byte)0));
            p.getInventory().setItem(8, ItemBuilder.setItem(Material.BED, "&cLeave the game!", 1, null, (byte)0));
        }
        if (GameStatus.status == GameStatus.INGAME && gp.isSpectator()) {
            p.getInventory().setItem(0, ItemBuilder.setItem(Material.COMPASS, "&7Teleporter", 1, null, (byte)0));
            p.getInventory().setItem(1, ItemBuilder.setItem(Material.FEATHER, "&eInvspector", 1, null, (byte)0));
            p.getInventory().setItem(8, ItemBuilder.setItem(Material.BED, "&cLeave the game!", 1, null, (byte)0));
        }
    }

}
