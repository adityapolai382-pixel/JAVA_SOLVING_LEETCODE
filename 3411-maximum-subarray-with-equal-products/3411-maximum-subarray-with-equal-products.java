
import java.math.BigInteger;

class Solution {
    public int maxLength(int[] nums) {

        int n = nums.length;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {

            BigInteger product = BigInteger.ONE;
            BigInteger gcd = BigInteger.ZERO;
            BigInteger lcm = BigInteger.ONE;

            for (int j = i; j < n; j++) {

                BigInteger num = BigInteger.valueOf(nums[j]);

                // Update product
                product = product.multiply(num);

                // Update GCD
                gcd = gcd.gcd(num);

                // Update LCM
                lcm = lcm.divide(lcm.gcd(num)).multiply(num);

                // Check product equivalent condition
                if (product.equals(gcd.multiply(lcm))) {

                    int length = j - i + 1;

                    maxLen = Math.max(maxLen, length);
                }
            }
        }

        return maxLen;
    }
}
