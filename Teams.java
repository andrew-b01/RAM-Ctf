package me.average.ramctf;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.scoreboard.*;



public class Teams implements CommandExecutor {

    static Team red;
    static Team blue;
    Location redflag;
    Location blueflag;
    Score bluescore;
    Score redscore;
    static int bluePoints = 0;
    static int redPoints = 0;
    Player player;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Setup setup = new Setup(); player = (Player) sender;

        double redx = setup.redx; double bluex = setup.bluex; double y = setup.y; double z = setup.z;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("MainScoreboard","dummy", ChatColor.BOLD + "------RamCTF-----\n\n");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        bluescore = obj.getScore(ChatColor.BLUE + "BLUE SCORE: " + ChatColor.GOLD + bluePoints);
        redscore = obj.getScore(ChatColor.RED + "RED SCORE: " + ChatColor.GOLD + redPoints);
        redscore.setScore(1);
        bluescore.setScore(2);
        player.setScoreboard(board);

        redflag = new Location(player.getWorld(), redx, y, z);
        blueflag = new Location(player.getWorld(), bluex, y, z);
        
        if (args.length == 0) {

            TextComponent blue = new TextComponent("\nJoin Blue Team\n");
            blue.setColor(ChatColor.BLUE);
            blue.setBold(true);
            blue.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team blue"));
            blue.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Click here to join blue team").color(ChatColor.GRAY).italic(true).create()));
            Bukkit.spigot().broadcast(blue);

            TextComponent red = new TextComponent("\nJoin Red Team");
            red.setColor(ChatColor.RED);
            red.setBold(true);
            red.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team red"));
            red.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Click here to join red team").color(ChatColor.GRAY).italic(true).create()));
            Bukkit.spigot().broadcast(red);
        }

        else if (args[0].equals("blue")) {
            blue = board.registerNewTeam("Blue");
            blue.setPrefix(ChatColor.BLUE + "[BLUE] " + ChatColor.WHITE);

            blue.setAllowFriendlyFire(false);
            blue.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
            blue.setCanSeeFriendlyInvisibles(true);
            blue.addPlayer(player);
            player.sendMessage(ChatColor.DARK_AQUA + "You have joined the BLUE team, press TAB to check teams");
    }

        else if (args[0].equals("red")) {
            red = board.registerNewTeam("Red");
            red.setPrefix(ChatColor.RED + "[RED] " + ChatColor.WHITE);

            red.setAllowFriendlyFire(false);
            red.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
            red.setCanSeeFriendlyInvisibles(true);
            red.addPlayer(player);
            player.sendMessage(ChatColor.DARK_AQUA + "You have joined the RED team, press TAB to check teams");
        }

        bluescore.setScore(0);

        redscore.setScore(0);

        return true;}
    public Team getTeamRed(){
        return red;
    }
    public Team getTeamBlue(){
        return blue;
    }
    public Location getBlueLocation(){
        return blueflag;
    }
    public Location getRedLocation(){
        return redflag;
    }

    public static void addBlue(){
        bluePoints += 1;
    }
    public static void addRed() {
        redPoints += 1;
    }
}



