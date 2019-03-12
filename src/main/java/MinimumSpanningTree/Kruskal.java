package MinimumSpanningTree;

/*
* Kruskal 算法：
*
* - 在 LazyPrim、Prim 算法中，我们每次都是先找到一个切分的横切边，再找这些横切边中权值最小的那个，则该边即是最小生成树
*   中的一段。其实如果从另一个方向思考 —— 如果我们每次先去找所有边中的最小边，只要该边不和之前找到的最小边之间构成环（因
*   为树中是不能有环的），则这些边就是最小生成树中的边，这就是 Kruskal 算法的思想。
* - 证明：对于每次找到的最小边，我们都能找到一个切分，使得该最小边是该切分的横切边，因此符合切分定理（Cut Property）。
* - 动画演示 SEE: https://coding.imooc.com/lesson/71.html#mid=1492（1'48''）
* */

public class Kruskal {
}
