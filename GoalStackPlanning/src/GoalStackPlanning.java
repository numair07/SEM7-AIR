import java.util.*;

class ExecuteProgram {
    private LinkedList<String> plan;
    private ArrayList<String> knowledgeBase;
    private Stack<String> stack;
    private Set<String> predicates;
    private Set<String> operators;

    private String initialState;
    private String goalState;

    public ExecuteProgram(String initialState, String goalState) {
        plan = new LinkedList<String>();
        knowledgeBase = new ArrayList<>();
        stack = new Stack<>();
        predicates = new HashSet();
        operators = new HashSet();

        this.initialState = initialState;
        this.goalState = goalState;

//        ON(A,B) : Block A is on B
//        ONTABLE(A) : A is on table
//        CLEAR(A) : Nothing is on top of A
//        HOLDING(A) : Arm is holding A.
//        ARMEMPTY : Arm is holding nothing

        predicates.add("ON");
        predicates.add("ONTABLE");
        predicates.add("CLEAR");
        predicates.add("HOLDING");
        predicates.add("ARMEMPTY");

        operators.add("STACK");
        operators.add("UNSTACK");
        operators.add("PICKUP");
        operators.add("PUTDOWN");
    }

    public void run() {
        String[] parseString = goalState.split(" ∧ ");
        for(String s : parseString) {
            stack.push(s);
        }
        parseString = initialState.split(" ∧ ");
        for(String s : parseString) {
            knowledgeBase.add(s);
        }
        String[] splitStrings;
        while (!stack.isEmpty()) {
            String stackTop = stack.pop();
            if(knowledgeBase.contains(stackTop)) {
               continue;
            }
            splitStrings = stackTop.split(",|\\(|\\)");
            if(predicates.contains(splitStrings[0])) {
                if(splitStrings[0].equals("ON")) {
                    stack.push("STACK(" + splitStrings[1] + "," + splitStrings[2]+")");
                    stack.push("HOLDING(" + splitStrings[1] + ")");
                    stack.push("CLEAR(" + splitStrings[2] + ")");
                }
                else if(splitStrings[0].equals("ONTABLE")) {
                    String secondOperand = "";
                    for(String s : knowledgeBase) {
                        if(s.contains("ON") && s.contains(splitStrings[1])) {
                            String[] splitS = s.split(",|\\(|\\)");
                            secondOperand = splitS[2];
                            break;
                        }
                    }
                    stack.push("PUTDOWN(" + splitStrings[1] + ")");
                    stack.push("HOLDING(" + splitStrings[1] + ")");
                    stack.push("UNSTACK(" + splitStrings[1] + "," + secondOperand + ")");
                    stack.push("CLEAR(" + splitStrings[1] + ")");
                    stack.push("ON(" + splitStrings[1] + "," + secondOperand + ")");
                    stack.push("ARMEMPTY");
                }
                else if(splitStrings[0].equals("CLEAR")) {
                    String firstOperand = "";
                    for(String s : knowledgeBase) {
                        if(s.contains("ON(") && s.contains(splitStrings[1])) {
                            String[] splitS = s.split(",|\\(|\\)");
                            firstOperand = splitS[1];
                            break;
                        }
                    }
                    stack.push("UNSTACK(" + firstOperand + "," + splitStrings[1] + ")");
                    stack.push("CLEAR(" + firstOperand + ")");
                    stack.push("ON(" + firstOperand + "," + splitStrings[1] + ")");
                    stack.push("ARMEMPTY");
                }
                else if(splitStrings[0].equals("HOLDING")) {
                    String secondOperand = "";
                    for(String s : knowledgeBase) {
                        if(s.contains("ON(") && s.contains(splitStrings[1])) {
                            String[] splitS = s.split(",|\\(|\\)");
                            secondOperand = splitS[2];
                            stack.push("UNSTACK(" + splitStrings[1] + "," + secondOperand + ")");
                            stack.push("CLEAR(" + splitStrings[1] + ")");
                            stack.push("ON(" + splitStrings[1] + "," + secondOperand + ")");
                            stack.push("ARMEMPTY");
                            break;
                        }
                        else if(s.contains("ONTABLE") && s.contains(splitStrings[1])) {
                            stack.push("PICKUP(" + splitStrings[1] + ")");
                            stack.push("ARMEMPTY");
                            stack.push("ONTABLE(" + splitStrings[1] + ")");
                            stack.push("CLEAR(" + splitStrings[1] + ")");
                            break;
                        }
                    }
                }
            }
            else if(operators.contains(splitStrings[0])) {
                plan.add(stackTop);
                if(splitStrings[0].equals("STACK")) {
                    knowledgeBase.remove("CLEAR(" + splitStrings[2] + ")");
                    knowledgeBase.remove("HOLDING(" + splitStrings[1] + ")");
                    knowledgeBase.add("ARMEMPTY");
                    knowledgeBase.add("ON(" + splitStrings[1] + "," + splitStrings[2] + ")");
                }
                else if(splitStrings[0].equals("UNSTACK")) {
                    knowledgeBase.remove("ARMEMPTY");
                    knowledgeBase.remove("ON(" + splitStrings[1] + "," + splitStrings[2] + ")");
                    knowledgeBase.add("HOLDING(" + splitStrings[1] + ")");
                    knowledgeBase.add("CLEAR(" + splitStrings[2] + ")");
                }
                else if(splitStrings[0].equals("PICKUP")) {
                    knowledgeBase.remove("ONTABLE(" + splitStrings[1] + ")");
                    knowledgeBase.remove("ARMEMPTY");
                    knowledgeBase.add("HOLDING(" + splitStrings[1] + ")");
                }
                else if(splitStrings[0].equals("PUTDOWN")) {
                    knowledgeBase.remove("HOLDING(" + splitStrings[1] + ")");
                    knowledgeBase.add("ONTABLE(" + splitStrings[1] + ")");
                    knowledgeBase.add("ARMEMPTY");
                }
            }

        }
        System.out.println("\nSTEPS TO BE FOLLOWED : ");
        Iterator iterator = plan.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}

public class GoalStackPlanning {
    public static void main(String... args) {
        String initalState = "ON(B,A) ∧ ONTABLE(A) ∧ ONTABLE(C) ∧ ONTABLE(D) ∧ CLEAR(B) ∧ CLEAR(C) ∧ CLEAR(D) ∧ ARMEMPTY";
        String goalState = "ON(C,A) ∧ ON(B,D) ∧ ONTABLE(A) ∧ ONTABLE(D) ∧ CLEAR(B) ∧ CLEAR(C) ∧ ARMEMPTY";
        ExecuteProgram executeProgram = new ExecuteProgram(initalState, goalState);
        executeProgram.run();
    }
}
