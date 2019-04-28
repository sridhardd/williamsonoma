package com.williamsonoma;

import java.util.*;
import static java.lang.System.out;

public class ZipCodeHelper {
    //Merges zip code ranges,
    //      Case 1: if the current range falls in existing range, it will be omitted, e.g. {1,4}, {2,3} results in {1,4}
    //      Case 2: if it overlaps, then start and end will be adjusted accordingly, e.g. {1,3}, {2,5} results in {1,5}
    public ZipCodeRange[] merge(ZipCodeRange[] zipCodes)
    {
        List<ZipCodeRange> result = new ArrayList<>(); //Don't know the size of result so considered list instead of array.

        if (zipCodes == null || zipCodes.length < 2) {
            out.println("zipCodes is null or its length is less than 2, Merge not required.");
            return zipCodes;
        }

        Arrays.sort(zipCodes);
        result.add(zipCodes[0]);

        for (int counter = 1; counter < zipCodes.length; counter++) {
            ZipCodeRange last = result.get(result.size()-1);

            if (last.getEnd() < zipCodes[counter].getStart())
            {
                out.printf("New range %s found, so adding to the result.\n", zipCodes[counter].toString());
                result.add(zipCodes[counter]);
            }
            else if (last.getEnd() < zipCodes[counter].getEnd()) //Case 2
            {
                out.printf("Overlap range %s found, so merging to last one in the result.\n", zipCodes[counter].toString());
                result.get(result.size()-1).setEnd(zipCodes[counter].getEnd());
            }
            else
            {
                out.printf("%s in previous range, so ignoring\n", zipCodes[counter].toString());
                //Ignoring Case 1
            }
        }

        Collections.sort(result);

        return result.toArray(new ZipCodeRange[result.size()]);
    }
}
