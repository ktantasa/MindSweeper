public class MineFieldTester{

   private static void Assert(int expected, int actual, String name){
      System.out.printf("%s - expected: %d actual: %d -- %s\n",name,expected,actual,(expected == actual ? "PASSED" : "FAILED"));
   }
   private static void AssertTrue(boolean actual, String name){
      System.out.printf("%s - expected: true actual: %b -- %s\n",name,actual,(actual ? "PASSED" : "FAILED"));
   }
   
   private static void AssertFalse(boolean actual, String name){
      System.out.printf("%s - expected: false actual: %b -- %s\n",name,actual,(!actual ? "PASSED" : "FAILED"));
   }
   
   public static void TestThreeArgs(int rows, int cols, int mines){
      MineField threeArg = new MineField(rows,cols,mines);
      
      System.out.printf("Testing three Arguement Constructor (%dx%d - %d)\n",rows,cols,mines);
      Assert(rows,threeArg.numRows(),"Number of Rows");
      Assert(cols,threeArg.numCols(),"Number of Cols");
      Assert(mines,threeArg.numMines(),"Number of Mines");
      System.out.println();
   }
   
   public static void testPopulate(MineField field){
      field.populateMineField(0,0);
      int count = 0;
      for(int r =0;r<field.numRows();r++){
         for(int c =0;c<field.numCols();c++){
            if(field.hasMine(r,c)){
               count++;
            }
         }
      }
      Assert(field.numMines(), count, "Populated Correct Mine Count");
   }
   public static void main(String[] args){
      
      System.out.println();
      
      TestThreeArgs(1,1,0);
      
      TestThreeArgs(4,4,5);
     
      
      boolean [][] testData = new boolean[4][];
      for(int i = 0;i<4;i++){
         testData[i] = new boolean[4];
      }
      
      
      MineField oneArg = new MineField(testData);
      
      System.out.println("Testing One Arguement Constructor (4x4 - 0)");
      Assert(4,oneArg.numRows(),"Number of Rows");
      Assert(4,oneArg.numCols(),"Number of Cols");
      Assert(0,oneArg.numMines(),"Number of Mines");
      AssertTrue(oneArg.inRange(0,0),"inRange Test 0,0");
      AssertTrue(oneArg.inRange(3,3),"inRange Test 3,3");
      AssertTrue(oneArg.inRange(1,2),"inRange Test 1,2");
      AssertFalse(oneArg.inRange(-1,2),"inRange Test -1,2");
      AssertFalse(oneArg.inRange(1,-2),"inRange Test 1,-2");
      AssertFalse(oneArg.inRange(4,0),"inRange Test 4,0");
      AssertFalse(oneArg.inRange(0,4),"inRange Test 0,4");
      
      
      testPopulate(oneArg);
      testPopulate(new MineField(4,4,5));
      testPopulate(new MineField(9,9,20));
   }


}