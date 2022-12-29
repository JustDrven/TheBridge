package cz.drven.thebridge.commands;

import cz.drven.thebridge.enums.GameStatus;
import cz.drven.thebridge.game.GamePlayer;
import cz.drven.thebridge.listeners.ServerListener;
import cz.drven.thebridge.manager.PlayerManager;
import cz.drven.thebridge.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatusCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            GamePlayer gp = PlayerManager.getGamePlayer(p);
            gp.setPlayer(p);
            if (p.hasPermission("midecon.admin")) {
                if (args.length == 0) {
                    p.sendMessage(Colors.format("&8[&aTheBridge&8] &7Use: &e/status show"));
                    p.sendMessage(Colors.format("&8[&aTheBridge&8] &7Use: &e/status set <wait|ingame>"));
                    return false;
                } else if (args.length == 1 && args[0].equalsIgnoreCase("show")) {
                    String motd = ServerListener.motd;
                    String[] parts = motd.split(";");
                    p.sendMessage(Colors.format("&8[&aTheBridge&8] &r"+ parts[0]));
                    p.sendMessage(Colors.format("&8[&aTheBridge&8] &r"+ parts[1]));
                    return false;
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (args[1].equalsIgnoreCase("wait")) {
                        GameStatus.status = GameStatus.WAITING;
                        p.sendMessage(Colors.format("&8[&aTheBridge&8] &7You set &aWaiting..."));
                    } else if (args[1].equalsIgnoreCase("ingame")) {
                        GameStatus.status = GameStatus.INGAME;
                        p.sendMessage(Colors.format("&8[&aTheBridge&8] &7You set &cInGame"));
                    } else {
                        p.sendMessage(Colors.format("&8[&aTheBridge&8] &c&lALLOWED STATUSES"));
                        p.sendMessage(Colors.format("&8[&aTheBridge&8] &7 - &fwait &8(&aWaiting...&8)"));
                        p.sendMessage(Colors.format("&8[&aTheBridge&8] &7 - &fingame &8(&cInGame&8)"));
                        return false;
                    }
                    return false;
                } else {
                    p.sendMessage(Colors.format("&8[&aTheBridge&8] &7Use: &e/status show"));
                    p.sendMessage(Colors.format("&8[&aTheBridge&8] &7Use: &e/status set <gameStatus>"));
                    return false;
                }
            }
        }
        return true;
    }
}
