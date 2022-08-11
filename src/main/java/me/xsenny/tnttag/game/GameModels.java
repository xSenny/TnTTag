package me.xsenny.tnttag.game;

import me.xsenny.tnttag.TntTag;
import me.xsenny.tnttag.arena.Arena;
import me.xsenny.tnttag.arena.ArenaMethods;
import me.xsenny.tnttag.color;
import me.xsenny.tnttag.gameListeners.GameRunner;
import me.xsenny.tnttag.party.PartyMethods;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.awt.*;
import java.util.ArrayList;

public class GameModels {

    public static TntTag plugin = TntTag.plugin;

    public static boolean doesExistsArena(){
        for (Arena arena : TntTag.arenas){
            if (!ArenaMethods.isArenaOcupat(arena)){
                return true;
            }
        }
        return false;
    }

    public static Arena getArena(){
        for (Arena arena : TntTag.arenas){
            if (!ArenaMethods.isArenaOcupat(arena)){
                return arena;
            }
        }
        return null;
    }

    public static Game createGame(Player p){
        if (doesExistsArena()){
            Arena arena = getArena();
            if (arena != null){
                Game game = new Game(new ArrayList<>(), GameStat.WaitingForPlayers, -1, arena, new ArrayList<>(), 0, -1, new ArrayList<>());
                TntTag.games.add(game);
                return game;
            }else{
            }
        }else{
        }
        return null;
    }

    public static void join(Player p){
        if (TntTag.games.size() >= 1){
            if (getGame(p) == null){
                for (Game game : TntTag.games){
                    if (game.getGameStat().equals(GameStat.WaitingForPlayers) || game.getGameStat().equals(GameStat.Countdown)){
                        if (game.getInGamePlayers().size() <= game.getArena().getMaxPlayers()) {
                            if (PartyMethods.isAPlayerInAParty(p)){
                                if (PartyMethods.isPlayerLeader(p)) {
                                    for (Player player : PartyMethods.getPartyForPlayer(p).getPlayersInParty()) {
                                        game.getInGamePlayers().add(player);
                                        p.teleport(game.getArena().getArenaLobby());
                                        for (Player players : game.getInGamePlayers()) {
                                            players.sendMessage(color.colore(plugin.getMessages().getString("game.player-joined")
                                                    .replace("[player]", player.getName())));
                                        }
                                        if (game.getInGamePlayers().size() == game.getArena().getMinPlayers()) {
                                            game.setTimer(30);
                                            game.setGameStat(GameStat.Countdown);
                                        }
                                    }
                                }else{
                                    p.sendMessage(color.colore(plugin.getMessages().getString("game.not-leader")));
                                }
                            }else{
                            game.getInGamePlayers().add(p);
                            p.teleport(game.getArena().getArenaLobby());
                            for (Player player : game.getInGamePlayers()) {
                                player.sendMessage(color.colore(plugin.getMessages().getString("game.player-joined")
                                        .replace("[player]", p.getName())));
                            }
                                System.out.println(game.getInGamePlayers().size());
                                System.out.println(game.getArena().getMinPlayers());
                            if (game.getInGamePlayers().size() == game.getArena().getMinPlayers()) {
                                game.setTimer(30);
                                game.setGameStat(GameStat.Countdown);
                            }}
                            break;
                        }
                    }else{
                        if (getGame(p) == null){
                            Game game1 = createGame(p);
                            if (game1 == null) {p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Nu exista nici o arena libera"));}
                            else if(game1.getGameStat().equals(GameStat.WaitingForPlayers) || game1.getGameStat().equals(GameStat.Countdown)){
                                if (game1.getInGamePlayers().size() <= game.getArena().getMaxPlayers()){
                                    if (PartyMethods.isAPlayerInAParty(p)){
                                        if (PartyMethods.isPlayerLeader(p)) {
                                            for (Player player : PartyMethods.getPartyForPlayer(p).getPlayersInParty()) {
                                                game.getInGamePlayers().add(player);
                                                p.teleport(game.getArena().getArenaLobby());
                                                for (Player players : game.getInGamePlayers()) {
                                                    players.sendMessage(color.colore(plugin.getMessages().getString("game.player-joined")
                                                            .replace("[player]", player.getName())));
                                                }
                                                System.out.println(game.getInGamePlayers().size());
                                                System.out.println(game.getArena().getMinPlayers());
                                                if (game.getInGamePlayers().size() == game.getArena().getMinPlayers()) {
                                                    game.setTimer(plugin.getSettings().getInt("game-countdown"));
                                                    game.setGameStat(GameStat.Countdown);
                                                }
                                            }
                                        }else{
                                            p.sendMessage("Nu esti leader");
                                        }
                                    }else{
                                    game1.getInGamePlayers().add(p);
                                    p.teleport(game1.getArena().getArenaLobby());
                                    for (Player player : game1.getInGamePlayers()){
                                        player.sendMessage(color.colore(plugin.getMessages().getString("game.player-joined")
                                                .replace("[player]", p.getName())));
                                    }
                                        System.out.println(game.getInGamePlayers().size());
                                        System.out.println(game.getArena().getMinPlayers());
                                    if (game.getInGamePlayers().size() == game.getArena().getMinPlayers()){
                                        game1.setTimer(plugin.getSettings().getInt("game-countdown"));
                                        game1.setGameStat(GameStat.Countdown);
                                    }}
                                }else{

                                }
                            }else{

                            }
                        }else{

                        }
                    }
                }
            }else{
                p.sendMessage(color.colore(plugin.getMessages().getString("game.already")));
            }
        }else{
            if (getGame(p) == null){
                Game game = createGame(p);
                if (game == null) {p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color.colore(plugin.getMessages().getString("game.not-arenas"))));}
                else if (game.getGameStat().equals(GameStat.WaitingForPlayers) || game.getGameStat().equals(GameStat.Countdown)){
                    if (game.getInGamePlayers().size() <= game.getArena().getMaxPlayers()){
                        if (PartyMethods.isAPlayerInAParty(p)){
                            if (PartyMethods.isPlayerLeader(p)) {
                                for (Player player : PartyMethods.getPartyForPlayer(p).getPlayersInParty()) {
                                    game.getInGamePlayers().add(player);
                                    p.teleport(game.getArena().getArenaLobby());
                                    for (Player players : game.getInGamePlayers()) {
                                        players.sendMessage(color.colore(plugin.getMessages().getString("game.player-joined")
                                                .replace("[player]", player.getName())));
                                    }
                                    System.out.println(game.getInGamePlayers().size());
                                    System.out.println(game.getArena().getMinPlayers());
                                    System.out.println(game.getInGamePlayers().size());
                                    System.out.println(game.getArena().getMinPlayers());
                                    if (game.getInGamePlayers().size() == game.getArena().getMinPlayers()) {
                                        game.setTimer(plugin.getSettings().getInt("game-countdown"));
                                        game.setGameStat(GameStat.Countdown);
                                    }
                                }
                            }else{
                                p.sendMessage(color.colore(plugin.getMessages().getString("game.not-leader")));
                            }
                        }else{
                        game.getInGamePlayers().add(p);
                        p.teleport(game.getArena().getArenaLobby());
                        for (Player player : game.getInGamePlayers()){
                            player.sendMessage(color.colore(plugin.getMessages().getString("game.player-joined")
                                    .replace("[player]", player.getName())));
                        }
                            System.out.println(game.getInGamePlayers().size());
                            System.out.println(game.getArena().getMinPlayers());
                        if (game.getInGamePlayers().size() == game.getArena().getMinPlayers()){
                            game.setTimer(plugin.getSettings().getInt("game-countdown"));
                            game.setGameStat(GameStat.Countdown);
                        }}
                    }else{

                    }
                }else{

                }
            }else{
                p.sendMessage(color.colore(plugin.getMessages().getString("game.already")));
            }
        }
    }

    public static void startGame(Game game){
        for (Player p : game.getInGamePlayers()){
            game.setGameStat(GameStat.InGame);
            game.setGameTimer(GameRunner.newTimer(100));
            p.teleport(game.getArena().getArenaLobby());
            p.sendMessage(color.colore(plugin.getMessages().getString("game.start")));
            TntTag.isPlayerPlaying.put(p, true);
        }
        GameRunner.addNewIt(game, GameRunner.nrIt(3));
    }

    public static Game getGame(Player p){
        for (Game game : TntTag.games){
            if (game.getInGamePlayers().contains(p)){
                return game;
            }
        }
        return null;
    }

    public static void leaveGame(Player p){
        if (!PartyMethods.isAPlayerInAParty(p)){
            Game game = getGame(p);
            if (game !=null){
                game.getInGamePlayers().remove(p);
                p.sendMessage(color.colore(plugin.getMessages().getString("game.leave-you")));
                for (Player player : game.getInGamePlayers()){
                    player.sendMessage(color.colore(plugin.getMessages().getString("game.leave-ann")
                            .replace("[player]", p.getName())));
                }
                for (Player players : Bukkit.getOnlinePlayers()){
                    if (!players.canSee(p)){
                        players.showPlayer(p);
                    }
                }
                if (game.getInGamePlayers().size() < 2){
                    game.setGameStat(GameStat.WaitingForPlayers);
                }
                for (Player players : Bukkit.getOnlinePlayers()){
                    if (!players.canSee(p)){
                        players.showPlayer(p);
                    }
                }
            }else{
                p.sendMessage(color.colore(plugin.getMessages().getString("game.not-in-game")));
            }
        }else{
            if (PartyMethods.isPlayerLeader(p)){
                for (Player player : PartyMethods.getPartyForPlayer(p).getPlayersInParty()){
                    Game game = getGame(p);
                    if (game !=null){
                        game.getInGamePlayers().remove(player);
                        player.sendMessage(color.colore(plugin.getMessages().getString("game.leave-you")));
                        for (Player players : game.getInGamePlayers()){
                            players.sendMessage(color.colore(plugin.getMessages().getString("game.leave-ann")
                                    .replace("[player]", p.getName())));
                        }
                        if (game.getInGamePlayers().size() < 2){
                            game.setGameStat(GameStat.WaitingForPlayers);
                        }
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()){
                    if (!players.canSee(p)){
                        players.showPlayer(p);
                    }
                }
            }else{
                p.sendMessage(color.colore(plugin.getMessages().getString("game.not-leader")));
            }

        }
    }

    public static void leaveAsSpectator(Player player){
        Game game = GameModels.getGame(player);
        if (game != null){
            if (game.getSpectators().contains(player)){
                game.getSpectators().remove(player);
                for (Player p : Bukkit.getOnlinePlayers()){
                    p.showPlayer(player);
                }
            }
        }
    }

    public static void playerLeaveServer(Player p){
        Game game = GameModels.getGame(p);
            if (game == null){
                if (PartyMethods.isAPlayerInAParty(p)){
                    if (!PartyMethods.isPlayerLeader(p)){
                        for (Player player : PartyMethods.getPartyForPlayer(p).getPlayersInParty()){
                            player.sendMessage(color.colore(plugin.getMessages().getString("events.left-server-in-party")
                                    .replace("[player]", p.getName())));
                        }
                        PartyMethods.getPartyForPlayer(p).getPlayersInParty().remove(p);
                    }else{
                        for (Player player : PartyMethods.getPartyForPlayer(p).getPlayersInParty()){
                            player.sendMessage(color.colore(plugin.getMessages().getString("events.left-server-as-party-leader")
                                    .replace("[player]", p.getName())));
                        }
                        TntTag.parties.remove(PartyMethods.getPartyForPlayer(p));
                    }
                }
            }else{
                if (PartyMethods.isAPlayerInAParty(p)){
                    if (!PartyMethods.isPlayerLeader(p)){
                        for (Player player : PartyMethods.getPartyForPlayer(p).getPlayersInParty()){
                            player.sendMessage(color.colore(plugin.getMessages().getString("events.left-server-in-party")
                                    .replace("[player]", p.getName())));
                        }
                        PartyMethods.getPartyForPlayer(p).getPlayersInParty().remove(p);
                        if (game.getItsPlayers().contains(p)){
                            game.getItsPlayers().remove(p);
                        }
                        for (Player player : game.getInGamePlayers()){
                            player.sendMessage(color.colore(plugin.getMessages().getString("game.leave-ann")
                                    .replace("[player]", p.getName())));
                        }
                        game.getInGamePlayers().remove(p);
                        if (game.getInGamePlayers().size() < 2){
                            game.setGameStat(GameStat.WaitingForPlayers);
                        }
                    }else{
                        for (Player player : PartyMethods.getPartyForPlayer(p).getPlayersInParty()){
                            player.sendMessage(color.colore(plugin.getMessages().getString("events.left-server-as-party-leader")
                                    .replace("[player]", p.getName())));
                        }
                        TntTag.parties.remove(PartyMethods.getPartyForPlayer(p));
                        if (game.getItsPlayers().contains(p)){
                            game.getItsPlayers().remove(p);
                        }
                        for (Player player : game.getInGamePlayers()){
                            player.sendMessage(color.colore(plugin.getMessages().getString("game.leave-ann")
                                    .replace("[player]", p.getName())));
                        }
                        game.getInGamePlayers().remove(p);
                        if (game.getInGamePlayers().size() < 2){
                            game.setGameStat(GameStat.WaitingForPlayers);
                        }
                    }
                }else{
                    if (game.getItsPlayers().contains(p)){
                        game.getItsPlayers().remove(p);
                    }
                    for (Player player : game.getInGamePlayers()){
                        player.sendMessage(color.colore(plugin.getMessages().getString("game.leave-ann")
                                .replace("[player]", p.getName())));
                    }
                    game.getInGamePlayers().remove(p);
                    if (game.getInGamePlayers().size() < 2){
                        game.setGameStat(GameStat.WaitingForPlayers);
                    }
                }
            }
        }

        public static void clearInventory(Player p){
        for (int i = 0; i < p.getInventory().getSize(); i++){
            if (p.getInventory().getItem(i) != null){
                p.getInventory().setItem(i, null);
            }
        }
        }


    public static boolean isPlayerInGame(Player p){
        return getGame(p) != null;
    }

    public static void deleteGame(Game game){
        TntTag.games.remove(game);
    }

    public static void stafeta(Player old, Player newTnt){
        Game game = GameModels.getGame(old);
        assert game != null;
        game.getItsPlayers().remove(old);
        game.getItsPlayers().add(newTnt);
        old.sendMessage(color.colore(plugin.getMessages().getString("game.baton")
                .replace("[player]", newTnt.getName())));
        newTnt.sendMessage(color.colore(plugin.getMessages().getString("game.newIt")));
        for (Player player : game.getInGamePlayers()){
            player.sendMessage(color.colore(plugin.getMessages().getString("new-It-ann")
                    .replace("[player]", newTnt.getName())));
        }for (Player player : game.getSpectators()){
            player.sendMessage(color.colore(plugin.getMessages().getString("new-It-ann")
                    .replace("[player]", newTnt.getName())));
        }
        newTnt.getInventory().setHelmet(new ItemStack(Material.TNT));
        spawnFirework(newTnt.getLocation(), 1);
        for (int i = 0; i < old.getInventory().getSize(); i++){
            old.getInventory().setItem(i, null);
        }for (int i = 0; i < 9; i++){
            newTnt.getInventory().setItem(i, new ItemStack(Material.TNT));
        }
        newTnt.getInventory().setItemInOffHand(new ItemStack(Material.TNT));
    }

    public static void spawnFirework(Location loc, int amount){
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();
    }

    public static void killPlayers(Game game){
        if (game.getRounds() >= 1){
            for (Player p : game.getItsPlayers()){
                Location location = game.getArena().getArenaLobby();
                p.teleport(location);
                p.sendMessage(color.colore(plugin.getMessages().getString("game.lost")));
                for (Player player : game.getInGamePlayers()){
                    player.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20F, 20F);
                    player.sendMessage(color.colore(plugin.getMessages().getString("player-lost")
                            .replace("[player]", p.getName())));
                }for (Player player : game.getSpectators()){
                    player.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20F, 20F);
                    player.sendMessage(color.colore(plugin.getMessages().getString("player-lost")
                            .replace("[player]", p.getName())));
                }
                for (int i = 0; i < p.getInventory().getSize(); i++){
                    if (p.getInventory().getItem(i) != null){
                        p.getInventory().setItem(i, null);
                    }
                }
                game.getInGamePlayers().remove(p);
                for (Player player : game.getInGamePlayers()){
                    player.hidePlayer(p);
                }
                p.setAllowFlight(true);
                game.getSpectators().add(p);
            }
            if (game.getInGamePlayers().size() == 1){
                stop(game);
            }
        }
    }

    public static void stop(Game game){
        deleteGame(game);
        for (Player p : game.getInGamePlayers()){
            p.sendMessage(color.colore(plugin.getMessages().getString("you-won")));
            for (Player player : game.getSpectators()){
                player.sendMessage(color.colore(plugin.getMessages().getString("player-won")
                        .replace("[player]", p.getName())));
            }
            p.teleport(game.getArena().getArenaLobby());
            for (int i = 0; i < p.getInventory().getSize(); i++){
                p.getInventory().setItem(i, null);
            }
            for (Player players : Bukkit.getOnlinePlayers()){
                for (Player player : game.getSpectators()){
                    players.showPlayer(player);
                }
            }
        }
    }
}
