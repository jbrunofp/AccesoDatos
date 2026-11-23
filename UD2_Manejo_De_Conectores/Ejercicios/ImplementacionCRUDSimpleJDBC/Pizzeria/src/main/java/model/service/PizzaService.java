package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dto.PizzaResumenDTO;
import model.entity.Pizza;

public interface PizzaService {
    List<Pizza> listarPizzas() throws SQLException;
    Pizza buscarPorId(int id) throws SQLException;
    List<Pizza> buscarPorNombre(String nombre) throws SQLException;
    List<PizzaResumenDTO> listarResumen() throws SQLException;
    void insertarPizza(Pizza pizza) throws SQLException;
    boolean actualizarPizza(Pizza pizza) throws SQLException;
    boolean eliminarPizza(int id) throws SQLException;
}
