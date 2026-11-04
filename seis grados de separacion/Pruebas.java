import java.util.List;

public class Pruebas {
    private static int ok = 0, total = 0;

    private static void verificar(String descripcion, boolean condicion) {
        total++;
        if (condicion) ok++;
        System.out.println((condicion ? "OK     " : "FALLA  ") + descripcion);
    }

    public static void ejecutar() {
        RedSocial red = Main.construirRed();

        verificar("La red tiene 15 usuarios y 16 conexiones",
                red.numeroUsuarios() == 15 && red.numeroConexiones() == 16);
        verificar("Las amistades van en los dos sentidos",
                red.getAmigos("Borojó").contains("Chontaduro") && red.getAmigos("Chontaduro").contains("Borojó"));
        verificar("Repetir una conexión no la duplica",
                !red.conectar("Borojó", "Chontaduro") && red.numeroConexiones() == 16);
        verificar("Un usuario no puede conectarse consigo mismo",
                !red.conectar("Lulo", "Lulo") && red.numeroConexiones() == 16);

        verificar("Distancia de un usuario a sí mismo = 0",
                red.distanciaMinima("Lulo", "Lulo") == 0 && red.caminoMasCorto("Lulo", "Lulo").equals(List.of("Lulo")));
        verificar("Amigos directos están a distancia 1",
                red.distanciaMinima("Borojó", "Chontaduro") == 1);
        verificar("Lulo a Feijoa = 2 conexiones (pasando por Mora)",
                red.caminoMasCorto("Lulo", "Feijoa").equals(List.of("Lulo", "Mora", "Feijoa")));
        verificar("Borojó a Uchuva = 6 conexiones",
                red.distanciaMinima("Borojó", "Uchuva") == 6);
        verificar("Camino más corto de Borojó a Uchuva",
                red.caminoMasCorto("Borojó", "Uchuva").equals(
                        List.of("Borojó", "Mamoncillo", "Mango", "Maracuyá", "Granadilla", "Curuba", "Uchuva")));
        verificar("BFS elige el atajo: Borojó a Mango = 2 y no 3",
                red.distanciaMinima("Borojó", "Mango") == 2);

        verificar("Recorrido por niveles desde Borojó",
                red.recorridoPorNiveles("Borojó").equals(List.of(
                        List.of("Borojó"),
                        List.of("Chontaduro", "Mamoncillo"),
                        List.of("Papaya", "Mango"),
                        List.of("Maracuyá"),
                        List.of("Granadilla", "Lulo"),
                        List.of("Curuba", "Guanábana", "Mora"),
                        List.of("Tomate de árbol", "Uchuva", "Feijoa", "Guayaba"))));

        boolean simetrica = true;
        for (String a : red.getUsuarios()) {
            for (String b : red.getUsuarios()) {
                if (red.distanciaMinima(a, b) != red.distanciaMinima(b, a)) simetrica = false;
            }
        }
        verificar("La distancia es simétrica para todos los pares", simetrica);
        verificar("Seis grados: ningún par de la red está a más de 6 conexiones",
                red.diametro() == 6);

        verificar("Usuario que no existe: distancia -1 y camino vacío",
                red.distanciaMinima("Piña", "Lulo") == -1 && red.caminoMasCorto("Piña", "Lulo").isEmpty());
        verificar("Recorrido de un usuario que no existe es vacío",
                red.recorridoPorNiveles("Piña").isEmpty());

        RedSocial dividida = new RedSocial();
        dividida.conectar("Lulo", "Mora");
        dividida.agregarUsuario("Borojó");
        verificar("Usuarios sin ruta entre sí: distancia -1",
                dividida.distanciaMinima("Lulo", "Borojó") == -1);

        System.out.println();
        System.out.println("Pruebas superadas: " + ok + " de " + total);
    }
}
