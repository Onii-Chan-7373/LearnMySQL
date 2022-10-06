package me.beppo.learnmysql.sql;

import me.beppo.learnmysql.LearnMySQL;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class SQLGetter {

    private LearnMySQL plugin;
    public SQLGetter(LearnMySQL plugin){
        this.plugin = plugin;
    }

    public void createTable(){
        PreparedStatement ps;
        try{
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS mobpoints " +
                    "(name VARCHAR(100), uuid VARCHAR(100), points INT(100), PRIMARY KEY (name))");
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createPlayer(Player p ){
        try {
            UUID uuid = p.getUniqueId();
            if(!exists(uuid)){
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT INTO mobpoints"
                    + "(name, uuid) VALUES (?,?)");
                ps2.setString(1,p.getName());
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();

                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM mobpoints WHERE uuid=?");
            ps.setString(1, uuid.toString());

            ResultSet result = ps.executeQuery();
            if(result.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void addPoints(UUID uuid, int points){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE mobpoints SET points=? WHERE uuid=?");
            ps.setInt(1, (getPoints(uuid) + points));
            ps.setString(2,uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getPoints(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT points FROM mobpoints WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int points = 0;
            if(rs.next()){
                points = rs.getInt("points");
                return points;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

}
