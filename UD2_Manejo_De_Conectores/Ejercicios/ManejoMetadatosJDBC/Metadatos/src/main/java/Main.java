import java.sql.*;
import java.util.Scanner;

public class Main {
    static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tienda", user = "bruno", password = "brunoabc123.";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            ejercicio1(conn);
            //ejercicio2(conn);
            //ejercicio3(conn);
        }
    }

    public static void ejercicio1(Connection conn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        DatabaseMetaData dBmD = conn.getMetaData();
        System.out.println("Nombre del SGBD: " + dBmD.getDatabaseProductName());
        System.out.println("Versión del SGBD: " + dBmD.getDatabaseProductVersion());

        System.out.println("Soporta procesamiento por lotes :" + dBmD.supportsBatchUpdates());
        System.out.println("Soporta transacciones: " + dBmD.supportsTransactions());
        System.out.println("Soporta savepoints: " + dBmD.supportsSavepoints());
        System.out.println("Soporta recuperación de claves generales: " + dBmD.supportsGetGeneratedKeys());

        ResultSet rSetTables = dBmD.getTables(conn.getCatalog(), null, "%", new String[]{
                "TABLE"
        });

        while (rSetTables.next()) {
            System.out.println(rSetTables.getString("TABLE_NAME"));
        }

        String tableName = sc.nextLine();
        ResultSet rSetColumn = dBmD.getColumns(conn.getCatalog(), null, tableName, "%");

        while (rSetColumn.next()) {
            ResultSetMetaData rSmDColumn = rSetColumn.getMetaData();
            for (int i = 1; i <= rSmDColumn.getColumnCount(); i++) {
            System.out.println(rSmDColumn.getColumnName(i));

            }
        }
    }

    public static void ejercicio2(Connection conn) {

    }

    public static void ejercicio3(Connection conn) {

    }
}
