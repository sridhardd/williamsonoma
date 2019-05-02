package com.williamsonoma;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        zipCodeRange[] zipCodes = {new zipCodeRange(94133,94133),
//                new zipCodeRange(94200,94299),
//                new zipCodeRange(94600,94699)};

        ZipCodeRange[] zipCodes = { new ZipCodeRange(94133,94133),
                new ZipCodeRange(94140,94158),
                new ZipCodeRange(94200,94299),
                new ZipCodeRange(94101,94100),
                new ZipCodeRange(94226,94399)};
        ZipCodeHelper helper = new ZipCodeHelper();
        List<ZipCodeRange> result = helper.merge(zipCodes);

        out.printf("Input: %s\n", Arrays.toString(zipCodes));
        out.printf("Output: %s\n", result.toString());
    }
}
