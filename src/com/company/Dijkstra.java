package com.company;

import java.util.*;

public class Dijkstra {

    Graph graph;

    //key, distance
    ArrayList<Map.Entry<Integer, Integer>> distance;
    ArrayList<Map.Entry<Integer, Integer>> previous;

    ArrayList<Boolean> visited;


    //key, distance
    PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<Integer, Integer>>()
    {
        @Override
        public int compare(Map.Entry<Integer, Integer> entry1, Map.Entry<Integer, Integer> entry2)
        {
             return entry1.getValue() - entry2.getValue();
        }
    });


    Dijkstra(Graph graph) {
        this.graph = graph;


    }


    private ArrayList<Integer> getNeighbors(int nodeId) {

        return this.graph.nodes.get(nodeId);
    }


    private int getDistance(int nodeId) {

        return this.graph.interiorOfNodes.get(nodeId).length();

    }

    public ArrayList<Map.Entry<Integer, Integer>> findPath(int source) {

        distance = new ArrayList<>(Collections.nCopies(this.graph.getnumberOfNodes(), new AbstractMap.SimpleEntry<Integer, Integer>(-1,-1)));
        previous = new ArrayList<>(Collections.nCopies(this.graph.getnumberOfNodes(), new AbstractMap.SimpleEntry<Integer, Integer>(-1,-1)));
        visited = new ArrayList<>(Collections.nCopies(this.graph.getnumberOfNodes(), false));


        int alt = 0;

        for (Integer node : this.graph.nodes.keySet()) {

                distance.set(node, new AbstractMap.SimpleEntry<>(node,Integer.MAX_VALUE));

                previous.set(node, new AbstractMap.SimpleEntry<>(node,Integer.MIN_VALUE));
        }

        distance.set(source, new AbstractMap.SimpleEntry<>(source, 0)) ;


        for(Map.Entry<Integer, Integer> entry : distance){
            pq.offer(entry);
        }

        while (!pq.isEmpty()) {
            pq.comparator();
            Map.Entry<Integer, Integer> uNode = pq.poll();

            //node is already removed/visited
            visited.add(uNode.getKey(), true);

            List<Integer> allNeighs = getNeighbors(uNode.getKey());

            for (Integer neighId:  allNeighs) {

                if(visited.get(neighId)) {
                    continue;
                }

                alt = uNode.getValue() + getDistance(neighId);

                if (alt < distance.get(neighId).getValue()) {


                    //IMPORTANT: needs removes and insert, because, the  java PQ does not have method for rehash heap in the PQ
                    Map.Entry<Integer, Integer> element =  distance.get(neighId);

                    pq.remove(element);
                    element.setValue(alt);
                    pq.add(element);


                    Map.Entry<Integer, Integer > p = previous.get(neighId);

                    p.setValue(uNode.getKey());
                }

            }

        }


        return previous;

    }


}
