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
    public Aihealue haeYksi(Integer key) throws SQLException {
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

    public HashMap<Integer, Integer> haeViestiketjujenMaara() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Aihealue.id AS aihealue, COUNT(Viestiketju.id) AS viestienLukumaara FROM Aihealue LEFT JOIN Viestiketju "
                + "ON Aihealue.id = Viestiketju.aihealue GROUP BY Aihealue.id");

        ResultSet rs = stmt.executeQuery();
        HashMap<Integer, Integer> mappi = new HashMap<>();
        
        while (rs.next()) {

            Integer aihealue = rs.getInt("aihealue");
            Integer viestienLukumaara = rs.getInt("viestienLukumaara");
            mappi.put(aihealue, viestienLukumaara);

        }

        rs.close();
        stmt.close();
        connection.close();

        return mappi;
    }

    public List<Viestiketju> haeViestiketjut(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE aihealue = ?");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();

        while (rs.next()) {
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
    public List<Aihealue> haeKaikki() throws SQLException {

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
        stmt.execute(kysely);
        connection.close();

    }
    
     public Integer haeAihealueHakusanalla(String hakusana) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Aihealue.id as aihealue FROM Viesti, Viestiketju, Aihealue "
                + "WHERE Viesti.viestiketju = Viestiketju.id AND Viestiketju.aihealue = Aihealue.id "
                + " AND Viesti.teksti LIKE '%" + hakusana + "%' OR Viesti.otsikko LIKE '%" + hakusana + "%'" );

        
        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int aihealueId = rs.getInt("aihealue");

        rs.close();
        stmt.close();
        connection.close();

        return aihealueId;
    }

    @Override
    public void poista(Integer key) throws SQLException {
        // ei toteutettu
    }

}
