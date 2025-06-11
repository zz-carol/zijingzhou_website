import java.io.*;
import java.util.*;

/* THIS CODE WAS MY (OUR) OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
    CODE WRITTEN BY OTHER STUDENTS OR ONLINE RESOURCES.
    Carol Zhou */

/*
 * self-referential class to represent a position in a path
 */
class Position{
    public int i;     //row
    public int j;     //column
    public char val;  //1, 0, or 'X'

    // reference to the previous position (parent) that leads to this position on a path
    Position parent;

    Position(int x, int y, char v){
        i=x; j = y; val=v;
    }

    Position(int x, int y, char v, Position p){
        i=x; j = y; val=v;
        parent=p;
    }

}

public class PathFinder {

    // main method: reads in maze file and finds path using both stackSearch and queueSearch
    public static void main(String[] args) throws IOException {
        if(args.length<1){
            System.err.println("***Usage: java PathFinder maze_file");
            System.exit(-1);
        }

        char [][] maze;
        maze = readMaze(args[0]);
        printMaze(maze);
        Position [] path = stackSearch(maze);
        if( path == null ){
            System.out.println("Maze is NOT solvable (no valid path identified in stackSearch).");
        } else {
            System.out.println("stackSearch Solution:");
            printPath(path);
            printMaze(maze);
        }

        char [][] maze2 = readMaze(args[0]);
        path = queueSearch(maze2); 
        if( path == null ){
            System.out.println("Maze is NOT solvable (no valid path identified in queueSearch).");
        } else {
            System.out.println("queueSearch Solution:");
            printPath(path);
            printMaze(maze2);
        }
    }

    public static Position [] queueSearch(char [][] maze) {
        ArrayDeque<Position> yetToExplore = new ArrayDeque<Position>(); // search list
        Position entrance = new Position(0, 0, '0'); 
        yetToExplore.addFirst(entrance); // add the entrance position to search list
        while(!(yetToExplore.isEmpty())) {
            Position nextPosition = yetToExplore.removeFirst(); // remove next position from search list
            if((nextPosition.i==maze.length-1) && (nextPosition.j==maze[0].length-1)) {
                ArrayDeque<Position> vistiedPosition = new ArrayDeque<Position>(); // a visted position list
                while(!(nextPosition==null)) {
                    maze[nextPosition.i][nextPosition.j] = 'X'; // Mark the current position as X
                    vistiedPosition.addFirst(nextPosition); // Add the current position to the visted position list
                    nextPosition = nextPosition.parent; // Update the current position to previous (parent) position
                }
                Position [] solution = new Position[vistiedPosition.size()];
                for (int k = 0; k < solution.length; k++) {
                    solution[k] = vistiedPosition.removeFirst();
                } // put visited positions in an array ordered from the start point to exit point
                return solution;
            } else {
                maze[nextPosition.i][nextPosition.j] = '.'; // mark the position as visited
                if((nextPosition.i+1 <= maze.length-1) && (maze[nextPosition.i+1][nextPosition.j]=='0')) {
                    yetToExplore.addLast(new Position(nextPosition.i+1, nextPosition.j, '0', nextPosition));
                } // add valid down neighbor position to the search list
                if((nextPosition.i-1 >= 0) && (maze[nextPosition.i-1][nextPosition.j]=='0')) {
                    yetToExplore.addLast(new Position(nextPosition.i-1, nextPosition.j, '0', nextPosition));
                } // add valid up neighbor position to the search list
                if((nextPosition.j+1 <= maze[0].length-1) && (maze[nextPosition.i][nextPosition.j+1]=='0')) {
                    yetToExplore.addLast(new Position(nextPosition.i, nextPosition.j+1, '0', nextPosition));
                } // add valid right neighbor position to the search list
                if((nextPosition.j-1 >= 0) && (maze[nextPosition.i][nextPosition.j-1]=='0')) {
                    yetToExplore.addLast(new Position(nextPosition.i, nextPosition.j-1, '0', nextPosition));
                } // add valid left neighbor position to the search list
            }
        } 
        return null; // return null if the list is empty and the method has not returned
    }

    public static Position [] stackSearch(char [][] maze) {
        Stack<Position> yetToExplore = new Stack<Position>(); // search list
        Position entrance = new Position(0, 0, '0');
        yetToExplore.push(entrance); // add the entrance position to search list
        while(!yetToExplore.empty()) {
            Position nextPosition = yetToExplore.pop(); // remove next position from search list
            if((nextPosition.i==maze.length-1) && (nextPosition.j==maze[0].length-1)) {
                Stack<Position> vistiedPosition = new Stack<Position>(); // a visted position list
                while(!(nextPosition==null)) {
                    maze[nextPosition.i][nextPosition.j] = 'X'; // Mark the current position as X
                    vistiedPosition.push(nextPosition); // Add the current position to the visted position list
                    nextPosition = nextPosition.parent; // Update the current position to previous (parent) position
                }
                Position [] solution = new Position[vistiedPosition.size()];
                for (int k = 0; k < solution.length; k++) {
                    solution[k] = vistiedPosition.pop();
                } // put visited positions in an array ordered from the start point to exit point
                return solution;
            } else {
                maze[nextPosition.i][nextPosition.j] = '.'; // mark the position as visited
                if((nextPosition.i+1 <= maze.length-1) && (maze[nextPosition.i+1][nextPosition.j]=='0')) {
                    yetToExplore.push(new Position(nextPosition.i+1, nextPosition.j, '0', nextPosition));
                } // add valid down neighbor position to the search list
                if((nextPosition.i-1 >= 0) && (maze[nextPosition.i-1][nextPosition.j]=='0')) {
                    yetToExplore.push(new Position(nextPosition.i-1, nextPosition.j, '0', nextPosition));
                } // add valid up neighbor position to the search list
                if((nextPosition.j+1 <= maze[0].length-1) && (maze[nextPosition.i][nextPosition.j+1]=='0')) {
                    yetToExplore.push(new Position(nextPosition.i, nextPosition.j+1, '0', nextPosition));
                } // add valid right neighbor position to the search list
                if((nextPosition.j-1 >= 0) && (maze[nextPosition.i][nextPosition.j-1]=='0')) {
                    yetToExplore.push(new Position(nextPosition.i, nextPosition.j-1, '0', nextPosition));
                } // add valid left neighbor position to the search list
            }
        } 
        return null; // return null if the list is empty and the method has not returned
    }

    // prints path through maze
    public static void printPath(Position [] path){
        System.out.print("Path: ");
        for(Position p : path){
            System.out.print("(" + p.i + "," + p.j + ") ");
        }
        System.out.println();
    }

    // reads in maze from file
    public static char [][] readMaze(String filename) throws IOException{
        char [][] maze;
        Scanner scanner;
        try{
            scanner = new Scanner(new FileInputStream(filename));
        }
        catch(IOException ex){
            System.err.println("*** Invalid filename: " + filename);
            return null;
        }

        int N = scanner.nextInt();
        scanner.nextLine();
        maze = new char[N][N];
        int i=0;
        while(i < N && scanner.hasNext()){
            String line =  scanner.nextLine();
            String [] tokens = line.split("\\s+");
            int j = 0;
            for (; j< tokens.length; j++){
                maze[i][j] = tokens[j].charAt(0);
            }
            if(j!=N){
                System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
                return null;
            }
            i++;
        }
        if(i!=N){
            System.err.println("*** Invalid file: has wrong number of rows: " + i);
            return null;
        }
        return maze;
    }

    // prints maze array
    public static void printMaze(char[][] maze){
        System.out.println("Maze: ");
        if(maze==null || maze[0] == null){
            System.err.println("*** Invalid maze array");
            return;
        }

        for(int i=0; i< maze.length; i++){
            for(int j = 0; j< maze[0].length; j++){
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

}
