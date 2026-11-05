import java.util.Scanner;

/**
 * SOFT-10 Estructura de Datos
 * Estrategia de aprendizaje: Algoritmos de ordenamiento y busqueda
 *
 * Programa que permite al usuario:
 *   1. Llenar un arreglo estatico de palabras (String) del tamano que el mismo defina.
 *   2. Ordenar el arreglo con el algoritmo que elija en un menu:
 *      seleccion, insercion, burbuja, mezcla (merge sort) o rapido (quick sort).
 *   3. Buscar una palabra en el arreglo ya ordenado mediante busqueda binaria.
 *
 * Todas las comparaciones entre palabras se hacen con el metodo comparar(),
 * que ignora mayusculas y minusculas. Es importante que el ordenamiento y la
 * busqueda binaria usen el MISMO criterio de comparacion; si no, la busqueda
 * binaria podria fallar porque el arreglo no estaria "ordenado" segun ella.
 *
 * Nota sobre la complejidad: en todos los algoritmos, n es la cantidad de
 * palabras del arreglo. Se cuenta cada comparacion entre dos palabras como una
 * operacion basica.
 */
public class OrdenamientoBusqueda {

    // Scanner compartido por todo el programa para leer lo que escribe el usuario.
    private static final Scanner entrada = new Scanner(System.in);

    // =====================================================================
    //  PROGRAMA PRINCIPAL
    // =====================================================================
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  ALGORITMOS DE ORDENAMIENTO Y BUSQUEDA");
        System.out.println("==============================================");

        // 1. El usuario define el tamano del arreglo estatico.
        int tamano = leerEnteroPositivo("Ingrese la cantidad de palabras que tendra el arreglo: ");
        String[] palabras = new String[tamano];

        // 2. El usuario llena el arreglo, posicion por posicion.
        llenarArreglo(palabras);

        System.out.print("\nArreglo original: ");
        imprimirArreglo(palabras);

        // 3. Menu de algoritmos de ordenamiento.
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            if (opcion >= 1 && opcion <= 5) {
                // Se ordena una COPIA del arreglo original, para que el usuario
                // pueda probar varios algoritmos partiendo siempre de los mismos datos.
                String[] copia = copiarArreglo(palabras);

                switch (opcion) {
                    case 1:
                        System.out.println("\nMetodo de SELECCION - Complejidad: O(n^2)");
                        ordenamientoSeleccion(copia);
                        break;
                    case 2:
                        System.out.println("\nMetodo de INSERCION - Complejidad: O(n^2) (mejor caso O(n))");
                        ordenamientoInsercion(copia);
                        break;
                    case 3:
                        System.out.println("\nMetodo de BURBUJA - Complejidad: O(n^2) (mejor caso O(n))");
                        ordenamientoBurbuja(copia);
                        break;
                    case 4:
                        System.out.println("\nMetodo de MEZCLA (Merge Sort) - Complejidad: O(n log n)");
                        ordenamientoMezcla(copia, 0, copia.length - 1);
                        break;
                    case 5:
                        System.out.println("\nMetodo RAPIDO (Quick Sort) - Complejidad: O(n log n) (peor caso O(n^2))");
                        ordenamientoRapido(copia, 0, copia.length - 1);
                        break;
                }

                System.out.print("Arreglo ordenado: ");
                imprimirArreglo(copia);

                // 4. Con el arreglo ya ordenado, se permite la busqueda binaria.
                menuBusqueda(copia);

            } else if (opcion != 0) {
                System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 0);

        System.out.println("\nPrograma finalizado. Hasta luego!");
        entrada.close();
    }

    // =====================================================================
    //  MENUS Y LECTURA DE DATOS
    // =====================================================================

    /** Muestra el menu de algoritmos de ordenamiento. */
    private static void mostrarMenu() {
        System.out.println("\n----------------------------------------------");
        System.out.println("  MENU DE ALGORITMOS DE ORDENAMIENTO");
        System.out.println("----------------------------------------------");
        System.out.println("  1. Seleccion  ............ O(n^2)");
        System.out.println("  2. Insercion  ............ O(n^2)");
        System.out.println("  3. Burbuja    ............ O(n^2)");
        System.out.println("  4. Mezcla (Merge Sort) ... O(n log n)");
        System.out.println("  5. Rapido (Quick Sort) ... O(n log n) promedio");
        System.out.println("  0. Salir");
    }

    /**
     * Permite al usuario buscar palabras con busqueda binaria las veces que
     * quiera dentro del arreglo ya ordenado.
     */
    private static void menuBusqueda(String[] arregloOrdenado) {
        String respuesta;
        do {
            System.out.print("\nIngrese la palabra a buscar (busqueda binaria): ");
            String objetivo = leerPalabra();

            int posicion = busquedaBinaria(arregloOrdenado, objetivo);

            if (posicion != -1) {
                System.out.println("La palabra \"" + objetivo + "\" SI se encuentra en el arreglo ordenado, "
                        + "en la posicion " + posicion + " (indice desde 0).");
            } else {
                System.out.println("La palabra \"" + objetivo + "\" NO se encuentra en el arreglo.");
            }

            System.out.print("Desea buscar otra palabra? (s/n): ");
            respuesta = entrada.nextLine().trim();
        } while (respuesta.equalsIgnoreCase("s"));
    }

    /** Pide al usuario cada una de las palabras del arreglo. */
    private static void llenarArreglo(String[] arreglo) {
        System.out.println("\nIngrese las " + arreglo.length + " palabras:");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.print("  Palabra " + (i + 1) + ": ");
            arreglo[i] = leerPalabra();
        }
    }

    /** Lee una palabra que no este vacia. */
    private static String leerPalabra() {
        String texto = entrada.nextLine().trim();
        while (texto.isEmpty()) {
            System.out.print("  No puede estar vacia, intente de nuevo: ");
            texto = entrada.nextLine().trim();
        }
        return texto;
    }

    /** Lee un numero entero; si el usuario escribe algo que no es numero, vuelve a pedirlo. */
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    /** Lee un numero entero mayor que cero (usado para el tamano del arreglo). */
    private static int leerEnteroPositivo(String mensaje) {
        int numero = leerEntero(mensaje);
        while (numero <= 0) {
            System.out.println("El tamano debe ser mayor que cero.");
            numero = leerEntero(mensaje);
        }
        return numero;
    }

    // =====================================================================
    //  UTILIDADES
    // =====================================================================

    /**
     * Compara dos palabras sin distinguir mayusculas de minusculas.
     * Devuelve un numero negativo si a va antes que b, 0 si son iguales
     * y un numero positivo si a va despues que b.
     */
    private static int comparar(String a, String b) {
        return a.compareToIgnoreCase(b);
    }

    /** Intercambia los elementos de las posiciones i y j del arreglo. */
    private static void intercambiar(String[] arreglo, int i, int j) {
        String temporal = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temporal;
    }

    /** Crea una copia del arreglo para no modificar el original. */
    private static String[] copiarArreglo(String[] original) {
        String[] copia = new String[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    /** Imprime el arreglo con el formato [a, b, c]. */
    private static void imprimirArreglo(String[] arreglo) {
        System.out.print("[");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.print(arreglo[i]);
            if (i < arreglo.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    // =====================================================================
    //  1. ORDENAMIENTO POR SELECCION
    // =====================================================================
    /**
     * Idea: en cada pasada se busca la palabra MENOR de la parte que aun no
     * esta ordenada y se coloca al inicio de esa parte.
     *
     * Complejidad de tiempo: O(n^2) en el mejor, promedio y peor caso.
     *   - El ciclo externo se repite n-1 veces.
     *   - El ciclo interno hace (n-1) + (n-2) + ... + 1 = n(n-1)/2 comparaciones.
     *   - Siempre recorre todo, aunque el arreglo ya este ordenado, por eso
     *     incluso el mejor caso es O(n^2).
     */
    public static void ordenamientoSeleccion(String[] arreglo) {
        int n = arreglo.length;

        // Paso 1: recorrer el arreglo; "i" marca la posicion donde se colocara
        // la siguiente palabra menor. Todo lo que esta antes de i ya esta ordenado.
        for (int i = 0; i < n - 1; i++) {

            // Paso 2: suponer que la menor palabra de la parte desordenada es la de la posicion i.
            int indiceMenor = i;

            // Paso 3: recorrer el resto del arreglo buscando una palabra aun menor.
            for (int j = i + 1; j < n; j++) {
                if (comparar(arreglo[j], arreglo[indiceMenor]) < 0) {
                    indiceMenor = j; // se encontro una menor, se guarda su posicion
                }
            }

            // Paso 4: colocar la menor encontrada en la posicion i (intercambio).
            if (indiceMenor != i) {
                intercambiar(arreglo, i, indiceMenor);
            }
        }
    }

    // =====================================================================
    //  2. ORDENAMIENTO POR INSERCION
    // =====================================================================
    /**
     * Idea: igual que cuando se ordenan cartas en la mano. Se toma cada palabra
     * y se "inserta" en su lugar correcto dentro de la parte izquierda, que ya
     * esta ordenada, desplazando hacia la derecha las palabras mayores.
     *
     * Complejidad de tiempo:
     *   - Peor caso O(n^2): arreglo en orden inverso; cada palabra se compara
     *     con todas las anteriores (1 + 2 + ... + (n-1) = n(n-1)/2).
     *   - Caso promedio O(n^2).
     *   - Mejor caso O(n): arreglo ya ordenado; el ciclo interno no se ejecuta
     *     y solo se hace una comparacion por palabra.
     */
    public static void ordenamientoInsercion(String[] arreglo) {
        int n = arreglo.length;

        // Paso 1: la primera palabra (posicion 0) se considera ya ordenada,
        // por eso se empieza desde la posicion 1.
        for (int i = 1; i < n; i++) {

            // Paso 2: guardar la palabra que se va a insertar ("clave").
            String clave = arreglo[i];
            int j = i - 1;

            // Paso 3: mientras haya palabras a la izquierda que sean MAYORES que la clave,
            // se desplazan una posicion a la derecha para abrirle espacio.
            while (j >= 0 && comparar(arreglo[j], clave) > 0) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }

            // Paso 4: insertar la clave en el hueco que quedo libre.
            arreglo[j + 1] = clave;
        }
    }

    // =====================================================================
    //  3. ORDENAMIENTO POR BURBUJA
    // =====================================================================
    /**
     * Idea: se recorren las palabras comparando cada par de vecinas y se
     * intercambian si estan en el orden incorrecto. En cada pasada la palabra
     * mayor "sube" (como una burbuja) hasta el final del arreglo.
     *
     * Complejidad de tiempo:
     *   - Peor caso O(n^2): n-1 pasadas con hasta n-1 comparaciones cada una.
     *   - Caso promedio O(n^2).
     *   - Mejor caso O(n): gracias a la bandera "huboIntercambio", si el arreglo
     *     ya esta ordenado se detiene despues de una sola pasada.
     */
    public static void ordenamientoBurbuja(String[] arreglo) {
        int n = arreglo.length;
        boolean huboIntercambio;

        // Paso 1: realizar pasadas sobre el arreglo. Despues de cada pasada "i",
        // las ultimas i+1 posiciones ya tienen las palabras mayores en su lugar.
        for (int i = 0; i < n - 1; i++) {
            huboIntercambio = false;

            // Paso 2: comparar pares de vecinas en la parte que aun no esta ordenada.
            for (int j = 0; j < n - 1 - i; j++) {

                // Paso 3: si la de la izquierda es mayor que la de la derecha, se intercambian.
                if (comparar(arreglo[j], arreglo[j + 1]) > 0) {
                    intercambiar(arreglo, j, j + 1);
                    huboIntercambio = true;
                }
            }

            // Paso 4: si en toda la pasada no hubo intercambios, el arreglo ya
            // esta ordenado y no tiene sentido seguir (optimizacion).
            if (!huboIntercambio) {
                break;
            }
        }
    }

    // =====================================================================
    //  4. ORDENAMIENTO POR MEZCLA (MERGE SORT)
    // =====================================================================
    /**
     * Idea (estrategia "divide y venceras"):
     *   - DIVIDIR: partir el arreglo en dos mitades.
     *   - VENCER: ordenar cada mitad de forma recursiva (llamandose a si mismo).
     *   - COMBINAR: mezclar las dos mitades ya ordenadas en un solo arreglo ordenado.
     * El caso base es un subarreglo de 0 o 1 elemento, que ya esta ordenado.
     *
     * Complejidad de tiempo: O(n log n) en el mejor, promedio y peor caso.
     *   - El arreglo se divide a la mitad repetidamente, lo que produce
     *     log2(n) niveles de recursion.
     *   - En cada nivel, la mezcla recorre en total las n palabras: O(n).
     *   - Total: n * log n.
     * Espacio adicional: O(n), por los arreglos temporales usados al mezclar.
     *
     * @param arreglo   arreglo a ordenar
     * @param izquierda indice inicial del tramo a ordenar
     * @param derecha   indice final del tramo a ordenar
     */
    public static void ordenamientoMezcla(String[] arreglo, int izquierda, int derecha) {
        // Caso base: si el tramo tiene 0 o 1 elementos, ya esta ordenado.
        if (izquierda >= derecha) {
            return;
        }

        // Paso 1 (dividir): calcular el punto medio del tramo.
        int medio = izquierda + (derecha - izquierda) / 2;

        // Paso 2 (vencer): ordenar recursivamente la mitad izquierda y la derecha.
        ordenamientoMezcla(arreglo, izquierda, medio);
        ordenamientoMezcla(arreglo, medio + 1, derecha);

        // Paso 3 (combinar): mezclar las dos mitades ordenadas.
        mezclar(arreglo, izquierda, medio, derecha);
    }

    /**
     * Mezcla dos tramos ya ordenados: arreglo[izquierda..medio] y
     * arreglo[medio+1..derecha], dejando el resultado ordenado en
     * arreglo[izquierda..derecha]. Complejidad: O(n) del tramo.
     */
    private static void mezclar(String[] arreglo, int izquierda, int medio, int derecha) {
        // Paso 1: calcular el tamano de cada mitad y copiarlas a arreglos temporales.
        int tamanoIzq = medio - izquierda + 1;
        int tamanoDer = derecha - medio;

        String[] mitadIzq = new String[tamanoIzq];
        String[] mitadDer = new String[tamanoDer];

        for (int i = 0; i < tamanoIzq; i++) {
            mitadIzq[i] = arreglo[izquierda + i];
        }
        for (int j = 0; j < tamanoDer; j++) {
            mitadDer[j] = arreglo[medio + 1 + j];
        }

        // Paso 2: comparar el primer elemento disponible de cada mitad y pasar
        // el menor al arreglo original. Se repite hasta agotar una de las mitades.
        int i = 0;          // indice para recorrer mitadIzq
        int j = 0;          // indice para recorrer mitadDer
        int k = izquierda;  // indice donde se escribe en el arreglo original

        while (i < tamanoIzq && j < tamanoDer) {
            // Se usa <= para que el algoritmo sea estable (respeta el orden de palabras iguales).
            if (comparar(mitadIzq[i], mitadDer[j]) <= 0) {
                arreglo[k] = mitadIzq[i];
                i++;
            } else {
                arreglo[k] = mitadDer[j];
                j++;
            }
            k++;
        }

        // Paso 3: copiar las palabras que hayan sobrado en la mitad izquierda (si hay).
        while (i < tamanoIzq) {
            arreglo[k] = mitadIzq[i];
            i++;
            k++;
        }

        // Paso 4: copiar las palabras que hayan sobrado en la mitad derecha (si hay).
        while (j < tamanoDer) {
            arreglo[k] = mitadDer[j];
            j++;
            k++;
        }
    }

    // =====================================================================
    //  5. ORDENAMIENTO RAPIDO (QUICK SORT)
    // =====================================================================
    /**
     * Idea (tambien "divide y venceras"):
     *   - Se elige una palabra llamada PIVOTE (aqui, la ultima del tramo).
     *   - Se PARTICIONA el arreglo: las palabras menores que el pivote quedan a
     *     su izquierda y las mayores a su derecha. El pivote queda en su
     *     posicion final definitiva.
     *   - Se repite recursivamente el proceso con la parte izquierda y la derecha.
     *
     * Complejidad de tiempo:
     *   - Mejor caso y caso promedio O(n log n): el pivote divide el arreglo en
     *     partes de tamano parecido, lo que genera unos log n niveles, y en cada
     *     nivel la particion recorre n palabras.
     *   - Peor caso O(n^2): el pivote siempre queda en un extremo (por ejemplo,
     *     si el arreglo ya estaba ordenado y se elige el ultimo como pivote),
     *     entonces cada particion solo reduce el tamano en 1:
     *     n + (n-1) + ... + 1 = n(n+1)/2.
     *
     * @param arreglo   arreglo a ordenar
     * @param izquierda indice inicial del tramo a ordenar
     * @param derecha   indice final del tramo a ordenar
     */
    public static void ordenamientoRapido(String[] arreglo, int izquierda, int derecha) {
        // Caso base: un tramo de 0 o 1 elementos ya esta ordenado.
        if (izquierda < derecha) {

            // Paso 1: particionar el tramo y obtener la posicion final del pivote.
            int posicionPivote = particionar(arreglo, izquierda, derecha);

            // Paso 2: ordenar recursivamente las palabras a la izquierda del pivote.
            ordenamientoRapido(arreglo, izquierda, posicionPivote - 1);

            // Paso 3: ordenar recursivamente las palabras a la derecha del pivote.
            ordenamientoRapido(arreglo, posicionPivote + 1, derecha);
        }
    }

    /**
     * Particion de Lomuto: toma como pivote la ultima palabra del tramo y
     * reorganiza el tramo para que las menores o iguales queden antes del
     * pivote y las mayores despues. Devuelve la posicion final del pivote.
     * Complejidad: O(n) del tramo, porque lo recorre una sola vez.
     */
    private static int particionar(String[] arreglo, int izquierda, int derecha) {
        // Paso 1: elegir el pivote (la ultima palabra del tramo).
        String pivote = arreglo[derecha];

        // Paso 2: "i" indica el final de la zona de palabras menores o iguales al pivote.
        int i = izquierda - 1;

        // Paso 3: recorrer el tramo; cada palabra menor o igual al pivote se
        // mueve a la zona de las menores.
        for (int j = izquierda; j < derecha; j++) {
            if (comparar(arreglo[j], pivote) <= 0) {
                i++;
                intercambiar(arreglo, i, j);
            }
        }

        // Paso 4: colocar el pivote justo despues de la zona de menores,
        // que es su posicion final en el arreglo ordenado.
        intercambiar(arreglo, i + 1, derecha);
        return i + 1;
    }

    // =====================================================================
    //  BUSQUEDA BINARIA
    // =====================================================================
    /**
     * Busca una palabra en un arreglo que YA ESTA ORDENADO.
     *
     * Idea: se compara la palabra buscada con la del centro del arreglo.
     *   - Si son iguales, se encontro.
     *   - Si la buscada es menor, solo puede estar en la mitad izquierda.
     *   - Si es mayor, solo puede estar en la mitad derecha.
     * Asi, en cada paso se descarta la mitad de las palabras restantes.
     *
     * Complejidad de tiempo: O(log n).
     *   En cada iteracion el espacio de busqueda se reduce a la mitad:
     *   n -> n/2 -> n/4 -> ... -> 1. La cantidad de veces que se puede dividir
     *   n entre 2 hasta llegar a 1 es log2(n). Por ejemplo, con 1 000 palabras
     *   se necesitan como maximo unas 10 comparaciones, mientras que una
     *   busqueda secuencial podria necesitar 1 000.
     *   Mejor caso O(1): la palabra esta justo en el centro en la primera comparacion.
     *
     * @param arreglo  arreglo ordenado donde se busca
     * @param objetivo palabra que se quiere encontrar
     * @return la posicion de la palabra, o -1 si no se encuentra
     */
    public static int busquedaBinaria(String[] arreglo, String objetivo) {
        // Paso 1: definir los limites del espacio de busqueda (todo el arreglo).
        int inicio = 0;
        int fin = arreglo.length - 1;

        // Paso 2: repetir mientras el espacio de busqueda no este vacio.
        while (inicio <= fin) {

            // Paso 3: calcular la posicion central. Se escribe asi, en vez de
            // (inicio + fin) / 2, para evitar desbordamiento con indices muy grandes.
            int medio = inicio + (fin - inicio) / 2;

            // Paso 4: comparar la palabra del centro con la buscada.
            int resultado = comparar(arreglo[medio], objetivo);

            if (resultado == 0) {
                // Paso 5a: son iguales, se encontro la palabra.
                return medio;
            } else if (resultado < 0) {
                // Paso 5b: la del centro es menor, la buscada debe estar a la DERECHA.
                inicio = medio + 1;
            } else {
                // Paso 5c: la del centro es mayor, la buscada debe estar a la IZQUIERDA.
                fin = medio - 1;
            }
        }

        // Paso 6: el espacio de busqueda quedo vacio, la palabra no esta en el arreglo.
        return -1;
    }
}
