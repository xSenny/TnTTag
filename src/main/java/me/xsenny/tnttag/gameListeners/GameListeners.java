package me.xsenny.tnttag.gameListeners;

import me.xsenny.tnttag.TntTag;
import me.xsenny.tnttag.color;
import me.xsenny.tnttag.game.Game;
import me.xsenny.tnttag.game.GameModels;
import me.xsenny.tnttag.game.GameStat;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListeners implements Listener {

    public static TntTag plugin = TntTag.plugin;
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            if (e.getEntity() instanceof Player){
                Player damager = (Player) e.getDamager();
                Player entity = (Player) e.getEntity();
                if (GameModels.isPlayerInGame(damager) || GameModels.isPlayerInGame(entity)){
                    if (GameModels.isPlayerInGame(damager)){
                        if (GameModels.getGame(damager).getGameStat().equals(GameStat.WaitingForPlayers)||
                                GameModels.getGame(damager).getGameStat().equals(GameStat.Countdown)){
                            e.setCancelled(true);
                        }
                    }
                    if (GameModels.isPlayerInGame(entity)){
                        if (GameModels.getGame(entity).getGameStat().equals(GameStat.WaitingForPlayers) ||
                                GameModels.getGame(entity).getGameStat().equals(GameStat.Countdown)){
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            if (e.getEntity() instanceof Player){
                Player damager = (Player) e.getDamager();
                Player entity = (Player) e.getEntity();
                if (GameModels.isPlayerInGame(damager) && GameModels.isPlayerInGame(entity)){
                    if (isPlayersInSameMatch(damager, entity)){
                        if (GameRunner.isIt(damager)){
                            GameModels.stafeta(damager, entity);
                        }
                    }
                }
            }
        }
    }

    public static boolean isPlayersInSameMatch(Player one, Player two){
        if (GameModels.getGame(one).equals(GameModels.getGame(two))){
            return true;
        }
        return false;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (GameModels.isPlayerInGame(e.getPlayer())){
            Game game = GameModels.getGame(p);
            GameModels.playerLeaveServer(p);
            if (game.getInGamePlayers().size() == 1){
                GameModels.stop(game);
            }
            //Iese un jucator random
            for (Player player : Bukkit.getOnlinePlayers()){
                if (!p.canSee(player)){
                    p.showPlayer(player);
                }
            }

            //Iese unul invizibil
            for (Player players : Bukkit.getOnlinePlayers()){
                if (!players.canSee(p)){
                    players.showPlayer(p);
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (!p.canSee(e.getPlayer())){
                p.showPlayer(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if (GameModels.isPlayerInGame(e.getPlayer())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(color.colore(plugin.getMessages().getString("events.place")));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (GameModels.isPlayerInGame(e.getPlayer())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(color.colore(plugin.getMessages().getString("events.break")));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (GameModels.isPlayerInGame((Player) e.getWhoClicked())){
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(color.colore(plugin.getMessages().getString("events.click")));
        }
    }


}
