package algorithm;

import model.Graph;
import stl.Array;

public class Floyd {
    private Graph graph;
    private Array<Array<Integer>> distance;
    final int inf = 0x3f3f3f3f;
    private int n;

    public Floyd(Graph graph) {
        this.graph = graph;

        // 先初始化distance
        n = this.graph.size();
        distance = new Array<>(n + 1);
        for (int i = 0; i <= n; i++)
            distance.set(i, new Array<>(n + 1));

        // 将distance中的值设为无穷大
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j)
                    distance.get(i).set(j, 0);
                else
                    distance.get(i).set(j, inf);
            }
        }

        for (int i = 0; i < this.graph.getEdges().size(); i++) {
            int u = this.graph.getEdges().get(i).getU().getId();
            int v = this.graph.getEdges().get(i).getV().getId();
            int cost = this.graph.getEdges().get(i).getDistance();

            if (cost > distance.get(u).get(v)) continue;
            else {
                distance.get(u).set(v, cost);
                distance.get(v).set(u, cost);
            }
        }
    }

    public void run() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    int cost = distance.get(i).get(j);
                    int tempCost = distance.get(i).get(k) + distance.get(k).get(j);
                    distance.get(i).set(j, Math.min(cost, tempCost));
                }
            }
        }
    }

    public Array<Array<Integer>> getDistance() {
        return distance;
    }

    public Array<Array<String>> getDistanceTable() {
        Array<Array<String>> tableDistance = new Array<>(n + 1);
        for (int i = 0; i <= n; i++)
            tableDistance.set(i, new Array<>(n + 1));

        for (int i = 0; i <= n; i++) {
            tableDistance.get(0).set(i, String.valueOf(i));
            tableDistance.get(i).set(0, String.valueOf(i));
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (distance.get(i).get(j) == inf)
                    tableDistance.get(i).set(j, "inf");
                else
                    tableDistance.get(i).set(j, String.valueOf(distance.get(i).get(j)));
            }
        }

        tableDistance.get(0).set(0, "");
        return tableDistance;
    }
}
