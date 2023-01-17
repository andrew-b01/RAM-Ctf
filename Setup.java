package me.average.ramctf;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Setup implements CommandExecutor {
    public static int bluex;
    public static int redx;
    public static int y;
    public static int z;
    public boolean clearmiddle = true;
    public static Player player;
    public static World world;
    public static boolean gameStarted = false;
    public static boolean gameSetup = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        player = (Player) sender;
        if (!(sender instanceof Player) || !(player.isOp())) {
            player.sendMessage(ChatColor.RED + "User must be OP to use this command");
            return true;
        }
        if (label.equalsIgnoreCase("end")) {
            gameStarted = false;
        }
        if (label.equalsIgnoreCase("start")) {
            
            if (!gameSetup){
                player.sendMessage(ChatColor.RED + "Game not set up. Setup game by typing /setup");
                return true;
            }
            if (gameStarted){
                player.sendMessage(ChatColor.RED + "End current game before starting new game by typing /end");
                return true;
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                Scoreboard board = player.getScoreboard();
                Team team = board.getEntryTeam(player.getName());
                if (team != null) {
                    if (team.getName().equalsIgnoreCase("blue")) {
                        Location teleportLocation = new Location(player.getWorld(), bluex + 7, y + 1, z + 8);
                        // player.teleport(teleportLocation);
                        player.setWalkSpeed(0);
                        for (int i = 5; i > 0; i--) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                            player.sendTitle(ChatColor.RED + "Game starting in " + i + " seconds", "", 10, 70, 20);
                            player.teleport(teleportLocation);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        player.setFlySpeed(0.1f);
                        player.setWalkSpeed(0.2f);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                        player.sendTitle(ChatColor.GREEN + "Good Luck!", "", 10, 170, 20);
                    } else if (team.getName().equalsIgnoreCase("red")) {
                        Location teleportLocation = new Location(player.getWorld(), redx + 7, y + 1, z + 8);
                        // player.teleport(teleportLocation);
                        player.setWalkSpeed(0);
                        
                        for (int i = 5; i > 0; i--) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                            player.sendTitle(ChatColor.RED + "Game starting in " + i + " seconds", "", 10, 70, 20);
                            player.teleport(teleportLocation);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        player.setFlySpeed(0.1f);
                        player.setWalkSpeed(0.2f);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                        player.sendTitle(ChatColor.GREEN + "Good Luck!", "", 10, 170, 20);
                    }
                }

            

            }

            gameStarted = true;
    }

        if (label.equalsIgnoreCase("clearmiddle")) {
            if(clearmiddle){
                Bukkit.broadcastMessage(ChatColor.AQUA + "Clearmiddle: false");
                clearmiddle = false;
            }
            else{
                Bukkit.broadcastMessage(ChatColor.AQUA + "Clearmiddle: true");
                clearmiddle = true;
            }

        }
        if (label.equalsIgnoreCase("setup")) {

            gameSetup = true;

            Bukkit.broadcastMessage(ChatColor.AQUA + "Game setup initialized\n");
            FlagLogic.setredX((int) player.getLocation().getX() - 77);
            FlagLogic.setblueX((int) player.getLocation().getX() + 93);
            FlagLogic.setZ((int) player.getLocation().getZ() + 8);
            FlagLogic.setY((int) player.getLocation().getY());
            bluex = (int) player.getLocation().getX() + 85;
            redx = (int) player.getLocation().getX() - 85;
            y = (int) player.getLocation().getY();
            z = (int) player.getLocation().getZ();

            platform(bluex, y, z, player, Material.BLUE_WOOL, "Blue", Material.BLUE_BANNER, clearmiddle); 
            platform(redx, y, z, player, Material.RED_WOOL, "Red", Material.RED_BANNER, clearmiddle);

            Bukkit.broadcastMessage(ChatColor.AQUA + "Run /team to set up teams");


            return true;
        }
        return true;
    }

    static void platform(int x, int y, int z, Player sender, Material material, String color, Material banner, Boolean clearmiddle) {
        for (int i = 1; i < 16; i++) {
            for (int o = 0; o < 15; o++) {
                world = sender.getWorld();
                world.getBlockAt(x + o, y, z + i).setType(material);
                int height = y;
                int airheight = 1;
                while (height < 255) {
                    world.getBlockAt(x + o, y + airheight, z + i).setType(Material.AIR);
                    airheight++;
                    height++;
                }

            }
        }
        World world = sender.getWorld();
        if(clearmiddle){
            if (color.equals("Red")) {
                for (int i = 85; i > 0; i--) {
                    for (int o = 15; o > 0; o--) {
                        for (int m = 15; m > 0; m--) {
                            world.getBlockAt(x + i, y + o, z + m).setType(Material.AIR);
                        }
                    }
                }
            } else {
                for (int i = -85; i < 0; i++) {
                    for (int o = 15; o > 0; o--) {
                        for (int m = 15; m > 0; m--) {
                            world.getBlockAt(x + i, y + o, z + m).setType(Material.AIR);
                        }
                    }
                }
            }
        }

        if(color.equals("Red")){
            FlagLogic.setredX(x + 7); FlagLogic.setY(y+1); FlagLogic.setZ(z+8);
        }
        
        else {
            FlagLogic.setblueX(x + 7); FlagLogic.setY(y+1); FlagLogic.setZ(z+8);
        }
            
        world.getBlockAt(x + 7, y + 1, z + 8).setType(banner);

        //Play particle on banner position
        //Location place = new Location(world, x + 1, y + 8, z + 8);
        //world.playEffect(place, Effect.ENDER_SIGNAL, 2003);

        if(color.equals("Blue")) {
            Bukkit.broadcastMessage(ChatColor.BLUE + color + " platform has been placed at: x " + x + " y " + y + " z " + z);
        }
        else {
            Bukkit.broadcastMessage(ChatColor.RED + color + " platform has been placed at: x " + x + " y " + y + " z " + z);
        }
    }
    static void clearRedFlag(){
        world.getBlockAt(FlagLogic.redFlagX, FlagLogic.redFlagY, FlagLogic.redFlagZ).setType(Material.AIR);
    }
    static void clearBlueFlag(){
        world.getBlockAt(FlagLogic.blueFlagX, FlagLogic.blueFlagY, FlagLogic.blueFlagZ).setType(Material.AIR);
    }
    static void resetRedFlag(){ world.getBlockAt(redx + 7, y + 1, z + 8).setType(Material.RED_BANNER); }
    static void resetBlueFlag(){
        world.getBlockAt(bluex + 7, y + 1, z + 8).setType(Material.BLUE_BANNER);
    }
    static void placeBlueFlag(int x, int y, int z){ world.getBlockAt(x, y, z).setType(Material.BLUE_BANNER);}
    static void placeRedFlag(int x, int y, int z){ world.getBlockAt(x, y, z).setType(Material.RED_BANNER);}
    }

