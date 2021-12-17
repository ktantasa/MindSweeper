public class VisibleFieldTester{

    private static void Assert(int expected, int actual, String name){
      System.out.printf("%s - expected: %d actual: %d -- %s\n",name,expected,actual,(expected == actual ? "PASSED" : "FAILED"));
   }
   private static void AssertTrue(boolean actual, String name){
      System.out.printf("%s - expected: true actual: %b -- %s\n",name,actual,(actual ? "PASSED" : "FAILED"));
   }
   
   private static void AssertFalse(boolean actual, String name){
      System.out.printf("%s - expected: false actual: %b -- %s\n",name,actual,(!actual ? "PASSED" : "FAILED"));
   }
    private static void AssertGreaterThan(int expected, int actual, String name){
      System.out.printf("%s - expected: %d actual: %d -- %s\n",name,expected,actual,(actual > expected ? "PASSED" : "FAILED"));
   }
   
   private static void testUncoverGoesAroundGuesses(){
      boolean [][] testData = new boolean[4][];
      for(int i = 0;i<4;i++){
         testData[i] = new boolean[4];
      }
      testData[0][0] = true;
      VisibleField field = new VisibleField(new MineField(testData));
      field.cycleGuess(1,1);
      field.cycleGuess(2,2);
      field.cycleGuess(2,2);
      field.uncover(3,3);
      for(int r =0;r<4;r++){
         for(int c =0;c<4;c++){
            if(r==0 && c==0){
               Assert(VisibleField.COVERED, field.getStatus(r,c), "Bomb Location");
            }
            else if(r==1 && c==1){
               Assert(VisibleField.MINE_GUESS, field.getStatus(r,c), "Guess Location");
            }
            else if(r==2 && c==2){
               AssertGreaterThan(VisibleField.COVERED, field.getStatus(r,c), "Question Location");
            }
            else{
               AssertGreaterThan(VisibleField.COVERED, field.getStatus(r,c), "Uncovered Location");
            }
         }
      }
      
   }
   private static void testLoseCondition(){
      boolean [][] testData = new boolean[4][];
      for(int i = 0;i<4;i++){
         testData[i] = new boolean[4];
      }
      testData[0][0] = true;
      VisibleField field = new VisibleField(new MineField(testData));
      field.cycleGuess(1,1);
      field.cycleGuess(2,2);
      field.cycleGuess(2,2);
      field.uncover(1,1);
      AssertFalse(field.isGameOver(), "Does not lose when clicking an adjacent square");
      field.uncover(0,0);
      AssertTrue(field.isGameOver(), "Does lose when clicking a bomb");
      
      
   }
   private static void testWinCondition(){
      boolean [][] testData = new boolean[4][];
      for(int i = 0;i<4;i++){
         testData[i] = new boolean[4];
      }
      testData[0][0] = true;
      VisibleField field = new VisibleField(new MineField(testData));
      field.uncover(1,1);
      AssertFalse(field.isGameOver(), "Does not win when clicking an adjacent square");
      field.uncover(3,3);
      AssertTrue(field.isGameOver(), "Does win when clearing the field");
      
      
   }
   private static void testResetDisplay(){
      boolean [][] testData = new boolean[4][];
      for(int i = 0;i<4;i++){
         testData[i] = new boolean[4];
      }
      testData[0][0] = true;
      VisibleField field = new VisibleField(new MineField(testData));
      field.uncover(3,3);
      Assert(0,field.getStatus(3,3), "Uncovers call success");
      field.resetGameDisplay();
      Assert(VisibleField.COVERED,field.getStatus(3,3), "Reset Game Display success");
      
      
   }
   
   public static void main(String[] args){
      System.out.println("Constructor Test");
      
      VisibleField field = new VisibleField(new MineField(6,6,5));
      Assert(5,field.numMinesLeft(), "Number of Mines Left");
      
      testUncoverGoesAroundGuesses();
      testLoseCondition();
      testWinCondition();
      testResetDisplay();
      
   }
}