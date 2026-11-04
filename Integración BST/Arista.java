// Una arista entre dos vértices, con peso y dirección.
public class Arista {
    private final String origen;
    private final String destino;
    private final double peso;        // 1.0 si la arista no es ponderada
    private final boolean dirigida;   // true si solo va del origen al destino

    public Arista(String o, String d, double p, boolean dir) {
        origen = o;
        destino = d;
        peso = p;
        dirigida = dir;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public double getPeso() { return peso; }
    public boolean isDirigida() { return dirigida; }

    // Una arista es ponderada cuando su peso es distinto de 1
    public boolean isPonderada() { return peso != 1.0; }

    @Override
    public String toString() {
        return origen + (dirigida ? " -> " : " -- ") + destino + " (" + peso + ")";
    }
}
