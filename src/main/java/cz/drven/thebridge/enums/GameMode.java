package cz.drven.thebridge.enums;

public enum GameMode {

    SOLO(1), DOUBLE(2), TRIPLE(3);

    private int maxPlayersPerTeam;

    GameMode(int i) {
        this.maxPlayersPerTeam = i;
    }

    public int getMaxPlayersPerTeam() {
        return maxPlayersPerTeam;
    }
}