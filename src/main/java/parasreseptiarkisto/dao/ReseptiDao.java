package parasreseptiarkisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import parasreseptiarkisto.database.Database;
import parasreseptiarkisto.domain.Resepti;
import parasreseptiarkisto.domain.ReseptinAinesosa;

public class ReseptiDao implements Dao<Resepti, Integer> {
    
        private Database database;
        private ReseptinAinesosaDao reseptinAinesosaDao;
        
        public ReseptiDao(Database database) {
            this.database = database;
            this.reseptinAinesosaDao = null;
        }

    public void setReseptinAinesosaDao(ReseptinAinesosaDao reseptionAinesosaDao) {
        this.reseptinAinesosaDao = reseptionAinesosaDao;
    }

    public ReseptinAinesosaDao getReseptinAinesosaDao() {
        return reseptinAinesosaDao;
    }

    @Override
    public Resepti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("SELECT * FROM Resepti WHERE id = ?");
        statement.setInt(1, key);
        ResultSet rs = statement.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Resepti resepti = new Resepti(rs.getInt("id"), rs.getString("nimi"), 
                                      null);
        
        rs.close();
        statement.close();
        connection.close();
        
        return resepti;
    }
    
    public Resepti findOneByName(String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("SELECT * FROM Resepti WHERE nimi = ?");
        statement.setString(1, nimi);
        ResultSet rs = statement.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Resepti resepti = new Resepti(rs.getInt("id"), rs.getString("nimi"), 
                                      null);
        
        rs.close();
        statement.close();
        connection.close();
        
        return resepti;
    }

    @Override
    public List<Resepti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Resepti");
        
        ResultSet rs = statement.executeQuery();
        
        List<Resepti> reseptit = new ArrayList<>();
        while(rs.next()) {
            reseptit.add(new Resepti(rs.getInt("id"), rs.getString("nimi"), 
                                     null));
        }
        
        rs.close();
        statement.close();
        connection.close();
        
        return reseptit;
    }

    @Override
    public Resepti saveOrUpdate(Resepti resepti) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("INSERT INTO Resepti (nimi)"
                        + " VALUES (?)");
        statement.setString(1, resepti.getNimi());        
        statement.executeUpdate();
        
        statement.close();
        connection.close();
        
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("DELETE FROM Resepti WHERE id = ?");
        statement.setInt(1, key);
        statement.executeUpdate();
        
        statement.close();
        connection.close();
    }
        
}