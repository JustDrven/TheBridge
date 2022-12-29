package cz.drven.thebridge.kits;

import cz.drven.thebridge.utils.Colors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class Kit implements Listener {

    private String name;
    private int price;
    private boolean free;
    private KitManager kitManager;
    private Material icon;
    private KitSelectRunnable kitSelectRunnable;
    private boolean def = false;
    private List<Player> players = new ArrayList<Player>();
    private short iconData = 0;
    private KitUnselectRunnable kitUnselectRunnable;

    public Kit(String name, int price, boolean free, Material icon, short iconData) {
        this.name = name;
        this.price = price;
        this.free = free;
        this.icon = icon;
        this.iconData = iconData;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Material getIcon() {
        return icon;
    }

    public short getIconData() {
        return iconData;
    }

    public boolean isFree() {
        return free;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKitSelectRunnable(KitSelectRunnable kitSelectRunnable) {
        this.kitSelectRunnable = kitSelectRunnable;
    }

    public boolean select(Player player) {
        if (players.contains(player)) {
            player.sendMessage(Colors.format("&8[&aTheBridge&8] &cYou already selected this kit!"));
            return false;
        }
        if (kitManager.getKitByPlayer(player) != null) {
            kitManager.getKitByPlayer(player).remove(player);
        }
        if (isFree() || player.hasPermission("midecon.freekits") || player.hasPermission("midecon.admin")) {
            this.add(player);
            player.sendMessage(Colors.format("&7Selected kit &e" + getName()));
            if (kitSelectRunnable != null) {
                kitSelectRunnable.onSelect(player, this);
            }
            return true;
        }
        return false;
    }

    public void add(Player player) {
        if (players.contains(player)) {
            return;
        }
        players.add(player);
    }

    public void remove(Player player) {
        if (players.contains(player)) {
            players.remove(player);
        }
    }

    public void removeAll() {
        players.clear();
    }

    public boolean isDef() {
        return def;
    }
    public List<Player> getPlayers() {
        return players;
    }

    public KitUnselectRunnable getKitUnselectRunnable() {
        return kitUnselectRunnable;
    }

}
