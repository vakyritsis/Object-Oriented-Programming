import java.util.*;

public class undo {
    int row, col;
    String str;

    public undo(int row, int col, String str) {
        this.row = row;
        this.col = col;
        this.str = new String(str);
    }

    public void printElement() {
        String holder = String.format("row = %d, col = %d, value = %s", row, col, str);
        
        System.out.println(holder);
    }
}