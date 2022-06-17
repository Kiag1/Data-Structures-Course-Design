package gui;

import model.Edge;
import model.Graph;
import model.Node;
import stl.Array;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {
    private MyPaint myPaint;
    private Graph graph;

    private Node selectedNode = null;
    private Node hoveredNode = null;
    private Edge hoveredEdge = null;

    private Array<Node> path = null;
    private Point cursorPoint;

    public GraphPanel(Graph graph) {
        this.graph = graph;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setPath(Array<Node> path) {
        this.path = path;
        hoveredEdge = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        // 只起到美化作用 并没有什么实质性的作用
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        myPaint = new MyPaint(graphics2D);

        // 如果当前状态是已经找到了两点之间的最短路径的话就直接调用drawPath
        if (graph.isFind()) {
            myPaint.drawPath(path);
        }

        if (selectedNode != null && cursorPoint != null) {
            Edge edge = new Edge(selectedNode, new Node(cursorPoint));
            myPaint.drawEdge(edge);
        }

        Array<Edge> edges = graph.getEdges();
        for (int i = 0; i < edges.size(); i++)
            myPaint.drawEdge(edges.get(i));


        Array<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            if (graph.isStart(nodes.get(i)))
                myPaint.drawNodeStart(nodes.get(i));
            else if (graph.isEnd(nodes.get(i)))
                myPaint.drawNodeEnd(nodes.get(i));
            else
                myPaint.drawNode(nodes.get(i));
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        Node selectNode = null;
        Array<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            if (myPaint.isWithinBounds(mouseEvent, nodes.get(i).getPoint())) {
                selectNode = nodes.get(i);
                break;
            }
        }

        // 如果选择某个点
        if (selectNode != null) {
            // 如果此时键盘同时按下了Ctrl + Shift
            if (mouseEvent.isControlDown() && mouseEvent.isShiftDown()) {
                graph.removeNode(selectNode);

                //

                graph.setFind(false);
                repaint();
                return;
            } else if (mouseEvent.isControlDown() && graph.isFind()) {
                path = selectNode.getPath();
                repaint();
                return;
            } else if (mouseEvent.isShiftDown()) { // 如果键盘按下了Shift
                // 如果鼠标按下了左键
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    if (!graph.isEnd(selectNode))
                        graph.setStart(selectNode);
                    else
                        JOptionPane.showMessageDialog(null, "不能将终点设为起点");
                } else if (SwingUtilities.isRightMouseButton(mouseEvent)){
                    if (!graph.isStart(selectNode))
                        graph.setEnd(selectNode);
                    else
                        JOptionPane.showMessageDialog(null, "不能将起点设为终点");
                } else return;
                graph.setFind(false);
                repaint();
                return;
            }
        }

        if (hoveredEdge != null) {
            if (mouseEvent.isShiftDown() && mouseEvent.isControlDown()) {
                graph.getEdges().remove(hoveredEdge);
                hoveredEdge = null;
                graph.setFind(false);
                repaint();
                return;
            }
            String tempDistance = JOptionPane.showInputDialog("请输入" + hoveredEdge.toString() + " 的距离");

            try {
                int distance = Integer.parseInt(tempDistance);
                if (distance > 0) {
                    hoveredEdge.setDistance(distance);
                    graph.setFind(false);
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "城市之间距离不能为负数");
                }
            } catch (NumberFormatException numberFormatException) {}
            return;
        }

        for (int i = 0; i < graph.getNodes().size(); i++) {
            if (myPaint.isOverlapping(mouseEvent, graph.getNodes().get(i).getPoint())) {
                JOptionPane.showMessageDialog(null, "禁止在现有的点周围新建");
                repaint();
                return;
            }
        }


        graph.addNode(mouseEvent.getPoint());
        graph.setFind(false);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // 这个释放鼠标的方法作用是连接两个点建立一条边
        Array<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            // 如果当前已经选中了一个城市 且现在鼠标悬停在的城市不是选中的城市， 并且鼠标和城市靠近 就建立连接
            if (selectedNode != null && nodes.get(i) != selectedNode && myPaint.isWithinBounds(mouseEvent, nodes.get(i).getPoint())) {
                Edge edge = new Edge(selectedNode, nodes.get(i));
                graph.addEdge(edge);
                graph.setFind(false);
            }
        }

        selectedNode = null;
        hoveredNode = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        // 拖拽 包括点的拖拽和边的拖拽
        hoveredNode = null;
        Array<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            if (selectedNode == null && myPaint.isWithinBounds(mouseEvent, nodes.get(i).getPoint()))
                selectedNode = nodes.get(i);
            else if (myPaint.isWithinBounds(mouseEvent, nodes.get(i).getPoint()))
                hoveredNode = nodes.get(i);
        }

        if (selectedNode != null) {
            if (mouseEvent.isControlDown()) {
                selectedNode.setPoint(mouseEvent.getPoint());
                cursorPoint = null;
                repaint();
                return;
            }

            cursorPoint = mouseEvent.getPoint();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.isControlDown()) {
            hoveredNode = null;
            Array<Node> nodes = graph.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
                if (myPaint.isWithinBounds(mouseEvent, nodes.get(i).getPoint()))
                    hoveredNode = nodes.get(i);
            }
        }

        hoveredEdge = null;
        Array<Edge> edges = graph.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            if (myPaint.isOnEdge(mouseEvent, edges.get(i)))
                hoveredEdge = edges.get(i);
        }

        repaint();
    }

    public void reset() {
        graph.clear();
        selectedNode = null;
        hoveredNode = null;
        hoveredEdge = null;
        repaint();
    }
}