package me.xsenny.tnttag.gameListeners;

import me.xsenny.tnttag.TntTag;
import me.xsenny.tnttag.color;
import me.xsenny.tnttag.game.Game;
import me.xsenny.tnttag.game.GameModels;
import me.xsenny.tnttag.game.GameStat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class GameRunner extends BukkitRunnable {

    public static TntTag plugin = TntTag.plugin;

    @Override
    public void run() {
        if (TntTag.games.size() >= 1){
            try{
                for (Game game : TntTag.games){
                    if (game.getGameStat().equals(GameStat.InGame)){
                        if (game.getGameTimer() > 0){
                            game.setGameTimer(game.getGameTimer()-1);
                            for (Player p : game.getInGamePlayers()){
                                if (p.hasPermission("timer")){
                                    p.sendMessage(String.valueOf(game.getGameTimer()));
                                }
                            }
                        }else if (game.getGameTimer() == 0){
                            GameModels.killPlayers(game);
                            newRound(game);

                        }
                    }
                }
            }catch (ConcurrentModificationException e){
                System.out.println();
            }
        }
    }

    public static int newTimer(int round){
        switch (round){
            case 1 :
                return 90;
            case 2:
                return 67;
            case 3:
                return 56;
            case 4:
                return 46;
            case 5:
                return 35;
            case 6:
                return 28;
            case 7:
                return 23;
            case 8:
                return 20;
            case 100:
                return 2;
            default:
                return 10;
        }
    }
    public static int nrIt(int players){
        if (players == 2){
            return 1;
        }
        return players/3;
    }
    public static void addNewIt(Game game, int nrIt){
        for (int i = 0; i < nrIt; i++){
            if (game.getInGamePlayers().size() > 0){
                int rnd = new Random().nextInt(game.getInGamePlayers().size());
                game.getItsPlayers().add(game.getInGamePlayers().get(rnd));
                for (Player player : game.getInGamePlayers()){
                    player.sendMessage(color.colore(plugin.getMessages().getString("game.new-It")
                            .replace("[player]", game.getInGamePlayers().get(rnd).getName())));
                }for (Player player : game.getSpectators()){
                    player.sendMessage(color.colore(plugin.getMessages().getString("game.new-It")
                            .replace("[player]", game.getInGamePlayers().get(rnd).getName())));
                }
            }
        }
    }
    public static ArrayList<Player> getPlayerNotIt(Game game){
        ArrayList<Player> inGame = game.getInGamePlayers();
        ArrayList<Player> its = game.getItsPlayers();
        ArrayList<Player> get = new ArrayList<>();
        for (Player p : inGame){
            if (!its.contains(p)){
                get.add(p);
            }
        }
        return get;
    }

    public static void newRound(Game game){
        game.setItsPlayers(new ArrayList<>());
        game.setRounds(game.getRounds() + 1);
        game.setGameTimer(newTimer(game.getRounds()));
        addNewIt(game, nrIt(game.getInGamePlayers().size()));
        if (game.getRounds() >= 5){
            for (Player p : game.getInGamePlayers()){
                p.teleport(game.getArena().getArenaLobby());
            }
        }
        for (Player p : game.getItsPlayers()){
            p.getInventory().setHelmet(new ItemStack(Material.TNT));
            p.getInventory().setItemInOffHand(new ItemStack(Material.TNT));
            for (int i = 0; i < 9; i ++){
                if (p.getInventory().getItem(i) != null){
                    p.getInventory().setItem(i, null);
                }
            }
        }
        for(Player p : game.getItsPlayers()){
            //TODO playerIt
            if (TntTag.games.contains(game)){
                playerIt(p);
                System.out.println(p.getName());
                p.sendMessage(color.colore(plugin.getMessages().getString("new-It")));
            }
        }
    }

    public static void playerIt(Player p){
        for (int i = 0; i < 9; i++){
            p.getInventory().setItem(i, new ItemStack(Material.TNT));
        }
        p.getInventory().setHelmet(new ItemStack(Material.TNT));
        p.getInventory().setItemInOffHand(new ItemStack(Material.TNT));
    }

    public static boolean isIt(Player p){

        if (GameModels.isPlayerInGame(p)){
            Game game = GameModels.getGame(p);
            if (game.getItsPlayers().contains(p)){
                return true;
            }else{
                return false;
            }
        }

        return false;
    }
}
