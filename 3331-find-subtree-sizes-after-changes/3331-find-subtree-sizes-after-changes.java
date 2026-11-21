
class Solution {
    List<Integer>[] tree;
    List<Integer>[] newTree;
    int[] ans;
    int[] last;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        tree = new ArrayList[n];
        newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
            newTree[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] newParent = parent.clone();
        last = new int[26];
        Arrays.fill(last, -1);

        // Find nearest matching ancestor
        dfs(0, parent, newParent, s);

        // Build modified tree
        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        ans = new int[n];

        // Calculate subtree sizes
        calculateSize(0);

        return ans;
    }

    void dfs(int node, int[] parent, int[] newParent, String s) {
        int ch = s.charAt(node) - 'a';

        int previous = last[ch];

        if (previous != -1) {
            newParent[node] = previous;
        }

        last[ch] = node;

        for (int child : tree[node]) {
            dfs(child, parent, newParent, s);
        }

        last[ch] = previous;
    }

    int calculateSize(int node) {
        int size = 1;

        for (int child : newTree[node]) {
            size += calculateSize(child);
        }

        return ans[node] = size;
    }
}
