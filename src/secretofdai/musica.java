package secretofdai;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class musica {
    
  private String pantalla;
  private SimpleSoundPlayer sound;
  private InputStream stream;

  public musica() {

  }
 
  public void Stop() {
   sound.stop();
  }

  public void Play(String nombre) {
    pantalla=nombre;
    sound = new SimpleSoundPlayer("snd/"+pantalla+".wav");
    stream = new ByteArrayInputStream(sound.getSamples());
     try {
      sound.play(stream);
    }catch (Exception a){
    };

    }
 
 
  }
