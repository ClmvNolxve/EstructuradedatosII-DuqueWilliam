import java.util.ArrayList;
import java.util.List;

/**
 * Nodo de un árbol general: guarda un dato y una lista de hijos.
 * @param <T> tipo del dato almacenado
 */
public class NodoGeneral<T> {
    private T dato;
    private List<NodoGeneral<T>> hijos = new ArrayList<>();

    public NodoGeneral(T dato) {
        this.dato = dato;
    }

    public void agregarHijo(NodoGeneral<T> hijo) {
        hijos.add(hijo);
    }

    public T getDato() {
        return dato;
    }

    public List<NodoGeneral<T>> getHijos() {
        return hijos;
    }
}
