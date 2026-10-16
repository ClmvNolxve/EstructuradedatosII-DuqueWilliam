import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Main {

    static final String[] FRUTAS = {
        "Aguacate", "Banano", "Cereza", "Durazno", "Feijoa", "Fresa", "Granadilla", "Guayaba",
        "Lulo", "Mango", "Maracuyá", "Mora", "Naranja", "Papaya", "Uchuva"
    };
    static final int BASE = 1001;   // el código de Aguacate es 1001, el de Uchuva 1015

    // ---------- Utilidades ----------
    static List<Integer> codigosOrdenados(int n) {
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < n; i++) lista.add(BASE + i);
        return lista;
    }

    // Orden de inserción que produce el BST más bajo posible:
    // primero la mediana, luego las medianas de cada mitad.
    static void ordenBalanceado(List<Integer> ordenada, int lo, int hi, List<Integer> salida) {
        if (lo > hi) return;
        int medio = (lo + hi) / 2;
        salida.add(ordenada.get(medio));
        ordenBalanceado(ordenada, lo, medio - 1, salida);
        ordenBalanceado(ordenada, medio + 1, hi, salida);
    }

    static ArbolBST<Integer, String> construir(List<Integer> orden, boolean nombresReales) {
        ArbolBST<Integer, String> arbol = new ArbolBST<>();
        for (int codigo : orden) {
            String nombre = nombresReales ? FRUTAS[codigo - BASE] : "Fruta-" + codigo;
            arbol.insertar(codigo, nombre);
        }
        return arbol;
    }

    // Promedio de comparaciones al buscar cada código del árbol
    static double promedioComparaciones(ArbolBST<Integer, String> arbol, List<Integer> codigos) {
        long suma = 0;
        for (int codigo : codigos) {
            arbol.buscar(codigo);
            suma += arbol.getUltimasComparaciones();
        }
        return (double) suma / codigos.size();
    }

    static void mostrarBusqueda(String etiqueta, ArbolBST<Integer, String> arbol, int codigo) {
        String resultado = arbol.buscar(codigo);
        System.out.println(etiqueta + ": buscar " + codigo + " -> "
                + (resultado != null ? resultado : "no encontrado")
                + " (" + arbol.getUltimasComparaciones() + " comparaciones)");
    }

    public static void main(String[] args) {
        // ================= 1. Demostración con 15 frutas =================
        List<Integer> ordenados = codigosOrdenados(FRUTAS.length);
        List<Integer> aleatorios = new ArrayList<>(ordenados);
        Collections.shuffle(aleatorios, new Random(7));
        List<Integer> balanceados = new ArrayList<>();
        ordenBalanceado(ordenados, 0, ordenados.size() - 1, balanceados);

        ArbolBST<Integer, String> arbolOrdenado = construir(ordenados, true);
        ArbolBST<Integer, String> arbolAleatorio = construir(aleatorios, true);
        ArbolBST<Integer, String> arbolBalanceado = construir(balanceados, true);

        System.out.println("=== 1. BST DE FRUTAS (15 códigos) ===");
        System.out.println();
        System.out.println("--- Insertando en orden ascendente " + ordenados + " ---");
        arbolOrdenado.imprimir();
        System.out.println("Altura: " + arbolOrdenado.altura());
        System.out.println();
        System.out.println("--- Insertando en orden aleatorio " + aleatorios + " ---");
        arbolAleatorio.imprimir();
        System.out.println("Altura: " + arbolAleatorio.altura());
        System.out.println();
        System.out.println("--- Insertando en orden balanceado (mejor caso) " + balanceados + " ---");
        arbolBalanceado.imprimir();
        System.out.println("Altura: " + arbolBalanceado.altura());

        // ================= 2. Búsquedas =================
        System.out.println();
        System.out.println("=== 2. BÚSQUEDA RECURSIVA ===");
        mostrarBusqueda("Ordenado  ", arbolOrdenado, 1015);
        mostrarBusqueda("Aleatorio ", arbolAleatorio, 1015);
        mostrarBusqueda("Balanceado", arbolBalanceado, 1015);
        mostrarBusqueda("Ordenado  ", arbolOrdenado, 9999);
        mostrarBusqueda("Balanceado", arbolBalanceado, 1010);

        // ================= 3. Pruebas =================
        System.out.println();
        System.out.println("=== 3. PRUEBAS ===");
        Pruebas.ejecutar();

        // ================= 4. Tabla comparativa =================
        System.out.println();
        System.out.println("=== 4. TABLA COMPARATIVA (alturas y comparaciones promedio por búsqueda) ===");
        System.out.println("Aleatorio = promedio de 20 corridas con semilla 42.");
        System.out.println();
        System.out.printf("%-6s | %-8s %-9s %-10s %-7s | %-9s %-10s %-11s%n",
                "n", "Altura", "Altura", "Altura", "Altura", "Comp.", "Comp.", "Comp.");
        System.out.printf("%-6s | %-8s %-9s %-10s %-7s | %-9s %-10s %-11s%n",
                "", "ordenado", "aleatorio", "balanceado", "mínima", "ordenado", "aleatorio", "balanceado");

        final int CORRIDAS = 20;
        Random rnd = new Random(42);
        for (int n : new int[]{10, 50, 100, 500, 1000}) {
            List<Integer> orden = codigosOrdenados(n);

            ArbolBST<Integer, String> ord = construir(orden, false);
            double compOrd = promedioComparaciones(ord, orden);

            List<Integer> bal = new ArrayList<>();
            ordenBalanceado(orden, 0, n - 1, bal);
            ArbolBST<Integer, String> arbolBal = construir(bal, false);
            double compBal = promedioComparaciones(arbolBal, orden);

            double sumaAltura = 0, sumaComp = 0;
            for (int i = 0; i < CORRIDAS; i++) {
                List<Integer> mezcla = new ArrayList<>(orden);
                Collections.shuffle(mezcla, rnd);
                ArbolBST<Integer, String> aleat = construir(mezcla, false);
                sumaAltura += aleat.altura();
                sumaComp += promedioComparaciones(aleat, orden);
            }

            int minima = 31 - Integer.numberOfLeadingZeros(n);   // ⌊log2 n⌋
            System.out.println(String.format(Locale.US,
                    "%-6d | %-8d %-9.1f %-10d %-7d | %-9.1f %-10.1f %-11.1f",
                    n, ord.altura(), sumaAltura / CORRIDAS, arbolBal.altura(), minima,
                    compOrd, sumaComp / CORRIDAS, compBal));
        }
    }
}
