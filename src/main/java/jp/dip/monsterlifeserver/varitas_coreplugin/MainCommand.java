package jp.dip.monsterlifeserver.varitas_coreplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static jp.dip.monsterlifeserver.varitas_coreplugin.API.MainAPI.*;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().config("コンソールから実行できません。");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("gexp")) {
            if (args.length == 0) {
                player.openInventory(getExpGUI(player, 1));
                return true;
            }
        }
        else if (cmd.getName().equalsIgnoreCase("test")) {
            if (getPlayerExp(player, Integer.parseInt(args[0])) != null) {
                Bukkit.broadcastMessage("exp " + getPlayerExp(player, Integer.parseInt(args[0])).getScore());
            }
            if (getPlayerLvl(player, Integer.parseInt(args[0])) != null) {
                Bukkit.broadcastMessage("lvl " + getPlayerLvl(player, Integer.parseInt(args[0])).getScore());
            }
        }
        return false;
    }
}