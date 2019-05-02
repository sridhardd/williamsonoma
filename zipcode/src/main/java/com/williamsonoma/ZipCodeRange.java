package com.williamsonoma;

import org.apache.log4j.Logger;

/**
 * Business Object (Entity) class to hold Zip Code Range
 *
 * @author Sridhar Dhavala
 *
 */
public class ZipCodeRange implements Comparable<ZipCodeRange> {
    /**
     * Instance of Logger object.
     */
    final static Logger logger = Logger.getLogger(ZipCodeRange.class);

    private int start;
    private int end;

    /*
     * <p>Constructor method to create an object of ZipCode Range</p>
     * @param low Represents the start of ZipCode Range
     * @param high Represents the end of ZipCode Range
     * @return Instance of ZipCode Range if Valid range (1001 to 99950 inclusive) is provided otherwise throws IllegalArgumentException.
     */
    public ZipCodeRange(int low, int high)
    {
        if (low > high || low < 1001 || high > 99950) //Source http://www.aip2.com/zip2.htm
        {
            logger.info("Invalid range " + low + " to " + high);
            throw new IllegalArgumentException("Start should be less than or equal to End in Zip Code Range and " +
                    "should be a valid US Zip Code.");
        }

        start = low;
        end = high;
    }

    /**
     * <p>Getter method for start property in ZipCodeRange</p>
     * @return value of start in ZipCode Range.
     */
    public int getStart() {
        return start;
    }

    /**
     * <p>Getter method for end property in ZipCodeRange</p>
     * @return value of end in ZipCode Range.
     */
    public int getEnd() {
        return end;
    }

    /**
     * <p>Setter method for start property in ZipCodeRange</p>
     * @param start value of start in ZipCodeRange
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * <p>Setter method for end property in ZipCodeRange</p>
     * @param end value of end in ZipCodeRange
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * <p>Implementation for compareTo method in Comparable Interface to compare one object of ZipCodeRange to another
     * @param that Instance of ZipCodeRange to compare with current instance.
     * @return Returns -1 if the new ZipCodeRange is smaller than current one, 1 if greater and 0 if equal.
     */
    @Override
    public int compareTo(ZipCodeRange that) {
        if (this.start == that.start) //If starts are same, then we compare ends.
            return Integer.compare(this.end, that.end);

        return Integer.compare(this.start, that.start);
    }

    /**
     * <p>Overridden method of toString in Object class</p>
     * @return Customized String representation of ZipCodeRange
     */
    @Override
    public String toString()
    {
        return "[" + this.start + "," + this.end + "]";
    }

    /**
     * <p>Overridden method of equals in Object class</p>
     * @param that new Object to compare with current object.
     * @return true if the ranges are same otherwise returns false.
     */
    @Override
    public boolean equals(Object that)
    {
        if (!(that instanceof  ZipCodeRange))
            return false;

        ZipCodeRange objZipCodeRange = (ZipCodeRange)that;
        return this.start == objZipCodeRange.start && this.end == objZipCodeRange.end;
    }
}
