package reseptiarkisto.domain;

import java.util.List;

public class Resepti {
    private Integer id;
    private String nimi;
    private List<ReseptinAinesosa> ainesosat;

    public Resepti(Integer id, String nimi, List<ReseptinAinesosa> ainesosat) {
        this.id = id;
        this.nimi = nimi;
        this.ainesosat = ainesosat;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public List<ReseptinAinesosa> getAinesosat() {
        return ainesosat;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public void setAinesosat(List<ReseptinAinesosa> ainesosat) {
        this.ainesosat = ainesosat;
    }
    
    public void lisaaRaakaAine(ReseptinAinesosa ainesosa) {
        ainesosat.add(ainesosa);
    }
    
    @Override
    public String toString() {
        return nimi;
    }
}
