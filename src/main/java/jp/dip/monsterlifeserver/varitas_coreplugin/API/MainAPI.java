package jp.dip.monsterlifeserver.varitas_coreplugin.API;

import jp.dip.monsterlifeserver.varitas_coreplugin.VARITAS_CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jp.dip.monsterlifeserver.varitas_coreplugin.VARITAS_CorePlugin.config;
import static jp.dip.monsterlifeserver.varitas_coreplugin.VARITAS_CorePlugin.plugin;

public class MainAPI {

    private static ArrayList<String> charaName;

    public static boolean debugMode;

    private static String inventoryName;

    public static String getExpGUIName() {
        return inventoryName;
    }

    public static void loadPlugin(Plugin plugin) {
        VARITAS_CorePlugin.plugin = plugin;
        config = new ConfigAPI(plugin);

        config.saveDefaultConfig();

        debugMode = config.getConfig().getBoolean("debugMode");
        inventoryName = config.getConfig().getString("InventoryName");

        loadCharaName();
    }

    public static int getExpGUIPage(Player player) {
        String stnum = player.getOpenInventory().getItem(34).getItemMeta().getDisplayName().split(" ")[1];
        return Integer.parseInt(stnum);
    }

    public static Inventory getExpGUI(Player player, int page) {
        Inventory inventory = Bukkit.createInventory(null, 36, getExpGUIName());
        ItemStack item;
        ItemMeta meta;
        List<String> lores;
        String name;
        int charaNumber = (page - 1) * 27 + 1;
        for (int i = 0; i < 27; i++) {
            item = new ItemStack(Material.BARRIER);
            meta = item.getItemMeta();
            name = "このキャラは所持していません。";
            meta.setDisplayName(name);
            if (charaName == null) loadCharaName();
            if (charaName.size() < charaNumber) {
                item.setItemMeta(meta);
                inventory.setItem(i, item);
                if (debugMode) Bukkit.broadcastMessage(ChatColor.RED + String.valueOf(charaNumber));
            } else {
                if (debugMode) Bukkit.broadcastMessage(ChatColor.GREEN + String.valueOf(charaNumber));
                lores = new ArrayList<>();
                if (getPlayerExp(player, charaNumber) != null) lores.add("経験値: " + Objects.requireNonNull(getPlayerExp(player, charaNumber)).getScore() + "exp");
                if (getPlayerLvl(player, charaNumber) != null) lores.add("レベル: Lv" + Objects.requireNonNull(getPlayerLvl(player, charaNumber)).getScore());
                if (lores.size() > 0) {
                    item = new ItemStack(Material.PAPER);
                    meta = item.getItemMeta();
                    name = charaName.get(charaNumber - 1);
                    meta.setDisplayName(name);
                    meta.setLore(lores);
                }
                item.setItemMeta(meta);
                inventory.setItem(i, item);
                if (debugMode) Bukkit.broadcastMessage(name);
            }
            charaNumber++;
        }
        item = new ItemStack(Material.ARROW);
        meta = item.getItemMeta();
        meta.setDisplayName("次");
        item.setItemMeta(meta);
        inventory.setItem(35, item);
        meta.setDisplayName("前");
        item.setItemMeta(meta);
        inventory.setItem(33, item);
        item = new ItemStack(Material.MAP);
        meta = item.getItemMeta();
        meta.setDisplayName("ページ: " + page);
        item.setItemMeta(meta);
        inventory.setItem(34, item);
        return inventory;
    }

    private static void loadCharaName() {
        if (config == null) loadPlugin(plugin);
        charaName = new ArrayList<>();
        boolean a = true;
        int i = 0;
        while (a) {
            i++;
            if (config.getConfig().getString("chara." + i + ".Name") == null) {
                a = false;
            } else {
                charaName.add(config.getConfig().getString("chara." + i + ".Name"));
            }
        }
    }

    public static Score getPlayerExp(Player player, int charaNumber) {
        if (player.getScoreboard().getObjective(charaNumber + "exp") == null) return null;
        return player.getScoreboard().getObjective(charaNumber + "exp").getScore(player.getName());
    }

    public static Score getPlayerLvl(Player player, int charaNumber) {
        if (player.getScoreboard().getObjective(charaNumber + "lvl") == null) return null;
        return player.getScoreboard().getObjective(charaNumber + "lvl").getScore(player.getName());
    }

}
