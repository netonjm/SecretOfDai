package secretofdai;

/*bueno, la clase se llama asi, porque en FENIX la funcion tenia este nombre
  entonces, pues me apetecia llamarla asi :P - Victor */

import java.lang.Math.*;
import java.util.*;
import java.awt.event.*;

public class map_get_pixel {

  public map_get_pixel() { }

  public static int ancho(int [][][]imagen) {
    return imagen.length;
  }

  public static int alto(int [][][]imagen) {
    if (imagen.length > 0) {
      return imagen[0].length;
    }
    return 0;
  }

/*Metodo asignar, metemos la imagen en un array tridimensional, metemos el color pisable
  el no pisable
*/
  public static boolean asignar(int [][][]imagen, int color_PI, int color_NP, MouseEvent e) {
    boolean es_pisable=false;
    int w=ancho(imagen);
    int h=alto(imagen);
    for (int i=0 ; i<w ; i++) {
      for (int j=0 ; j<h ; j++) {
        for (int c = 0; c < 3; c++) {
          if (imagen[e.getX()][e.getY()][c] == color_PI) es_pisable=true;
          if (imagen[e.getX()][e.getY()][c] == color_NP) es_pisable=false;
        }
      }
    }
    return es_pisable;
  }

}