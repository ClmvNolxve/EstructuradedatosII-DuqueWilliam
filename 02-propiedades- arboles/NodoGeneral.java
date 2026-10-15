import java.util.ArrayList;
import java.util.List;

public class NodoGeneral<T> {
    private T dato;
    private List<NodoGeneral<T>> hijos = new ArrayList<>();

    public NodoGeneral(T dato) { this.dato = dato; }

    public void agregarHijo(NodoGeneral<T> hijo) { hijos.add(hijo); }

    public T getDato() { return dato; }

    public List<NodoGeneral<T>> getHijos() { return hijos; }

    // Una hoja no tiene hijos
    public boolean esHoja() { return hijos.isEmpty(); }

    // El grado de un nodo es su número de hijos
    public int grado() { return hijos.size(); }
}
