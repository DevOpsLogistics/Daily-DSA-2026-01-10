# Maximum Weight Independent Set of Exactly Size K

**Language:** JavaScript | **Date:** 2026-01-10 11:02

## Description
Given a tree with N nodes, where each node has an associated weight. An 'Independent Set' is a set of nodes in which no two nodes are adjacent (connected by an edge). Your task is to find the maximum total weight of an independent set that contains exactly K nodes.

Input:
- weights: An array of N integers where weights[i] is the weight of node i.
- edges: A 2D array of N-1 edges where edges[j] = [u, v] represents an undirected edge between nodes u and v.
- k: An integer representing the required size of the independent set.

Output:
- Return the maximum weight, or -1 if it's impossible to form an independent set of size K.

Example:
Input: weights = [10, 50, 20, 20], edges = [[0, 1], [0, 2], [0, 3]], k = 2
Output: 40
Explanation: Node 1 is connected to 0, 2, 3. Independent sets of size 2 are {1, 2}, {1, 3}, {2, 3}. Weights are 50+20=70, 50+20=70, 20+20=40. However, {1, 2} is NOT an independent set because node 1 is only connected to 0. Wait, in this tree (0 is center), nodes {1, 2, 3} are not connected to each other. So {1, 2}, {1, 3}, {2, 3} are independent. The max weight is 50+20=70. (Note: Example corrected: if 0 is connected to 1, 2, 3, then 1, 2, 3 are leaves and not connected to each other).

## Explanation
This problem is solved using Dynamic Programming on Trees (Tree DP). We define dp[u][k][state] as the maximum weight of an independent set of size k within the subtree rooted at u, where state 0 means node u is not included and state 1 means node u is included. Transitions: 1) If u is not included (state 0), its children can either be included or not. 2) If u is included (state 1), its children must not be included. We use a knapsack-like merging process at each node to combine results from children. By limiting the inner loops to the size of the subtrees (subtreeSize), the overall time complexity is optimized to O(N * K). Space complexity is O(N * K) to store the DP table.
