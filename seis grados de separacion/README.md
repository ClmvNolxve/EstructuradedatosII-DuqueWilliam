# Reto Seis grados de separación: red social de frutas típicas de Colombia

**Asignatura:** Estructura de Datos II – Ingeniería de Software

Red social simulada cuyos usuarios son frutas típicas de Colombia. Con un recorrido en anchura (BFS) se calcula el número mínimo de conexiones entre dos usuarios y se muestra el recorrido por niveles.

## Contenido
- `RedSocial.java`: grafo no dirigido con usuarios, conexiones y los métodos BFS.
- `Main.java`: construye la red, muestra el recorrido por niveles y las distancias.
- `Pruebas.java`: 16 verificaciones automáticas, sin librerías externas.
- `red_por_niveles.drawio`: diagrama de la red organizado por niveles desde Borojó, con el camino más corto marcado.
- `salida.txt`: salida completa de la ejecución.

## Ejecución
```
javac -encoding UTF-8 *.java
java Main
```

## La red simulada

15 usuarios y 16 conexiones. Cada conexión es una amistad en los dos sentidos.

| Usuario | Amigos |
|---|---|
| Borojó | Chontaduro, Mamoncillo |
| Chontaduro | Borojó, Papaya |
| Curuba | Granadilla, Tomate de árbol, Uchuva |
| Feijoa | Guayaba, Mora |
| Granadilla | Curuba, Guanábana, Maracuyá |
| Guanábana | Granadilla |
| Guayaba | Feijoa, Mora |
| Lulo | Maracuyá, Mora |
| Mamoncillo | Borojó, Mango |
| Mango | Mamoncillo, Maracuyá, Papaya |
| Maracuyá | Granadilla, Lulo, Mango |
| Mora | Feijoa, Guayaba, Lulo |
| Papaya | Chontaduro, Mango |
| Tomate de árbol | Curuba |
| Uchuva | Curuba |

La red es un grafo porque una persona puede tener varios amigos y los amigos pueden formar ciclos, algo que un árbol no permite. Por ejemplo, Borojó, Chontaduro, Papaya, Mango y Mamoncillo forman un ciclo.

## Cómo funciona el BFS

El BFS visita primero a todos los amigos directos, luego a los amigos de los amigos, y así por niveles. Usa una **cola** y un conjunto de **visitados**.

1. Se pone el origen en la cola y se marca como visitado.
2. Se saca un usuario de la cola.
3. Sus amigos que no estén visitados se marcan y se agregan al final de la cola.
4. Se repite hasta que la cola quede vacía.

**Por qué da el número mínimo de conexiones:** como la cola atiende primero a los usuarios más cercanos, el primer momento en que se llega a un usuario es por el camino con menos conexiones. Un recorrido en profundidad (DFS) no ofrece esa garantía.

**Niveles:** en cada vuelta se procesan exactamente los usuarios que había en la cola al empezar, y esos forman un nivel. El nivel de un usuario es su distancia al origen.

**Camino:** al descubrir a cada usuario se guarda quién lo descubrió (su padre). Al llegar al destino se retrocede por los padres hasta el origen.

**Costo:** O(U + C), con U usuarios y C conexiones, porque cada usuario y cada conexión se revisan una vez.

## Recorrido por niveles desde Borojó

| Nivel | Frutas |
|---|---|
| 0 | Borojó |
| 1 | Chontaduro, Mamoncillo |
| 2 | Papaya, Mango |
| 3 | Maracuyá |
| 4 | Granadilla, Lulo |
| 5 | Curuba, Guanábana, Mora |
| 6 | Tomate de árbol, Uchuva, Feijoa, Guayaba |

Cada fruta aparece una sola vez, en el nivel más cercano a Borojó. Mango queda en el nivel 2 y no en el 3, porque el BFS toma el atajo por Mamoncillo en lugar de pasar por Chontaduro y Papaya.

## Número mínimo de conexiones

| Origen | Destino | Camino | Conexiones |
|---|---|---|---|
| Borojó | Uchuva | Borojó, Mamoncillo, Mango, Maracuyá, Granadilla, Curuba, Uchuva | 6 |
| Chontaduro | Guayaba | Chontaduro, Papaya, Mango, Maracuyá, Lulo, Mora, Guayaba | 6 |
| Lulo | Feijoa | Lulo, Mora, Feijoa | 2 |
| Mango | Mango | Mango | 0 |
| Borojó | Piña | No existe en la red | Sin ruta |

## Seis grados de separación

Desde cada usuario se calculó la distancia al usuario más lejano.

| Usuario | Distancia al más lejano |
|---|---|
| Borojó, Chontaduro, Feijoa, Guayaba, Tomate de árbol, Uchuva | 6 |
| Curuba, Guanábana, Mamoncillo, Mora, Papaya | 5 |
| Granadilla, Lulo, Mango | 4 |
| Maracuyá | 3 |

La mayor distancia entre cualquier par de usuarios de la red es **6**, así que esta red cumple la idea de los seis grados: todos están a seis conexiones o menos. Maracuyá es el usuario más central, porque llega a todos en tres conexiones como máximo.

## Pruebas

Las 16 verificaciones de `Pruebas.java` dieron **OK** (16 de 16). Cubren:
- tamaño de la red, amistades en los dos sentidos, conexiones repetidas y conexión de un usuario consigo mismo;
- distancia 0, distancia 1, distancia 2 y distancia 6, con el camino exacto;
- el atajo del BFS, el recorrido por niveles completo y la simetría de la distancia en todos los pares;
- usuario que no existe y usuarios sin ruta entre sí, que devuelven -1.
