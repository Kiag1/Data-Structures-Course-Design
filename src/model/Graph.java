package model;

import stl.Array;

import java.awt.*;

public class Graph {
    private int number = 1;
    private Array<Node> nodes = new Array<>();
    private Array<Edge> edges = new Array<>();

    private Node start;
    private Node end;

    private boolean find = false;

    // 返回当前图中有多少个节点(城市)
    public int size() { return this.nodes.size(); };

    public void setFind(boolean find) {
        this.find = find;
    }

    public boolean isFind() {
        return this.find;
    }

    public void setNodes(Array<Node> nodes) {
        this.nodes = nodes;
    }

    public void setEdges(Array<Edge> edges) {
        this.edges = edges;
    }

    public Array<Node> getNodes() {
        return this.nodes;
    }

    public Array<Edge> getEdges() {
        return this.edges;
    }

    public boolean isNodeReachable(Node node) {
        for (int i = 0; i < edges.size(); i++)
            if (node == edges.get(i).getU() || node == edges.get(i).getV())
                return true;
        return false;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public Node getStart() { return this.start; }

    public Node getEnd() { return this.end; }

    public boolean isStart(Node node) {
        return node == start;
    }

    public boolean isEnd(Node node) {
        return node == end;
    }

    public void addNode(Point point) {
        Node node = new Node(point);
        addNode(node);
    }

    private void addNode(Node node) {
        node.setId(number++);
        nodes.push_back(node);
        if (node.getId() == 1)
            start = node;
    }

    public void addEdge(Edge edge) {
        boolean hasAdd = false;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).equal(edge)) {
                hasAdd = true;
                break;
            }
        }

        if (!hasAdd) edges.push_back(edge);
    }

    public void removeNode(Node node) {
        while (true) {
            Edge edge = null;
            for (int i = 0; i < edges.size(); i++) {
                if (edges.get(i).containNode(node)) {
                    edge = edges.get(i);
                    break;
                }
            }
            if (edge == null) break;
            else edges.remove(edge);
        }
        nodes.remove(node);
    }

    public void clear() {
        number = 1;
        nodes.clear();
        edges.clear();
        find = false;

        start = null;
        end = null;
    }
}
