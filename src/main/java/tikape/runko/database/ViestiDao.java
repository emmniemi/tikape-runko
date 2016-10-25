/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }
    
    public void lisaaViesti(Integer viestiketju, String otsikko, String lahettaja, String teksti) throws Exception {
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();
        
        String kysely = "INSERT INTO Viesti (viestiketju, lahettaja, teksti, otsikko) VALUES (" + viestiketju + ", '" + lahettaja + "', '" + 
            teksti + "', '" + otsikko +"')";
        System.out.println("");
        System.out.println(kysely);
        System.out.println("");
        stmt.execute(kysely);
        connection.close();
        
        

    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer aihe = rs.getInt("aihe");
        String teksti = rs.getString("teksti");
        String lahettaja = rs.getString("lähettäjä");
        Timestamp lahetysaika = rs.getTimestamp("lähetysaika");
        String otsikko = rs.getString("otsikko");

        Viesti v = new Viesti(id, aihe, teksti, lahettaja, lahetysaika, otsikko);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer aihe = rs.getInt("aihe");
            String teksti = rs.getString("teksti");
            String lahettaja = rs.getString("lähettäjä");
            Timestamp lahetysaika = rs.getTimestamp("lähetysaika");
            String otsikko = rs.getString("otsikko");

            Viesti v = new Viesti(id, aihe, teksti, lahettaja, lahetysaika, otsikko);
            viestit.add(v);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
