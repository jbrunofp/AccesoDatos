import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/empresa_ventas";
        String user = "bruno";
        String password = "brunoabc123.";

        try (Connection con = DriverManager.getConnection(url, user, password); Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            /*EJERCICIOS CON STATEMENT*/
            //ejercicio1(stm);
            //ejercicio2(stm);
            //ejercicio3(stm);
            //ejercicio4(stm);

            /*EJERCICIOS CON PREPARED-STATEMENT*/
            //ejercicio5(con);
            //ejercicio6(con);
            ejercicio7(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ejercicio1(Statement stm) throws SQLException {
        String sentencia = "SELECT * FROM empleados";
        ResultSet rSet = stm.executeQuery(sentencia);
        while (rSet.next()) {
            System.out.printf("""
                            \u001B[32mDatos Empleado\u001B[0m
                            ------------------------
                            Nº Empleado: %d
                            Apellido: %s
                            Oficio: %s
                            Salario: %.2f
                            Nº Departamento: %d
                            ------------------------\n
                            """, rSet.getInt("EMP_NO"), rSet.getString("APELLIDO"),
                    rSet.getString("OFICIO"), rSet.getDouble("SALARIO"),
                    rSet.getInt("DEP_NO"));

        }
    }

    public static void ejercicio2(Statement stm) throws SQLException {
        String DEP_NO = "55, ";
        String DNOMBRE = "'INFORMATICA',";
        String LOCALIDAD = " 'OURENSE'";
        String sentencia = "INSERT IGNORE INTO departamentos VALUES (" + DEP_NO + DNOMBRE + LOCALIDAD + ")";
        int insert = stm.executeUpdate(sentencia);
        System.out.println("Número de filas afectadas: " + insert);
    }

    public static void ejercicio3(Statement stm) throws SQLException {
        String sentencia = "SELECT EMP_NO, SALARIO FROM empleados";
        ResultSet rSet = stm.executeQuery(sentencia);
        double salario;

        while (rSet.next()) {
            salario = rSet.getDouble("SALARIO");
            rSet.updateDouble("SALARIO", salario * 1.05);
            rSet.updateRow();
        }

    }

    public static void ejercicio4(Statement stm) throws SQLException {
        String sentencia = "DELETE FROM empleados WHERE DEP_NO IS NULL";
        int delete = stm.executeUpdate(sentencia);
        System.out.println("Número de filas afectadas: " + delete);


    }

    public static void ejercicio5(Connection con) throws SQLException {
        String insert = "INSERT INTO empleados (EMP_NO, APELLIDO, OFICIO, SALARIO, DEP_NO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preStm = con.prepareStatement(insert); Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingresa el número de empleado: ");
            preStm.setInt(1, sc.nextInt());
            sc.nextLine();
            System.out.println("Ingresa el apellido: ");
            preStm.setString(2, sc.nextLine().toUpperCase());
            System.out.println("Ingresa el oficio: ");
            preStm.setString(3, sc.nextLine().toUpperCase());
            System.out.println("Ingresa el salario: ");
            preStm.setDouble(4, sc.nextDouble());
            sc.nextLine();
            System.out.println("Ingresa el número de departamento: ");
            preStm.setInt(5, sc.nextInt());
            sc.nextLine();

            System.out.println("Número de filas afectadas: " + preStm.executeUpdate());
        }
    }

    public static void ejercicio6(Connection con) throws SQLException {
        String insert = "SELECT * FROM empleados where DEP_NO = ?";
        try (PreparedStatement preStm = con.prepareStatement(insert); Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingresa el número de departamento: ");
            preStm.setInt(1, sc.nextInt());
            ResultSet rSet = preStm.executeQuery();
            while (rSet.next()) {
                System.out.printf("""
                                \u001B[32mDatos Empleado\u001B[0m
                                ------------------------
                                Nº Empleado: %d
                                Apellido: %s
                                Oficio: %s
                                Salario: %.2f
                                Nº Departamento: %d
                                ------------------------\n
                                """, rSet.getInt("EMP_NO"), rSet.getString("APELLIDO"), rSet.getString("OFICIO"),
                        rSet.getDouble("SALARIO"),
                        rSet.getInt("DEP_NO"));
            }

        }
    }

    public static void ejercicio7(Connection con) throws SQLException {
        String update = "SELECT EMP_NO, COMISION FROM empleados where EMP_NO = ?";
        try (PreparedStatement preStm = con.prepareStatement(update,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Ingresa el número de empleado: ");
            preStm.setInt(1, sc.nextInt());
            sc.nextLine();

            ResultSet rSet = preStm.executeQuery();
            rSet.next();
            System.out.println("Ingresa la comisión: ");
            rSet.updateInt("COMISION", sc.nextInt());
            rSet.updateRow();
            System.out.println("Número de filas afectadas: ");
        }
    }
}
