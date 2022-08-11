package me.xsenny.tnttag.game;

import me.xsenny.tnttag.arena.Arena;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> inGamePlayers;
    private GameStat gameStat;
    private int timer;
    private Arena arena;
    private ArrayList<Player> itsPlayers;
    private int rounds;
    private int gameTimer;
    private ArrayList<Player> spectators;



    public Game(ArrayList<Player> inGamePlayers, GameStat gameStat, int timer, Arena arena, ArrayList<Player> itsPlayers, int rounds, int gameTimer, ArrayList<Player> spectators) {
        this.inGamePlayers = inGamePlayers;
        this.gameStat = gameStat;
        this.timer = timer;
        this.arena = arena;
        this.itsPlayers = itsPlayers;
        this.rounds = rounds;
        this.gameTimer = gameTimer;
        this.spectators = spectators;
    }
    public ArrayList<Player> getSpectators(){
        return spectators;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(int gameTimer) {
        this.gameTimer = gameTimer;
    }

    public ArrayList<Player> getItsPlayers() {
        return itsPlayers;
    }

    public void setItsPlayers(ArrayList<Player> itsPlayers) {
        this.itsPlayers = itsPlayers;
    }
    public ArrayList<Player> getInGamePlayers() {
        return inGamePlayers;
    }

    public void setInGamePlayers(ArrayList<Player> inGamePlayers) {
        this.inGamePlayers = inGamePlayers;
    }

    public GameStat getGameStat() {
        return gameStat;
    }

    public void setGameStat(GameStat gameStat) {
        this.gameStat = gameStat;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
