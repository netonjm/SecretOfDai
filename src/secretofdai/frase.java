package secretofdai;

public class frase {
  private String frase;
  private int color;
  private int x,y;
  private double tiempo;
  public frase() {
  }

  public void modificaFrase(String frase,int x,int y) {
    this.frase=frase;
    this.x=x;
    this.y=y;
    this.tiempo=(frase.length()*1)+100;
  }

  public int devuelveX () {
    return x;
  }
  public int devuelveY () {
      return y;
  }

  public double devuelveTiempo() {
      return tiempo;
  }

  public String toString() {
    return frase;
  }

  public void reducirTiempo () {
    tiempo--;
  }

}
