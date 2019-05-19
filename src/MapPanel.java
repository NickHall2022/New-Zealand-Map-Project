import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MapPanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {
	protected BufferedImage map;
	private boolean hasZoomed;
	private double scale;
	private double priorScale;
	private double xTranslation;
	private double yTranslation;
	private double xDistance;
	private double yDistance;
	private boolean hasDragged;
	private boolean hasReleased;
	private Point origin;
	
	public MapPanel(BufferedImage map) {
		this.map = map;
		this.scale = 0.235;
		this.priorScale = 0.235;
		this.hasZoomed = true;
		this.hasDragged = false;
		this.hasReleased = false;
		this.xTranslation = 0.0;
		this.yTranslation = 0.0;
		this.xDistance = 0.0;
		this.yDistance = 0.0;
		this.origin = MouseInfo.getPointerInfo().getLocation();
		this.setBounds(10, 30, 700, 880);
		AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(scale, scale);
		addMouseWheelListener(this);
	    addMouseMotionListener(this);
	    addMouseListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        if (scale < 0.22) {
    		this.scale = 0.22;
    	    this.priorScale = 0.22;
        } else if (scale > 2.0) {
    		this.scale = 2.0;
    	    this.priorScale = 2.0;
    	}
		if (this.hasZoomed) {
			AffineTransform affineTransform = new AffineTransform();
            this.xTranslation = (scale/priorScale) * (this.xTranslation) + (1 - scale/priorScale) * (MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX());
            this.yTranslation = (scale/priorScale) * (this.xTranslation) + (1 - scale/priorScale) * (MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY());
            affineTransform.translate(xTranslation, yTranslation);
            affineTransform.scale(scale, scale);
            priorScale = scale;
            g2.transform(affineTransform);
            hasZoomed = false;
		}
		
		if (hasDragged) {
	            AffineTransform affineTransform = new AffineTransform();
	            affineTransform.translate(xTranslation + xDistance, yTranslation + yDistance);
	            affineTransform.scale(scale, scale);
	            g2.transform(affineTransform);

	            if (hasReleased) {
	                xTranslation += xDistance;
	                yTranslation += yDistance;
	                hasDragged = false;
	            }

	       }
		 
        g2.drawImage(map, 0, 0, this);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		   Point current = arg0.getLocationOnScreen();
	       xDistance = current.x - origin.x;
	       yDistance = current.y - origin.y;
	       hasDragged = true;
	       repaint();		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.hasReleased = false;
		this.origin = MouseInfo.getPointerInfo().getLocation();		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.hasReleased = true;
		repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.hasZoomed = true;

        if (e.getWheelRotation() < 0) {
            this.scale *= 1.1;
            this.repaint();
        }

        if (e.getWheelRotation() > 0) {
            this.scale /= 1.1;
            this.repaint();
        }
	}

}
