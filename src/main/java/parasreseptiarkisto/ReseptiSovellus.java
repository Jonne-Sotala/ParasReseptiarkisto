package parasreseptiarkisto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import parasreseptiarkisto.dao.RaakaAineDao;
import parasreseptiarkisto.dao.ReseptiDao;
import parasreseptiarkisto.dao.ReseptinAinesosaDao;
import parasreseptiarkisto.database.Database;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class ReseptiSovellus {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:reseptit.db");
        ReseptiDao reseptiDao = new ReseptiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        ReseptinAinesosaDao reseptinAinesosaDao
                = new ReseptinAinesosaDao(database, reseptiDao, raakaAineDao);
        reseptiDao.setReseptinAinesosaDao(reseptinAinesosaDao);

        System.out.println("Hello world!");

        Spark.get("*", (req, res) -> {

            List<String> reseptit = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = database.getConnection();

            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT nimi FROM Resepti");
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                String nimi = tulos.getString("nimi");
                reseptit.add(nimi);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("lista", reseptit);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("*", (req, res) -> {
            System.out.println("Hei maailma!");
            System.out.println("Saatiin: "
                    + req.queryParams("huonekalu"));

            // avaa yhteys tietokantaan
            Connection conn = database.getConnection();

            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("INSERT INTO Resepti (nimi) VALUES (?)");
            stmt.setString(1, req.queryParams("huonekalu"));

            stmt.executeUpdate();

            // sulje yhteys tietokantaan
            conn.close();

            res.redirect("/");
            return "";
        });
    }
}
