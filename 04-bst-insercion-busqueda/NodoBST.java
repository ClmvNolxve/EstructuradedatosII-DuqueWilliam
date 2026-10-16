public class NodoBST<K extends Comparable<K>, V> {
    private K clave;                 // código del producto (criterio de orden)
    private V valor;                 // dato asociado (nombre de la fruta)
    private NodoBST<K, V> izquierdo; // claves menores
    private NodoBST<K, V> derecho;   // claves mayores

    public NodoBST(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() { return clave; }
    public V getValor() { return valor; }
    public void setValor(V valor) { this.valor = valor; }

    public NodoBST<K, V> getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoBST<K, V> izquierdo) { this.izquierdo = izquierdo; }

    public NodoBST<K, V> getDerecho() { return derecho; }
    public void setDerecho(NodoBST<K, V> derecho) { this.derecho = derecho; }
}
