package me.xsenny.tnttag.arena;

import org.bukkit.Location;

public class Arena {

    private String name;
    private GsonLocation arenaLobby;
    private Integer minPlayers;
    private Integer maxPlayers;
    private int spawn;
    private boolean ocupat;



    public Arena(String name, Location arenaLobby, Integer minPlayers, Integer maxPlayers) {
        this.name = name;
        this.arenaLobby = new GsonLocation(arenaLobby);
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getArenaLobby() {
        return arenaLobby.toLocation();
    }

    public void setArenaLobby(Location location) {
        this.arenaLobby = new GsonLocation(location);
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }


    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getSpawn() {
        return spawn;
    }

    public void setSpawn(int spawn) {
        this.spawn = spawn;
    }


}
