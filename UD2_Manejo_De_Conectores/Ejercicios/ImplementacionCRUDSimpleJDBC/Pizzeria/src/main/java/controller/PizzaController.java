package controller;

import java.sql.SQLException;
import java.util.List;

import model.dto.PizzaResumenDTO;
import model.entity.Pizza;
import model.service.PizzaService;
import view.PizzaView;


public class PizzaController {
    private final PizzaService servicio;
    private final PizzaView vista;

    public PizzaController(PizzaService servicio, PizzaView vista) {
        this.servicio = servicio;
        this.vista = vista;
    }

    public void iniciar() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = leerOpcion();
            try {
                switch (opcion) {
                    case 1: listarPizzas(); break;
                    case 2: insertarPizza(); break;
                    case 3: actualizarPizza(); break;
                    case 4: eliminarPizza(); break;
                    case 5: buscarPizzas(); break;
                    case 6: mostrarResumen(); break;
                    case 7: vista.mostrarMensaje("Hasta pronto."); break;
                    default: vista.mostrarError("Opción no válida. Elige un número del 1 al 7.");
                }
            } catch (SQLException e) {
                vista.mostrarError("Error de base de datos: " + e.getMessage());
            } catch (NumberFormatException e) {
                // Debe ir antes que IllegalArgumentException, de la que hereda
                vista.mostrarError("Has introducido un valor numérico no válido.");
            } catch (IllegalArgumentException e) {
                vista.mostrarError(e.getMessage());
            }
        } while (opcion != 7);
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(vista.pedirTexto("Elige una opción: "));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void listarPizzas() throws SQLException {
        List<Pizza> pizzas = servicio.listarPizzas();
        if (pizzas.isEmpty()) {
            vista.mostrarMensaje("No hay pizzas en el catálogo.");
        } else {
            vista.mostrarPizzas(pizzas);
        }
    }

    private void insertarPizza() throws SQLException {
        String nombre = vista.pedirTexto("Nombre: ");
        String descripcion = vista.pedirTexto("Descripción: ");
        double precio = parsearPrecio(vista.pedirTexto("Precio: "));
        boolean disponible = parsearDisponible(vista.pedirTexto("¿Disponible? (s/n): "));

        if (descripcion.isEmpty()) {
            descripcion = null;
        }
        servicio.insertarPizza(new Pizza(nombre, descripcion, precio, disponible));
        vista.mostrarMensaje("Pizza insertada correctamente.");
    }

    private void actualizarPizza() throws SQLException {
        int id = Integer.parseInt(vista.pedirTexto("Identificador de la pizza a actualizar: "));
        Pizza pizza = servicio.buscarPorId(id);
        if (pizza == null) {
            vista.mostrarError("No existe ninguna pizza con identificador " + id + ".");
            return;
        }

        vista.mostrarMensaje("Datos actuales:");
        vista.mostrarPizza(pizza);
        vista.mostrarMensaje("Pulsa Enter para mantener el valor actual.");

        String nombre = vista.pedirTexto("Nuevo nombre: ");
        String descripcion = vista.pedirTexto("Nueva descripción: ");
        String precio = vista.pedirTexto("Nuevo precio: ");
        String disponible = vista.pedirTexto("¿Disponible? (s/n): ");

        if (!nombre.isEmpty()) {
            pizza.setNombre(nombre);
        }
        if (!descripcion.isEmpty()) {
            pizza.setDescripcion(descripcion);
        }
        if (!precio.isEmpty()) {
            pizza.setPrecio(parsearPrecio(precio));
        }
        if (!disponible.isEmpty()) {
            pizza.setDisponible(parsearDisponible(disponible));
        }

        if (servicio.actualizarPizza(pizza)) {
            vista.mostrarMensaje("Pizza actualizada correctamente.");
        } else {
            vista.mostrarError("No se ha actualizado nada: la pizza con identificador " + id + " ya no existe.");
        }
    }

    private void eliminarPizza() throws SQLException {
        int id = Integer.parseInt(vista.pedirTexto("Identificador de la pizza a eliminar: "));
        if (servicio.eliminarPizza(id)) {
            vista.mostrarMensaje("Pizza eliminada correctamente.");
        } else {
            vista.mostrarError("No existe ninguna pizza con identificador " + id + ".");
        }
    }

    private void buscarPizzas() throws SQLException {
        vista.mostrarMenuBusqueda();
        int tipo = Integer.parseInt(vista.pedirTexto("Elige el tipo de búsqueda: "));

        if (tipo == 1) {
            int id = Integer.parseInt(vista.pedirTexto("Identificador: "));
            Pizza pizza = servicio.buscarPorId(id);
            if (pizza == null) {
                vista.mostrarError("No existe ninguna pizza con identificador " + id + ".");
            } else {
                vista.mostrarPizza(pizza);
            }
        } else if (tipo == 2) {
            String nombre = vista.pedirTexto("Nombre (o parte del nombre): ");
            List<Pizza> pizzas = servicio.buscarPorNombre(nombre);
            if (pizzas.isEmpty()) {
                vista.mostrarMensaje("No se ha encontrado ninguna pizza con ese nombre.");
            } else {
                vista.mostrarPizzas(pizzas);
            }
        } else {
            vista.mostrarError("Tipo de búsqueda no válido.");
        }
    }

    private void mostrarResumen() throws SQLException {
        List<PizzaResumenDTO> resumen = servicio.listarResumen();
        if (resumen.isEmpty()) {
            vista.mostrarMensaje("No hay pizzas en el catálogo.");
        } else {
            vista.mostrarResumen(resumen);
        }
    }

    // Admite coma o punto como separador decimal
    private double parsearPrecio(String texto) {
        return Double.parseDouble(texto.replace(',', '.'));
    }

    private boolean parsearDisponible(String texto) {
        String t = texto.toLowerCase();
        if (t.equals("s") || t.equals("si") || t.equals("sí")) {
            return true;
        }
        if (t.equals("n") || t.equals("no")) {
            return false;
        }
        throw new IllegalArgumentException("Disponibilidad no válida. Escribe 's' o 'n'.");
    }
}
