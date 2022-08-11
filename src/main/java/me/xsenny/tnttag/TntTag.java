package me.xsenny.tnttag;

import me.xsenny.tnttag.arena.Arena;
import me.xsenny.tnttag.arena.ArenaMethods;
import me.xsenny.tnttag.command.TabExecutor;
import me.xsenny.tnttag.command.commands;
import me.xsenny.tnttag.game.Game;
import me.xsenny.tnttag.game.GameModels;
import me.xsenny.tnttag.game.GameStat;
import me.xsenny.tnttag.gameListeners.GameListeners;
import me.xsenny.tnttag.gameListeners.GameRunner;
import me.xsenny.tnttag.party.PartyMethods;
import me.xsenny.tnttag.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public final class TntTag extends JavaPlugin {

    public static TntTag plugin;
    public static ArrayList<Arena> arenas = new ArrayList<>();
    public static ArrayList<Game> games = new ArrayList<>();
    public static ArrayList<Party> parties = new ArrayList<>();
    public static HashMap<Player, Boolean> isPlayerPlaying = new HashMap<>();
    public File messages = null;
    public YamlConfiguration messagesC = new YamlConfiguration();
    public File settings = null;
    public YamlConfiguration settingsC = new YamlConfiguration();


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        //Files
        messages = new File(getDataFolder(), "messages.yml");
        settings = new File(getDataFolder(), "settings.yml");
        createFiles();
        loadYaml();

        //Party Timer
        Bukkit.getScheduler().runTaskTimer(this, ()->{
            for (Player p : Bukkit.getOnlinePlayers()){
                if (PartyMethods.inviteTimer.get(p) != null){
                    if (PartyMethods.inviteTimer.get(p) > 0){
                        PartyMethods.inviteTimer.put(p, PartyMethods.inviteTimer.get(p)-1);
                    }else if (PartyMethods.inviteTimer.get(p) == 0){
                        PartyMethods.inviteTimer.put(p, -1);
                        p.sendMessage(color.colore(plugin.getMessages().getString("commands.party.invite-lost")
                                .replace("[player]", PartyMethods.invitedPlayer.get(p).getName())));
                        PartyMethods.invitedPlayer.get(p).sendMessage(color.colore(plugin.getMessages().getString("commands.party.invite-lost-1")
                                .replace("[party]", p.getName())));
                        PartyMethods.invitedPlayer.put(p, null);
                    }
                }
            }
        }, 0L, 20L);

        Bukkit.getScheduler().runTaskTimer(this, ()->{
            for (Game game : games){
                for (Player player : game.getInGamePlayers()){
                    player.setHealth(20.0);
                    player.setFoodLevel(20);
                }
            }
        }, 0L, 20L);
        //Game Countdown
        Bukkit.getScheduler().runTaskTimer(this, ()->{
            for (Game game : games) {
                if (game.getGameStat() == GameStat.Countdown) {
                    if (game.getTimer() > 0) {
                        game.setTimer(game.getTimer() - 1);
                        if (game.getTimer() == 20 || game.getTimer() == 10 || game.getTimer() <= 5){
                            for (Player p : game.getInGamePlayers()) {
                                p.sendTitle(String.valueOf(game.getTimer()), "", 20, 20, 20);
                                p.sendMessage(String.valueOf(game.getTimer()));
                            }
                        }
                    } else if (game.getTimer() == 0) {
                        game.setTimer(-1);
                        game.setGameStat(GameStat.InGame);
                        //TODO start game
                        GameModels.startGame(game);
                    }
                }
            }
        }, 0L, 20L);

        //GameRunner

        BukkitTask gameRunner = new GameRunner().runTaskTimer(this, 0L, 20L);

        //Command
        getCommand("tnt").setExecutor(new commands());
        getCommand("tnt").setTabCompleter(new TabExecutor());

        //Listener
        getServer().getPluginManager().registerEvents(new GameListeners(), this);

        //Data
        try {
            ArenaMethods.loadBlocks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (getServer().getPluginManager().isPluginEnabled(this)){
            try {
                ArenaMethods.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Files
    public void createFiles(){
        if (!messages.exists()){
            saveResource("messages.yml", false);
        }if (!settings.exists()){
            saveResource("settings.yml", false);
        }
    }

    public void loadYaml() {
        try {
            messagesC.load(messages);
            settingsC.load(settings);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("Failed to load config files");
            e.printStackTrace();
        }
    }
    public YamlConfiguration getMessages(){
        return messagesC;
    }
    public YamlConfiguration getSettings(){
        return settingsC;
    }
    public void saveMessages() {
        try {
            messagesC.save(messages);
            settingsC.save(settings);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Couldn't save config files");
        }
    }
    public void reloadMessages() {
        messagesC = YamlConfiguration.loadConfiguration(messages);
        settingsC = YamlConfiguration.loadConfiguration(settings);
    }


}
