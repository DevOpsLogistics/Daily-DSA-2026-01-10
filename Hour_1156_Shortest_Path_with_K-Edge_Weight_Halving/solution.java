import java.util.*;

public class Solution {
    static class Node implements Comparable<Node> {
        int u, k;
        long dist;

        Node(int u, int k, long dist) {
            this.u = u;
            this.k = k;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return Long.compare(this.dist, other.dist);
        }
    }

    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public long solve(int N, int M, int K, int[][] edges) {
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(new Edge(edge[1], edge[2]));
        }

        long[][] dist = new long[N + 1][K + 1];
        for (long[] row : dist) Arrays.fill(row, Long.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[1][0] = 0;
        pq.add(new Node(1, 0, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.u;
            int k = current.k;

            if (current.dist > dist[u][k]) continue;

            for (Edge e : adj.get(u)) {
                // Option 1: Don't use power-up
                if (dist[e.to][k] > dist[u][k] + e.weight) {
                    dist[e.to][k] = dist[u][k] + e.weight;
                    pq.add(new Node(e.to, k, dist[e.to][k]));
                }

                // Option 2: Use power-up if k < K
                if (k < K) {
                    int halvedWeight = e.weight / 2;
                    if (dist[e.to][k + 1] > dist[u][k] + halvedWeight) {
                        dist[e.to][k + 1] = dist[u][k] + halvedWeight;
                        pq.add(new Node(e.to, k + 1, dist[e.to][k + 1]));
                    }
                }
            }
        }

        long minDistance = Long.MAX_VALUE;
        for (int k = 0; k <= K; k++) {
            minDistance = Math.min(minDistance, dist[N][k]);
        }

        return minDistance == Long.MAX_VALUE ? -1 : minDistance;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] edges = {{1, 2, 10}, {2, 3, 20}};
        System.out.println(sol.solve(3, 2, 1, edges)); // Output: 20
    }
}