/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.*;
import java.util.*;
import tikape.runko.domain.Viestiketju;
import tikape.runko.domain.Viesti;

/**
 *
 * @author aleksisv
 */
public class ViestiketjuDao implements Dao<Viestiketju, Integer> {

    private Database database;

    public ViestiketjuDao(Database database) {
        this.database = database;
    }

    public HashMap<Integer, Integer> haeViestienMaara(int key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt2 = connection.prepareStatement("SELECT Viestiketju.id AS viestiketju, COUNT(Viesti.id) AS viestienLukumaara FROM Viestiketju "
                + "LEFT JOIN Viesti "
                + "ON Viestiketju.id = Viesti.viestiketju "
                + "WHERE Viestiketju.aihealue = ? "
                + "GROUP BY Viestiketju.id");

        stmt2.setObject(1, key);

        ResultSet rs = stmt2.executeQuery();
        HashMap<Integer, Integer> mappi = new HashMap<>();

        while (rs.next()) {

            Integer viestiketju = rs.getInt("viestiketju");
            Integer viestienLukumaara = rs.getInt("viestienLukumaara");
            mappi.put(viestiketju, viestienLukumaara);

        }

        rs.close();
        stmt2.close();
        connection.close();

        return mappi;
    }

    public String haeNimiIDlla(Integer viestiketjuID) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viestiketju.nimi FROM Viestiketju"
                + " WHERE Viestiketju.id = ?");

        stmt.setObject(1, viestiketjuID);

        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String nimi = rs.getString("nimi");

        rs.close();
        stmt.close();
        connection.close();

        return nimi;
    }
    
    public HashMap<Integer, String> haeViimeinenViesti(int key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT Viestiketju.id AS id, Viesti.lahetysaika AS viimeinen FROM Viesti "
                + "LEFT JOIN Viestiketju "
                + "ON Viesti.viestiketju = Viestiketju.id WHERE Viestiketju.aihealue = ?"
                + "GROUP BY Viestiketju.id ORDER BY viimeinen DESC");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        HashMap<Integer, String> mappi = new HashMap<>();

        while (rs.next()) {

            Integer id = rs.getInt("id");
            String viimeinenViesti = rs.getString("viimeinen");
            mappi.put(id, viimeinenViesti);

        }

        rs.close();
        stmt.close();
        connection.close();

        return mappi;
    }
    
    @Override
    public Viestiketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer aihealue = rs.getInt("aihealue");
        String nimi = rs.getString("nimi");
//        Timestamp aika = rs.getTimestamp("aika");

        Viestiketju a = new Viestiketju(id, aihealue, nimi
        //                ,aika
        );

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }

    public String haeViestiketjunNimi(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String nimi = rs.getString("nimi");

        rs.close();
        stmt.close();
        connection.close();
        return nimi;
    }

    @Override
    public List<Viestiketju> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju");

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> aiheet = new ArrayList<>();
        while (rs.next()) {

            Integer id = rs.getInt("id");
            Integer aihealue = rs.getInt("aihealue");
            String nimi = rs.getString("nimi");
//            Timestamp aika = rs.getTimestamp("aika");

            Viestiketju a = new Viestiketju(id, aihealue, nimi
            //                    ,aika
            );
            aiheet.add(a);

        }

        rs.close();
        stmt.close();
        connection.close();

        return aiheet;
    }

    public List<Viesti> haeViestit(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE viestiketju = ?");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer viestiketju = rs.getInt("viestiketju");
            String teksti = rs.getString("teksti");
            String lahettaja = rs.getString("lahettaja");
            String lahetysaika = rs.getString("lahetysaika");
            String otsikko = rs.getString("otsikko");

            Viesti v = new Viesti(id, viestiketju, teksti, lahettaja, lahetysaika, otsikko);

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

    public void lisaaViestiketju(Integer aihealue, String nimi) throws SQLException {
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();

        String kysely = "INSERT INTO Viestiketju (aihealue, nimi) VALUES (" + aihealue + ", '" + nimi + "')";

        stmt.execute(kysely);
        connection.close();

    }

}
