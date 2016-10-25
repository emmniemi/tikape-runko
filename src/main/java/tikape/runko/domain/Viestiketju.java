
package tikape.runko.domain;

import java.sql.Timestamp;

public class Viestiketju {
    private Integer id;
    private Integer aihealue;
    private String nimi;
//    private Timestamp aika;

    public Viestiketju(Integer id, Integer aihealue, String nimi
//            , Timestamp aika
    ){
        this.id = id;
        this.aihealue = aihealue;
        this.nimi = nimi;
//        this.aika = aika;
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

//    public Timestamp getAika() {
//        return aika;
//    }
//
//    public void setAika(Timestamp aika) {
//        this.aika = aika;
//    }
    
    
    
}
