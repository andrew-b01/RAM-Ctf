package me.average.ramctf;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;
import me.average.ramctf.Tools;

public class PlatformSpawner {

    static void Spawn(int x, int y, int z, Player sender, Material material, boolean spawn) {
        if (spawn) {
            World world = sender.getWorld();
            for (int i = 1; i < 16; i++) {
                for (int o = 0; o < 15; o++) {
                    world.getBlockAt(x + o, y, z + i).setType(material);
                    
                }
            }
        }
    }
}
