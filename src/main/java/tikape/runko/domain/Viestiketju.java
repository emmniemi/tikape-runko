
package tikape.runko.domain;

import java.sql.Timestamp;

public class Viestiketju {
    private Integer id;
    private Integer aihealue;
    private String nimi;

    public Viestiketju(Integer id, Integer aihealue, String nimi){
        this.id = id;
        this.aihealue = aihealue;
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAihealue() {
        return aihealue;
    }

    public void setAihealue(Integer aihealue) {
        this.aihealue = aihealue;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    
    
    
}
