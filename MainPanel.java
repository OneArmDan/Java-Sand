import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MainPanel extends JPanel implements ActionListener {
    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    static final int PIX_SIZE = 4;
    static final int GAME_PIX = (WIDTH*HEIGHT)/PIX_SIZE;
    static final int DELAY = 0;
    
    boolean isPress = false;
    int mouseX;
    int mouseY;
    Timer timer;

    Board board = new Board();
    Board tboard = new Board();

    long startTick = 0;
    long endTick = 0;
    long between = 0;
    

    //constructor
    MainPanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        addMouseMotionListener(new MyMouseMotionAdapter());
        addMouseListener(new MyMouseAdapter());
        start();
    }
    //setters
    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }
    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }
    //getters
    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }
    //methods
    public void start(){
        timer = new Timer(DELAY,this);
        timer.start();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        
        
        

        if (isPress) board.insertParticle(mouseX, mouseY, new Particle(Color.BLUE, "bluethang", 1));

        drawBoard(g);
        
        g.setColor(Color.black);
        drawGrid(g);

        g.setColor(Color.white);
        g.drawString("X: " + mouseX + ", Y: " + mouseY, mouseX, mouseY); //mouse coords on cursor
        drawFPSCounter(g);
        
        for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                Particle element = board.getElement(x, y);
                if((element.getId() == 1 && y < Board.BOARD_HEIGHT - 1) && board.getElement(x, y + 1).getId() == 0) {
                    tboard.downOne(x, y, new Particle(Color.black, "air", 0), new Particle(Color.BLUE, "bluethang", 1));
                }
            }
        }

        for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                board.setBoard(x, y, tboard.getElement(x, y));
            }
        }
        
        
    }


    public void drawFPSCounter(Graphics g) {
        startTick = System.currentTimeMillis();
        between = (startTick + 1 - endTick);
        g.drawString("fps: " + (1000/between), 10, 10);
        endTick = System.currentTimeMillis();
    }

    public void drawBoard(Graphics g) {
        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                g.setColor(board.getElement(j, i).getColor());
                g.fillRect(j * PIX_SIZE, i * PIX_SIZE, PIX_SIZE, PIX_SIZE);
                
            }
        }
    }

    public void drawGrid(Graphics g) {
        for (int i = 0; i < HEIGHT/PIX_SIZE; i++) {
            g.drawLine(i*PIX_SIZE, 0, i*PIX_SIZE, HEIGHT);
            g.drawLine(0, i*PIX_SIZE, WIDTH, i*PIX_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        repaint();
    }



    //User Input
  

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_Q){}
        }
    }

    public class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            board.insertParticle(mouseX, mouseY, new Particle(Color.BLUE, "bluethang", 1));
            isPress = true;
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            isPress = false;
        }
    }

    public class MyMouseMotionAdapter extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            setMouseX(e.getX());
            setMouseY(e.getY());
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            if (isInBounds(mouseX, mouseY)){
                setMouseX(mouseX);
                setMouseY(mouseY);
            }
            //System.out.println(board.boardToString());
        }
        public static boolean isInBounds(int x, int y) {
            if(x > WIDTH - PIX_SIZE || x < 0 || y > HEIGHT - PIX_SIZE || y < 0) return false;
            else return true;
        }
    }

}
