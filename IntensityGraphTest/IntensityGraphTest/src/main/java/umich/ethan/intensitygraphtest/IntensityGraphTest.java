package umich.ethan.intensitygraphtest;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
 
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.epics.graphene.Cell2DDataset;
import org.epics.graphene.Cell2DDatasets;
import java.awt.image.BufferedImage;

import org.epics.graphene.GraphBuffer; 
import org.epics.graphene.IntensityGraph2DRenderer; 
import org.epics.util.array.ArrayDouble;
import org.epics.util.stats.Ranges;

 
public class IntensityGraphTest extends JFrame 
{
     final public static void main( String [] args ) {
	SwingUtilities.invokeLater( new Runnable() {
	    
	    @Override
	    public void run() {
		new IntensityGraphTest();
	    }
	});
    }
     
     private double[] getData(int numRand){
        
     Random ran=new Random(0); 
     double data[] = new double[numRand*numRand];
        for (int i = 0; i < (numRand * numRand); i++) {
            data[i] = ran.nextGaussian();
        }
        return data; 
     
     
     }
      
        
     IntensityGraph2DRenderer renderer; 
     BufferedImage image; 
     GraphBuffer graphBuffer; 
     
     
     public IntensityGraphTest(){
         
        image= new BufferedImage(500, 500, BufferedImage.TYPE_3BYTE_BGR); 
        renderer=new IntensityGraph2DRenderer(500, 500); 
        int numRand=10; 
        ArrayDouble dataList = new ArrayDouble(getData(numRand)); 
        Cell2DDataset data = Cell2DDatasets.linearRange(dataList, Ranges.range(0, numRand), numRand, Ranges.range(0, numRand), numRand);
        
        MyCanvas c = new MyCanvas();
	setContentPane( c );
	setVisible( true );
	setSize( 500 , 500 );
	setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        graphBuffer=new GraphBuffer(renderer); 
        renderer.draw(graphBuffer, data);
     }
     
      private class MyCanvas extends JPanel {
	
	@Override
	public void paintComponent( Graphics g ) {
	    Graphics2D g2d = (Graphics2D) g;
	    super.paintComponent( g2d );
           
	    if (  graphBuffer.getImage()!= null ) {
                
		g2d.drawImage(  graphBuffer.getImage() , 0 , 0 ,  graphBuffer.getImage().getWidth() ,  graphBuffer.getImage().getHeight() , null );
	    }
	}
    }
     
     
}
