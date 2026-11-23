import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tienda", user = "bruno", password= "brunoabc123.";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);
            String sentencia = "INSERT INTO ? (?) VALUES (?)";

            try (PreparedStatement pStm = conn.prepareStatement(sentencia,
                    Statement.RETURN_GENERATED_KEYS);Scanner sc = new Scanner(System.in)){

                System.out.print("Ingresa el nombre del cliente: ");
                pStm.setString(1, "Cliente");
                pStm.setString(2, "nombre");
                pStm.setString(3, sc.nextLine());
                pStm.executeQuery(sentencia);
                pStm.addBatch();

                ResultSet rSet = pStm.getGeneratedKeys();
                rSet.next();
                int id = rSet.getInt(1);

                pStm.setString(1, "Pedido");
                pStm.setString(2, "cliente_id");
                pStm.setInt(3, id);
                pStm.executeQuery(sentencia);
                pStm.addBatch();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }  
}
