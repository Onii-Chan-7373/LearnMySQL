package me.beppo.learnmysql;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {

    LearnMySQL plugin;
    public Listeners(LearnMySQL plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        plugin.data.createPlayer(p);
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e){
        if(e.getEntity().getKiller() instanceof Player){
            Player p = (Player) e.getEntity().getKiller();
            plugin.data.addPoints(p.getUniqueId(), 1);
            p.sendMessage("Point added!");
        }
    }

}
