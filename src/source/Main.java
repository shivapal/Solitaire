package source;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {
	
	private static int x;
	private static int y;
	private static int counter;

	public static void runGUI(){
		JFrame frame = new JFrame("did this work?");
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	    
		//code from https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
	    frame.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				x = arg0.getX();
				y= arg0.getY();
				counter++;
				
				System.out.println(arg0.getX()+","+arg0.getY()+","+counter);
				//retPanel.removeAll();
				//retPanel.add(getBakerBoard());
				//retPanel.setVisible(false);
				//retPanel.setVisible(true);
				
				frame.revalidate();
				frame.repaint();
								
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
	
	}
	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runGUI();
            }
        });
		
		

	}

	
	
}
