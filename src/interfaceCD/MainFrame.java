package interfaceCD;

import classDiagram.handllingInput;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainFrame extends JFrame implements ActionListener{
    private JButton bStart;
    private JPanel p;
    private JScrollPane q;
    private handllingInput h;
    private final String imagePath = System.getProperty("user.dir")+"\\image\\";
    private final JMenuBar menuBar=new JMenuBar(); 
    private final JMenu menuFile=new JMenu ("File");
    private final JMenu menuEdit=new JMenu ("Edit");
    private final JMenu menuHelp=new JMenu ("Help");
    private final JMenuItem itemNew=new JMenuItem("New Java Source");
    private final JMenuItem itemOpen=new JMenuItem("Open Image");
    private final JMenuItem itemSave=new JMenuItem("Save Image");
    private final JMenuItem iteamCheck=new JMenuItem("Automatic Sort");
    private final JMenuItem itemContact=new JMenu("Contact Us");
    private final JMenuItem itemContact1=new JMenuItem("Phương Thảo");
    private final JMenuItem itemContact2=new JMenuItem("Chí Dũng");
    private final JMenuItem itemContact3=new JMenuItem("Thanh Bình");
    private final JMenuItem itemInstructions = new JMenuItem("Instructions");

    
    public MainFrame(){
        super("Class Diagram");
        init();
        }
    
    private void init(){
        setBounds(50,0,1200,700); 
        setIconImage(new ImageIcon(imagePath+"iconApp.png").getImage());
        
        itemNew.addActionListener(this);
        itemOpen.addActionListener(this);
        itemSave.addActionListener(this);
        iteamCheck.addActionListener(this);
        itemContact1.addActionListener(this);
        itemContact2.addActionListener(this);
        itemContact3.addActionListener(this);
        itemInstructions.addActionListener(this);
        bStart=new JButton ("",new ImageIcon(new ImageIcon(imagePath+"start.jpg").getImage().getScaledInstance(180,60,Image.SCALE_SMOOTH)));          
        bStart.setBounds(510,280,180,60);
        bStart.addActionListener(this);
        
        menuFile.add(itemNew);
        menuFile.addSeparator();      
        menuFile.add(itemOpen);
        menuFile.addSeparator();
        menuFile.add(itemSave);
        menuEdit.add(iteamCheck);
        itemContact.add(itemContact1);
        itemContact.add(itemContact2);
        itemContact.add(itemContact3);
        menuHelp.add(itemContact);
        menuHelp.addSeparator();
        menuHelp.add(itemInstructions);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
        p=new PicPanel(imagePath+"background.jpg");
        p.add(bStart);
        add(p);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setResizable(false);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {  
        if(e.getSource()==bStart || e.getSource()== itemNew){
            h=new handllingInput();
            JFileChooser openFile = new JFileChooser();            
            openFile.setDialogTitle("Open Java Source");
            openFile.setCurrentDirectory(new java.io.File("."));         
            openFile.setMultiSelectionEnabled(rootPaneCheckingEnabled);
            openFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            openFile.addChoosableFileFilter(new FileNameExtensionFilter("java file", "java"));
            openFile.setAcceptAllFileFilterUsed(true);
            int i=openFile.showOpenDialog(this);
            if(i==JFileChooser.APPROVE_OPTION){
                try {                    
                    h.set(openFile.getSelectedFiles());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (p!=null) remove(p);
                if (q!=null) remove(q);
                p=new MyPanel(h.get());
                q=new JScrollPane(p);              
                add(q);
                setResizable(true);
                setVisible(rootPaneCheckingEnabled);
            }            
        }
        if(e.getSource()==itemOpen){
            JFileChooser openFile=new JFileChooser();  
            openFile.setDialogTitle("Open Image");
            openFile.setCurrentDirectory(new java.io.File("."));
            openFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
            openFile.addChoosableFileFilter(new FileNameExtensionFilter("image file", "png","jpg","jpeg","gif"));
            openFile.setAcceptAllFileFilterUsed(true);
            int i=openFile.showOpenDialog(this);
            if(i==JFileChooser.APPROVE_OPTION){
                if (p!=null) remove(p);
                if (q!=null) remove(q);
                p=new PicPanel(openFile.getSelectedFile().getPath());
                add(p);
                setResizable(true);
                setVisible(rootPaneCheckingEnabled);
            }            
        }
        
        if (e.getSource() == itemSave){
            new SaveFrame(this,p);
        }
        
        if(e.getSource() ==itemContact1){
            URI uri = null; 
            try {
                uri = new URI("https://www.facebook.com/Thalia153");
            } catch (URISyntaxException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                java.awt.Desktop.getDesktop().browse(uri);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(e.getSource() ==itemContact2){
            URI uri = null; 
            try {
                uri = new URI("https://www.facebook.com/nguyendung108108");
            } catch (URISyntaxException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                java.awt.Desktop.getDesktop().browse(uri);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(e.getSource() ==itemContact3){
            URI uri = null; 
            try {
                uri = new URI("https://www.facebook.com/profile.php?id=100000959632773");
            } catch (URISyntaxException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                java.awt.Desktop.getDesktop().browse(uri);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(e.getSource()==iteamCheck){
            if (p!=null) remove(p);
            if (q!=null) remove(q);
            p=new MyPanel(h.get());
            q=new JScrollPane(p);              
            add(q);
            setResizable(true);
            setVisible(rootPaneCheckingEnabled);            
        }
        
        if (e.getSource() == itemInstructions){
            if (p!=null) remove(p);
            if (q!=null) remove(q);
            p=new PicPanel(imagePath+"instructions.png");              
            add(p);
            setResizable(true);
            setVisible(rootPaneCheckingEnabled); 
        }
    }     
}   
   
    
        

