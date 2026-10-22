# Eliminación en un BST de frutas (los tres casos)

**Asignatura:** Estructura de Datos II – Ingeniería de Software

Continuación del BST de códigos de frutas: se agrega el método recursivo `eliminar()` con los tres casos (nodo hoja, nodo con un hijo y nodo con dos hijos) y se muestra el árbol antes y después de cada eliminación.

## Contenido
- `NodoBST.java`, `ArbolBST.java`: el BST anterior más `eliminar()`, `preorden()` y `setClave()`.
- `Main.java`: construye el árbol, elimina en cinco pasos e imprime el árbol antes y después de cada uno.
- `Pruebas.java`: 25 verificaciones automáticas.
- `diagramas_eliminacion.drawio`: 5 pestañas, una por eliminación, con el árbol **antes** (arriba) y **después** (abajo).
- `salida.txt`: salida completa de la ejecución.

## Ejecución
```
javac -encoding UTF-8 *.java
java Main
```

## Árbol de partida (13 nodos, altura 3)

```
raíz: 1008 Guayaba
    izq: 1004 Durazno
        izq: 1002 Banano
            izq: 1001 Aguacate
        der: 1006 Fresa
            izq: 1005 Feijoa
            der: 1007 Granadilla
    der: 1012 Mora
        izq: 1010 Mango
            izq: 1009 Lulo
            der: 1011 Maracuyá
        der: 1014 Papaya
            der: 1015 Uchuva
```

Se eligió para que tenga hojas (Lulo, Aguacate, Feijoa…), nodos con un solo hijo (Banano, Papaya) y nodos con dos hijos (Durazno, Guayaba).

## Algoritmo

`eliminar(clave)` baja recursivamente igual que la búsqueda. Al encontrar el nodo, aplica uno de tres casos:

| Caso | Condición | Qué se hace |
|---|---|---|
| **1. Hoja** | No tiene hijos | Se devuelve `null`: el padre deja de apuntarle. |
| **2. Un hijo** | Solo tiene hijo izquierdo o solo derecho | Se devuelve ese hijo: ocupa el lugar del nodo eliminado. |
| **3. Dos hijos** | Tiene ambos hijos | Se busca el **sucesor inorden** (el menor del subárbol derecho, es decir, el nodo más a la izquierda). El nodo recibe la clave y el valor del sucesor, y luego se elimina el sucesor del subárbol derecho. |

**Por qué funciona el caso 3:** el sucesor es el código inmediatamente mayor al eliminado, así que al ponerlo en su lugar siguen cumpliéndose las dos condiciones del BST (todo lo de la izquierda es menor y todo lo de la derecha es mayor). Además, el sucesor nunca tiene hijo izquierdo, por lo que quitarlo de su posición original siempre cae en el caso 1 o en el caso 2.

**Costo:** O(h), con h la altura del árbol, igual que buscar e insertar.

## Las cinco eliminaciones (aplicadas en secuencia)

Cada "después" es el "antes" de la siguiente fila. Los diagramas dibujados están en `diagramas_eliminacion.drawio` y el árbol en texto, antes y después de cada paso, está en `salida.txt`.

| # | Caso | Eliminar | Antes | Después |
|---|---|---|---|---|
| 1 | Hoja | 1009 Lulo | Mango tiene hijos Lulo y Maracuyá. | Mango queda solo con el hijo derecho Maracuyá. 12 nodos. |
| 2 | Un hijo (derecho) | 1014 Papaya | Papaya solo tiene a Uchuva. | Uchuva es ahora el hijo derecho de Mora. 11 nodos. |
| 3 | Un hijo (izquierdo) | 1002 Banano | Banano solo tiene a Aguacate. | Aguacate es ahora el hijo izquierdo de Durazno. 10 nodos. |
| 4 | Dos hijos | 1004 Durazno | Hijos: Aguacate (izq.) y Fresa (der.). El sucesor es 1005 Feijoa, hoja. | Feijoa ocupa el lugar de Durazno y Fresa queda sin hijo izquierdo. 9 nodos. |
| 5 | Dos hijos (raíz) | 1008 Guayaba | El sucesor es 1010 Mango, que tiene un hijo (Maracuyá). | Mango es la nueva raíz y Maracuyá ocupa el sitio de Mango. 8 nodos. |

El paso 5 muestra que el caso de dos hijos se apoya en los otros dos: al quitar el sucesor de su posición original, esa eliminación se resuelve como caso de un hijo.

### Árbol final (8 nodos)
```
raíz: 1010 Mango
    izq: 1005 Feijoa
        izq: 1001 Aguacate
        der: 1006 Fresa
            der: 1007 Granadilla
    der: 1012 Mora
        izq: 1011 Maracuyá
        der: 1015 Uchuva
```

Su recorrido inorden es 1001, 1005, 1006, 1007, 1010, 1011, 1012, 1015: sigue ordenado, así que la propiedad del BST se conserva.

## Observaciones
- La altura se mantuvo en 3 durante las cinco eliminaciones. Eliminar no balancea el árbol: solo reconecta los nodos afectados.
- Con el sucesor se puede usar también el **predecesor** inorden (el mayor del subárbol izquierdo); ambos son válidos y producen árboles distintos pero correctos.
- Eliminar un código inexistente no cambia el árbol y devuelve `false`.

## Pruebas

Las 25 verificaciones de `Pruebas.java` dieron **OK** (25 de 25). Comprueban, para cada eliminación, que:
- `eliminar()` devuelve `true` y el código ya no se encuentra;
- el tamaño disminuye en 1;
- la estructura coincide con la esperada (se compara el preorden, que junto con el orden de un BST identifica el árbol).

También cubren los casos límite (código inexistente, eliminar dos veces el mismo, árbol vacío, vaciar todo el árbol) y una prueba con 200 datos aleatorios y 100 eliminaciones aleatorias, donde el inorden resultante coincide con los códigos que debían quedar.
