import org.h2.tools.Console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class Main {
    static void main() {
        String user = "sa";
        String password = "";
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";


        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión establecida con éxito");
            Statement stm = connection.createStatement();

            String sql = "CREATE TABLE alumnos (ID INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100), EDAD INT)";
            String sqlInsert1 = "INSERT INTO alumnos VALUES (1, 'bruno', 22)";
            String sqlInsert2 = "INSERT INTO alumnos VALUES (2, 'xoel', 21)";
            String sqlInsert3 = "INSERT INTO alumnos VALUES (3, 'anxo', 20)";

            stm.execute(sql);
            stm.execute(sqlInsert1);
            stm.execute(sqlInsert2);
            stm.execute(sqlInsert3);
            Console.main("-web", "-browser");

        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}
