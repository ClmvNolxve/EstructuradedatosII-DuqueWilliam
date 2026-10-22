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

    private static boolean estaOrdenado(List<Integer> lista) {
        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i - 1) >= lista.get(i)) return false;
        }
        return true;
    }

    public static void ejecutar() {
        // Los casos se aplican en secuencia: el "después" de uno es el "antes" del siguiente.
        ArbolBST<Integer, String> a = Main.construirDemo();

        verificar("Árbol inicial: 13 nodos, altura 3",
                a.getTamanio() == 13 && a.altura() == 3);
        verificar("Árbol inicial: estructura (preorden) esperada",
                a.preorden().equals(List.of(1008, 1004, 1002, 1001, 1006, 1005, 1007,
                                            1012, 1010, 1009, 1011, 1014, 1015)));

        // ----- Caso 1: hoja -----
        verificar("Caso 1 (hoja 1009): eliminar devuelve true y ya no se encuentra",
                a.eliminar(1009) && a.buscar(1009) == null);
        verificar("Caso 1: quedan 12 nodos",
                a.getTamanio() == 12);
        verificar("Caso 1: estructura esperada",
                a.preorden().equals(List.of(1008, 1004, 1002, 1001, 1006, 1005, 1007,
                                            1012, 1010, 1011, 1014, 1015)));

        // ----- Caso 2: un hijo (derecho) -----
        verificar("Caso 2 (1014, hijo derecho): eliminar devuelve true y ya no se encuentra",
                a.eliminar(1014) && a.buscar(1014) == null);
        verificar("Caso 2: quedan 11 nodos y el hijo 1015 sigue en el árbol",
                a.getTamanio() == 11 && "Uchuva".equals(a.buscar(1015)));
        verificar("Caso 2: estructura esperada (1015 ocupa el lugar de 1014)",
                a.preorden().equals(List.of(1008, 1004, 1002, 1001, 1006, 1005, 1007,
                                            1012, 1010, 1011, 1015)));

        // ----- Caso 2: un hijo (izquierdo) -----
        verificar("Caso 2 (1002, hijo izquierdo): eliminar devuelve true y ya no se encuentra",
                a.eliminar(1002) && a.buscar(1002) == null);
        verificar("Caso 2: quedan 10 nodos y el hijo 1001 sigue en el árbol",
                a.getTamanio() == 10 && "Aguacate".equals(a.buscar(1001)));
        verificar("Caso 2: estructura esperada (1001 ocupa el lugar de 1002)",
                a.preorden().equals(List.of(1008, 1004, 1001, 1006, 1005, 1007,
                                            1012, 1010, 1011, 1015)));

        // ----- Caso 3: dos hijos (sucesor es hoja) -----
        verificar("Caso 3 (1004, dos hijos): eliminar devuelve true y ya no se encuentra",
                a.eliminar(1004) && a.buscar(1004) == null);
        verificar("Caso 3: quedan 9 nodos",
                a.getTamanio() == 9);
        verificar("Caso 3: estructura esperada (1005, el sucesor, ocupa el lugar de 1004)",
                a.preorden().equals(List.of(1008, 1005, 1001, 1006, 1007,
                                            1012, 1010, 1011, 1015)));

        // ----- Caso 3: raíz con dos hijos (sucesor con un hijo) -----
        verificar("Caso 3 (raíz 1008, dos hijos): eliminar devuelve true y ya no se encuentra",
                a.eliminar(1008) && a.buscar(1008) == null);
        verificar("Caso 3 (raíz): quedan 8 nodos",
                a.getTamanio() == 8);
        verificar("Caso 3 (raíz): estructura esperada (1010 es la nueva raíz, 1011 ocupa su sitio)",
                a.preorden().equals(List.of(1010, 1005, 1001, 1006, 1007,
                                            1012, 1011, 1015)));
        verificar("Tras las 5 eliminaciones, el inorden sigue ordenado",
                estaOrdenado(a.inorden()));

        // ----- Casos límite -----
        verificar("Eliminar un código inexistente (9999) devuelve false y no cambia el tamaño",
                !a.eliminar(9999) && a.getTamanio() == 8);
        verificar("Eliminar dos veces el mismo código: la segunda devuelve false",
                !a.eliminar(1009));
        ArbolBST<Integer, String> vacio = new ArbolBST<>();
        verificar("Eliminar en un árbol vacío devuelve false",
                !vacio.eliminar(1));
        for (int clave : new ArrayList<>(a.inorden())) a.eliminar(clave);
        verificar("Eliminar todos los nodos deja el árbol vacío (tamaño 0, altura -1)",
                a.getTamanio() == 0 && a.altura() == -1);

        // ----- Prueba con muchos datos -----
        List<Integer> todos = new ArrayList<>();
        for (int i = 1; i <= 200; i++) todos.add(i);
        List<Integer> orden = new ArrayList<>(todos);
        Collections.shuffle(orden, new Random(5));
        ArbolBST<Integer, String> grande = new ArbolBST<>();
        for (int c : orden) grande.insertar(c, "F" + c);

        List<Integer> aEliminar = new ArrayList<>(todos);
        Collections.shuffle(aEliminar, new Random(6));
        aEliminar = new ArrayList<>(aEliminar.subList(0, 100));
        boolean todasEliminadas = true;
        for (int c : aEliminar) todasEliminadas &= grande.eliminar(c);
        List<Integer> restantes = new ArrayList<>(todos);
        restantes.removeAll(aEliminar);

        verificar("200 datos aleatorios, 100 eliminaciones aleatorias: todas devuelven true",
                todasEliminadas);
        verificar("Quedan 100 nodos y el inorden coincide con los códigos restantes",
                grande.getTamanio() == 100 && grande.inorden().equals(restantes));
        boolean ningunoEliminado = true, todosRestantes = true;
        for (int c : aEliminar) ningunoEliminado &= grande.buscar(c) == null;
        for (int c : restantes) todosRestantes &= grande.buscar(c) != null;
        verificar("Ningún código eliminado se encuentra y todos los restantes sí",
                ningunoEliminado && todosRestantes);

        System.out.println();
        System.out.println("Pruebas superadas: " + ok + " de " + total);
    }
}
