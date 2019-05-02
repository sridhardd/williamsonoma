package com.williamsonoma;

import java.util.*;
import org.apache.log4j.Logger;

/**
 * Main class responsible for Merging Zip Code ranges.
 *
 * @author Sridhar Dhavala
 *
 */

public class ZipCodeHelper {
    /**
     * Instance of Logger object.
     */
    final static Logger logger = Logger.getLogger(ZipCodeHelper.class);
    /*
     * <p>This method merges an array of zip code ranges to form individual ranges.</p>
     * @param zipCodes Individual ranges of zip coces.
     * @return The merged zip code ranges by removing the overlaps and also merges continuous ranges to form a single range.
     * Rules:
     * 1: If the current range falls in existing range, it will be omitted, e.g. {1,4}, {2,3} results in {1,4}
     * 2: If it overlaps, then start and end will be adjusted accordingly, e.g. {1,3}, {2,5} results in {1,5}
     * 3: If there's a common range, the super set will be considered and current range will be ignored. e.g. {10,80},
     * {15,25} results in {10,80}
     */
    public List<ZipCodeRange> merge(ZipCodeRange[] zipCodes)
    {
        /**
         * Variable to store the result of the zip code ranges after merging.
         */
        List<ZipCodeRange> result;

        if (zipCodes == null || zipCodes.length ==0)
        {
            logger.info("zipCodes is null or empty.");
            return null;
        }

        result = new ArrayList<>();

        if (zipCodes.length == 1)
        {
            logger.info("Only one zip code range provided, nothing to merge");
            result.add(zipCodes[0]);
            return result;
        }

        result.add(zipCodes[0]);

        for (int counter = 1; counter < zipCodes.length; counter++) {
            ZipCodeRange last = result.get(result.size()-1);

            if (last.getEnd() < zipCodes[counter].getStart())
            {
                logger.debug("New range "+ zipCodes[counter].toString() + " found, so adding to the result.");
                result.add(zipCodes[counter]);
            }
            else if (last.getEnd() < zipCodes[counter].getEnd()) //Case 2
            {
                logger.debug("Overlap range " + zipCodes[counter].toString() + " found, so merging to last one in the result.");
                result.get(result.size()-1).setEnd(zipCodes[counter].getEnd());
            }
            else
            {
                logger.debug(zipCodes[counter].toString() + " in previous range, so ignoring.");
            }
        }

        Collections.sort(result);
        return result;
    }
}
