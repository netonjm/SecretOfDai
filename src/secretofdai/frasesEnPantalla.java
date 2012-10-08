package secretofdai;

import java.awt.Graphics2D;

class frasesEnPantalla {
  private frase[] frasesPantalla;
  private int numFrases;

  public frasesEnPantalla() {
    frasesPantalla=new frase[10];
    numFrases=0;
  }

  public void anadirFrase(frase tmp) {
    if (numFrases>9) numFrases=0;
    frasesPantalla[numFrases++] = tmp;
  }

  public void dibujar(Graphics2D g) {
    for (int i=0;i<9;i++) {

      if (frasesPantalla[i]!=null) {

        g.drawString(frasesPantalla[i].toString(), frasesPantalla[i].devuelveX(), frasesPantalla[i].devuelveY());
        frasesPantalla[i].reducirTiempo();
        if (frasesPantalla[i].devuelveTiempo()<=0) frasesPantalla[i]=null;
      }
      //g.drawString(frase[i],frase[i]. );
    }
  }

}
