# 15-Puzzle-Solvers
This repository contains four versions of my 15-Puzzle Solver. I made these solvers while taking an Artificial Intelligence course at my college, and each solver implements a different search algorithm. These search algorithms include Breadth-First Search(BFS), Iterative Deepening Depth-First Search(IDDFS), A*, and Iterative Deepening A*(IDA*).

## About The Project:
The  purpose of these solvers is to observe how efficient each search algorithm is in terms of time and space. The order starts off with the BFS solver, which takes the most time and space compared to the others. The rest use less time and space, with the IDA* solver being the most efficient. All of the solvers were written using Java version "11.0.12." Main.java contains the main function where the user is asked to enter a puzzle to solve, Node.java contains the class definition for creating nodes within the project, and Solver.java contains the class definition and methods used to solve the puzzle.

## How to use:
- To run the solvers, a Java compiler and Java version 11.02.12 or higher are required. Once installed, navigate to the directory using the terminal and type the following command to create the executable: javac Main.java Node.java Solver.java

- Once the executable is created, you can run the program. When running the program, you will be asked to input the puzzle you would like to solve. Enter a puzzle using a sequence of numbers ranging from 0-15. Make sure that the numbers are spaced out by one whitespace character.
  - Example: 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 0 15

- Once you have typed in the sequence, hit enter and the output will be printed. The A* and IDA* solvers will have an extra prompt requiring you to type in a 1 or 2 to select the heuristic you wish to use. If there is a solution, you will see the movements that were made to solve the puzzle. You will also see information regarding the search for that puzzle such as the number of nodes expanded, the runtime in milliseconds, and the memory usage in Kilobytes. If no solution is found, a message will be returned saying that a solution could not be found.

- Once the output is printed, a prompt will appear asking you to enter a new puzzle. If you wish to solve a new puzzle, go ahead and input a new puzzle. If not, Hit enter to exit the program


