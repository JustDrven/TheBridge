package cz.drven.thebridge.listeners;

import cz.drven.thebridge.Main;
import cz.drven.thebridge.enums.GameStatus;
import cz.drven.thebridge.game.GamePlayer;
import cz.drven.thebridge.manager.GameManager;
import cz.drven.thebridge.manager.PlayerManager;
import cz.drven.thebridge.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        GamePlayer gamePlayer = PlayerManager.getGamePlayer(p);
        gamePlayer.setPlayer(p);

        if (GameStatus.status == GameStatus.WAITING) {
            Bukkit.getOnlinePlayers().stream().forEach(players -> {
                players.sendMessage(Colors.format("&8[&aTheBridge&8] &7Player&e "+ gamePlayer.getPlayer().getName() + "&7 has joined the game. &c("+ Bukkit.getOnlinePlayers().size() +")"));
            });
        }


        new BukkitRunnable() {

            @Override
            public void run() {

                p.setFoodLevel(20);
                p.setHealth(20.0);

                GameManager.update(p);

                gamePlayer.InventoryClear();

            }
        }.runTaskLater(Main.getInstance(), 20L);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        GamePlayer gamePlayer = PlayerManager.getGamePlayer(p);
        gamePlayer.setPlayer(p);

        if (GameStatus.status == GameStatus.WAITING) {
            Bukkit.getOnlinePlayers().stream().forEach(players -> {
                players.sendMessage(Colors.format("&8[&aTheBridge&8] &7Player&e "+ gamePlayer.getPlayer().getName() + "&7 left the game. &c("+ Bukkit.getOnlinePlayers().size() +")"));
            });
        }


    }

}
