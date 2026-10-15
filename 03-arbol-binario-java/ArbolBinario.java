// Utilidades para medir y clasificar árboles binarios a partir de su raíz.
public class ArbolBinario {

    // Cuenta todos los nodos del árbol
    public static int contarNodos(NodoArbol<?> nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    // Altura en aristas: una hoja tiene altura 0 y un árbol vacío -1
    public static int altura(NodoArbol<?> nodo) {
        if (nodo == null) return -1;
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    // LLENO: cada nodo tiene 0 o 2 hijos
    public static boolean esLleno(NodoArbol<?> nodo) {
        if (nodo == null || nodo.esHoja()) return true;
        if (nodo.cantidadHijos() == 1) return false;
        return esLleno(nodo.getIzquierdo()) && esLleno(nodo.getDerecho());
    }

    // COMPLETO: todos los niveles llenos, salvo quizá el último,
    // que se llena de izquierda a derecha.
    // Se numeran los nodos como en un arreglo (raíz = 0, hijos = 2i+1 y 2i+2);
    // el árbol es completo si ningún índice supera el total de nodos.
    public static boolean esCompleto(NodoArbol<?> raiz) {
        return esCompleto(raiz, 0, contarNodos(raiz));
    }

    private static boolean esCompleto(NodoArbol<?> nodo, int indice, int total) {
        if (nodo == null) return true;
        if (indice >= total) return false;
        return esCompleto(nodo.getIzquierdo(), 2 * indice + 1, total)
            && esCompleto(nodo.getDerecho(), 2 * indice + 2, total);
    }

    // PERFECTO: todos los internos tienen 2 hijos y todas las hojas están al mismo nivel.
    // Equivale a tener exactamente 2^(h+1) - 1 nodos.
    public static boolean esPerfecto(NodoArbol<?> raiz) {
        int h = altura(raiz);
        return contarNodos(raiz) == (1 << (h + 1)) - 1;
    }

    // DEGENERADO: ningún nodo tiene dos hijos (cada nodo tiene a lo sumo uno), como una lista
    public static boolean esDegenerado(NodoArbol<?> nodo) {
        if (nodo == null || nodo.esHoja()) return true;
        if (nodo.cantidadHijos() == 2) return false;
        return esDegenerado(nodo.tieneIzquierdo() ? nodo.getIzquierdo() : nodo.getDerecho());
    }

    // Muestra la estructura indicando si cada nodo es hijo izquierdo o derecho
    public static void imprimir(NodoArbol<?> nodo, String prefijo, String etiqueta) {
        if (nodo == null) return;
        System.out.println(prefijo + etiqueta + nodo.getDato());
        imprimir(nodo.getIzquierdo(), prefijo + "    ", "izq: ");
        imprimir(nodo.getDerecho(), prefijo + "    ", "der: ");
    }

    public static String sn(boolean valor) { return valor ? "Sí" : "No"; }
}
