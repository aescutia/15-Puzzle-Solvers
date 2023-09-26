import java.util.*;

/*
CS 411 HW 04 15 Puzzle Solver using IDDFS
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

  // calculates the memory usage after performing bfs
  public static long getMemory(long mem, Runtime process) {
    long totalMem = Math.abs((mem-process.freeMemory())/1024);  // divided by 1024 to get KB
    return totalMem;
  }
  
  public static void main(String[] args) {
    
    Scanner scanner = new Scanner(System.in);
    Solver solver = new Solver();
    String[] tokens = null;  // stores the tokenized version of the user input
    Node initial = null;  // Node to store the initial puzzle state
    Node solution = null;  // Node that contains the puzzle solution
    String input = "";
    long startTime = 0;  // used to store the initial time before bfs is performed
    long endTime = 0;  // used to store the end time after bfs is performed
    long memUsage = 0;  // used for calculating Memory usage
    Runtime process = Runtime.getRuntime();
    
    System.out.println("Welcome to the 15 Puzzle Solver!\n");
    System.out.println("Please enter the puzzle you'd like to solve or hit enter to exit");
    System.out.print("Puzzle: ");
    input = scanner.nextLine();

    while(!(input.equals(""))) {
      tokens = input.split(" ");
      
      initial = new Node(getInput(tokens), "");
      initial.setDepth(0);
      startTime = System.currentTimeMillis();
      memUsage = process.totalMemory();
      
      solution = solver.iterativeDeepeningSearch(initial);
      endTime = System.currentTimeMillis();

      if(!(solution == null)) {
        System.out.println("The solution to the puzzle is: " + solution.getPath());
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