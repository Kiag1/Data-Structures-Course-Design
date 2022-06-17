package stl;

public class Array<E> {
    private E[] data;
    private int size;

    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = capacity;
    }

    public Array() {
        this(10);
        size = 0;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return data.length;
    }

    public boolean empty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
    }

    public void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    public void insert(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("illegal index edge cannot insert anymore element");
        }
        if (size == data.length) {
            resize(2 * data.length);
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = e;
        size++;
    }

    public void addFirst(E e) {
        insert(0, e);
    }

    public void addLast(E e) {
        insert(size, e);
    }

    public void push_back(E e) {
        addLast(e);
    }

    public int count(E e) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                count++;
            }
        }
        return count;
    }

    public int find(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("下标越界了 不能访问");
        }
        return data[index];
    }

    public E getFirst() {
        return data[0];
    }

    public E getLast() {
        return data[size - 1];
    }

    public int getLastIndex() { return size - 1;}

    public void swap(int swap1, int swap2) {
        if (swap1 < 0 || swap1 >= size || swap2 < 0 || swap2 >= size)
            throw new IllegalArgumentException("Argument index out of bound");
        E tmp = data[swap1];
        data[swap1] = data[swap2];
        data[swap2] = tmp;
        // swap operation finish
    }

    public void remove(E e) {
        remove(find(e));
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Illegal index edge can not remove the current element");
        }
        E ans = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        return ans;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    public E pop_back() {
        return removeLast();
    }


    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("下标越界了 无法修改对应下标元素");
        }
        data[index] = e;
    }

    //    public void set(int index, E e) {
//        if (index < 0 || index > size) {
//            throw new IllegalArgumentException("Illegal index edge can not set the current element");
//        }
//        if (index == size) {
//            resize(2 * data.length);
//        }
//        data[size++] = e;
//    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();

        // ans.append(String.format("现在数组的大小为: %d, 数组的容量为: %d\n", size, data.length));
        // ans.append("[");

        for (int i = 0; i < size; i++) {
            ans.append(data[i]);
            ans.append("->");
        }
        ans.append("NULL");
        return ans.toString();
    }

    public static void main(String[] args) {
        Array<Integer> e = new Array<>();
        for (int i = 0; i < 10; i++) {
            e.push_back(i);
            System.out.println(e);
        }
        e.set(10, 1);
        System.out.println(e);
    }

}