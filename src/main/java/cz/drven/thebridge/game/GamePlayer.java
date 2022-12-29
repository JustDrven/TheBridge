package cz.drven.thebridge.game;

import cz.drven.thebridge.enums.TeamType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GamePlayer {

    private Player player;
    private boolean spectator = false;
    private TeamType teamType = TeamType.NONE;

    public GamePlayer(Player player) {
        this.player = player;
    }

    /**
     * @return: set player to online player
     * **/
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return: set player to spectator mod
     * **/
    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    /**
     * @return: if player is spectator
     * **/
    public boolean isSpectator() {
        return spectator;
    }

    /**
     * @return: return online player
     * **/
    public Player getPlayer() {
        return player;
    }

    public void InventoryClear() {
        this.player.getInventory().setArmorContents((ItemStack[]) null);
        this.player.getInventory().clear();
    }

    public void setTeam(TeamType type) {
        this.teamType = type;
    }

    public TeamType getTeam() {
        return teamType;
    }
}
