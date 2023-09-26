import java.util.*;

class Node {
  ArrayList<Integer> state;  // Stores the current state
  String stateString;  // String version of the current state
  String path;  // Keeps track of the moves made at the current state

  Node(ArrayList<Integer> curState, String move) {
    this.state = curState;
    this.stateString ="";
    toString(state);
    this.path = move;
  }

  String getPath() {
    return this.path;
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