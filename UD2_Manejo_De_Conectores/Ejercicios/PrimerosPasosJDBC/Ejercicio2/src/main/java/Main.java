import java.sql.*;


public class Main {
    static void main() {
        String user = "sa";
        String password = "";
        String url = "jdbc:h2:./data/instituto";


        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stm = connection.createStatement()) {
            System.out.println("Conexión establecida con éxito");

            String sql = "CREATE TABLE IF NOT EXISTS ciclos (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY," +
                    "nombre VARCHAR(100)," +
                    "GRADO VARCHAR(50) NOT NULL CHECK (" +
                    "LOWER(GRADO) IN ('medio', 'superior')))";
            String sqlInsert1 = "INSERT INTO ciclos VALUES (1, 'bruno', 'medio')";
            String sqlInsert2 = "INSERT INTO ciclos VALUES (2, 'xoel', 'superior')";
            String sqlInsert3 = "INSERT INTO ciclos VALUES (3, 'anxo', 'medio')";


            stm.execute(sql);
            stm.execute(sqlInsert1);
            if (stm.getUpdateCount() != -1) {
                System.out.println("Registro insertado");
            }
            stm.execute(sqlInsert2);
            if (stm.getUpdateCount() != -1) {
                System.out.println("Registro insertado");
            }
            stm.execute(sqlInsert3);
            if (stm.getUpdateCount() != -1) {
                System.out.println("Registro insertado");
            }

            String sqlUpdate1 = "UPDATE ciclos SET nombre = 'bruno', grado = 'superior' WHERE id = 1;";
            stm.executeUpdate(sqlUpdate1);
            if (stm.getUpdateCount() != -1) {
                System.out.println("Registro modificado. Número de modificaciones: " + stm.getUpdateCount());
            }

        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}
