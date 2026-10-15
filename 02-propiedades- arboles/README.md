# Guía 2. Anatomía y propiedades de un árbol

**Asignatura:** Estructura de Datos II – Ingeniería de Software, IV semestre

## Contenido
- `NodoGeneral.java`: nodo genérico con dato, lista de hijos, `esHoja()` y `grado()`.
- `Main.java`: construye el árbol, implementa `altura()` recursiva, calcula las métricas y ejecuta las pruebas.
- `arbol_propiedades.drawio`: diagrama rotulado (raíz, nodos internos, hojas, grado, altura y niveles).
- `salida.txt`: salida de la ejecución del programa.

## Ejecución
```
javac -encoding UTF-8 *.java
java Main
```

## Convenciones
- **Profundidad** de un nodo: número de aristas desde la raíz hasta él (la raíz tiene profundidad 0).
- **Nivel** = profundidad + 1 (la raíz está en el nivel 1).
- **Altura** de un nodo: número de aristas del camino más largo hasta una hoja (una hoja tiene altura 0).
- **Grado** de un nodo: número de hijos. **Grado del árbol:** el mayor grado entre todos sus nodos.
- **Longitud de un camino:** número de aristas.

## El árbol (13 nodos, 4 niveles)

```
Universidad
├── Facultad de Ingeniería
│   ├── Sistemas
│   │   ├── Estructura de Datos II
│   │   └── Bases de Datos
│   └── Industrial
│       └── Logística
├── Facultad de Ciencias
│   ├── Matemáticas
│   │   └── Cálculo
│   └── Física
└── Biblioteca
    └── Hemeroteca
```

## Tabla de métricas

| Nodo | Tipo | Padre | Grado | Profundidad | Nivel | Altura |
|---|---|---|---|---|---|---|
| Universidad | Raíz | — | 3 | 0 | 1 | 3 |
| Facultad de Ingeniería | Interno | Universidad | 2 | 1 | 2 | 2 |
| Sistemas | Interno | Facultad de Ingeniería | 2 | 2 | 3 | 1 |
| Estructura de Datos II | Hoja | Sistemas | 0 | 3 | 4 | 0 |
| Bases de Datos | Hoja | Sistemas | 0 | 3 | 4 | 0 |
| Industrial | Interno | Facultad de Ingeniería | 1 | 2 | 3 | 1 |
| Logística | Hoja | Industrial | 0 | 3 | 4 | 0 |
| Facultad de Ciencias | Interno | Universidad | 2 | 1 | 2 | 2 |
| Matemáticas | Interno | Facultad de Ciencias | 1 | 2 | 3 | 1 |
| Cálculo | Hoja | Matemáticas | 0 | 3 | 4 | 0 |
| Física | Hoja | Facultad de Ciencias | 0 | 2 | 3 | 0 |
| Biblioteca | Interno | Universidad | 1 | 1 | 2 | 1 |
| Hemeroteca | Hoja | Biblioteca | 0 | 2 | 3 | 0 |

**Resumen:** 13 nodos · 1 raíz · 6 hojas · 7 nodos internos (incluida la raíz) · **grado del árbol = 3** · **altura del árbol = 3**.

**Verificación:** la suma de los grados es 3+2+2+1+2+1+1 = 12 = n − 1, como debe cumplirse en todo árbol.

## Relaciones de parentesco (ejemplos)

| Relación | Ejemplo |
|---|---|
| Padre de "Sistemas" | Facultad de Ingeniería |
| Hijos de "Sistemas" | Estructura de Datos II, Bases de Datos |
| Hermanos | Sistemas e Industrial; Estructura de Datos II y Bases de Datos |
| Ancestros de "Cálculo" | Matemáticas, Facultad de Ciencias, Universidad |
| Descendientes de "Facultad de Ingeniería" | Sistemas, Industrial, Estructura de Datos II, Bases de Datos, Logística |

## Profundidad de cinco nodos

| Nodo | Profundidad |
|---|---|
| Universidad | 0 |
| Facultad de Ingeniería | 1 |
| Sistemas | 2 |
| Física | 2 |
| Estructura de Datos II | 3 |

## Algoritmo de altura

```java
static int altura(NodoGeneral<?> nodo) {
    if (nodo.getHijos().isEmpty()) return 0;      // caso base: hoja
    int max = 0;
    for (NodoGeneral<?> hijo : nodo.getHijos()) {
        max = Math.max(max, altura(hijo));        // altura del hijo más alto
    }
    return 1 + max;                               // se suma la arista hacia ese hijo
}
```

**Cómo funciona:** una hoja no tiene camino hacia abajo, así que su altura es 0. Para un nodo interno, la altura es 1 más la mayor altura entre sus hijos, porque el camino más largo pasa por el hijo más profundo. El método visita cada nodo una vez, por lo que su costo es O(n).

**Traza para "Sistemas":** altura(Estructura de Datos II) = 0 y altura(Bases de Datos) = 0, entonces altura(Sistemas) = 1 + max(0, 0) = 1.
**Traza para "Universidad":** altura(Ingeniería) = 2, altura(Ciencias) = 2, altura(Biblioteca) = 1, entonces altura(Universidad) = 1 + max(2, 2, 1) = 3.

## Ejercicios

**1. Profundidad de cada hoja**

| Hoja | Profundidad |
|---|---|
| Estructura de Datos II | 3 |
| Bases de Datos | 3 |
| Logística | 3 |
| Cálculo | 3 |
| Física | 2 |
| Hemeroteca | 2 |

**2. Dos caminos distintos**
- Camino A: Universidad → Facultad de Ingeniería → Sistemas → Estructura de Datos II. Tiene 4 nodos y **longitud 3**.
- Camino B: Universidad → Biblioteca → Hemeroteca. Tiene 3 nodos y **longitud 2**.

**3. Agregar un nuevo nivel.** Se agregó "Derivadas" como hijo de "Cálculo". La altura del árbol pasó de 3 a **4** y ahora hay 5 niveles y 14 nodos.
- Cálculo deja de ser hoja (altura 1), Matemáticas pasa a altura 2, Facultad de Ciencias a 3 y Universidad a 4.
- Las ramas que no contienen a Cálculo no cambian: Facultad de Ingeniería sigue en altura 2 y Biblioteca en 1.
- El grado del árbol sigue siendo 3, porque ningún nodo ganó más de un hijo.

La altura de un árbol solo aumenta cuando se agrega un nodo debajo de una hoja que está en el camino más largo.

## Pruebas (cálculo manual vs. programa)

| Caso | Esperado | Obtenido |
|---|---|---|
| Altura de una hoja (Física) | 0 | 0 |
| Altura de Sistemas | 1 | 1 |
| Altura de Facultad de Ingeniería | 2 | 2 |
| Altura de Biblioteca | 1 | 1 |
| Altura del árbol | 3 | 3 |
| Altura tras agregar "Derivadas" | 4 | 4 |

La salida completa del programa está en `salida.txt`.
