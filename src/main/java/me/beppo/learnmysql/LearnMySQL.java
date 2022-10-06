package me.beppo.learnmysql;

import me.beppo.learnmysql.sql.MySQL;
import me.beppo.learnmysql.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class LearnMySQL extends JavaPlugin {

    public MySQL SQL;
    public SQLGetter data;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.SQL = new MySQL();
        this.data = new SQLGetter(this);
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Database not connected");
        }
        if(SQL.isConnected()){
            Bukkit.getLogger().info("Database is connected");
            data.createTable();
            this.getServer().getPluginManager().registerEvents(new Listeners(this), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SQL.disconnect();
    }
}
