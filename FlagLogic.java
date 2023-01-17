package me.average.ramctf;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import me.average.ramctf.Teams;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class FlagLogic implements Listener{
    static int redFlagX;
    static int blueFlagX;
    static int redFlagZ;
    static int redFlagY;
    static int blueFlagZ;
    static int blueFlagY;
    static boolean once;
    boolean blueFlagCaptured = false;
    boolean redFlagCaptured = false;
    public Player player;
    public String blueCaptureName;
    public String redCaptureName;


    static void setredX(int x){
        redFlagX = x;
    }

    static void setblueX(int x){
        blueFlagX = x;
    }

    static void setZ(int z){
        blueFlagZ = z;
        redFlagZ = z;
    }

    static void setY(int y){
        blueFlagY = y;
        redFlagY = y;
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        Teams.updateScoreboard();
        player = event.getPlayer();
        Location location = player.getLocation();
        double playerx = location.getX();
        double playerz = location.getZ();
        double playery = location.getY();
        World world = player.getWorld();
        Scoreboard board = player.getScoreboard();
        Team team = board.getEntryTeam(player.getName());
        // System.out.println(redFlagX);  System.out.println(redFlagY); System.out.println(redFlagZ); System.out.println();
        if (team != null) {
            if (playerx >= redFlagX - 2 && playerx <= redFlagX + 2 && playerz >= redFlagZ - 2 && playerz <= redFlagZ + 2 && playery <= redFlagY + 2 && playery >= redFlagY - 2) {
                if (once) {
                    if (!redFlagCaptured && Teams.blue.hasPlayer(event.getPlayer())) {
                        world.playEffect(location, Effect.BLAZE_SHOOT, 0, 100);
                        Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.RED + "Red " + ChatColor.AQUA + "flag stolen by " + ChatColor.BLUE + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                        redFlagCaptured = true;
                        redCaptureName = event.getPlayer().getDisplayName();
                        once = !once;
                        Setup.clearRedFlag();
                        world.getBlockAt(location).setType(Material.AIR);
                    }
                    if (blueFlagCaptured && Teams.red.hasPlayer(event.getPlayer()) && (event.getPlayer().getName() == blueCaptureName)) {
                        Teams.addRed();
                        Setup.resetBlueFlag();
                        Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.BLUE + "Blue " + ChatColor.AQUA + "flag captured by " + ChatColor.RED + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                        blueFlagCaptured = false;
                        blueCaptureName = "";
                        world.playEffect(location, Effect.ENDERDRAGON_SHOOT, 0, 100);
                        world.getBlockAt(location).setType(Material.AIR);
                    }
                } else {
                    once = !once;
                }

            } else if (playerx >= blueFlagX - 2 && playerx <= blueFlagX + 2 && playerz >= blueFlagZ - 2 && playerz <= blueFlagZ + 2 && playery <= blueFlagY + 2 && playery >= blueFlagY - 2) {
                if (once) {
                    if (!blueFlagCaptured && Teams.red.hasPlayer(event.getPlayer())) {
                        world.playEffect(location, Effect.BLAZE_SHOOT, 0, 100);
                        Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.BLUE + "Blue " + ChatColor.AQUA + "flag stolen by " + ChatColor.RED + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                        blueFlagCaptured = true;
                        blueCaptureName = event.getPlayer().getDisplayName();
                        once = !once;
                        Setup.clearBlueFlag();

                    }
                    if (redFlagCaptured && Teams.blue.hasPlayer(event.getPlayer()) && (event.getPlayer().getName() == redCaptureName)) {
                        Teams.addBlue();
                        Setup.resetRedFlag();
                        Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + ChatColor.RED + "Red " + ChatColor.AQUA + "flag captured by " + ChatColor.BLUE + event.getPlayer().getName() + ChatColor.AQUA + " ! ! !");
                        redFlagCaptured = false;
                        redCaptureName = "";
                        world.playEffect(location, Effect.ENDERDRAGON_SHOOT, 0, 100);
                    }
                } else {
                    once = !once;
                }

            }
        }

    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        PlatformSpawner.Spawn(Setup.bluex, Setup.y, Setup.z, event.getPlayer(), Material.BLUE_WOOL, true);
        PlatformSpawner.Spawn(Setup.redx, Setup.y, Setup.z, event.getPlayer(), Material.RED_WOOL, true);
        Player player = event.getPlayer();
        Scoreboard board = player.getScoreboard();
        Team team = board.getEntryTeam(player.getName());
        if (team != null) {
            if(team.getName().equalsIgnoreCase("red"))
                if(blueFlagCaptured && (event.getPlayer().getName().equals(blueCaptureName))){
                    player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 2);
            } else if(team.getName().equalsIgnoreCase("blue"))
                if(redFlagCaptured && (event.getPlayer().getName().equals(redCaptureName))) {
                    player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 2);
                }
            }
        }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        World world = event.getEntity().getWorld();
        if(redFlagCaptured && Teams.blue.hasPlayer(event.getEntity()) && (event.getEntity().getName() == redCaptureName)){
            Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + "RED FLAG DROPPED " + " ! ! !");
            Location location = event.getEntity().getLocation();
            world.playEffect(location, Effect.FIREWORK_SHOOT, 0, 500);
            world.spawnEntity(location, EntityType.FIREWORK);
            redFlagCaptured = false;
            redFlagX = (int)event.getEntity().getLocation().getX();
            redFlagY = (int)event.getEntity().getLocation().getY();
            redFlagZ = (int)event.getEntity().getLocation().getZ();
            redCaptureName = "";
            Setup.placeRedFlag(redFlagX, redFlagY, redFlagZ);
        }
        if(blueFlagCaptured && Teams.red.hasPlayer(event.getEntity()) && (event.getEntity().getName() == blueCaptureName)){
            Bukkit.broadcastMessage(ChatColor.AQUA + "! ! ! " + "BLUE FLAG DROPPED " + " ! ! !");
            Location location = event.getEntity().getLocation();
            world.playEffect(location, Effect.FIREWORK_SHOOT, 0, 500);
            world.spawnEntity(location, EntityType.FIREWORK);
            blueFlagCaptured = false;
            blueFlagX = (int)event.getEntity().getLocation().getX();
            blueFlagY = (int)event.getEntity().getLocation().getY();
            blueFlagZ = (int)event.getEntity().getLocation().getZ();
            blueCaptureName = "";
            Setup.placeRedFlag(blueFlagX, blueFlagY, blueFlagZ);
        }

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Scoreboard board = player.getScoreboard();
        Team team = board.getEntryTeam(player.getName());
        if(team != null){
            if(team.getName().equalsIgnoreCase("red")){
                Location respawnLocation = new Location(player.getWorld(), redFlagX, redFlagY+2, redFlagZ);
                event.setRespawnLocation(respawnLocation);
            }else if (team.getName().equalsIgnoreCase("blue")){
                Location respawnLocation = new Location(player.getWorld(), blueFlagX, blueFlagY+2, blueFlagZ);
                event.setRespawnLocation(respawnLocation);
            }
        }
    }
    
}
