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
import java.nio.DirectIntBufferU;
import java.nio.IntBuffer;
import java.nio.ReadOnlyBufferException;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.misc.VM;
import sun.nio.ch.DirectBuffer;


class DirectIntBufferRU



    extends java.nio.DirectIntBufferU

    implements DirectBuffer
{















































































































































    // For duplicates and slices
    //
    DirectIntBufferRU(DirectBuffer db,         // package-private
                               int mark, int pos, int lim, int cap,
                               int off)
    {








        super(db, mark, pos, lim, cap, off);

    }

    public java.nio.IntBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 2);
        assert (off >= 0);
        return new DirectIntBufferRU(this, -1, 0, rem, rem, off);
    }

    public java.nio.IntBuffer duplicate() {
        return new DirectIntBufferRU(this,
                                              this.markValue(),
                                              this.position(),
                                              this.limit(),
                                              this.capacity(),
                                              0);
    }

    public java.nio.IntBuffer asReadOnlyBuffer() {








        return duplicate();

    }


























































    public java.nio.IntBuffer put(int x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.IntBuffer put(int i, int x) {




        throw new ReadOnlyBufferException();

    }

    public java.nio.IntBuffer put(java.nio.IntBuffer src) {




































        throw new ReadOnlyBufferException();

    }

    public java.nio.IntBuffer put(int[] src, int offset, int length) {




























        throw new ReadOnlyBufferException();

    }

    public IntBuffer compact() {












        throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
        return true;
    }

    public boolean isReadOnly() {
        return true;
    }















































    public java.nio.ByteOrder order() {





        return ((java.nio.ByteOrder.nativeOrder() != java.nio.ByteOrder.BIG_ENDIAN)
                ? java.nio.ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

    }


























}
