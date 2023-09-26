import java.util.*;

class Solver {
  HashMap<Integer, Coordinates> coords;  // Stores coordinates for puzzle tiles
  int totalNodes; // Keeps track of the expanded nodes
  String goalState;
  boolean isGoal;  // bool flag for when goal state is reached

  // // Default constructor for the solver class
  Solver() {
    this.coords = new HashMap<>();
    setCoords();
    this.totalNodes = 0;
    this.goalState = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0";
    this.isGoal = false;
    
  }

  // Runs Iterative Deepening Search
  Node iterativeDeepeningSearch(Node initial, int h) {
    Stack<Node> path =  new Stack<>();
    int bound = 0;
    int infinity = Integer.MAX_VALUE;
    int t = 0; 
    
    if(h == 1) {
      bound = misplacedTiles(initial);
    } else {
      bound = manhattanDist(initial);
    }
    
    path.push(initial);
    while(true) {
      t = aStar(path, 0, bound, h);
      
      if(isGoal) {
        return path.pop();
      }
      if(t == infinity) {
        break;
      }
      bound = t;
      path.clear();
      path.push(initial);
    }

    return null;  // Solution wasn't be found
  }

  // Runs the A*/IDDFS algorithm
  int aStar(Stack<Node> path,int g, int bound, int h) {
    Node currNode = path.peek();
    Node succ = null;
    int t = 0;
    int hVal = 0;
    
    if(h == 1) {
      hVal = misplacedTiles(currNode);
    } else {
      hVal = manhattanDist(currNode);
    }
    
    int f = g + hVal;
    if (f > bound) {  // Past the threshold
      return f;
    }
    
    if(currNode.getStateString().equals(goalState)) {  // goal state found
      isGoal = true;
      return currNode.getValue();
    }
    
    totalNodes++;
    int min = Integer.MAX_VALUE;
    ArrayList<Node> moves = expandNodes(currNode, h, bound);  // Stores successor nodes
    for(int x = 0; x < moves.size(); x++) {
      succ = moves.get(x);
      if(path.search(succ) == -1) {  // curr node is not in the stack
        path.push(succ);
        t = aStar(path, g+1, bound, h);
        if(isGoal){
          return t;
        }
        if(t < min) {
          min = t;
        } else{
          path.pop();
        }
      }
    }
    return min;
  }

  // expands the node by calling the different functions below
  // nodes are sorted in ascending order and returned via list
  ArrayList<Node> expandNodes(Node currState, int h, int b) {
    ArrayList<Node> successors = new ArrayList<>();
    Node n = null;
    
    n = moveUp(currState, h);
    if(n != null) {
      successors.add(n);
    }
    
    n = moveDown(currState, h);
    if(n != null) {
      successors.add(n);
    }
    
    n = moveLeft(currState, h);
    if(n != null) {
      successors.add(n);
    }
    
    n = moveRight(currState, h);
    if(n != null) {
      successors.add(n);
    }

    successors.sort(new SortNodes());
    
    return successors;
  }

  // Moves the empty square up one space and returns new state
  Node moveUp(Node state, int heuristic) {
    int temp = 0;
    int zero = getZeroIndex(state.getState());
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    if (zero == 0 || zero == 1 || zero == 2 || zero == 3) {
      return null;
    } else {
      temp = copy.get(zero - 4);
      copy.set(zero - 4, 0);
      copy.set(zero, temp);
      Node nextState = new Node(copy, "U");
      nextState.setParent(state);
      nextState.setDepth(state.getDepth() + 1);
      setHeuristicValue(nextState, heuristic);
      return nextState;
    }
  }

  // Moves the empty square down one space and returns new state
  Node moveDown(Node state, int heuristic) {
    int temp = 0;
    int zero = getZeroIndex(state.getState());
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    if (zero == 12 || zero == 13 || zero == 14 || zero == 15) {
      return null;
    } else {
      temp = copy.get(zero + 4);
      copy.set(zero + 4, 0);
      copy.set(zero, temp);
      Node nextState = new Node(copy, "D");
      nextState.setParent(state);
      nextState.setDepth(state.getDepth() + 1);
      setHeuristicValue(nextState, heuristic);
      return nextState;
    }
  }

  // Moves the empty square left one space and returns new state
  Node moveLeft(Node state, int heuristic) {
    int temp = 0;
    int zero = getZeroIndex(state.getState());
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    if (zero == 0 || zero == 4 || zero == 8 || zero == 12) {
      return null;
    } else {
      temp = copy.get(zero - 1);
      copy.set(zero - 1, 0);
      copy.set(zero, temp);
      Node nextState = new Node(copy, "L");
      nextState.setParent(state);
      nextState.setDepth(state.getDepth() + 1);
      setHeuristicValue(nextState, heuristic);
      return nextState;
    }
  }

  // Moves the empty square right one space and returns new state
  Node moveRight(Node state, int heuristic) {
    int temp = 0;
    int zero = getZeroIndex(state.getState());
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    if (zero == 3 || zero == 7 || zero == 11 || zero == 15) {
      return null;
    } else {
      temp = copy.get(zero + 1);
      copy.set(zero + 1, 0);
      copy.set(zero, temp);
      Node nextState = new Node(copy, "R");
      nextState.setParent(state);
      nextState.setDepth(state.getDepth() + 1);
      setHeuristicValue(nextState, heuristic);
      return nextState;
    }
  }

  // Performs the Misplaced Tile Heursitic and returns value
  int misplacedTiles(Node state) {
    int misplaced = 0;
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    for (int x = 0; x < 16; x++) {
      if (x == 15) {
        if (copy.get(15) != 0) { // spot 15 has to be 0
          misplaced++;
        }
        continue;
      }
      if (copy.get(x) != (x + 1)) {
        misplaced++;
      }
    }
    return misplaced;
  }

  // Performs the Manhattan Distance heursitic and returns value
  int manhattanDist(Node state) {
    int currVal = 0;
    int distance = 0;
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    for (int i = 0; i < 16; i++) {
      currVal = copy.get(i);
      x2 = coords.get(currVal).getX();
      y2 = coords.get(currVal).getY();
      x1 = i / 4;
      y1 = i % 4;
      distance += Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    return distance;
  }

  // Sets the node's value equal to heuristicVal + depth
  void setHeuristicValue(Node state, int heuristic) {
    int hVal = 0;
    int g = state.getDepth();
    if (heuristic == 1) {
      hVal = misplacedTiles(state);
    } else {
      hVal = manhattanDist(state);
    }
     state.setValue(g+hVal);
     
    return;
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
    isGoal = false;
  }


  // populating the map with coordinates. The coordinates
  // correspond to the value's location in the goal state
  void setCoords() {
    coords.put(1, new Coordinates(0, 0));
    coords.put(2, new Coordinates(0, 1));
    coords.put(3, new Coordinates(0, 2));
    coords.put(4, new Coordinates(0, 3));
    coords.put(5, new Coordinates(1, 0));
    coords.put(6, new Coordinates(1, 1));
    coords.put(7, new Coordinates(1, 2));
    coords.put(8, new Coordinates(1, 3));
    coords.put(9, new Coordinates(2, 0));
    coords.put(10, new Coordinates(2, 1));
    coords.put(11, new Coordinates(2, 2));
    coords.put(12, new Coordinates(2, 3));
    coords.put(13, new Coordinates(3, 0));
    coords.put(14, new Coordinates(3, 1));
    coords.put(15, new Coordinates(3, 2));
    coords.put(0, new Coordinates(3, 3));
  }
}

// used to store the coordinates for the values on the board
class Coordinates {
  int x;
  int y;

  Coordinates(int v1, int v2) {
    this.x = v1;
    this.y = v2;
  }

  int getX() {
    return this.x;
  }

  int getY() {
    return this.y;
  }
}