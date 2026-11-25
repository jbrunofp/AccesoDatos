import java.sql.*;
import java.util.Scanner;

public class Main {
    static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tienda", user = "root", password = "Holaaloh3343.";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            //ejercicio1(conn);
            ejercicio2(conn);
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

        System.out.print("Ingresa el nombre de la tabla: ");
        String tableName = sc.nextLine();
        ResultSet rSetColumn = dBmD.getColumns(conn.getCatalog(), null, tableName, "%");

        while (rSetColumn.next()) {
            System.out.println(rSetColumn.getString("COLUMN_NAME"));

        }

        System.out.println("\nClave primaria de " + tableName + ":");
        try (ResultSet rSetPk = dBmD.getPrimaryKeys(conn.getCatalog(), null, tableName)) {
            while (rSetPk.next()) {
                System.out.println(rSetPk.getString("COLUMN_NAME"));
            }
        }
    }

    public static void ejercicio2(Connection conn) throws  SQLException {
        String sql = "SELECT * FROM cliente";

        try (Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            ResultSetMetaData md = rs.getMetaData();
            int numCols = md.getColumnCount();

            // 1. Número de columnas
            System.out.println("Número de columnas: " + numCols);

            // 2. Nombre, etiqueta y tipo SQL de cada columna
            for (int i = 1; i <= numCols; i++) {
                System.out.println("Columna " + i
                        + " | nombre: " + md.getColumnName(i)
                        + " | etiqueta: " + md.getColumnLabel(i)
                        + " | tipo SQL: " + md.getColumnTypeName(i));
            }

            // 3. Recorrido dinámico de los registros
            System.out.println();
            for (int i = 1; i <= numCols; i++) {
                System.out.print(md.getColumnLabel(i) + "\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= numCols; i++) {
                    System.out.print(rs.getObject(i) + "\t");
                }
                System.out.println();
            }
        }
    }

    public static void ejercicio3(Connection conn) {

    }
}
