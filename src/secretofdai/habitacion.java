package secretofdai;

import java.awt.Image;
import javax.swing.ImageIcon;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class habitacion {
  //Donde aparace el personaje en la habitación
  private int entrada_x;
  private int entrada_y;

  // 0=normal,1=menu (con cursor default y sin personaje),2=animacion (avi)
  private int tipo;

  private String nombre;
  Image fondo; //Sprite de fondo

  int personajes[]=new int[8];
  int items[]=new int[8];
  int portales[]=new int[4];

  int num_item;
  int num_npc;
  int num_portal;

  public habitacion(String nombre,int x, int y,int tipo) {
    this.nombre=nombre;
    num_npc=0;
    num_portal=0;
    num_npc=0;
    fondo = loadImage("images/escenarios/"+nombre+".jpg");
    modificaEntrada (x,y);
    this.tipo=tipo;
  }

  public String devuelveNombre() {
    return nombre;
  }

  public Image devuelveFondo() {
    return fondo;
  }

  public int devuelveTipo() {
    return tipo;
  }

  public void anadirItem(int nuevo) {
    items[num_item++]=nuevo;
  }

  public void anadirNPC(int nuevo) {
    personajes[num_npc++]=nuevo;
  }

  public void anadirNPC(int nuevo,npc tmp,int x, int y) {
    personajes[num_npc++]=nuevo;
    tmp.posicionaNPC(x,y);
  }

  public void anadirPortal(int nuevo) {
    portales[num_portal++]=nuevo;
  }

  public void borrarNPC(int num) {
    if (num>personajes.length) System.out.print("Excepcion!xD");
    else {
      organizarNPC();
      num_item--;
    }
  }

  // Devuelve Un numero de Item, NPC o Portal de una posicion del array
  public int devItem(int num) {
    return items[num];
  }

  public int devNPC(int num) {
    return personajes[num];
  }

  public int devPortal(int num) {
  return portales[num];
}

  // Devuelve Numero de NPC's, Portales e Items que tiene la clase
  public int devuelveNumItems() {
    return num_item;
  }

  public int devuelveEntradaX() {
    return this.entrada_x;
  }

  public int devuelveEntradaY() {
    return this.entrada_y;
  }

  public void modificaEntrada(int x,int y){
    this.entrada_x=x;
    this.entrada_y=y;
  }

  public int devuelveNumNPC() {
    return num_npc;
  }

  public int devuelveNumPortal() {
    return num_portal;
  }

  //metodo para organizar el array de npc's cuando se quiten del mapa
  public void organizarNPC() { }
  //metodo para organizar el array de items en el escenario's cuando se cojan del mapa

  public void borrarItem(int num) {

    for (int i=num;i<items.length;i++) {
      if (i!=items.length-1) items[i]=items[i+1];
    }
    num_item--;
  }

  //metodo para organizar el array de Portales, aunque yo si se quitaran los ocultaria.
  public void organizarPortales() { }

  private Image loadImage(String fileName) {
    return new ImageIcon(fileName).getImage();
}

  public void cargaMusicaPantalla(){
    try {
      SimpleSoundPlayer sound = new SimpleSoundPlayer("snd/"+nombre+".wav");
      InputStream stream = new ByteArrayInputStream(sound.getSamples());
      sound.play(stream);
    }catch (Exception a){};

  }

}
