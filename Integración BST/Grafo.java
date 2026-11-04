import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// Grafo G = (V, A) formado por vértices y aristas.
public class Grafo {
    private final Map<String, Vertice> vertices = new LinkedHashMap<>();
    private final List<Arista> aristas = new ArrayList<>();

    // ---------- Construcción ----------
    public boolean agregarVertice(String nombre) {
        if (vertices.containsKey(nombre)) return false;
        vertices.put(nombre, new Vertice(nombre));
        return true;
    }

    // Devuelve false si falta un vértice, si es un lazo o si la arista ya existe.
    public boolean agregarArista(String origen, String destino, double peso, boolean dirigida) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) return false;
        if (origen.equals(destino)) return false;
        if (buscarIndice(origen, destino, dirigida) >= 0) return false;
        aristas.add(new Arista(origen, destino, peso, dirigida));
        return true;
    }

    // Busca una arista igual: una dirigida solo coincide en el mismo sentido,
    // una no dirigida coincide en cualquiera de los dos.
    private int buscarIndice(String origen, String destino, boolean dirigida) {
        for (int i = 0; i < aristas.size(); i++) {
            Arista a = aristas.get(i);
            if (a.isDirigida() != dirigida) continue;
            boolean mismoSentido = a.getOrigen().equals(origen) && a.getDestino().equals(destino);
            boolean contrario = a.getOrigen().equals(destino) && a.getDestino().equals(origen);
            if (mismoSentido || (!dirigida && contrario)) return i;
        }
        return -1;
    }

    public List<Vertice> getVertices() { return new ArrayList<>(vertices.values()); }

    public List<Arista> getAristas() { return new ArrayList<>(aristas); }

    public int numeroVertices() { return vertices.size(); }

    public int numeroAristas() { return aristas.size(); }

    // Hay conexión de a hacia b: una arista dirigida a -> b, o una no dirigida entre ambos
    public boolean existeArista(String a, String b) {
        for (Arista x : aristas) {
            if (x.getOrigen().equals(a) && x.getDestino().equals(b)) return true;
            if (!x.isDirigida() && x.getOrigen().equals(b) && x.getDestino().equals(a)) return true;
        }
        return false;
    }

    // ---------- Grados ----------
    public int gradoSalida(String v) {
        int total = 0;
        for (Arista a : aristas) {
            if (a.getOrigen().equals(v)) total++;
            else if (!a.isDirigida() && a.getDestino().equals(v)) total++;
        }
        return total;
    }

    public int gradoEntrada(String v) {
        int total = 0;
        for (Arista a : aristas) {
            if (a.getDestino().equals(v)) total++;
            else if (!a.isDirigida() && a.getOrigen().equals(v)) total++;
        }
        return total;
    }

    public double pesoTotal() {
        double suma = 0;
        for (Arista a : aristas) suma += a.getPeso();
        return suma;
    }

    // ---------- Clasificación ----------
    public boolean esDirigido() {
        if (aristas.isEmpty()) return false;
        for (Arista a : aristas) if (!a.isDirigida()) return false;
        return true;
    }

    public boolean esNoDirigido() {
        for (Arista a : aristas) if (a.isDirigida()) return false;
        return true;
    }

    public boolean esPonderado() {
        for (Arista a : aristas) if (a.isPonderada()) return true;
        return false;
    }

    // Densidad = aristas posibles que existen. Dirigido: A / (V(V-1)). No dirigido: 2A / (V(V-1))
    public double densidad() {
        double v = vertices.size();
        if (v < 2) return 0;
        double posibles = v * (v - 1);
        return esDirigido() ? aristas.size() / posibles : 2.0 * aristas.size() / posibles;
    }

    public boolean esDenso() { return densidad() > 0.5; }

    // Vértices que se pueden alcanzar desde el origen, con o sin respetar la dirección
    private Set<String> alcanzables(String origen, boolean respetarDireccion) {
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new ArrayDeque<>();
        visitados.add(origen);
        cola.add(origen);
        while (!cola.isEmpty()) {
            String v = cola.poll();
            for (Arista a : aristas) {
                String w = null;
                if (a.getOrigen().equals(v)) w = a.getDestino();
                else if (a.getDestino().equals(v) && (!respetarDireccion || !a.isDirigida())) w = a.getOrigen();
                if (w != null && visitados.add(w)) cola.add(w);
            }
        }
        return visitados;
    }

    // Conexo ignorando la dirección de las aristas
    public boolean esDebilmenteConexo() {
        if (vertices.isEmpty()) return false;
        String primero = vertices.keySet().iterator().next();
        return alcanzables(primero, false).size() == vertices.size();
    }

    // Desde cada vértice se llega a todos los demás respetando la dirección
    public boolean esFuertementeConexo() {
        if (vertices.isEmpty()) return false;
        for (String v : vertices.keySet()) {
            if (alcanzables(v, true).size() != vertices.size()) return false;
        }
        return true;
    }

    // ---------- Ciclos ----------
    // DFS con tres estados. Devuelve el primer ciclo encontrado (el primer y el último
    // vértice son el mismo) o una lista vacía si el grafo es acíclico.
    public List<String> buscarCiclo() {
        Map<String, Integer> estado = new HashMap<>();   // 0 blanco, 1 gris, 2 negro
        for (String v : vertices.keySet()) {
            if (estado.getOrDefault(v, 0) == 0) {
                List<String> ciclo = dfsCiclo(v, null, estado, new ArrayList<>());
                if (!ciclo.isEmpty()) return ciclo;
            }
        }
        return new ArrayList<>();
    }

    private List<String> dfsCiclo(String v, Arista llegada, Map<String, Integer> estado, List<String> pila) {
        estado.put(v, 1);
        pila.add(v);
        for (Arista a : aristas) {
            if (a == llegada) continue;                  // no volver por la misma arista no dirigida
            String w;
            if (a.getOrigen().equals(v)) w = a.getDestino();
            else if (!a.isDirigida() && a.getDestino().equals(v)) w = a.getOrigen();
            else continue;

            int e = estado.getOrDefault(w, 0);
            if (e == 1) {                                // se volvió a un vértice en proceso
                List<String> ciclo = new ArrayList<>(pila.subList(pila.indexOf(w), pila.size()));
                ciclo.add(w);
                return ciclo;
            }
            if (e == 0) {
                List<String> ciclo = dfsCiclo(w, a, estado, pila);
                if (!ciclo.isEmpty()) return ciclo;
            }
        }
        pila.remove(pila.size() - 1);
        estado.put(v, 2);
        return new ArrayList<>();
    }

    public boolean esCiclico() { return !buscarCiclo().isEmpty(); }

    // ---------- Conversiones ----------
    // Quita la dirección: dos aristas dirigidas entre el mismo par se funden en una sola
    // arista no dirigida, con la suma de los pesos.
    public Grafo sinDireccion() {
        Grafo g = new Grafo();
        for (String v : vertices.keySet()) g.agregarVertice(v);
        for (Arista a : aristas) {
            int i = g.buscarIndice(a.getOrigen(), a.getDestino(), false);
            if (i < 0) {
                g.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso(), false);
            } else {
                Arista previa = g.aristas.get(i);
                g.aristas.set(i, new Arista(previa.getOrigen(), previa.getDestino(),
                        previa.getPeso() + a.getPeso(), false));
            }
        }
        return g;
    }

    // Agrega la dirección: cada arista no dirigida se convierte en dos aristas dirigidas,
    // una en cada sentido y con el mismo peso.
    public Grafo conDireccion() {
        Grafo g = new Grafo();
        for (String v : vertices.keySet()) g.agregarVertice(v);
        for (Arista a : aristas) {
            g.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso(), true);
            if (!a.isDirigida()) {
                g.agregarArista(a.getDestino(), a.getOrigen(), a.getPeso(), true);
            }
        }
        return g;
    }
}
