/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ethan.grapheneTest.graphtest;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;
import org.epics.graphene.AxisRanges;
 

import org.epics.graphene.BubbleGraph2DRenderer;
import org.epics.graphene.Point3DWithLabelDataset;
import org.epics.graphene.Point3DWithLabelDatasets;
import org.epics.util.array.ArrayDouble;
/**
 *
 * @author YifengYang
 */
public class Main extends JFrame{
    
    final public static void main( String [] args ) {
	SwingUtilities.invokeLater( new Runnable() {
	    
	    @Override
	    public void run() {
		new Main();
	    }
	});
    }
    
    
   private Point3DWithLabelDataset defaultDataset( ) {
       Random rand = new Random(System.currentTimeMillis());
      
        
        int size = 50;
        ArrayDouble x = new ArrayDouble(new double[size], false);
        ArrayDouble y = new ArrayDouble(new double[size], false);
        ArrayDouble z = new ArrayDouble(new double[size], false);
        String[] labelSet = new String[]{"First", "Second", "Third", "Fourth", "Fifth"};
        List<String> labels = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            x.setDouble(i, rand.nextGaussian());
            y.setDouble(i, rand.nextGaussian());
            z.setDouble(i, 5.0 + rand.nextGaussian());
            labels.add(labelSet[rand.nextInt(labelSet.length)]);
            
        }

        Point3DWithLabelDataset data = Point3DWithLabelDatasets.build(x, y, z, labels);
         
        return data;
        
    }
    
    BubbleGraph2DRenderer renderer; 
    Point3DWithLabelDataset data=defaultDataset(); 
    BufferedImage image;
    int count=0;
    
    public Main(){
         
        
        image = new BufferedImage(600, 300, BufferedImage.TYPE_3BYTE_BGR);
        renderer = new BubbleGraph2DRenderer(600, 300);
        
	 
	MyCanvas c = new MyCanvas();
	setContentPane( c );
	
	setVisible( true );
	setSize( 600 , 300 );
	setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	
	//revalidate();
	//repaint();
	 
	Timer t = new Timer();
    
        t.scheduleAtFixedRate( new TimerTask() {

	    @Override
	    public void run() {
		
                 
                 
                data=defaultDataset();
		renderer.draw((Graphics2D)image.getGraphics(), data);
		 
		Main.this.repaint();
                 
                
	    }
	} , 1000 , 1000 );
    }
    
   private class MyCanvas extends JPanel {
	
	@Override
	public void paintComponent( Graphics g ) {
	    Graphics2D g2d = (Graphics2D) g;
	    super.paintComponent( g2d );
	    if ( image != null ) {
                
		g2d.drawImage( image , 0 , 0 , image.getWidth() , image.getHeight() , null );
	    }
	}
    }
   
}
