import java.util.ArrayList;
import java.util.List;

public class Main {

    // ---------- Método de la guía: altura recursiva ----------
    // Caso base: una hoja tiene altura 0.
    // Caso recursivo: 1 + la mayor altura entre los hijos.
    static int altura(NodoGeneral<?> nodo) {
        if (nodo.getHijos().isEmpty()) return 0;
        int max = 0;
        for (NodoGeneral<?> hijo : nodo.getHijos()) {
            max = Math.max(max, altura(hijo));
        }
        return 1 + max;
    }

    // ---------- Métricas adicionales ----------
    // Profundidad: distancia (en aristas) desde la raíz hasta el nodo.
    static int profundidad(NodoGeneral<?> actual, NodoGeneral<?> objetivo, int nivel) {
        if (actual == objetivo) return nivel;
        for (NodoGeneral<?> hijo : actual.getHijos()) {
            int r = profundidad(hijo, objetivo, nivel + 1);
            if (r >= 0) return r;
        }
        return -1;
    }

    // Camino desde la raíz hasta el nodo objetivo (lista de datos)
    static <T> List<T> camino(NodoGeneral<T> actual, NodoGeneral<T> objetivo) {
        if (actual == objetivo) {
            List<T> lista = new ArrayList<>();
            lista.add(actual.getDato());
            return lista;
        }
        for (NodoGeneral<T> hijo : actual.getHijos()) {
            List<T> sub = camino(hijo, objetivo);
            if (sub != null) {
                sub.add(0, actual.getDato());
                return sub;
            }
        }
        return null;
    }

    static int gradoDelArbol(NodoGeneral<?> nodo) {
        int max = nodo.grado();
        for (NodoGeneral<?> hijo : nodo.getHijos()) {
            max = Math.max(max, gradoDelArbol(hijo));
        }
        return max;
    }

    static int contarNodos(NodoGeneral<?> nodo) {
        int total = 1;
        for (NodoGeneral<?> hijo : nodo.getHijos()) total += contarNodos(hijo);
        return total;
    }

    static void recolectarHojas(NodoGeneral<String> nodo, List<NodoGeneral<String>> hojas) {
        if (nodo.esHoja()) hojas.add(nodo);
        for (NodoGeneral<String> hijo : nodo.getHijos()) recolectarHojas(hijo, hojas);
    }

    static void imprimirTabla(NodoGeneral<String> raiz, NodoGeneral<String> nodo) {
        int prof = profundidad(raiz, nodo, 0);
        String tipo = nodo == raiz ? "Raíz" : (nodo.esHoja() ? "Hoja" : "Interno");
        System.out.printf("%-24s %-8s %-6d %-6d %-6d %d%n",
                nodo.getDato(), tipo, nodo.grado(), prof, prof + 1, altura(nodo));
        for (NodoGeneral<String> hijo : nodo.getHijos()) imprimirTabla(raiz, hijo);
    }

    static String ruta(List<String> camino) {
        return String.join(" -> ", camino) + "  (longitud = " + (camino.size() - 1) + " aristas)";
    }

    public static void main(String[] args) {
        // ---------- Construcción del árbol (13 nodos, 4 niveles) ----------
        NodoGeneral<String> universidad = new NodoGeneral<>("Universidad");

        NodoGeneral<String> ingenieria = new NodoGeneral<>("Facultad de Ingeniería");
        NodoGeneral<String> ciencias = new NodoGeneral<>("Facultad de Ciencias");
        NodoGeneral<String> biblioteca = new NodoGeneral<>("Biblioteca");
        universidad.agregarHijo(ingenieria);
        universidad.agregarHijo(ciencias);
        universidad.agregarHijo(biblioteca);

        NodoGeneral<String> sistemas = new NodoGeneral<>("Sistemas");
        NodoGeneral<String> industrial = new NodoGeneral<>("Industrial");
        ingenieria.agregarHijo(sistemas);
        ingenieria.agregarHijo(industrial);

        NodoGeneral<String> matematicas = new NodoGeneral<>("Matemáticas");
        NodoGeneral<String> fisica = new NodoGeneral<>("Física");
        ciencias.agregarHijo(matematicas);
        ciencias.agregarHijo(fisica);

        NodoGeneral<String> hemeroteca = new NodoGeneral<>("Hemeroteca");
        biblioteca.agregarHijo(hemeroteca);

        NodoGeneral<String> ed2 = new NodoGeneral<>("Estructura de Datos II");
        NodoGeneral<String> bd = new NodoGeneral<>("Bases de Datos");
        sistemas.agregarHijo(ed2);
        sistemas.agregarHijo(bd);

        NodoGeneral<String> logistica = new NodoGeneral<>("Logística");
        industrial.agregarHijo(logistica);

        NodoGeneral<String> calculo = new NodoGeneral<>("Cálculo");
        matematicas.agregarHijo(calculo);

        // ---------- 1. Tabla de métricas ----------
        System.out.println("=== TABLA DE MÉTRICAS ===");
        System.out.printf("%-24s %-8s %-6s %-6s %-6s %s%n",
                "Nodo", "Tipo", "Grado", "Prof.", "Nivel", "Altura");
        imprimirTabla(universidad, universidad);

        List<NodoGeneral<String>> hojas = new ArrayList<>();
        recolectarHojas(universidad, hojas);
        int total = contarNodos(universidad);
        System.out.println();
        System.out.println("Total de nodos:   " + total);
        System.out.println("Hojas:            " + hojas.size());
        System.out.println("Nodos internos:   " + (total - hojas.size()) + " (incluye la raíz)");
        System.out.println("Grado del árbol:  " + gradoDelArbol(universidad));
        System.out.println("Altura del árbol: " + altura(universidad));

        // ---------- 2. Profundidad de cinco nodos ----------
        System.out.println();
        System.out.println("=== PROFUNDIDAD DE CINCO NODOS ===");
        NodoGeneral<String>[] cinco = new NodoGeneral[]{universidad, ingenieria, sistemas, ed2, fisica};
        for (NodoGeneral<String> n : cinco) {
            System.out.println(n.getDato() + ": " + profundidad(universidad, n, 0));
        }

        // ---------- Ejercicio 1: profundidad de cada hoja ----------
        System.out.println();
        System.out.println("=== EJERCICIO 1: PROFUNDIDAD DE CADA HOJA ===");
        for (NodoGeneral<String> h : hojas) {
            System.out.println(h.getDato() + ": " + profundidad(universidad, h, 0));
        }

        // ---------- Ejercicio 2: dos caminos ----------
        System.out.println();
        System.out.println("=== EJERCICIO 2: DOS CAMINOS ===");
        System.out.println("Camino A: " + ruta(camino(universidad, ed2)));
        System.out.println("Camino B: " + ruta(camino(universidad, hemeroteca)));

        // ---------- 3. Pruebas: comparación con el cálculo manual ----------
        System.out.println();
        System.out.println("=== PRUEBAS (esperado manual vs. obtenido) ===");
        probar("Altura de una hoja (Física)", 0, altura(fisica));
        probar("Altura de Sistemas", 1, altura(sistemas));
        probar("Altura de Facultad de Ingeniería", 2, altura(ingenieria));
        probar("Altura de Biblioteca", 1, altura(biblioteca));
        probar("Altura del árbol completo", 3, altura(universidad));

        // ---------- Ejercicio 3: agregar un nuevo nivel ----------
        System.out.println();
        System.out.println("=== EJERCICIO 3: AGREGAR UN NUEVO NIVEL ===");
        System.out.println("Altura antes:   " + altura(universidad));
        NodoGeneral<String> derivadas = new NodoGeneral<>("Derivadas");
        calculo.agregarHijo(derivadas);
        System.out.println("Se agregó 'Derivadas' como hijo de 'Cálculo'");
        System.out.println("Altura después: " + altura(universidad));
        System.out.println("Profundidad de Derivadas: " + profundidad(universidad, derivadas, 0));
        System.out.println("Total de nodos: " + contarNodos(universidad));
        probar("Altura del árbol con nuevo nivel", 4, altura(universidad));
    }

    static void probar(String descripcion, int esperado, int obtenido) {
        String resultado = esperado == obtenido ? "OK" : "FALLA";
        System.out.println(descripcion + ": esperado=" + esperado
                + ", obtenido=" + obtenido + " -> " + resultado);
    }
}
