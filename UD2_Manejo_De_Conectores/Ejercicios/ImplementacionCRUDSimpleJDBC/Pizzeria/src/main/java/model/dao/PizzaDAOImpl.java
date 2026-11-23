package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.PizzaResumenDTO;
import model.entity.Pizza;
import model.util.ConnectionPool;

public class PizzaDAOImpl implements PizzaDAO {

    @Override
    public Pizza obtenerPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, precio, disponible FROM pizzas WHERE id = ?";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Pizza> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, descripcion, precio, disponible FROM pizzas ORDER BY id";
        List<Pizza> pizzas = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pizzas.add(mapear(rs));
            }
        }
        return pizzas;
    }

    @Override
    public List<Pizza> buscarPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, precio, disponible FROM pizzas "
                   + "WHERE nombre LIKE ? ORDER BY id";
        List<Pizza> pizzas = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pizzas.add(mapear(rs));
                }
            }
        }
        return pizzas;
    }

    @Override
    public List<PizzaResumenDTO> listarResumen() throws SQLException {
        String sql = "SELECT nombre, precio FROM pizzas ORDER BY id";
        List<PizzaResumenDTO> resumen = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resumen.add(new PizzaResumenDTO(rs.getString("nombre"), rs.getDouble("precio")));
            }
        }
        return resumen;
    }

    @Override
    public void guardar(Pizza pizza) throws SQLException {
        String sql = "INSERT INTO pizzas (nombre, descripcion, precio, disponible) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pizza.getNombre());
            ps.setString(2, pizza.getDescripcion());
            ps.setDouble(3, pizza.getPrecio());
            ps.setBoolean(4, pizza.isDisponible());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean actualizar(Pizza pizza) throws SQLException {
        String sql = "UPDATE pizzas SET nombre = ?, descripcion = ?, precio = ?, disponible = ? WHERE id = ?";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pizza.getNombre());
            ps.setString(2, pizza.getDescripcion());
            ps.setDouble(3, pizza.getPrecio());
            ps.setBoolean(4, pizza.isDisponible());
            ps.setInt(5, pizza.getId());
            return ps.executeUpdate() > 0; // Filas afectadas: 0 si el id no existe
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM pizzas WHERE id = ?";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0; // Filas afectadas: 0 si el id no existe
        }
    }


    private Pizza mapear(ResultSet rs) throws SQLException {
        return new Pizza(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getBoolean("disponible"));
    }
}
