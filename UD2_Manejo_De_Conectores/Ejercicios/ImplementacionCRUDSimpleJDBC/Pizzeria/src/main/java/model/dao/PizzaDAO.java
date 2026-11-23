package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.dto.PizzaResumenDTO;
import model.entity.Pizza;

public interface PizzaDAO {
    Pizza obtenerPorId(int id) throws SQLException;
    List<Pizza> listarTodos() throws SQLException;
    List<Pizza> buscarPorNombre(String nombre) throws SQLException;
    List<PizzaResumenDTO> listarResumen() throws SQLException;
    void guardar(Pizza pizza) throws SQLException;
    boolean actualizar(Pizza pizza) throws SQLException;
    boolean eliminar(int id) throws SQLException;
}
