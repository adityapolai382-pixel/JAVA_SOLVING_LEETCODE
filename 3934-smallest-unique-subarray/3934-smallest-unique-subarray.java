
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {

        int n = nums.length;

        // Step 1: Initialize suffix array
        Integer[] sa = new Integer[n];
        int[] rank = nums.clone();

        for (int i = 0; i < n; i++) {
            sa[i] = i;
        }

        // Step 2: Build suffix array using doubling
        for (int k = 1; k < n; k *= 2) {

            final int len = k;
            final int[] r = rank;

            Arrays.sort(sa, (a, b) -> {

                if (r[a] != r[b]) {
                    return Integer.compare(r[a], r[b]);
                }

                int x = (a + len < n) ? r[a + len] : -1;
                int y = (b + len < n) ? r[b + len] : -1;

                return Integer.compare(x, y);
            });

            int[] newRank = new int[n];

            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {

                int a = sa[i - 1];
                int b = sa[i];

                int a2 = (a + k < n) ? rank[a + k] : -1;
                int b2 = (b + k < n) ? rank[b + k] : -1;

                if (rank[a] != rank[b] || a2 != b2) {
                    newRank[b] = newRank[a] + 1;
                } else {
                    newRank[b] = newRank[a];
                }
            }

            rank = newRank;

            if (rank[sa[n - 1]] == n - 1) {
                break;
            }
        }

        // Step 3: Find the position of each suffix
        int[] pos = new int[n];

        for (int i = 0; i < n; i++) {
            pos[sa[i]] = i;
        }

        // Step 4: Calculate LCP using Kasai's algorithm
        int[] lcp = new int[n];

        int h = 0;

        for (int i = 0; i < n; i++) {

            int r = pos[i];

            if (r == 0) {
                h = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n && j + h < n
                    && nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        // Step 5: Find minimum unique subarray length
        int answer = n;

        for (int i = 0; i < n; i++) {

            int maxLcp = 0;

            // LCP with previous suffix
            if (i > 0) {
                maxLcp = Math.max(maxLcp, lcp[i]);
            }

            // LCP with next suffix
            if (i < n - 1) {
                maxLcp = Math.max(maxLcp, lcp[i + 1]);
            }

            // One more element makes the prefix unique
            int length = maxLcp + 1;

            if (length <= n - sa[i]) {
                answer = Math.min(answer, length);
            }
        }

        return answer;
    }
}
