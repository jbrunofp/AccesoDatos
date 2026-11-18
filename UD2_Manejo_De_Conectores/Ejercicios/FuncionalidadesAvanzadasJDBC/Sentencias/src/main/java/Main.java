import java.sql.*;
import java.util.Scanner;

public class Main {
    static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/empresa_ventas";
        String user = "bruno";
        String password = "brunoabc123.";

        try (Connection con = DriverManager.getConnection(url, user, password)
        ) {
            //ejercicio1(con);
            //ejercicio2(con);
            ejercicio3(con);
            //ejercicio(con);
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
        try (CallableStatement pCall = con.prepareCall(sentence); Scanner sc = new Scanner(System.in)){
            System.out.println("Introduce el número de departamento:");
            pCall.setInt(1, sc.nextInt());
            sc.nextLine();

            pCall.registerOutParameter(2, Types.DECIMAL);
            pCall.execute();

            double salario = pCall.getDouble(2);
            System.out.println("Salario medio: " + salario);
        }
    }

    public static void ejercicio3(Connection con) throws  SQLException{
        String sentence = "CALL subir_salario(?)";
        try (CallableStatement pCall = con.prepareCall(sentence); Scanner sc = new Scanner(System.in)){

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
    }

    public static void ejercicio5(Connection con) {
    }

    public static void ejercicio6(Connection con) {
    }
}
