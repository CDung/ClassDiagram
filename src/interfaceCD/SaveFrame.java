package interfaceCD;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveFrame extends JFrame implements ActionListener{
    private final String imagePath = System.getProperty("user.dir")+"\\image\\";
    private String temp;
    JTextField name = new JTextField("Class Diagram",25);
    String [] types = {"png","jpg","jpeg","gif"}; 
    JComboBox modify = new JComboBox(types);
    JTextField path = new JTextField(imagePath,25);
    JLabel nameLabel = new JLabel("Name");
    JLabel modifyLabel = new JLabel("Modify");
    JLabel pathLabel = new JLabel("Path File");
    JButton open = new JButton ("Change");
    JButton oke = new JButton ("Ok");
    JPanel p;
    
    SaveFrame(JFrame parent,JPanel p){
        super("Save Image");
        this.p=p;
        setSize(450,150);
        setResizable(false);
        setLocationRelativeTo(parent);
        setIconImage(new ImageIcon(imagePath+"iconApp.png").getImage());
        setLayout(new FlowLayout());
        add(nameLabel);
        add(name);
        add(modifyLabel);
        modify.setLocation(modifyLabel.getX(), modifyLabel.getY());
        add(modify);
        add(pathLabel);
        add(path);
        add(open); 
        add(oke);
        open.addActionListener(this) ;
        oke.addActionListener(this) ;
        modify.addActionListener(this);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open){
            JFileChooser openFile = new JFileChooser();  
            openFile.setDialogTitle("Save Image");
            openFile.setCurrentDirectory(new java.io.File("."));
            openFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            openFile.addChoosableFileFilter(new FileNameExtensionFilter("image file", "png","jpg","jpeg","gif"));
            openFile.setAcceptAllFileFilterUsed(true);
            int i=openFile.showOpenDialog(this);
            if(i==JFileChooser.APPROVE_OPTION){
                File file=openFile.getSelectedFile();
                
                if (file.isDirectory()){
                    path.setText(file.getPath()+"\\");
                }
                else {
                    path.setText(file.getParentFile().getPath()+"\\");
                    file.delete();
                }             
            }   
        }
        if (e.getSource()==oke){
            BufferedImage img = new BufferedImage(p.getWidth(),p.getHeight(),BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            p.print(g2);
            try {
                    ImageIO.write(img, (String)modify.getItemAt(modify.getSelectedIndex()), new File(path.getText()+name.getText()+"."+modify.getItemAt(modify.getSelectedIndex())));
                } 
            catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            setVisible(false);
        }
    }
    
}
