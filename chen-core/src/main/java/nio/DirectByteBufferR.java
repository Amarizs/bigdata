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

import java.io.FileDescriptor;
import java.nio.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DirectByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.misc.VM;
import sun.nio.ch.DirectBuffer;


class DirectByteBufferR



    extends java.nio.DirectByteBuffer

    implements DirectBuffer
{






































































    // Primary constructor
    //
    DirectByteBufferR(int cap) {                   // package-private
























        super(cap);

    }

























    // For memory-mapped buffers -- invoked by FileChannelImpl via reflection
    //
    protected DirectByteBufferR(int cap, long addr,
                                     FileDescriptor fd,
                                     Runnable unmapper)
    {






        super(cap, addr, fd, unmapper);

    }



    // For duplicates and slices
    //
    DirectByteBufferR(DirectBuffer db,         // package-private
                               int mark, int pos, int lim, int cap,
                               int off)
    {








        super(db, mark, pos, lim, cap, off);

    }

    public java.nio.ByteBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 0);
        assert (off >= 0);
        return new DirectByteBufferR(this, -1, 0, rem, rem, off);
    }

    public java.nio.ByteBuffer duplicate() {
        return new DirectByteBufferR(this,
                                              this.markValue(),
                                              this.position(),
                                              this.limit(),
                                              this.capacity(),
                                              0);
    }

    public java.nio.ByteBuffer asReadOnlyBuffer() {








        return duplicate();

    }


























































    public java.nio.ByteBuffer put(byte x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer put(int i, byte x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer put(java.nio.ByteBuffer src) {




































        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer put(byte[] src, int offset, int length) {




























        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer compact() {












        throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
        return true;
    }

    public boolean isReadOnly() {
        return true;
    }
































































    byte _get(int i) {                          // package-private
        return unsafe.getByte(address + i);
    }

    void _put(int i, byte b) {                  // package-private



        throw new ReadOnlyBufferException();

    }






















    private java.nio.ByteBuffer putChar(long a, char x) {









        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putChar(char x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putChar(int i, char x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.CharBuffer asCharBuffer() {
        int off = this.position();
        int lim = this.limit();
        assert (off <= lim);
        int rem = (off <= lim ? lim - off : 0);

        int size = rem >> 1;
        if (!unaligned && ((address + off) % (1 << 1) != 0)) {
            return (bigEndian
                    ? (java.nio.CharBuffer)(new ByteBufferAsCharBufferRB(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off))
                    : (java.nio.CharBuffer)(new ByteBufferAsCharBufferRL(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off)));
        } else {
            return (nativeByteOrder
                    ? (java.nio.CharBuffer)(new DirectCharBufferRU(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                    : (CharBuffer)(new DirectCharBufferRS(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
        }
    }






















    private java.nio.ByteBuffer putShort(long a, short x) {









        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putShort(short x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putShort(int i, short x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ShortBuffer asShortBuffer() {
        int off = this.position();
        int lim = this.limit();
        assert (off <= lim);
        int rem = (off <= lim ? lim - off : 0);

        int size = rem >> 1;
        if (!unaligned && ((address + off) % (1 << 1) != 0)) {
            return (bigEndian
                    ? (java.nio.ShortBuffer)(new ByteBufferAsShortBufferRB(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off))
                    : (java.nio.ShortBuffer)(new ByteBufferAsShortBufferRL(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off)));
        } else {
            return (nativeByteOrder
                    ? (java.nio.ShortBuffer)(new DirectShortBufferRU(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                    : (ShortBuffer)(new DirectShortBufferRS(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
        }
    }






















    private java.nio.ByteBuffer putInt(long a, int x) {









        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putInt(int x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putInt(int i, int x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.IntBuffer asIntBuffer() {
        int off = this.position();
        int lim = this.limit();
        assert (off <= lim);
        int rem = (off <= lim ? lim - off : 0);

        int size = rem >> 2;
        if (!unaligned && ((address + off) % (1 << 2) != 0)) {
            return (bigEndian
                    ? (java.nio.IntBuffer)(new ByteBufferAsIntBufferRB(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off))
                    : (java.nio.IntBuffer)(new ByteBufferAsIntBufferRL(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off)));
        } else {
            return (nativeByteOrder
                    ? (java.nio.IntBuffer)(new DirectIntBufferRU(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                    : (IntBuffer)(new DirectIntBufferRS(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
        }
    }






















    private java.nio.ByteBuffer putLong(long a, long x) {









        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putLong(long x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putLong(int i, long x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.LongBuffer asLongBuffer() {
        int off = this.position();
        int lim = this.limit();
        assert (off <= lim);
        int rem = (off <= lim ? lim - off : 0);

        int size = rem >> 3;
        if (!unaligned && ((address + off) % (1 << 3) != 0)) {
            return (bigEndian
                    ? (java.nio.LongBuffer)(new ByteBufferAsLongBufferRB(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off))
                    : (java.nio.LongBuffer)(new ByteBufferAsLongBufferRL(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off)));
        } else {
            return (nativeByteOrder
                    ? (java.nio.LongBuffer)(new DirectLongBufferRU(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                    : (LongBuffer)(new DirectLongBufferRS(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
        }
    }






















    private java.nio.ByteBuffer putFloat(long a, float x) {









        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putFloat(float x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putFloat(int i, float x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.FloatBuffer asFloatBuffer() {
        int off = this.position();
        int lim = this.limit();
        assert (off <= lim);
        int rem = (off <= lim ? lim - off : 0);

        int size = rem >> 2;
        if (!unaligned && ((address + off) % (1 << 2) != 0)) {
            return (bigEndian
                    ? (java.nio.FloatBuffer)(new ByteBufferAsFloatBufferRB(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off))
                    : (java.nio.FloatBuffer)(new ByteBufferAsFloatBufferRL(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off)));
        } else {
            return (nativeByteOrder
                    ? (java.nio.FloatBuffer)(new DirectFloatBufferRU(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                    : (FloatBuffer)(new DirectFloatBufferRS(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
        }
    }






















    private java.nio.ByteBuffer putDouble(long a, double x) {









        throw new ReadOnlyBufferException();

    }

    public java.nio.ByteBuffer putDouble(double x) {




        throw new ReadOnlyBufferException();

    }

    public ByteBuffer putDouble(int i, double x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.DoubleBuffer asDoubleBuffer() {
        int off = this.position();
        int lim = this.limit();
        assert (off <= lim);
        int rem = (off <= lim ? lim - off : 0);

        int size = rem >> 3;
        if (!unaligned && ((address + off) % (1 << 3) != 0)) {
            return (bigEndian
                    ? (java.nio.DoubleBuffer)(new ByteBufferAsDoubleBufferRB(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off))
                    : (java.nio.DoubleBuffer)(new ByteBufferAsDoubleBufferRL(this,
                                                                       -1,
                                                                       0,
                                                                       size,
                                                                       size,
                                                                       off)));
        } else {
            return (nativeByteOrder
                    ? (java.nio.DoubleBuffer)(new DirectDoubleBufferRU(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off))
                    : (DoubleBuffer)(new DirectDoubleBufferRS(this,
                                                                 -1,
                                                                 0,
                                                                 size,
                                                                 size,
                                                                 off)));
        }
    }

}
