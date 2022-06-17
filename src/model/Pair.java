package model;

public class Pair implements Comparable<Pair>{
    public Integer now;
    public Integer cost;

    public Pair(Integer now, Integer distance) {
        this.now = now;
        this.cost = distance;
    }

    @Override
    public int compareTo(Pair pair) {
        if (this.cost == pair.cost) return 0;
        else if (this.cost < pair.cost) return 1;
        else return -1;
    }
}
