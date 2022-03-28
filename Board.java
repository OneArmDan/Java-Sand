import java.util.ArrayList;

public class Board { 
    final static int BOARD_HEIGHT = MainPanel.GAME_PIX/MainPanel.HEIGHT;
    final static int BOARD_WIDTH = MainPanel.GAME_PIX/MainPanel.WIDTH;
    ArrayList<ArrayList<Particle>> board = new ArrayList<ArrayList<Particle>>(BOARD_HEIGHT);

    //constructor
    Board() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            ArrayList<Particle> tempW = new ArrayList<Particle>(BOARD_WIDTH);
            for (int j = 0; j < BOARD_WIDTH; j++) {
                tempW.add(new Particle(0));
            }
            board.add(tempW);
        }
        //System.out.println("BOARD CREATED");
    }

    Board(ArrayList<ArrayList<Particle>> newBoard) {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            this.board.add(newBoard.get(y));
            for (int x = 0; x < BOARD_WIDTH; x++) {
                this.board.get(y).add(x, newBoard.get(y).get(x));
            }
        }
    }

    //setters
    public void setBoard(ArrayList<ArrayList<Particle>> newBoard) {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            this.board.add(newBoard.get(y));
            for (int x = 0; x < BOARD_WIDTH; x++) {
                this.board.get(y).add(x, newBoard.get(y).get(x));
            }
        }
    }
    public void setElement(int x, int y, Particle element){
        this.board.get(y).set(x, element);
    }
    //getters
    public ArrayList<ArrayList<Particle>> getBoard() {
        return board;
    }
    public Particle getElement(int x, int y) {
        return board.get(y).get(x);
    }
    //methods
    public void insertParticle(int x, int y, Particle element){
        board.get(y/MainPanel.PIX_SIZE).set(x/MainPanel.PIX_SIZE, element);
    }
    public String boardToString() {
        String str = "";
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Particle> iList = board.get(i);
            for (int j = 0; j < board.get(i).size(); j++) {
                str = str + iList.get(j).getId();
            }
            str = str + "\n";
        }
        return str;
    }
    public void downOne(int x, int y, Particle lessDense, Particle moreDense) {
        setElement(x, y, lessDense);
        setElement(x, y + 1, moreDense);
    }
    public void downRight(int x, int y, Particle lessDense, Particle moreDense) {
        setElement(x, y, lessDense);
        setElement(x + 1, y + 1, moreDense);
    }
    public void downLeft(int x, int y, Particle lessDense, Particle moreDense) {
        setElement(x, y, lessDense);
        setElement(x - 1, y + 1, moreDense);
    }
    public void calcDown() {
        Board tboard = new Board();
        for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                Particle element = getElement(x, y);
                
                if(y < Board.BOARD_HEIGHT - 1 && element.getDensity() > getElement(x, y + 1).getDensity()){ //if off ground and below is less dense
                    tboard.setElement(x, y + 1, element);//go down once
                }
                else tboard.setElement(x, y, element);
                
                /*else if (y < Board.BOARD_HEIGHT - 1) {//if off ground
                    Particle belowElement = getElement(x, y + 1);
                    if (element.getDensity() <= belowElement.getDensity()) {//if belowElement is not less dense
                        if(x < Board.BOARD_WIDTH - 1 && x > 0) { //if not touching either border
                            Particle rightElement = getElement(x + 1, y);
                            Particle bRightElement = getElement(x + 1, y + 1);
                            Particle leftElement = getElement(x - 1, y);
                            Particle bLeftElement = getElement(x - 1, y + 1);
                        
                            if (element.getDensity() > bRightElement.getDensity() && element.getDensity() > bLeftElement.getDensity()) {//if both sides are less dense
                                if(rightElement.getDensity() <= bRightElement.getDensity()) //if right is less dense than br
                                    tboard.downRight(x, y, bRightElement, element);
                                else if (leftElement.getDensity() <= bLeftElement.getDensity()) //if left is less dense than bl
                                    tboard.downLeft(x, y, bLeftElement, element);
                            }
                            else if (element.getDensity() > bRightElement.getDensity() && rightElement.getDensity() <= bRightElement.getDensity()) // if only right side is less dense
                                tboard.downRight(x, y, bRightElement, element);
                            else if (element.getDensity() > bLeftElement.getDensity() && leftElement.getDensity() <= bLeftElement.getDensity()) // if only left side is less dense
                                tboard.downLeft(x, y, bLeftElement, element);

                        }//if not touching either border
                        else if (x <= 0){//if touching left border
                            Particle rightElement = getElement(x + 1, y);
                            Particle bRightElement = getElement(x + 1, y + 1);
                            if (element.getDensity() > bRightElement.getDensity() && rightElement.getDensity() <= bRightElement.getDensity()) // if only right side is less dense
                                tboard.downRight(x, y, bRightElement, element);
                        }
                        else if(x >= Board.BOARD_WIDTH - 1){//if touching right border
                            Particle leftElement = getElement(x - 1, y);
                            Particle bLeftElement = getElement(x - 1, y + 1);
                            if (element.getDensity() > bLeftElement.getDensity() && leftElement.getDensity() <= bLeftElement.getDensity()) // if only left side is less dense
                                tboard.downLeft(x, y, bLeftElement, element);
                        }
                    }//if belowElement is more dense
                }//if off ground*/
            }
        }
        for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                setElement(x, y, tboard.getElement(x, y));
            }
        }
    }
}