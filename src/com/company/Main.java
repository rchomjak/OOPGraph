package com.company;


public class Main {



    public static void main(String[] args) {
	// write your code here

        Path a = new Path();

        Graph g = a.buildGraph("Input");

        System.out.println(a.findPath(g, 0, 4));

        System.out.println(a.getPathString(g, 0, 4));


        // Dijkstra dijkstra = new Dijkstra(g);

      //  dijkstra.findPath(0);


       // System.out.println(("przyk(3x2)la(1x5)))))D"));
    }
}
