public class Main {

    static final String[] FRUTAS = {
        "Aguacate", "Banano", "Cereza", "Durazno", "Feijoa", "Fresa", "Granadilla", "Guayaba",
        "Lulo", "Mango", "Maracuyá", "Mora", "Naranja", "Papaya", "Uchuva"
    };
    static final int BASE = 1001;   // código de Aguacate; Uchuva es 1015

    // Árbol de partida: 13 nodos, altura 3, con nodos hoja, nodos con un hijo
    // (1002 y 1014) y nodos con dos hijos.
    static ArbolBST<Integer, String> construirDemo() {
        int[] orden = {1008, 1004, 1012, 1002, 1006, 1010, 1014, 1001, 1005, 1007, 1009, 1011, 1015};
        ArbolBST<Integer, String> arbol = new ArbolBST<>();
        for (int codigo : orden) arbol.insertar(codigo, FRUTAS[codigo - BASE]);
        return arbol;
    }

    static void eliminarYMostrar(ArbolBST<Integer, String> arbol, int codigo, String caso, String explicacion) {
        System.out.println("--------------------------------------------------");
        System.out.println(caso + ": eliminar " + codigo + " " + FRUTAS[codigo - BASE]);
        System.out.println(explicacion);
        System.out.println();
        System.out.println("ANTES (" + arbol.getTamanio() + " nodos, altura " + arbol.altura() + "):");
        arbol.imprimir();
        boolean eliminado = arbol.eliminar(codigo);
        System.out.println();
        System.out.println("DESPUÉS (" + arbol.getTamanio() + " nodos, altura " + arbol.altura()
                + ", eliminado = " + eliminado + "):");
        arbol.imprimir();
        System.out.println("Inorden: " + arbol.inorden());
        System.out.println();
    }

    public static void main(String[] args) {
        ArbolBST<Integer, String> arbol = construirDemo();

        System.out.println("=== 1. ELIMINACIÓN EN UN BST DE FRUTAS ===");
        System.out.println("Cada eliminación se aplica sobre el resultado de la anterior.");
        System.out.println();

        eliminarYMostrar(arbol, 1009, "CASO 1 - NODO HOJA",
                "Lulo no tiene hijos: su padre (Mango) simplemente deja de apuntarle.");

        eliminarYMostrar(arbol, 1014, "CASO 2 - UN HIJO (DERECHO)",
                "Papaya solo tiene hijo derecho (Uchuva): Uchuva ocupa el lugar de Papaya bajo Mora.");

        eliminarYMostrar(arbol, 1002, "CASO 2 - UN HIJO (IZQUIERDO)",
                "Banano solo tiene hijo izquierdo (Aguacate): Aguacate ocupa el lugar de Banano bajo Durazno.");

        eliminarYMostrar(arbol, 1004, "CASO 3 - DOS HIJOS",
                "Durazno tiene dos hijos: se reemplaza por su sucesor inorden, el menor de su subárbol\n"
              + "derecho (Feijoa), y luego se elimina Feijoa de su posición original (era una hoja).");

        eliminarYMostrar(arbol, 1008, "CASO 3 - DOS HIJOS (LA RAÍZ)",
                "Guayaba tiene dos hijos: su sucesor es Mango (el menor del subárbol derecho).\n"
              + "Mango sube a la raíz y se elimina de su sitio original; como tenía un hijo (Maracuyá),\n"
              + "esa eliminación se resuelve con el caso de un hijo.");

        System.out.println("--------------------------------------------------");
        System.out.println("CASO LÍMITE: eliminar un código que no existe (9999)");
        System.out.println("eliminar(9999) = " + arbol.eliminar(9999) + " (el árbol no cambia: "
                + arbol.getTamanio() + " nodos)");

        System.out.println();
        System.out.println("=== 2. PRUEBAS ===");
        Pruebas.ejecutar();
    }
}
