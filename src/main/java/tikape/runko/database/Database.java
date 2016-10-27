package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

//         tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE Aihealue; DROP TABLE Viestiketju; DROP TABLE Viesti;");
//        lista.add("CREATE TABLE Aihealue (id INTEGER PRIMARY KEY, aiheenNimi VARCHAR(100));");
//        lista.add("CREATE TABLE Viestiketju (id INTEGER PRIMARY KEY, aihealue INTEGER, "
//                + "nimi VARCHAR(100), aika TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, FOREIGN KEY (aihealue) REFERENCES Aihealue(id));");
//        lista.add("CREATE TABLE Viesti (id INTEGER PRIMARY KEY, viestiketju INTEGER, teksti VARCHAR(500), lahettaja VARCHAR(50), "
//                + "lahetysaika DATE DEFAULT (datetime('now', 'localtime')), "
//                + " otsikko VARCHAR(100), FOREIGN KEY (viestiketju) REFERENCES Viestiketju(id));");
//        lista.add("INSERT INTO Aihealue (aiheenNimi) VALUES ('Tietokannat');");
//        lista.add("INSERT INTO Aihealue (aiheenNimi) VALUES ('Kukkaset ja mehiläiset');");
//
//        lista.add("INSERT INTO Viestiketju (aihealue, nimi) VALUES (1, 'TiKaPe');");
//        lista.add("INSERT INTO Viestiketju (aihealue, nimi) VALUES (1, 'TiKaPe2');");
//        lista.add("INSERT INTO Viestiketju (aihealue, nimi) VALUES (1, 'TiKaPe3');");
//        lista.add("INSERT INTO Viesti (viestiketju, teksti, lahettaja, otsikko) VALUES (1, 'Jee', 'Homeros taas kerran', 'Moi:)');");

        return lista;
    }
}
