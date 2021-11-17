import java.util.*;

class Node {
    public int[][] matrix;
    public Node[] children;
    public int heuristicValue;

    public Node() {
        matrix = new int[3][3];
        children = new Node[4];
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public void assignMatrix(int[][] matrix) {
        for(int i=0; i<3; i++) {
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, 3);
        }
    }

    public void printMatrix() {
        System.out.println();
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public int generateHeuristicValue(int[][] matrix, int g) {
        int count = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(this.matrix[i][j]!=matrix[i][j]) {
                    count++;
                }
            }
        }
        heuristicValue = count + 1;
        return count;
    }
}

class EightPuzzleProblem {
    public int[][] matrix;
    protected int[][] finalSolution;
    public int[][] tempMatrix;
    public int gofx;

    public Node headNode;
    public Node currNode;

    private final Scanner scanner;

    public PriorityQueue<Node> priorityQueue;
    public ArrayList<int[][]> visitedNodes;


    public EightPuzzleProblem() {
        matrix = new int[3][3];
        tempMatrix = new int[3][3];
        finalSolution = new int[3][3];
        gofx = 0;
        scanner = new Scanner(System.in);
        priorityQueue = new PriorityQueue<Node>(8, new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if (n1.heuristicValue > n2.heuristicValue) return 1;
                else if (n1.heuristicValue < n2.heuristicValue) return -1;
                return 0;
            }
        });
        visitedNodes = new ArrayList<>();
    }

    private void assignMatrix() {
        for(int i=0; i<3; i++) {
            System.arraycopy(matrix[i], 0, tempMatrix[i], 0, 3);
        }
    }

    private boolean checkMatrix(int[][] check) {
        int zeroCellCount = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(check[i][j] == 0) {
                    zeroCellCount++;
                }
                else if(!(check[i][j] >=1 && check[i][j]<=8)) {
                    return true;
                }
            }
        }
        return zeroCellCount != 1;
    }

    public boolean compareMatrices(int[][] matrix1, int[][] matrix2) {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkNodesVisited(int[][] matrix) {
        Iterator <int[][]> iterator = visitedNodes.iterator();
        while (iterator.hasNext()) {
            if(compareMatrices(iterator.next(), matrix)) {
                return true;
            }
        }
        return false;
    }

    private void generateChildren() {
        // find the blank node index
        int blankNodeI = 0, blankNodeJ = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(currNode.matrix[i][j] == 0) {
                    blankNodeI = i;
                    blankNodeJ = j;
                    break;
                }
            }
        }

        boolean up = false, down = false, right = false, left = false;

        if(blankNodeI >= 1) {
            up = true;
        }
        if(blankNodeI <= 1) {
            down = true;
        }
        if(blankNodeJ >= 1) {
            left = true;
        }
        if(blankNodeJ <= 1) {
            right = true;
        }

        int childCount = 0;

        if(up) {
            assignMatrix();
            up(blankNodeI, blankNodeJ);
            currNode.children[childCount] = new Node();
            currNode.children[childCount].assignMatrix(tempMatrix);
            currNode.children[childCount].generateHeuristicValue(finalSolution, gofx);
            if(!checkNodesVisited(currNode.children[childCount].matrix)) priorityQueue.add(currNode.children[childCount]);
            childCount++;
        }
        if(down) {
            assignMatrix();
            down(blankNodeI, blankNodeJ);
            currNode.children[childCount] = new Node();
            currNode.children[childCount].assignMatrix(tempMatrix);
            currNode.children[childCount].generateHeuristicValue(finalSolution, gofx);
            if(!checkNodesVisited(currNode.children[childCount].matrix)) priorityQueue.add(currNode.children[childCount]);
            childCount++;
        }
        if(left) {
            assignMatrix();
            left(blankNodeI, blankNodeJ);
            currNode.children[childCount] = new Node();
            currNode.children[childCount].assignMatrix(tempMatrix);
            currNode.children[childCount].generateHeuristicValue(finalSolution, gofx);
            if(!checkNodesVisited(currNode.children[childCount].matrix)) priorityQueue.add(currNode.children[childCount]);
            childCount++;
        }
        if(right) {
            assignMatrix();
            right(blankNodeI, blankNodeJ);
            currNode.children[childCount] = new Node();
            currNode.children[childCount].assignMatrix(tempMatrix);
            currNode.children[childCount].generateHeuristicValue(finalSolution, gofx);
            if(!checkNodesVisited(currNode.children[childCount].matrix)) priorityQueue.add(currNode.children[childCount]);
        }
    }

    private void up(int blankNodeI, int blankNodeJ) {
        int temp = tempMatrix[blankNodeI-1][blankNodeJ];
        tempMatrix[blankNodeI-1][blankNodeJ] = 0;
        tempMatrix[blankNodeI][blankNodeJ] = temp;
    }

    private void down(int blankNodeI, int blankNodeJ) {
        int temp = tempMatrix[blankNodeI+1][blankNodeJ];
        tempMatrix[blankNodeI+1][blankNodeJ] = 0;
        tempMatrix[blankNodeI][blankNodeJ] = temp;
    }

    private void left(int blankNodeI, int blankNodeJ) {
        int temp = tempMatrix[blankNodeI][blankNodeJ-1];
        tempMatrix[blankNodeI][blankNodeJ-1] = 0;
        tempMatrix[blankNodeI][blankNodeJ] = temp;
    }

    private void right(int blankNodeI, int blankNodeJ) {
        int temp = tempMatrix[blankNodeI][blankNodeJ+1];
        tempMatrix[blankNodeI][blankNodeJ+1] = 0;
        tempMatrix[blankNodeI][blankNodeJ] = temp;
    }

    public void run() {
        System.out.println("--- ENTER 0 FOR BLANK CELL ---");
        System.out.println("Enter the Matrix (To be solved) row wise. [3]x[3]");
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        headNode = new Node();
        headNode.assignMatrix(matrix);

        if(checkMatrix(matrix)) {
            System.out.println("Incorrect format entered, Please try again.");
            return;
        }

        System.out.println("Enter the expected Matrix row wise. [3]x[3]");

        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                finalSolution[i][j] = scanner.nextInt();
            }
        }

        if(checkMatrix(finalSolution)) {
            System.out.println("Incorrect format entered, Please try again.");
            return;
        }
        if(headNode.generateHeuristicValue(finalSolution, gofx) == 0) {
            System.out.println("\nPresent at Goal State.");
            headNode.printMatrix();
            return;
        }

        priorityQueue.add(headNode);
        currNode = headNode;
        int count = 0;
        while(currNode.generateHeuristicValue(finalSolution, gofx) !=0) {
            if(priorityQueue.isEmpty()) {
                System.out.println("FAILURE, Node could not be found");
                return;
            }
            else {
                currNode = priorityQueue.poll();
                visitedNodes.add(currNode.matrix);
                currNode.printMatrix();
                for(int i=0; i<3; i++) {
                    System.arraycopy(currNode.matrix[i], 0, matrix[i], 0, 3);
                }
                gofx++;
                generateChildren();
                count++;
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        EightPuzzleProblem eightPuzzleProblem = new EightPuzzleProblem();
        eightPuzzleProblem.run();
    }
}
