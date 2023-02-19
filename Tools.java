package me.average.ramctf;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Tools{
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

    public static boolean pointWithinBluePlatform(double x, double z){

        double distanceFromPlayerToPlatform = Math.sqrt(Math.pow(x - bluePlatformX, 2) + Math.pow(z - bluePlatformZ, 2));

        if(distanceFromPlayerToPlatform <= 7.5){
            return true;
        } else {
            return false;
        }
    }

    public static boolean pointWithinRedPlatform(double x, double z){

        double distanceFromPlayerToPlatform = Math.sqrt(Math.pow(x - redPlatformX, 2) + Math.pow(z - redPlatformZ, 2));

        if(distanceFromPlayerToPlatform <= 7.5){
            return true;
        } else {
            return false;
        }
    }

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

    public static void clearRedFlag(World world){world.getBlockAt(currentRedFlagX, currentRedFlagY, currentRedFlagZ).setType(Material.AIR);}
    public static void clearBlueFlag(World world){world.getBlockAt(currentBlueFlagX, currentBlueFlagY, currentBlueFlagZ).setType(Material.AIR);}

    public static void resetRedFlag(World world){ world.getBlockAt(redPlatformX, redPlatformY, redPlatformZ).setType(Material.RED_BANNER); resetRedFlagCoordsAtPlatform();}
    public static void resetBlueFlag(World world){ world.getBlockAt(bluePlatformX, bluePlatformY, bluePlatformZ).setType(Material.BLUE_BANNER); resetBlueFlagCoordsAtPlatform();}

    public static void resetRedFlagCoordsAtPlatform() { setRedFlagCoords(redPlatformX, redPlatformY, redPlatformZ);}
    public static void resetBlueFlagCoordsAtPlatform() { setBlueFlagCoords(bluePlatformX, bluePlatformY, bluePlatformZ);}

    public static boolean blueFlagAtPlatform() { return (currentBlueFlagX == bluePlatformX && currentBlueFlagZ == bluePlatformZ);}
    public static boolean redFlagAtPlatform() { return (currentRedFlagX == redPlatformX && currentRedFlagZ == redPlatformZ);}

    public static void respawnRedFlagAtPosition(World world){ world.getBlockAt(currentRedFlagX, currentRedFlagY, currentRedFlagZ).setType(Material.RED_BANNER); }
    public static void respawnBlueFlagAtPosition(World world){ world.getBlockAt(currentBlueFlagX, currentBlueFlagY, currentBlueFlagZ).setType(Material.BLUE_BANNER); }

}