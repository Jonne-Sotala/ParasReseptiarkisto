package parasreseptiarkisto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import parasreseptiarkisto.dao.RaakaAineDao;
import parasreseptiarkisto.dao.ReseptiDao;
import parasreseptiarkisto.dao.ReseptinAinesosaDao;
import parasreseptiarkisto.database.Database;
import parasreseptiarkisto.domain.RaakaAine;
import parasreseptiarkisto.domain.Resepti;
import parasreseptiarkisto.domain.ReseptinAinesosa;
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
        
        
        // Pääsivu
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("reseptilista", reseptiDao.findAll());
            map.put("raakaainelista", raakaAineDao.findAll());

            return new ModelAndView(map, "reseptit");
        }, new ThymeleafTemplateEngine());

        // Raaka-aine sivu
        Spark.get("/raakaaine", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaainelista", raakaAineDao.findAll());

            return new ModelAndView(map, "lisaaRaakaAine");
        }, new ThymeleafTemplateEngine());

        // Raaka-aineiden lisääminen
        Spark.post("/raakaaine/lisaa", (req, res) -> {
            raakaAineDao.saveOrUpdate(new RaakaAine(-1, req.queryParams("raaka-aine")));

            res.redirect("/raakaaine");
            return "Raaka-aine lisätty";
        });

        // Raaka-aineiden poistaminen
        Spark.post("/raakaaine/poista/:id", (req, res) -> {
            raakaAineDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/raakaaine");
            return "Raaka-aine poistettu";
        });

        // Resepti sivu
        Spark.get("/resepti", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("reseptilista", reseptiDao.findAll());
            map.put("raakaainelista", raakaAineDao.findAll());

            return new ModelAndView(map, "lisaaResepti");
        }, new ThymeleafTemplateEngine());

        // Reseptien lisääminen
        Spark.post("/resepti/lisaa", (req, res) -> {
            reseptiDao.saveOrUpdate(new Resepti(-1, req.queryParams("resepti"), new ArrayList<>()));

            res.redirect("/resepti");
            return "Resepti lisätty";
        });

        // Reseptin poistaminen
        Spark.post("/resepti/poista/:id", (req, res) -> {
            reseptiDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/resepti");
            return "Resepti poistettu";
        });

        // Raaka-aineiden liittäminen reseptiin
        Spark.post("/resepti/liita", (req, res) -> {
            reseptinAinesosaDao.saveOrUpdate(new ReseptinAinesosa(
                    -1,
                    reseptiDao.findOneByName(req.queryParams("resepti")),
                    raakaAineDao.findOneByName(req.queryParams("raaka-aine")),
                    Integer.parseInt(req.queryParams("jarjestys")),
                    req.queryParams("maara"),
                    req.queryParams("ohje")));

            res.redirect("/resepti");
            return "Raaka-aine liitetty";
        });

        // Reseptin tarkastelu
        Spark.get("/resepti/katso/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            Resepti resepti = reseptiDao.findOne(Integer.parseInt(req.params(":id")));
            resepti.setAinesosat(reseptinAinesosaDao.findReseptinAinesosatByKey(resepti.getId()));

            List<ReseptinAinesosa> ainesosat = resepti.getAinesosat().stream().sorted((a1, a2) -> {
                return a1.getLisaamisjarjestys() - a2.getLisaamisjarjestys();
            }).collect(Collectors.toCollection(ArrayList::new));

            map.put("resepti", resepti);
            map.put("ainesosat", resepti.getAinesosat());

            return new ModelAndView(map, "reseptiInfo");
        }, new ThymeleafTemplateEngine());
    }
}