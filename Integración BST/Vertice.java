// Un vértice de la red social. Representa a una fruta típica de Colombia.
public class Vertice {
    private final String nombre;

    public Vertice(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object otro) {
        return otro instanceof Vertice && ((Vertice) otro).nombre.equals(nombre);
    }

    @Override
    public int hashCode() { return nombre.hashCode(); }

    @Override
    public String toString() { return nombre; }
}
