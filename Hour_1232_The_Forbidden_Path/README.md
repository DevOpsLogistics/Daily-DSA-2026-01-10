# The Forbidden Path

**Language:** Go | **Date:** 2026-01-10 12:32

## Description
Given a tree with N nodes and N-1 weighted edges. Each node is either 'Safe' (0) or 'Corrupted' (1). You need to find the maximum possible weight (sum of edge weights) of a simple path between any two nodes such that the path contains at most K 'Corrupted' nodes.

Input:
- n: number of nodes (1 <= n <= 10^5)
- k: maximum allowed corrupted nodes (0 <= k <= 20)
- colors: an array of size n where colors[i] is 0 (Safe) or 1 (Corrupted)
- edges: a 2D array where edges[i] = [u, v, w] representing a weighted edge between u and v.

Example:
n = 3, k = 1
colors = [0, 1, 0]
edges = [[0, 1, 10], [1, 2, 5]]
Output: 15 (Path 0-1-2 has one corrupted node (node 1), total weight 10+5=15)

Example 2:
n = 3, k = 0
colors = [0, 1, 0]
edges = [[0, 1, 10], [1, 2, 5]]
Output: 0 (Any path involving node 1 is forbidden. Only paths are single nodes 0 or 2 with weight 0).

## Explanation
The problem is solved using Dynamic Programming on Trees. We define dp[u][k] as the maximum distance from node u to any node in its subtree such that the path contains exactly k corrupted nodes. 

1. For each node u, we recursively calculate the DP states of its children.
2. When processing a child v of u, we first update the global maximum answer by combining the current path from u and the path coming from v. The condition is that the total number of corrupted nodes (count_u + count_v - color[u]) must be less than or equal to K. Note that color[u] is subtracted because it is counted in both dp[u] and subDP[v].
3. After checking the path combination, we update dp[u] with values from subDP[v] + weight(u, v).
4. Complexity: The state space is N * K. During merging, we use nested loops up to K, resulting in O(N * K^2). Given K is small (20), this is efficient enough for N = 10^5.
