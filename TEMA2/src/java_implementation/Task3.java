// Copyright 2020
// Author: Matei Simtinică

import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Task3
 * This being an optimization problem, the solve method's logic has to work differently.
 * You have to search for the minimum number of arrests by successively querying the oracle.
 * Hint: it might be easier to reduce the current task to a previously solved task
 */
public class Task3 extends Task {
    String task2InFilename;
    String task2OutFilename;
    // TODO: define necessary variables and/or data structures
    private Integer verticesNo;
    private Integer edgesNo;
    private Map<Integer, List<Integer>> graph = new HashMap<>();
    private Map<Integer, List<Integer>> nonGraph = new HashMap<>();
    private boolean value;
    private List<Integer> families = new ArrayList<>();
    private Integer newEdgeNo = 0;

    public Integer getVerticesNo() {
        return verticesNo;
    }

    public void setVerticesNo(Integer verticesNo) {
        this.verticesNo = verticesNo;
    }

    public Integer getEdgesNo() {
        return edgesNo;
    }

    public void setEdgesNo(Integer edgesNo) {
        this.edgesNo = edgesNo;
    }

    public Map<Integer, List<Integer>> getGraph() {
        return graph;
    }

    public void setGraph(Map<Integer, List<Integer>> graph) {
        this.graph = graph;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public List<Integer> getFamilies() {
        return families;
    }

    public void setFamilies(List<Integer> families) {
        this.families = families;
    }

    public Map<Integer, List<Integer>> getNonGraph() {
        return nonGraph;
    }

    public void setNonGraph(Map<Integer, List<Integer>> nonGraph) {
        this.nonGraph = nonGraph;
    }

    public Integer getNewEdgeNo() {
        return newEdgeNo;
    }

    public void setNewEdgeNo(Integer newEdgeNo) {
        this.newEdgeNo = newEdgeNo;
    }

    @Override
    public void solve(String inFilename, String oracleInFilename, String oracleOutFilename, String outFilename) throws IOException, InterruptedException {
        task2InFilename = inFilename + "_t2";
        task2OutFilename = outFilename + "_t2";
        Task2 task2Solver = new Task2();
        task2Solver.addFiles(task2InFilename, oracleInFilename, oracleOutFilename, task2OutFilename);
        readProblemData(inFilename);

        // TODO: implement a way of successively querying the oracle (using Task2) about various arrest numbers until you
        //  find the minimum

        // make non graph
        for(int i = 1; i <= graph.size(); i++) {
            List<Integer> adjList = graph.get(i);
            List<Integer> nonEdge = new ArrayList<>();
            for(int j = 1; j <= verticesNo; j++) {
                boolean ok = true;
                for (Integer integer : adjList) {
                    if (j == integer) {
                        ok = false;
                        break;
                    }
                }
                if(ok && j != i) {
                    nonEdge.add(j);
                    newEdgeNo++;
                }
                nonGraph.put(i, nonEdge);
            }
        }

        newEdgeNo /= 2;

        // facem fisierul de input pt task3 de forma input task2
        for(int i = 1; i <= verticesNo; i++) {

            String line = verticesNo + " " + newEdgeNo + " " + (verticesNo - i);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(task2InFilename));
            bufferedWriter.write(line);
            bufferedWriter.newLine();

            for(Integer key : nonGraph.keySet()) {
                for(Integer values : nonGraph.get(key)) {
                    if(values > key) {
                        line = key + " " + values;
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                }
            }
            bufferedWriter.flush();

            task2Solver.solve(task2InFilename, oracleInFilename, oracleOutFilename, task2OutFilename);
            reduceToTask2(task2OutFilename);

            if(value) {
                break;
            }
        }

        writeAnswer(outFilename);
    }

    @Override
    public void readProblemData(String inFilename) throws IOException {
        // TODO: read the problem input (inFilename) and store the data in the object's attributes

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(inFilename)));
        String line = bufferedReader.readLine();

        String[] numbers = line.split(" ");
        verticesNo = Integer.parseInt(numbers[0]);
        edgesNo = Integer.parseInt(numbers[1]);
        for(Integer i = 1; i <= verticesNo; i++) {
            graph.put(i, new LinkedList<>());
        }
        for(Integer i = 1; i <= verticesNo; i++) {
            nonGraph.put(i, new LinkedList<>());
        }

        while((line = bufferedReader.readLine()) != null) {
            String[] numbers2 = line.split(" ");

            graph.get(Integer.parseInt(numbers2[0])).add(Integer.parseInt(numbers2[1]));
            graph.get(Integer.parseInt(numbers2[1])).add(Integer.parseInt(numbers2[0]));
        }
    }

    public void reduceToTask2(String task2OutFilename) throws IOException {
        // TODO: reduce the current problem to Task2

        BufferedReader bufferedReader = new BufferedReader(new FileReader(task2OutFilename));
        String line = bufferedReader.readLine();
        if(line.equals("True")) {
            value = true;
            line = bufferedReader.readLine();
            String[] numbers = line.split(" ");

            for(int i = 1; i <= verticesNo; i++) {
                boolean ok = true;
                for (String iter : numbers) {
                    if(Integer.parseInt(iter) == i) {
                        ok = false;
                        break;
                    }
                }
                if(ok) {
                    families.add(i);
                }
            }
        }
    }

    public void extractAnswerFromTask2() {
        // TODO: extract the current problem's answer from Task2's answer

    }

    @Override
    public void writeAnswer(String outFilename) throws IOException {
        // TODO: write the answer to the current problem (outFilename)

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFilename));
        System.out.println(outFilename);

        if(families.isEmpty()) {
            bufferedWriter.write(Integer.toString(verticesNo));
        }
        else {
            for(Integer integer : families) {
                bufferedWriter.write(Integer.toString(integer) + " ");
            }
        }
        bufferedWriter.flush();
    }
}
