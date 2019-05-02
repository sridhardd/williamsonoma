package com.williamsonoma;

import org.apache.log4j.Logger;
import org.testng.Assert;
import java.util.Arrays;
import java.util.List;

public class ZipCodeHelperTest {
    /**
     * Instance of Logger object.
     */
    final static Logger logger = Logger.getLogger(ZipCodeHelperTest.class);

    @org.testng.annotations.Test
    public void testMergeNull() {
        logger.info("In testMergeNull with NULL object.");
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        List<ZipCodeRange> result = zipCodeHelper.merge(null);
        Assert.assertNull(result, "Merged result of a Null object should be Null.");
    }

    @org.testng.annotations.Test
    public void testMergeEmpty() {
        logger.info("In testMergeEmpty with Empty array as input.");
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = {};
        List<ZipCodeRange> result = zipCodeHelper.merge(zipCodes);
        Assert.assertNull(result, "Merged result of an empty range should be empty.");
    }

    @org.testng.annotations.Test(expectedExceptions = IllegalArgumentException.class)
    public void testMergeInvalidRange() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = {new ZipCodeRange(12345, 34567), new ZipCodeRange(54321, 12345)};
        logger.info("In testMergeInvalidRange with input " + Arrays.toString(zipCodes));
        List<ZipCodeRange> result = zipCodeHelper.merge(zipCodes);
        logger.info("Result is " + result.toString());
    }

    @org.testng.annotations.Test
    public void testMergeOne() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = { new ZipCodeRange(12345, 12345) };
        logger.info("In testMergeOne with input "+ Arrays.toString(zipCodes));
        List<ZipCodeRange> result = zipCodeHelper.merge(zipCodes);
        logger.info("Result is " + result.toString());
        Assert.assertEquals(zipCodes, result.toArray(), "Merged result of a single range should be same.");
    }

    @org.testng.annotations.Test
    public void testMergeDistinct() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = { new ZipCodeRange(12345, 12350),
                new ZipCodeRange(12360, 12390),
                new ZipCodeRange(13310, 13350)
        };
        logger.info("In testMergeDistinct with input " + Arrays.toString(zipCodes));
        List<ZipCodeRange> result = zipCodeHelper.merge(zipCodes);
        logger.info("Result is " + result.toString());
        Assert.assertEquals(zipCodes, result.toArray(), "Merged result should be same as input.");
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

        logger.info("In testMergeValid with input " + Arrays.toString(zipCodes));
        List<ZipCodeRange> result = zipCodeHelper.merge(zipCodes);
        logger.info("Result is " + result.toString());
        Assert.assertEquals(result.toArray(), zipCodesExpected, "Merge result invalid.");
    }

    @org.testng.annotations.Test
    public void testMergeValid2() {
        ZipCodeHelper zipCodeHelper = new ZipCodeHelper();
        ZipCodeRange[] zipCodes = { new ZipCodeRange(12330, 12350),
                new ZipCodeRange(12349, 12360),
                new ZipCodeRange(13310, 13350)
        };

        logger.info("In testMergeValid2 with input " + Arrays.toString(zipCodes));

        ZipCodeRange[] zipCodesExpected = { new ZipCodeRange(12330, 12360),
                new ZipCodeRange(13310, 13350)
        };

        List<ZipCodeRange> result = zipCodeHelper.merge(zipCodes);
        logger.info("Result is " + result.toString());
        Assert.assertEquals(zipCodesExpected, result.toArray(), "Merged result Invalid.");
    }
}