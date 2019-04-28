package org.firebears.util;

public class Statistic {

    public final int MAX;
    private long[] data;
    private int count = 0;

    public Statistic() {
        this(100);
    }

    public Statistic(int m) {
        MAX = m;
        data = new long[MAX];
    }

    public void add(long j) {
        if (j < MAX) {
            data[count++] = j;
        }
    }

    public void clear() {
        count = 0;
    }

    public String toString() {
        long min=Long.MAX_VALUE;
        long max=Long.MIN_VALUE;
        long sum = 0;
        for (int i=0; i<count; i++) {
            if (data[i] < min) min = data[i];
            if (data[i] > max) max = data[i];
            sum += data[i];
        }
        long avg = (count > 0) ? sum / count : 0;
        StringBuilder sb = new StringBuilder();
        sb.append(count).append(',');
        sb.append(avg).append(',');
        sb.append(min).append(',');
        sb.append(max).append(',');
        for (int i=0; i<count; i++) { 
            sb.append(data[i]).append(',');
        }
        return sb.toString();
    }

    public int size() {
        return count;
    }

}
