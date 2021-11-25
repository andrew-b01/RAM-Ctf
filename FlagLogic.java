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
    boolean blueFlagCaptured = false;
    boolean redFlagCaptured = false;
    Player player;

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
        player = event.getPlayer();
        Location location = player.getLocation();
        double playerx = location.getX();
        double playerz = location.getZ();
        double playery = location.getY();
        World world = player.getWorld();

            if(playerx >= redFlagX-2 && playerx <= redFlagX+2 && playerz >= flagZ - 2 && playerz <= flagZ + 2 && playery <= flagY + 2 && playery >= flagY -2){
                if(once){
                    if (!redFlagCaptured && Teams.blue.hasPlayer(event.getPlayer())) {
                        world.playEffect(location, Effect.BLAZE_SHOOT, 0);
                        Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.RED + "Red " + ChatColor.AQUA + "flag stolen by " + ChatColor.RED + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                        redFlagCaptured = true;
                        once = !once;
                        Setup.clearRedFlag();

                    }
                    if (blueFlagCaptured && Teams.red.hasPlayer(event.getPlayer())) {
                        Teams.addRed();
                        Setup.resetBlueFlag();
                        Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.BLUE + "Blue " + ChatColor.AQUA + "flag captured by " + ChatColor.BLUE + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                        blueFlagCaptured = false;
                    }
                } else{once = !once;}

            }
            else if(playerx >= blueFlagX-2 && playerx <= blueFlagX+2 && playerz >= flagZ - 2 && playerz <= flagZ + 2 && playery <= flagY + 2 && playery >= flagY -2){
                if(once) {
                    if (!blueFlagCaptured && Teams.red.hasPlayer(event.getPlayer())) {
                        world.playEffect(location, Effect.BLAZE_SHOOT, 0);
                        Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.BLUE + "Blue " + ChatColor.AQUA + "flag stolen by " + ChatColor.BLUE + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                        blueFlagCaptured = true;
                        once = !once;
                        Setup.clearBlueFlag();

                    }
                    if (redFlagCaptured && Teams.blue.hasPlayer(event.getPlayer())) {
                        Teams.addBlue();
                        Setup.resetRedFlag();
                        Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.RED + "Red " + ChatColor.AQUA + "flag captured by " + ChatColor.RED + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                        redFlagCaptured = false;
                    }
                } else{once = !once;}

            }

    }
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(blueFlagCaptured || redFlagCaptured){
            player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 2);
        }

    }
}
