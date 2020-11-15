package jp.dip.monsterlifeserver.varitas_coreplugin.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static jp.dip.monsterlifeserver.varitas_coreplugin.API.MainAPI.*;

public class onInventoryClick implements Listener {

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent e) {
        if (e.getView() == null) return;
        if (e.getInventory() == null) return;
        if (e.getCurrentItem() == null) return;
        Player player = (Player) e.getWhoClicked();
        InventoryView inventoryView = e.getView();
        if (inventoryView.getTitle().equals(getExpGUIName())) {
            e.setCancelled(true);
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("次");
            item.setItemMeta(meta);
            if (e.getCurrentItem().isSimilar(item)) {
                player.openInventory(getExpGUI(player, getExpGUIPage(player) + 1));
                return;
            }
            meta.setDisplayName("前");
            item.setItemMeta(meta);
            if (e.getCurrentItem().isSimilar(item)) {
                if (getExpGUIPage(player) - 1 <= 0) return;
                player.openInventory( getExpGUI(player, getExpGUIPage(player) - 1));
            }
        }

    }

}