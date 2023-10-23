/**
 * Author:  Sherali Ozodov
 * File name: ConnectFour.java
 * Course:  CSC 210
 *
 * The ConnectFour program implements a two-player board game
 * in which  tokens are dropped into rows and columns along a
 * vertical grid. Tokens go straight down to occupy the lowest
 * space in the column. The first player to make a horizontal,
 * vertical or diagonal line out of given target value of them
 * wins the game. If the board is filled before any player gets
 * there, the game is considered a draw.
 */

package connect;
import java.util.Scanner;
import java.util.ArrayList;

public class connectfour {

   // Here, we create constant variables height and width
   // which thay can't be changed later.
   static final int height = 6;
   static final int width = 7;

   public static void main(String[] args) {
      /**
       * The main class contains, the scanner object is created.
       * Grid of the game is assigned by creategrid() method to
       * String[][] grid.
       *
       */
      Scanner scanner = new Scanner(System.in);
      String[][] grid = creategrid();
      int target = target_check(scanner);
      int count_term = 0;
      boolean winner = false;

      //it prints pattern of the grid
      printpattern(grid);

      // 1 is added to count_term every time when a token is
      // successfully dropped. If count_term is even, dropRed()
      // method works and drop red token, Similarly, if count_term
      // is odd, dropYellow() method works and drop yellow token.
      //
      while (winner == false && count_term <= 42) {
         if (count_term % 2 == 0) {
            dropRed(grid, scanner);
         } else
            dropYellow(grid, scanner);
         count_term++;

         // winnercheck() method returns true when a player wins
         winner = winnercheck(grid, target);
         //print pattern
         printpattern(grid);

         // when winner is true and count_term is even, Yellow wins
         // since Red starts the game, and vice versa. if count_term
         // reaches the total number of places in the grid which is 42,
         // the game stops and prints "Tie game".
         if (winner == true) {
            if (count_term % 2 == 0) {
               System.out.println("Congratulations! Yellow won.");
            } else
               System.out.println("Congratulations! Red won.");
         } else if (count_term > 41) {
            System.out.println("Tie game.");
            break;
         }
      }
   }

   public static int target_check(Scanner scanner) {
      /**
       *  This method check the user selects a correct number of tokens
       *  to win. The scanner must be integer and number must be
       *  3,4 or 5. This method returns a target number. If these
       *  requirement are not satisfied, it repeats asking again and
       *  again until correct integer is typed.
       */

      int target;
      System.out.println("How many tokens are needed to win: 3, 4, or 5: ");
      // it repeats asking until snanner is integer and valid target number
      // which is 3-5
      do {
         while (!scanner.hasNextInt()) {
            System.out.println("That's not a number! Choose a number: ");
            scanner.next();
         }
         target = scanner.nextInt();

         if (target < 3 || target > 5)
            System.out.println("Invalid number! Choose 3,4 or 5: ");
      } while (target < 3 || target > 5);
      System.out.println("");

      return target;

   }
   public static String[][] creategrid() {
      /**
       This method returns a 6x7 grid and fill it with " " initially.
       For that, it creates a 2d string array and returns it.
       */
      String[][] grid = new String[height][width];

      for (int row = 0; row < grid.length; row++) {
         for (int col = 0; col < grid[0].length; col++) {
            grid[row][col] = " ";
         }
      }
      return grid;
   }

   public static void dropRed(String[][] grid, Scanner scanner) {

      /**
       * 	This method asks the red user pick a column between 1 and 7.
       * 	Then it checks the number, which is a column of the grid, is
       *  	greater that 0 and less that the number of columns in the grid.
       */

      int column;
      System.out.println("---------------");
      System.out.print("Red to play. Pick a column (1-7): ");

      // it repeats asking until snanner is integer and valid column number
      // which is 1-7
      do {
         while (!scanner.hasNextInt()) {
            System.out.println("That's not a number! Choose a number (1-7): ");
            scanner.next();
         }

         column = scanner.nextInt();
         if (column < 0 || column > grid[0].length)
            System.out.print("Invalid column. Choose another column: ");
      } while (column < 0 || column > grid[0].length);
      System.out.println("");

      // If choosen column is valid and the place is " " which is a free
      // space, it drops a red token. If the choosen place is not " ", it
      // means the column is full and within while loop, it asks to choose
      // another column that is not full.
      if (grid[0][column - 1] == " ") {
         for (int i = height - 1; i >= 0; i--) {
            if (grid[i][column - 1] == " ") {
               grid[i][column - 1] = "R";
               break;
            }
         }
      } else if (grid[0][column - 1] != " ") {
         while (grid[0][column - 1] != " ") {
            System.out.println("Full column. Choose another column: ");
            column = scanner.nextInt();
         }
         for (int i = height - 1; i >= 0; i--) {
            if (grid[i][column - 1] == " ") {
               grid[i][column - 1] = "R";
               break;
            }
         }
      }
   }

   public static void dropYellow(String[][] grid, Scanner scanner) {
      /**
       * 	This method does exactly the same think as dropRed does.
       * 	It asks the yellow user pick a column between 1 and 7.
       * 	Then it checks the number, which is a column of the grid, is
       *  greater that 0 and less that the number of columns in the grid.
       */

      int column;
      System.out.println("---------------");
      System.out.print("Yellow to play. Pick a column (1-7): ");
      // it repeats asking until snanner is integer and valid column number
      // which is 1-7
      do {
         while (!scanner.hasNextInt()) {
            System.out.println("That's not a number! Choose a number: (1-7) ");
            scanner.next(); // this is important!
         }

         column = scanner.nextInt();
         if (column < 0 || column > grid[0].length)
            System.out.println("Invalid column. Choose another column: ");
      } while (column < 0 || column > grid[0].length);
      System.out.println("");

      // If choosen column is valid and the place is " " which is a
      // free space,it drops a yellow token. If the choosen place is not
      // " ", it means the column is full and within while loop, it asks
      // to choose another column that is not full.
      if (grid[0][column - 1] == " ") {
         for (int i = height - 1; i >= 0; i--) {
            if (grid[i][column - 1] == " ") {
               grid[i][column - 1] = "Y";
               break;
            }
         }
      } else if (grid[0][column - 1] != " ") {
         while (grid[0][column] != " ") {
            System.out.print("Full column. Choose another column: ");
            column = scanner.nextInt();
         }
         for (int i = height - 1; i >= 0; i--) {
            if (grid[i][column - 1] == " ") {
               grid[i][column - 1] = "Y";
               break;
            }
         }
      }
   }

   public static void printpattern(String[][] grid) {
      /**
       *  This method loops throught each column of the grid
       *  and prints the elements with "|".
       */
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[0].length; j++) {
            System.out.print("|" + grid[i][j]);
         }
         System.out.print("|");
         System.out.println();
      }
   }

   public static boolean winnercheck(String[][] grid, int target) {
      /**
       *  This method checks the player to make a horizontal,
       *  vertical or diagonal line out of given target value.
       *  If there is a winner, it returns true. Otherwise, it
       *  returns false.
       */
      String current_player = " ";

      //check for across
      // it loops throught each columns and row of the grid.
      for (int a = 0; a < grid.length; a++) {
         for (int b = 0; b < grid[a].length - target + 1; b++) {
            // Them it assign the current row to the current_player variable.
            current_player = grid[a][b];
            // After creating tow arraylist player_grid and target_grid, it
            // loops throught according to target variable and adds rows to
            // target_grid arraylist to check for across. At the same time, it
            // adds current_player to player_grid arraylist acorting to target
            // target variable.
            ArrayList < String > target_grid = new ArrayList < String > ();
            ArrayList < String > player_grid = new ArrayList < String > ();
            for (int target_count = 0; target_count < target; target_count++) {
               target_grid.add(grid[a][b + target_count]);
               player_grid.add(current_player);
            }
            // Then it compares these arraylists to each other, and
            // if they are similar filled by not " ", it means there is winner.
            // Then it returns true. Otherwise it returns false.
            if (current_player != " ") {
               if (player_grid.equals(target_grid)) {
                  return true;
               }
            }
         }
      }
      //check for up and down
      // it loops throught each columns and row of the grid.
      for (int a = 0; a < grid.length - target + 1; a++) {
         for (int b = 0; b < grid[a].length; b++) {
            // Them it assign the current row to the current_player variable.
            current_player = grid[a][b];
            // After creating tow arraylist player_grid and target_grid, it
            // loops throught according to target variable and adds rows to
            // target_grid arraylist to check for up and down. At the same time, it
            // adds current_player to player_grid arraylist acorting to target target
            // variable.
            ArrayList < String > target_grid = new ArrayList < String > ();
            ArrayList < String > player_grid = new ArrayList < String > ();
            for (int target_count = 0; target_count < target; target_count++) {
               target_grid.add(grid[a + target_count][b]);
               player_grid.add(current_player);
            }
            // Then it compares these arraylists to each other, and
            // if they are similar filled by not " ", it means there is winner.
            // Then it returns true. Otherwise it returns false.
            if (current_player != " ") {
               if (player_grid.equals(target_grid)) {
                  return true;
               }
            }
         }
      }
      //check upward diagonal
      // it loops throught each columns and row of the grid.
      for (int a = target - 1; a < grid.length; a++) {
         for (int b = 0; b < grid[a].length - target + 1; b++) {
            // Them it assign the current row to the current_player variable.
            current_player = grid[a][b];
            // After creating tow arraylist player_grid and target_grid, it
            // loops throught according to target variable and adds rows to
            // target_grid arraylist to check for upward diagnol. At the same
            // time, it adds current_player to player_grid arraylist acorting
            // to target target variable.
            ArrayList < String > target_grid = new ArrayList < String > ();
            ArrayList < String > player_grid = new ArrayList < String > ();
            for (int target_count = 0; target_count < target; target_count++) {
               target_grid.add(grid[a - target_count][b + target_count]);
               player_grid.add(current_player);
            }
            // Then it compares these arraylists to each other, and
            // if they are similar filled by not " ", it means there is winner.
            // Then it returns true. Otherwise it returns false.
            if (current_player != " ") {
               if (player_grid.equals(target_grid)) {
                  return true;
               }
            }
         }
      }
      //check downward diagonal
      // it loops throught each columns and row of the grid.
      for (int a = 0; a < grid.length - target + 1; a++) {
         for (int b = 0; b < grid[a].length - target + 1; b++) {
            // Them it assign the current row to the current_player variable.
            current_player = grid[a][b];
            // After creating tow arraylist player_grid and target_grid, it
            // loops throught according to target variable and adds rows to
            // target_grid arraylist to check for downward diagonal. At the same
            // time, it adds current_player to player_grid arraylist acorting
            // to target target variable.
            ArrayList < String > target_grid = new ArrayList < String > ();
            ArrayList < String > player_grid = new ArrayList < String > ();
            for (int target_count = 0; target_count < target; target_count++) {
               target_grid.add(grid[a + target_count][b + target_count]);
               player_grid.add(current_player);
            }
            // Then it compares these arraylists to each other, and
            // if they are similar filled by not " ", it means there is winner.
            // Then it returns true. Otherwise it returns false.
            if (current_player != " ") {
               if (player_grid.equals(target_grid)) {
                  return true;
               }
            }
         }
      }
      return false;
   }
}