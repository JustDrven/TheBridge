package cz.drven.thebridge.listeners;

import cz.drven.thebridge.enums.GameStatus;
import cz.drven.thebridge.utils.Colors;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListener implements Listener {

    public static String motd = Colors.format("&7Status: &eLoading...&r;&7Map: &eLoading...");

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        switch (GameStatus.status) {
            case WAITING:
                motd = Colors.format("&7Status: &aWaiting...&r;&7Map: &eVoting...");
                e.setMotd(Colors.format("&7Status: &aWaiting...&r;&7Map: &eVoting..."));
                break;
            case INGAME:
                motd = Colors.format("&7Status: &cInGame&r;&7Map: &e--/--");
                e.setMotd(Colors.format("&7Status: &cInGame&r;&7Map: &e--/--"));
                break;
            case RESTARTING:
                motd = Colors.format("&7Status: &cStopped!&r;&7Map: &eReseting...");
                e.setMotd(Colors.format("&7Status: &cStopped!&r;&7Map: &eReseting..."));
                break;
        }
    }

}
