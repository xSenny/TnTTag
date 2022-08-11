package me.xsenny.tnttag.arena;

import com.google.gson.Gson;
import me.xsenny.tnttag.TntTag;
import me.xsenny.tnttag.color;
import me.xsenny.tnttag.game.Game;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArenaMethods {

    public static TntTag plugin = TntTag.plugin;


    public static boolean minMax(Integer min, Integer max){
        if (min >= 3){
            if (min < max){
                if(max - min >= 2){
                    return true;
                }
            }
        }
        return false;
    }

    public static void createArena(String name, Player p, Integer min, Integer max){
        if (minMax(min, max)){
            Arena arena = new Arena(name, p.getLocation(), min, max);
            TntTag.arenas.add(arena);
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.admin.create")
                    .replace("[name]", name)));
            try {
                save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-number-ok")));
        }
    }

    public static Arena getArena(String name){
        for (Arena arena : TntTag.arenas){
            if (arena.getName().equalsIgnoreCase(name)){
                return arena;
            }
        }
        return null;
    }

    public static void setSpawn(String name, Location location, Player p){
        Arena arena = getArena(name);
        if (arena != null){
            arena.setArenaLobby(location);
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.admin.spawn-set")));
            try {
                save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.admin.not-exists")));
        }
    }

    public static void setMinPlayers(String name, Integer min, Player p){
        Arena arena = getArena(name);
        if (arena != null){
            if (minMax(min, arena.getMaxPlayers())){
                arena.setMinPlayers(min);
                try {
                    save();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.sendMessage(color.colore(plugin.getMessages().getString("commands.admin.min-set")));
            }
        }else{
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.admin.not-exists")));
        }
    }

    public static void setMaxPlayers(String name, Integer max, Player p){
        Arena arena = getArena(name);
        if (arena != null){
            if (minMax(arena.getMinPlayers(), max)){
                arena.setMaxPlayers(max);
                try {
                    save();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.sendMessage(color.colore(plugin.getMessages().getString("commands.admin.max-set")));
            }
        }else{
            p.sendMessage(color.colore(plugin.getMessages().getString("not-exists")));
        }
    }


    public static void save() throws IOException {

        Gson gson = new Gson();
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/Arenas.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(TntTag.arenas, writer);
        writer.flush();
        writer.close();
        System.out.println(plugin.getMessages().getString("commands.admin.saved"));

    }

    public static boolean isArenaOcupat(Arena arena){
        for (Game game : TntTag.games){
            if (game.getArena().equals(arena)){
                return true;
            }
        }
        return false;
    }

    public static void loadBlocks() throws IOException {

        Gson gson = new Gson();
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/Arenas.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            Arena[] n = gson.fromJson(reader, Arena[].class);
            TntTag.arenas = new ArrayList<>(Arrays.asList(n));
            System.out.println(plugin.getMessages().getString("commands.admin.loaded"));
        }

    }
    public static void deleteArena(String name, Player p){
        Arena arena = getArena(name);
        if (arena != null){
            TntTag.arenas.remove(arena);
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.admin.removed")
                    .replace("[arena]", arena.getName())));
            try {
                save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.admin" +
                    ".not-exists")));
        }
    }
}
