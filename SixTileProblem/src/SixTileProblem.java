import java.util.*;

class Tile {
    private String configuration;
    private int level;
    private int heuristic;
    private String parent;

    public Tile(String configuration, int level, int heuristic) {
        this.configuration = configuration;
        this.level = level;
        this.heuristic = heuristic;
        parent = "";
    }

    public String getConfiguration() {
        return configuration;
    }

    public int generateFScore() {
        return heuristic+level;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }
}

public class SixTileProblem {
    private String initialConfiguration;
    private String finalConfiguration;
    private Set<String> visited;
    private PriorityQueue<Tile> open;

    private Vector<Tile> shortestPath;

    int generateHeuristicValue(String s1) {
        int count = 0;
        for(int i=0; i<s1.length(); i++) {
            if(s1.charAt(i) != finalConfiguration.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    boolean checkVisited(String s1) {
        return visited.contains(s1) ? true : false;
    }

    String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

    Vector<String> generateChilren(String s) {
        Vector<String> children = new Vector<>();
        if(s.length() % 2 == 0) {
            String temp;
            for(int i=0; i<s.length(); i+=2) {
                temp = s;
                temp = swap(temp, i, i+1);
                children.add(temp);
            }
            for(int i=1; i<s.length()-1; i+=2) {
                temp = s;
                temp = swap(temp, i, i+1);
                children.add(temp);
            }
        }
        else {
            String temp;
            for(int i=0; i<s.length()-1; i+=2) {
                temp = s;
                temp = swap(temp, i, i+1);
                children.add(temp);
            }
            temp = s;
            for(int i=1; i<s.length(); i+=2) {
                temp = s;
                temp = swap(temp, i, i+1);
                children.add(temp);
            }
        }
        return children;
    }

    public SixTileProblem() {
        initialConfiguration = "BWBWBW";
        finalConfiguration = "BBBWWW";
        visited = new HashSet<>();
        open = new PriorityQueue<>(new ConfigurationComparator());
        shortestPath = new Vector<>();
    }

    public void run() {
        String current = "";
        int level = 0;
        current = initialConfiguration;
        if(generateHeuristicValue(current) == 0) {
            System.out.println("AT GOAL STATE");
            return;
        }
        Tile t = new Tile(current, level, generateHeuristicValue(current));
        shortestPath.add(t);
        open.add(t);
        while (!open.isEmpty()) {
            level++;
            Tile tempTile = open.poll();
            System.out.println(tempTile.getConfiguration());
            if(generateHeuristicValue(tempTile.getConfiguration()) == 0) {
                shortestPath.add(tempTile);
                break;
            }
            Vector<String> children = generateChilren(tempTile.getConfiguration());
            for (String ch : children) {
                if(!checkVisited(ch)) {
                    t = new Tile(ch, level, generateHeuristicValue(ch));
                    t.setParent(tempTile.getConfiguration());
                    open.add(t);
                    shortestPath.add(t);
                    visited.add(ch);
                }
            }
        }
    }

    public void getShortestPath() {
        System.out.print("\nShortest Path :");
        Stack<String> stack = new Stack<>();
        Iterator iterator = shortestPath.iterator();
        String parent = "";
        stack.push(finalConfiguration);
        while(iterator.hasNext()) {
            Tile t = (Tile) iterator.next();
            if(generateHeuristicValue(t.getConfiguration()) == 0) {
                parent = t.getParent();
                stack.push(parent);
                break;
            }
        }
        while (parent!="") {
            iterator = shortestPath.iterator();
            while(iterator.hasNext()) {
                Tile t = (Tile) iterator.next();
                if(t.getConfiguration().equals(parent)) {
                    parent = t.getParent();
                    stack.push(parent);
                    break;
                }
            }
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}

class ConfigurationComparator implements Comparator<Tile> {
    @Override
    public int compare(Tile tile1, Tile tile2) {
        return tile1.generateFScore() > tile2.generateFScore() ? 1 : -1;
    }
}