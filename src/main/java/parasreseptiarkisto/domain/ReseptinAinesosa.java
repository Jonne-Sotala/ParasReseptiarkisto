package parasreseptiarkisto.domain;

public class ReseptinAinesosa {
    private Integer id;
    private Resepti resepti;
    private RaakaAine raakaaine;    
    private Integer lisaamisjarjestys;
    private String maara;
    private String ohje;

    public ReseptinAinesosa(Integer id, Resepti resepti, RaakaAine raakaaine, Integer lisaamisjarjestys, String maara, String ohje) {
        this.id = id;
        this.resepti = resepti;
        this.raakaaine = raakaaine;
        this.lisaamisjarjestys = lisaamisjarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }


    public Integer getId() {
        return id;
    }

    public Resepti getResepti() {
        return resepti;
    }

    public RaakaAine getRaakaaine() {
        return raakaaine;
    }

    public String getMaara() {
        return maara;
    }

    public Integer getLisaamisjarjestys() {
        return lisaamisjarjestys;
    }

    public String getOhje() {
        return ohje;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setResepti(Resepti resepti) {
        this.resepti = resepti;
    }

    public void setRaakaaine(RaakaAine raakaaine) {
        this.raakaaine = raakaaine;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public void setLisaamisjarjestys(Integer lisaamisjarjestys) {
        this.lisaamisjarjestys = lisaamisjarjestys;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }  
    
    @Override
    public String toString() {
        return lisaamisjarjestys + ". " + raakaaine + ", " + maara;
    }
}
