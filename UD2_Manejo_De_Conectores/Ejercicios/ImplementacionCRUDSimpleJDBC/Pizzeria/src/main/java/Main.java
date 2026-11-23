import java.sql.SQLException;

import controller.PizzaController;
import model.dao.PizzaDAO;
import model.dao.PizzaDAOImpl;
import model.service.PizzaService;
import model.service.PizzaServiceImpl;
import model.util.ConnectionPool;
import view.PizzaView;

public class Main {
    public static void main(String[] args) {
        PizzaDAO dao = new PizzaDAOImpl();
        PizzaService servicio = new PizzaServiceImpl(dao);
        PizzaView vista = new PizzaView();
        PizzaController controlador = new PizzaController(servicio, vista);

        try {
            controlador.iniciar();
        } finally {
            try {
                ConnectionPool.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar el pool de conexiones: " + e.getMessage());
            }
        }
    }
}
