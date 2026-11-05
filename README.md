# Algoritmos de ordenamiento y búsqueda

**Curso:** SOFT-10 Estructura de Datos
**Docente:** Romario Salas Cerdas
**Estudiante:** _(tu nombre)_

## Descripción

Programa en Java que:

1. Pide al usuario el tamaño de un **arreglo estático de palabras** (`String[]`) y lo llena con las palabras que el usuario escribe.
2. Muestra un **menú** para elegir el algoritmo de ordenamiento:

   | Opción | Algoritmo | Mejor caso | Caso promedio | Peor caso |
   |---|---|---|---|---|
   | 1 | Selección | O(n²) | O(n²) | O(n²) |
   | 2 | Inserción | O(n) | O(n²) | O(n²) |
   | 3 | Burbuja | O(n) | O(n²) | O(n²) |
   | 4 | Mezcla (Merge Sort) | O(n log n) | O(n log n) | O(n log n) |
   | 5 | Rápido (Quick Sort) | O(n log n) | O(n log n) | O(n²) |

3. Con el arreglo ya ordenado, permite hacer **búsqueda binaria** de una palabra: **O(log n)**, porque en cada paso descarta la mitad de las palabras que quedan.

Cada algoritmo es una subrutina (método `static`) del programa principal. Sus pasos están comentados y cada uno indica su complejidad en notación O grande.

Las comparaciones **no distinguen mayúsculas de minúsculas** (`compareToIgnoreCase`). El ordenamiento y la búsqueda binaria usan el mismo criterio.

## Estructura

```
src/
└── OrdenamientoBusqueda.java   // programa principal, algoritmos y búsqueda binaria
```

## Cómo ejecutarlo

Requiere Java (JDK 8 o superior). Desde la carpeta del proyecto:

```bash
javac -d out src/OrdenamientoBusqueda.java
java -cp out OrdenamientoBusqueda
```

También se puede abrir `src/OrdenamientoBusqueda.java` en NetBeans, IntelliJ o VS Code y ejecutarlo desde ahí.

## Ejemplo de uso

```
Ingrese la cantidad de palabras que tendra el arreglo: 5

Ingrese las 5 palabras:
  Palabra 1: pera
  Palabra 2: manzana
  Palabra 3: uva
  Palabra 4: banano
  Palabra 5: kiwi

Arreglo original: [pera, manzana, uva, banano, kiwi]
...
Seleccione una opcion: 4

Metodo de MEZCLA (Merge Sort) - Complejidad: O(n log n)
Arreglo ordenado: [banano, kiwi, manzana, pera, uva]

Ingrese la palabra a buscar (busqueda binaria): pera
La palabra "pera" SI se encuentra en el arreglo ordenado, en la posicion 3 (indice desde 0).
```
