import java.util.*;

class Solver {
  Stack<Node> toVisit;  // Stack to hold states to visit
  int totalNodes;  // Keeps track of the expanded nodes
  String goalState;
  boolean cutoff;  // flag indicating that the solution is in a deeper depth
  
  // Default constructor for the solver class
  Solver() {
    this.toVisit = new Stack<>();
    this.totalNodes = 0;
    this.goalState = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0";
    this.cutoff = false;
  }

  // Runs the IDDFS algorithm
  Node iterativeDeepeningSearch(Node initial) {
    Node result = null;
    int infinity = Integer.MAX_VALUE;
    int depth = 0;
    while(depth < infinity) {
      result = dfs(initial, depth);
      if(cutoff == false) {
        return result;
      }
      depth++;
      toVisit.clear();
    }
    return result;
  }

  // performs a dfs to find the goal state
  Node dfs(Node initial, int depthLimit) {
    toVisit.push(initial);
    Node result = null;
    Node currNode = null;
    
    while(!(toVisit.isEmpty())) {
      currNode = toVisit.pop();
      totalNodes++;
      
      if (currNode.getStateString().equals(goalState)) {
        if(cutoff) {
          cutoff = false;
        }
        return currNode;
      }

      if(currNode.getDepth() > depthLimit) {
        cutoff = true;  // solution is in a different depth
      } else {
        if(!(isCycle(currNode))) {
          expandNodes(currNode);
        }
      }
    }
    return result;
  }


  // checks to see if there is a cycle with the path that was taken
  boolean isCycle(Node currState) {
    Node parent = currState.getParent();
    HashSet<String> visited = new HashSet<>();  // stores the states that were already visited
    visited.add(currState.getStateString());
    while(parent != null) {
      if(visited.contains(parent.getStateString())) {
        return true;
      } else {
        parent = parent.getParent();
      }
    }
    return false;
  }

  // expands teh node by calling the different functions below
  void expandNodes(Node currState) {
    moveUp(currState.getState(), currState.getPath(), currState.getDepth());
    moveDown(currState.getState(), currState.getPath(), currState.getDepth());
    moveLeft(currState.getState(), currState.getPath(), currState.getDepth());
    moveRight(currState.getState(), currState.getPath(), currState.getDepth());
  }


  // Moves the empty square up one space and adds the new state
  // to the queue
  void moveUp(ArrayList<Integer> state, String currPath, int currDepth) {
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
      nextState.setDepth(currDepth+1);
      toVisit.push(nextState);
    }
  }

  // Moves the empty square down one space and adds the new state
  // to the queue
  void moveDown(ArrayList<Integer> state, String currPath, int currDepth) {
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
      nextState.setDepth(currDepth+1);
      toVisit.push(nextState);
    }
  }

  // Moves the empty square left one space and adds the new state
  // to the queue
  void moveLeft(ArrayList<Integer> state, String currPath, int currDepth){
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
      nextState.setDepth(currDepth+1);
      toVisit.push(nextState);
    }
  }

  // Moves the empty square right one space and adds the new state
  // to the queue
  void moveRight(ArrayList<Integer> state, String currPath, int currDepth) {
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
      nextState.setDepth(currDepth+1);
      toVisit.push(nextState);
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
    while(!(toVisit.isEmpty())) {
      toVisit.pop();
    }
  }
}