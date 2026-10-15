public class NodoArbol<T> {
    private T dato;
    private NodoArbol<T> izquierdo;
    private NodoArbol<T> derecho;

    // Constructor para un nodo sin hijos
    public NodoArbol(T dato) {
        this(dato, null, null);
    }

    // Constructor con hijos
    public NodoArbol(T dato, NodoArbol<T> izquierdo, NodoArbol<T> derecho) {
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    // ---------- Getters y setters ----------
    public T getDato() { return dato; }
    public void setDato(T dato) { this.dato = dato; }

    public NodoArbol<T> getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoArbol<T> izquierdo) { this.izquierdo = izquierdo; }

    public NodoArbol<T> getDerecho() { return derecho; }
    public void setDerecho(NodoArbol<T> derecho) { this.derecho = derecho; }

    // ---------- Métodos básicos ----------
    public boolean tieneIzquierdo() { return izquierdo != null; }

    public boolean tieneDerecho() { return derecho != null; }

    // Una hoja no tiene hijos
    public boolean esHoja() { return izquierdo == null && derecho == null; }

    // Cantidad de hijos: 0, 1 o 2
    public int cantidadHijos() {
        int total = 0;
        if (izquierdo != null) total++;
        if (derecho != null) total++;
        return total;
    }

    @Override
    public String toString() { return String.valueOf(dato); }
}
