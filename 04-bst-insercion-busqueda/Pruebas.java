import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Pruebas {
    private static int ok = 0, total = 0;

    private static void verificar(String descripcion, boolean condicion) {
        total++;
        if (condicion) ok++;
        System.out.println((condicion ? "OK     " : "FALLA  ") + descripcion);
    }

    public static void ejecutar() {
        // Árbol pequeño con códigos de frutas
        ArbolBST<Integer, String> a = new ArbolBST<>();
        a.insertar(1010, "Mango");
        a.insertar(1005, "Feijoa");
        a.insertar(1015, "Uchuva");
        a.insertar(1003, "Cereza");
        a.insertar(1008, "Guayaba");

        verificar("Árbol vacío: altura -1 y búsqueda devuelve null",
                new ArbolBST<Integer, String>().altura() == -1
                        && new ArbolBST<Integer, String>().buscar(1) == null);
        verificar("Tamaño tras 5 inserciones = 5", a.getTamanio() == 5);
        verificar("Inorden devuelve los códigos ordenados",
                a.inorden().equals(List.of(1003, 1005, 1008, 1010, 1015)));
        verificar("Buscar 1010 devuelve Mango", "Mango".equals(a.buscar(1010)));
        verificar("Buscar 1003 devuelve Cereza", "Cereza".equals(a.buscar(1003)));
        verificar("Buscar código inexistente (9999) devuelve null", a.buscar(9999) == null);
        verificar("Buscar la raíz cuesta 1 comparación", a.buscar(1010) != null && a.getUltimasComparaciones() == 1);

        a.insertar(1010, "Mango Tommy");
        verificar("Clave repetida actualiza el valor", "Mango Tommy".equals(a.buscar(1010)));
        verificar("Clave repetida no aumenta el tamaño", a.getTamanio() == 5);

        // Mismos datos, distinto orden de inserción => mismo contenido, distinta altura
        List<Integer> ordenados = new ArrayList<>();
        for (int i = 1; i <= 15; i++) ordenados.add(i);
        List<Integer> aleatorios = new ArrayList<>(ordenados);
        Collections.shuffle(aleatorios, new Random(1));

        ArbolBST<Integer, String> ordenado = new ArbolBST<>();
        for (int c : ordenados) ordenado.insertar(c, "F" + c);
        ArbolBST<Integer, String> aleatorio = new ArbolBST<>();
        for (int c : aleatorios) aleatorio.insertar(c, "F" + c);

        verificar("Datos ordenados (15): altura = 14 (n - 1)", ordenado.altura() == 14);
        verificar("Datos aleatorios (15): altura menor que la de los ordenados", aleatorio.altura() < ordenado.altura());
        verificar("Ambos árboles tienen el mismo contenido (inorden igual)",
                ordenado.inorden().equals(aleatorio.inorden()));

        System.out.println();
        System.out.println("Pruebas superadas: " + ok + " de " + total);
    }
}
