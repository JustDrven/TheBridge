package cz.drven.thebridge;

import cz.drven.thebridge.commands.StartCommand;
import cz.drven.thebridge.commands.StatusCommand;
import cz.drven.thebridge.enums.GameMode;
import cz.drven.thebridge.enums.GameStatus;
import cz.drven.thebridge.enums.LogType;
import cz.drven.thebridge.kits.Kit;
import cz.drven.thebridge.kits.KitManager;
import cz.drven.thebridge.listeners.*;
import cz.drven.thebridge.manager.GameManager;
import cz.drven.thebridge.manager.PlayerManager;
import cz.drven.thebridge.utils.Colors;
import cz.drven.thebridge.utils.Logger;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Drven
 * **/
public final class Main extends JavaPlugin {

    private static Main instance;
    private KitManager kitManager;
    public static Kit jumper = new Kit(Colors.format("Jumper"), 250, false, Material.FEATHER, (short)0);
    public static Kit headler = new Kit(Colors.format("Healer"), 0, true, Material.REDSTONE, (short)0);


    public static Main getInstance() {
        return instance;
    }

    public static int getMaxPlayers() {
        return Main.getInstance().getConfig().getInt("game.settings.max");
    }
    public static String getMap() {
        return Main.getInstance().getConfig().getString("game.settings.map");
    }

    @Override
    public void onEnable() {
        try {
            saveDefaultConfig();
            instance = this;
            GameStatus.status = GameStatus.WAITING;
            GameManager.gameMode = GameMode.SOLO;
            Logger.log("&aInstance was been enabled", LogType.INFO);
        } catch (Exception exception) {
            Logger.log("&cError with instance", LogType.ERROR, "SERVER");
        }
        try {
            registerKitManager();
            getKitManager().registerKit(jumper);
            getKitManager().registerKit(headler);
            Logger.log("&aKits was been loaded!", LogType.INFO);
        } catch (Exception exception) {
            Logger.log("&cError with kits", LogType.ERROR, "KITS");
        }
        try {
            getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
            getServer().getPluginManager().registerEvents(new SpectatorListener(), this);
            getServer().getPluginManager().registerEvents(new ServerListener(), this);
            getServer().getPluginManager().registerEvents(new InventoryListener(), this);
            getServer().getPluginManager().registerEvents(new GameListener(), this);
            getServer().getPluginManager().registerEvents(new KitManager(this), this);
            Logger.log("&aListeners was been loaded!", LogType.INFO);
        } catch (Exception exception) {
            Logger.log("&cError with listeners", LogType.ERROR, "REGISTER");
        }
        try {
            getCommand("start").setExecutor(new StartCommand());
            getCommand("status").setExecutor(new StatusCommand());
            Logger.log("&aCommands was been loaded!", LogType.INFO);
        } catch (Exception exception) {
            Logger.log("&cError with commands", LogType.ERROR, "REGISTER");
        }
        try {
            PlayerManager.clear();
            Logger.log("&aAll world & data was been reset!", LogType.INFO);
        } catch (Exception e) { Logger.log("&cError with reset data & world", LogType.ERROR, "Reset"); }
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public KitManager registerKitManager() {
        if (kitManager != null) {
            return this.kitManager;
        }
        KitManager kitManager = new KitManager(this);
        this.getServer().getPluginManager().registerEvents(kitManager, this);
        return kitManager;
    }

}
