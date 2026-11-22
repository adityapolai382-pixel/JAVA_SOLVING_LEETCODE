
import java.util.*;

class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int n = candies.length;
        List<Boolean> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int updated = candies[i] + extraCandies;
            boolean greatest = true;

            for (int j = 0; j < n; j++) {
                if (updated < candies[j]) {
                    greatest = false;
                    break;
                }
            }

            ans.add(greatest);
        }

        return ans;
    }
}
