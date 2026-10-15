# Guía 3. Clasificación de árboles binarios – Laboratorio "Mi primer árbol binario"

**Asignatura:** Estructura de Datos II – Ingeniería de Software

## Contenido
- `NodoArbol.java`: nodo genérico `NodoArbol<T>` con referencias izquierda y derecha, constructores, getters/setters y métodos básicos.
- `ArbolBinario.java`: utilidades para contar nodos, calcular la altura y clasificar un árbol (lleno, completo, perfecto, degenerado).
- `Main.java`: arma "Mi primer árbol" y los cuatro árboles de la guía, los imprime y comprueba la clasificación.
- `arboles_binarios.drawio`: 5 páginas (pestañas), una por árbol, diseñadas antes de programar.
- `salida.txt`: salida de la ejecución.

## Ejecución
```
javac -encoding UTF-8 *.java
java Main
```

## Diseño de la clase NodoArbol<T>

| Elemento | Descripción |
|---|---|
| `dato`, `izquierdo`, `derecho` | Atributos privados. Los hijos son referencias a otro `NodoArbol<T>`; `null` indica que no hay hijo. |
| `NodoArbol(T dato)` | Crea un nodo sin hijos. |
| `NodoArbol(T dato, izquierdo, derecho)` | Crea un nodo con sus dos hijos (pueden ser `null`). |
| `getDato / setDato`, `getIzquierdo / setIzquierdo`, `getDerecho / setDerecho` | Acceso y modificación. |
| `esHoja()`, `tieneIzquierdo()`, `tieneDerecho()`, `cantidadHijos()` | Métodos básicos de consulta. |

A diferencia de `NodoGeneral` (lista de hijos), aquí cada nodo tiene **como máximo dos hijos**, y la posición importa: izquierdo y derecho son distintos.

## Definiciones y cómo se verifican

| Tipo | Definición | Cómo se comprueba en el código |
|---|---|---|
| **Lleno** | Cada nodo tiene 0 o 2 hijos. | Recursivo: si un nodo tiene exactamente 1 hijo, falla. |
| **Completo** | Todos los niveles están llenos, salvo quizá el último, que se llena de izquierda a derecha. | Se numeran los nodos como en un arreglo (raíz 0, hijos 2i+1 y 2i+2); es completo si ningún índice supera el total de nodos. |
| **Perfecto** | Todos los nodos internos tienen 2 hijos y todas las hojas están al mismo nivel. | Un árbol de altura h es perfecto si tiene exactamente 2^(h+1) − 1 nodos. |
| **Degenerado** | Cada nodo tiene un solo hijo; se parece a una lista. | Recursivo: falla si algún nodo tiene 2 hijos. |

## Clasificación de los árboles

| Árbol | Nodos | Altura | Lleno | Completo | Perfecto | Degenerado |
|---|---|---|---|---|---|---|
| Mi primer árbol | 6 | 2 | No | No | No | No |
| Árbol lleno | 5 | 2 | Sí | Sí | No | No |
| Árbol completo | 6 | 2 | No | Sí | No | No |
| Árbol perfecto | 7 | 2 | Sí | Sí | Sí | No |
| Árbol degenerado | 5 | 4 | No | No | No | Sí |

### Observaciones
- **Las categorías no son excluyentes.** El árbol "lleno" de la guía (A, B, C, D, E) también es completo, porque D y E ocupan las primeras posiciones del último nivel. No es perfecto porque le faltan 2 nodos para tener 7.
- **Perfecto implica lleno y completo.** Es el caso más estricto.
- **Completo no implica lleno.** En el árbol completo, C tiene solo el hijo izquierdo F.
- **Mi primer árbol no entra en ninguna categoría**, porque C solo tiene hijo derecho. Si ese hijo estuviera a la izquierda, sería completo.
- **Degenerado = lista.** Con n nodos su altura es n − 1 (aquí 4 con 5 nodos), mientras que el perfecto de 7 nodos tiene altura 2. Un árbol perfecto de altura h tiene 2^(h+1) − 1 nodos, de modo que la altura crece como log₂(n); en el degenerado crece como n.

## Pruebas (clasificación esperada vs. obtenida)
Cada clasificación de la tabla se calculó primero a mano y luego se comparó con el resultado del programa. Los cinco casos dieron **OK**; consulta `salida.txt`.
