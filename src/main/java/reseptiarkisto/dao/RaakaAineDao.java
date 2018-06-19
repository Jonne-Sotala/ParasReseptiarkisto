package reseptiarkisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import reseptiarkisto.database.Database;
import reseptiarkisto.domain.RaakaAine;

public class RaakaAineDao implements Dao<RaakaAine, Integer> {
    
    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        statement.setInt(1, key);
        ResultSet rs = statement.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        RaakaAine raakaAine = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        statement.close();
        connection.close();
        
        return raakaAine;
    }
    
    public RaakaAine findOneByName(String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?");
        statement.setString(1, nimi);
        ResultSet rs = statement.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        RaakaAine raakaAine = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        statement.close();
        connection.close();
        
        return raakaAine;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM RaakaAine");
        ResultSet rs = statement.executeQuery();
        
        List<RaakaAine> raakaAineet = new ArrayList<>();
        while (rs.next()) {
            raakaAineet.add(new RaakaAine(rs.getInt("id"), rs.getString("nimi")));
        }
        
        rs.close();
        statement.close();
        connection.close();
        
        return raakaAineet;
    }

    @Override
    public RaakaAine saveOrUpdate(RaakaAine raakaAine) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
        statement.setString(1, raakaAine.getNimi());
        statement.executeUpdate();
        
        statement.close();
        connection.close();
        
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");
        statement.setInt(1, key);
        statement.executeUpdate();        
        statement.close();
        
        PreparedStatement statement2 = connection.prepareStatement("DELETE FROM ReseptinAinesosa "
                + "WHERE ReseptinAinesosa.raakaaine_id = ?");
        statement2.setInt(1, key);
        statement2.executeUpdate();
        
        statement2.close();
        connection.close();
    }
    
    
}
