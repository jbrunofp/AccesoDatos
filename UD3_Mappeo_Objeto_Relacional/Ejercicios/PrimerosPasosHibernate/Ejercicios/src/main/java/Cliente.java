import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String nombre;

    @Column(nullable = true)
    LocalDate fechanac;

    public Cliente() {}

    public Cliente(String nombre, LocalDate fechanac) {
        this.nombre = nombre;
        this.fechanac = fechanac;

    }
}
