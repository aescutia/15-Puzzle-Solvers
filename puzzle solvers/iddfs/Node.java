import java.util.*;

class Node {
  ArrayList<Integer> state;  // Stores the current state
  String stateString;  // String version of the current state
  String path;  // Keeps track of the moves made at the current state
  int depth;  // keeps track of the node's depth
  Node parent;  // keeps track of the node's parent

  Node(ArrayList<Integer> curState, String move) {
    this.state = curState;
    this.stateString ="";
    toString(state);
    this.path = move;
    this.depth = 0;
    this.parent = null;
  }

  String getPath() {
    return this.path;
  }

  int getDepth() {
    return this.depth;
  }

  void setDepth(int d) {
    this.depth = d;
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