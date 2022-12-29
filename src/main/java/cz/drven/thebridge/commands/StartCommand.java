package cz.drven.thebridge.commands;

import cz.drven.thebridge.manager.GameManager;
import cz.drven.thebridge.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("midecon.admin")) {
                GameManager.startGame(p);
                return false;
            } else {
                p.sendMessage(Colors.format("&8[&aCmd&8] &cYou're not allowed to do this!"));
            }
        }
        return true;
    }
}
