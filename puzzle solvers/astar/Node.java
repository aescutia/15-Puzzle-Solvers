import java.util.*;

class Node {
  ArrayList<Integer> state;  // Stores the current state
  String stateString;  // String version of the current state
  String path;  // Keeps track of the moves made at the current state
  Node parent;  // keeps track of the node's parent
  int value;  // Stores the value calculated by the heuristic
  int depth;  // Stores the depth of the node
  
  Node(ArrayList<Integer> curState, String move) {
    this.state = curState;
    this.stateString ="";
    toString(state);
    this.path = move;
    this.parent = null;
    this.value = 0;
    this.depth = 0;
  }

  String getPath() {
    return this.path;
  }

  void setValue(int v) {
    this.value = v;
  }
  
  int getValue() {
    return this.value;
  }

  void setDepth(int d) {
    this.depth = d;
  }
  
  int getDepth() {
    return this.depth;
  }

  Node getParent() {
    return this.parent;
  }

  void setParent(Node p) {
    this.parent = p;
  }

  String getStateString() {
    return this.stateString;
  }

  ArrayList<Integer> getState() {
    return this.state;
  }

  // Takes the array list of the state and converts it
  // to a string
  void toString(ArrayList<Integer> theState) {
    for(int x = 0; x < 16; x++) {
      stateString += theState.get(x);
      if(!(x==15)) {
        stateString += " ";
      }
    }
  }
}


// Comparator used to compare the nodes in the Priority Queue
class SortNodes implements Comparator<Node> {
    public int compare(Node n1, Node n2)
    {
        if(n1.value > n2.value) {
          return 1;
        } else if(n1.value == n2.value) {
          return 0;
        } else {
          return -1;
        }
    }
}