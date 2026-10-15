public class Main {
    public static void main(String[] args) {
        // Nivel 0: raíz
        NodoGeneral<String> raiz = new NodoGeneral<>("Empresa");

        // Nivel 1
        NodoGeneral<String> tecnologia = new NodoGeneral<>("Tecnología");
        NodoGeneral<String> finanzas = new NodoGeneral<>("Finanzas");
        NodoGeneral<String> rrhh = new NodoGeneral<>("RRHH");
        raiz.agregarHijo(tecnologia);
        raiz.agregarHijo(finanzas);
        raiz.agregarHijo(rrhh);

        // Nivel 2
        NodoGeneral<String> desarrollo = new NodoGeneral<>("Desarrollo");
        NodoGeneral<String> infra = new NodoGeneral<>("Infraestructura");
        tecnologia.agregarHijo(desarrollo);
        tecnologia.agregarHijo(infra);
        finanzas.agregarHijo(new NodoGeneral<>("Contabilidad"));
        finanzas.agregarHijo(new NodoGeneral<>("Tesorería"));

        // Nivel 3
        desarrollo.agregarHijo(new NodoGeneral<>("Backend"));
        desarrollo.agregarHijo(new NodoGeneral<>("Frontend"));

        System.out.println("Raíz: " + raiz.getDato());
        imprimir(raiz, 0);
    }

    // Recorrido recursivo: imprime el nodo y luego cada hijo con más sangría
    private static <T> void imprimir(NodoGeneral<T> nodo, int nivel) {
        System.out.println("  ".repeat(nivel) + "- " + nodo.getDato());
        for (NodoGeneral<T> hijo : nodo.getHijos()) {
            imprimir(hijo, nivel + 1);
        }
    }
}