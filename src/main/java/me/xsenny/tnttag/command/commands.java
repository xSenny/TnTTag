package me.xsenny.tnttag.command;

import me.xsenny.tnttag.TntTag;
import me.xsenny.tnttag.arena.ArenaMethods;
import me.xsenny.tnttag.color;
import me.xsenny.tnttag.game.GameModels;
import me.xsenny.tnttag.party.PartyMethods;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commands implements CommandExecutor {

    public static TntTag plugin = TntTag.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            System.out.println(TntTag.plugin.getMessages().getString("commands.errors.not-player"));
        }
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 2 && "party".equalsIgnoreCase(args[0])){
                if ("create".equalsIgnoreCase(args[1])){
                    PartyMethods.createParty(p);
                }else if ("leave".equalsIgnoreCase(args[1])){
                    PartyMethods.leaveParty(p);
                }else if ("accept".equalsIgnoreCase(args[1])){
                    PartyMethods.acceptInvite(p);
                }else if ("deny".equalsIgnoreCase(args[1])){
                    PartyMethods.dennyInvite(p);
                }
            }else if (args.length == 3 && "party".equalsIgnoreCase(args[0])){
                if ("invite".equalsIgnoreCase(args[1])){
                    Player player = Bukkit.getPlayer(args[2]);
                    if (player != null){
                        PartyMethods.inviteSomeone(p, player);
                    }else{
                        p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.not-player-online")
                                .replace("[player]", args[2])));
                    }
                }else if ("kick".equalsIgnoreCase(args[1])){
                    Player player = Bukkit.getPlayer(args[2]);
                    if (player != null){
                        PartyMethods.kickPlayer(p, player);
                    }else{
                        p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.not-player-online")
                                .replace("[player]", args[2])));
                    }
                }
            }else if ("admin".equalsIgnoreCase(args[0])){
                if (args.length >= 2){
                    if ("createarena".equalsIgnoreCase(args[1]) && args.length == 5){
                        if (p.hasPermission("tnt.createarena")){
                            try{
                                Integer min = Integer.parseInt(args[3]);
                                Integer max = Integer.parseInt(args[4]);
                                ArenaMethods.createArena(args[2], p, min, max);
                            }catch (NumberFormatException e){
                                p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-number-ok")));
                            }
                        }else{
                            p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-perms")));
                        }
                    }else if ("setarenalobby".equalsIgnoreCase(args[1]) && args.length == 3){
                        if (p.hasPermission("tnt.editarena")){
                            ArenaMethods.setSpawn(args[2], p.getLocation(), p);
                        }else{
                            p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-perms")));
                        }

                    }else if ("setmin".equalsIgnoreCase(args[1]) && args.length == 4){
                        if (p.hasPermission("tnttag.editarena")){
                            try{
                                Integer min = Integer.parseInt(args[3]);
                                ArenaMethods.setMinPlayers(args[2], min, p);
                            }catch (NumberFormatException e){
                                p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-number")));
                            }
                        }else{
                            p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-perms")));
                        }
                    }else if ("setmax".equalsIgnoreCase(args[1]) && args.length == 4){
                        if (p.hasPermission("tnttag.editarena")){
                            try{
                                Integer max = Integer.parseInt(args[3]);
                                ArenaMethods.setMaxPlayers(args[2], max, p);
                            }catch (NumberFormatException e){
                                p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-number")));
                            }
                        }else{
                            p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-perms")));
                        }
                    }else if ("deletearena".equalsIgnoreCase(args[1]) && args.length == 3){
                        if (p.hasPermission("tnttag.deletearena")){
                            ArenaMethods.deleteArena(args[2], p);
                        }else{
                            p.sendMessage(color.colore(plugin.getMessages().getString("commands.errors.no-perms")));
                        }
                    }
                }
            }else if ("join".equalsIgnoreCase(args[0])){
                GameModels.join(p);
            }else if ("leave".equalsIgnoreCase(args[0])){
                GameModels.leaveGame(p);
            }else{
                p.sendMessage("Vezi tu");
            }


        }

        return true;
    }
}
