import java.util.*;

/*
CS 411 HW 06: 15 Puzzle Solver using IDA* Search
By: Alexis Escutia
NetID: aescut3
UIN: 679743479
*/


class Main {

  // Returns an array list contating the initial state
  public static ArrayList<Integer> getInput(String[] input) {
    ArrayList<Integer> puzzle = new ArrayList<>();
    for(int x = 0; x < 16; x++) {
      puzzle.add(Integer.valueOf(input[x]));
    }
    return puzzle;
  }

  // calculates the memory usage after performing A*
  public static long getMemory(long mem, Runtime process) {
    long totalMem = Math.abs((mem-process.freeMemory())/1024);  // divided by 1024 to get KB
    return totalMem;
  }

  // returns the solution path
 public static String getPath(Node solution) {
    String s = "";
    s = solution.getPath();
   //System.out.println("Printing solution, this is s: " + s);
    while(solution.getParent() != null) {
      solution = solution.getParent();
     // System.out.println("Printing solution, this is s: " + s);
      s = solution.getPath() + s;
    }
    return s;
  }
  
  public static void main(String[] args) {
    String path= "";
    Scanner scanner = new Scanner(System.in);
    Solver solver = new Solver();
    String heuristic = "";  // stores the selected heuristic
    String[] tokens = null;  // stores the tokenized version of the user input
    Node initial = null;  // Node to store the initial puzzle state
    Node solution = null;  // Node that contains the puzzle solution
    String input = "";
    long startTime = 0;  // used to store the initial time before A* is performed
    long endTime = 0;  // used to store the end time after A* is performed
    long memUsage = 0;  // used for calculating Memory usage
    Runtime process = Runtime.getRuntime();
    
    System.out.println("Welcome to the 15 Puzzle Solver using IDA*!\n");
    System.out.println("Please enter the puzzle you'd like to solve or hit enter to exit");
    System.out.print("Puzzle: ");
    input = scanner.nextLine();

    while(!(input.equals(""))) {
      tokens = input.split(" ");
      initial = new Node(getInput(tokens), "");

      System.out.println("\nPlease Select the Heuristic You Would Like to Use");
      System.out.println("Type 1 for Misplaced Tiles, 2 for Manhattan Distance");
      System.out.print("Heuristic: ");
      
      heuristic = scanner.nextLine();
      startTime = System.currentTimeMillis();
      memUsage = process.totalMemory();
      
      solution = solver.iterativeDeepeningSearch(initial, Integer.valueOf(heuristic));
      
      endTime = System.currentTimeMillis();

      if(!(solution == null)) {
        path = getPath(solution);
        System.out.println("\nThe solution to the puzzle is: " + path);
        System.out.println("Number of Nodes Expanded: " + solver.totalNodes);
        System.out.println("Total Time in Milliseconds: " + ((endTime - startTime)));
        System.out.println("Memory Used: " + getMemory(memUsage,process) + " Kbs\n\n");
      } else {
        System.out.println("I'm sorry, A solution could not be found\n\n");
      }
      
      solver.reset();
      initial = null;
      solution = null;
      System.out.println("Please enter the puzzle you'd like to solve or hit enter to exit");
      System.out.print("Puzzle: ");
      input = scanner.nextLine();
    }

    scanner.close();
    System.out.println("\n\nThank you for using the 15 Puzzle Solver. Goodbye!");
  }
}