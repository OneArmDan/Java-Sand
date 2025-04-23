import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Board { 
    final static int BOARD_HEIGHT = MainPanel.GAME_PIX/MainPanel.HEIGHT;    //The number of y elements on the board 2d array
    final static int BOARD_WIDTH = MainPanel.GAME_PIX/MainPanel.WIDTH;      //The number of x elements on the board 2d array
    ArrayList<ArrayList<Particle>> board = new ArrayList<ArrayList<Particle>>(BOARD_HEIGHT);    //2d array of particles

    //constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //creates a board filled with "air"
    Board() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            ArrayList<Particle> tempW = new ArrayList<Particle>(BOARD_WIDTH);
            for (int j = 0; j < BOARD_WIDTH; j++) {
                tempW.add(new Particle(0));
            }
            board.add(tempW);
        }
        System.out.println("BOARD CREATED");
    }

    //setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //sets all of this board's values equal to a given board
    public void setBoard(ArrayList<ArrayList<Particle>> newBoard) {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            this.board.add(newBoard.get(y));
            for (int x = 0; x < BOARD_WIDTH; x++) {
                this.board.get(y).add(x, newBoard.get(y).get(x));
            }
        }
    }

    //sets the value of an element to a given particle
    public void setBoard(int x, int y, Particle element){
        this.board.get(y).set(x, element);
    }

    //getters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public ArrayList<ArrayList<Particle>> getBoard() {
        return board;
    }
    public Particle getElement(int x, int y) {
        return board.get(y).get(x);
    }

    //static methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //takes mouse coordinates and inserts the given element
    public void insertParticle(int x, int y, Particle element){
        board.get(y/MainPanel.PIX_SIZE).set(x/MainPanel.PIX_SIZE, element);
    }

    //shows the name and density of a given particle at the mouse cursor
    public static String getMousePartData(int x, int y, Board board){
        Particle element = board.getElement(x/MainPanel.PIX_SIZE, y/MainPanel.PIX_SIZE);
        String string = element.getName() + " D:" + element.getDensity();
        return string;
    }

    //methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //converts a 2d array into a string, for debugging
    public String boardToString() {
        String str = "";
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Particle> iList = board.get(i);
            for (int j = 0; j < board.get(i).size(); j++) {
                str = str + iList.get(j).getId();
            }
            str = str + "\n";
        }
        System.out.println("BOARDTOSTRING");
        return str;
    }

    public int particleCount() {
        int count = 0;
        int partType = 0;
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Particle> iList = board.get(i);
            for (int j = 0; j < board.get(i).size(); j++) {
                partType = iList.get(j).getId();
                if (partType != 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    //moves an element down once
    public void downOne(int x, int y, Particle lessDense, Particle moreDense) {
        setBoard(x, y, lessDense);
        setBoard(x, y + 1, moreDense);
    }
    //moves an element down and right once
    public void downRight(int x, int y, Particle lessDense, Particle moreDense) {
        setBoard(x, y, lessDense);
        setBoard(x + 1, y + 1, moreDense);
    }
    //moves an element down and left once
    public void downLeft(int x, int y, Particle lessDense, Particle moreDense) {
        setBoard(x, y, lessDense);
        setBoard(x - 1, y + 1, moreDense);
    }
    //moves an element right once
    public void goRight(int x, int y, Particle lessDense, Particle moreDense){
        setBoard(x, y, lessDense);
        setBoard(x + 1, y, moreDense);
    }
    //moves an element left once
    public void goLeft(int x, int y, Particle lessDense, Particle moreDense){
        setBoard(x, y, lessDense);
        setBoard(x - 1, y, moreDense);
    }
    // Check if a particle can move to a target position
    private boolean canMove(Particle current, Particle target, Particle adjacent) {
        return current.getDensity() > target.getDensity() && adjacent.getDensity() <= target.getDensity();
    }

    // Handle diagonal movement
    private void handleDiagonalMovement(int x, int y, Particle element) {
        Particle rightElement = getElement(x + 1, y);
        Particle bRightElement = getElement(x + 1, y + 1);
        Particle leftElement = getElement(x - 1, y);
        Particle bLeftElement = getElement(x - 1, y + 1);

        boolean leftLight = canMove(element, bLeftElement, leftElement);
        boolean rightLight = canMove(element, bRightElement, rightElement);

        if (leftLight && rightLight) {
            if (ThreadLocalRandom.current().nextInt(2) == 0) {
                downLeft(x, y, bLeftElement, element);
            } else {
                downRight(x, y, bRightElement, element);
            }
        } else if (leftLight) {
            downLeft(x, y, bLeftElement, element);
        } else if (rightLight) {
            downRight(x, y, bRightElement, element);
        }
    }

    // calcDown function
    public void calcDown() {
        for (int y = Board.BOARD_HEIGHT - 1; y > 0; y--) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                Particle element = getElement(x, y);

                // Skip if the element is already at the bottom
                if (y >= Board.BOARD_HEIGHT - 1) continue;

                Particle belowElement = getElement(x, y + 1);

                // Move directly down if possible
                if (element.getDensity() > belowElement.getDensity()) {
                    downOne(x, y, belowElement, element);
                    continue;
                }

                // Handle diagonal movement
                if (x > 0 && x < Board.BOARD_WIDTH - 1) {
                    handleDiagonalMovement(x, y, element);
                } else if (x == 0) { // Left border
                    Particle bRightElement = getElement(x + 1, y + 1);
                    Particle rightElement = getElement(x + 1, y);
                    if (canMove(element, bRightElement, rightElement)) {
                        downRight(x, y, bRightElement, element);
                    }
                } else if (x == Board.BOARD_WIDTH - 1) { // Right border
                    Particle bLeftElement = getElement(x - 1, y + 1);
                    Particle leftElement = getElement(x - 1, y);
                    if (canMove(element, bLeftElement, leftElement)) {
                        downLeft(x, y, bLeftElement, element);
                    }
                }
            }
        }
    }
}