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
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.HeapByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**



 * A read-only HeapByteBuffer.  This class extends the corresponding
 * read/write class, overriding the mutation methods to throw a {@link
 * ReadOnlyBufferException} and overriding the view-buffer methods to return an
 * instance of this class rather than of the superclass.

 */

class HeapByteBufferR
    extends java.nio.HeapByteBuffer
{

    // For speed these fields are actually declared in X-Buffer;
    // these declarations are here as documentation
    /*




    */

    HeapByteBufferR(int cap, int lim) {            // package-private







        super(cap, lim);
        this.isReadOnly = true;

    }

    HeapByteBufferR(byte[] buf, int off, int len) { // package-private







        super(buf, off, len);
        this.isReadOnly = true;

    }

    protected HeapByteBufferR(byte[] buf,
                                   int mark, int pos, int lim, int cap,
                                   int off)
    {







        super(buf, mark, pos, lim, cap, off);
        this.isReadOnly = true;

    }

    public java.nio.ByteBuffer slice() {
        return new HeapByteBufferR(hb,
                                        -1,
                                        0,
                                        this.remaining(),
                                        this.remaining(),
                                        this.position() + offset);
    }

    public java.nio.ByteBuffer duplicate() {
        return new HeapByteBufferR(hb,
                                        this.markValue(),
                                        this.position(),
                                        this.limit(),
                                        this.capacity(),
                                        offset);
    }

    public java.nio.ByteBuffer asReadOnlyBuffer() {








        return duplicate();

    }




































    public boolean isReadOnly() {
        return true;
    }

    public java.nio.ByteBuffer put(byte x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer put(int i, byte x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer put(byte[] src, int offset, int length) {








        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer put(java.nio.ByteBuffer src) {























        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer compact() {







        throw new ReadOnlyBufferException();

    }





    byte _get(int i) {                          // package-private
        return hb[i];
    }

    void _put(int i, byte b) {                  // package-private



        throw new ReadOnlyBufferException();

    }

    // char













    public java.nio.ByteBuffer putChar(char x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putChar(int i, char x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.CharBuffer asCharBuffer() {
        int size = this.remaining() >> 1;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.CharBuffer)(new ByteBufferAsCharBufferRB(this,
                                                               -1,
                                                               0,
                                                               size,
                                                               size,
                                                               off))
                : (CharBuffer)(new ByteBufferAsCharBufferRL(this,
                                                               -1,
                                                               0,
                                                               size,
                                                               size,
                                                               off)));
    }


    // short













    public java.nio.ByteBuffer putShort(short x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putShort(int i, short x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ShortBuffer asShortBuffer() {
        int size = this.remaining() >> 1;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.ShortBuffer)(new ByteBufferAsShortBufferRB(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                : (ShortBuffer)(new ByteBufferAsShortBufferRL(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
    }


    // int













    public java.nio.ByteBuffer putInt(int x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putInt(int i, int x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.IntBuffer asIntBuffer() {
        int size = this.remaining() >> 2;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.IntBuffer)(new ByteBufferAsIntBufferRB(this,
                                                             -1,
                                                             0,
                                                             size,
                                                             size,
                                                             off))
                : (IntBuffer)(new ByteBufferAsIntBufferRL(this,
                                                             -1,
                                                             0,
                                                             size,
                                                             size,
                                                             off)));
    }


    // long













    public java.nio.ByteBuffer putLong(long x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putLong(int i, long x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.LongBuffer asLongBuffer() {
        int size = this.remaining() >> 3;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.LongBuffer)(new ByteBufferAsLongBufferRB(this,
                                                               -1,
                                                               0,
                                                               size,
                                                               size,
                                                               off))
                : (LongBuffer)(new ByteBufferAsLongBufferRL(this,
                                                               -1,
                                                               0,
                                                               size,
                                                               size,
                                                               off)));
    }


    // float













    public java.nio.ByteBuffer putFloat(float x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putFloat(int i, float x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.FloatBuffer asFloatBuffer() {
        int size = this.remaining() >> 2;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.FloatBuffer)(new ByteBufferAsFloatBufferRB(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                : (FloatBuffer)(new ByteBufferAsFloatBufferRL(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
    }


    // double













    public java.nio.ByteBuffer putDouble(double x) {




        throw new ReadOnlyBufferException();

    }

    public ByteBuffer putDouble(int i, double x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.DoubleBuffer asDoubleBuffer() {
        int size = this.remaining() >> 3;
        int off = offset + position();
        return (bigEndian
                ? (java.nio.DoubleBuffer)(new ByteBufferAsDoubleBufferRB(this,
                                                                   -1,
                                                                   0,
                                                                   size,
                                                                   size,
                                                                   off))
                : (DoubleBuffer)(new ByteBufferAsDoubleBufferRL(this,
                                                                   -1,
                                                                   0,
                                                                   size,
                                                                   size,
                                                                   off)));
    }











































}
