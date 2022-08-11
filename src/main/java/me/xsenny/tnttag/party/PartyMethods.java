package me.xsenny.tnttag.party;

import me.xsenny.tnttag.TntTag;
import me.xsenny.tnttag.color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PartyMethods {

    public static TntTag plugin = TntTag.plugin;

    public static HashMap<Player, Player> invitedPlayer = new HashMap<>();
    public static HashMap<Player, Integer> inviteTimer = new HashMap<>();

    public static void createParty(Player leader) {
        if (!PartyMethods.isAPlayerInAParty(leader)){
            ArrayList<Player> playerArrayList = new ArrayList<>();
            playerArrayList.add(leader);
            Party party = new Party(leader, playerArrayList);
            TntTag.parties.add(party);
            leader.sendMessage(color.colore(plugin.getMessages().getString("commands.party.create")));
        }else{
            leader.sendMessage(color.colore(plugin.getMessages().getString("commands.party.create-failed")));
        }
    }

    public static boolean isAPlayerInAParty(Player p) {
        for (Party party : TntTag.parties) {
            if (party.getPlayersInParty().contains(p)) {
                return true;
            }
        }
        return false;
    }

    public static Party getPartyForPlayer(Player p) {
        for (Party party : TntTag.parties) {
            if (party.getPlayersInParty().contains(p)) {
                return party;
            }
        }
        return null;
    }

    public static boolean isPlayerLeader(Player p) {
        Party party = getPartyForPlayer(p);
        if (party == null) return false;
        if (party.getLeader().equals(p)) {
            return true;
        }
        return false;
    }

    public static void inviteSomeone(Player inviter, Player requested) {
        if (isAPlayerInAParty(inviter)) {
            if (isPlayerLeader(inviter)) {
                if (!isAPlayerInAParty(requested)) {
                    if (inviter.equals(requested)){
                        inviter.sendMessage(color.colore(plugin.getMessages().getString("commands.party.invite-failed")));
                    }
                    String mess = plugin.getMessages().getString("commands.party.invite-receive");
                    mess = mess.replace("[player]", inviter.getName());
                    requested.sendMessage(color.colore(mess));
                    invitedPlayer.put(inviter, requested);
                    inviteTimer.put(inviter, plugin.getSettings().getInt("party-invite-countdown"));
                    String messs = plugin.getMessages().getString("commands.party.invite");
                    messs = messs.replace("[player]", requested.getName());
                    inviter.sendMessage(color.colore(messs));
                }else{
                    String mess = plugin.getMessages().getString("commands.party.is-already");
                    mess = mess.replace("[player]", requested.getName());
                    inviter.sendMessage(color.colore(mess));
                }
            } else {
                inviter.sendMessage(color.colore(plugin.getMessages().getString("commands.party.not-leader")));
            }
        } else {
            inviter.sendMessage(color.colore(plugin.getMessages().getString("commands.party.not-in-a-party")));
        }
    }

    public static Player whoInvited(Player p) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (invitedPlayer.get(player) != null){
                if (invitedPlayer.get(player).equals(p)){
                    return player;
                }
            }
        }
        return null;
    }

    public static void acceptInvite(Player p) {
        if (!isAPlayerInAParty(p)) {
            Player player = whoInvited(p);
            if (player != null) {
                invitedPlayer.put(player, null);
                inviteTimer.put(player, -1);
                if (getPartyForPlayer(player) != null) {
                    getPartyForPlayer(player).getPlayersInParty().add(p);
                    String mes = plugin.getMessages().getString("commands.party.accept-invite");
                    mes = mes.replace("[player]", player.getDisplayName());
                    p.sendMessage(color.colore(mes));
                    mes = plugin.getMessages().getString("commands.party.accept").replace("[player]", p.getName());
                    player.sendMessage(color.colore(mes));
                } else {
                    p.sendMessage(color.colore(plugin.getMessages().getString("commands.party.has-left")));
                    String mes = plugin.getMessages().getString("commands.party.you-left")
                                    .replace("[player]", p.getName());
                    player.sendMessage(color.colore(mes));
                }
            } else {
                p.sendMessage(color.colore(plugin.getMessages().getString("commands.party.no-invite")));
            }
        } else {
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.party.another-party")));
        }
    }

    public static void dennyInvite(Player p) {
        Player player = whoInvited(p);
        if (player != null) {
            invitedPlayer.put(player, null);
            inviteTimer.put(player, -1);
            String mes = plugin.getMessages().getString("commands.party.deny")
                            .replace("[player]", player.getDisplayName());
            p.sendMessage(color.colore(mes));
            mes = plugin.getMessages().getString("commands.party.deny-your")
                    .replace("[player]",p.getName());
            player.sendMessage(color.colore(mes));
        } else {
            p.sendMessage(color.colore(plugin.getMessages().getString("no-invite")));
        }
    }

    public static void leaveParty(Player p) {
        Party party = getPartyForPlayer(p);
        if (party == null) return;
        if (isPlayerLeader(p)) {
            party.getPlayersInParty().remove(p);
            for (Player player : party.getPlayersInParty()) {
                player.sendMessage(color.colore(plugin.getMessages().getString("commands.party.leader-left")
                        .replace("[player]", p.getName())));
            }
            TntTag.parties.remove(party);
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.party.leader-party-pv")));
        }else{
            party.getPlayersInParty().remove(p);
            p.sendMessage(color.colore(plugin.getMessages().getString("left")));
            for (Player player : party.getPlayersInParty()) {
                player.sendMessage(color.colore(plugin.getMessages().getString("commands.party.left-announcement")
                        .replace("[player]", p.getName())));
            }
        }
    }
    public static void kickPlayer(Player leader, Player p){
        Party party = getPartyForPlayer(leader);
        if (leader.equals(p)){
            leader.sendMessage(color.colore(plugin.getMessages().getString("commands.party.kick-yourself")));
        }
        if (party == null) return;
        if (isPlayerLeader(leader)){
            party.getPlayersInParty().remove(p);
            for (Player player : party.getPlayersInParty()) {
                player.sendMessage(color.colore(plugin.getMessages().getString("commands.party.kick-ann")
                        .replace("[leader]", leader.getName())
                        .replace("[player]", p.getName())));
            }
            p.sendMessage(color.colore(plugin.getMessages().getString("commands.party.kick")
                    .replace("[player]", leader.getName())));
        }else{
            leader.sendMessage(color.colore(plugin.getMessages().getString("commands.party.no-perm")));
        }
    }
}

