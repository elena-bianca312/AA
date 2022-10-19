// Copyright 2020
// Author: Matei Simtinică

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Bonus Task
 * You have to implement 4 methods:
 * readProblemData         - read the problem input and store it however you see fit
 * formulateOracleQuestion - transform the current problem instance into a SAT instance and write the oracle input
 * decipherOracleAnswer    - transform the SAT answer back to the current problem's answer
 * writeAnswer             - write the current problem's answer
 */
public class BonusTask extends Task {
    // TODO: define necessary variables and/or data structures

    private Integer verticesNo;
    private Integer edgesNo;
    private Integer vertexNo = 1;
    private Map<Integer, List<Integer>> graph = new HashMap<>();
    private boolean value;
    List<Integer> cliqueList = new ArrayList<>();

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

    public Integer getVertexNo() {
        return vertexNo;
    }

    public void setVertexNo(Integer vertexNo) {
        this.vertexNo = vertexNo;
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

    public List<Integer> getCliqueList() {
        return cliqueList;
    }

    public void setCliqueList(List<Integer> cliqueList) {
        this.cliqueList = cliqueList;
    }

    @Override
    public void solve(String inFilename, String oracleInFilename, String oracleOutFilename, String outFilename) throws IOException, InterruptedException {
        readProblemData(inFilename);
        formulateOracleQuestion(oracleInFilename);
        askOracle();
        decipherOracleAnswer(oracleOutFilename);
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

        while((line = bufferedReader.readLine()) != null) {
            String[] numbers2 = line.split(" ");

            graph.get(Integer.parseInt(numbers2[0])).add(Integer.parseInt(numbers2[1]));
        }
    }


    @Override
    public void formulateOracleQuestion(String oracleInFilename) throws IOException {
        // TODO: transform the current problem into a SAT problem and write it (oracleInFilename) in a format
        //  understood by the oracle

        int variablesNo = verticesNo;
        int clausesNo = verticesNo + edgesNo;
        int weight = 0;
        List<String> lines = new ArrayList<>();

        Map<Integer, List<Integer>> variables = new HashMap<>();

        for(int i = 1; i <= verticesNo; i++) {
            List<Integer> variablesLine = new ArrayList<>();
            for (int j = vertexNo * (i - 1) + 1; j <= vertexNo * i; j++) {
                variablesLine.add(j);
            }
            variables.put(i, variablesLine);
        }
        String line = "";

        // soft clauses
        for(int i = 1; i <= graph.size(); i++) {
            for(Integer j : variables.get(i)) {
                line = "1 -" + j + " 0";
                weight++;
                lines.add(line);
            }
        }

        // at least one vertex in vertex cover should come from edges:
        // hard clauses
        for(int i = 1; i <= graph.size(); i++) {
            for(int j = 0; j < graph.get(i).size(); j++) {
                line = (weight + 1) + " ";
                for (Integer k : variables.get(i)) {
                    line = line.concat(k + " ");
                }
                int n = graph.get(i).get(j);
                for (Integer k : variables.get(n)) {
                    line = line.concat(k + " ");
                }
                line = line.concat("0");
                lines.add(line);
            }
        }

        line = "p wcnf " + variablesNo + " " + clausesNo + " " + (weight + 1);
        lines.add(0, line);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(oracleInFilename)));
        for(String s : lines) {
            bufferedWriter.write(s);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();

    }

    @Override
    public void decipherOracleAnswer(String oracleOutFilename) throws IOException {
        // TODO: extract the current problem's answer from the answer given by the oracle (oracleOutFilename)

        BufferedReader bufferedReader = new BufferedReader(new FileReader(oracleOutFilename));
        String line = bufferedReader.readLine();
        if(line.equals("False")) {
            value = false;
        }
        else {
            value = true;
            line = bufferedReader.readLine();
            String[] numbers = line.split(" ");
            for(int i = 0; i < verticesNo; i++) {
                if(Integer.parseInt(numbers[i]) > 0) {
                    cliqueList.add(Integer.parseInt(numbers[i]));
                }
            }
        }
    }

    @Override
    public void writeAnswer(String outFilename) throws IOException {
        // TODO: write the answer to the current problem (outFilename)

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFilename));
        String line = "";

        for(Integer i : cliqueList) {
            line = line.concat(i + " ");
        }
        bufferedWriter.write(line);

        bufferedWriter.flush();
    }
}
