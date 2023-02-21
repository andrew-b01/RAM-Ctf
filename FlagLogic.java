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
import me.average.ramctf.Tools;
import org.bukkit.potion.PotionEffect;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Slime;

public class FlagLogic implements Listener {
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
    public Slime redSlime;
    public Slime blueSlime;

    static void setredX(int x) {
        redFlagX = x;
    }

    static void setblueX(int x) {
        blueFlagX = x;
    }

    static void setZ(int z) {
        blueFlagZ = z;
        redFlagZ = z;
    }

    static void setY(int y) {
        blueFlagY = y;
        redFlagY = y;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Teams.updateScoreboard();

        PlatformSpawner.Spawn(Tools.bluePlatformX - 7, Tools.bluePlatformY - 1, Tools.bluePlatformZ - 8,
                event.getPlayer(), Material.BLUE_WOOL, true);
        PlatformSpawner.Spawn(Tools.redPlatformX - 7, Tools.redPlatformY - 1, Tools.redPlatformZ - 8, event.getPlayer(),
                Material.RED_WOOL, true);
        Player player = event.getPlayer();
        Scoreboard board = player.getScoreboard();
        Team team = board.getEntryTeam(player.getName());
        World world = player.getWorld();
        Location location = player.getLocation();

        if (!redFlagCaptured) {
            Tools.respawnRedFlagAtPosition(world);
        }

        if (!blueFlagCaptured) {
            Tools.respawnBlueFlagAtPosition(world);
        }

        if (!redFlagCaptured && !Tools.redFlagAtPlatform()) {
            Tools.respawnRedFlagAtPosition(world);
            Location redFlagLocation = new Location(world, Tools.currentRedFlagX, Tools.currentRedFlagY + 20,
                    Tools.currentRedFlagZ);
            world.spawnParticle(Particle.FIREWORKS_SPARK, redFlagLocation, 20);
        }

        if (!blueFlagCaptured && !Tools.blueFlagAtPlatform()) {
            Tools.respawnBlueFlagAtPosition(world);
            Location blueFlagLocation = new Location(world, Tools.currentBlueFlagX, Tools.currentBlueFlagY + 20,
                    Tools.currentBlueFlagZ);
            world.spawnParticle(Particle.FIREWORKS_SPARK, blueFlagLocation, 20);
        }

        if (team != null) {

            if (team.getName().equalsIgnoreCase("red")) {
                if (blueFlagCaptured && (event.getPlayer().getName().equals(blueCaptureName))) {
                    world.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));

                }

            } else if (team.getName().equalsIgnoreCase("blue")) {
                if (redFlagCaptured && (event.getPlayer().getName().equals(redCaptureName))) {
                    world.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
                }
            }

            if (Tools.PlayerOnRedFlag(player)) {
                if (!redFlagCaptured && Teams.blue.hasEntry(event.getPlayer().getDisplayName())) {

                    world.playEffect(location, Effect.BLAZE_SHOOT, 0, 100);
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(ChatColor.RED + "Red " + ChatColor.AQUA + "flag stolen by "
                                + ChatColor.BLUE + event.getPlayer().getName(), "", 10, 40, 10);
                    }

                    redFlagCaptured = true;
                    redCaptureName = event.getPlayer().getDisplayName();

                    if (Tools.redSlime != null) {
                        Tools.removeRedSlime();
                    }

                    Tools.clearRedFlag(world);
                    world.getBlockAt(location).setType(Material.AIR);
                }

                if (!redFlagCaptured && !Tools.redFlagAtPlatform()
                        && Teams.red.hasEntry(event.getPlayer().getDisplayName())) {
                    world.playEffect(location, Effect.BAT_TAKEOFF, 0, 100);
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(ChatColor.RED + "Red " + ChatColor.AQUA + "flag returned by "
                                + ChatColor.RED + event.getPlayer().getName(), "", 10, 40, 10);
                    }

                    if (Tools.redSlime != null) {
                        Tools.removeRedSlime();
                    }
                    Tools.clearRedFlag(world);
                    Tools.resetRedFlag(world);
                }
            }

            if (Tools.PlayerOnBlueFlag(player)) {

                if (!blueFlagCaptured && Teams.red.hasEntry(event.getPlayer().getDisplayName())) {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(ChatColor.BLUE + "Blue " + ChatColor.AQUA + "flag stolen by "
                                + ChatColor.RED + event.getPlayer().getName(), "", 10, 40, 10);
                    }

                    if (Tools.blueSlime != null) {
                        Tools.removeBlueSlime();
                    }

                    world.playEffect(location, Effect.BLAZE_SHOOT, 0, 100);
                    blueFlagCaptured = true;
                    blueCaptureName = event.getPlayer().getDisplayName();
                    Tools.clearBlueFlag(world);

                }

                if (!blueFlagCaptured && !Tools.blueFlagAtPlatform()
                        && Teams.blue.hasEntry(event.getPlayer().getDisplayName())) {
                    world.playEffect(location, Effect.BAT_TAKEOFF, 0, 100);
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(ChatColor.BLUE + "Blue " + ChatColor.AQUA + "flag returned by "
                                + ChatColor.BLUE + event.getPlayer().getName(), "", 10, 40, 10);
                    }

                    Tools.clearBlueFlag(world);
                    Tools.resetBlueFlag(world);

                    if (Tools.blueSlime != null) {
                        Tools.removeBlueSlime();
                    }
                }
            }

            if (Tools.PlayerWithinRedPlatform(player)) {
                if (blueFlagCaptured && Teams.red.hasEntry(event.getPlayer().getDisplayName())
                        && (event.getPlayer().getName() == blueCaptureName)) {
                    Teams.addRed();
                    Tools.resetBlueFlag(world);
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(ChatColor.BLUE + "Blue " + ChatColor.AQUA + "flag captured by "
                                + ChatColor.RED + event.getPlayer().getName(), "", 10, 40, 10);
                    }

                    blueFlagCaptured = false;
                    blueCaptureName = "";
                    world.playEffect(location, Effect.EXTINGUISH, 1, 200);
                    world.getBlockAt(location).setType(Material.AIR);
                    event.getPlayer().removePotionEffect(PotionEffectType.GLOWING);

                }
            }

            if (Tools.PlayerWithinBluePlatform(player)) {
                if (redFlagCaptured && Teams.blue.hasEntry(event.getPlayer().getDisplayName())
                        && (event.getPlayer().getName() == redCaptureName)) {
                    Teams.addBlue();
                    Tools.resetRedFlag(world);
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(ChatColor.RED + "Red " + ChatColor.AQUA + "flag captured by "
                                + ChatColor.BLUE + event.getPlayer().getName(), "", 10, 40, 10);
                    }

                    redFlagCaptured = false;
                    redCaptureName = "";
                    world.playEffect(location, Effect.EXTINGUISH, 1, 200);
                    event.getPlayer().removePotionEffect(PotionEffectType.GLOWING);

                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        World world = event.getEntity().getWorld();

        if (redFlagCaptured && Teams.blue.hasEntry(event.getEntity().getDisplayName())
                && (event.getEntity().getName() == redCaptureName)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(ChatColor.RED + "RED FLAG DROPPED", "", 10, 40, 10);
            }

            event.getEntity().removePotionEffect(PotionEffectType.GLOWING);
            Location location = event.getEntity().getLocation();
            world.playEffect(location, Effect.FIREWORK_SHOOT, 0, 500);
            world.spawnEntity(location, EntityType.FIREWORK);
            redFlagCaptured = false;

            redFlagX = (int) location.getX();
            redFlagY = (int) location.getY();
            redFlagZ = (int) location.getZ();
            Tools.setRedFlagCoords(redFlagX, redFlagY, redFlagZ);
            Tools.respawnRedFlagAtPosition(world);

            Location slimeLocation = new Location(world, location.getX(), location.getY() + 2, location.getZ());
            Tools.spawnRedSlime(slimeLocation);

            redCaptureName = "";

        }
        if (blueFlagCaptured && Teams.red.hasEntry(event.getEntity().getDisplayName())
                && (event.getEntity().getName() == blueCaptureName)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(ChatColor.BLUE + "BLUE FLAG DROPPED", "", 10, 40, 10);
            }

            event.getEntity().removePotionEffect(PotionEffectType.GLOWING);
            Location location = event.getEntity().getLocation();
            world.playEffect(location, Effect.FIREWORK_SHOOT, 0, 500);
            world.spawnEntity(location, EntityType.FIREWORK);
            blueFlagCaptured = false;

            blueFlagX = (int) location.getX();
            blueFlagY = (int) location.getY();
            blueFlagZ = (int) location.getZ();
            Tools.setBlueFlagCoords(blueFlagX, blueFlagY, blueFlagZ);
            Tools.respawnBlueFlagAtPosition(world);

            Location slimeLocation = new Location(world, location.getX(), location.getY() + 2, location.getZ());
            Tools.spawnBlueSlime(slimeLocation);

            blueCaptureName = "";

        }

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Scoreboard board = player.getScoreboard();
        Team team = board.getEntryTeam(player.getName());
        if (team != null) {
            if (team.getName().equalsIgnoreCase("red")) {
                Location respawnLocation = new Location(player.getWorld(), Tools.redPlatformX, Tools.redPlatformY,
                        Tools.redPlatformZ);
                event.setRespawnLocation(respawnLocation);
            } else if (team.getName().equalsIgnoreCase("blue")) {
                Location respawnLocation = new Location(player.getWorld(), Tools.bluePlatformX, Tools.bluePlatformY,
                        Tools.bluePlatformZ);
                event.setRespawnLocation(respawnLocation);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Material blockType = event.getBlockPlaced().getType();
        if (blockType == Material.BLUE_BANNER || blockType == Material.RED_BANNER) {
            event.setCancelled(true);
        }

        if (Tools.pointWithinBluePlatform(event.getBlock().getX(), event.getBlock().getZ())) {
            event.setCancelled(true);
        }

        if (Tools.pointWithinRedPlatform(event.getBlock().getX(), event.getBlock().getZ())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        if (blockType == Material.BLUE_BANNER || blockType == Material.RED_BANNER) {
            event.setCancelled(true);
        }

        if ((blockType == Material.BLUE_WOOL || blockType == Material.RED_WOOL)
                && (Tools.pointWithinBluePlatform(event.getBlock().getX(), event.getBlock().getZ())
                        || Tools.pointWithinRedPlatform(event.getBlock().getX(), event.getBlock().getZ()))) {
            event.setCancelled(true);
        }
    }

}
