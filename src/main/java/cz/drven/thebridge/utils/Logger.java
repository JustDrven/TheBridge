package cz.drven.thebridge.utils;

import cz.drven.thebridge.Main;
import cz.drven.thebridge.enums.LogType;

public class Logger {

    public static void log(String text, LogType type) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(Colors.format("&8(&e"+ type.name() +"&8) &7- &f"+ text));
    }

    public static void log(String text, LogType type, String prefix) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(Colors.format("&8[&a"+ prefix +"&8]&8(&e"+ type.name() +"&8) &7- &f"+ text));
    }

}
