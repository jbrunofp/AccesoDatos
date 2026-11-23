import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/empresa_ventas";
        String user = "root";/*"bruno";*/
        String password = "Holaaloh3343.";/*"brunoabc123.";*/

        try (Connection con = DriverManager.getConnection(url, user, password)
        ) {
            //ejercicio1(con);
            //ejercicio2(con);
            //ejercicio3(con);
            ejercicio4(con);
            //ejercicio(con);
            //ejercicio(con);
            //ejercicio(con);
        }
    }

    public static void ejercicio1(Connection con) throws SQLException {
        String sentence = "CALL contar_empleados()";
        try (CallableStatement pCall = con.prepareCall(sentence)) {
            ResultSet rSet = pCall.executeQuery();
            rSet.next();
            System.out.println("Total de empleados: " + rSet.getInt("total_empleados"));
        }
    }

    public static void ejercicio2(Connection con) throws SQLException {
        String sentence = "CALL salario_medio_departamento(?, ?)";
        try (CallableStatement pCall = con.prepareCall(sentence); Scanner sc = new Scanner(System.in)) {
            System.out.println("Introduce el número de departamento:");
            pCall.setInt(1, sc.nextInt());
            sc.nextLine();

            pCall.registerOutParameter(2, Types.DECIMAL);
            pCall.execute();

            double salario = pCall.getDouble(2);
            System.out.println("Salario medio: " + salario);
        }
    }

    public static void ejercicio3(Connection con) throws SQLException {
        String sentence = "CALL subir_salario(?)";
        try (CallableStatement pCall = con.prepareCall(sentence); Scanner sc = new Scanner(System.in)) {

            System.out.println("Introduce el salario: ");
            pCall.setDouble(1, sc.nextDouble());
            sc.nextLine();

            pCall.registerOutParameter(1, Types.DOUBLE);
            pCall.execute();

            double salario = pCall.getDouble(1);
            System.out.println("Salario nuevo: " + salario);
        }
    }

    public static void ejercicio4(Connection con) {
        String sentence = "SELECT * FROM empleados ORDER BY EMP_NO";
        try (Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
             ResultSet rSet = stm.executeQuery(sentence)) {
            if (rSet.first()) {
                System.out.println("Primer registro: " + mostrar(rSet));
            }
            System.out.println();
            if (rSet.last()) {
                System.out.println("Último registro: " + mostrar(rSet));
            }
            System.out.println();
            if (rSet.previous()) {
                System.out.println("Registro anterior: " + mostrar(rSet));
            }
            System.out.println();
            if (rSet.next()) {
                System.out.println("Registro siguiente: " + mostrar(rSet));
            }
            System.out.println();
            if (rSet.absolute(3)) {
                System.out.println("Registro en la tercera posición: " + mostrar(rSet));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void ejercicio5(Connection con) {
    }

    public static void ejercicio6(Connection con) {
    }

    public static String mostrar(ResultSet rSet) throws  SQLException{
        ResultSetMetaData rSetMetaData = rSet.getMetaData();
        int nColumnas = rSetMetaData.getColumnCount();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= nColumnas; i++) {
            sb.append("\n").append(rSetMetaData.getColumnName(i)).append(": ");
            sb.append(rSet.getString(i));

        }
        return sb.toString();
    }
}
