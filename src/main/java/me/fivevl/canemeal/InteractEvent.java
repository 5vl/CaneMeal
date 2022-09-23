package me.fivevl.canemeal;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.Random;

public class InteractEvent implements Listener {
    Random random = new Random();
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().getType() != Material.BONE_MEAL || e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.SUGAR_CANE) {
            return;
        }
        Location topBlock = e.getClickedBlock().getLocation();
        while (topBlock.getBlock().getType() == Material.SUGAR_CANE) {
            topBlock.add(0, 1, 0);
        }
        if (topBlock.getBlockY() >= p.getWorld().getMaxHeight()) {
            p.sendActionBar(ChatColor.RED + "Your sugar cane is at the world height limit, which is " + p.getWorld().getMaxHeight() + " blocks!");
            return;
        }
        Block above = topBlock.getBlock();
        if (above.getType() != Material.AIR && above.getType() != Material.SUGAR_CANE) {
            p.sendActionBar(ChatColor.RED + "Your sugar cane cannot grow through blocks!");
            return;
        }
        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
        p.getWorld().playEffect(e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), Effect.BONE_MEAL_USE, 0);
        int height = 0;
        if (random.nextBoolean()) {
            Block newBlock = topBlock.getBlock();
            newBlock.setType(Material.SUGAR_CANE);
            height = newBlock.getY();
        } else {
            for (int i = 0; i < 2; i++) {
                Block newBlock = topBlock.getBlock();
                if (newBlock.getType() != Material.AIR && newBlock.getType() != Material.SUGAR_CANE) {
                    p.sendActionBar(ChatColor.RED + "Your sugar cane cannot grow through blocks!");
                    return;
                }
                newBlock.setType(Material.SUGAR_CANE);
                height = newBlock.getY();
            }
        }
        p.sendActionBar(ChatColor.GREEN + "The top of your sugar cane is now at Y level " + height + "!");
    }
}
