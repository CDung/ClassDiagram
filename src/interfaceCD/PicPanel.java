package interfaceCD;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PicPanel extends JPanel{
    private final String fileName;
    
    PicPanel(String fileName){
        this.fileName=fileName;
        setLayout(null);        
    }
      
    @Override
    public void paint(Graphics g) {    
        ImageIcon icon = new ImageIcon(fileName);  
        Dimension d = getSize();
        g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null); 
    }

}
