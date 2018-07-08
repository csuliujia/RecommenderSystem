package com.dtdream.geely.tuijian;

import com.aliyun.odps.io.DoubleWritable;
import com.aliyun.odps.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class CFEdgeValue implements Writable {
    private DoubleWritable interest_rate = new DoubleWritable();
    private DoubleWritable length = new DoubleWritable();

    public CFEdgeValue() {
    }

    public CFEdgeValue(double interest_rate, double length) {
        this.interest_rate.set(interest_rate);
        this.length.set(length);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        interest_rate.write(dataOutput);
        length.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        interest_rate.readFields(dataInput);
        length.readFields(dataInput);
    }

    public DoubleWritable getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(DoubleWritable interest_rate) {
        this.interest_rate = interest_rate;
    }

    public DoubleWritable getLength() {
        return length;
    }

    public void setLength(DoubleWritable length) {
        this.length = length;
    }
}
