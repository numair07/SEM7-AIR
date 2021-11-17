import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

class HillClimbingAlgorithm {

    Scanner scanner;
    int[][] entryState;
    int[][] goalState;
    int[][] currentState;

    public HillClimbingAlgorithm() {
        scanner = new Scanner(System.in);
        entryState = new int[3][3];
        goalState = new int[3][3];
        currentState = new int[3][3];
        for(int i=0; i<3; i++) {
            for(int j=0; j<3;j++) {
                entryState[i][j] = goalState[i][j] = currentState[i][j] = 0;
            }
        }
    }

    public boolean checkInput(int[][] matrix) {
        int zeroCount = 0;
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if(matrix[i][j] > 8 || matrix[i][j] < 0) {
                    return false;
                }
                else if(matrix[i][j] == 0) {
                    zeroCount += 1;
                }
            }
        }
        return zeroCount > 1 || zeroCount == 0 ? false : true;
    }

    public void assignCurrentState(int[][] matrix) {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                currentState[i][j] = matrix[i][j];
            }
        }
    }

    public void printCurrentState() {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                System.out.print(currentState[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int generateHeuristic(int[][] matrix) {
        int heuristicValue = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(matrix[i][j] != goalState[i][j])
                    heuristicValue++;
            }
        }
        return heuristicValue;
    }

    public void run() {
        System.out.println("Enter the Entry State [3]x[3]");
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                currentState[i][j] = entryState[i][j] = scanner.nextInt();
            }
        }
        if(!checkInput(entryState)) {
            System.out.println("INVALID STATE");
            return;
        }

        System.out.println("Enter the Goal State [3]x[3]");
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                goalState[i][j] = scanner.nextInt();
            }
        }
        if(!checkInput(goalState)) {
            System.out.println("INVALID STATE");
            return;
        }

        System.out.println("------");
        printCurrentState();

        int[][] nextState;
        while(generateHeuristic(currentState) > 0) {
            int iIndex = 0, jIndex = 0;
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    if(currentState[i][j] == 0) {
                        iIndex = i;
                        jIndex = j;
                        break;
                    }
                }
            }
            nextState = new int[3][3];
            int temp = 0;
            if(iIndex >= 1) {
                for(int i=0; i<3; i++) {
                    for(int j=0; j<3; j++) {
                        nextState[i][j] = currentState[i][j];
                    }
                }
                temp = nextState[iIndex-1][jIndex];
                nextState[iIndex - 1][jIndex] = 0;
                nextState[iIndex][jIndex] = temp;
                if(generateHeuristic(nextState) < generateHeuristic(currentState)) {
                    assignCurrentState(nextState);
                    printCurrentState();
                    continue;
                }
            }
            if(iIndex <= 1) {
                for(int i=0; i<3; i++) {
                    for(int j=0; j<3; j++) {
                        nextState[i][j] = currentState[i][j];
                    }
                }
                temp = nextState[iIndex + 1][jIndex];
                nextState[iIndex + 1][jIndex] = 0;
                nextState[iIndex][jIndex] = temp;
                if(generateHeuristic(nextState) < generateHeuristic(currentState)) {
                    assignCurrentState(nextState);
                    printCurrentState();
                    continue;
                }
            }
            if(jIndex >= 1) {
                for(int i=0; i<3; i++) {
                    for(int j=0; j<3; j++) {
                        nextState[i][j] = currentState[i][j];
                    }
                }
                temp = nextState[iIndex][jIndex - 1];
                nextState[iIndex][jIndex - 1] = 0;
                nextState[iIndex][jIndex] = temp;
                if(generateHeuristic(nextState) < generateHeuristic(currentState)) {
                    assignCurrentState(nextState);
                    printCurrentState();
                    continue;
                }
            }
            if(jIndex <= 1) {
                for(int i=0; i<3; i++) {
                    for(int j=0; j<3; j++) {
                        nextState[i][j] = currentState[i][j];
                    }
                }
                temp = nextState[iIndex][jIndex + 1];
                nextState[iIndex][jIndex + 1] = 0;
                nextState[iIndex][jIndex] = temp;
                if(generateHeuristic(nextState) < generateHeuristic(currentState)) {
                    assignCurrentState(nextState);
                    printCurrentState();
                    continue;
                }
            }
            System.out.println("FAILURE");
        }

    }
}
public class Main {
    public static void main(String... args) {
        HillClimbingAlgorithm hillClimbingAlgorithm = new HillClimbingAlgorithm();
        hillClimbingAlgorithm.run();
    }
}
