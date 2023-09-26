import java.util.*;

class Solver {
  Queue<Node> toVisit;  // Queue to hold states to visit
  HashSet<String> visited;  // Set that keeps track of visited states
  int totalNodes;
  String goalState;

  // Default constructor for the solver class
  Solver() {
    this.toVisit = new LinkedList<>();
    this.visited = new HashSet<>();
    this.totalNodes = 0;
    this.goalState = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0";
  }

  // testing with this: 1 0 2 4 5 7 3 8 9 6 11 12 13 10 14 15
  // Performs a breadth-first search to solve the inputted puzzle
  Node bfs(Node initial) {
    Node currNode = null;
    if (initial.getStateString().equals(goalState)) {
      return initial;
    } 
    visited.add(initial.getStateString());
    toVisit.add(initial);
    while (!(toVisit.isEmpty())) {
      currNode = toVisit.remove();
      totalNodes++;
      
      if (currNode.getStateString().equals(goalState)) {
        return currNode;
      } else {
        expandNodes(currNode);
        visited.add(currNode.getStateString());
      }

    }
    return null;
  }

  // Finds the moves that can be made in teh current state
  // by calling the functions below
  void expandNodes(Node currState) {
    moveUp(currState.getState(), currState.getPath());
    moveDown(currState.getState(), currState.getPath());
    moveLeft(currState.getState(), currState.getPath());
    moveRight(currState.getState(), currState.getPath());
  }


  // Moves the empty square up one space and adds the new state
  // to the queue
  void moveUp(ArrayList<Integer> state, String currPath) {
    int temp = 0;
    int zero = getZeroIndex(state);
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state);
    if (zero == 0 || zero == 1 || zero == 2 || zero == 3) {
      return;
    } else {
      temp = copy.get(zero - 4);
      copy.set(zero - 4, 0);
      copy.set(zero, temp);
      String updatedPath = currPath + "U";
      Node nextState = new Node(copy, updatedPath);
      if (visited.contains(nextState.getStateString())) {
        return;
      } else {
        toVisit.add(nextState);
      }
    }
  }

  // Moves the empty square down one space and adds the new state
  // to the queue
  void moveDown(ArrayList<Integer> state, String currPath) {
    int temp = 0;
    int zero = getZeroIndex(state);
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state);
    if (zero == 12 || zero == 13 || zero == 14 || zero == 15) {
      return;
    } else {
      temp = copy.get(zero + 4);
      copy.set(zero + 4, 0);
      copy.set(zero, temp);
      String updatedPath = currPath + "D";
      Node nextState = new Node(copy, updatedPath);
      if (visited.contains(nextState.getStateString())) {
        return;
      } else {
        toVisit.add(nextState);
      }
    }
  }

  // Moves the empty square left one space and adds the new state
  // to the queue
  void moveLeft(ArrayList<Integer> state, String currPath){
    int temp = 0;
    int zero = getZeroIndex(state);
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state);
    if (zero == 0 || zero == 4 || zero == 8 || zero == 12) {
      return;
    } else {
      temp = copy.get(zero - 1);
      copy.set(zero - 1, 0);
      copy.set(zero, temp);
      String updatedPath = currPath + "L";
      Node nextState = new Node(copy, updatedPath);
      if (visited.contains(nextState.getStateString())) {
        return;
      } else {
        toVisit.add(nextState);
      }
    }
  }

  // Moves the empty square right one space and adds the new state
  // to the queue
  void moveRight(ArrayList<Integer> state, String currPath) {
    int temp = 0;
    int zero = getZeroIndex(state);
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state);
    if (zero == 3 || zero == 7 || zero == 11 || zero == 15) {
      return;
    } else {
      temp = copy.get(zero + 1);
      copy.set(zero + 1, 0);
      copy.set(zero, temp);
      String updatedPath = currPath + "R";
      Node nextState = new Node(copy, updatedPath);
      if (visited.contains(nextState.getStateString())) {
        return;
      } else {
        toVisit.add(nextState);
      }
    }
  }

  // This function returns the index where the empty square
  // is located
  int getZeroIndex(ArrayList<Integer> state) {
    int zeroIndex = 0;
    for (int x = 0; x < 16; x++) {
      if (state.get(x) == 0) {
        zeroIndex = x;
        break;
      }
    }
    return zeroIndex;
  }

  // Resets the solver for the next Puzzle
  void reset() {
    totalNodes = 0;
    visited.clear();
    while(!(toVisit.isEmpty())) {
      toVisit.remove();
    }
  }
  
}