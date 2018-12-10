package com.company;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path implements  pathInterface{

    @Override
    public Graph buildGraph(String fileName) {

        Graph graph = null;


        int numberOfNodes = 0;
        String temp;
        String decompressedText = "";

        try (
         Scanner in = new Scanner(new FileReader(fileName));


        ) {
            StringBuilder sb = new StringBuilder();


            while(in.hasNextLine()) {

                //Reading
                if (numberOfNodes <= 0) {

                    temp = in.nextLine();

                    numberOfNodes = Integer.parseInt(temp, 2);


                    if (numberOfNodes > 0) {

                        graph = new Graph(numberOfNodes);

                    } else {
                        return null;
                    }

                    continue;
                } else {
                    ;
                    //TODO: Should be ERROR
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

        Dijkstra dijkstra = new Dijkstra(Graph);

        List<Map.Entry<Integer, Integer>> wholeTree = dijkstra.findPath(beginingNode);

        ArrayList<Integer> reverseNodePath = getPath(wholeTree, beginingNode, destinationNode);

        String pathString = "";

        for(int i = reverseNodePath.size() -1; i >= 0; i-- ) {

            pathString += reverseNodePath.get(i) + ",";

        }


        return pathString;
    }

    @Override
    public String getPathString(Graph Graph, int beginingNode, int destinationNode) {

        Dijkstra dijkstra = new Dijkstra(Graph);

        List<Map.Entry<Integer, Integer>> wholeTree = dijkstra.findPath(beginingNode);

        ArrayList<Integer> reverseNodePath = getPath(wholeTree, beginingNode, destinationNode);

        String pathString = "";


        for(int i = reverseNodePath.size() -1; i >= 0; i-- ) {

            pathString += Graph.interiorOfNodes.get(reverseNodePath.get(i)) + ",";

        }


        return pathString;

    }

    private ArrayList<Integer> getPath(List<Map.Entry<Integer, Integer>> tree, int source, int destination) {

        if(tree.size() < source || tree.size() < destination) {

            throw new IllegalArgumentException("You are asking of node id, which does not exists");
        }

        ArrayList<Integer> reversePath = new ArrayList<>();

        int findingElement = destination;

        while((findingElement != source)) {

            reversePath.add(findingElement);
            findingElement = tree.get(findingElement).getValue();
        }

        reversePath.add(source);

        return reversePath;
    }

}
