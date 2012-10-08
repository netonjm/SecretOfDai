package secretofdai;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.awt.Rectangle;
import java.awt.*;

class jugador {
  /*
    Nombre del personaje que además de servirnos para su identificación
   nos sirve para la localización de las imágenes de las animaciones.
   Además, que nos sirve por si en un futuro queremos hacer una aventura
   controlando 2 o mas personajes principales.
  */
  private String nombre;

  /*
   actual_x & actual_y determinan la posición que tiene actualmente el personaje.
   destino_x & destino_y determinan la posición destino del personaje.
  */
  private int actual_x,actual_y,destino_x,destino_y;
  private frase frase;
  /*
    - numFramesAnimacion: es una variables que nos determina cuantos frames va
    a tener que leer el juego por cada animación, con esto conseguirmemos más
    fotogramas y que las animaciones sean mas fluidas. OJO que ocuparan más memoria.
    - animacionJugador[]: es un array de imagenes (toda la animación, fotograma a
   fotograma), el numero de imagenes que llevara este array lo define la variable
   anterior.
    - spriteJugador: Java, no es capaz de mostrar en pantalla una Image en pantalla
   solo puede mostrar Sprites, con lo que desarroyamos un método que convierte el
   array de Imagenes de animacionJugador[] en Sprite y lo metemos en este para poder
   trabajar. En el sprite guardas la secuencia de movimiento y puedes devolver la imagen
   que quieras.
   */
  private int numFramesAnimacion;
  private Image animacionJugador[];
  private Sprite spriteJugador;

  /*
  Esta variable se refiere al estado del personaje, posibles estados:
      estado 0: Parado
             1: Caminando
             2: Hablando
             3: ....
  */

  private int estadoAnimacion;

  public jugador(int x, int y) {
    this.nombre="lluna";

    this.actual_x=x;
    this.actual_y=y;
    this.destino_x=x;
    this.destino_y=y;

    frase=new frase();

    cambiaEstadoAnimacion(0);
  }

  public void posicionaJugador(int x, int y) {
    this.actual_x=x;
    this.actual_y=y;
    this.destino_x=x;
    this.destino_y=y;
  }

 public void mueveJugador(int x, int y) {
   if (x%2!=0) x++;
   if (y%2!=0) y++;
   this.destino_x=x;
   this.destino_y=y;
 }

public void paraJugador() {
    this.destino_x=this.actual_x;
    this.destino_y=this.actual_y;
}

public void actualizaMovimiento () {
  int velocidad=2;
  if (actual_x>destino_x) actual_x-=velocidad;
  else if (actual_x<destino_x) actual_x+=velocidad;
  if (actual_y>destino_y) actual_y-=velocidad;
  else if (actual_y<destino_y) actual_y+=velocidad;
}

public boolean estaQuieto() {
  return this.actual_x==this.destino_x && this.actual_y==this.destino_y;
}

 public int devuelveX() {
   return this.actual_x;
 }

 public int devuelveY() {
   return this.actual_y;
 }

 public frase devuelveFrase() {
   return this.frase;
 }

public int devuelveEstadoAnimacion() {
  return estadoAnimacion;
}

 private Image cargaImagen(String ruta) {
   return new ImageIcon(ruta).getImage();
 }

 public void cambiaEstadoAnimacion(int i) {
   estadoAnimacion=i;
   cargaAnimacion();
 }

 public boolean colisionPortal(portal portal){
   Rectangle r=new Rectangle(this.actual_x+30,this.actual_y+110,20,20);
   return r.intersects( portal.devuelveX(),portal.devuelveY(), portal.devuelveSprite().getWidth(),portal.devuelveSprite().getHeight());
}

 public boolean colisionNPC(npc npc){
   Rectangle r=new Rectangle(this.actual_x+30,this.actual_y+110,20,20);
   return r.intersects( npc.devuelveX(),npc.devuelveY(), npc.devuelveSprite().getWidth(),npc.devuelveSprite().getHeight());
}

 public boolean colisionItem(item item){
   Rectangle r=new Rectangle(this.actual_x,this.actual_y,devuelveSprite().getWidth(),devuelveSprite().getHeight());
   return r.intersects( item.devuelveX(),item.devuelveY(), item.devuelveSprite().getWidth(),item.devuelveSprite().getHeight());
}

 public void cambiaFrase(String tmp_frase) {

   //try {
     //SimpleSoundPlayer sound = new SimpleSoundPlayer("snd/pam.wav");
     //InputStream stream = new ByteArrayInputStream(sound.getSamples());
     //sound.play(stream);
   //}catch (Exception a){};

   cambiaEstadoAnimacion(2);
   frase.modificaFrase(tmp_frase,this.actual_x,this.actual_y);

 }

 public void dibujarFrase(Graphics2D g) {

   if (frase.devuelveTiempo()>0) {
        g.drawString(frase.toString(), frase.devuelveX(), frase.devuelveY());
        frase.reducirTiempo();

        if (frase.devuelveTiempo()<=0) this.cambiaEstadoAnimacion(0);
      }
  }

 public void cargaAnimacion() {
   numFramesAnimacion=8;
   animacionJugador=new Image[numFramesAnimacion];

   switch (estadoAnimacion) {

     case 0:
       // Parado
       for (int i=0;i<numFramesAnimacion;i++) {
         animacionJugador[i]=cargaImagen("images/personajes/"+this.nombre+"/"+this.nombre+"_c0.png");
       }
       break;
     case 1:
       // Andando derecha
       for (int i=0;i<numFramesAnimacion;i++) {
         animacionJugador[i]=cargaImagen("images/personajes/"+this.nombre+"/"+this.nombre+"_a"+i+".png");
           //if (direccion==0) animacionJugador[i]=cargaImagen("images/personajes/"+this.nombre+"/"+this.nombre+"_ad"+i+".png");
         //else animacionJugador[i]=cargaImagen("images/personajes/"+this.nombre+"/"+this.nombre+"_ai"+i+".png");
       }
       break;
     case 2:
       // Hablando
       for (int i=0;i<numFramesAnimacion;i++) {
         animacionJugador[i]=cargaImagen("images/personajes/"+this.nombre+"/"+this.nombre+"_h"+i+".png");
       }
       break;
     case 3:
           // andando izq
           for (int i=0;i<numFramesAnimacion;i++) {
             animacionJugador[i]=cargaImagen("images/personajes/"+this.nombre+"/"+this.nombre+"_ai"+i+".png");
           }
       break;
   }

   // Creamos el sprite
   Animation anim = new Animation();
   for (int i=0;i<numFramesAnimacion;i++) {
     anim.addFrame(animacionJugador[i], 100);
   }
   spriteJugador = new Sprite(anim);
 }

 public Sprite devuelveSprite () {
  return spriteJugador;
}

 public Sprite actualizaAnimacion() {
   long elapsedTime=16;
   if (spriteJugador.getX() < 0) spriteJugador.setVelocityX(Math.abs(spriteJugador.getVelocityX()));
   else if (spriteJugador.getX() + spriteJugador.getWidth() >= 800)
   {
     spriteJugador.setVelocityX(-Math.abs(spriteJugador.getVelocityX()));
   } if (spriteJugador.getY() < 0) spriteJugador.setVelocityY(Math.abs(spriteJugador.getVelocityY()));
   else if (spriteJugador.getY() + spriteJugador.getHeight() >= 600)
   {
     spriteJugador.setVelocityY(-Math.abs(spriteJugador.getVelocityY()));
   }
   // Actualización sprite
   spriteJugador.update(elapsedTime);
   return spriteJugador;
 }

public String toString() {
  return actual_x+" "+actual_y+" "+destino_x+" "+destino_y;
}

}

