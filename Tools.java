package me.average.ramctf;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class Tools{
    // Static variables
    static int redPlatformX;
    static int redPlatformY;
    static int redPlatformZ;
    
    static int bluePlatformX;
    static int bluePlatformY;
    static int bluePlatformZ;

    static int currentRedFlagX;
    static int currentRedFlagY;
    static int currentRedFlagZ;

    static int currentBlueFlagX;
    static int currentBlueFlagY;
    static int currentBlueFlagZ;

    public static Slime redSlime;
    public static Slime blueSlime; 

    // Static helper methods
    public static void spawnRedSlime(Location location){
        redSlime = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
        redSlime.setSize(3);
        redSlime.setAI(false);
        redSlime.setInvulnerable(true);
        redSlime.setInvisible(true);
        redSlime.setGlowing(true);
        redSlime.setCollidable(false);
        redSlime.setSilent(true);
        redSlime.setMaxHealth(2047);
        redSlime.setHealth(2047);
    }
    
    public static void removeBlueSlime(){
        blueSlime.remove();
        blueSlime = null;
    }

    public static void removeRedSlime(){
        redSlime.remove();
        redSlime = null;
    }
    public static void spawnBlueSlime(Location location){
        blueSlime = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
        blueSlime.setSize(3);
        blueSlime.setAI(false);
        blueSlime.setInvulnerable(true);
        blueSlime.setInvisible(true);
        blueSlime.setGlowing(true);
        blueSlime.setCollidable(false);
        blueSlime.setSilent(true);
        blueSlime.setMaxHealth(2047);
        blueSlime.setHealth(2047);
    }

    public static void setRedPlatformCoords(int x, int y, int z){
        redPlatformX = x;
        redPlatformY = y;
        redPlatformZ = z;
    }

    public static void setBluePlatformCords(int x, int y, int z){
        bluePlatformX = x;
        bluePlatformY = y;
        bluePlatformZ = z;
    }

    public static void setRedFlagCoords(int x, int y, int z){
        currentRedFlagX = x;
        currentRedFlagY = y;
        currentRedFlagZ = z;
    }

    public static void setBlueFlagCoords(int x, int y, int z){
        currentBlueFlagX = x;
        currentBlueFlagY = y;
        currentBlueFlagZ = z;
    }


    //returns true if player is on red platform
    public static boolean PlayerWithinRedPlatform(Player player){
        int playerX = (int) player.getLocation().getX();
        int playerY = (int) player.getLocation().getY();
        int playerZ = (int) player.getLocation().getZ();

        double distanceFromPlayerToPlatform = Math.sqrt(Math.pow(playerX - redPlatformX, 2) + Math.pow(playerZ - redPlatformZ, 2));
        
        if(distanceFromPlayerToPlatform <= 7.5 && (playerY - redPlatformY) >= -1){
            return true;
        } else {
            return false;
        }
    }

    //returns true if player is on blue platform
    public static boolean PlayerWithinBluePlatform(Player player){
        int playerX = (int) player.getLocation().getX();
        int playerY = (int) player.getLocation().getY();
        int playerZ = (int) player.getLocation().getZ();

        double distanceFromPlayerToPlatform = Math.sqrt(Math.pow(playerX - bluePlatformX, 2) + Math.pow(playerZ - bluePlatformZ, 2));

        if(distanceFromPlayerToPlatform <= 7.5 && (playerY - bluePlatformY) >= -1){
            return true;
        } else {
            return false;
        }
    }

    //returms true if point is within red platform
    public static boolean pointWithinBluePlatform(double x, double z){

        double distanceFromPlayerToPlatform = Math.sqrt(Math.pow(x - bluePlatformX, 2) + Math.pow(z - bluePlatformZ, 2));

        if(distanceFromPlayerToPlatform <= 7.5){
            return true;
        } else {
            return false;
        }
    }

    //returns true if point is within blue platform
    public static boolean pointWithinRedPlatform(double x, double z){

        double distanceFromPlayerToPlatform = Math.sqrt(Math.pow(x - redPlatformX, 2) + Math.pow(z - redPlatformZ, 2));

        if(distanceFromPlayerToPlatform <= 7.5){
            return true;
        } else {
            return false;
        }
    }

    //returns true if player is on red flag
    public static boolean PlayerOnRedFlag(Player player){
        int playerX = (int) player.getLocation().getX();
        int playerY = (int) player.getLocation().getY();
        int playerZ = (int) player.getLocation().getZ();

        double distanceFromPlayerToFlag = Math.sqrt(Math.pow(playerX - currentRedFlagX, 2) + Math.pow(playerZ - currentRedFlagZ, 2));

        if(distanceFromPlayerToFlag <= 2 && Math.abs(currentRedFlagY - playerY) <= 3 && (playerY - currentBlueFlagY) >= -1){
            return true;
        } else {
            return false;
        }
    }

    //returns true if player is on blue flag
    public static boolean PlayerOnBlueFlag(Player player){
        int playerX = (int) player.getLocation().getX();
        int playerY = (int) player.getLocation().getY();
        int playerZ = (int) player.getLocation().getZ();

        double distanceFromPlayerToFlag = Math.sqrt(Math.pow(playerX - currentBlueFlagX, 2) + Math.pow(playerZ - currentBlueFlagZ, 2));
        if(distanceFromPlayerToFlag <= 2 && Math.abs(currentBlueFlagY - playerY) <= 3 && (playerY - currentBlueFlagY) >= -1){
            return true;
        } else {
            return false;
        }
    }

    //removes flag
    public static void clearRedFlag(World world){world.getBlockAt(currentRedFlagX, currentRedFlagY, currentRedFlagZ).setType(Material.AIR);}
    public static void clearBlueFlag(World world){world.getBlockAt(currentBlueFlagX, currentBlueFlagY, currentBlueFlagZ).setType(Material.AIR);}

    //sets flag to platform
    public static void resetRedFlag(World world){ world.getBlockAt(redPlatformX, redPlatformY, redPlatformZ).setType(Material.RED_BANNER); resetRedFlagCoordsAtPlatform();}
    public static void resetBlueFlag(World world){ world.getBlockAt(bluePlatformX, bluePlatformY, bluePlatformZ).setType(Material.BLUE_BANNER); resetBlueFlagCoordsAtPlatform();}

    //resets coords of flag
    public static void resetRedFlagCoordsAtPlatform() { setRedFlagCoords(redPlatformX, redPlatformY, redPlatformZ);}
    public static void resetBlueFlagCoordsAtPlatform() { setBlueFlagCoords(bluePlatformX, bluePlatformY, bluePlatformZ);}

    //returns true if flag is at platform
    public static boolean blueFlagAtPlatform() { return (currentBlueFlagX == bluePlatformX && currentBlueFlagZ == bluePlatformZ);}
    public static boolean redFlagAtPlatform() { return (currentRedFlagX == redPlatformX && currentRedFlagZ == redPlatformZ);}

    //respawns flag at coords
    public static void respawnRedFlagAtPosition(World world){ world.getBlockAt(currentRedFlagX, currentRedFlagY, currentRedFlagZ).setType(Material.RED_BANNER); }
    public static void respawnBlueFlagAtPosition(World world){ world.getBlockAt(currentBlueFlagX, currentBlueFlagY, currentBlueFlagZ).setType(Material.BLUE_BANNER); }

}