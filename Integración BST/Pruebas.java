import java.util.List;

public class Pruebas {
    private static int ok = 0, total = 0;

    private static void verificar(String descripcion, boolean condicion) {
        total++;
        if (condicion) ok++;
        System.out.println((condicion ? "OK     " : "FALLA  ") + descripcion);
    }

    public static void ejecutar() {
        Grafo g = Main.construirRed();

        verificar("La red tiene 12 vértices y 18 aristas, más que el mínimo de 10 y 15",
                g.numeroVertices() == 12 && g.numeroAristas() == 18);
        verificar("No se agrega una arista repetida",
                !g.agregarArista("Lulo", "Maracuyá", 3.0, true) && g.numeroAristas() == 18);
        verificar("No se aceptan lazos ni vértices que no existen",
                !g.agregarArista("Lulo", "Lulo", 1.0, true) && !g.agregarArista("Lulo", "Piña", 1.0, true));
        verificar("Borojó y Chontaduro se siguen en los dos sentidos con dos aristas distintas",
                g.existeArista("Borojó", "Chontaduro") && g.existeArista("Chontaduro", "Borojó"));

        verificar("Grados de Mora, entrada 2 y salida 2",
                g.gradoEntrada("Mora") == 2 && g.gradoSalida("Mora") == 2);
        verificar("Grados de Mango, entrada 3 y salida 1",
                g.gradoEntrada("Mango") == 3 && g.gradoSalida("Mango") == 1);
        int sumaEntrada = 0, sumaSalida = 0;
        for (Vertice v : g.getVertices()) {
            sumaEntrada += g.gradoEntrada(v.getNombre());
            sumaSalida += g.gradoSalida(v.getNombre());
        }
        verificar("La suma de grados de entrada y la de salida son iguales al número de aristas",
                sumaEntrada == 18 && sumaSalida == 18);
        verificar("El peso total es 85", g.pesoTotal() == 85.0);

        verificar("El grafo es dirigido y ponderado", g.esDirigido() && g.esPonderado());
        List<String> ciclo = g.buscarCiclo();
        boolean cicloValido = ciclo.size() > 1 && ciclo.get(0).equals(ciclo.get(ciclo.size() - 1));
        for (int i = 0; i + 1 < ciclo.size(); i++) {
            cicloValido &= g.existeArista(ciclo.get(i), ciclo.get(i + 1));
        }
        verificar("El ciclo encontrado es válido y cada paso es una arista real", cicloValido);
        verificar("El ciclo es Mango, Lulo, Maracuyá, Mango",
                ciclo.equals(List.of("Mango", "Lulo", "Maracuyá", "Mango")));
        verificar("Es débilmente conexo pero no fuertemente conexo",
                g.esDebilmenteConexo() && !g.esFuertementeConexo());
        verificar("La densidad es 18 sobre 132 y el grafo es disperso",
                Math.abs(g.densidad() - 18.0 / 132.0) < 1e-9 && !g.esDenso());

        Grafo noDirigido = g.sinDireccion();
        verificar("Sin dirección quedan 12 vértices y 17 aristas porque Borojó y Chontaduro se funden",
                noDirigido.numeroVertices() == 12 && noDirigido.numeroAristas() == 17 && noDirigido.esNoDirigido());
        boolean pesoFundido = false;
        for (Arista a : noDirigido.getAristas()) {
            if (a.getOrigen().equals("Borojó") && a.getDestino().equals("Chontaduro")) pesoFundido = a.getPeso() == 8.0;
        }
        verificar("La arista fundida suma los pesos 3 y 5", pesoFundido);
        Grafo redirigido = noDirigido.conDireccion();
        verificar("Al volver a dirigir cada arista da dos, en total 34",
                redirigido.numeroAristas() == 34 && redirigido.esDirigido());
        verificar("La versión dirigida tiene seguimientos que el original no tenía",
                redirigido.existeArista("Maracuyá", "Lulo") && !g.existeArista("Maracuyá", "Lulo"));

        Grafo acciclico = new Grafo();
        for (String f : new String[]{"A", "B", "C"}) acciclico.agregarVertice(f);
        acciclico.agregarArista("A", "B", 1.0, true);
        acciclico.agregarArista("B", "C", 1.0, true);
        verificar("Un grafo dirigido sin ciclos se reconoce como acíclico",
                !acciclico.esCiclico() && !acciclico.esPonderado());

        Grafo arbol = new Grafo();
        for (String f : new String[]{"A", "B", "C"}) arbol.agregarVertice(f);
        arbol.agregarArista("A", "B", 1.0, false);
        arbol.agregarArista("B", "C", 1.0, false);
        verificar("Un grafo no dirigido sin ciclos no cuenta como cíclico la arista de ida y vuelta",
                !arbol.esCiclico());
        arbol.agregarArista("C", "A", 1.0, false);
        verificar("Un triángulo no dirigido sí es cíclico", arbol.esCiclico());

        Grafo separado = new Grafo();
        separado.agregarVertice("A");
        separado.agregarVertice("B");
        verificar("Dos vértices sin aristas no forman un grafo conexo", !separado.esDebilmenteConexo());

        System.out.println();
        System.out.println("Pruebas superadas: " + ok + " de " + total);
    }
}
