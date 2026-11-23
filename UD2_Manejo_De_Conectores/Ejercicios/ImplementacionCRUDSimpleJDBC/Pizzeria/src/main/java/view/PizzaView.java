package view;

import java.util.List;
import java.util.Scanner;

import model.dto.PizzaResumenDTO;
import model.entity.Pizza;


public class PizzaView {
    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println();
        System.out.println("===== PIZZERÍA =====");
        System.out.println("1. Listar todas las pizzas");
        System.out.println("2. Insertar una nueva pizza");
        System.out.println("3. Actualizar una pizza");
        System.out.println("4. Eliminar una pizza");
        System.out.println("5. Buscar pizzas");
        System.out.println("6. Mostrar listado resumido");
        System.out.println("7. Salir");
    }

    public void mostrarMenuBusqueda() {
        System.out.println("1. Buscar por identificador");
        System.out.println("2. Buscar por nombre");
    }

    public String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.err.println("ERROR: " + mensaje);
    }

    public void mostrarPizza(Pizza p) {
        String descripcion = (p.getDescripcion() == null) ? "" : p.getDescripcion();
        System.out.printf("[%d] %s | %s | %.2f € | %s%n",
                p.getId(), p.getNombre(), descripcion, p.getPrecio(),
                p.isDisponible() ? "Disponible" : "No disponible");
    }

    public void mostrarPizzas(List<Pizza> pizzas) {
        for (Pizza p : pizzas) {
            mostrarPizza(p);
        }
    }

    public void mostrarResumen(List<PizzaResumenDTO> resumen) {
        for (PizzaResumenDTO dto : resumen) {
            System.out.printf("%s - %.2f €%n", dto.getNombre(), dto.getPrecio());
        }
    }
}
