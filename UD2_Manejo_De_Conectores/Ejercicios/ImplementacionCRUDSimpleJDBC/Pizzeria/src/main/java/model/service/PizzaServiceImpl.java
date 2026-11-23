package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.PizzaDAO;
import model.dto.PizzaResumenDTO;
import model.entity.Pizza;

public class PizzaServiceImpl implements PizzaService {
    private final PizzaDAO pizzaDAO;

    public PizzaServiceImpl(PizzaDAO pizzaDAO) {
        this.pizzaDAO = pizzaDAO;
    }

    @Override
    public List<Pizza> listarPizzas() throws SQLException {
        return pizzaDAO.listarTodos();
    }

    @Override
    public Pizza buscarPorId(int id) throws SQLException {
        return pizzaDAO.obtenerPorId(id);
    }

    @Override
    public List<Pizza> buscarPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Debes indicar un nombre para buscar.");
        }
        return pizzaDAO.buscarPorNombre(nombre.trim());
    }

    @Override
    public List<PizzaResumenDTO> listarResumen() throws SQLException {
        return pizzaDAO.listarResumen();
    }

    @Override
    public void insertarPizza(Pizza pizza) throws SQLException {
        validar(pizza);
        pizzaDAO.guardar(pizza);
    }

    @Override
    public boolean actualizarPizza(Pizza pizza) throws SQLException {
        validar(pizza);
        return pizzaDAO.actualizar(pizza);
    }

    @Override
    public boolean eliminarPizza(int id) throws SQLException {
        return pizzaDAO.eliminar(id);
    }

    // Reglas de negocio: nombre no vacío y precio mayor que cero
    private void validar(Pizza pizza) {
        if (pizza.getNombre() == null || pizza.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la pizza no puede estar vacío.");
        }
        if (pizza.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres.");
        }
        if (pizza.getDescripcion() != null && pizza.getDescripcion().length() > 255) {
            throw new IllegalArgumentException("La descripción no puede superar los 255 caracteres.");
        }
        if (!(pizza.getPrecio() > 0)) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        if (pizza.getPrecio() > 9999.99) {
            throw new IllegalArgumentException("El precio no puede superar 9999,99 €.");
        }
    }
}
