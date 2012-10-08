package secretofdai;

import java.awt.Rectangle;
import java.awt.Image;
import java.io.InputStream;
import javax.swing.ImageIcon;
import java.io.ByteArrayInputStream;
import java.awt.Graphics2D;

public class portal {
  private int x,y;

  // Habitación enlace (toma de referencia el numero cuando añades en la base de datos)
  int enlace;

  /* Nombre del personaje que además de servirnos para su identificación
  nos sirve para la localización de las imágenes de las animaciones.
  Además, que nos sirve por si en un futuro queremos hacer una aventura
  controlando 2 o mas personajes principales.
 */
 private String nombre;
 private Image imagePortal;
 private Sprite spritePortal;

 public portal(int x, int y,String nombre,int enlace) {
   this.nombre=nombre;

   imagePortal=cargaImagen("images/portales/"+this.nombre+"_a.png");

   // Creamos el sprite
   Animation anim = new Animation();
   anim.addFrame(imagePortal, 100);
   spritePortal = new Sprite(anim);

   this.x=x;
   this.y=y;
   this.enlace=enlace;
 }

 public void posicionaPortal(int x, int y) {
   this.x=x;
   this.y=y;
 }

public int devuelveX() {
  return this.x;
}

public int devuelveY() {
  return this.y;
}

public int devuelveHabitacionEnlace(){
  return enlace;
}

public Image devuelveImage() {
  return imagePortal;
}

public Sprite devuelveSprite() {
  return spritePortal;
}

private Image cargaImagen(String ruta) {
  return new ImageIcon(ruta).getImage();
}

public String toString() {
  return x+" "+y;
}


}
