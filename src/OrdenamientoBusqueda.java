import java.util.Scanner;

// Práctica: Algoritmos de ordenamiento y búsqueda
// SOFT-10 Estructura de Datos
//
// El programa le pide al usuario cuántas palabras quiere guardar, las llena en un
// arreglo, deja escoger con qué algoritmo ordenarlas y al final permite buscar
// una palabra con búsqueda binaria.
//
// Para todas las comparaciones uso compareToIgnoreCase (en el método comparar),
// así "Casa" y "casa" se toman como iguales. Lo importante es que ordenar y buscar
// usen la misma forma de comparar, porque si no la búsqueda binaria se pierde.
public class OrdenamientoBusqueda {

    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  ALGORITMOS DE ORDENAMIENTO Y BUSQUEDA");
        System.out.println("==============================================");

        // primero el usuario dice de qué tamaño quiere el arreglo
        int tamano = leerEnteroPositivo("Ingrese la cantidad de palabras que tendra el arreglo: ");
        String[] palabras = new String[tamano];

        // y luego lo va llenando palabra por palabra
        llenarArreglo(palabras);

        System.out.print("\nArreglo original: ");
        imprimirArreglo(palabras);

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            if (opcion >= 1 && opcion <= 5) {
                // ordeno una copia y no el original, así se pueden probar
                // varios métodos con las mismas palabras desordenadas
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

                // ya con el arreglo ordenado se puede usar la búsqueda binaria
                menuBusqueda(copia);

            } else if (opcion != 0) {
                System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 0);

        System.out.println("\nPrograma finalizado. Hasta luego!");
        entrada.close();
    }

    // ---------------- menús y lectura de datos ----------------

    static void mostrarMenu() {
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

    // deja buscar todas las palabras que el usuario quiera hasta que diga que no
    static void menuBusqueda(String[] arregloOrdenado) {
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

    static void llenarArreglo(String[] arreglo) {
        System.out.println("\nIngrese las " + arreglo.length + " palabras:");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.print("  Palabra " + (i + 1) + ": ");
            arreglo[i] = leerPalabra();
        }
    }

    // no dejo meter palabras vacías
    static String leerPalabra() {
        String texto = entrada.nextLine().trim();
        while (texto.isEmpty()) {
            System.out.print("  No puede estar vacia, intente de nuevo: ");
            texto = entrada.nextLine().trim();
        }
        return texto;
    }

    // lee un número; si el usuario escribe letras no se cae, solo lo vuelve a pedir
    static int leerEntero(String mensaje) {
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

    // para el tamaño del arreglo, que no puede ser 0 ni negativo
    static int leerEnteroPositivo(String mensaje) {
        int numero = leerEntero(mensaje);
        while (numero <= 0) {
            System.out.println("El tamano debe ser mayor que cero.");
            numero = leerEntero(mensaje);
        }
        return numero;
    }

    // ---------------- métodos de apoyo ----------------

    // negativo si a va antes que b, 0 si son iguales, positivo si a va después
    static int comparar(String a, String b) {
        return a.compareToIgnoreCase(b);
    }

    static void intercambiar(String[] arreglo, int i, int j) {
        String temporal = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temporal;
    }

    static String[] copiarArreglo(String[] original) {
        String[] copia = new String[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    static void imprimirArreglo(String[] arreglo) {
        System.out.print("[");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.print(arreglo[i]);
            if (i < arreglo.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    // ---------------- 1. Selección ----------------
    // En cada vuelta busco la palabra más pequeña de lo que falta por ordenar
    // y la pongo al inicio de esa parte.
    //
    // Complejidad: O(n^2) siempre.
    // El for de afuera da n-1 vueltas y el de adentro va recorriendo lo que falta,
    // entonces en total son n(n-1)/2 comparaciones más o menos. No importa si el
    // arreglo ya venía ordenado, igual lo recorre todo, por eso ni el mejor caso mejora.
    static void ordenamientoSeleccion(String[] arreglo) {
        int n = arreglo.length;

        // i es la posición donde va a quedar la siguiente palabra más pequeña,
        // todo lo que está antes de i ya quedó ordenado
        for (int i = 0; i < n - 1; i++) {
            int indiceMenor = i; // de momento asumo que la menor es la de i

            // reviso el resto a ver si hay alguna más pequeña
            for (int j = i + 1; j < n; j++) {
                if (comparar(arreglo[j], arreglo[indiceMenor]) < 0) {
                    indiceMenor = j;
                }
            }

            // cambio la menor que encontré a la posición i
            if (indiceMenor != i) {
                intercambiar(arreglo, i, indiceMenor);
            }
        }
    }

    // ---------------- 2. Inserción ----------------
    // Funciona parecido a cuando uno acomoda cartas en la mano: agarro una palabra
    // y la meto en su lugar dentro de la parte de la izquierda, que ya está ordenada,
    // corriendo hacia la derecha las que son mayores.
    //
    // Complejidad: O(n^2) en el peor caso y en el promedio.
    // El peor caso es cuando viene al revés, porque cada palabra se compara con todas
    // las anteriores. Si el arreglo ya está ordenado el while ni entra y queda en O(n).
    static void ordenamientoInsercion(String[] arreglo) {
        int n = arreglo.length;

        // empiezo en 1 porque una sola palabra (la de la posición 0) ya está "ordenada"
        for (int i = 1; i < n; i++) {
            String clave = arreglo[i]; // la palabra que voy a acomodar
            int j = i - 1;

            // mientras la de la izquierda sea mayor que la clave, la corro un espacio
            // a la derecha para hacerle campo
            while (j >= 0 && comparar(arreglo[j], clave) > 0) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }

            // aquí quedó el hueco donde va la clave
            arreglo[j + 1] = clave;
        }
    }

    // ---------------- 3. Burbuja ----------------
    // Voy comparando cada palabra con la de al lado y si están al revés las cambio.
    // En cada pasada la palabra mayor se va "subiendo" hasta el final, como una burbuja.
    //
    // Complejidad: O(n^2) en el peor caso y en el promedio (n-1 pasadas con hasta n-1
    // comparaciones cada una). Le puse una bandera para que si en una pasada no cambia
    // nada se detenga, así si ya viene ordenado solo hace una pasada: O(n).
    static void ordenamientoBurbuja(String[] arreglo) {
        int n = arreglo.length;
        boolean huboIntercambio;

        // después de cada pasada las últimas posiciones ya tienen las mayores,
        // por eso el for de adentro va llegando cada vez menos lejos (n - 1 - i)
        for (int i = 0; i < n - 1; i++) {
            huboIntercambio = false;

            for (int j = 0; j < n - 1 - i; j++) {
                // si la de la izquierda es mayor que la de la derecha, se cambian
                if (comparar(arreglo[j], arreglo[j + 1]) > 0) {
                    intercambiar(arreglo, j, j + 1);
                    huboIntercambio = true;
                }
            }

            // si no se cambió nada ya está ordenado, no vale la pena seguir
            if (!huboIntercambio) {
                break;
            }
        }
    }

    // ---------------- 4. Mezcla (Merge Sort) ----------------
    // Este usa "divide y vencerás": parto el arreglo a la mitad, ordeno cada mitad
    // llamando al mismo método (recursión) y después junto las dos mitades ya ordenadas.
    // Se deja de partir cuando el pedazo tiene 1 elemento o ninguno, porque eso ya
    // está ordenado.
    //
    // Complejidad: O(n log n) en todos los casos.
    // Como siempre se parte a la mitad, salen unos log n niveles de división, y en cada
    // nivel juntar las mitades recorre las n palabras. Entonces es n por log n.
    // La desventaja es que necesita arreglos extra para mezclar (memoria O(n)).
    static void ordenamientoMezcla(String[] arreglo, int izquierda, int derecha) {
        // si el pedazo tiene 1 o 0 elementos ya no hay nada que hacer
        if (izquierda >= derecha) {
            return;
        }

        // saco la mitad (lo escribo así en vez de (izq+der)/2 para que no se desborde)
        int medio = izquierda + (derecha - izquierda) / 2;

        // ordeno cada mitad por aparte
        ordenamientoMezcla(arreglo, izquierda, medio);
        ordenamientoMezcla(arreglo, medio + 1, derecha);

        // y las junto
        mezclar(arreglo, izquierda, medio, derecha);
    }

    // junta arreglo[izquierda..medio] con arreglo[medio+1..derecha], que ya vienen
    // ordenados cada uno, y deja todo ordenado en arreglo[izquierda..derecha]
    static void mezclar(String[] arreglo, int izquierda, int medio, int derecha) {
        int tamanoIzq = medio - izquierda + 1;
        int tamanoDer = derecha - medio;

        // copio cada mitad a un arreglo temporal
        String[] mitadIzq = new String[tamanoIzq];
        String[] mitadDer = new String[tamanoDer];

        for (int i = 0; i < tamanoIzq; i++) {
            mitadIzq[i] = arreglo[izquierda + i];
        }
        for (int j = 0; j < tamanoDer; j++) {
            mitadDer[j] = arreglo[medio + 1 + j];
        }

        int i = 0;          // por dónde voy en la mitad izquierda
        int j = 0;          // por dónde voy en la mitad derecha
        int k = izquierda;  // dónde voy escribiendo en el arreglo original

        // comparo la primera que queda de cada mitad y paso la menor al arreglo
        while (i < tamanoIzq && j < tamanoDer) {
            // con <= las palabras iguales mantienen el orden en que venían
            if (comparar(mitadIzq[i], mitadDer[j]) <= 0) {
                arreglo[k] = mitadIzq[i];
                i++;
            } else {
                arreglo[k] = mitadDer[j];
                j++;
            }
            k++;
        }

        // cuando una mitad se acaba, lo que sobró de la otra se copia tal cual
        // (ya viene ordenado)
        while (i < tamanoIzq) {
            arreglo[k] = mitadIzq[i];
            i++;
            k++;
        }
        while (j < tamanoDer) {
            arreglo[k] = mitadDer[j];
            j++;
            k++;
        }
    }

    // ---------------- 5. Rápido (Quick Sort) ----------------
    // También es "divide y vencerás". Escojo una palabra como pivote (aquí la última),
    // acomodo las menores a la izquierda y las mayores a la derecha, y con eso el
    // pivote ya queda en su lugar final. Después hago lo mismo con cada lado.
    //
    // Complejidad: O(n log n) en el promedio y en el mejor caso, cuando el pivote parte
    // el arreglo más o menos a la mitad (log n niveles y en cada uno se recorren n).
    // El peor caso es O(n^2): pasa cuando el pivote siempre queda en una punta, por
    // ejemplo si el arreglo ya venía ordenado, porque cada vez solo se quita un elemento.
    static void ordenamientoRapido(String[] arreglo, int izquierda, int derecha) {
        // con 1 o 0 elementos ya está ordenado
        if (izquierda < derecha) {
            // acomodo alrededor del pivote y me devuelve dónde quedó
            int posicionPivote = particionar(arreglo, izquierda, derecha);

            // ordeno lo que quedó a la izquierda y a la derecha del pivote
            ordenamientoRapido(arreglo, izquierda, posicionPivote - 1);
            ordenamientoRapido(arreglo, posicionPivote + 1, derecha);
        }
    }

    // la partición (método de Lomuto): recorre el pedazo una vez, O(n)
    static int particionar(String[] arreglo, int izquierda, int derecha) {
        String pivote = arreglo[derecha]; // uso la última como pivote

        // i marca hasta dónde llegan las palabras menores o iguales al pivote
        int i = izquierda - 1;

        // cada vez que encuentro una menor o igual al pivote la paso a esa zona
        for (int j = izquierda; j < derecha; j++) {
            if (comparar(arreglo[j], pivote) <= 0) {
                i++;
                intercambiar(arreglo, i, j);
            }
        }

        // el pivote va justo después de las menores, ese es su lugar final
        intercambiar(arreglo, i + 1, derecha);
        return i + 1;
    }

    // ---------------- Búsqueda binaria ----------------
    // Solo sirve si el arreglo ya está ordenado. Me fijo en la palabra del centro:
    // si es la que busco, listo; si la que busco es menor, tiene que estar en la mitad
    // izquierda; si es mayor, en la derecha. Así en cada paso descarto la mitad.
    //
    // Complejidad: O(log n).
    // Como cada vez queda la mitad (n, n/2, n/4, ... hasta 1), la cantidad de pasos es
    // las veces que se puede dividir n entre 2, o sea log2(n). Por ejemplo con 1000
    // palabras son como 10 comparaciones como máximo, mientras que buscando una por una
    // podrían ser las 1000. Si la palabra justo está en el centro es O(1).
    //
    // Devuelve la posición donde está la palabra, o -1 si no está.
    static int busquedaBinaria(String[] arreglo, String objetivo) {
        // al inicio busco en todo el arreglo
        int inicio = 0;
        int fin = arreglo.length - 1;

        // mientras quede algún pedazo donde buscar
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int resultado = comparar(arreglo[medio], objetivo);

            if (resultado == 0) {
                return medio; // la encontré
            } else if (resultado < 0) {
                inicio = medio + 1; // la del centro es menor, sigo buscando a la derecha
            } else {
                fin = medio - 1;    // la del centro es mayor, sigo buscando a la izquierda
            }
        }

        // ya no quedó dónde buscar, entonces no está
        return -1;
    }
}
