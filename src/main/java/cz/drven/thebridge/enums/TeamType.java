package cz.drven.thebridge.enums;

public enum TeamType {

    NONE("None"), RED("Red"), BLUE("Blue");

    private String team;

    TeamType(String teamName) {
        this.team = teamName;
    }

    public String toStringName() {
        return this.team;
    }

}
