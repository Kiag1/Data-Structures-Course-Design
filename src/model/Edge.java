package model;

//  implements Comparable<Edge>
public class Edge {
    private Node u; // 起点
    private Node v; // 终点
    private int distance = 1;

    public Edge(Node u, Node v) {
        this.u = u;
        this.v = v;
    }

    public Node getU() {
        return this.u;
    }

    public Node getV() {
        return this.v;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return this.distance;
    }

    public boolean containNode(Node node) {
        return this.u == node || this.v == node;
    }

    public boolean equal(Edge edge) {
        return (this.u == edge.u && this.v == edge.v) || (this.u == edge.v && this.v == edge.u);
    }

//    @Override
//    public int compareTo(Edge edge) {
//        if (this.distance == edge.distance) return 0;
//        else if (this.distance < edge.distance) return 1;
//        else return -1;
//    }

    @Override
    public String toString() {
        return "城市" + this.u.getId() + " 到 城市" + this.v.getId();
    }
}