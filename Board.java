import java.awt.Color;
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
                tempW.add(new Particle(Color.black, "air", 0));
            }
            board.add(tempW);
        }
        System.out.println("BOARD CREATED");
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
    public void setBoard(int x, int y, Particle element){
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
        System.out.println("BOARDTOSTRING");
        return str;
    }
    public void downOne(int x, int y, Particle upElement, Particle downElement) {
        setBoard(x, y, upElement);
        setBoard(x, y + 1, downElement);
    }
}
