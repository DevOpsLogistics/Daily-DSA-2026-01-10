package main

import (
	"fmt"
)

type Edge struct {
	to     int
	weight int64
}

var (
	adj    [][]Edge
	colors []int
	maxK   int
	ans    int64
)

func max64(a, b int64) int64 {
	if a > b {
		return a
	}
	return b
}

func dfs(u, p int) []int64 {
	// dp[i] is the maximum weight of a path starting from u going down to a leaf 
	// in its subtree, containing exactly i corrupted nodes.
	dp := make([]int64, maxK+1)
	for i := range dp {
		dp[i] = -1
	}

	cu := colors[u]
	if cu <= maxK {
		dp[cu] = 0
	}

	for _, edge := range adj[u] {
		v, w := edge.to, edge.weight
		if v == p {
			continue
		}

		subDP := dfs(v, u)

		// Try to form a path passing through u by connecting two branches
		for i := 0; i <= maxK; i++ {
			if dp[i] == -1 {
				continue
			}
			for j := 0; j <= maxK-i+cu; j++ {
				if subDP[j] != -1 {
					// Path nodes = (nodes in branch i) + (nodes in branch j) - (node u counted twice)
					// But since cu is added to both, we subtract it once.
					if i+j-cu <= maxK {
						ans = max64(ans, dp[i]+subDP[j]+w)
					}
				}
			}
		}

		// Update u's DP state with the current branch
		for j := 0; j+cu <= maxK; j++ {
			if subDP[j] != -1 {
				dp[j+cu] = max64(dp[j+cu], subDP[j]+w)
			}
		}
	}

	for i := 0; i <= maxK; i++ {
		ans = max64(ans, dp[i])
	}

	return dp
}

func solve(n int, k int, c []int, e [][]int) int64 {
	adj = make([][]Edge, n)
	for _, edge := range e {
		u, v, w := edge[0], edge[1], int64(edge[2])
		adj[u] = append(adj[u], Edge{v, w})
		adj[v] = append(adj[v], Edge{u, w})
	}
	colors = c
	maxK = k
	ans = 0

	dfs(0, -1)
	return ans
}

func main() {
	n := 3
	k := 1
	colors := []int{0, 1, 0}
	edges := [][]int{{0, 1, 10}, {1, 2, 5}}
	fmt.Println(solve(n, k, colors, edges)) // Output: 15
}