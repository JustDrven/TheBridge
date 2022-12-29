package cz.drven.thebridge.manager;

import cz.drven.thebridge.Main;
import cz.drven.thebridge.enums.GameMode;
import cz.drven.thebridge.enums.GameStatus;
import cz.drven.thebridge.game.GamePlayer;
import cz.drven.thebridge.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class GameManager {

    public static GameMode gameMode = GameMode.SOLO;

    public static String getDisplayName() {
        switch (gameMode) {
            case SOLO:
                return gameMode.getMaxPlayersPerTeam() + "v" + gameMode.getMaxPlayersPerTeam();
            case DOUBLE:
                return gameMode.getMaxPlayersPerTeam() + "v" + gameMode.getMaxPlayersPerTeam();
            case TRIPLE:
                return gameMode.getMaxPlayersPerTeam() + "v" + gameMode.getMaxPlayersPerTeam();
        }
        return "";
    }

    public static void startGame(Player player) {
        GameStatus.status = GameStatus.INGAME;
        Bukkit.getOnlinePlayers().stream().forEach(ps -> {
            ps.sendMessage(Colors.format("&8[&aServer&8] &aGame has started!"));
            ps.sendTitle(Colors.format("&e&l &e&l  &9&l &c&l"), Colors.format("&a&oGame has started!"));
        });
        update(player);
    }

    public static void update(Player player) {
        GamePlayer gamePlayer = PlayerManager.getGamePlayer(player);
        gamePlayer.setPlayer(player);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("Game", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Colors.format("&r &a&lTheBridge &r"));
        try {
            switch (GameStatus.status) {
                case WAITING:
                    Score map = obj.getScore(Colors.format("&fMap: &e"+ Main.getMap()));
                    map.setScore(6);
                    Score score5 = obj.getScore(Colors.format("&c&l &2&l &e&l  &r&l   "));
                    score5.setScore(5);
                    Score score4 = obj.getScore(Colors.format("&fStatus: &aWaiting..."));
                    score4.setScore(4);
                    Score score3 = obj.getScore(Colors.format("&fMode: &e"+ getDisplayName()));
                    score3.setScore(3);
                    Score score2 = obj.getScore(Colors.format("&fTeam: &e"+ gamePlayer.getTeam().toStringName()));
                    score2.setScore(2);
                    Score score1 = obj.getScore(Colors.format("&e&l &8&l &e&l &c&l &f&l"));
                    score1.setScore(1);
                    Score score = obj.getScore(Colors.format("&7      &nmc.midecon.eu&r     "));
                    score.setScore(0);
                    break;
                case INGAME:
                    Score score55 = obj.getScore(Colors.format("&c&l &e&l&l&9 &e&l  &r&l   "));
                    score55.setScore(5);
                    if (gamePlayer.isSpectator()) {
                        Score score44 = obj.getScore(Colors.format("&fMode: &7Spectator"));
                        score44.setScore(4);
                    } else {
                        Score score44 = obj.getScore(Colors.format("&fMode: &aAlive"));
                        score44.setScore(3);
                    }
                    Score score33 = obj.getScore(Colors.format("&fPlayers: &e"+ Bukkit.getOnlinePlayers().size() + "&7/&c"+ Bukkit.getMaxPlayers()));
                    score33.setScore(2);
                    Score score11 = obj.getScore(Colors.format("&e&l &e&l&1&l &e&l &c&l &f&l"));
                    score11.setScore(1);
                    Score score111 = obj.getScore(Colors.format("&7      &nmc.midecon.eu&r     "));
                    score111.setScore(0);
                    break;
                case RESTARTING:
                    Score score66666 = obj.getScore(Colors.format("&e&l &e&l&a&l  &2    &e&l "));
                    score66666.setScore(3);
                    Score score333 = obj.getScore(Colors.format("&fServer restarting in &e2&fs"));
                    score333.setScore(2);
                    Score score11222222 = obj.getScore(Colors.format("&e&l &e&l&e  &e&l &c&l &f&l"));
                    score11222222.setScore(1);
                    Score score111111 = obj.getScore(Colors.format("&7      &nmc.midecon.eu&r     "));
                    score111111.setScore(0);
                    break;
                default:
                    break;
            }
        } catch (Exception exception) { }
        Bukkit.getOnlinePlayers().stream().forEach(players -> {
            players.setScoreboard(board);
        });
    }

}
