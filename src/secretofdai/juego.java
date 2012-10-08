package secretofdai;

//import java.awt.Image;
//import java.awt.DisplayMode;
//import java.awt.Graphics2D;
//import javax.swing.ImageIcon;
//import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.media.*;
import javax.media.bean.playerbean.*;

 

class juego {

    /* Creamos TODOS los items en un array o de un fichero, para despues
       poder llamarlos */
    private item lista_items[]=new item[20];
    private portal lista_portales[]=new portal[5];
    private npc lista_personajes[]=new npc[20];
    private habitacion lista_habitaciones[]=new habitacion[5];
    private String lista_animaciones[]=new String[4];

    private jugador lluna;

    public ScreenManager pantalla;

    private int escenario_actual;
    private boolean menu_activo=true;  //variable booleano para controlar si estamos o no en el menu
    private boolean terminado=false;

    private int tiempo=0;

    private Cursor cursor;//atributo cursor
    private int estadoCursor=5;

    frasesEnPantalla frases = new frasesEnPantalla();
    // private Sprite sprite;
    //private Image fondoPantalla;

    private static final DisplayMode POSSIBLE_MODES[] = {
        new DisplayMode(800, 600, 32, 0),
    };

    //creamos la variable mapa de dureza que utilizaremos a lo largo del juego
    //tambien creamos un array tridimensional donde meteremos los valores de la imagen de durezas
    map_get_pixel mapa_durezas=new map_get_pixel();
    private int imagenD[][][];

    //Variables relacionadas con el ratón
    private boolean botonIzqRaton = false;
    private boolean botonDerRaton = false;

    adaptadorRaton raton=new adaptadorRaton(this);

    // Botones del menú principal
    JButton menuNuevoJuego = new JButton();
    JButton menuCargarJuego = new JButton();
    JButton menuSalvarJuego = new JButton();
    JButton menuQuitarJuego = new JButton();

    //Thread de la musica
    musica cancion=new musica();

    String VideoIntro = "file://D:\\Trabajo\\SecretOfDai\\secretofdai\\images\\secretofdai.avi";
    String SoundGame="snd/sound.wav";
    String SoundPam="snd/pam.wav";
    
    public void PlayVideo(String VideoSource)
    {
           try {
               
      Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
 
    
      Player player = Manager.createPlayer(new MediaLocator(VideoSource));
      player.realize();
 
      while (player.getState() != Controller.Realized) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {}
      }
 
      JComponent  m_playerComp = (JComponent) player.getVisualComponent();
      //JPanel m_pnlDraw = new JPanel(new FlowLayout());
      //m_pnlDraw.setOpaque(false);
      //m_pnlDraw.add(new JButton("aaaaaaaaaaaa"));
      
      //setBackground(Color.BLACK);
 
      pantalla.devuelveFrame().getLayeredPane().add(m_playerComp, new Integer(1));
      //pantalla.devuelveFrame().getLayeredPane().add(m_pnlDraw, new Integer(10));
 
      //setLayout(null);
      //add(layeredPane);
 
      player.start();
 
    } catch (Exception e) {
      System.out.println("Error creating player");
      e.printStackTrace();
      return;
    }
    
    }
    
    public juego() {
        /* CONSTRUCTOR PRINCIPAL -------------------------------------------------
             Primero creamos los objetos y los items de todo el juego, posteriormente
             las habitaciones y asignamos qué items,portales y personajes va a tener */
        
        
        escenario_actual=4;
        
        pantalla = new ScreenManager();
        try {
          DisplayMode displayMode = pantalla.findFirstCompatibleMode(POSSIBLE_MODES);
          pantalla.setFullScreen(displayMode);

          
          /*
          Hemos creado en ScreenManager un método que nos devuelve el frame que estamos
          utilizando para nuestro juego, el frame llena la pantalla entera (800x600)
          y tiene un metodo que es addMouseListener que nos permite capturar los
          eventos del ratón.
          */

          pantalla.devuelveFrame().addMouseListener(raton);
          //pantalla.devuelveFrame().addMouseMotionListener(new adaptadorRatonMotion(this));
          
        
          
          //PlayVideo(url);
                
          creaBaseDatos();
          repeticion();
        }
        finally {
          pantalla.restoreScreen();
        }
      } // -------------------------------------------------------------------------

      public void repeticion() {
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        cargarCursor();
        //lista_habitaciones[0].cargaMusica();

        while (!terminado) {
          Graphics2D g = pantalla.getGraphics();
          dibuja_escena(g,escenario_actual);
          g.dispose();
          pantalla.update();
          try {
            Thread.sleep(8);
          }
          catch (InterruptedException ex) { }
        }

      }

        public void creaBaseDatos(){

          /* Este método lo uso para crear todos los items del juego y poder usarlos
               para los escenarios que necesitemos. */
          // Objetos .........

          lista_items[0]=new item("extintor",443,250,"Buaaaa, me siento bombero!","0","0");
          lista_items[1]=new item("cd",600,400,"Pedazo de CD que la flipas!","0","0");
          lista_items[2]=new item("empezar",200,250,"0","0","0");
          lista_items[3]=new item("salir",400,400,"0","0","0");

          lista_portales[0]=new portal(557,118,"puerta1",1);
          lista_portales[1]=new portal(280,93,"puerta2",0);
          lista_portales[2]=new portal(488,59,"puerta3",2);
          lista_portales[3]=new portal(371,57,"puerta4",1);
          lista_portales[4]=new portal(144,493,"puerta5",3);



          // Personajes ........
          lista_personajes[0]=new npc("lluna",300,200,"Mmmmm como me pone!","No gracias, no es mi tipo.","No creo que pueda usar eso.");
          lista_personajes[3]=new npc("jose",300,400,"Hola lluna me pones como las cabras","No me gustas la verdad.","No creo que pueda usar eso.");
          lista_personajes[1]=new npc("lluna",100,200,"Mi otro yo!","0","0");
          lista_personajes[2]=new npc("Victor",270,260,"WOP","WOP2","wop3");

          // Personaje Principal .........
          lluna=new jugador(350,300);

          // Escenarios ........
          lista_habitaciones[0]=new habitacion("pasillo",100,200,0);
          lista_habitaciones[1]=new habitacion("kat",100,400,0);
          lista_habitaciones[2]=new habitacion("puertasalidaclase",410,275,0);
          lista_habitaciones[3]=new habitacion("encuentovictor",97,85,0);
          lista_habitaciones[4]=new habitacion("menu_principal",0,0,1);

          // Ahora añadimos en las habitaciones lo que queremos que haya


          // Pantalla Pasillo
          lista_habitaciones[0].anadirPortal(0);
          lista_habitaciones[0].anadirItem(0);
          lista_habitaciones[1].anadirNPC(3);
          
          // Pantalla Kat
          lista_habitaciones[1].anadirPortal(1);
          lista_habitaciones[1].anadirPortal(2);
          lista_habitaciones[1].anadirItem(1);
          lista_habitaciones[1].anadirNPC(2);

          //lista_habitaciones[2].anadirPortal(3);
          lista_habitaciones[2].anadirPortal(4);

          lista_habitaciones[3].anadirNPC(0);

          // Menu
          lista_habitaciones[4].anadirItem(2);
          lista_habitaciones[4].anadirItem(3);
        }


        public void dibuja_escena (Graphics2D g,int x) {
            // Esta función se encarga de limpiar la pantalla y cargar el contenido del escenario.
            g.setColor(Color.black);

            /*
             Antes de pintar a LLuna en la pantalla comprobamos que no se tenga que mover
             Si se tuviera que mover lo movemos con actualiza movimiento, sino le ponemos
             en el estado de Parado.
             */
            if (!lluna.estaQuieto()) lluna.actualizaMovimiento();
            else {
             if (lluna.devuelveEstadoAnimacion()==1 || lluna.devuelveEstadoAnimacion()==3) lluna.cambiaEstadoAnimacion(0);  // Si está quieto y está con la animacion de andar, lo deja parado.
            }
            //Pintamos la habitación donde nos encontremos
            g.drawImage(lista_habitaciones[x].devuelveFondo(), 0, 0, null);

           // Pintamos todos los Items, Portales y NPC's que existan en esa habitación


           for (int i=0;i<lista_habitaciones[x].devuelveNumPortal();i++) {
             int portal=lista_habitaciones[x].devPortal(i);
             g.drawImage(lista_portales[portal].devuelveImage(), lista_portales[portal].devuelveX(), lista_portales[portal].devuelveY(), null);

             // si atraviesa algun portal de los que hay le lleva a la pantalla en question
             if (lluna.colisionPortal(lista_portales[portal]))  {
               lluna.paraJugador();
               lista_habitaciones[escenario_actual].modificaEntrada(lluna.devuelveX()-40,lluna.devuelveY());
               escenario_actual = lista_portales[portal].devuelveHabitacionEnlace();
               imagenD = ImageLoader.cargaMatriz("images/escenarios/dureza_"+lista_habitaciones[escenario_actual].devuelveNombre()+".jpg");
               lluna.posicionaJugador(lista_habitaciones[escenario_actual].devuelveEntradaX(),lista_habitaciones[escenario_actual].devuelveEntradaY() );

             
               cancion.cambiaMusica(lista_habitaciones[escenario_actual].devuelveNombre());
               cancion.run();
               cancion.start();
               
               //lluna.cambiaFrase("Ohhh!! Estoy en: "+lista_habitaciones[escenario_actual].devuelveNombre());
            }
           }

           for (int i=0;i<lista_habitaciones[x].devuelveNumItems();i++) {
             int item=lista_habitaciones[x].devItem(i);
             g.drawImage(lista_items[item].devuelveImagen(), lista_items[item].devuelveX(), lista_items[item].devuelveY(), null);
           }

           for (int i=0;i<lista_habitaciones[x].devuelveNumNPC();i++) {
                      int persona=lista_habitaciones[x].devNPC(i);
                      g.drawImage(lista_personajes[persona].actualizaAnimacion().getImage(), lista_personajes[persona].devuelveX(), lista_personajes[persona].devuelveY(), null);
                      lista_personajes[persona].dibujarFrase(g);
           }

            // Pintamos a Lluna el ultimo para que aparezca el encima de todos los frames.
            //lluna.devuelveFrase().devuelveX()
            if (lista_habitaciones[escenario_actual].devuelveTipo()==0){
              lluna.dibujarFrase(g);
              g.drawImage(lluna.actualizaAnimacion().getImage(), lluna.devuelveX(), lluna.devuelveY(), null);
            }
            //Añadimos 1, a nuestra unidad de tiempo creada por nosotros.
            tiempo++;
          }

          private void cargarCursor(){
            //Este método es el que nos carga un nuevo cursor para el ratón (NO IMPLEMENTADO)
            Toolkit herramienta=Toolkit.getDefaultToolkit();
            Image img=herramienta.getImage("images/cursores/andar.png");

          // ************ Los siguientes metodos y clases son para el ratón **************
          System.out.println(estadoCursor);
            switch (estadoCursor) {
              case 0:

                //Andar
                img = herramienta.getImage("images/cursores/andar.png");
                break;
              case 1:
                //mirar
                img = herramienta.getImage("images/cursores/mirar.png");
                break;
              case 2:
                //usar - dar
                img = herramienta.getImage("images/cursores/usar.png");
                break;
              case 3:
                //hablar
                img = herramienta.getImage("images/cursores/hablar.png");
                break;
              case 4:
                //coger
                img = herramienta.getImage("images/cursores/coger.png");
                break;
              case 5:
                //coger
                img = herramienta.getImage("images/cursores/normal.png");
                break;

            }

            cursor=herramienta.createCustomCursor(img,new Point(0,0),"cursor");
            pantalla.cambiaCursor(cursor);
          }

          public static void main(String[] args) {
            juego juego = new juego();
          }

          public void mousePressed(MouseEvent ev)   {
          }

          public void mouseDragged(MouseEvent ev) {
          }

          public void mouseMoved(MouseEvent e) {
            //setCursor(cursor);
            //System.out.print(e.getX());
          }

          public void mouseClicked(MouseEvent e) {

            if (e.getButton()==1) {

              switch (estadoCursor) {
                       case 0:
                         //andar
                         if ((lluna.devuelveEstadoAnimacion()==0 || lluna.devuelveEstadoAnimacion()==1)
                             && mapa_durezas.asignar(imagenD,255,0,e)==true) {
                           System.out.println("Dureza Pisable, Moviendo personaje a: Eje X=" + e.getX()+" Eje Y=" + e.getY());

                           if (e.getX()<lluna.devuelveX()) lluna.cambiaEstadoAnimacion(3);
                           else lluna.cambiaEstadoAnimacion(1);

                           lluna.mueveJugador(e.getX()-38 , e.getY() - 160);

                         } else {
                           System.out.println("Dureza NO PISABLE -> Eje X=" + e.getX()+" Eje Y=" + e.getY());
                         }

                         break;
                       case 1://mirar

                         for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumItems();i++) {
                           int item=lista_habitaciones[escenario_actual].devItem(i);

                           if (lluna.colisionItem(lista_items[item]) && raton.devuelveBotonPresionado() && lista_items[item].clickeoItem(e.getX(),e.getY()))  {
                             lluna.cambiaFrase(lista_items[item].devuelveMirarFrase());

                           }
                         }

                         for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumNPC();i++) {
                            int persona=lista_habitaciones[escenario_actual].devNPC(i);
                            if (lluna.colisionNPC(lista_personajes[persona]) && raton.devuelveBotonPresionado() )  {
                              lluna.cambiaFrase(lista_personajes[persona].devuelveMirarFrase() );
                          }
                        }

                         break;
                       case 2:

                         for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumItems();i++) {
                           int item=lista_habitaciones[escenario_actual].devItem(i);
                           if (lluna.colisionItem(lista_items[item]) && raton.devuelveBotonPresionado() && lista_items[item].clickeoItem(e.getX(),e.getY()) )  {
                             lluna.cambiaFrase(lista_items[item].devuelveUsarFrase());
                           }
                         }

                         for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumNPC();i++) {
                           int persona=lista_habitaciones[escenario_actual].devNPC(i);
                           if (lluna.colisionNPC(lista_personajes[persona]) && raton.devuelveBotonPresionado() )  {
                             lluna.cambiaFrase(lista_personajes[persona].devuelveUsarFrase() );
                           }
                         }

                         break;
                       case 3:

                         for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumItems();i++) {
                           int item=lista_habitaciones[escenario_actual].devItem(i);
                           if (lluna.colisionItem(lista_items[item]) && raton.devuelveBotonPresionado() && lista_items[item].clickeoItem(e.getX(),e.getY()) )  {
                             lluna.cambiaFrase("Pero como vas a hablar con un objeto?");
                           }
                         }

                         for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumNPC();i++) {
                           int persona=lista_habitaciones[escenario_actual].devNPC(i);
                           if (lluna.colisionNPC(lista_personajes[persona]) && raton.devuelveBotonPresionado() )  {
                             lista_personajes[persona].conversacion(lluna);
                           }
                         }

                         break;
                       case 4:
                         //coger
                         for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumItems();i++) {
                           int item=lista_habitaciones[escenario_actual].devItem(i);
                           if (lluna.colisionItem(lista_items[item]) && raton.devuelveBotonPresionado() && lista_items[item].clickeoItem(e.getX(),e.getY()))  {
                             lista_habitaciones[escenario_actual].borrarItem(item);
                           }
                         }

                         for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumNPC();i++) {
                           int persona=lista_habitaciones[escenario_actual].devNPC(i);
                           if (lluna.colisionNPC(lista_personajes[persona]) && raton.devuelveBotonPresionado() )  {
                             lluna.cambiaFrase(lista_personajes[persona].devuelveCogerFrase());
                           }
                         }
                         break;
                         case 5:

                           for (int i=0;i<lista_habitaciones[escenario_actual].devuelveNumItems();i++) {
                             int item=lista_habitaciones[escenario_actual].devItem(i);

                             if (lista_items[item].clickeoItem(e.getX(),e.getY())) {
                               if (lista_items[item].devuelveNombre().equals("empezar")) {
                                 //cancion.currentThread().destroy();
                                 //cancion.cambiaMusica(lista_habitaciones[escenario_actual].devuelveNombre());
                                 estadoCursor=0;
                                 cargarCursor();
                                 escenario_actual = 0;
                                 imagenD = ImageLoader.cargaMatriz("images/escenarios/dureza_"+lista_habitaciones[escenario_actual].devuelveNombre()+".jpg");
                                 //lista_habitaciones[escenario_actual].cargaMusicaPantalla();

                                 cancion.cambiaMusica(lista_habitaciones[escenario_actual].devuelveNombre());
                                 cancion.start();

                               }else if (lista_items[item].devuelveNombre().equals("creditos")) {
                                 escenario_actual = 2;
                               } else terminado=true;
                             }

                           }

              }

            } else if (e.getButton()==3) {
              estadoCursor++;
              if (estadoCursor>4) { estadoCursor=0; }
              cargarCursor();
              if (lluna.devuelveEstadoAnimacion()==0) lluna.cambiaEstadoAnimacion(1);
              else lluna.cambiaEstadoAnimacion(0);
            }
          }

          public void mouseReleased(MouseEvent e) {
          }

          public void mouseExited(MouseEvent e) {
          }

          public void mouseEntered(MouseEvent e) {

          }

        }

        class adaptadorRatonMotion
            extends java.awt.event.MouseMotionAdapter {
          juego adaptee;


          adaptadorRatonMotion(juego adaptee) {
            this.adaptee = adaptee;
          }

          public void mouseDragged(MouseEvent e) {
            adaptee.mouseDragged(e);
          }

          public void mouseMoved(MouseEvent e) {
            adaptee.mouseMoved(e);
            //lluna.
          }
        }

        class adaptadorRaton
            extends java.awt.event.MouseAdapter {
          juego adaptee;
          boolean botonPresionado=false;

          adaptadorRaton(juego adaptee) {
            this.adaptee = adaptee;
          }

          public boolean devuelveBotonPresionado(){
            return botonPresionado;
          }

          public void mouseClicked(MouseEvent e) {
            botonPresionado=true;
            adaptee.mouseClicked(e);
          }

          public void mouseReleased(MouseEvent e) {
            botonPresionado=false;
            adaptee.mouseReleased(e);
          }

          public void mousePressed(MouseEvent e) {

            adaptee.mousePressed(e);
          }

          public void mouseExited(MouseEvent e) {
            adaptee.mouseExited(e);
          }

          public void mouseEntered(MouseEvent e) {
            adaptee.mouseEntered(e);
          }
        }

