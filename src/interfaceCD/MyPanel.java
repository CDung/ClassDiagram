package interfaceCD;

import classDiagram.manageInterface;
import classDiagram.manageObject;
import classDiagram.manageMember;
import classDiagram.manageMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel {
    private List<Rectangle2D> nodes;
    private final List<manageObject> objects;
    private Rectangle2D dragged;
    private Point offset;
    private double[] size;
    MyPanel(List<List<manageObject>> classList) {
        nodes = new ArrayList();
        objects= new ArrayList();
        for(List<manageObject> list:classList)
            for (manageObject my_class : list)
                objects.add(my_class);
        size = new double[objects.size()];
        for(int i=0;i<size.length;i++) 
            size[i]=1.0;
        setBackground(Color.gray);
        
        int maxH=0,maxW=0,maxX=0,maxY=0;
        List<Integer> listH=new ArrayList();
        List<Integer> listW=new ArrayList();
        List<Integer> listX=new ArrayList();
        List<Integer> listY=new ArrayList();
        for(List<manageObject> list:classList)            
            for (manageObject my_class :list){
                int w, h;
                w = 30 + my_class.maxLength().length()*6;
                if (my_class.getListMember().isEmpty() && my_class.getListMethod().isEmpty()) { h = 20*3; }
                else if (my_class.getListMember().isEmpty()) { h = 20*(my_class.getListMethod().size()+2); }
                else if (my_class.getListMethod().isEmpty()) { h = 20*(my_class.getListMember().size()+2); }
                else { h = 20*(my_class.getListMember().size()+my_class.getListMethod().size()+1); }
                if (h>maxH) maxH=h;
                if (w>maxW) maxW=w;
                listH.add(h);
                listW.add(w);
            }
        
        int k=0,i=0,j=0;
        for(List<manageObject> list:classList){
            i=0;
            for (manageObject my_class :list){
                int x=i*(100+maxW)+(maxW-listW.get(k))/2;                
                int y=j*(300+maxH)+(maxH-listH.get(k))/2;
                if(j==0) y=100;
                if(i==0) x=100;
                nodes.add(new Rectangle2D.Float(x, y, listW.get(k),listH.get(k) ));
                if(x>maxX) maxX=x;
                if(y>maxY) maxY=y;
                k++;
                i++;
            }
            j++;
        }        
        setPreferredSize(new Dimension(maxX+maxW+200,maxY+maxH+200)); 

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                for (Rectangle2D node : nodes) {
                    if (node.contains(e.getPoint())) {
                        dragged = node;
                        offset = new Point(node.getBounds().x-e.getX(), node.getBounds().y-e.getY());
                        repaint();
                        break;
                    }
                }
            }
            public void mouseClicked(MouseEvent e){
                int i = 0;
                boolean counter = false;
                Point mouse = e.getPoint();
                for (Rectangle2D node: nodes){
                    if (node.contains(mouse)){
                        counter = true;
                        if (SwingUtilities.isLeftMouseButton(e)){
                            size[i] = size[i]*2;
                            dragged = node;
                            int x = (int)(node.getX()-node.getWidth()/2);
                            int y = (int)(node.getY()-node.getHeight()/2);
                            int w = (int)node.getWidth()*2;
                            int h = (int)node.getHeight()*2;
                            dragged.setFrame(x, y, w, h);
                            repaint();
                            break;
                        }
                        if (SwingUtilities.isRightMouseButton(e)){
                            if (size[i] > 0.125) {
                                size[i] = size[i] / 2;
                                dragged = node;
                                int x = (int) (node.getX() + node.getWidth() / 4);
                                int y = (int) (node.getY() + node.getHeight() / 4);
                                int w = (int) node.getWidth() / 2;
                                int h = (int) node.getHeight() / 2;
                                dragged.setFrame(x, y, w, h);
                                repaint();
                                break;
                            }
                            else {
                                dragged = node;
                                break;
                            }
                        }
                    }
                    else{
                        counter = false;
                    }
                    i++;
                }
                if (counter == false){
                    int j = 0;
                    for (Rectangle2D n: nodes){
                        if (SwingUtilities.isLeftMouseButton(e)){
                            size[j] = size[j]*2;
                            dragged = n;
                            int x = (int) (n.getX() - (mouse.x - n.getX()));
                            int y = (int) (n.getY() - (mouse.y - n.getY()));
                            int w = (int)n.getWidth()*2;
                            int h = (int)n.getHeight()*2;
                            dragged.setFrame(x, y, w, h);
                            repaint();
                        }
                        if (SwingUtilities.isRightMouseButton(e)){
                            if (size[j] > 0.125) {
                                size[j] = size[j] / 2;
                                dragged = n;
                                int x = (int) (n.getX() + (mouse.x - n.getX())/2);
                                int y = (int) (n.getY() + (mouse.y - n.getY())/2);
                                int w = (int) n.getWidth() / 2;
                                int h = (int) n.getHeight() / 2;
                                dragged.setFrame(x, y, w, h);
                                repaint();
                            }
                        }
                        j++;
                    }
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (dragged != null) {
                    repaint();
                }
                dragged = null;
                offset = null;
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragged != null && offset != null) {
                    Point to = e.getPoint();
                    to.x += offset.x;
                    to.y += offset.y;
                    Rectangle bounds = dragged.getBounds();
                    bounds.setLocation(to);
                    dragged.setFrame(bounds);
                    repaint();
                }
            }
        });
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        //Draw the inheritance relationship
        for (int i = 0; i < nodes.size(); i++) {
            manageObject con = objects.get(i).getRel().getE();
            if (con != null) 
                drawInheritance(nodes.get(i), nodes.get(objects.indexOf(con)), g2d);                      
        }
        //Draw the realization relationship
        for (int i = 0; i < nodes.size(); i++) {
            List<manageObject> cacCon = objects.get(i).getRel().getI();
            if (cacCon != null)
                for(manageObject con:cacCon)                    
                    drawRealization(nodes.get(i), nodes.get(objects.indexOf(con)), g2d);            
        }
        //Draw the association relationship
        for (int i = 0; i < nodes.size(); i++) {
            List<manageObject> cacCon = objects.get(i).getRel().getA();
            if (cacCon != null)
                for(manageObject con:cacCon)                    
                    drawAssociation(nodes.get(i), nodes.get(objects.indexOf(con)), g2d);            
        }
        //Draw the nodes
        int i = 0;
        for (Rectangle2D node : nodes) {
            drawNode(i, node, g2d);
            i++;
        }
        g2d.dispose();
    }
    private void drawNode(int i, Rectangle2D node, Graphics2D g) {
        int x = node.getBounds().x;
        int y = node.getBounds().y;
        int w = node.getBounds().width;
        //Draw the white base
        g.setColor(Color.white);
        g.fill(node);
        //Draw the black border
        g.setColor(Color.black);
        g.draw(node);
        //Draw the blue highlight if clicked
        if (node == dragged) {
            g.setColor(Color.blue);
            g.draw(node);
        }
        //Draw the text
        g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(12*size[i])));
        g.setColor(Color.black);
        if (objects.get(i) instanceof manageInterface){
            g.setFont(new Font("TimesRoman", Font.ITALIC, (int)(12*size[i])));
        }
        g.drawString(objects.get(i).getName(), (int) (x + 15 * size[i]), (int) (y + 15 * size[i]));
        g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(12*size[i])));
        g.drawLine(x, (int)(y + 20*size[i]),x + w, (int)(y + 20*size[i]));
        int j = 1;
        g.setColor(Color.black);
        for (manageMember member : objects.get(i).getListMember()){
            g.drawString(member.getName(), (int)(x + 15*size[i]), (int)(y + (15 + j*20)*size[i]));
            j++;
        }
        g.setColor(Color.black);
        if (objects.get(i).getListMember().size() == 0) {
            j++;
        }
        g.drawLine(x,y + (int)((20 + (j - 1)*20)*size[i]),x + w,(int)(y + (20 + (j - 1)*20)*size[i]));
        for (manageMethod method : objects.get(i).getListMethod()){
            g.drawString(method.getName(),(int)(x + 15*size[i]),(int)(y + (15 + j*20)*size[i]));
            j++;
        }
    }
    private void drawLine(Rectangle2D node1, Rectangle2D node2, Graphics2D g, String relationship) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
        Point from = node1.getBounds().getLocation();
        Point to = node2.getBounds().getLocation();
        switch (relationship) {
            case "Realization":
                g2d.setStroke(dashed);
                from.x += node1.getWidth() / 2;
                to.x += node2.getWidth() / 2;
                to.y += node2.getHeight();
                g2d.drawLine(from.x, from.y, from.x, from.y - 40);
                from.y -= 40;
                g2d.drawLine(to.x, to.y + 17, to.x, to.y + 40);
                to.y += 40;
                break;
            case "Inheritance":
                from.x += node1.getWidth() / 4;
                to.x += node2.getWidth() / 4;
                to.y += node2.getHeight();
                g2d.drawLine(from.x, from.y, from.x, from.y - 40);
                from.y -= 40;
                g2d.drawLine(to.x, to.y + 17, to.x, to.y + 40);
                to.y += 40;
                break;
            default:
                from.x += node1.getWidth() * 3 / 4;
                to.x += node2.getWidth() * 3 / 4;
                to.y += node2.getHeight();
                g2d.drawLine(from.x, from.y, from.x, from.y - 40);
                from.y -= 40;
                g2d.drawLine(to.x, to.y + 17, to.x, to.y + 40);
                to.y += 40;
                break;
        }
        if (from.y == to.y) {
            g2d.drawLine(from.x, from.y, to.x, to.y);
        }
        else if (from.y > to.y) {
            g2d.drawLine(from.x, from.y, from.x, (from.y + to.y)/2);
            g2d.drawLine(to.x, (from.y + to.y)/2, to.x, to.y);
            g2d.drawLine(from.x, (from.y + to.y)/2, to.x, (from.y + to.y)/2);
        }
        else {
            int mid;
            if (node1.getX() < node2.getX()) {
                mid = (int) ((node1.getX()+node1.getWidth()+node2.getX())/2);
            }
            else {
                mid = (int) ((node2.getX()+node2.getWidth()+node1.getX())/2);
            }
            g2d.drawLine(from.x, from.y, mid, from.y);
            g2d.drawLine(mid, to.y, to.x, to.y);
            g2d.drawLine(mid, from.y, mid, to.y);
        }
    }
    private void drawInheritance(Rectangle2D node1, Rectangle2D node2, Graphics2D g) {
        g.setColor(Color.BLACK);
        Point from = node1.getBounds().getLocation();
        from.x += node1.getWidth() / 4;
        Point to = node2.getBounds().getLocation();
        to.x += node2.getWidth() / 4;
        to.y += node2.getHeight();
        //Draw the arrow
        AffineTransform old = g.getTransform();
        g.translate(to.x, to.y);
        g.rotate(Math.toRadians(30));
        g.drawLine(0, 0, 0, 20);
        g.translate(0, 20);
        g.rotate(Math.toRadians(-120));
        g.drawLine(0, 0, 0, 20);
        g.translate(0, 20);
        g.rotate(Math.toRadians(-120));
        g.drawLine(0, 0, 0, 20);
        g.setTransform(old);
        //Draw the connected line
        drawLine(node1, node2, g, "Inheritance");
    }
    private void drawRealization(Rectangle2D node1, Rectangle2D node2, Graphics2D g) {
        g.setColor(Color.BLACK);
        Point from = node1.getBounds().getLocation();
        from.x += node1.getWidth() / 2;
        Point to = node2.getBounds().getLocation();
        to.x += node2.getWidth() / 2;
        to.y += node2.getHeight();
        //Draw the arrow
        AffineTransform old = g.getTransform();
        g.translate(to.x, to.y);
        g.rotate(Math.toRadians(30));
        g.drawLine(0, 0, 0, 20);
        g.translate(0, 20);
        g.rotate(Math.toRadians(-120));
        g.drawLine(0, 0, 0, 20);
        g.translate(0, 20);
        g.rotate(Math.toRadians(-120));
        g.drawLine(0, 0, 0, 20);
        g.setTransform(old);
        //Draw the connected line
        drawLine(node1, node2, g, "Realization");
    }
    private void drawAssociation(Rectangle2D node1, Rectangle2D node2, Graphics2D g) {
        g.setColor(Color.BLACK);
        Point from = node1.getBounds().getLocation();
        from.x += node1.getWidth() * 3 / 4;
        Point to = node2.getBounds().getLocation();
        to.x += node2.getWidth() * 3 / 4;
        to.y += node2.getHeight();
        //Draw the arrow
        AffineTransform old = g.getTransform();
        g.translate(to.x, to.y);
        g.rotate(Math.toRadians(30));
        g.drawLine(0, 0, 0, 20);
        g.rotate(Math.toRadians(-30));
        g.drawLine(0, 0, 0, 17);
        g.rotate(Math.toRadians(-30));
        g.drawLine(0, 0, 0, 20);
        g.setTransform(old);
        //Draw the connected line
        drawLine(node1, node2, g, "Association");
    }
}