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


import java.nio.*;
import java.nio.Bits;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**

 * A read/write HeapByteBuffer.






 */

class HeapByteBuffer
    extends java.nio.ByteBuffer
{

    // For speed these fields are actually declared in X-Buffer;
    // these declarations are here as documentation
    /*

    protected final byte[] hb;
    protected final int offset;

    */

    HeapByteBuffer(int cap, int lim) {            // package-private

        super(-1, 0, lim, cap, new byte[cap], 0);
        /*
        hb = new byte[cap];
        offset = 0;
        */




    }

    HeapByteBuffer(byte[] buf, int off, int len) { // package-private

        super(-1, off, off + len, buf.length, buf, 0);
        /*
        hb = buf;
        offset = 0;
        */




    }

    protected HeapByteBuffer(byte[] buf,
                                   int mark, int pos, int lim, int cap,
                                   int off)
    {

        super(mark, pos, lim, cap, buf, off);
        /*
        hb = buf;
        offset = off;
        */




    }

    public java.nio.ByteBuffer slice() {
        return new HeapByteBuffer(hb,
                                        -1,
                                        0,
                                        this.remaining(),
                                        this.remaining(),
                                        this.position() + offset);
    }

    public java.nio.ByteBuffer duplicate() {
        return new HeapByteBuffer(hb,
                                        this.markValue(),
                                        this.position(),
                                        this.limit(),
                                        this.capacity(),
                                        offset);
    }

    public java.nio.ByteBuffer asReadOnlyBuffer() {

        return new HeapByteBufferR(hb,
                                     this.markValue(),
                                     this.position(),
                                     this.limit(),
                                     this.capacity(),
                                     offset);



    }



    protected int ix(int i) {
        return i + offset;
    }

    public byte get() {
        return hb[ix(nextGetIndex())];
    }

    public byte get(int i) {
        return hb[ix(checkIndex(i))];
    }







    public java.nio.ByteBuffer get(byte[] dst, int offset, int length) {
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

    public java.nio.ByteBuffer put(byte x) {

        hb[ix(nextPutIndex())] = x;
        return this;



    }

    public java.nio.ByteBuffer put(int i, byte x) {

        hb[ix(checkIndex(i))] = x;
        return this;



    }

    public java.nio.ByteBuffer put(byte[] src, int offset, int length) {

        checkBounds(offset, length, src.length);
        if (length > remaining())
            throw new BufferOverflowException();
        System.arraycopy(src, offset, hb, ix(position()), length);
        position(position() + length);
        return this;



    }

    public java.nio.ByteBuffer put(java.nio.ByteBuffer src) {

        if (src instanceof HeapByteBuffer) {
            if (src == this)
                throw new IllegalArgumentException();
            HeapByteBuffer sb = (HeapByteBuffer)src;
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

    public java.nio.ByteBuffer compact() {

        System.arraycopy(hb, ix(position()), hb, ix(0), remaining());
        position(remaining());
        limit(capacity());
        discardMark();
        return this;



    }





    byte _get(int i) {                          // package-private
        return hb[i];
    }

    void _put(int i, byte b) {                  // package-private

        hb[i] = b;



    }

    // char



    public char getChar() {
        return java.nio.Bits.getChar(this, ix(nextGetIndex(2)), bigEndian);
    }

    public char getChar(int i) {
        return java.nio.Bits.getChar(this, ix(checkIndex(i, 2)), bigEndian);
    }



    public java.nio.ByteBuffer putChar(char x) {

        java.nio.Bits.putChar(this, ix(nextPutIndex(2)), x, bigEndian);
        return this;



    }

    public java.nio.ByteBuffer putChar(int i, char x) {

        java.nio.Bits.putChar(this, ix(checkIndex(i, 2)), x, bigEndian);
        return this;



    }

    public java.nio.CharBuffer asCharBuffer() {
        int size = this.remaining() >> 1;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.CharBuffer)(new ByteBufferAsCharBufferB(this,
                                                               -1,
                                                               0,
                                                               size,
                                                               size,
                                                               off))
                : (CharBuffer)(new ByteBufferAsCharBufferL(this,
                                                               -1,
                                                               0,
                                                               size,
                                                               size,
                                                               off)));
    }


    // short



    public short getShort() {
        return java.nio.Bits.getShort(this, ix(nextGetIndex(2)), bigEndian);
    }

    public short getShort(int i) {
        return java.nio.Bits.getShort(this, ix(checkIndex(i, 2)), bigEndian);
    }



    public java.nio.ByteBuffer putShort(short x) {

        java.nio.Bits.putShort(this, ix(nextPutIndex(2)), x, bigEndian);
        return this;



    }

    public java.nio.ByteBuffer putShort(int i, short x) {

        java.nio.Bits.putShort(this, ix(checkIndex(i, 2)), x, bigEndian);
        return this;



    }

    public java.nio.ShortBuffer asShortBuffer() {
        int size = this.remaining() >> 1;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.ShortBuffer)(new ByteBufferAsShortBufferB(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                : (ShortBuffer)(new ByteBufferAsShortBufferL(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
    }


    // int



    public int getInt() {
        return java.nio.Bits.getInt(this, ix(nextGetIndex(4)), bigEndian);
    }

    public int getInt(int i) {
        return java.nio.Bits.getInt(this, ix(checkIndex(i, 4)), bigEndian);
    }



    public java.nio.ByteBuffer putInt(int x) {

        java.nio.Bits.putInt(this, ix(nextPutIndex(4)), x, bigEndian);
        return this;



    }

    public java.nio.ByteBuffer putInt(int i, int x) {

        java.nio.Bits.putInt(this, ix(checkIndex(i, 4)), x, bigEndian);
        return this;



    }

    public java.nio.IntBuffer asIntBuffer() {
        int size = this.remaining() >> 2;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.IntBuffer)(new ByteBufferAsIntBufferB(this,
                                                             -1,
                                                             0,
                                                             size,
                                                             size,
                                                             off))
                : (IntBuffer)(new ByteBufferAsIntBufferL(this,
                                                             -1,
                                                             0,
                                                             size,
                                                             size,
                                                             off)));
    }


    // long



    public long getLong() {
        return java.nio.Bits.getLong(this, ix(nextGetIndex(8)), bigEndian);
    }

    public long getLong(int i) {
        return java.nio.Bits.getLong(this, ix(checkIndex(i, 8)), bigEndian);
    }



    public java.nio.ByteBuffer putLong(long x) {

        java.nio.Bits.putLong(this, ix(nextPutIndex(8)), x, bigEndian);
        return this;



    }

    public java.nio.ByteBuffer putLong(int i, long x) {

        java.nio.Bits.putLong(this, ix(checkIndex(i, 8)), x, bigEndian);
        return this;



    }

    public java.nio.LongBuffer asLongBuffer() {
        int size = this.remaining() >> 3;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.LongBuffer)(new ByteBufferAsLongBufferB(this,
                                                               -1,
                                                               0,
                                                               size,
                                                               size,
                                                               off))
                : (LongBuffer)(new ByteBufferAsLongBufferL(this,
                                                               -1,
                                                               0,
                                                               size,
                                                               size,
                                                               off)));
    }


    // float



    public float getFloat() {
        return java.nio.Bits.getFloat(this, ix(nextGetIndex(4)), bigEndian);
    }

    public float getFloat(int i) {
        return java.nio.Bits.getFloat(this, ix(checkIndex(i, 4)), bigEndian);
    }



    public java.nio.ByteBuffer putFloat(float x) {

        java.nio.Bits.putFloat(this, ix(nextPutIndex(4)), x, bigEndian);
        return this;



    }

    public java.nio.ByteBuffer putFloat(int i, float x) {

        java.nio.Bits.putFloat(this, ix(checkIndex(i, 4)), x, bigEndian);
        return this;



    }

    public java.nio.FloatBuffer asFloatBuffer() {
        int size = this.remaining() >> 2;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.FloatBuffer)(new ByteBufferAsFloatBufferB(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                : (FloatBuffer)(new ByteBufferAsFloatBufferL(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
    }


    // double



    public double getDouble() {
        return java.nio.Bits.getDouble(this, ix(nextGetIndex(8)), bigEndian);
    }

    public double getDouble(int i) {
        return java.nio.Bits.getDouble(this, ix(checkIndex(i, 8)), bigEndian);
    }



    public java.nio.ByteBuffer putDouble(double x) {

        java.nio.Bits.putDouble(this, ix(nextPutIndex(8)), x, bigEndian);
        return this;



    }

    public ByteBuffer putDouble(int i, double x) {

        java.nio.Bits.putDouble(this, ix(checkIndex(i, 8)), x, bigEndian);
        return this;



    }

    public java.nio.DoubleBuffer asDoubleBuffer() {
        int size = this.remaining() >> 3;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.DoubleBuffer)(new ByteBufferAsDoubleBufferB(this,
                                                                   -1,
                                                                   0,
                                                                   size,
                                                                   size,
                                                                   off))
                : (DoubleBuffer)(new ByteBufferAsDoubleBufferL(this,
                                                                   -1,
                                                                   0,
                                                                   size,
                                                                   size,
                                                                   off)));
    }











































}
