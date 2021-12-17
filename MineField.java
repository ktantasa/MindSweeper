// Name: Kant Tantasathien

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MineField class with locations of mines for a game. This class is mutable,
 * because we sometimes need to change it once it's created. mutators:
 * populateMineField, resetEmpty includes convenience method to tell the number
 * of mines adjacent to a location.
 */
public class MineField {

   // <put instance variables here>
   private int numRows;
   private int numCols;
   private int numMines;
   private boolean[][] mineData;

   /**
    * Create a minefield with same dimensions as the given array, and populate it
    * with the mines in the array such that if mineData[row][col] is true, then
    * hasMine(row,col) will be true and vice versa. numMines() for this minefield
    * will corresponds to the number of 'true' values in mineData.
    * 
    * @param mineData the data for the mines; must have at least one row and one
    *                 col, and must be rectangular (i.e., every row is the same
    *                 length) mineData would be true if there's a mine at that
    *                 location and false if it's not at the location.
    */
   public MineField(boolean[][] mineData) {
      this.numRows = mineData.length;
      this.numCols = mineData[0].length;
      this.numMines = 0;
      this.mineData = new boolean[numRows][]; // created a new Array for our mineData, this ensure that our data is
                                              // safely copied
      for (int r = 0; r < numRows; r++) { // loop through all of the rows
         this.mineData[r] = new boolean[numCols]; // create an array to hold all of the cells on a per row basis
         for (int c = 0; c < numCols; c++) {
            if (mineData[r][c]) { // Detecting whether or not a mine exists at this location
               this.numMines++;
               this.mineData[r][c] = true; // Placing a mine in the copy of the data
            }
         }
      }
   }

   /**
    * Create an empty minefield (i.e. no mines anywhere), that may later have
    * numMines mines (once populateMineField is called on this object). Until
    * populateMineField is called on such a MineField, numMines() will not
    * correspond to the number of mines currently in the MineField.
    * 
    * @param numRows  number of rows this minefield will have, must be positive
    * @param numCols  number of columns this minefield will have, must be positive
    * @param numMines number of mines this minefield will have, once we populate
    *                 it. PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3
    *                 of total number of field locations).
    */
   public MineField(int numRows, int numCols, int numMines) {
      this.numRows = numRows;
      this.numCols = numCols;
      this.numMines = numMines;
      this.mineData = new boolean[numRows][];
      for (int r = 0; r < numRows; r++) {
         this.mineData[r] = new boolean[numCols];
         for (int c = 0; c < numCols; c++) {
            this.mineData[r][c] = false;
         }
      }
   }

   /**
    * Removes any current mines on the minefield, and puts numMines() mines in
    * random locations on the minefield, ensuring that no mine is placed at (row,
    * col).
    * 
    * @param row the row of the location to avoid placing a mine
    * @param col the column of the location to avoid placing a mine PRE:
    *            inRange(row, col) and numMines() < (1/3 * numRows() * numCols())
    */
   public void populateMineField(int row, int col) {
      resetEmpty(); // make sure the minefield is empty
      Random random = new Random(); // Instantiate the random object
      int currentMine = 0;
      while (currentMine < numMines) { // loop until the number of mine we placed is equal to the mines we are supposed
                                       // to place
         int r = random.nextInt(numRows);
         int c = random.nextInt(numCols);
         if (r == row && c == col) { // ensure that the rows & column we select are not the same as the one we are
                                     // supposed to ignore
            continue;
         }
         if (mineData[r][c]) { // ensure that we are not placing mines on the same location
            continue;
         }
         mineData[r][c] = true; // Place the mine at our random location
         currentMine++; // increase our counter
      }
   }

   /**
    * Reset the minefield to all empty squares. This does not affect numMines(),
    * numRows() or numCols() Thus, after this call, the actual number of mines in
    * the minefield does not match numMines(). Note: This is the state a minefield
    * created with the three-arg constructor is in at the beginning of a game.
    */
   public void resetEmpty() {
      for (int r = 0; r < numRows; r++) {
         for (int c = 0; c < numCols; c++) {
            mineData[r][c] = false; // loop through all the rows & columns and set everything to false
         }
      }
   }

   /**
    * Returns the number of mines adjacent to the specified mine location (not
    * counting a possible mine at (row, col) itself). Diagonals are also considered
    * adjacent, so the return value will be in the range [0,8]
    * 
    * @param row row of the location to check
    * @param col column of the location to check
    * @return the number of mines adjacent to the square at (row, col) Used Ternary
    *         Operator to simplify the conversion of Booleans to integers. If the
    *         boolean is true, the ternary will return a 1. If the boolean is
    *         false, the ternary will return a 0. PRE: inRange(row, col)
    */
   public int numAdjacentMines(int row, int col) {
      List<Integer> cells = new ArrayList<>(8); // create a list of integers to hold adjacent cells
      if (row > 0) { // handling the top 3 cells
         if (col > 0) { // topleft
            cells.add(mineData[row - 1][col - 1] ? 1 : 0); // if a mine exists in the indicated cells, put a 1 on the
                                                           // list, otherwise put a 0
         }
         cells.add(mineData[row - 1][col] ? 1 : 0); // top middle
         if (col < numCols - 1) {
            cells.add(mineData[row - 1][col + 1] ? 1 : 0); // top right
         }
      }
      if (row < numRows - 1) { // bottom three cells
         if (col > 0) {
            cells.add(mineData[row + 1][col - 1] ? 1 : 0); // bottom left
         }
         cells.add(mineData[row + 1][col] ? 1 : 0); // bottom middle
         if (col < numCols - 1) {
            cells.add(mineData[row + 1][col + 1] ? 1 : 0); // bottom right
         }
      }
      if (col > 0) {
         cells.add(mineData[row][col - 1] ? 1 : 0); // left
      }
      if (col < numCols - 1) {
         cells.add(mineData[row][col + 1] ? 1 : 0); // right
      }
      return cells.stream().mapToInt(Integer::intValue).sum(); // Essentially cast this something that is able to get a
                                                               // sum for
                                                               // sum counts the number of adjacent cells that contain
                                                               // the mine
   }

   /**
    * Returns true iff (row,col) is a valid field location. Row numbers and column
    * numbers start from 0.
    * 
    * @param row row of the location to consider
    * @param col column of the location to consider
    * @return whether (row, col) is a valid field location Combine all of the
    *         boolean checks.
    */
   public boolean inRange(int row, int col) {
      return (row >= 0 && row < numRows) && (col >= 0 && col < numCols);
   }

   /**
    * Returns the number of rows in the field.
    * 
    * @return number of rows in the field
    */
   public int numRows() {
      return numRows;
   }

   /**
    * Returns the number of columns in the field.
    * 
    * @return number of columns in the field
    */
   public int numCols() {
      return numCols;
   }

   /**
    * Returns whether there is a mine in this square
    * 
    * @param row row of the location to check
    * @param col column of the location to check
    * @return whether there is a mine in this square PRE: inRange(row, col)
    */
   public boolean hasMine(int row, int col) {
      return mineData[row][col];
   }

   /**
    * Returns the number of mines you can have in this minefield. For mines created
    * with the 3-arg constructor, some of the time this value does not match the
    * actual number of mines currently on the field. See doc for that constructor,
    * resetEmpty, and populateMineField for more details.
    * 
    * @return
    */
   public int numMines() {
      return numMines;
   }

   // <put private methods here>

}
