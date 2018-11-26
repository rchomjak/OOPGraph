package com.company;

public class Node {

    private int id;
    private int weight;

    Node(int id) {
        this.id = id;
    }

    Node(int id, int weight) {

        this.id = id;
        this.weight = weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;

    }

    public int getId() {
        return id;
    }
}
