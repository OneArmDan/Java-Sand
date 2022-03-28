import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    JLabel title = new JLabel("Java 2D");
    Font medFont = new Font("Century", Font.PLAIN, 14);
    JButton selectButton = new JButton("Select");
    
    MainPanel panel = new MainPanel();
    
    public MainFrame(){
        super("Java 2D");

        add(panel);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        this.setLocationRelativeTo(null);
    }
}