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
import java.nio.ByteBufferAsIntBufferRL;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

class ByteBufferAsIntBufferL                  // package-private
    extends java.nio.IntBuffer
{



    protected final java.nio.ByteBuffer bb;
    protected final int offset;



    ByteBufferAsIntBufferL(java.nio.ByteBuffer bb) {   // package-private

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

    ByteBufferAsIntBufferL(java.nio.ByteBuffer bb,
                           int mark, int pos, int lim, int cap,
                           int off)
    {

        super(mark, pos, lim, cap);
        this.bb = bb;
        offset = off;



    }

    public java.nio.IntBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 2) + offset;
        assert (off >= 0);
        return new ByteBufferAsIntBufferL(bb, -1, 0, rem, rem, off);
    }

    public java.nio.IntBuffer duplicate() {
        return new ByteBufferAsIntBufferL(bb,
                                                    this.markValue(),
                                                    this.position(),
                                                    this.limit(),
                                                    this.capacity(),
                                                    offset);
    }

    public java.nio.IntBuffer asReadOnlyBuffer() {

        return new ByteBufferAsIntBufferRL(bb,
                                                 this.markValue(),
                                                 this.position(),
                                                 this.limit(),
                                                 this.capacity(),
                                                 offset);



    }



    protected int ix(int i) {
        return (i << 2) + offset;
    }

    public int get() {
        return java.nio.Bits.getIntL(bb, ix(nextGetIndex()));
    }

    public int get(int i) {
        return java.nio.Bits.getIntL(bb, ix(checkIndex(i)));
    }









    public java.nio.IntBuffer put(int x) {

        java.nio.Bits.putIntL(bb, ix(nextPutIndex()), x);
        return this;



    }

    public java.nio.IntBuffer put(int i, int x) {

        java.nio.Bits.putIntL(bb, ix(checkIndex(i)), x);
        return this;



    }

    public IntBuffer compact() {

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
