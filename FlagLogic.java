package me.average.ramctf;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class FlagLogic implements Listener {
    static int redFlagX;
    static int blueFlagX;
    static int flagZ;
    static int flagY;
    static boolean once;
    boolean flagCaptured;
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
        Setup setup = new Setup();
        PlatformSpawner.Spawn(setup.bluex, setup.y, setup.z, event.getPlayer(), Material.BLUE_WOOL, true);
        PlatformSpawner.Spawn(setup.redx, setup.y, setup.z, event.getPlayer(), Material.RED_WOOL, true);
        Player player = event.getPlayer();
        Location location = player.getLocation();
        double playerx = location.getX();
        double playerz = location.getZ();
        double playery = location.getY();
        World world = player.getWorld();
        Teams teams = new Teams();

            if(playerx >= redFlagX-2 && playerx <= redFlagX+2 && playerz >= flagZ - 2 && playerz <= flagZ + 2 && playery <= flagY + 2 && playery >= flagY -2 && Teams.blue.hasPlayer(event.getPlayer())){
                if(once){
                    world.playEffect(location, Effect.BLAZE_SHOOT,0);
                    Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.RED + "Red " + ChatColor.AQUA + "flag captured by " + ChatColor.RED + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                    flagCaptured = true;
                    once = !once;
                }
                else{once = !once;teams.addRed();}

            }
            else if(playerx >= blueFlagX-2 && playerx <= blueFlagX+2 && playerz >= flagZ - 2 && playerz <= flagZ + 2 && playery <= flagY + 2 && playery >= flagY -2 && Teams.red.hasPlayer(event.getPlayer())){
                if(once){
                    world.playEffect(location, Effect.BLAZE_SHOOT,0);
                    Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.BLUE + "Blue " + ChatColor.AQUA + "flag captured by " + ChatColor.BLUE + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                    flagCaptured = true;
                    once = !once;
                }
                else{once = !once;teams.addBlue();}
            }

    }
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(flagCaptured){
            player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 10);
        }

    }
}
