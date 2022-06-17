package gui;

import model.Edge;
import model.Node;
import stl.Array;

import java.awt.*;
import java.awt.event.MouseEvent;


public class MyPaint {
    private Graphics2D graphics2D;
    private static int radius = 20; // 默认的节点的半径

    public MyPaint(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    // 作用是判断鼠标是否选中了某个点(城市)
    public static boolean isWithinBounds(MouseEvent mouseEvent, Point point) {
        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();

        double pointX = point.getX();
        double pointY = point.getY();

        return Math.pow(mouseX - pointX, 2) + Math.pow(mouseY - pointY, 2) <= (radius - 5) * (radius - 5);
    }

    // 作用是判断是不是在某些点的周围新建点(判断新建点是否会与已经存在的点发生重叠)
    public static boolean isOverlapping(MouseEvent mouseEvent, Point point) {
        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();

        double pointX = point.getX();
        double pointY = point.getY();

        return Math.pow(mouseX - pointX, 2) + Math.pow(mouseY - pointY, 2) <= 4 * radius * radius;
    }

    // 判断是不是选中了一条边
    public static boolean isOnEdge(MouseEvent mouseEvent, Edge edge) {
        Node u = edge.getU();
        Node v = edge.getV();

        double minX = Math.min(u.getX(), v.getX());
        double maxX = Math.max(u.getX(), v.getX());
        double minY = Math.min(u.getY(), v.getY());
        double maxY = Math.max(u.getY(), v.getY());

        if (mouseEvent.getX() > maxX || mouseEvent.getX() < minX || mouseEvent.getY() > maxY || mouseEvent.getY() < minY) return false;
        else if (mouseEvent.getX() == maxX || mouseEvent.getX() == minX){
            if (minY <= mouseEvent.getY() && mouseEvent.getY() <= maxY) return true;
            else return false;
        } else {
            double y1 = maxY - mouseEvent.getY();
            double y2 = mouseEvent.getY() - minY;
            double x1 = maxX - mouseEvent.getX();
            double x2 = mouseEvent.getX() - minX;
//            System.out.println(y1 * x2 + " " + y2 * x1);
            if (Math.abs(y1 / x1 - y2 / x2) < 0.7) return true;
            else return false;
        }
    }

    public void drawNode (Node node) {
        graphics2D.setColor(new Color(156, 39, 176));
        graphics2D.fillOval((int)node.getX() - radius, (int)node.getY() - radius, 2 * radius, 2 * radius);

        radius -= 5;

        graphics2D.setColor(new Color(225, 190, 231));
        graphics2D.fillOval((int)node.getX() - radius, (int)node.getY() - radius, 2 * radius, 2 * radius);

        radius += 5;
        String id = String.valueOf(node.getId());
        drawCenterMessage(id, node.getPoint());
    }

    // draw id and distance
    public void drawCenterMessage(String message, Point point) {
        graphics2D.setColor(Color.RED);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        double width = fontMetrics.getStringBounds(message, graphics2D).getWidth();
        graphics2D.drawString(message, (int)(point.x - width / 2), (int)(point.y + fontMetrics.getMaxAscent() / 2));
    }

    public void drawEdge(Edge edge) {
        Point u = edge.getU().getPoint();
        Point v = edge.getV().getPoint();

        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawLine(u.x, u.y, v.x, v.y);

        int midX = (u.x + v.x) / 2;
        int midY = (u.y + v.y) / 2;
        int tempRadius = radius / 2;
        graphics2D.fillOval(midX - tempRadius, midY - tempRadius, 2 * tempRadius, 2 * tempRadius);
        drawCenterMessage(String.valueOf(edge.getDistance()), new Point(midX, midY));
    }

    // 画出起点到终点的最短路径
    public void drawPath(Array<Node> nodes) {
        Array<Edge> edges = new Array<>();
        for (int i = 1; i < nodes.size(); i++)
            edges.push_back(new Edge(nodes.get(i), nodes.get(i - 1)));

        for (int i = 0; i < edges.size(); i++)
            drawPath(edges.get(i));
    }

    private void drawPath(Edge edge) {
        Point u = edge.getU().getPoint();
        Point v = edge.getV().getPoint();

        graphics2D.setColor(new Color(253, 192, 0));
        graphics2D.setStroke(new BasicStroke(8));
        graphics2D.drawLine(u.x, u.y, v.x, v.y);
    }

    public void drawNodeStart(Node node) {
        graphics2D.setColor(new Color(0, 188, 212));
        graphics2D.fillOval((int)node.getX() - radius, (int)node.getY() - radius, 2 * radius, 2 * radius);

        radius -= 5;

        graphics2D.setColor(new Color(178, 235, 242));
        graphics2D.fillOval((int)node.getX() - radius, (int)node.getY() - radius, 2 * radius, 2 * radius);

        radius += 5;
        String id = String.valueOf(node.getId());
        drawCenterMessage(id, node.getPoint());
    }

    public void drawNodeEnd(Node node) {
        graphics2D.setColor(new Color(244, 67, 54));
        graphics2D.fillOval((int)node.getX() - radius, (int)node.getY() - radius, 2 * radius, 2 * radius);

        radius -= 5;

        graphics2D.setColor(new Color(255, 205, 210));
        graphics2D.fillOval((int)node.getX() - radius, (int)node.getY() - radius, 2 * radius, 2 * radius);

        radius += 5;
        String id = String.valueOf(node.getId());
        drawCenterMessage(id, node.getPoint());
    }
}
