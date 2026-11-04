import java.util.List;

public class Main {

    // Red social simulada. Los usuarios son frutas típicas de Colombia
    // y cada conexión es una amistad entre dos frutas.
    static RedSocial construirRed() {
        RedSocial red = new RedSocial();
        String[] usuarios = {
            "Borojó", "Chontaduro", "Curuba", "Feijoa", "Granadilla", "Guanábana", "Guayaba", "Lulo",
            "Mamoncillo", "Mango", "Maracuyá", "Mora", "Papaya", "Tomate de árbol", "Uchuva"
        };
        for (String u : usuarios) red.agregarUsuario(u);

        String[][] amistades = {
            {"Borojó", "Chontaduro"}, {"Borojó", "Mamoncillo"}, {"Chontaduro", "Papaya"},
            {"Mamoncillo", "Mango"}, {"Papaya", "Mango"}, {"Mango", "Maracuyá"},
            {"Maracuyá", "Lulo"}, {"Maracuyá", "Granadilla"}, {"Lulo", "Mora"},
            {"Granadilla", "Curuba"}, {"Granadilla", "Guanábana"}, {"Mora", "Guayaba"},
            {"Mora", "Feijoa"}, {"Guayaba", "Feijoa"}, {"Curuba", "Tomate de árbol"},
            {"Curuba", "Uchuva"}
        };
        for (String[] par : amistades) red.conectar(par[0], par[1]);
        return red;
    }

    static void mostrarDistancia(RedSocial red, String origen, String destino) {
        List<String> camino = red.caminoMasCorto(origen, destino);
        System.out.println(origen + " a " + destino);
        if (camino.isEmpty()) {
            System.out.println("    Sin ruta o el usuario no existe");
        } else {
            System.out.println("    " + String.join(" -> ", camino));
            System.out.println("    Conexiones mínimas " + red.distanciaMinima(origen, destino));
        }
    }

    public static void main(String[] args) {
        RedSocial red = construirRed();

        // ================= 1. La red =================
        System.out.println("=== 1. RED SOCIAL DE FRUTAS TÍPICAS DE COLOMBIA ===");
        System.out.println(red.numeroUsuarios() + " usuarios y " + red.numeroConexiones() + " conexiones");
        System.out.println();
        for (String usuario : red.getUsuarios()) {
            System.out.println(usuario + " -> " + String.join(", ", red.getAmigos(usuario)));
        }

        // ================= 2. BFS por niveles =================
        System.out.println();
        System.out.println("=== 2. RECORRIDO POR NIVELES DESDE BOROJÓ (BFS) ===");
        List<List<String>> niveles = red.recorridoPorNiveles("Borojó");
        for (int i = 0; i < niveles.size(); i++) {
            System.out.println("Nivel " + i + "  " + String.join(", ", niveles.get(i)));
        }

        // ================= 3. Distancia mínima =================
        System.out.println();
        System.out.println("=== 3. NÚMERO MÍNIMO DE CONEXIONES ===");
        mostrarDistancia(red, "Borojó", "Uchuva");
        mostrarDistancia(red, "Chontaduro", "Guayaba");
        mostrarDistancia(red, "Lulo", "Feijoa");
        mostrarDistancia(red, "Mango", "Mango");
        mostrarDistancia(red, "Borojó", "Piña");

        // ================= 4. Seis grados =================
        System.out.println();
        System.out.println("=== 4. SEIS GRADOS DE SEPARACIÓN ===");
        System.out.printf("%-18s %s%n", "Usuario", "Distancia al más lejano");
        for (String usuario : red.getUsuarios()) {
            System.out.printf("%-18s %d%n", usuario, red.excentricidad(usuario));
        }
        System.out.println();
        System.out.println("Distancia máxima entre cualquier par de usuarios: " + red.diametro());

        // ================= 5. Pruebas =================
        System.out.println();
        System.out.println("=== 5. PRUEBAS ===");
        Pruebas.ejecutar();
    }
}
