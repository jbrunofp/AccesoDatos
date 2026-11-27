import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.time.LocalDate;

public class Main {
    static void main(String[] args) {
        Ejercicio1();
    }

    public static void Ejercicio1() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
             EntityManager em = emf.createEntityManager()) {
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                Cliente cliente = new Cliente("Aiden", LocalDate.of(2005, 4, 30));
                em.persist(cliente);
                et.commit();
            } catch (Exception e) {
                if (et.isActive()) et.rollback();
                e.printStackTrace();
            }
        }
    }
}
