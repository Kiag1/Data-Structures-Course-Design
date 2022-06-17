package algorithm;

import model.Edge;
import model.Graph;
import model.Node;
import model.Pair;
import stl.Array;
import stl.MinHeap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dijkstra {
    private boolean safe = false;
    private String message = null;

    private Graph graph;
    private Array<Array<Pair>> G;
    private Array<Integer> parent;
    private Array<Boolean> vis;
    private Array<Integer> distance;
    private MinHeap<Pair> minHeap;
    final int inf = 0x3f3f3f3f;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        int nodeNumber = this.graph.size();
        G = new Array<>(nodeNumber + 1);
        vis = new Array<>(nodeNumber + 1);
        distance = new Array<>(nodeNumber + 1);
        parent = new Array<>(nodeNumber + 1);
        minHeap = new MinHeap<>();

        // 建立邻接表

        for (int i = 0; i < G.size(); i++)
            G.set(i, new Array<>());

        for (int i = 0; i < this.graph.getEdges().size(); i++) {
            int u = this.graph.getEdges().get(i).getU().getId();
            int v = this.graph.getEdges().get(i).getV().getId();
            int cost = this.graph.getEdges().get(i).getDistance();
            G.get(u).push_back(new Pair(v, cost));
            G.get(v).push_back(new Pair(u, cost));
        }

        safe = checkSafe();
    }

    private boolean checkSafe() {
        if (graph.getStart() == null) {
            message = "未定义起点";
            return false;
        } else if (graph.getEnd() == null) {
            message = "未定义终点";
            return false;
        } else return true;
    }

    public void run() throws IllegalStateException{

        if (safe == false) {
            throw new IllegalStateException(message);
        }

        // 初始化
        for (int i = 0; i < vis.size(); i++) {
            vis.set(i, false);
            distance.set(i, inf);
            parent.set(i, -1);
        }

        // 将起始点的distance初始化为0
        int start = graph.getStart().getId();
        int end = graph.getEnd().getId();


        distance.set(start, 0);
        minHeap.push(new Pair(start, 0));

        while (!minHeap.empty()) {
            int now = minHeap.top().now, cost = minHeap.top().cost;
            minHeap.pop();
            if (vis.get(now) == true) continue;
            vis.set(now, true);

            for (int i = 0; i < G.get(now).size(); i++) {
                int tempNow = G.get(now).get(i).now;
                int tempCost = G.get(now).get(i).cost;
                if (distance.get(tempNow) > distance.get(now) + tempCost) {
                    distance.set(tempNow, distance.get(now) + tempCost);
                    parent.set(tempNow, now);
                    minHeap.push(new Pair(tempNow, distance.get(tempNow)));
                }
            }
        }
    }

    public Array<Node> getNodes() throws IllegalStateException{
        int start = graph.getStart().getId();
        int end = graph.getEnd().getId();

        if (distance.get(end) == inf)
            throw new IllegalStateException("不存在从" + start + "到" + end + "的路径");
        else {
            graph.setFind(true);
        }

        Set<Integer> nodes = new HashSet<>();
        int now = end;
        while (now != -1) {
            nodes.add(now);
            now = parent.get(now);
        }

        Array<Node> res = new Array<>();

        for (int i = 0; i < this.graph.getNodes().size(); i++) {
            int nodeNumber = this.graph.getNodes().get(i).getId();

            if (nodes.contains(nodeNumber)) {
                res.push_back(this.graph.getNodes().get(i));
            }
        }

        return res;
    }

    public Array<Edge> getEdge() throws IllegalStateException {
        int start = graph.getStart().getId();
        int end = graph.getEnd().getId();

        if (distance.get(end) == inf)
            throw new IllegalStateException("不存在从" + start + "到" + end + "的路径");
        else
            graph.setFind(true);

        Set<Integer> nodes = new HashSet<>();
        int now = end;
        while (parent.get(now) != -1) {
            nodes.add(now);
            now = parent.get(now);
        }

        Array<Edge> res = new Array<>();
        for (int i = 0; i < this.graph.getEdges().size(); i++) {
            int u = this.graph.getEdges().get(i).getU().getId();
            int v = this.graph.getEdges().get(i).getV().getId();
            if (nodes.contains(u) && nodes.contains(v))
                res.push_back(this.graph.getEdges().get(i));
        }

        return res;
    }
}