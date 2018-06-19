package reseptiarkisto.dao;

import com.sun.corba.se.spi.orbutil.fsm.StateImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import reseptiarkisto.database.Database;
import reseptiarkisto.domain.Resepti;
import reseptiarkisto.domain.ReseptinAinesosa;

public class ReseptinAinesosaDao implements Dao<ReseptinAinesosa, Integer> {

    private Database database;
    private ReseptiDao reseptiDao;
    private RaakaAineDao raakaAineDao;

    public ReseptinAinesosaDao(Database database, ReseptiDao reseptiDao, RaakaAineDao raakaAineDao) {
        this.database = database;
        this.reseptiDao = reseptiDao;
        this.raakaAineDao = raakaAineDao;
    }

    @Override
    public ReseptinAinesosa findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement
                = connection.prepareStatement("SELECT * FROM ReseptinAinesosa WHERE id = ?");
        statement.setInt(1, key);
        ResultSet rs = statement.executeQuery();

        if (!rs.next()) {
            return null;
        }

        ReseptinAinesosa ainesosa = new ReseptinAinesosa(
                rs.getInt("id"),
                reseptiDao.findOne(rs.getInt("resepti_id")),
                raakaAineDao.findOne(rs.getInt("raakaaine_id")),
                rs.getInt("lisaamisjarjestys"),
                rs.getString("maara"),
                rs.getString("ohje"));

        rs.close();
        statement.close();
        connection.close();
        
        return ainesosa;
    }

    @Override
    public List<ReseptinAinesosa> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement
                = connection.prepareStatement("SELECT * FROM ReseptinAinesosa");
        ResultSet rs = statement.executeQuery();

        List<ReseptinAinesosa> ainesosat = new ArrayList<>();
        while (rs.next()) {
            ainesosat.add(new ReseptinAinesosa(
                    rs.getInt("id"),
                    reseptiDao.findOne(rs.getInt("resepti_id")),
                    raakaAineDao.findOne(rs.getInt("raakaaine_id")),
                    rs.getInt("lisaamisjarjestys"),
                    rs.getString("maara"),
                    rs.getString("ohje")));
        }

        rs.close();
        statement.close();
        connection.close();  
        
        return ainesosat;
    }
    
    public List<ReseptinAinesosa> findReseptinAinesosatByKey(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement
                = connection.prepareStatement("SELECT * FROM ReseptinAinesosa "
                        + "WHERE ReseptinAinesosa.resepti_id = ?");
        statement.setInt(1, key);
        ResultSet rs = statement.executeQuery();
        
        List<ReseptinAinesosa> ainesosat = new ArrayList<>();
        while (rs.next()) {
            ainesosat.add(new ReseptinAinesosa(
                    rs.getInt("id"),
                    reseptiDao.findOne(rs.getInt("resepti_id")),
                    raakaAineDao.findOne(rs.getInt("raakaaine_id")),
                    rs.getInt("lisaamisjarjestys"),
                    rs.getString("maara"),
                    rs.getString("ohje")));
        } 
        
        rs.close();
        statement.close();
        connection.close();
        
        return ainesosat;
    }
    
    public Integer raakaaineenKayttoMaaraByKey(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM ReseptinAinesosa "
                + "WHERE ReseptinAinesosa.raakaaine_id = ?");
        statement.setInt(1, key);
        ResultSet rs = statement.executeQuery();
        
        int maara = 0;
        while (rs.next()) {
            maara++;
        }
        
        rs.close();
        statement.close();
        connection.close(); 
        
        return maara;
    }

    @Override
    public ReseptinAinesosa saveOrUpdate(ReseptinAinesosa ainesosa) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO ReseptinAinesosa"
                + " (resepti_id, raakaaine_id, lisaamisjarjestys, maara, ohje)"
                + " VALUES (?, ?, ?, ?, ?)");
        statement.setInt(1, ainesosa.getResepti().getId());
        statement.setInt(2, ainesosa.getRaakaaine().getId());
        statement.setInt(3, ainesosa.getLisaamisjarjestys());
        statement.setString(4, ainesosa.getMaara());
        statement.setString(5, ainesosa.getOhje());
        statement.executeUpdate();
        
        statement.close();
        connection.close();
        
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = 
                connection.prepareStatement("DELETE FROM ReseptinAinesosa WHERE id = ?");
        statement.setInt(1, key);
        statement.executeUpdate();
        
        statement.close();
        connection.close();
    }

}
