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
import java.nio.Bits;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DirectCharBufferRS;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.misc.VM;
import sun.nio.ch.DirectBuffer;


class DirectCharBufferS

    extends java.nio.CharBuffer



    implements DirectBuffer
{



    // Cached unsafe-access object
    protected static final Unsafe unsafe = java.nio.Bits.unsafe();

    // Cached array base offset
    private static final long arrayBaseOffset = (long)unsafe.arrayBaseOffset(char[].class);

    // Cached unaligned-access capability
    protected static final boolean unaligned = java.nio.Bits.unaligned();

    // Base address, used in all indexing calculations
    // NOTE: moved up to Buffer.java for speed in JNI GetDirectBufferAddress
    //    protected long address;

    // An object attached to this buffer. If this buffer is a view of another
    // buffer then we use this field to keep a reference to that buffer to
    // ensure that its memory isn't freed before we are done with it.
    private final Object att;

    public Object attachment() {
        return att;
    }






































    public Cleaner cleaner() { return null; }
















































































    // For duplicates and slices
    //
    DirectCharBufferS(DirectBuffer db,         // package-private
                               int mark, int pos, int lim, int cap,
                               int off)
    {

        super(mark, pos, lim, cap);
        address = db.address() + off;



        att = db;



    }

    public java.nio.CharBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 1);
        assert (off >= 0);
        return new DirectCharBufferS(this, -1, 0, rem, rem, off);
    }

    public java.nio.CharBuffer duplicate() {
        return new DirectCharBufferS(this,
                                              this.markValue(),
                                              this.position(),
                                              this.limit(),
                                              this.capacity(),
                                              0);
    }

    public java.nio.CharBuffer asReadOnlyBuffer() {

        return new DirectCharBufferRS(this,
                                           this.markValue(),
                                           this.position(),
                                           this.limit(),
                                           this.capacity(),
                                           0);



    }



    public long address() {
        return address;
    }

    private long ix(int i) {
        return address + ((long)i << 1);
    }

    public char get() {
        return (java.nio.Bits.swap(unsafe.getChar(ix(nextGetIndex()))));
    }

    public char get(int i) {
        return (java.nio.Bits.swap(unsafe.getChar(ix(checkIndex(i)))));
    }


    char getUnchecked(int i) {
        return (java.nio.Bits.swap(unsafe.getChar(ix(i))));
    }


    public java.nio.CharBuffer get(char[] dst, int offset, int length) {

        if (((long)length << 1) > java.nio.Bits.JNI_COPY_TO_ARRAY_THRESHOLD) {
            checkBounds(offset, length, dst.length);
            int pos = position();
            int lim = limit();
            assert (pos <= lim);
            int rem = (pos <= lim ? lim - pos : 0);
            if (length > rem)
                throw new BufferUnderflowException();


            if (order() != java.nio.ByteOrder.nativeOrder())
                java.nio.Bits.copyToCharArray(ix(pos), dst,
                                          (long)offset << 1,
                                          (long)length << 1);
            else

                java.nio.Bits.copyToArray(ix(pos), dst, arrayBaseOffset,
                                 (long)offset << 1,
                                 (long)length << 1);
            position(pos + length);
        } else {
            super.get(dst, offset, length);
        }
        return this;



    }



    public java.nio.CharBuffer put(char x) {

        unsafe.putChar(ix(nextPutIndex()), java.nio.Bits.swap((x)));
        return this;



    }

    public java.nio.CharBuffer put(int i, char x) {

        unsafe.putChar(ix(checkIndex(i)), java.nio.Bits.swap((x)));
        return this;



    }

    public java.nio.CharBuffer put(java.nio.CharBuffer src) {

        if (src instanceof DirectCharBufferS) {
            if (src == this)
                throw new IllegalArgumentException();
            DirectCharBufferS sb = (DirectCharBufferS)src;

            int spos = sb.position();
            int slim = sb.limit();
            assert (spos <= slim);
            int srem = (spos <= slim ? slim - spos : 0);

            int pos = position();
            int lim = limit();
            assert (pos <= lim);
            int rem = (pos <= lim ? lim - pos : 0);

            if (srem > rem)
                throw new BufferOverflowException();
            unsafe.copyMemory(sb.ix(spos), ix(pos), (long)srem << 1);
            sb.position(spos + srem);
            position(pos + srem);
        } else if (src.hb != null) {

            int spos = src.position();
            int slim = src.limit();
            assert (spos <= slim);
            int srem = (spos <= slim ? slim - spos : 0);

            put(src.hb, src.offset + spos, srem);
            src.position(spos + srem);

        } else {
            super.put(src);
        }
        return this;



    }

    public java.nio.CharBuffer put(char[] src, int offset, int length) {

        if (((long)length << 1) > java.nio.Bits.JNI_COPY_FROM_ARRAY_THRESHOLD) {
            checkBounds(offset, length, src.length);
            int pos = position();
            int lim = limit();
            assert (pos <= lim);
            int rem = (pos <= lim ? lim - pos : 0);
            if (length > rem)
                throw new BufferOverflowException();


            if (order() != java.nio.ByteOrder.nativeOrder())
                java.nio.Bits.copyFromCharArray(src,
                                            (long)offset << 1,
                                            ix(pos),
                                            (long)length << 1);
            else

                java.nio.Bits.copyFromArray(src, arrayBaseOffset,
                                   (long)offset << 1,
                                   ix(pos),
                                   (long)length << 1);
            position(pos + length);
        } else {
            super.put(src, offset, length);
        }
        return this;



    }

    public java.nio.CharBuffer compact() {

        int pos = position();
        int lim = limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);

        unsafe.copyMemory(ix(pos), ix(0), (long)rem << 1);
        position(rem);
        limit(capacity());
        discardMark();
        return this;



    }

    public boolean isDirect() {
        return true;
    }

    public boolean isReadOnly() {
        return false;
    }




    public String toString(int start, int end) {
        if ((end > limit()) || (start > end))
            throw new IndexOutOfBoundsException();
        try {
            int len = end - start;
            char[] ca = new char[len];
            java.nio.CharBuffer cb = java.nio.CharBuffer.wrap(ca);
            java.nio.CharBuffer db = this.duplicate();
            db.position(start);
            db.limit(end);
            cb.put(db);
            return new String(ca);
        } catch (StringIndexOutOfBoundsException x) {
            throw new IndexOutOfBoundsException();
        }
    }


    // --- Methods to support CharSequence ---

    public CharBuffer subSequence(int start, int end) {
        int pos = position();
        int lim = limit();
        assert (pos <= lim);
        pos = (pos <= lim ? pos : lim);
        int len = lim - pos;

        if ((start < 0) || (end > len) || (start > end))
            throw new IndexOutOfBoundsException();
        return new DirectCharBufferS(this,
                                            -1,
                                            pos + start,
                                            pos + end,
                                            capacity(),
                                            offset);
    }







    public java.nio.ByteOrder order() {

        return ((java.nio.ByteOrder.nativeOrder() == java.nio.ByteOrder.BIG_ENDIAN)
                ? java.nio.ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);





    }


























}
