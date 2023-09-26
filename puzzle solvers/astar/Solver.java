import java.util.*;

class Solver {
  PriorityQueue<Node> toVisit; // Priority Queue to hold states to visit
  HashMap<String, Integer> visited; // map to keep track of states and heuristic values
  HashMap<Integer, Coordinates> coords;
  int totalNodes; // Keeps track of the expanded nodes
  String goalState;

  // Default constructor for the solver class
  Solver() {
    this.toVisit = new PriorityQueue<>(new SortNodes());
    this.visited = new HashMap<>();
    this.coords = new HashMap<>();
    setCoords();
    this.totalNodes = 0;
    this.goalState = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0";
  }

  // Runs the A* algorithm
  Node aStar(Node initial, int h) {
    Node currNode = null;
    addToQueue(initial, h);
    toVisit.add(initial);

    while (!toVisit.isEmpty()) {
      currNode = toVisit.poll();

      if (currNode.getStateString().equals(goalState)) {
        return currNode;
      } else {
        expandNodes(currNode, h);
        totalNodes++;
      }
    }
    return null;
  }

  // expands the node by calling the different functions below
  void expandNodes(Node currState, int h) {
    moveUp(currState, h);
    moveDown(currState, h);
    moveLeft(currState, h);
    moveRight(currState, h);
  }

  // Moves the empty square up one space and adds the new state
  // to the queue
  void moveUp(Node state, int heuristic) {
    int temp = 0;
    int zero = getZeroIndex(state.getState());
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    if (zero == 0 || zero == 1 || zero == 2 || zero == 3) {
      return;
    } else {
      temp = copy.get(zero - 4);
      copy.set(zero - 4, 0);
      copy.set(zero, temp);
      String updatedPath = state.getPath() + "U";
      Node nextState = new Node(copy, updatedPath);
      nextState.setParent(state);
      nextState.setDepth(state.getDepth() + 1);
      addToQueue(nextState, heuristic);
    }
  }

  // Moves the empty square down one space and adds the new state
  // to the queue
  void moveDown(Node state, int heuristic) {
    int temp = 0;
    int zero = getZeroIndex(state.getState());
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    if (zero == 12 || zero == 13 || zero == 14 || zero == 15) {
      return;
    } else {
      temp = copy.get(zero + 4);
      copy.set(zero + 4, 0);
      copy.set(zero, temp);
      String updatedPath = state.getPath() + "D";
      Node nextState = new Node(copy, updatedPath);
      nextState.setParent(state);
      nextState.setDepth(state.getDepth() + 1);
      addToQueue(nextState, heuristic);
    }
  }

  // Moves the empty square left one space and adds the new state
  // to the queue
  void moveLeft(Node state, int heuristic) {
    int temp = 0;
    int zero = getZeroIndex(state.getState());
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    if (zero == 0 || zero == 4 || zero == 8 || zero == 12) {
      return;
    } else {
      temp = copy.get(zero - 1);
      copy.set(zero - 1, 0);
      copy.set(zero, temp);
      String updatedPath = state.getPath() + "L";
      Node nextState = new Node(copy, updatedPath);
      nextState.setParent(state);
      nextState.setDepth(state.getDepth() + 1);
      addToQueue(nextState, heuristic);
    }
  }

  // Moves the empty square right one space and adds the new state
  // to the queue
  void moveRight(Node state, int heuristic) {
    int temp = 0;
    int zero = getZeroIndex(state.getState());
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(state.getState());
    if (zero == 3 || zero == 7 || zero == 11 || zero == 15) {
      return;
    } else {
      temp = copy.get(zero + 1);
      copy.set(zero + 1, 0);
      copy.set(zero, temp);
      String updatedPath = state.getPath() + "R";
      Node nextState = new Node(copy, updatedPath);
      nextState.setParent(state);
      nextState.setDepth(state.getDepth() + 1);
      addToQueue(nextState, heuristic);
    }
  }

  // Performs the Misplaced Tile Heursitic
  void misplacedTiles(Node state) {
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
    misplaced += state.getDepth();
    state.setValue(misplaced);
  }

  // Performs the Manhattan Distance heursitic
  void manhattanDist(Node state) {
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
    distance += state.getDepth();
    state.setValue(distance);
  }

  // Adds node to queue. Node is only added if it's
  // a new state, or if the value for that state is
  // less than value of when it was last seen
  void addToQueue(Node newState, int heuristic) {
    String currState = newState.getStateString();
    if (heuristic == 1) {
      misplacedTiles(newState);
    } else {
      manhattanDist(newState);
    }
    if (visited.containsKey(currState) == false || newState.getValue() < visited.get(currState)) {
      visited.put(currState, newState.getValue());
      toVisit.add(newState);
    } else {
      return;
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
    toVisit.clear();
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