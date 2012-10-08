package secretofdai;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

class item extends Rectangle{
  private String nombre;

  private int x,y;
  private Image imagenItem;
  private Sprite spriteItem;

  private boolean coger;
  private boolean usar;

  private String cogerFrase;
  private String mirarFrase;
  private String usarFrase;

  public item(String nombre,int x,int y,String m,String c,String u) {
    super(x,y);
    this.nombre=nombre;
    this.x=x;
    this.y=y;

    if (c.equals("0")) coger=false;
    else coger=true;
    this.cogerFrase=c;

    this.mirarFrase=m;

    if (u.equals("0")) usar=false;
    else usar=true;
    this.usarFrase=u;

    imagenItem=cargaImagen("images/objetos/"+this.nombre+".png");
    Animation anim = new Animation();
    anim.addFrame(imagenItem, 100);
    spriteItem = new Sprite(anim);
  }

  public boolean clickeoItem(int x_mouse, int y_mouse){
    Rectangle r=new Rectangle(this.x,this.y,devuelveSprite().getWidth(),devuelveSprite().getHeight());
    return r.intersects(x_mouse,y_mouse, 1,1);
}

  public String devuelveNombre(){
    return nombre;
  }

  public void posicionaItem(int x, int y) {
    this.x=x;
    this.y=y;
  }

  public int devuelveX() {
    return this.x;
  }

  public int devuelveY() {
    return this.y;
  }

  public boolean devuelveCoger(){
    return coger;
  }

  public boolean devuelveUsar(){
    return usar;
  }

  public String devuelveCogerFrase(){
    if (cogerFrase.equals("0")) return "No puedo coger eso.";
    else return cogerFrase;

  }

  public String devuelveUsarFrase(){
    if (usarFrase.equals("0")) return "No puedo usar eso.";
    else return usarFrase;
  }

  public String devuelveMirarFrase(){
    return mirarFrase;
  }

  public Image devuelveImagen(){
    return imagenItem;
  }

  public Sprite devuelveSprite(){
    return spriteItem;
  }

  private Image cargaImagen(String ruta) {
   return new ImageIcon(ruta).getImage();
 }


}
