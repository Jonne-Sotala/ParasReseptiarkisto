package parasreseptiarkisto.domain;

public class RaakaAine {
    private Integer id;
    private String nimi;

    public RaakaAine(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    @Override
    public String toString() {
        return this.nimi;
    }
}
