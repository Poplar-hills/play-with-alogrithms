package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static Utils.Helpers.*;

/*
* Dense graph implemented by adjacency matrix（使用邻接矩阵实现稠密图）
* */

public class DenseGraph implements Graph {
    private int n, m;  // n 为节点数，m 为边数
    private boolean directed;  // 该图是否为有向图
    private boolean[][] graph;  // 图的结构是二维布尔数组，graph[i][j] 表示顶点 i 与顶点 j 是否相连

    public DenseGraph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        m = 0;                      // 边数初始化为0
        graph = new boolean[n][n];  // 初始化 n * n 的矩阵，
        for (int i = 0; i < n; i++)
            graph[i] = new boolean[n];  // 初始矩阵内没有相连接的顶点（默认都为 false）
    }

    /*
     * 增操作
     * */
    public void addEdge(int v, int w) {  // 在顶点 v 和 w 之间建立一条边
        if (v < 0 || v >= n || w < 0 || w >= m)
            throw new IllegalArgumentException("addEdge failed. Vertex index is out of boundary");

        if (hasEdge(v, w))  // 两点之间是否已存在边（该实现中不允许平行边）
            return;

        graph[v][w] = true;
        if (!directed)
            graph[w][v] = true;
        m++;
    }

    /*
     * 查操作
     * */
    public boolean hasEdge(int v, int w) {
        if (v < 0 || v >= n || w < 0 || w >= n)
            throw new IllegalArgumentException("hasEdge failed. Vertex index is out of boundary");
        return graph[v][w];
    }

    public int getVertexCount() { return n; }

    public int getEdgeCount() { return m; }

    /*
     * Misc
     * */
    public Iterable<Integer> adjIterator(int v) {  // 读取一个顶点的所有邻边（∵ 不能暴露 graph 给外界 ∴ 使用迭代器模式，返回一个访问某一顶点的边的迭代器）
        if (v < 0 || v >= n)
            throw new IllegalArgumentException("adjIterator failed. Vertex index is out of boundary");

        ArrayList<Integer> edges = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (graph[v][i])  // 如果为 true 则添加索引 i 进 edges
                edges.add(i);
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                s.append(graph[i][j]);
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        DenseGraph g = new DenseGraph(4, false);
        log(g);
    }
}
