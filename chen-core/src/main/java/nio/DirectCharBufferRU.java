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
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DirectCharBufferU;
import java.nio.ReadOnlyBufferException;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.misc.VM;
import sun.nio.ch.DirectBuffer;


class DirectCharBufferRU



    extends java.nio.DirectCharBufferU

    implements DirectBuffer
{















































































































































    // For duplicates and slices
    //
    DirectCharBufferRU(DirectBuffer db,         // package-private
                               int mark, int pos, int lim, int cap,
                               int off)
    {








        super(db, mark, pos, lim, cap, off);

    }

    public java.nio.CharBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 1);
        assert (off >= 0);
        return new DirectCharBufferRU(this, -1, 0, rem, rem, off);
    }

    public java.nio.CharBuffer duplicate() {
        return new DirectCharBufferRU(this,
                                              this.markValue(),
                                              this.position(),
                                              this.limit(),
                                              this.capacity(),
                                              0);
    }

    public java.nio.CharBuffer asReadOnlyBuffer() {








        return duplicate();

    }


























































    public java.nio.CharBuffer put(char x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.CharBuffer put(int i, char x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.CharBuffer put(java.nio.CharBuffer src) {




































        throw new ReadOnlyBufferException();

    }

    public java.nio.CharBuffer put(char[] src, int offset, int length) {




























        throw new ReadOnlyBufferException();

    }

    public java.nio.CharBuffer compact() {












        throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
        return true;
    }

    public boolean isReadOnly() {
        return true;
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
        return new DirectCharBufferRU(this,
                                            -1,
                                            pos + start,
                                            pos + end,
                                            capacity(),
                                            offset);
    }







    public java.nio.ByteOrder order() {





        return ((java.nio.ByteOrder.nativeOrder() != java.nio.ByteOrder.BIG_ENDIAN)
                ? java.nio.ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

    }


























}
