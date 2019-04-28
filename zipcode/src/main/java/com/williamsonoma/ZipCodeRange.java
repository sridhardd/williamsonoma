package com.williamsonoma;
//This class holds valid zip code ranges Start and End Values.
import static java.lang.System.out;

public class ZipCodeRange implements Comparable<ZipCodeRange> {
    private int start;
    private int end;

    public ZipCodeRange(int low, int high)
    {
        if (low > high)
        {
            out.printf("Invalid range %d to %d\n", low, high);
            throw new IllegalArgumentException("Start should be less than or equal to End in Zip Code Range.");
        }

        //We can also validate zip codes to be 5 digits, but I am not sure if there are zip codes less than 5 characters.

        start = low;
        end = high;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    //To help sort the values
    public int compareTo(ZipCodeRange that) {
        if (this.start == that.start) //If starts are same, then we compare ends.
            return Integer.compare(this.end, that.end);

        return Integer.compare(this.start, that.start);
    }

    @Override
    public String toString()
    {
        return "[" + this.start + "," + this.end + "]";
    }

    //Comparing all properties to decide equality.
    public boolean equals(ZipCodeRange that)
    {
        return this.start == that.start && this.end == that.end;
    }
}
