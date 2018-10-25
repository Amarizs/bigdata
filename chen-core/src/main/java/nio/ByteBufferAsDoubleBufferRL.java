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


import java.nio.ByteBuffer;
import java.nio.ByteBufferAsDoubleBufferL;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.ReadOnlyBufferException;

class ByteBufferAsDoubleBufferRL                  // package-private
    extends java.nio.ByteBufferAsDoubleBufferL
{








    ByteBufferAsDoubleBufferRL(java.nio.ByteBuffer bb) {   // package-private












        super(bb);

    }

    ByteBufferAsDoubleBufferRL(ByteBuffer bb,
                               int mark, int pos, int lim, int cap,
                               int off)
    {





        super(bb, mark, pos, lim, cap, off);

    }

    public java.nio.DoubleBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 3) + offset;
        assert (off >= 0);
        return new ByteBufferAsDoubleBufferRL(bb, -1, 0, rem, rem, off);
    }

    public java.nio.DoubleBuffer duplicate() {
        return new ByteBufferAsDoubleBufferRL(bb,
                                                    this.markValue(),
                                                    this.position(),
                                                    this.limit(),
                                                    this.capacity(),
                                                    offset);
    }

    public java.nio.DoubleBuffer asReadOnlyBuffer() {








        return duplicate();

    }























    public java.nio.DoubleBuffer put(double x) {




        throw new java.nio.ReadOnlyBufferException();

    }

    public java.nio.DoubleBuffer put(int i, double x) {




        throw new java.nio.ReadOnlyBufferException();

    }

    public DoubleBuffer compact() {

















        throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
        return bb.isDirect();
    }

    public boolean isReadOnly() {
        return true;
    }











































    public java.nio.ByteOrder order() {




        return ByteOrder.LITTLE_ENDIAN;

    }

}
