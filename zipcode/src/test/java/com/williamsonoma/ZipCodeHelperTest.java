package com.williamsonoma;

import org.testng.Assert;
import java.util.Arrays;
import static java.lang.System.out;

public class ZipCodeHelperTest {

    @org.testng.annotations.Test
    public void testMergeNull() {
        out.println("In testMergeNull with NULL object.");
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] result = zipCodeHelper.merge(null);
        out.printf("Result is %s\n", Arrays.toString(result));
        Assert.assertNull(result, "Merged result of a Null object should be Null.");
    }

    @org.testng.annotations.Test
    public void testMergeEmpty() {
        out.println("In testMergeEmpty with Empty array as input.");
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = {};
        ZipCodeRange[] result = zipCodeHelper.merge(zipCodes);
        out.printf("Result is %s\n", Arrays.toString(result));
        Assert.assertEquals(result.length,0, "Merged result of an empty range should be empty.");
    }

    @org.testng.annotations.Test(expectedExceptions = IllegalArgumentException.class)
    public void testMergeInvalidRange() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = {new ZipCodeRange(12345, 34567), new ZipCodeRange(54321, 12345)};
        out.printf("In testMergeInvalidRange with input %s\n", Arrays.toString(zipCodes));
        ZipCodeRange[] result = zipCodeHelper.merge(zipCodes);
        out.printf("Result is %s\n", Arrays.toString(result));
    }

    @org.testng.annotations.Test
    public void testMergeOne() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = { new ZipCodeRange(12345, 12345) };
        out.printf("In testMergeOne with input %s\n", Arrays.toString(zipCodes));
        ZipCodeRange[] result = zipCodeHelper.merge(zipCodes);
        out.printf("Result is %s\n", Arrays.toString(result));
        Assert.assertTrue(Arrays.equals(zipCodes, result), "Merged result of a single range should be same.");
    }

    @org.testng.annotations.Test
    public void testMergeDistinct() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = { new ZipCodeRange(12345, 12350),
                new ZipCodeRange(12360, 12390),
                new ZipCodeRange(13310, 13350)
        };
        out.printf("In testMergeDistinct with input %s\n", Arrays.toString(zipCodes));
        ZipCodeRange[] result = zipCodeHelper.merge(zipCodes);
        out.printf("Result is %s\n", Arrays.toString(result));
        Assert.assertTrue(Arrays.equals(zipCodes, result), "Merged result should be same as input.");
    }

    @org.testng.annotations.Test
    public void testMergeValid() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = { new ZipCodeRange(12330, 12350),
                new ZipCodeRange(12335, 12340),
                new ZipCodeRange(13310, 13350)
        };

        ZipCodeRange[] zipCodesExpected = { new ZipCodeRange(12330, 12350),
                new ZipCodeRange(13310, 13350)
        };

        out.printf("In testMergeValid with input %s\n", Arrays.toString(zipCodes));
        ZipCodeRange[] result = zipCodeHelper.merge(zipCodes);
        out.printf("Result is %s\n", Arrays.toString(result));
        Assert.assertTrue(areEqual(zipCodesExpected, result), "Merged result Invalid.");
    }

    @org.testng.annotations.Test
    public void testMergeValid2() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = { new ZipCodeRange(12330, 12350),
                new ZipCodeRange(12349, 12360),
                new ZipCodeRange(13310, 13350)
        };

        out.printf("In testMergeValid2 with input %s\n", Arrays.toString(zipCodes));

        ZipCodeRange[] zipCodesExpected = { new ZipCodeRange(12330, 12360),
                new ZipCodeRange(13310, 13350)
        };

        ZipCodeRange[] result = zipCodeHelper.merge(zipCodes);
        out.printf("Result is %s\n", Arrays.toString(result));
        Assert.assertTrue(areEqual(zipCodesExpected, result), "Merged result Invalid.");
    }

    private boolean areEqual(ZipCodeRange[] zipCodes1, ZipCodeRange[] zipCodes2)
    {
        Arrays.sort(zipCodes1);
        Arrays.sort(zipCodes2);

        if (zipCodes1 == null && zipCodes2 == null) {
            out.printf("Testing Ranges equality, zipCode1 is null and zipCode2 is null, returning true.");
            return true;
        }

        if (zipCodes1 == null && zipCodes2 != null) {
            out.printf("Testing Ranges equality, zipCode1 is null and zipCode2 is not null, returning false.");
            return false;
        }

        if (zipCodes1 != null && zipCodes2 == null) {
            out.printf("Testing Ranges equality, zipCode1 is not null and zipCode2 is null, returning false.");
            return false;
        }

        if (zipCodes1.length != zipCodes2.length) {
            out.printf("Testing Ranges equality, zipCode1 length didn't match with zipCode2 length, returning false.");
            return false;
        }

        for (int counter = 0; counter < zipCodes1.length; counter++) {
            if (!zipCodes1[counter].equals(zipCodes2[counter])) {
                out.printf("Testing Ranges equality, found a mismatch with zipCode1 and zipCode2, returning false.");
                return false;
            }
        }

        out.printf("Testing Ranges equality, each range of zipCode1 matched with zipCode2 true.");
        return true;
    }
}