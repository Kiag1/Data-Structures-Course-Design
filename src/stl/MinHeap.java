package stl;

public class MinHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MinHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MinHeap() {
        data = new Array<>();
    }

    public int size() {
        return data.size();
    }

    public boolean empty() {
        return data.empty();
    }

    // 获取父亲节点
    private int parent(int index) {
        if (index < 0)
            throw new IllegalArgumentException(String.format("Argument index-%d out of bound", index));
        else if (index == 0)
            throw new IllegalArgumentException("index-0 do not have parent");
        else
            return (index - 1) / 2;
    }

    // 获取左子节点
    private int leftChild(int index) {
        if (index < 0)
            throw new IllegalArgumentException(String.format("Argument index-%d out of bound", index));
        else
            return index * 2 + 1;
    }

    // 获取右子节点
    private int rightChild(int index) {
        if (index < 0)
            throw new IllegalArgumentException(String.format("Argument index-%d out of bound", index));
        else
            return index * 2 + 2;
    }

    // 上传节点
    private void pushUp(int index) {
        // 需要上传的节点还不是根节点 并且 该节点的值比父节点的值还要大
        while (index > 0 && data.get(index).compareTo(data.get(parent(index))) < 0) {
            data.swap(index, parent(index));
            index = parent(index);
        }
    }

    // 最小堆的插入操作（优先队列的插入操作）
    public void push(E e) {
        data.push_back(e);
        pushUp(data.getLastIndex());
    }

    // 获取当前最小堆中的堆顶元素 即整个最小堆中的最小值
    public E top() {
        if (data.empty())
            throw new IllegalArgumentException("MinHeap is empty now, cannot get the top element");
        else
            return data.getFirst();
    }

    // 最小堆中的下传操作
    private void pushDown(int index) {
        // 当存在左节点（即至少存在一个子节点时 继续循环
        while (leftChild(index) < data.size()) {
            // next 表示接下来要下传的节点 （左右节点中值最小的节点）
            int next;

            // 存在右节点 并且 右节点的值要比左节点的值小  则 next 下一个节点为右节点 否则 为左子节点
            if (rightChild(index) < data.size() && data.get(rightChild(index)).compareTo(data.get(leftChild(index))) < 0)
                next = rightChild(index);
            else
                next = leftChild(index);

            // 如果当前节点的值比next节点的值要大则结束循环 否则继续循环
            if (data.get(index).compareTo(data.get(next)) <= 0)
                break;
            data.swap(index, next);
            index = next;
        }
    }

    // 取出最小堆中的堆顶元素返回 并删除该元素
    public E pop() {
        E topElement = top();

        data.swap(0, data.getLastIndex());
        data.pop_back();

        pushDown(0);
        return topElement;
    }

    public static void main(String[] args) {
        MinHeap<Integer> minHeap = new MinHeap<>();
        minHeap.push(1);
        minHeap.push(1);
        minHeap.push(2);
    }
}
