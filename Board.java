import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Board { 
    final static int BOARD_HEIGHT_IN_CELLS = MainPanel.GAME_PIX/MainPanel.HEIGHT;    //The number of y elements on the board 2d array
    final static int BOARD_WIDTH_IN_CELLS = MainPanel.GAME_PIX/MainPanel.WIDTH;      //The number of x elements on the board 2d array
    ArrayList<ArrayList<Particle>> board = new ArrayList<ArrayList<Particle>>(BOARD_HEIGHT_IN_CELLS);    //2d array of particles

    //constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //creates a board filled with "air"
    Board() {
        for (int i = 0; i < BOARD_HEIGHT_IN_CELLS; i++) {
            ArrayList<Particle> row = new ArrayList<Particle>(BOARD_WIDTH_IN_CELLS);
            for (int j = 0; j < BOARD_WIDTH_IN_CELLS; j++) {
                row.add(new Particle(0));
            }
            board.add(row);
        }
        System.out.println("BOARD CREATED");
    }

    //setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //sets all of this board's values equal to a given board
    public void setBoard(ArrayList<ArrayList<Particle>> newBoard) {
        for (int rowIndex = 0; rowIndex < BOARD_HEIGHT_IN_CELLS; rowIndex++) {
            this.board.add(newBoard.get(rowIndex));
            for (int columnIndex = 0; columnIndex < BOARD_WIDTH_IN_CELLS; columnIndex++) {
                this.board.get(rowIndex).add(columnIndex, newBoard.get(rowIndex).get(columnIndex));
            }
        }
    }

    //sets the value of an element to a given particle
    public void setBoard(int columnIndex, int rowIndex, Particle element){
        this.board.get(rowIndex).set(columnIndex, element);
    }

    //getters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public ArrayList<ArrayList<Particle>> getBoard() {
        return board;
    }
    public Particle getElement(int columnIndex, int rowIndex) {
        return board.get(rowIndex).get(columnIndex);
    }

    //static methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //takes mouse coordinates and inserts the given element
    public void insertParticle(int columnIndex, int rowIndex, Particle element){
        board.get(rowIndex/MainPanel.PIX_SIZE).set(columnIndex/MainPanel.PIX_SIZE, element);
    }

    //shows the name and density of a given particle at the mouse cursor
    public static String getMousePartData(int columnIndex, int rowIndex, Board board){
        Particle element = board.getElement(columnIndex/MainPanel.PIX_SIZE, rowIndex/MainPanel.PIX_SIZE);
        String string = element.getName() + " D:" + element.getDensity();
        return string;
    }

    //methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //converts a 2d array into a string, for debugging
    public String boardToString() {
        String str = "";
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Particle> currentRow = board.get(i);
            for (int j = 0; j < board.get(i).size(); j++) {
                str = str + currentRow.get(j).getId();
            }
            str = str + "\n";
        }
        System.out.println("BOARDTOSTRING");
        return str;
    }

    public int particleCount() {
        int count = 0;
        int particleTpe = 0;
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Particle> currentRow = board.get(i);
            for (int j = 0; j < board.get(i).size(); j++) {
                particleTpe = currentRow.get(j).getId();
                if (particleTpe != 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    //moves an element down once
    public void moveDownOne(int columnIndex, int rowIndex, Particle lessDense, Particle moreDense) {
        setBoard(columnIndex, rowIndex, lessDense);
        setBoard(columnIndex, rowIndex + 1, moreDense);
    }
    //moves an element down and right once
    public void moveDownRight(int columnIndex, int rowIndex, Particle lessDense, Particle moreDense) {
        setBoard(columnIndex, rowIndex, lessDense);
        setBoard(columnIndex + 1, rowIndex + 1, moreDense);
    }
    //moves an element down and left once
    public void moveDownLeft(int columnIndex, int rowIndex, Particle lessDense, Particle moreDense) {
        setBoard(columnIndex, rowIndex, lessDense);
        setBoard(columnIndex - 1, rowIndex + 1, moreDense);
    }
    //moves an element right once
    public void moveRight(int columnIndex, int rowIndex, Particle lessDense, Particle moreDense){
        setBoard(columnIndex, rowIndex, lessDense);
        setBoard(columnIndex + 1, rowIndex, moreDense);
    }
    //moves an element left once
    public void moveLeft(int columnIndex, int rowIndex, Particle lessDense, Particle moreDense){
        setBoard(columnIndex, rowIndex, lessDense);
        setBoard(columnIndex - 1, rowIndex, moreDense);
    }
    // Check if a particle can move to a target position
    private boolean canMove(Particle current, Particle target, Particle adjacent) {
        return current.getDensity() > target.getDensity() && adjacent.getDensity() <= target.getDensity();
    }

    // Handle diagonal movement
    private void handleDiagonalMovement(int columnIndex, int rowIndex, Particle element) {
        Particle rightElement = getElement(columnIndex + 1, rowIndex);
        Particle bRightElement = getElement(columnIndex + 1, rowIndex + 1);
        Particle leftElement = getElement(columnIndex - 1, rowIndex);
        Particle bLeftElement = getElement(columnIndex - 1, rowIndex + 1);

        boolean leftLight = canMove(element, bLeftElement, leftElement);
        boolean rightLight = canMove(element, bRightElement, rightElement);

        if (leftLight && rightLight) {
            if (ThreadLocalRandom.current().nextInt(2) == 0) {
                moveDownLeft(columnIndex, rowIndex, bLeftElement, element);
            } else {
                moveDownRight(columnIndex, rowIndex, bRightElement, element);
            }
        } else if (leftLight) {
            moveDownLeft(columnIndex, rowIndex, bLeftElement, element);
        } else if (rightLight) {
            moveDownRight(columnIndex, rowIndex, bRightElement, element);
        }
    }

    // calcDown function
    public void calcDown() {
        for (int rowIndex = Board.BOARD_HEIGHT_IN_CELLS - 1; rowIndex > 0; rowIndex--) {
            for (int columnIndex = 0; columnIndex < Board.BOARD_WIDTH_IN_CELLS; columnIndex++) {
                Particle element = getElement(columnIndex, rowIndex);

                // Skip if the element is already at the bottom
                if (rowIndex >= Board.BOARD_HEIGHT_IN_CELLS - 1) continue;

                Particle belowElement = getElement(columnIndex, rowIndex + 1);

                // Move directly down if possible
                if (element.getDensity() > belowElement.getDensity()) {
                    moveDownOne(columnIndex, rowIndex, belowElement, element);
                    continue;
                }

                // Handle diagonal movement
                if (columnIndex > 0 && columnIndex < Board.BOARD_WIDTH_IN_CELLS - 1) {
                    handleDiagonalMovement(columnIndex, rowIndex, element);
                } else if (columnIndex == 0) { // Left border
                    Particle bRightElement = getElement(columnIndex + 1, rowIndex + 1);
                    Particle rightElement = getElement(columnIndex + 1, rowIndex);
                    if (canMove(element, bRightElement, rightElement)) {
                        moveDownRight(columnIndex, rowIndex, bRightElement, element);
                    }
                } else if (columnIndex == Board.BOARD_WIDTH_IN_CELLS - 1) { // Right border
                    Particle bLeftElement = getElement(columnIndex - 1, rowIndex + 1);
                    Particle leftElement = getElement(columnIndex - 1, rowIndex);
                    if (canMove(element, bLeftElement, leftElement)) {
                        moveDownLeft(columnIndex, rowIndex, bLeftElement, element);
                    }
                }
            }
        }
    }
}