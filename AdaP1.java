import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AdaP1 {
    public static int Ncomp = 0;
    public static int Nasig = 0;

    public static void main (String args[]) throws IOException{ 
      int NcompTotales = 0;
      int NasigTotales = 0;
      int MediaNcomp = 0;
      int MediaNasig = 0;

    File file = new File("Parte1.csv");
    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);

        for(int i = 3; i <= 30; i++){
          for(int j = 0; j <= 20; j++){
            Ncomp = 0;
            Nasig = 0;
            int[] v = rellenarVector();
            v = descolocarVector(v,v.length);
            //Instant start = Instant.now();
            quickSort(v,i);
            //Instant finish = Instant.now();
            //long timeElapsed = Duration.between(start, finish).toNanos();
            NcompTotales = NcompTotales + Ncomp;
            NasigTotales = NasigTotales + Nasig;
        }
        MediaNcomp = NcompTotales/20;
        MediaNasig = NasigTotales/20;
        bw.write(String.valueOf(i)+";"+String.valueOf(MediaNcomp)+";"+String.valueOf(MediaNasig));
        bw.write("\n");
        NcompTotales = 0;
        NasigTotales = 0;
        MediaNcomp = 0;
        MediaNasig = 0;
      }
      bw.close();
      System.out.println("COMPLETADO");
    }
    public static void comp(int n){
        Ncomp = Ncomp + n;
    }

    public static void asig(int n){
        Nasig = Nasig + n;
    }



    /**
     * Metodo utilizado para llamar al algoritmo con su tamaño
     * @param intArray Vector de ints desordenado
     */
    public static void quickSort(int[] intArray, int K) {
        recQuickSort(intArray, 0, intArray.length - 1, K);
      }
    
    /**
     * Metodo quicksort que elige entre el metodo de insercion o el de mediana dependiendo del valor umbral
     * @param intArray Vector de ints utilizado
     * @param left Valor limite inferior del vector
     * @param right Valor limite superior del vector
     * @param K Umbral introducido por el usuario que indica el tamaño minimo del vector para realizar el metodo de insercion
     */
      public static void recQuickSort(int[] intArray, int left, int right, int K) {
        int size = right - left + 1;
        if (size <= K)
          insertionSort(intArray, left, right);
        else {
          double median = medianOf3(intArray, left, right);
          int partition = partitionIt(intArray, left, right, median);
          recQuickSort(intArray, left, partition - 1,K);
          recQuickSort(intArray, partition + 1, right,K);
        }
      }
    

      /**
       * Metodo auxiliar que devuelve el valor de la mediana de 3 
       * @param intArray Vector de ints del cual queremos averiguar la posicion de la mediana 
       * @param left Valor limite inferior del vector
       * @param right Valor limite superior del vector
       * @return Valor int de la mediana de 3
       */
      public static int medianOf3(int[] intArray, int left, int right) {
        int center = (left + right) / 2;
    
        if (intArray[left] > intArray[center])
          swap(intArray, left, center);

        if (intArray[left] > intArray[right])
          swap(intArray, left, right);

        if (intArray[center] > intArray[right])
          swap(intArray, center, right);
        
        comp(3);

        swap(intArray, center, right - 1);
        return intArray[right - 1];
      }
    
    /**
     * Metodo utilizado para obtener el valor que se usará para encontrar el valor limite superior del nuevo vector particion
     * @param intArray Vector de ints del cual buscamos obtener el valor
     * @param left Valor limite inferior del vector 
     * @param right Valor limite superior del vector    
     * @param pivot Valor de la mediana de 3 
     * @return Indice del valor limite superior del nuevo vector particion
     */
      public static int partitionIt(int[] intArray, int left, int right, double pivot) {
        int leftPtr = left;
        int rightPtr = right - 1;
    
        while (true) {
          while (intArray[++leftPtr] < pivot)
            comp(1);
            ;
          while (intArray[--rightPtr] > pivot)
            comp(1);
            ;
          if (leftPtr >= rightPtr)
            break;
          else
            swap(intArray, leftPtr, rightPtr);
        }
        swap(intArray, leftPtr, right - 1);
        return leftPtr;
      }
    

        /**
         * Metodo de insercion en el algoritmo Quicksort
         * @param a Vector de ints
         * @param low Valor limite inferior del vector
         * @param high Valor limite superior del vector
         */
        private static void insertionSort(int a[], int low,int high)
        {
          for (int i = low; i < high; ++i) {
              int key = a[i];
              asig(1);
              int j = i - 1;
              while (j >= 0 && a[j] > key) {
                  a[j + 1] = a[j];
                  j = j - 1;
                  comp(1);
                  asig(1);
              }
              a[j + 1] = key;
              asig(1);
          }
        }

        /**
         * Metodo auxiliar empleado para imprimir el array
         * @param v Vector de ints que vamos a imprimir
         */
        public static void printArray(int v[]){
            System.out.print("[");
            for(int i = 0; i<v.length-1; i++ ){
                System.out.print(v[i] + ", ");
            }
            System.out.println(v[v.length-1]+"]");
        }
    
        /**
         * Metodo de reorganizacion aleatoria del vector
         * @param v Vector de ints  
         * @param M Longitud del vector introducido (si M = v.lenght, se descoloca completamente || si M es v.length / 10 se descoloca un poco)
         * @return
         */
        public static int[] descolocarVector(int[] v, int M){
            Random rand = new Random();
            for(int i=1; i<M; i++){
                int x = rand.nextInt(M);
                int y = rand.nextInt(M);
                swapaux(v,x,y);
            }
            return v;
        }
    
    /**
     * Metodo de relleno del vector (rellena el vector con M elementos)
     * @return v (Vector de M elementos que contiene ints de 1 a M)
     */
        public static int[] rellenarVector(){
            int[] v = new int[100000];
            for(int i = 0; i<100000;i++){
                v[i] = i+1;
            }
            return v;
        }
    
    /**
     * Metodo auxiliar de intercambio de valores para aleatorizar el vector
     * @param v Vector de ints que se busca aleatorizar    
     * @param x Valor x aleatorio de acceso al vector 
     * @param y Valor y aleatorio de acceso al vector
     */
        public static void swap(int[] v, int x, int y){
            int temp = v[x];
            v[x] = v[y];
            v[y] = temp;
            asig(3);
        }

        public static void swapaux(int[] v, int x, int y){
          int temp = v[x];
          v[x] = v[y];
          v[y] = temp;
      }
}
