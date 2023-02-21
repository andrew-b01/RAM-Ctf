package me.average.ramctf;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

public class InvisibleSlime extends JavaPlugin {

    private Slime slime;
    private BukkitRunnable task;

    public void spawnAndGlow(Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        // Spawn an invisible slime at the specified location
        slime = (Slime) world.spawnEntity(location, EntityType.SLIME);
        slime.setSize(2);
        slime.setAI(false);
        slime.setInvulnerable(true);
        slime.setCollidable(false);
        slime.setInvisible(true);
        slime.setCustomName("");
        slime.setCustomNameVisible(false);
        slime.setGlowing(true);
        slime.setSilent(true);

        // Schedule a task to make the slime glow
        task = new BukkitRunnable() {
            @Override
            public void run() {
                slime.setGlowing(true);
            }
        };
        task.runTaskLater(this, 20); // Change the delay (in ticks) as needed
    }

    public void despawn() {
        if (slime != null) {
            slime.remove();
        }
        if (task != null) {
            task.cancel();
        }
    }

}