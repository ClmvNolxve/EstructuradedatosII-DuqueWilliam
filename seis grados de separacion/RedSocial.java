import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

// Red social simulada: grafo no dirigido y sin pesos.
// Cada usuario guarda sus amigos en un conjunto ordenado para que el
// recorrido BFS sea siempre el mismo.
public class RedSocial {
    private final Map<String, Set<String>> amigos = new TreeMap<>();
    private int conexiones;

    // ---------- Construcción de la red ----------
    public boolean agregarUsuario(String nombre) {
        if (amigos.containsKey(nombre)) return false;
        amigos.put(nombre, new TreeSet<>());
        return true;
    }

    // Una conexión es una amistad en los dos sentidos.
    // Devuelve false si es consigo mismo o si ya existía.
    public boolean conectar(String a, String b) {
        if (a.equals(b)) return false;
        agregarUsuario(a);
        agregarUsuario(b);
        if (!amigos.get(a).add(b)) return false;
        amigos.get(b).add(a);
        conexiones++;
        return true;
    }

    public boolean existe(String usuario) { return amigos.containsKey(usuario); }

    public Set<String> getAmigos(String usuario) {
        return Collections.unmodifiableSet(amigos.get(usuario));
    }

    public Set<String> getUsuarios() { return Collections.unmodifiableSet(amigos.keySet()); }

    public int numeroUsuarios() { return amigos.size(); }

    public int numeroConexiones() { return conexiones; }

    // ---------- BFS: recorrido por niveles ----------
    // Nivel 0 es el origen, nivel 1 sus amigos directos,
    // nivel 2 los amigos de sus amigos, y así sucesivamente.
    public List<List<String>> recorridoPorNiveles(String origen) {
        List<List<String>> niveles = new ArrayList<>();
        if (!existe(origen)) return niveles;

        Set<String> visitados = new TreeSet<>();
        Queue<String> cola = new ArrayDeque<>();
        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            int tamanioNivel = cola.size();            // usuarios que hay en este nivel
            List<String> nivel = new ArrayList<>();
            for (int i = 0; i < tamanioNivel; i++) {
                String actual = cola.poll();
                nivel.add(actual);
                for (String amigo : amigos.get(actual)) {
                    if (visitados.add(amigo)) {        // add devuelve true si era nuevo
                        cola.add(amigo);
                    }
                }
            }
            niveles.add(nivel);
        }
        return niveles;
    }

    // ---------- BFS: camino más corto ----------
    // Guarda de qué usuario se llegó a cada uno (padre) para reconstruir el camino.
    private Map<String, String> padres(String origen, String destino) {
        Map<String, String> padre = new HashMap<>();
        padre.put(origen, null);
        Queue<String> cola = new ArrayDeque<>();
        cola.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(destino)) break;         // ya se encontró, no hace falta seguir
            for (String amigo : amigos.get(actual)) {
                if (!padre.containsKey(amigo)) {
                    padre.put(amigo, actual);
                    cola.add(amigo);
                }
            }
        }
        return padre;
    }

    // Lista de usuarios desde el origen hasta el destino, o lista vacía si no hay ruta
    public List<String> caminoMasCorto(String origen, String destino) {
        if (!existe(origen) || !existe(destino)) return new ArrayList<>();
        Map<String, String> padre = padres(origen, destino);
        if (!padre.containsKey(destino)) return new ArrayList<>();

        LinkedList<String> camino = new LinkedList<>();
        for (String u = destino; u != null; u = padre.get(u)) {
            camino.addFirst(u);
        }
        return camino;
    }

    // Número mínimo de conexiones entre dos usuarios, o -1 si no hay ruta
    public int distanciaMinima(String origen, String destino) {
        List<String> camino = caminoMasCorto(origen, destino);
        return camino.isEmpty() ? -1 : camino.size() - 1;
    }

    // Distancia al usuario más lejano que se puede alcanzar desde el origen
    public int excentricidad(String origen) {
        return recorridoPorNiveles(origen).size() - 1;
    }

    // La mayor distancia entre cualquier par de usuarios de la red
    public int diametro() {
        int max = 0;
        for (String usuario : amigos.keySet()) {
            max = Math.max(max, excentricidad(usuario));
        }
        return max;
    }
}
