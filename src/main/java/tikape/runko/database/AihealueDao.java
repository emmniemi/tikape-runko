
package tikape.runko.database;

import java.sql.*;
import java.util.*;
import tikape.runko.domain.Aihealue;
import tikape.runko.domain.Viestiketju;


public class AihealueDao implements Dao<Aihealue, Integer> {

    private Database database;

    public AihealueDao(Database database) {
        this.database = database;
    }

    @Override
    public Aihealue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String aiheenNimi = rs.getString("aiheenNimi");

        Aihealue aa = new Aihealue(id, aiheenNimi);

        rs.close();
        stmt.close();
        connection.close();

        return aa;
    }
    
    public String haeAiheenNimi(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

       
        String aiheenNimi = rs.getString("aiheenNimi");

        

        rs.close();
        stmt.close();
        connection.close();
        return aiheenNimi;
    }
    
    
    public List<Viestiketju> haeViestiketjut(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE aihealue = ?");
        
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        
        while(rs.next()) {
            Integer id = rs.getInt("id");
            Integer aihealue = rs.getInt("aihealue");
            String nimi = rs.getString("nimi");
            
            Viestiketju viestiketju = new Viestiketju(id, aihealue, nimi);
            
            viestiketjut.add(viestiketju);
            
        }
        
        

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
    }

    @Override
    public List<Aihealue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue");

        ResultSet rs = stmt.executeQuery();
        List<Aihealue> aihealueet = new ArrayList<>();
        while (rs.next()) {

            
        Integer id = rs.getInt("id");
        String aiheenNimi = rs.getString("aiheenNimi");

        Aihealue aa = new Aihealue(id, aiheenNimi);
            aihealueet.add(aa);

        }

        rs.close();
        stmt.close();
        connection.close();

        return aihealueet;
    }
    
    public void lisaaAihealue(String aiheenNimi) throws Exception {
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();

        String kysely = "INSERT INTO Aihealue (aiheenNimi) VALUES ('" + aiheenNimi + "')";
        System.out.println("");
        System.out.println(kysely);
        System.out.println("");
        stmt.execute(kysely);
        connection.close();


    }
    
 

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}

