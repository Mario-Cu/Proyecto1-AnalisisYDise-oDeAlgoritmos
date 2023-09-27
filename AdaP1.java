import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.awt.Desktop;


public class AdaP1 {
  public static int Ncomp = 0;
  public static int Nasig = 0;

  public static void main(String args[]) throws IOException {
    int tamVector = 10000;
    int NcompTotales = 0;
    int NasigTotales = 0;
    int MediaNcomp = 0;
    int MediaNasig = 0;
    long timeElapsed = 0;
    File csvOutputFile = new File("ResultadosP1.csv");
    FileWriter fw = new FileWriter(csvOutputFile);
    BufferedWriter bw = new BufferedWriter(fw);
    String header = "Tamaño,NumComp,NumAsign,TiempoEmpleado";
    bw.write(header);
    bw.newLine();

    for (int i = 1; i <= 10; i++) {
      int n = tamVector * i;
      for (int j = 0; j <= 20; j++) {
        Ncomp = 0;
        Nasig = 0;
        int[] v = rellenarVector(n);
        Instant start = Instant.now();
        ordena1(v, v.length);
        Instant finish = Instant.now();
        timeElapsed = Duration.between(start, finish).toNanos();
        NcompTotales = NcompTotales + Ncomp;
        NasigTotales = NasigTotales + Nasig;
      }
      MediaNcomp = NcompTotales / 20;
      MediaNasig = NasigTotales / 20;
      bw.write(String.valueOf(n) + "," + String.valueOf(MediaNcomp) + "," + String.valueOf(MediaNasig) + ","
          + String.valueOf(timeElapsed));
      bw.newLine();
      NcompTotales = 0;
      NasigTotales = 0;
      MediaNcomp = 0;
      MediaNasig = 0;
      timeElapsed = 0;
    }
    bw.close();
    System.out.println("COMPLETADO");

    try {
      if (!Desktop.isDesktopSupported())
      {
        System.out.println("not supported");
        return;
      }
      Desktop desktop = Desktop.getDesktop();
      if (csvOutputFile.exists())
        desktop.open(csvOutputFile);
        
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  
  /**
   * Metodo de relleno del vector (rellena el vector con M elementos)
   * 
   * @return v (Vector de M elementos que contiene ints de 1 a M)
   */
  public static int[] rellenarVector(int n) {

    int[] v = new Random().ints(n, 0, 2*n).toArray();
    return v;

  }

    /**
   * Metodo auxiliar empleado para imprimir el array
   * 
   * @param v Vector de ints que vamos a imprimir
   */
  public static void printArray(int v[]) {
    System.out.print("[");
    for (int i = 0; i < v.length - 1; i++) {
      System.out.print(v[i] + ", ");
    }
    System.out.println(v[v.length - 1] + "]");
  }


  public static void comp(int n) {
    Ncomp = Ncomp + n;
  }

  public static void asig(int n) {
    Nasig = Nasig + n;
  }

  public static void ordena1(int v[], int tam) {
    /* v con índices de 0 a tam-1 */
    int h, r, i, j, w;
    r = tam - 1;
    h = 1;
    while (h <= r / 9) {
      h = 3 * h + 1;
    }
    while (h > 0) {
      for (i = h; i <= r; i++) {
        j = i;
        w = v[i];
        while ((j >= h) && (w < v[j - h])) {
          comp(1);
          asig(1);
          v[j] = v[j - h];
          j = j - h;
        }
        v[j] = w;
        asig(2);
      }
      h = h / 3;
    }
  }

  void ordena2(int v[], int tam) {
    /* v con índices de 0 a tam-1 */
    boolean swapped = true;
    int start = 0;
    int end = tam;
    while (swapped == true) {
      swapped = false;
      for (int i = start; i < end - 1; ++i) {
        if (v[i] > v[i + 1]) {
          int temp = v[i];
          v[i] = v[i + 1];
          v[i + 1] = temp;
          swapped = true;
        }
      }
      if (swapped == false) {
        break;
      }
      swapped = false;
      end = end - 1;
      for (int i = end - 1; i >= start; i--) {
        if (v[i] > v[i + 1]) {
          int temp = v[i];
          v[i] = v[i + 1];
          v[i + 1] = temp;
          swapped = true;
        }
      }
      start = start + 1;
    }
  }

  void ordena3(int[] v, int tam) {
    /* v con índices de 0 a tam-1 */
    int w[] = new int[tam];
    ordena3rec(v, w, 0, tam - 1);
  }

  void ordena3rec(int v[], int w[], int l, int r) {
    if (l < r) {
      int m = (l + r) / 2;
      ordena3rec(v, w, l, m);
      ordena3rec(v, w, m + 1, r);
      int ia = l;
      int ib = m + 1;
      int ic = l;
      while ((ia <= m) && (ib <= r)) {
        if (v[ia] < v[ib]) {
          w[ic] = v[ia];
          ia++;
          ic++;
        } else {
          w[ic] = v[ib];
          ib++;
          ic++;
        }
      }
      while (ia <= m) {
        w[ic] = v[ia];
        ia++;
        ic++;
      }
      while (ib <= r) {
        w[ic] = v[ib];
        ib++;
        ic++;
      }
      for (int i = l; i <= r; i++) {
        v[i] = w[i];
      }
    }
  }




}
