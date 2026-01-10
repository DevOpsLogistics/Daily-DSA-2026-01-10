/**
 * @param {number[]} weights
 * @param {number[][]} edges
 * @param {number} K
 * @return {number}
 */
function maxWeightIndependentSet(weights, edges, K) {
    const n = weights.length;
    if (K === 0) return 0;
    if (K > n) return -1;

    const adj = Array.from({ length: n }, () => []);
    for (const [u, v] of edges) {
        adj[u].push(v);
        adj[v].push(u);
    }

    // dp[u][k][0]: Max weight in subtree u with k nodes, u NOT picked
    // dp[u][k][1]: Max weight in subtree u with k nodes, u IS picked
    const dp = Array.from({ length: n }, () => 
        Array.from({ length: K + 1 }, () => [-1, -1])
    );

    const subtreeSize = new Array(n).fill(0);

    function dfs(u, p) {
        dp[u][0][0] = 0;
        if (K >= 1) dp[u][1][1] = weights[u];
        subtreeSize[u] = 1;

        for (const v of adj[u]) {
            if (v === p) continue;
            dfs(v, u);

            const next_dp = Array.from({ length: K + 1 }, () => [-1, -1]);
            const limitU = Math.min(subtreeSize[u], K);
            const limitV = Math.min(subtreeSize[v], K);

            for (let i = 0; i <= limitU; i++) {
                for (let j = 0; j <= limitV && i + j <= K; j++) {
                    // Case 1: u is not picked, v can be picked or not picked
                    if (dp[u][i][0] !== -1) {
                        const maxV = Math.max(dp[v][j][0], dp[v][j][1]);
                        if (maxV !== -1) {
                            next_dp[i + j][0] = Math.max(next_dp[i + j][0], dp[u][i][0] + maxV);
                        }
                    }
                    // Case 2: u is picked, v MUST NOT be picked
                    if (dp[u][i][1] !== -1) {
                        if (dp[v][j][0] !== -1) {
                            next_dp[i + j][1] = Math.max(next_dp[i + j][1], dp[u][i][1] + dp[v][j][0]);
                        }
                    }
                }
            }
            
            subtreeSize[u] += subtreeSize[v];
            for (let k = 0; k <= K; k++) {
                dp[u][k][0] = next_dp[k][0];
                dp[u][k][1] = next_dp[k][1];
            }
        }
    }

    dfs(0, -1);
    const result = Math.max(dp[0][K][0], dp[0][K][1]);
    return result < 0 ? -1 : result;
}