package model.entity;

public class Pizza {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;

    public Pizza(int id, String nombre, String descripcion, double precio, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
    }


    public Pizza(String nombre, String descripcion, double precio, boolean disponible) {
        this(0, nombre, descripcion, precio, disponible);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
