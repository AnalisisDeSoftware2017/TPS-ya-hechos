package herramienta;

import java.util.ArrayList;

public class Test {
    // comentario 1
    /* comentario 2
    * lalala
    * lalala
    * lalala
    * lalala
    * lalala
    * lalala
    * lalala
    * lalala
    * lalala
    * lalala
    * lalala
    * lalala
    */

    public static void main(String[] args) {
        int x = 25;
        for (int i = 10; i < x; i++) {
            System.out.println(i * x);
        }
        x = x + 25;
        imprimirClasesYMetodos(new ArrayList<Clase>());
    }

    public static void imprimirClasesYMetodos(ArrayList<Clase> clases) {
        int y = 25;
        sum(y, y);
    }

    public int sum(int a, int b) {
        return a + b + c + d + e;
    }

    public int asd() {
        if (true) {
            return 1;
        }
        else if (!true) {
            return 2;
        }
        else
            return 3;
        asd();
    }

}

public class Complejo {

    public int complejo() {
        int x = 0;
        int y = 25;
        int z = 2;
        if (true) {
          if (x == 0 && y > 23 && z < 16)
            return 1;
          else if (x == 0 && y > 23 && z >= 16)
            return 33;
        }
        else if (x > 0 && y > 0) {
            return 2;
        }
        else
            return 3;
        menos_complejo();
    }

    public int menos_complejo() {
        int x = 0;
        int y = 25;
        int z = 2;
        if (true) {
          if (x == 0 && y > 23 && z < 16)
            return 1;
          else if (x == 0 && y > 23 && z >= 16)
            return 33;
        }
        else
            return 3;
    }

}
