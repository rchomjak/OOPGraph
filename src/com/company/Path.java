package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path implements  pathInterface{

    @Override
    public Graph buildGraph(String fileName) {

        Graph graph = null;

        Boolean isFirstLineRead = false;

        int numberOfNodes = 0;
        String temp;
        String decompressedText = "";

        try (
         Scanner in = new Scanner(new FileReader(fileName));


        ) {
            StringBuilder sb = new StringBuilder();


            while(in.hasNextLine()) {

                //Reading
                if (numberOfNodes <= 0 && !isFirstLineRead) {

                    temp = in.nextLine();
                    isFirstLineRead = true;

                    numberOfNodes = Integer.parseInt(temp, 2);

                    System.out.println(numberOfNodes);

                    System.out.println(temp);


                    if (numberOfNodes > 0) {

                        graph = new Graph(numberOfNodes);

                    } else {
                        return null;
                    }

                    continue;
                }




                //Reading the nodes connections
                for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {

                    if (in.hasNextLine()) {

                        temp = in.nextLine();
                        String[] stringNodes = temp.split(" ");
                        ArrayList<Integer> nodesIndex = new ArrayList<>();

                        for(int i = 0; i <stringNodes.length; i++) {

                            Integer connection = Integer.parseInt(stringNodes[i], 2);
                            nodesIndex.add(connection);

                        }

                        if (graph == null) {

                            throw new IllegalStateException("Graph is null, this means, that file input is incorrect");
                        } else {

                            graph.nodes.put(nodeIndex, nodesIndex);
                        }



                    } else {
                        throw new IllegalStateException("File input is incorrect");

                    }
                }
                //TEXT token should be here
                if (in.hasNextLine()) {

                    temp = in.nextLine();

                    if (!temp.equals("TEXT")) {
                        throw new IllegalStateException("File input is incorrect, missing TEXT token.");

                    }
                }


                //Reading the nodes text
                for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {

                    if (in.hasNextLine()) {

                        temp = in.nextLine();


                        if (graph == null) {

                            throw new IllegalStateException("Graph is null, this means, that file input is incorrect");
                        } else {

                           decompressedText = decompress(graph, nodeIndex, temp);


                        }

                    } else {
                        throw new IllegalStateException("File input is incorrect");

                    }
                }





            }




        }catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        return graph;
    }

    @Override
    public String decompress(Graph Graph, int node, String code) {

        String[] splitStr = code.split("(?<=\\))");

        String processingString = "";
        String inputProcessingString = "";

        for (int i = 0; i < splitStr.length; i++) {

            inputProcessingString = processingString;

            processingString = decompressOperation(inputProcessingString + splitStr[i]);

        }

        Graph.interiorOfNodes.add(node, processingString.length() > 0? processingString: null);

        return processingString.length() > 0? processingString: null;

    }


    private static String decompressOperation(String input) {

        Integer repeatTimes;
        Integer backLetters;

        String stringTimer = "";

        Integer possitionFromRepeat;
        Integer possitionToRepeat;


        Pattern pattern = Pattern.compile("(\\((?<backLetters>[0-9]+)x(?<repeatTimes>[0-9]+)\\))");

        Matcher matcher = pattern.matcher(input);


        if (matcher.find()) {

            backLetters = Integer.parseInt(matcher.group("backLetters"));
            repeatTimes = Integer.parseInt(matcher.group("repeatTimes"));


        } else {

            //No special meaning in the text
            return input;
        }

        possitionFromRepeat = matcher.start("backLetters") - 1 - backLetters;
        possitionToRepeat = matcher.start("backLetters") - 1;


        if (possitionFromRepeat < 0) {

            throw new IllegalArgumentException("Input is incorrect");

        }

        for (int i = 0; i < repeatTimes; i++) {
            stringTimer += input.substring(possitionFromRepeat, possitionToRepeat);
        }


        return input.substring(0, possitionFromRepeat) + stringTimer;
    }




        @Override
    public String findPath(Graph Graph, int beginingNode, int destinationNode) {
        return null;
    }

    @Override
    public String getPathString(Graph Graph, int beginingNode, int destinationNode) {
        return null;
    }
}
