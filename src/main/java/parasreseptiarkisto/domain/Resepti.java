package parasreseptiarkisto.domain;

import java.util.List;

public class Resepti {
    private Integer id;
    private String nimi;
    private List<ReseptinAinesosa> ainekset;

    public Resepti(Integer id, String nimi, List<ReseptinAinesosa> ainekset) {
        this.id = id;
        this.nimi = nimi;
        this.ainekset = ainekset;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public List<ReseptinAinesosa> getAinekset() {
        return ainekset;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public void lisaaRaakaAine(ReseptinAinesosa ainesosa) {
        ainekset.add(ainesosa);
    }
    
    @Override
    public String toString() {
        return nimi;
    }
}
