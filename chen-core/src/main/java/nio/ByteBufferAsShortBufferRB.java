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
import java.nio.ByteBufferAsShortBufferB;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;

class ByteBufferAsShortBufferRB                  // package-private
    extends java.nio.ByteBufferAsShortBufferB
{








    ByteBufferAsShortBufferRB(java.nio.ByteBuffer bb) {   // package-private












        super(bb);

    }

    ByteBufferAsShortBufferRB(ByteBuffer bb,
                              int mark, int pos, int lim, int cap,
                              int off)
    {





        super(bb, mark, pos, lim, cap, off);

    }

    public java.nio.ShortBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 1) + offset;
        assert (off >= 0);
        return new ByteBufferAsShortBufferRB(bb, -1, 0, rem, rem, off);
    }

    public java.nio.ShortBuffer duplicate() {
        return new ByteBufferAsShortBufferRB(bb,
                                                    this.markValue(),
                                                    this.position(),
                                                    this.limit(),
                                                    this.capacity(),
                                                    offset);
    }

    public java.nio.ShortBuffer asReadOnlyBuffer() {








        return duplicate();

    }























    public java.nio.ShortBuffer put(short x) {




        throw new java.nio.ReadOnlyBufferException();

    }

    public java.nio.ShortBuffer put(int i, short x) {




        throw new java.nio.ReadOnlyBufferException();

    }

    public ShortBuffer compact() {

















        throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
        return bb.isDirect();
    }

    public boolean isReadOnly() {
        return true;
    }











































    public java.nio.ByteOrder order() {

        return ByteOrder.BIG_ENDIAN;




    }

}
