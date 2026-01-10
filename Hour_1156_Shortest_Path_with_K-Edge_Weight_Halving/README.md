# Shortest Path with K-Edge Weight Halving

**Language:** Java | **Date:** 2026-01-10 11:56

## Description
Given a weighted directed graph with N vertices and M edges, find the minimum distance from vertex 1 to vertex N. You are allowed to apply a 'power-up' to at most K edges along your path. A 'power-up' on an edge with weight W reduces its weight to floor(W/2). 

Input Example:
N = 3, M = 2, K = 1
Edges: 
1 2 10
2 3 20

Output Example:
20

Explanation: The original path 1->2->3 has a total weight of 10 + 20 = 30. By using the one allowed power-up on the edge (2,3), the weight becomes 10 + floor(20/2) = 10 + 10 = 20.

## Explanation
The problem is solved using a modified Dijkstra's algorithm. We define the state of our search as (u, k), where 'u' is the current vertex and 'k' is the number of power-ups already used. The distance array `dist[u][k]` stores the minimum cost to reach node 'u' using exactly 'k' power-ups. For every edge (u, v) with weight 'w', we explore two possible transitions: (1) Moving to 'v' without using a power-up, transitioning to state (v, k) with weight 'w'. (2) Moving to 'v' using a power-up (if k < K), transitioning to state (v, k+1) with weight 'floor(w/2)'. The priority queue ensures we always process the state with the smallest accumulated distance. The total time complexity is O((N*K + M*K) * log(N*K)), and the space complexity is O(N*K + M).
