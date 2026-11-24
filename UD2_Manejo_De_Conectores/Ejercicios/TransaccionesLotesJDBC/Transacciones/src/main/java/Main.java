import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tienda", user = "bruno", password = "brunoabc123.";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            //ejercicio1(conn);
            //ejercicio2(conn);
            ejercicio3(conn);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void ejercicio1(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        String insertCliente = "INSERT INTO cliente (nombre) VALUES (?)";
        String insertPedido = "INSERT INTO pedido (cliente_id, descripcion) VALUES (?, ?)";


        try (PreparedStatement pStm = conn.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS); PreparedStatement pStm2 = conn.prepareStatement(insertPedido); Scanner sc = new Scanner(System.in)) {
            System.out.print("Ingrese el nombre del nuevo cliente: ");
            pStm.setString(1, sc.nextLine());
            System.out.println();
            pStm.executeUpdate();

            ResultSet key = pStm.getGeneratedKeys();
            key.next();

            pStm2.setInt(1, key.getInt(1));
            System.out.print("Ingrese la descripcion del pedido: ");
            pStm2.setString(2, sc.nextLine());
            System.out.println();
            pStm2.executeUpdate();

            pStm2.setInt(1, key.getInt(1));
            System.out.print("Ingrese la descripcion del segundo pedido: ");
            pStm2.setString(2, sc.nextLine());
            System.out.println();
            pStm2.executeUpdate();

            conn.commit();
        }
    }

    public static void ejercicio2(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        String insertCliente = "INSERT INTO cliente (nombre) VALUES (?)";

        try (PreparedStatement pStm = conn.prepareStatement(insertCliente); Scanner sc = new Scanner(System.in)) {
            for (int i = 0; i < 5; i++) {
                System.out.print("Ingresa el nombre del cliente Nº: ");
                pStm.setString(1, sc.nextLine());
                System.out.println();
                pStm.addBatch();
            }
            pStm.executeBatch();
            conn.commit();

            ResultSet rSet = pStm.executeQuery("SELECT * FROM cliente");
            while (rSet.next()){
                System.out.println(rSet.getString("nombre"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            conn.rollback();
        }
    }

    public static void ejercicio3(Connection conn) throws SQLException{
        conn.setAutoCommit(false);
        String insert = "INSERT cliente (nombre, email) VALUES (?, ?)";

        try (PreparedStatement pStm = conn.prepareStatement(insert)){
            pStm.setString(1, "Ana");
            pStm.setString(2, "emailAna");
            pStm.executeUpdate();
            Savepoint sp = conn.setSavepoint();

            pStm.setString(1, "Luis");
            pStm.setString(2, "emailAna");

            try {
                pStm.executeUpdate();
            } catch (SQLException e) {
                System.out.println("No se ha podido insertar a Luis");
                if (e.getSQLState().startsWith("23")){
                    conn.rollback(sp);
                }
            }

            pStm.setString(1, "Marta");
            pStm.setString(2, "emailMarta");
            pStm.executeUpdate();

            conn.commit();
        }
    }
}
