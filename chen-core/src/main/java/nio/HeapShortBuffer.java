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


import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.HeapShortBufferR;
import java.nio.ShortBuffer;

/**

 * A read/write HeapShortBuffer.






 */

class HeapShortBuffer
    extends java.nio.ShortBuffer
{

    // For speed these fields are actually declared in X-Buffer;
    // these declarations are here as documentation
    /*

    protected final short[] hb;
    protected final int offset;

    */

    HeapShortBuffer(int cap, int lim) {            // package-private

        super(-1, 0, lim, cap, new short[cap], 0);
        /*
        hb = new short[cap];
        offset = 0;
        */




    }

    HeapShortBuffer(short[] buf, int off, int len) { // package-private

        super(-1, off, off + len, buf.length, buf, 0);
        /*
        hb = buf;
        offset = 0;
        */




    }

    protected HeapShortBuffer(short[] buf,
                                   int mark, int pos, int lim, int cap,
                                   int off)
    {

        super(mark, pos, lim, cap, buf, off);
        /*
        hb = buf;
        offset = off;
        */




    }

    public java.nio.ShortBuffer slice() {
        return new HeapShortBuffer(hb,
                                        -1,
                                        0,
                                        this.remaining(),
                                        this.remaining(),
                                        this.position() + offset);
    }

    public java.nio.ShortBuffer duplicate() {
        return new HeapShortBuffer(hb,
                                        this.markValue(),
                                        this.position(),
                                        this.limit(),
                                        this.capacity(),
                                        offset);
    }

    public java.nio.ShortBuffer asReadOnlyBuffer() {

        return new HeapShortBufferR(hb,
                                     this.markValue(),
                                     this.position(),
                                     this.limit(),
                                     this.capacity(),
                                     offset);



    }



    protected int ix(int i) {
        return i + offset;
    }

    public short get() {
        return hb[ix(nextGetIndex())];
    }

    public short get(int i) {
        return hb[ix(checkIndex(i))];
    }







    public java.nio.ShortBuffer get(short[] dst, int offset, int length) {
        checkBounds(offset, length, dst.length);
        if (length > remaining())
            throw new BufferUnderflowException();
        System.arraycopy(hb, ix(position()), dst, offset, length);
        position(position() + length);
        return this;
    }

    public boolean isDirect() {
        return false;
    }



    public boolean isReadOnly() {
        return false;
    }

    public java.nio.ShortBuffer put(short x) {

        hb[ix(nextPutIndex())] = x;
        return this;



    }

    public java.nio.ShortBuffer put(int i, short x) {

        hb[ix(checkIndex(i))] = x;
        return this;



    }

    public java.nio.ShortBuffer put(short[] src, int offset, int length) {

        checkBounds(offset, length, src.length);
        if (length > remaining())
            throw new BufferOverflowException();
        System.arraycopy(src, offset, hb, ix(position()), length);
        position(position() + length);
        return this;



    }

    public java.nio.ShortBuffer put(java.nio.ShortBuffer src) {

        if (src instanceof HeapShortBuffer) {
            if (src == this)
                throw new IllegalArgumentException();
            HeapShortBuffer sb = (HeapShortBuffer)src;
            int n = sb.remaining();
            if (n > remaining())
                throw new BufferOverflowException();
            System.arraycopy(sb.hb, sb.ix(sb.position()),
                             hb, ix(position()), n);
            sb.position(sb.position() + n);
            position(position() + n);
        } else if (src.isDirect()) {
            int n = src.remaining();
            if (n > remaining())
                throw new BufferOverflowException();
            src.get(hb, ix(position()), n);
            position(position() + n);
        } else {
            super.put(src);
        }
        return this;



    }

    public ShortBuffer compact() {

        System.arraycopy(hb, ix(position()), hb, ix(0), remaining());
        position(remaining());
        limit(capacity());
        discardMark();
        return this;



    }






































































































































































































































































































































































    public java.nio.ByteOrder order() {
        return ByteOrder.nativeOrder();
    }



}
