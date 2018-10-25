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
import java.nio.ByteBufferAsLongBufferL;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;

class ByteBufferAsLongBufferRL                  // package-private
    extends java.nio.ByteBufferAsLongBufferL
{








    ByteBufferAsLongBufferRL(java.nio.ByteBuffer bb) {   // package-private












        super(bb);

    }

    ByteBufferAsLongBufferRL(ByteBuffer bb,
                             int mark, int pos, int lim, int cap,
                             int off)
    {





        super(bb, mark, pos, lim, cap, off);

    }

    public java.nio.LongBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 3) + offset;
        assert (off >= 0);
        return new ByteBufferAsLongBufferRL(bb, -1, 0, rem, rem, off);
    }

    public java.nio.LongBuffer duplicate() {
        return new ByteBufferAsLongBufferRL(bb,
                                                    this.markValue(),
                                                    this.position(),
                                                    this.limit(),
                                                    this.capacity(),
                                                    offset);
    }

    public java.nio.LongBuffer asReadOnlyBuffer() {








        return duplicate();

    }























    public java.nio.LongBuffer put(long x) {




        throw new java.nio.ReadOnlyBufferException();

    }

    public java.nio.LongBuffer put(int i, long x) {




        throw new java.nio.ReadOnlyBufferException();

    }

    public LongBuffer compact() {

















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
