package ShortestPath;

import Graph.SparseGraph;
import MinimumSpanningTree.Edge;
import MinimumSpanningTree.WeightedGraph;
import MinimumSpanningTree.WeightedGraphReader.WeightedGraphReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static Utils.Helpers.log;

/*
* Bellman-ford 单源最短路径算法（Bellman-ford Single Source Shortest Path）
*
* - 概要
*   - 可以为含有负权边（negative weight edge）的图计算最短路径（Dijkstra 算法则不行）
*   - 相应的代价是复杂度是 O(EV)，大大高于 Dijkstra 算法。
*
* - 前提：
*   - 计算含有负权边的图的最短路径仍然依赖于松弛操作（relaxation）。
*   - 负权环：
*     - 如果一幅图中包含负权环（negative weight cycle），则该图中不存在最短路径，因为在环中每转一圈得到的总距离就越小。
*     - 例：3个顶点形成的负权环：0->1: 5, 1->2: -4, 2->0: -3；2个顶点形成的负权环：1->2: -4, 2->1: 1
*     - Bellman-ford 算法允许图中含有负权边，但不允许有负权环。
*
* - 算法原理 & 复杂度：
*   - 在 Dijkstra 算法中，因为前提是图中没有负权边，因此当找到某个顶点的所有邻边中最短的那条时就可以认定该边一定在最短路径树上。
*     而当图中存在负权边时，该假设就不再成立，因为经过更多节点的路径可能总距离反而更短，因此需要检查是否存在这种路径。
*   - 检查的办法就是反复对图中的每条边进行松弛操作，使得起始顶点到每个顶点的距离逐步逼近其最短距离。
*               --- g ----
*             / d ---- e  \
*            f  |   c  |  h    图中从 a 到 b 有4条不同的路径，反复对边 a->b 进行松弛以检查哪条路径的距离最短。
*             \ | /   \| /
*               a ---- b
*   - 在一幅图中，从起始顶点到某一顶点最多会经过 V 个顶点（即经过所有顶点）、最多有 V-1 条边。对所有边进行一次松弛操作能够确定
*     最小路径树上的一段路径，因为最小路径树最多有 V-1 段，因此需要对所有边进行 V-1 轮松弛操作即可找到完整的最小路径树。
*   - 对所有边条边进行一次松弛操作的复杂度是 O(E)，要进行 V-1 轮这样的操作，因此整体复杂度是 O(VE)。
*
* - 实现：
*   - 因为 Bellman-ford 算法是通过遍历来逐步趋近每个顶点的最短距离，不再需要对边比较，因此也就不需要使用索引堆作为辅助数据结构了。
* */

public class BellmanFord<Weight extends Number & Comparable<Weight>> {
    private WeightedGraph graph;
    private int source;
    private Weight[] distances;
    private List<Edge<Weight>> spt;

    public BellmanFord(WeightedGraph graph, int source) {
        this.graph = graph;
        this.source = source;
        int n = graph.getVertexCount();
        distances = (Weight[]) new Object[n];
        spt = new ArrayList<>();

        bellmanFord();
    }

    private void bellmanFord() {

    }

    public Weight[] distances() { return distances; }

    public Weight distanceTo(int w) { return distances[w]; }

    public List<Edge<Weight>> shortestPathTree() { return spt; }

    public List<Edge<Weight>> shortestPathTo(int w) {
        Stack<Edge<Weight>> stack = new Stack<>();
        List<Edge<Weight>> path = new ArrayList<>();

        int targetVertex = w;
        for (int i = spt.size() - 1; i >= 0; i--) {
            Edge<Weight> e = spt.get(i);
            if (e.w() == targetVertex) {
                targetVertex = e.v();
                stack.add(e);
            }
        }

        while (!stack.empty())
            path.add(stack.pop());

        return path;
    }

    public static void main(String[] args) {
        WeightedGraph<Double> g = new WeightedGraphReader()
                .read("src/main/java/ShortestPath/TestData/testG2.txt")
                .build(SparseGraph.class, true);

        BellmanFord<Double> b = new BellmanFord<>(g, 0);

        log(b.shortestPathTree());
        log(b.distances);
//        log(b.shortestPathTo(1));
//        log(b.shortestPathTo(2));
//        log(b.shortestPathTo(3));
//        log(b.shortestPathTo(4));
    }
}
