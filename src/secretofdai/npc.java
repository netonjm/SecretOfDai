package secretofdai;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.*;

 class npc extends Component {
  private String nombre;
  private int x,y;
  private frase frase;

  private int numFramesAnimacion;
  private Image animacionJugador[];
  private Sprite spriteJugador;

  private String cogerFrase;
  private String mirarFrase;
  private String usarFrase;

  private boolean usar;

  private int estadoAnimacion;

  public npc(String nombre,int x, int y,String m,String c,String u) {
    this.nombre=nombre;
    cargaAnimacion();
    this.x=x;
    this.y=y;

    this.cogerFrase=c;
    this.mirarFrase=m;
    this.usarFrase=u;

    frase=new frase();

    cambiaEstadoAnimacion(0);
  }

  public void posicionaNPC(int x, int y) {
   this.x=x;
   this.y=y;
 }

 public int devuelveX() {
   return this.x;
 }

 public int devuelveY() {
   return this.y;
 }

  public boolean devuelveUsar(){
    return usar;
  }

  public String devuelveCogerFrase(){
    return cogerFrase;
  }

  public String devuelveUsarFrase(){
    return usarFrase;
  }

  public String devuelveMirarFrase(){
    return mirarFrase;
  }


 private Image cargaImagen(String ruta) {
   Image manolo = new ImageIcon(ruta).getImage();
   return manolo;
 }

 public Sprite devuelveSpriteAnimacion() {
   Sprite sprite;
   numFramesAnimacion=4;
   animacionJugador=new Image[numFramesAnimacion];

   for (int i=0;i<numFramesAnimacion;i++) {
     animacionJugador[i]=cargaImagen("images/personajes/"+this.nombre+"/"+this.nombre+"_h"+i+".gif");
   }

   // Creamos el sprite
   Animation anim = new Animation();
   for (int i=0;i<numFramesAnimacion;i++) {
     anim.addFrame(animacionJugador[i], 200);
   }

   sprite = new Sprite(anim);
   return sprite;
 }

 public void cambiaEstadoAnimacion(int i) {
   estadoAnimacion=i;
   cargaAnimacion();
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

 public void cambiaFrase(String tmp_frase) {

   //try {
     //SimpleSoundPlayer sound = new SimpleSoundPlayer("snd/pam.wav");
     //InputStream stream = new ByteArrayInputStream(sound.getSamples());
     //sound.play(stream);
   //}catch (Exception a){};

   cambiaEstadoAnimacion(2);
   frase.modificaFrase(tmp_frase,this.x,this.y);

 }

 public boolean clickeoNPC(int x_mouse, int y_mouse){
   Rectangle r=new Rectangle(this.x,this.y,devuelveSprite().getWidth(),devuelveSprite().getHeight());
   return r.intersects(x_mouse,y_mouse, 1,1);
}

 public void dibujarFrase(Graphics2D g) {

   if (frase.devuelveTiempo()>0) {
        g.drawString(frase.toString(), frase.devuelveX(), frase.devuelveY());
        frase.reducirTiempo();

        if (frase.devuelveTiempo()<=0) this.cambiaEstadoAnimacion(0);
      }
  }

public void conversacion(jugador lluna) {
   lluna.cambiaFrase("Hola creo que me gustas.");

   this.cambiaFrase("Ya lo se estoy muy bueno y quiero sexo contigo, más que tú.");
 }

 }
