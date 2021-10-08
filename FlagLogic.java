package me.average.ramctf;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class FlagLogic implements Listener {
    static int redFlagX;
    static int blueFlagX;
    static int flagZ;
    static int flagY;
    static boolean once;
    static void setredX(int x){
        redFlagX = x;
    }

    static void setblueX(int x){
        blueFlagX = x;
    }

    static void setZ(int z){
        flagZ = z;
    }

    static void setY(int y){
        flagY = y;
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Setup setup = new Setup();
        double playerx = location.getX();
        double playerz = location.getZ();
        double playery = location.getY();
        World world = player.getWorld();

            if(playerx >= redFlagX-2 && playerx <= redFlagX+2 && playerz >= flagZ - 2 && playerz <= flagZ + 2 && playery <= flagY + 2 && playery >= flagY -2){
                if(once){
                    world.playEffect(location, Effect.BLAZE_SHOOT,0);
                    Bukkit.broadcastMessage(ChatColor.AQUA + "Red flag captured");
                    once = !once;
                }
                else{once = !once;Teams.addRed();}

            }
            else if(playerx >= blueFlagX-2 && playerx <= blueFlagX+2 && playerz >= flagZ - 2 && playerz <= flagZ + 2 && playery <= flagY + 2 && playery >= flagY -2){
                if(once){
                    world.playEffect(location, Effect.BLAZE_SHOOT,0);
                    Bukkit.broadcastMessage(ChatColor.AQUA + "Blue flag captured");
                    once = !once;
                }
                else{once = !once;Teams.addBlue();}
            }

    }

}
