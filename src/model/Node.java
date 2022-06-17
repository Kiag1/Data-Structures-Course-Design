package model;

import stl.Array;

import java.awt.*;

public class Node {
    private Point point = new Point();
    private int id;
    private Array<Node> path; // 他到其他点的路径 或者 其他点到他的路径

    public Node() {};

    public Node(int id) {
        this.id = id;
    }

    public Node(Point point) {
        this.point = point;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return this.point;
    }

    public void setPath(Array<Node> path) {
        this.path = path;
    }

    public Array<Node> getPath() {
        return this.path;
    }

    public double getX() {
        return this.point.getX();
    }

    public double getY() {
        return this.point.getY();
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Node" + this.id;
    }
}
