package MinimumSpanningTree.AuxiliaryDataStructure;

import java.util.Arrays;

import static Utils.Helpers.log;

/*
* 并查集（作为 KruskalMST 的辅助数据结构）
*
* - 采用了 UnionFind6 中的实现，即 Quick Union + rank 优化 + path compression 优化。
* */

public class UnionFind {
    private int[] parents;
    private int[] ranks;

    public UnionFind(int size) {
        parents = new int[size];
        ranks = new int[size];

        for (int i = 0; i < size; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }
    }

    private int find(int p) {
        if (p < 0 || p >= parents.length)
            throw new IllegalArgumentException("find failed. p is out of bound.");
        return (parents[p] == p) ? p : find(parents[p]);
    }

    public boolean isConnencted(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot)
            return;

        if (ranks[pRoot] < ranks[qRoot]) {
            parents[pRoot] = qRoot;
        } else if (ranks[pRoot] > ranks[qRoot]) {
            parents[qRoot] = pRoot;
        } else {
            parents[pRoot] = qRoot;
            ranks[qRoot]++;
        }
    }

    public int getSize() { return parents.length; }

    @Override
    public String toString() {
        return Arrays.toString(parents);
    }

    public static void main(String[] args) {
        UnionFind uf = new UnionFind(5);

        uf.union(2, 4);
        log(uf);
        uf.union(4, 0);
        log(uf);

        log(uf.isConnencted(2, 0));
    }
}
