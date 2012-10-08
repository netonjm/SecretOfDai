package secretofdai;

import java.lang.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

public class ImageLoader extends JFrame {
    BufferedImage img;
    int width = 200;
    int height = 200;
    boolean active = true;

    public ImageLoader() {
        //frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Imagen");
        pack();
        //setResizable(false);
        setVisible(true);
        addWindowListener( new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            active = false;
            setVisible(false);
          }
        });
    }


   public static void pintaMatriz(int imagen[][][]) {
      ImageLoader image = new ImageLoader();
      image.createImage(imagen);
      try {
        while(image.active) {
          image.repaint();
          Thread.sleep(500l);
        }
      } catch(Exception e) {
        e.printStackTrace();
      }
      image.dispose();
   }

   public static int[][][] cargaMatriz(String file) {
      BufferedImage img;
      try {
        img = ImageLoader._loadImage(file);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      System.out.println(img.getWidth()+"x"+img.getHeight());
      int[][][] matriz = new int[img.getWidth()][img.getHeight()][img.getRaster().getNumBands()];
      for (int i = 0; i < img.getWidth(); i++) {
        for (int j = 0; j < img.getHeight(); j++) {
          img.getRaster().getPixel(i,j,matriz[i][j]);
        }
      }
      return matriz;

   }

    public boolean createImage() {
        try {
          img = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
          for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
              int[] color = {(i*256)/width,0,0};
              img.getRaster().setPixel(i,j,color);
            }
          }
          setSize(width, height);
        } catch (Exception e) {
          e.printStackTrace();
          return false;
        }
        return true;
    }

    public boolean createImage(int[][][] buffer) {
        try {
          width = buffer.length;
          height = (buffer.length>0)?buffer[0].length:0;
          img = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
          for (int w = 0; w < Math.min(buffer.length,img.getWidth()); w++) {
            for (int h = 0; h < Math.min(buffer[w].length,img.getHeight()); h++) {
              img.getRaster().setPixel(w,h,buffer[w][h]);
            }
          }
          setSize(width, height);
        } catch (Exception e) {
          e.printStackTrace();
          return false;
        }
        return true;
    }

    public boolean loadImage(String file) {
        try {
          img = ImageLoader._loadImage(file);
          setSize(width, height);
        } catch (Exception e) {
          e.printStackTrace();
          return false;
        }
        return true;
    }

    public void update(Graphics g) {
          paint(g);
    }

    public void paint(Graphics g) {
        if (img != null) {
          g.drawImage(img,0,0,this);
        }
    }

    public static BufferedImage _loadImage(String s) throws Exception {
        Frame myImageObserver = new Frame();
        MediaTracker mediatracker = new MediaTracker(myImageObserver);
        Image image = Toolkit.getDefaultToolkit().getImage(s);
        mediatracker.addImage(image, 0);
        mediatracker.waitForAll();
        myImageObserver = null;
        return toBufferedImage(image);
    }

    // This method returns a buffered image with the contents of an image
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;

        // Create a buffered image using the default color model
        int type = BufferedImage.TYPE_3BYTE_BGR;
        bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

}
