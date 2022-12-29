package cz.drven.thebridge.listeners;

import cz.drven.thebridge.Main;
import cz.drven.thebridge.enums.GameStatus;
import cz.drven.thebridge.events.GameEnd;
import cz.drven.thebridge.events.GameStart;
import cz.drven.thebridge.manager.GameManager;
import cz.drven.thebridge.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GameListener implements Listener {

    public static int time = 10;
    public static int id = 0;

    @EventHandler
    public void onGameEnd(GameEnd e) {
        e.getWinner().sendTitle(Colors.format("&e&lVictory"), Colors.format("&e&l  "));
        e.getLoser().sendTitle(Colors.format("&c&lGame Over"), Colors.format("&e&l &e&l &9&l "));
        Bukkit.getOnlinePlayers().stream().forEach(players -> {
            players.sendMessage("&r&l   ");
            players.sendMessage("&r&l  ");
            players.sendMessage("&r&l     ");
            players.sendMessage("&r&l     ");
            players.sendMessage("&r&l   ");
            players.sendMessage("&r&l   ");
            players.sendMessage("&8==============================");
            players.sendMessage("&7Winner: &e"+ e.getWinner().getName());
            players.sendMessage("&7Loser: &c"+ e.getLoser().getName());
            players.sendMessage("&8==============================");
        });
        GameStatus.status = GameStatus.RESTARTING;
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();

            }
        }.runTaskLater(Main.getInstance(), 400L);
    }

    @EventHandler
    public void onGameStart(GameStart e) {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                time--;
                if (time <= 5) {
                    Bukkit.getOnlinePlayers().stream().forEach(players -> {
                        players.closeInventory();
                    });
                }
                if (time <= 5 || time == 15 || time >= 20 || time == 10) {
                    Bukkit.broadcastMessage(Colors.format("&8[&aTheBridge&8] &7Game start in: &e" + time + " &7second(s)"));
                }
                if (time == 0) {
                    Bukkit.getOnlinePlayers().stream().forEach(ps -> {
                        GameManager.startGame(ps);
                    });

                    Bukkit.getScheduler().cancelTask(id);
                }
            }
        }, 0L,20L);
    }

    @EventHandler
    public void onJoined(PlayerJoinEvent e) {
        if (Bukkit.getOnlinePlayers().size() >= Main.getMaxPlayers()) {
            Bukkit.getServer().getPluginManager().callEvent(new GameStart());
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Player loser = (Player) e.getEntity();
        Player winner = e.getEntity().getKiller();
        Bukkit.getServer().getPluginManager().callEvent(new GameEnd(winner, loser));
    }

}
