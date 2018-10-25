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
import java.nio.ByteBufferAsFloatBufferB;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ReadOnlyBufferException;

class ByteBufferAsFloatBufferRB                  // package-private
    extends java.nio.ByteBufferAsFloatBufferB
{








    ByteBufferAsFloatBufferRB(java.nio.ByteBuffer bb) {   // package-private












        super(bb);

    }

    ByteBufferAsFloatBufferRB(ByteBuffer bb,
                              int mark, int pos, int lim, int cap,
                              int off)
    {





        super(bb, mark, pos, lim, cap, off);

    }

    public java.nio.FloatBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        assert (pos <= lim);
        int rem = (pos <= lim ? lim - pos : 0);
        int off = (pos << 2) + offset;
        assert (off >= 0);
        return new ByteBufferAsFloatBufferRB(bb, -1, 0, rem, rem, off);
    }

    public java.nio.FloatBuffer duplicate() {
        return new ByteBufferAsFloatBufferRB(bb,
                                                    this.markValue(),
                                                    this.position(),
                                                    this.limit(),
                                                    this.capacity(),
                                                    offset);
    }

    public java.nio.FloatBuffer asReadOnlyBuffer() {








        return duplicate();

    }























    public java.nio.FloatBuffer put(float x) {




        throw new java.nio.ReadOnlyBufferException();

    }

    public java.nio.FloatBuffer put(int i, float x) {




        throw new java.nio.ReadOnlyBufferException();

    }

    public FloatBuffer compact() {

















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
