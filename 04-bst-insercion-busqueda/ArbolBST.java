import java.util.ArrayList;
import java.util.List;

public class ArbolBST<K extends Comparable<K>, V> {
    private NodoBST<K, V> raiz;
    private int tamanio;
    private int ultimasComparaciones; // nodos comparados en la última búsqueda

    // ---------- Inserción recursiva ----------
    // Menores a la izquierda, mayores a la derecha.
    // Si la clave ya existe, se actualiza su valor (no se duplica).
    public void insertar(K clave, V valor) {
        raiz = insertar(raiz, clave, valor);
    }

    private NodoBST<K, V> insertar(NodoBST<K, V> nodo, K clave, V valor) {
        if (nodo == null) {                       // caso base: posición libre
            tamanio++;
            return new NodoBST<>(clave, valor);
        }
        int cmp = clave.compareTo(nodo.getClave());
        if (cmp < 0) {
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), clave, valor));
        } else if (cmp > 0) {
            nodo.setDerecho(insertar(nodo.getDerecho(), clave, valor));
        } else {
            nodo.setValor(valor);                 // clave repetida: actualiza
        }
        return nodo;
    }

    // ---------- Búsqueda recursiva ----------
    // Devuelve el valor asociado a la clave, o null si no existe.
    public V buscar(K clave) {
        ultimasComparaciones = 0;
        return buscar(raiz, clave);
    }

    private V buscar(NodoBST<K, V> nodo, K clave) {
        if (nodo == null) return null;            // caso base: no existe
        ultimasComparaciones++;
        int cmp = clave.compareTo(nodo.getClave());
        if (cmp == 0) return nodo.getValor();     // caso base: encontrado
        return cmp < 0 ? buscar(nodo.getIzquierdo(), clave)
                       : buscar(nodo.getDerecho(), clave);
    }

    public boolean contiene(K clave) { return buscar(clave) != null; }

    // ---------- Métricas ----------
    public int getTamanio() { return tamanio; }

    public int getUltimasComparaciones() { return ultimasComparaciones; }

    // Altura en aristas: hoja = 0, árbol vacío = -1
    public int altura() { return altura(raiz); }

    private int altura(NodoBST<K, V> nodo) {
        if (nodo == null) return -1;
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    // ---------- Recorrido inorden: devuelve las claves ordenadas ----------
    public List<K> inorden() {
        List<K> lista = new ArrayList<>();
        inorden(raiz, lista);
        return lista;
    }

    private void inorden(NodoBST<K, V> nodo, List<K> lista) {
        if (nodo == null) return;
        inorden(nodo.getIzquierdo(), lista);
        lista.add(nodo.getClave());
        inorden(nodo.getDerecho(), lista);
    }

    // ---------- Impresión de la estructura ----------
    public void imprimir() { imprimir(raiz, "", "raíz: "); }

    private void imprimir(NodoBST<K, V> nodo, String prefijo, String etiqueta) {
        if (nodo == null) return;
        System.out.println(prefijo + etiqueta + nodo.getClave() + " " + nodo.getValor());
        imprimir(nodo.getIzquierdo(), prefijo + "    ", "izq: ");
        imprimir(nodo.getDerecho(), prefijo + "    ", "der: ");
    }
}
