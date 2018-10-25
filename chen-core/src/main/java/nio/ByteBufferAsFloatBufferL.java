/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

// -- This file was mechanically generated: Do not edit! -- //

package nio;


import java.nio.Bits;
import java.nio.ByteBuffer;
import java.nio.ByteBufferAsFloatBufferRL;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

class ByteBufferAsFloatBufferL                  // package-private
    extends java.nio.FloatBuffer
{



    protected final java.nio.ByteBuffer bb;
    protected final int offset;



    ByteBufferAsFloatBufferL(java.nio.ByteBuffer bb) {   // package-private

        super(-1, 0,
              bb.remaining() >> 2,
              bb.remaining() >> 2);
        this.bb = bb;
        // enforce limit == capacity
        int cap = this.capacity();
        this.limit(cap);
        int pos = this.position();
        assert (pos <= cap);
        offset = pos;



    }

    ByteBufferAsFloatBufferL(java.nio.ByteBuffer bb,
                             int mark, int pos, int lim, int cap,
                             int off)
    {

        super(mark, pos, lim, cap);
        this.bb = bb;
        offset = off;



    }

    public java.nio.FloatBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 2) + offset;
        assert (off >= 0);
        return new ByteBufferAsFloatBufferL(bb, -1, 0, rem, rem, off);
    }

    public java.nio.FloatBuffer duplicate() {
        return new ByteBufferAsFloatBufferL(bb,
                                                    this.markValue(),
                                                    this.position(),
                                                    this.limit(),
                                                    this.capacity(),
                                                    offset);
    }

    public java.nio.FloatBuffer asReadOnlyBuffer() {

        return new ByteBufferAsFloatBufferRL(bb,
                                                 this.markValue(),
                                                 this.position(),
                                                 this.limit(),
                                                 this.capacity(),
                                                 offset);



    }



    protected int ix(int i) {
        return (i << 2) + offset;
    }

    public float get() {
        return java.nio.Bits.getFloatL(bb, ix(nextGetIndex()));
    }

    public float get(int i) {
        return java.nio.Bits.getFloatL(bb, ix(checkIndex(i)));
    }









    public java.nio.FloatBuffer put(float x) {

        java.nio.Bits.putFloatL(bb, ix(nextPutIndex()), x);
        return this;



    }

    public java.nio.FloatBuffer put(int i, float x) {

        java.nio.Bits.putFloatL(bb, ix(checkIndex(i)), x);
        return this;



    }

    public FloatBuffer compact() {

        int pos = position();
        int lim = limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);

        java.nio.ByteBuffer db = bb.duplicate();
        db.limit(ix(lim));
        db.position(ix(0));
        ByteBuffer sb = db.slice();
        sb.position(pos << 2);
        sb.compact();
        position(rem);
        limit(capacity());
        discardMark();
        return this;



    }

    public boolean isDirect() {
        return bb.isDirect();
    }

    public boolean isReadOnly() {
        return false;
    }











































    public java.nio.ByteOrder order() {




        return ByteOrder.LITTLE_ENDIAN;

    }

}
