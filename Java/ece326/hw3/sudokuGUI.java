
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_END;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class sudokuGUI  { 
    JFrame frame;
    JMenuBar menu;
    JMenu newGame;
    JMenuItem easy, intermediate, expert;
    JButton[] buttons;
    JButton erase, undo, solve;
    JCheckBox verifyBox;
    JTextField[][] textFields;
    JTextField currField;
    int[][] solutionArray;
    solver slvr = new solver();
    Stack<undo> stack;

    public sudokuGUI() {
        frame = new JFrame("Sudoku");
        frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);

        menu = new JMenuBar();
        menu.setVisible(true);
		frame.setJMenuBar(menu);

        newGame = new JMenu("New Game");

        easy = new JMenuItem("Easy");
        intermediate = new JMenuItem("Intermediate");
        expert = new JMenuItem("Expert");

        easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				difficultyActionPerformed(evt);
			}
		});

        intermediate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				difficultyActionPerformed(evt);
			}
		});

        expert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				difficultyActionPerformed(evt);
			}
		});

        newGame.add(easy);
        newGame.add(intermediate);
        newGame.add(expert);

        menu.add(newGame);
        
        JPanel centerPanel = new JPanel(new GridLayout(3, 3));
        frame.add(centerPanel, CENTER);

        JPanel[] arrays = new JPanel[9];
        int i, j;

        for(i = 0; i < 9; i++) {
            arrays[i] = new JPanel(new GridLayout(3, 3));
            arrays[i].setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            centerPanel.add(arrays[i]);
        }

        textFields = new JTextField[9][9];
        solutionArray = new int[9][9];

        for(i = 0; i < 9; i++) {
            for(j = 0; j < 9; j++) {
                textFields[i][j] = new JTextField("");
                textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                Font font1 = new Font("SansSerif", Font.BOLD, 30);
                textFields[i][j].setFont(font1);
                arrays[(j / 3) + (i / 3) * 3].add(textFields[i][j]);
                textFields[i][j].setEditable(false);
                textFields[i][j].setFocusable(false);
                textFields[i][j].setName(i + "" + j);
                textFields[i][j].addFocusListener(new FocusListener() {
                    public void focusGained(FocusEvent evt){
                        focusGainedActionPerformed(evt);
                    }
                    public void focusLost(FocusEvent evt){
                        focusLostActionPerformed(evt);
                    }
                });

                textFields[i][j].addKeyListener(new KeyListener() {

                    public void keyTyped(KeyEvent evt) {

                        char c = evt.getKeyChar();
                        if(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) { // back space or delete
                            evt.consume();
                            deleteKeyTypedActionPerformed(evt);
                        }
                        else if(c >= '1' && c <= '9') { // number between 1-9
                            evt.consume();
                            numberKeyTypedActionPerformed(evt);
                        }
                       
                        else { // anything else
                            evt.consume();
                        }
                    }
                    public void keyPressed(KeyEvent evt) {
                        // empty
                      }
                    public void keyReleased(KeyEvent evt) {
                        // empty
                    }
                }
                );

            }
        }

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttons = new JButton[9];

        for(i = 0; i < 9; i++) {
            buttons[i] = new JButton(String.valueOf(i + 1));
			buttons[i].setMnemonic(97 + i);
			buttons[i].setEnabled(false);
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    buttonsActionPerformed(evt);
                }
            });

			buttonsPanel.add(buttons[i]);
        }

        
        //color the buttons
        erase = new JButton();
        erase.setIcon(new ImageIcon(new ImageIcon("images/eraser.png").getImage().getScaledInstance(10, 15, java.awt.Image.SCALE_SMOOTH)));
        erase.setEnabled(false);
        erase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				eraseActionPerformed(evt);
			}
		});
        buttonsPanel.add(erase);

        undo = new JButton();
        undo.setIcon(new ImageIcon(new ImageIcon("images/undo.png").getImage().getScaledInstance(10, 15, java.awt.Image.SCALE_SMOOTH)));
        undo.setEnabled(false);
        undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				undoActionPerformed(evt);
			}
		});
        buttonsPanel.add(undo);

        verifyBox = new JCheckBox("Verify against solution");
        verifyBox.setEnabled(false);
        verifyBox.setRolloverEnabled(false);
        verifyBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				verifyActionPerformed(evt);
			}
		});
        buttonsPanel.add(verifyBox);

        solve = new JButton();
        solve.setIcon(new ImageIcon(new ImageIcon("images/rubik.png").getImage().getScaledInstance(10, 15, java.awt.Image.SCALE_SMOOTH)));
        solve.setEnabled(false);
        solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				solutionActionPerformed(evt);
			}
		});
        buttonsPanel.add(solve);

        frame.add(buttonsPanel, PAGE_END);
        frame.pack();
        frame.setVisible(true);
    }
  
    private void difficultyActionPerformed(ActionEvent evt) {
        stack = new Stack<undo>();
        int i = 0, j;
        URL url = null;
        Character c;
        String choice = evt.getActionCommand();
        try {
            if(choice.equals("Easy"))
                url = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=easy");
            else if(choice.equals("Intermediate"))
                url = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=intermediate");
            else if(choice.equals("Expert"))
                url = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=expert");

            URLConnection urlcon = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                
                if(inputLine.length() <= 0)
                    break;
                
                for(j = 0; j < 9; j++) {
                    c = inputLine.charAt(j);
                    if(c == '.') {
                        textFields[i][j].setText("");
                        textFields[i][j].setBackground(new Color(255, 255, 255));
                        textFields[i][j].setEditable(true);
                        textFields[i][j].setFocusable(true);
                        solutionArray[i][j] = 0;
                    }
                    else {
                        textFields[i][j].setText(c.toString());
                        textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                        textFields[i][j].setBackground(new Color(200, 200, 200));
                        textFields[i][j].setEditable(false);
                        textFields[i][j].setFocusable(true);
                        solutionArray[i][j] = c - '0';

                    }
                }
                i++;    
            }
            
            slvr.solveSudoku(solutionArray, 0, 0);
            // comment out the next line to get-print the solution 
            //slvr.print(solutionArray);

            for(i = 0; i < 9; i++)
                buttons[i].setEnabled(true);
            
            erase.setEnabled(true);
            undo.setEnabled(true);
            verifyBox.setEnabled(true);
            solve.setEnabled(true);

            in.close();
        }
        catch(MalformedURLException ex) {
            System.out.println("Malformed URL");
            ex.printStackTrace();
        }
        catch(IOException ex) {
            System.out.println("Error while reading or writing from URL: "+url.toString() );
            ex.printStackTrace();
        }
    }

    private void focusGainedActionPerformed(FocusEvent evt) {
        JTextField temp = currField = (JTextField)evt.getSource();

        int i, j;
        if(!temp.getText().equals("")) {    
            for(i = 0; i < 9; i++) {
                for(j = 0; j < 9; j++) {
                    if(textFields[i][j].getText().equals(temp.getText()) && !textFields[i][j].getBackground().equals(new Color(100, 150, 200)) && !textFields[i][j].getBackground().equals(new Color(255, 0, 0))) 
                        textFields[i][j].setBackground(new Color(255, 255, 200));
                }
            }
        }
    }

    private void focusLostActionPerformed(FocusEvent evt) {
        int i, j;
        for(i = 0; i < 9; i++) {
            for(j = 0; j < 9; j++) {
                if(!textFields[i][j].getBackground().equals(new Color(100, 150, 200)) && !textFields[i][j].getBackground().equals(new Color(255, 0, 0))) {
                    if(!textFields[i][j].isEditable()) 
                        textFields[i][j].setBackground(new Color(200, 200, 200)); 
                    else
                        textFields[i][j].setBackground(new Color(255, 255, 255));
                }
            }
        }
        checkColors();
    }

    private void solutionActionPerformed(ActionEvent evt) {
        int i, j;
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                textFields[i][j].setText(String.valueOf(solutionArray[i][j]));
                System.out.print(solutionArray[i][j] + " ");
            }
            System.out.println();
        }
        for(i = 0; i < 9; i++)
            buttons[i].setEnabled(false);
            
        erase.setEnabled(false);
        undo.setEnabled(false);
        verifyBox.setEnabled(false);
        solve.setEnabled(false);

        JOptionPane.showMessageDialog(frame, "              Good luck next time!\n              Select new game", "", JOptionPane.PLAIN_MESSAGE);

    }
    
    
    private void numberKeyTypedActionPerformed(KeyEvent evt) {
        String str = String.valueOf(evt.getKeyChar());
        int row = currField.getName().charAt(0) - '0';
        int col = currField.getName().charAt(1) - '0';
        String prevValue = currField.getText();
        undo item = new undo(row, col, textFields[row][col].getText());

        if(currField.isEditable()){
            currField.setText(str);
            stack.push(item);
            checkCollisions(Integer.valueOf(str));

            if(!prevValue.equals("")) {
                fixRed(Integer.valueOf(prevValue), row, col);
            }
            checkColors();
            checkForWin();
        }     

    }

    private void deleteKeyTypedActionPerformed(KeyEvent evt) {
        int row = currField.getName().charAt(0) - '0';
        int col = currField.getName().charAt(1) - '0';
        String prevValue = currField.getText();
        undo item = new undo(row, col, textFields[row][col].getText());

        if(currField.isEditable() && !currField.getText().equals("")){
            currField.setText("");
            stack.push(item);
            checkCollisions(Integer.valueOf(item.str));
            if(!prevValue.equals(""))  {
                fixRed(Integer.valueOf(prevValue), row, col);
            }
            

            checkColors();
            currField.setBackground(new Color(255, 255, 255));
            
        }   
    }

    private void buttonsActionPerformed(ActionEvent evt) {

        String str = evt.getActionCommand();
        int row = currField.getName().charAt(0) - '0';
        int col = currField.getName().charAt(1) - '0';
        String prevValue = currField.getText();
        undo item = new undo(row, col, textFields[row][col].getText());

        if(currField.isEditable()){
            currField.setText(str);
            stack.push(item);
            checkCollisions(Integer.valueOf(str));

            if(!prevValue.equals("")) {
                fixRed(Integer.valueOf(prevValue), row, col);
            }
            checkColors();
            checkForWin();
        }       
    }

    private void eraseActionPerformed(ActionEvent evt) {

        int row = currField.getName().charAt(0) - '0';
        int col = currField.getName().charAt(1) - '0';
        String prevValue = currField.getText();
        undo item = new undo(row, col, textFields[row][col].getText());

        if(currField.isEditable() && !currField.getText().equals("")){
            currField.setText("");
            stack.push(item);
            checkCollisions(Integer.valueOf(item.str));
            if(!prevValue.equals(""))  {
                fixRed(Integer.valueOf(prevValue), row, col);
            }
            

            checkColors();
            currField.setBackground(new Color(255, 255, 255));
            
        }   
      
    }
    private void undoActionPerformed(ActionEvent evt) {

        if(stack.isEmpty())
            return;

        undo item  = stack.pop();
        String prevValue = textFields[item.row][item.col].getText();
        textFields[item.row][item.col].setText(item.str);
        if(item.str.equals("")) {
            checkCollisions(0);
        }
        else
            checkCollisions(Integer.valueOf(item.str));
        if(!prevValue.equals(""))
            fixRed(Integer.valueOf(prevValue), item.row, item.col);
        checkColors();
        checkForWin();
    }

    private void verifyActionPerformed(ItemEvent evt) {
        int i, j;

        checkColors();
    }
    private void checkColors() {
        int i, j;
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if(verifyBox.isSelected() && !textFields[i][j].getText().equals(String.valueOf(solutionArray[i][j])) && textFields[i][j].isEditable() 
                && !textFields[i][j].getText().equals(String.valueOf(""))  && !textFields[i][j].getBackground().equals(new Color(255, 0, 0)))
                    textFields[i][j].setBackground(new Color(100, 150, 200));
                else if(!verifyBox.isSelected() && textFields[i][j].getBackground().equals(new Color(100, 150, 200)))
                    textFields[i][j].setBackground(new Color(255, 255, 255));
                else if(textFields[i][j].getText().equals("") && textFields[i][j].getBackground().equals(new Color(255, 0 ,0)))
                    textFields[i][j].setBackground(new Color(255, 255, 255));
            }
        }
    }


    private void checkCollisions(int num) {
        int i, j, row, col;
        int collitions = 0;
        row = currField.getName().charAt(0) - '0';
        col = currField.getName().charAt(1) - '0';

        //check for the same num in the similar row

        for(i = 0; i < 9; i++) {
            if(textFields[row][i].getText().equals(String.valueOf(num)) && i != col) {
                textFields[row][i].setBackground(new Color(255, 0, 0));
                collitions++;
            }
        }

        //check for the same num in the similar column
        for(i = 0; i < 9; i++) {
            if(textFields[i][col].getText().equals(String.valueOf(num)) && i != row) {
                textFields[i][col].setBackground(new Color(255, 0, 0));
                collitions++;
            }
        }

        //check for the same num in the similar 3*3 square
        int startRow = row - row % 3, startCol = col - col % 3;
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                if(textFields[i + startRow][j + startCol].getText().equals(String.valueOf(num)) && row != i + startRow && col != j + startCol) {
                    textFields[i + startRow][j + startCol].setBackground(new Color(255, 0, 0));
                    collitions++;
                }
            }
        }
        if(collitions != 0) 
            currField.setBackground(new Color(255, 0, 0));
        
        else 
            currField.setBackground(new Color(255, 255, 255));

    }

    // this method is called after checkCollitions
    // revert's the red colors as soon as the colltion is fixed
    private void fixRed(int prev, int row, int col) {
        int i, j;
        int counter = 0;
        int lastCoord = -1, lastx = -1, lasty = -1;
        
        for(i = 0; i < 9; i++) {
            if(textFields[row][i].getText().equals(String.valueOf(prev))) {
                counter++;
                lastCoord = i;
            }
        }

        if(counter == 1) 
            anotherCheck(row, lastCoord, prev);
           
        counter = 0;

        for(i = 0; i < 9; i++) {
            if(textFields[i][col].getText().equals(String.valueOf(prev))) {
                counter++;
                lastCoord = i;
            }
        }

        if(counter == 1) 
            anotherCheck(lastCoord, col, prev);
        

        counter = 0;
        int startRow = row - row % 3, startCol = col - col % 3;

        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                if(textFields[i + startRow][j + startCol].getText().equals(String.valueOf(prev))) {
                    counter++;
                    lastx = i + startRow;
                    lasty = j + startCol;

                }
            }
        }
        
        if(counter == 1) {
            anotherCheck(lastx, lasty, prev);
        }

    }

    // this method is only called by fixRed
    // check if a red box has colltions in col-row-same box and if it doesnt the color is reverted to the original 
    private void anotherCheck(int row, int col, int num) {
        int i, j;
        int counter = 0;

        for(i = 0; i < 9; i++) {
            if(textFields[row][i].getText().equals(String.valueOf(num)))
                counter++;
        }
        if(counter > 1)
            return;

        counter = 0;

        for(i = 0; i < 9; i++) {
            if(textFields[i][col].getText().equals(String.valueOf(num)))
                counter++;
        }

        if(counter > 1)
            return;
        
        counter = 0;
        int startRow = row - row % 3, startCol = col - col % 3;

        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                if(textFields[i + startRow][j + startCol].getText().equals(String.valueOf(num)) )
                    counter++;
            }
        }
        if(counter > 1)
            return;
        else {
            if(textFields[row][col].isEditable()) 
                textFields[row][col].setBackground(new Color(255, 255, 255));
            
            else 
                textFields[row][col].setBackground(new Color(200, 200, 200));
              
        }
    }

    // this method is called when the user makes a move (except erase; the puzzle cant be solved if the last move is erase)
    // it checks if the user has solve the puzzle and if so it prints a messege
    private void checkForWin() {
        int i, j;

        for(i = 0; i < 9; i++) {
            for(j = 0; j < 9; j++) 
                if(textFields[i][j].getText().equals(String.valueOf(solutionArray[i][j])))
                    continue;
                else
                    return;
        }

        // if for loop is finished without return the user has solved the puzzle
        // print messege to the user and disable all buttons

        for(i = 0; i < 9; i++)
            buttons[i].setEnabled(false);
            
        erase.setEnabled(false);
        undo.setEnabled(false);
        verifyBox.setEnabled(false);
        solve.setEnabled(false);
        JOptionPane.showMessageDialog(frame, "              Sudoku Solved!\n              Select new game", "", JOptionPane.PLAIN_MESSAGE);
    }
}
