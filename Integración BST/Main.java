import java.util.List;
import java.util.Locale;

public class Main {

    // Red social de frutas típicas de Colombia.
    // Cada arista A -> B significa que A sigue a B y el peso son las interacciones por semana.
    static Grafo construirRed() {
        Grafo g = new Grafo();
        String[] frutas = {
            "Borojó", "Chontaduro", "Curuba", "Feijoa", "Granadilla", "Guanábana",
            "Lulo", "Mango", "Maracuyá", "Mora", "Papaya", "Uchuva"
        };
        for (String f : frutas) g.agregarVertice(f);

        Object[][] sigue = {
            {"Lulo", "Maracuyá", 8.0}, {"Maracuyá", "Mango", 5.0}, {"Mango", "Lulo", 3.0},
            {"Mora", "Lulo", 6.0}, {"Mora", "Uchuva", 4.0}, {"Uchuva", "Curuba", 7.0},
            {"Curuba", "Granadilla", 2.0}, {"Granadilla", "Maracuyá", 9.0}, {"Guanábana", "Granadilla", 5.0},
            {"Papaya", "Mango", 4.0}, {"Chontaduro", "Papaya", 6.0}, {"Borojó", "Chontaduro", 3.0},
            {"Chontaduro", "Borojó", 5.0}, {"Feijoa", "Mora", 7.0}, {"Guanábana", "Mora", 3.0},
            {"Feijoa", "Guanábana", 4.0}, {"Uchuva", "Mango", 2.0}, {"Borojó", "Papaya", 2.0}
        };
        for (Object[] s : sigue) g.agregarArista((String) s[0], (String) s[1], (Double) s[2], true);
        return g;
    }

    static String sn(boolean valor) { return valor ? "Sí" : "No"; }

    static void clasificar(String titulo, Grafo g) {
        System.out.println(titulo);
        System.out.println("    Vértices " + g.numeroVertices() + ", aristas " + g.numeroAristas());
        System.out.println("    Dirigido " + sn(g.esDirigido()) + ", ponderado " + sn(g.esPonderado())
                + ", cíclico " + sn(g.esCiclico()) + ", conexo " + sn(g.esDebilmenteConexo()));
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Grafo g = construirRed();

        // ================= 1. Vértices =================
        System.out.println("=== 1. VÉRTICES ===");
        System.out.printf("%-14s %-16s %s%n", "Vértice", "Grado de entrada", "Grado de salida");
        for (Vertice v : g.getVertices()) {
            System.out.printf("%-14s %-16d %d%n", v.getNombre(), g.gradoEntrada(v.getNombre()), g.gradoSalida(v.getNombre()));
        }

        // ================= 2. Aristas =================
        System.out.println();
        System.out.println("=== 2. ARISTAS ===");
        System.out.printf("%-3s %-12s %-3s %-12s %-5s %-9s %s%n", "No", "Origen", "", "Destino", "Peso", "Dirigida", "Ponderada");
        int n = 1;
        for (Arista a : g.getAristas()) {
            System.out.printf("%-3d %-12s %-3s %-12s %-5.1f %-9s %s%n", n++, a.getOrigen(),
                    a.isDirigida() ? "->" : "--", a.getDestino(), a.getPeso(), sn(a.isDirigida()), sn(a.isPonderada()));
        }
        System.out.println("Peso total " + g.pesoTotal());

        // ================= 3. Clasificación =================
        System.out.println();
        System.out.println("=== 3. CLASIFICACIÓN DEL GRAFO ===");
        System.out.println("G = (V, A) con |V| = " + g.numeroVertices() + " y |A| = " + g.numeroAristas());
        System.out.println("Dirigido " + sn(g.esDirigido()) + ". Todas las aristas van de un seguidor a la fruta que sigue");
        System.out.println("Ponderado " + sn(g.esPonderado()) + ". El peso son las interacciones por semana");
        List<String> ciclo = g.buscarCiclo();
        System.out.println("Cíclico " + sn(!ciclo.isEmpty()) + ". Ciclo encontrado " + String.join(" -> ", ciclo));
        System.out.println("Débilmente conexo " + sn(g.esDebilmenteConexo()) + ". Ignorando la dirección se llega de cualquier fruta a otra");
        System.out.println("Fuertemente conexo " + sn(g.esFuertementeConexo()) + ". Siguiendo la dirección no siempre se llega");
        System.out.printf("Densidad %.3f. %s%n", g.densidad(), g.esDenso() ? "Denso" : "Disperso");

        // ================= 4. Ejercicio no dirigido a dirigido =================
        System.out.println();
        System.out.println("=== 4. EJERCICIO CONVERTIR NO DIRIGIDO EN DIRIGIDO ===");
        Grafo noDirigido = g.sinDireccion();
        Grafo redirigido = noDirigido.conDireccion();
        clasificar("Grafo original, dirigido", g);
        clasificar("Versión sin dirección, amistad entre frutas", noDirigido);
        clasificar("Versión sin dirección convertida otra vez en dirigida", redirigido);
        System.out.println();
        System.out.println("Cada arista no dirigida se convierte en dos aristas dirigidas, una en cada sentido");
        System.out.println("Se pierde quién seguía a quién y aparecen seguimientos que no existían");
        System.out.println("Por ejemplo Lulo y Maracuyá. Antes solo Lulo seguía a Maracuyá. Ahora también Maracuyá sigue a Lulo: "
                + sn(redirigido.existeArista("Maracuyá", "Lulo")) + " (en el original era " + sn(g.existeArista("Maracuyá", "Lulo")) + ")");

        // ================= 5. Ejercicio ciclo =================
        System.out.println();
        System.out.println("=== 5. EJERCICIO CICLOS ===");
        System.out.println("Ciclo de tres frutas " + String.join(" -> ", ciclo));
        System.out.println("Ciclo de dos frutas Borojó -> Chontaduro -> Borojó: "
                + sn(g.existeArista("Borojó", "Chontaduro") && g.existeArista("Chontaduro", "Borojó")));

        // ================= 6. Ejercicio densidad =================
        System.out.println();
        System.out.println("=== 6. EJERCICIO DENSIDAD ===");
        int v = g.numeroVertices();
        System.out.println("Aristas posibles en un grafo dirigido " + v + " x " + (v - 1) + " = " + (v * (v - 1)));
        System.out.printf("Aristas existentes %d, densidad %.3f%n", g.numeroAristas(), g.densidad());

        // ================= 7. Pruebas =================
        System.out.println();
        System.out.println("=== 7. PRUEBAS ===");
        Pruebas.ejecutar();
    }
}
