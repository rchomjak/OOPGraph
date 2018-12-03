/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public class Graph {

    private final int numberOfNodes;


    public Graph(int number)
    {
        this.numberOfNodes = number;
    }

    public int getnumberOfNodes()
    {
        return numberOfNodes;
    }

    //mapa przechowujaca liste sasiedztwa dla tworzonego grafu
    Map <Integer, ArrayList<Integer>> nodes = new HashMap <>();

    //lista zawierajaca informacje przechowywane w poszczegolnych wezlach grafu
    ArrayList<String> interiorOfNodes = new ArrayList<>();
}
