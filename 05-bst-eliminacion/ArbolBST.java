import java.util.ArrayList;
import java.util.List;

public class ArbolBST<K extends Comparable<K>, V> {
    private NodoBST<K, V> raiz;
    private int tamanio;
    private int ultimasComparaciones; // nodos comparados en la última búsqueda

    // ---------- Inserción recursiva ----------
    public void insertar(K clave, V valor) {
        raiz = insertar(raiz, clave, valor);
    }

    private NodoBST<K, V> insertar(NodoBST<K, V> nodo, K clave, V valor) {
        if (nodo == null) {
            tamanio++;
            return new NodoBST<>(clave, valor);
        }
        int cmp = clave.compareTo(nodo.getClave());
        if (cmp < 0) {
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), clave, valor));
        } else if (cmp > 0) {
            nodo.setDerecho(insertar(nodo.getDerecho(), clave, valor));
        } else {
            nodo.setValor(valor);
        }
        return nodo;
    }

    // ---------- Búsqueda recursiva ----------
    public V buscar(K clave) {
        ultimasComparaciones = 0;
        return buscar(raiz, clave);
    }

    private V buscar(NodoBST<K, V> nodo, K clave) {
        if (nodo == null) return null;
        ultimasComparaciones++;
        int cmp = clave.compareTo(nodo.getClave());
        if (cmp == 0) return nodo.getValor();
        return cmp < 0 ? buscar(nodo.getIzquierdo(), clave)
                       : buscar(nodo.getDerecho(), clave);
    }

    public boolean contiene(K clave) { return buscar(clave) != null; }

    // ---------- Eliminación recursiva (los tres casos) ----------
    // Devuelve true si la clave existía y fue eliminada.
    public boolean eliminar(K clave) {
        int antes = tamanio;
        raiz = eliminar(raiz, clave);
        return tamanio < antes;
    }

    // Devuelve la nueva raíz del subárbol una vez eliminada la clave.
    private NodoBST<K, V> eliminar(NodoBST<K, V> nodo, K clave) {
        if (nodo == null) return null;                 // la clave no existe

        int cmp = clave.compareTo(nodo.getClave());
        if (cmp < 0) {
            nodo.setIzquierdo(eliminar(nodo.getIzquierdo(), clave));
        } else if (cmp > 0) {
            nodo.setDerecho(eliminar(nodo.getDerecho(), clave));
        } else {
            // Se encontró el nodo a eliminar
            // CASO 1: nodo hoja -> el padre deja de apuntarle
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                tamanio--;
                return null;
            }
            // CASO 2: un solo hijo -> el hijo ocupa el lugar del nodo
            if (nodo.getIzquierdo() == null) {
                tamanio--;
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                tamanio--;
                return nodo.getIzquierdo();
            }
            // CASO 3: dos hijos -> se reemplaza por el sucesor inorden
            // (el menor del subárbol derecho) y se elimina el sucesor de su sitio.
            NodoBST<K, V> sucesor = minimo(nodo.getDerecho());
            nodo.setClave(sucesor.getClave());
            nodo.setValor(sucesor.getValor());
            nodo.setDerecho(eliminar(nodo.getDerecho(), sucesor.getClave()));
        }
        return nodo;
    }

    // El menor de un subárbol es su nodo más a la izquierda
    private NodoBST<K, V> minimo(NodoBST<K, V> nodo) {
        if (nodo.getIzquierdo() == null) return nodo;
        return minimo(nodo.getIzquierdo());
    }

    // ---------- Métricas ----------
    public int getTamanio() { return tamanio; }

    public int getUltimasComparaciones() { return ultimasComparaciones; }

    // Altura en aristas: hoja = 0, árbol vacío = -1
    public int altura() { return altura(raiz); }

    private int altura(NodoBST<K, V> nodo) {
        if (nodo == null) return -1;
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    // ---------- Recorridos ----------
    // Inorden: devuelve las claves ordenadas
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

    // Preorden: junto con el inorden identifica de forma única la estructura del árbol
    public List<K> preorden() {
        List<K> lista = new ArrayList<>();
        preorden(raiz, lista);
        return lista;
    }

    private void preorden(NodoBST<K, V> nodo, List<K> lista) {
        if (nodo == null) return;
        lista.add(nodo.getClave());
        preorden(nodo.getIzquierdo(), lista);
        preorden(nodo.getDerecho(), lista);
    }

    // ---------- Impresión de la estructura ----------
    public void imprimir() {
        if (raiz == null) {
            System.out.println("(árbol vacío)");
            return;
        }
        imprimir(raiz, "", "raíz: ");
    }

    private void imprimir(NodoBST<K, V> nodo, String prefijo, String etiqueta) {
        if (nodo == null) return;
        System.out.println(prefijo + etiqueta + nodo.getClave() + " " + nodo.getValor());
        imprimir(nodo.getIzquierdo(), prefijo + "    ", "izq: ");
        imprimir(nodo.getDerecho(), prefijo + "    ", "der: ");
    }
}
