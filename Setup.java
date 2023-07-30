package me.average.ramctf;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import me.average.ramctf.Teams;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            //resets all variables
            Teams.bluePoints = 0;
            Teams.redPoints = 0;
            //resets variables in other classes
            Teams.bluescore.setScore(0);
            Teams.redscore.setScore(0);
            
            FlagLogic.endGame();
            for (Player player : Bukkit.getOnlinePlayers()) {
                //play a fun melody to celebrate the end of the game
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                player.removePotionEffect(PotionEffectType.GLOWING);

                //if red team has more points, say red won, otherwise say blue won
                
                if (Teams.bluescore.getScore() > Teams.redscore.getScore()) {
                    player.sendTitle("Blue Team Wins!", "", 10, 70, 20);
                } else if (Teams.bluescore.getScore() < Teams.redscore.getScore()) {
                    player.sendTitle("Red Team Wins!", "", 10, 70, 20);
                } else {
                    player.sendTitle("Tie Game!", "", 10, 70, 20);
                }
                
                
            }
            

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
                //give all players some starter items such as a sword, armor, bow, food and water
                // player.getInventory().clear();
                // player.getInventory().setArmorContents(null);
                // player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));
                // player.getInventory().addItem(new ItemStack(Material.LEATHER_HELMET));
                // player.getInventory().addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
                // player.getInventory().addItem(new ItemStack(Material.LEATHER_LEGGINGS));
                // player.getInventory().addItem(new ItemStack(Material.LEATHER_BOOTS));
                // player.getInventory().addItem(new ItemStack(Material.BOW));
                // player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
                // player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                // player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 8));
                // player.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 64));
                // player.sendMessage(ChatColor.GREEN + "You have been given starter tools!");

                //teleport players to their respective platforms
                if (team != null) {
                    //if player is on red team, teleport them to the red platform
                    if (team.getName().equalsIgnoreCase("blue")) {
                        Location teleportLocation = new Location(player.getWorld(), Tools.bluePlatformX, Tools.bluePlatformY, Tools.bluePlatformZ, 90, 0);
                        player.setWalkSpeed(0);
                        int timer = 0;
                        //teleport player to platform and count down to game start
                        for (int i = 500; i > 0; i--) {

                            if (timer%100 == 0) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                                player.sendTitle(ChatColor.RED + "Game starting in " + i/100 + " seconds", "", 10, 70, 20);
                            }

                            player.teleport(teleportLocation);

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            timer += 1;
                        }
                        //set player's walk speed to normal and play a sound to indicate game start
                        player.setFlySpeed(0.1f);
                        player.setWalkSpeed(0.2f);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                        player.sendTitle(ChatColor.GREEN + "Good Luck!", "", 10, 170, 20);
                        
                        //if player is on blue team, teleport them to the blue platform
                    } else if (team.getName().equalsIgnoreCase("red")) {
                        Location teleportLocation = new Location(player.getWorld(), Tools.redPlatformX, Tools.redPlatformY, Tools.redPlatformZ, -90, 0);
                        player.setWalkSpeed(0);
                        int timer = 0;

                        //teleport player to platform and count down to game start
                        for (int i = 500; i > 0; i--) {

                            if (timer%100 == 0) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                                player.sendTitle(ChatColor.RED + "Game starting in " + i/100 + " seconds", "", 10, 70, 20);
                            }

                            player.teleport(teleportLocation);

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            timer += 1;
                        }

                        //set player's walk speed to normal and play a sound to indicate game start
                        player.setFlySpeed(0.1f);
                        player.setWalkSpeed(0.2f);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                        player.sendTitle(ChatColor.GREEN + "Good Luck!", "", 10, 140, 20);
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
        
        //setup game
        if (label.equalsIgnoreCase("setup")) {

            gameSetup = true;

            Bukkit.broadcastMessage(ChatColor.AQUA + "Game setup initialized\n");

            bluex = (int) player.getLocation().getX() + 85;
            redx = (int) player.getLocation().getX() - 85;

            y = (int) player.getLocation().getY();
            z = (int) player.getLocation().getZ();

            Tools.setBlueFlagCoords(bluex+7, y+1, z+8);
            Tools.setBluePlatformCords(bluex+7, y+1, z+8);

            Tools.setRedFlagCoords(redx+7, y+1, z+8);
            Tools.setRedPlatformCoords(redx+7, y+1, z+8);
            
            //create platforms
            platform(bluex, y, z, player, Material.BLUE_WOOL, "Blue", Material.BLUE_BANNER, clearmiddle); 
            platform(redx, y, z, player, Material.RED_WOOL, "Red", Material.RED_BANNER, clearmiddle);

            Bukkit.broadcastMessage(ChatColor.AQUA + "Run /team to set up teams");

            return true;
        }
        return true;
    }

    //create platforms
    static void platform(int x, int y, int z, Player sender, Material material, String color, Material banner, Boolean clearmiddle) {
        for (int i = 1; i < 16; i++) {
            //
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
        //parameter to clear middle of platform
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
            
        world.getBlockAt(x + 7, y + 1, z + 8).setType(banner);

        if(color.equals("Blue")) {
            Bukkit.broadcastMessage(ChatColor.BLUE + color + " Platform has been placed at: x " + x + " y " + y + " z " + z);
        }
        else {
            Bukkit.broadcastMessage(ChatColor.RED + color + " Platform has been placed at: x " + x + " y " + y + " z " + z);
        }
    }
}

