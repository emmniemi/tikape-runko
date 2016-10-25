
package tikape.runko.domain;

public class Aihealue {
    private Integer id;
    private String aiheenNimi;

    public Aihealue(Integer id, String aiheenNimi) {
        this.id = id;
        this.aiheenNimi = aiheenNimi;
    }

    public String getAiheenNimi() {
        return aiheenNimi;
    }

    public Integer getId() {
        return id;
    }

    public void setAiheenNimi(String aiheenNimi) {
        this.aiheenNimi = aiheenNimi;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
