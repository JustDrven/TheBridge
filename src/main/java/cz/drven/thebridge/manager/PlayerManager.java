package cz.drven.thebridge.manager;

import cz.drven.thebridge.enums.TeamType;
import cz.drven.thebridge.game.GamePlayer;
import cz.drven.thebridge.utils.Colors;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    public static HashMap<UUID, GamePlayer> playerMap = new HashMap<UUID, GamePlayer>();

    public static GamePlayer getGamePlayer(Player p) {
        GamePlayer gamePlayer = playerMap.get(p.getUniqueId());
        if (gamePlayer == null) {
            GamePlayer player = new GamePlayer(p);
            playerMap.put(p.getUniqueId(), player);
            return player;
        }
        return gamePlayer;
    }

    public static void selectTeam(Player player, TeamType type) {
        GamePlayer gp = getGamePlayer(player);
        gp.setPlayer(player);
        if (gp.getTeam() == type) {
            gp.getPlayer().sendMessage(Colors.format("&8[&aTheBridge&8] &cYou're already select this team!"));
            return;
        }
        switch (gp.getTeam()) {
            case RED:
                gp.setTeam(TeamType.RED);
                gp.getPlayer().sendMessage(Colors.format("&8[&aTheBridge&8] &7Select team: &e"+ gp.getTeam().toStringName()));
                break;
            case BLUE:
                gp.setTeam(TeamType.BLUE);
                gp.getPlayer().sendMessage(Colors.format("&8[&aTheBridge&8] &7Select team: &e"+ gp.getTeam().toStringName()));
                break;
        }
    }

    public static void clear() {
        playerMap.clear();
    }

}
