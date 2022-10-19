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
 * Task2
 * You have to implement 4 methods:
 * readProblemData         - read the problem input and store it however you see fit
 * formulateOracleQuestion - transform the current problem instance into a SAT instance and write the oracle input
 * decipherOracleAnswer    - transform the SAT answer back to the current problem's answer
 * writeAnswer             - write the current problem's answer
 */
public class Task2 extends Task {
    // TODO: define necessary variables and/or data structures
    private Integer verticesNo;
    private Integer edgesNo;
    private Integer cliqueNo;
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

    public Integer getCliqueNo() {
        return cliqueNo;
    }

    public void setCliqueNo(Integer cliqueNo) {
        this.cliqueNo = cliqueNo;
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
        cliqueNo = Integer.parseInt(numbers[2]);
        for(Integer i = 1; i <= verticesNo; i++) {
            graph.put(i, new LinkedList<>());
        }

        while((line = bufferedReader.readLine()) != null) {
            String[] numbers2 = line.split(" ");

            graph.get(Integer.parseInt(numbers2[0])).add(Integer.parseInt(numbers2[1]));
            graph.get(Integer.parseInt(numbers2[1])).add(Integer.parseInt(numbers2[0]));
        }
    }

    @Override
    public void formulateOracleQuestion(String oracleInFilename) throws IOException {
        // TODO: transform the current problem into a SAT problem and write it (oracleInFilename) in a format
        //  understood by the oracle

        int variablesNo = verticesNo * cliqueNo;
        int clausesNo = 0;
        clausesNo = clausesNo + cliqueNo;
        clausesNo = clausesNo + (cliqueNo * (cliqueNo - 1)) / 2 * verticesNo;
        clausesNo = clausesNo + ((verticesNo * (verticesNo - 1) / 2 - edgesNo) * cliqueNo) * cliqueNo;
        clausesNo = clausesNo + cliqueNo * verticesNo * (verticesNo - 1) / 2;
        //System.out.println(clausesNo);
        List<String> lines = new ArrayList<>();
        lines.add("p cnf " + variablesNo + " " + clausesNo);
        Map<Integer, List<Integer>> variables = new HashMap<>();

        //c.1
        for(Integer i = 1; i <= verticesNo; i++) {
            List<Integer> variablesLine = new ArrayList<>();
            String line = "";
            for(int j = cliqueNo * (i - 1) + 1; j <= cliqueNo * i; j++) {
                variablesLine.add(j);
            }
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

        //clauses for the non-edge nodes
        //b
        String line = "";

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
                if(ok && j > i)
                    nonEdge.add(j);
            }

            for (Integer integer : nonEdge) {
                for (int k = 0; k < cliqueNo; k++) {
                    for(int k2 = 0; k2 < cliqueNo; k2++) {
                        line = "-" + variables.get(i).get(k) + " -" + variables.get(integer).get(k2) + " 0";
                        lines.add(line);
                    }
                }
            }
        }

        //c.2
        for(int i = 0; i < cliqueNo; i++) {
            String line2 = "";
            for(int j = 1; j <= verticesNo; j++) {
                for(int k = j + 1; k <= verticesNo; k++) {
                    line = "-" + variables.get(j).get(i) + " -" + variables.get(k).get(i) + " 0";
                    lines.add(line);
                }
                line2 = line2.concat(variables.get(j).get(i) + " ");
            }
            line2 = line2.concat("0");
            lines.add(line2);
        }


        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(oracleInFilename)));
        for(String s : lines) {
            bufferedWriter.write(s);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
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
            for(int i = 1; i <= varNo / cliqueNo; i++) {
                for(int j = 1; j <= cliqueNo; j++) {
                    if(Integer.parseInt(numbers[cliqueNo * (i - 1) + j - 1]) > 0) {
                        cliqueList.add(i);
                    }
                }
            }
            //System.out.println(cliqueList);
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
            for (Integer integer : cliqueList) {
                data2 = data2.concat(integer + " ");
            }
            bufferedWriter.newLine();
            bufferedWriter.write(data2);
            cliqueList.clear();
        }
        bufferedWriter.flush();
    }
}
