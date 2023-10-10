import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.awt.Desktop;

public class AdaP1 {
  public static long Ncomp = 0;
  public static long Nasig = 0;
 //COMENTAR EL CODIGO POR DIOS
  public static void main(String args[]) throws IOException {
    System.out.println("---------------------INICIO-----------------------");
    int tamVector = 10000;
    long NcompTotales = 0;
    long NasigTotales = 0;
    long MediaNcomp = 0;
    long MediaNasig = 0;
    long timeElapsed = 0;
    long timeElapseTotal = 0;
    long MediaTimeElapsed = 0;
    File csvOutputFile = new File("ResultadosP1.csv");
    FileWriter fw = new FileWriter(csvOutputFile);
    BufferedWriter bw = new BufferedWriter(fw);
    for (int z = 1; z <= 3; z++) {
      System.out.println("--------------------- ALGORITMO: " + z + " -----------------------");
      bw.write("ALGORITMO: " + z);
      bw.newLine();
      String header = "Tamaño,NumComp,NumAsign,TiempoEmpleado";
      bw.write(header);
      bw.newLine();
      for (int i = 1; i <= 10; i++) {
        int n = tamVector * i;
        System.out.println("--------------------- N = " + n + " -----------------------");
        for (int j = 0; j <= 20; j++) {
          Ncomp = 0;
          Nasig = 0;
          timeElapsed = 0;
          int[] v = rellenarVector(n);
          Instant start = Instant.now();
          switch (z) {
            case 1:
              ordena1(v, v.length);
              break;
            case 2:
              ordena2(v, v.length);
              break;
            case 3:
              ordena3(v, v.length);
              break;
          }
          Instant finish = Instant.now();
          timeElapsed = Duration.between(start, finish).toNanos();
          NcompTotales = NcompTotales + Ncomp;
          NasigTotales = NasigTotales + Nasig;
          timeElapseTotal = timeElapseTotal + timeElapsed;
        }
        MediaNcomp = NcompTotales / 20;
        MediaNasig = NasigTotales / 20;
        MediaTimeElapsed = timeElapseTotal / 20;
        bw.write(String.valueOf(n) + "," + String.valueOf(MediaNcomp) + "," + String.valueOf(MediaNasig) + ","
            + String.valueOf(MediaTimeElapsed));
        bw.newLine();
        NcompTotales = 0;
        NasigTotales = 0;
        timeElapseTotal = 0;
        MediaNcomp = 0;
        MediaNasig = 0;
        MediaTimeElapsed = 0;
      }
      bw.newLine();
      bw.newLine();
      bw.newLine();
      bw.newLine();
    }
    bw.close();
    System.out.println("---------------------COMPLETADO--------------------");
    try {
      if (!Desktop.isDesktopSupported()) {
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

    int[] v = new Random().ints(n, 0, 2 * n).toArray();
    return v;

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

  static void ordena2(int v[], int tam) {
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
          asig(3);
          swapped = true;
        }
        comp(1);
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
          asig(3);
          swapped = true;
        }
        comp(1);
      }
      start = start + 1;
    }
  }

  static void ordena3(int[] v, int tam) {
    /* v con índices de 0 a tam-1 */
    int w[] = new int[tam];
    ordena3rec(v, w, 0, tam - 1);
  }

  static void ordena3rec(int v[], int w[], int l, int r) {
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
          asig(1);
        } else {
          w[ic] = v[ib];
          ib++;
          ic++;
          asig(1);
        }
        comp(1);
      }
      while (ia <= m) {
        w[ic] = v[ia];
        ia++;
        ic++;
        asig(1);
      }
      while (ib <= r) {
        w[ic] = v[ib];
        ib++;
        ic++;
        asig(1);
      }
      for (int i = l; i <= r; i++) {
        v[i] = w[i];
        asig(1);
      }
    }
  }

}
