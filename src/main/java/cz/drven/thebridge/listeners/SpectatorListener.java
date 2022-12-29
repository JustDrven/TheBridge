package cz.drven.thebridge.listeners;

import cz.drven.thebridge.enums.GameStatus;
import cz.drven.thebridge.game.GamePlayer;
import cz.drven.thebridge.manager.PlayerManager;
import cz.drven.thebridge.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpectatorListener implements Listener {

    @EventHandler
    public void onSpectAdd(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        GamePlayer gamePlayer = PlayerManager.getGamePlayer(p);
        gamePlayer.setPlayer(p);

        if (GameStatus.status == GameStatus.INGAME) {
            if (!gamePlayer.isSpectator()) {
                gamePlayer.setSpectator(true);
            }
        }
    }
    @EventHandler
    public void onSpectJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        GamePlayer gamePlayer = PlayerManager.getGamePlayer(p);
        gamePlayer.setPlayer(p);

        if (gamePlayer.isSpectator()) {
            if (GameStatus.status == GameStatus.INGAME) {
                gamePlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
                gamePlayer.getPlayer().setAllowFlight(true);
                gamePlayer.getPlayer().setHealth(gamePlayer.getPlayer().getMaxHealth());
                Bukkit.getOnlinePlayers().stream().forEach(players -> {
                    players.hidePlayer(gamePlayer.getPlayer());
                });
                gamePlayer.getPlayer().sendMessage(Colors.format("&8[&aTheBridge&8] &7Your mode is &eSpectator"));
            } else {
                gamePlayer.getPlayer().kickPlayer(Colors.format("&8[&aTheBridge&8] &cOnly &9&lVIP &cspectating this server!"));
            }
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (GameStatus.status == GameStatus.WAITING || GameStatus.status == GameStatus.RESTARTING) {
            p.setFoodLevel(20);
            p.setHealth(20.0);
        }
    }

}
