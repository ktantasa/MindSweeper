// Name: Kant Tantasathien

/**
 * VisibleField class This is the data that's being displayed at any one point
 * in the game (i.e., visible field, because it's what the user can see about
 * the minefield). Client can call getStatus(row, col) for any square. It
 * actually has data about the whole current state of the game, including the
 * underlying mine field (getMineField()). Other accessors related to game
 * status: numMinesLeft(), isGameOver(). It also has mutators related to actions
 * the player could do (resetGameDisplay(), cycleGuess(), uncover()), and
 * changes the game state accordingly.
 * 
 * It, along with the MineField (accessible in mineField instance variable),
 * forms the Model for the game application, whereas GameBoardPanel is the View
 * and Controller, in the MVC design pattern. It contains the MineField that
 * it's partially displaying. That MineField can be accessed (or modified) from
 * outside this class via the getMineField accessor.
 */
public class VisibleField {
   // ----------------------------------------------------------
   // The following public constants (plus numbers mentioned in comments below) are
   // the possible states of one
   // location (a "square") in the visible field (all are values that can be
   // returned by public method
   // getStatus(row, col)).

   // The following are the covered states (all negative values):
   public static final int COVERED = -1; // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // The following are the uncovered states (all non-negative values):

   // values in the range [0,8] corresponds to number of mines adjacent to this
   // square

   public static final int MINE = 9; // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10; // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11; // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------

   // <put instance variables here>
   private MineField mineField;
   private int[][] mineData;
   private int minesFound;

   /**
    * Create a visible field that has the given underlying mineField. The initial
    * state will have all the mines covered up, no mines guessed, and the game not
    * over.
    * 
    * @param mineField the minefield to use for for this VisibleField
    */
   public VisibleField(MineField mineField) {
      this.mineField = mineField;
      minesFound = 0;
      mineData = new int[mineField.numRows()][];
      for (int r = 0; r < mineField.numRows(); r++) {
         this.mineData[r] = new int[mineField.numCols()];
         for (int c = 0; c < mineField.numCols(); c++) {
            this.mineData[r][c] = COVERED;
         }
      }
   }

   /**
    * Reset the object to its initial state (see constructor comments), using the
    * same underlying MineField. Use nested loop to set the 2D array to COVERED.
    */
   public void resetGameDisplay() {
      minesFound = 0;
      for (int r = 0; r < mineField.numRows(); r++) {
         for (int c = 0; c < mineField.numCols(); c++) {
            this.mineData[r][c] = COVERED;
         }
      }
   }

   /**
    * Returns a reference to the mineField that this VisibleField "covers"
    * 
    * @return the minefield
    */
   public MineField getMineField() {
      return mineField; // return mineField
   }

   /**
    * Returns the visible status of the square indicated.
    * 
    * @param row row of the square
    * @param col col of the square
    * @return the status of the square at location (row, col). See the public
    *         constants at the beginning of the class for the possible values that
    *         may be returned, and their meanings. PRE: getMineField().inRange(row,
    *         col)
    */
   public int getStatus(int row, int col) {
      return mineData[row][col];
   }

   /**
    * Returns the the number of mines left to guess. This has nothing to do with
    * whether the mines guessed are correct or not. Just gives the user an
    * indication of how many more mines the user might want to guess. This value
    * can be negative, if they have guessed more than the number of mines in the
    * minefield.
    * 
    * @return the number of mines left to guess. Number of mines - minesFound =
    *         number of mines that is left
    */
   public int numMinesLeft() {
      return mineField.numMines() - minesFound;

   }

   /**
    * Cycles through covered states for a square, updating number of guesses as
    * necessary. Call on a COVERED square changes its status to MINE_GUESS; call on
    * a MINE_GUESS square changes it to QUESTION; call on a QUESTION square changes
    * it to COVERED again; call on an uncovered square has no effect.
    * 
    * @param row row of the square
    * @param col col of the square PRE: getMineField().inRange(row, col)
    * 
    */
   public void cycleGuess(int row, int col) {
      switch (mineData[row][col]) {
         case COVERED: // If the block is COVERED, the blocks become GUESS when right clicked
            minesFound++;
            mineData[row][col] = MINE_GUESS;
            break;
         case MINE_GUESS: // If the block is a GUESS, the blocks become QUESTION when right clicked
            minesFound--;
            mineData[row][col] = QUESTION;
            break;
         case QUESTION: // If the block is a QUESTION, the blocks become COVERED when right clicked
            mineData[row][col] = COVERED;
            break;
      }

   }

   /**
    * Uncovers this square and returns false iff you uncover a mine here. If the
    * square wasn't a mine or adjacent to a mine it also recursively uncovers all
    * the squares in the neighboring area that are also not next to any mines,
    * possibly uncovering a large region. Any mine-adjacent squares you reach will
    * also be uncovered, and form (possibly along with parts of the edge of the
    * whole field) the boundary of this region. Does not uncover, or keep searching
    * through, squares that have the status MINE_GUESS. Note: this action may cause
    * the game to end: either in a win (opened all the non-mine squares) or a loss
    * (opened a mine).
    * 
    * @param row of the square
    * @param col of the square
    * @return false iff you uncover a mine at (row, col) PRE:
    *         getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {
      if (isUncovered(row, col) || mineData[row][col] == MINE_GUESS) { // make sure that we do not uncover something
                                                                       // that is already uncovered
         return true;
      }
      if (mineField.hasMine(row, col)) {
         mineData[row][col] = EXPLODED_MINE;
         return false;
      }
      mineData[row][col] = mineField.numAdjacentMines(row, col); // set covered status to the number of adjacent mines.
      if (mineData[row][col] < 1) { // If the number of Adjacment mines is 0 then call the uncover fuction on each
                                    // adjacent cells.
         if (row > 0) { // handling the top 3 cells
            if (col > 0) { // topleft
               uncover(row - 1, col - 1);   //Run the entire uncover function for the top left cell if the program is not in the top left cell
            }
            uncover(row - 1, col); // top middle
            if (col < mineField.numCols() - 1) {
               uncover(row - 1, col + 1); //Run the entire uncover function for the top right cell if the program is not in the top right cell
            }
         }
         if (row < mineField.numRows() - 1) { //Run the entire uncover function for the bottom left cell if the program is not in the top left cell
            if (col > 0) {
               uncover(row + 1, col - 1); // bottom left
            }
            uncover(row + 1, col); // bottom middle
            if (col < mineField.numCols() - 1) {
               uncover(row + 1, col + 1); // bottom right
            }
         }
         if (col > 0) {
            uncover(row, col - 1); // left
         }
         if (col < mineField.numCols() - 1) {
            uncover(row, col + 1); // right
         }
      }
      return true;
      // check to see if it's less than 0
      // check to see if mineField has a class. If it's true, set to mine
   }

   /**
    * Returns whether the game is over. (Note: This is not a mutator.)
    * 
    * @return whether game over
    */
   public boolean isGameOver() {
      int gameOver = 0;
      for (int r = 0; r < mineField.numRows(); r++) {
         for (int c = 0; c < mineField.numCols(); c++) {
            if (mineData[r][c] == EXPLODED_MINE) { // if you hit a mine
               // perform gameover action
               doGameOver();
               return true;
            }
            if (!isUncovered(r, c)) { // Still in play
               gameOver++;
            }
         }
      }

      return gameOver == mineField.numMines(); // Everything need to be uncovered except ones we know to be mines
   }

   /**
    * Returns whether this square has been uncovered. (i.e., is in any one of the
    * uncovered states, vs. any one of the covered states).
    * 
    * @param row of the square
    * @param col of the square
    * @return whether the square is uncovered PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {
      return mineData[row][col] > COVERED;
   }

   // <put private methods here>
   /**
    * This loops over minefield and shows where the mine exists. If a block is
    * guessed incorrectly, it shows the guessed icon. Used the switch statement for
    * the method. The method does not return any value and has no parameter.
    */
   private void doGameOver() {
      for (int r = 0; r < mineField.numRows(); r++) {
         for (int c = 0; c < mineField.numCols(); c++) {
            switch (mineData[r][c]) {
               case COVERED:
               case QUESTION:
                  if (mineField.hasMine(r, c)) {
                     mineData[r][c] = MINE;
                  }
                  break;
               case MINE_GUESS:
                  mineData[r][c] = mineField.hasMine(r, c) ? MINE : INCORRECT_GUESS;
                  break;
            }
         }
      }
   }

}
