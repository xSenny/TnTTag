package me.xsenny.tnttag.party;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Party {

    private Player leader;
    private ArrayList<Player> playersInParty;


    public Party(Player leader, ArrayList<Player> playersInParty) {
        this.leader = leader;
        this.playersInParty = playersInParty;
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public ArrayList<Player> getPlayersInParty() {
        return playersInParty;
    }

    public void setPlayersInParty(ArrayList<Player> playersInParty) {
        this.playersInParty = playersInParty;
    }
}
