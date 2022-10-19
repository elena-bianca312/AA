// Copyright 2020
// Author: Matei Simtinică

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Task1
 * You have to implement 4 methods:
 * readProblemData         - read the problem input and store it however you see fit
 * formulateOracleQuestion - transform the current problem instance into a SAT instance and write the oracle input
 * decipherOracleAnswer    - transform the SAT answer back to the current problem's answer
 * writeAnswer             - write the current problem's answer
 */
public class Task1 extends Task {
    // TODO: define necessary variables and/or data structures
    private Integer verticesNo;
    private Integer edgesNo;
    private Integer spiesNo;
    private Map<Integer, List<Integer>> graph = new HashMap<>();
    private boolean value;
    List<Integer> spiesList = new ArrayList<>();

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

    public Integer getSpiesNo() {
        return spiesNo;
    }

    public void setSpiesNo(Integer spiesNo) {
        this.spiesNo = spiesNo;
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

    public List<Integer> getSpiesList() {
        return spiesList;
    }

    public void setSpiesList(List<Integer> spiesList) {
        this.spiesList = spiesList;
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

        BufferedReader bufferedReader = new BufferedReader(new FileReader(inFilename));
        String line = bufferedReader.readLine();

        String[] data = line.split(" ");
        verticesNo = Integer.parseInt(data[0]);
        edgesNo = Integer.parseInt(data[1]);
        spiesNo = Integer.parseInt(data[2]);
        for(Integer i = 1; i <= verticesNo; i++) {
            graph.put(i, new LinkedList<>());
        }

        while((line = bufferedReader.readLine()) != null) {
            String[] relations = line.split(" ");

            graph.get(Integer.parseInt(relations[0])).add(Integer.parseInt(relations[1]));
        }
    }

    @Override
    public void formulateOracleQuestion(String oracleInFilename) throws IOException {
        // TODO: transform the current problem into a SAT problem and write it (oracleInFilename) in a format
        //  understood by the oracle

        int variablesNo = verticesNo * spiesNo;
        int clausesNo = verticesNo + (spiesNo * (spiesNo - 1)) / 2 * verticesNo + edgesNo * spiesNo;
        List<String> lines = new ArrayList<>();
        lines.add("p cnf " + variablesNo + " " + clausesNo);
        Map<Integer, List<Integer>> variables = new HashMap<>();
        for(Integer i = 1; i <= verticesNo; i++) {
            List<Integer> variablesLine = new ArrayList<>();
            String line = "";
            for(Integer j = spiesNo * (i - 1) + 1; j <= spiesNo * i; j++) {
                variablesLine.add(j);
                line = line.concat(j + " ");
            }
            line = line.concat("0");
            lines.add(line);
            variables.put(i, variablesLine);
            for(int k1 = 0; k1 < variablesLine.size(); k1++) {
                for(int k2 = k1; k2 < variablesLine.size(); k2++) {
                    if(!variablesLine.get(k1).equals(variablesLine.get(k2))) {
                        line = "";
                        line = line.concat("-" + variablesLine.get(k1) + " -" + variablesLine.get(k2) + " 0");
                        lines.add(line);
                    }
                }
            }
        }

        String line = "";
        for(int i = 1; i <= graph.size(); i++) {
            List<Integer> adjList = graph.get(i);
            for (Integer integer : adjList) {
                for (int k = 0; k < spiesNo; k++) {
                    line = "-" + variables.get(i).get(k) + " -" + variables.get(integer).get(k) + " 0";
                    lines.add(line);
                    System.out.println(line);
                }
            }
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(oracleInFilename));
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
            Integer varNo = Integer.parseInt(bufferedReader.readLine());
            line = bufferedReader.readLine();
            String[] numbers = line.split(" ");
            for(int i = 1; i <= varNo / spiesNo; i++) {
                for(int j = 1; j <= spiesNo; j++) {
                    if(Integer.parseInt(numbers[spiesNo * (i - 1) + j - 1]) > 0) {
                        spiesList.add(j);
                    }
                }
            }
        }
    }

    @Override
    public void writeAnswer(String outFilename) throws IOException {
        // TODO: write the answer to the current problem (outFilename)

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFilename));
        String data;
        if(!value) {
            data = "False";
            bufferedWriter.write(data);
        }
        else {
            data = "True";
            bufferedWriter.write(data);
            String data2 = "";
            for (Integer integer : spiesList) {
                data2 = data2.concat(integer + " ");
            }
            bufferedWriter.newLine();
            bufferedWriter.write(data2);
        }
        bufferedWriter.flush();
    }
}
