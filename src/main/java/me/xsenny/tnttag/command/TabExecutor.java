package me.xsenny.tnttag.command;

import me.xsenny.tnttag.TntTag;
import me.xsenny.tnttag.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabExecutor implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> argss = new ArrayList<>();

        if (args.length == 1){
            argss.add("party");
            argss.add("admin");
            argss.add("join");
            argss.add("leave");
        }else if (args.length == 2 && "party".equalsIgnoreCase(args[0])){
            argss.add("create");
            argss.add("invite");
            argss.add("accept");
            argss.add("deny");
            argss.add("leave");
            argss.add("kick");
        }else if (args.length == 2 && "admin".equalsIgnoreCase(args[0])){
            argss.add("createarena");
            argss.add("setarenalobby");
            argss.add("setmin");
            argss.add("setmax");
            argss.add("deletearena");
        }else if ((args.length == 3) && ("admin".equalsIgnoreCase(args[0])) && ("setarenalobby".equalsIgnoreCase(args[1]) || "setmin".equalsIgnoreCase(args[1])
        || "setmax".equalsIgnoreCase(args[1]) || "deletearena".equalsIgnoreCase(args[1]))){
            for (Arena arena : TntTag.arenas){
                argss.add(arena.getName());
            }
        }else if ("createarena".equalsIgnoreCase(args[1])){
            if (args.length == 3){
                argss.add("name");
            }else if (args.length == 4){
                argss.add("minimPlayers");
            }else if (args.length == 5){
                argss.add("maximPlayers");
            }
        }
        else if ((args.length == 3) &&("party".equalsIgnoreCase(args[0])) && ("invite".equalsIgnoreCase(args[1]) || "kick".equalsIgnoreCase(args[1]))){
            for (Player p : Bukkit.getOnlinePlayers()){
                argss.add(p.getName());
            }
        }

        return argss;
    }
}
