// Copyright 2020
// Author: Matei Simtinică

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the abstract base class for all tasks that have to be implemented.
 */
public abstract class Task {
    String inFilename;
    String oracleInFilename;
    String oracleOutFilename;
    String outFilename;

    public abstract void solve(String arg1, String s, String arg, String inFilename) throws IOException, InterruptedException;

    public abstract void readProblemData(String inFilename) throws IOException;

    public void formulateOracleQuestion(String oracleInFilename) throws IOException {}

    public void decipherOracleAnswer(String oracleOutFilename) throws IOException {}

    public abstract void writeAnswer(String outFilename) throws IOException;

    /**
     * Stores the files paths as class attributes.
     *
     * @param inFilename         the file containing the problem input
     * @param oracleInFilename   the file containing the oracle input
     * @param oracleOutFilename  the file containing the oracle output
     * @param outFilename        the file containing the problem output
     */
    public void addFiles(String inFilename, String oracleInFilename, String oracleOutFilename, String outFilename) {
        this.inFilename = inFilename;
        this.oracleInFilename = oracleInFilename;
        this.oracleOutFilename = oracleOutFilename;
        this.outFilename = outFilename;
    }

    /**
     * Asks the oracle for an answer to the formulated question.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void askOracle() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectErrorStream(true);
        builder.command("python3", "sat_oracle.py", oracleInFilename, oracleOutFilename);
        Process process = builder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String buffer;
        StringBuilder output = new StringBuilder();

        while ((buffer = in.readLine()) != null) {
            output.append(buffer).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Error encountered while running oracle");
            System.err.println(output.toString());
            System.exit(-1);
        }
    }
}
