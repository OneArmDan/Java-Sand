import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MainPanel extends JPanel implements ActionListener {
    static final int WIDTH = 768;
    static final int HEIGHT = 768;
    static final int PIX_SIZE = 16;
    static final int GAME_PIX = (WIDTH*HEIGHT)/PIX_SIZE;
    static final int DELAY = 15;
    
    boolean isMousePress = false;
    boolean qToggle = false;        //toggels between stone and sand
    boolean particleCountButton = false;
    int mouseX;
    int mouseY;
    Timer timer;

    Board gameBoard = new Board();
    Board tempBoard = new Board();
    Particle selected = new Particle(1); //holds the selected particle type, sand by default

    long startTick = 0;
    long endTick = 0;
    long between = 0;
    
    //constructor
    MainPanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));         //sets the dimensions of the panel
        setBackground(Color.BLACK);                             //
        setFocusable(true);                          //Apperantly this may help with performance
        addKeyListener(new MyKeyAdapter());                     //Listens to keyboard
        addMouseMotionListener(new MyMouseMotionAdapter());     //Listens to mouse movement
        addMouseListener(new MyMouseAdapter());                 //Listens to Mouse buttons
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

    //checks whether an x and y coordinate pair is in bounds
    public static boolean isInBounds(int x, int y) {
        if(x > WIDTH - PIX_SIZE || x < 0 || y > HEIGHT - PIX_SIZE || y < 0) return false;
        else return true;
    }

    //Starts the game loop, I don't really know how this works
    public void start(){
        timer = new Timer(DELAY,this);
        timer.start();
    }
    
    //I have no idea honestly
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    //Gets called every loop, my gameloop
    public void draw(Graphics g){
        if (qToggle == true) selected = new Particle(2);// if q is down, stone
        else selected = new Particle(1);//default sand

        if (particleCountButton) System.out.println(gameBoard.particleCount());

        if (isMousePress)
            gameBoard.insertParticle(mouseX, mouseY, selected);
        
        drawBoard(g);
        
        g.setColor(Color.gray);
        drawGrid(g);

        g.setColor(Color.white);
        g.drawString("X: " + mouseX + ", Y: " + mouseY + " " + Board.getMousePartData(mouseX, mouseY, gameBoard), mouseX, mouseY); //mouse coords on cursor
        drawFPSCounter(g);

        gameBoard.calcDown(tempBoard);
    }

    //Draws the FPS counter on the top left
    public void drawFPSCounter(Graphics g) {
        startTick = System.currentTimeMillis();
        between = (startTick + 1 - endTick);
        g.drawString("fps: " + (1000/between), 10, 10);
        endTick = System.currentTimeMillis();
    }

    //Draws the pixels and elements present in the gameBoard
    public void drawBoard(Graphics g) {
        for (int y = 0; y < gameBoard.getBoard().size(); y++) {
            for (int x = 0; x < gameBoard.getBoard().get(y).size(); x++) {
                g.setColor(gameBoard.getElement(x, y).getColor());
                g.fillRect(x * PIX_SIZE, y * PIX_SIZE, PIX_SIZE, PIX_SIZE);
            }
        }
    }

    //Draws the grid according to the Pixel size
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

    //User Input, I have no idea how any of this works
  
    //Determines if the Q key has been pressed
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_Q){//toggles qToggle when q is pressed
                if(qToggle == false) qToggle = true; 
                else if (qToggle == true) qToggle = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(particleCountButton == false) particleCountButton = true;
                else if (particleCountButton == true) particleCountButton = false;
            }
        }
    }

    //Determines if the mouse key is pressed down
    public class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            isMousePress = true;
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            isMousePress = false;
        }
    }

    //Determines the coordinates of the Mouse cursor
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
            //board.insertParticle(mouseX, mouseY, selected);
            if (isInBounds(mouseX, mouseY)){
                setMouseX(mouseX);
                setMouseY(mouseY);
            }
            //System.out.println(board.boardToString());
        }
    }
}