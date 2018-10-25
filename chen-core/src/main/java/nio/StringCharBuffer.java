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

package nio;


// ## If the sequence is a string, use reflection to share its array

import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

class StringCharBuffer                                  // package-private
    extends java.nio.CharBuffer
{
    CharSequence str;

    StringCharBuffer(CharSequence s, int start, int end) { // package-private
        super(-1, start, end, s.length());
        int n = s.length();
        if ((start < 0) || (start > n) || (end < start) || (end > n))
            throw new IndexOutOfBoundsException();
        str = s;
    }

    public java.nio.CharBuffer slice() {
        return new StringCharBuffer(str,
                                    -1,
                                    0,
                                    this.remaining(),
                                    this.remaining(),
                                    offset + this.position());
    }

    private StringCharBuffer(CharSequence s,
                             int mark,
                             int pos,
                             int limit,
                             int cap,
                             int offset) {
        super(mark, pos, limit, cap, null, offset);
        str = s;
    }

    public java.nio.CharBuffer duplicate() {
        return new StringCharBuffer(str, markValue(),
                                    position(), limit(), capacity(), offset);
    }

    public java.nio.CharBuffer asReadOnlyBuffer() {
        return duplicate();
    }

    public final char get() {
        return str.charAt(nextGetIndex() + offset);
    }

    public final char get(int index) {
        return str.charAt(checkIndex(index) + offset);
    }

    char getUnchecked(int index) {
        return str.charAt(index + offset);
    }

    // ## Override bulk get methods for better performance

    public final java.nio.CharBuffer put(char c) {
        throw new ReadOnlyBufferException();
    }

    public final java.nio.CharBuffer put(int index, char c) {
        throw new ReadOnlyBufferException();
    }

    public final java.nio.CharBuffer compact() {
        throw new ReadOnlyBufferException();
    }

    public final boolean isReadOnly() {
        return true;
    }

    final String toString(int start, int end) {
        return str.toString().substring(start + offset, end + offset);
    }

    public final CharBuffer subSequence(int start, int end) {
        try {
            int pos = position();
            return new StringCharBuffer(str,
                                        -1,
                                        pos + checkIndex(start, pos),
                                        pos + checkIndex(end, pos),
                                        capacity(),
                                        offset);
        } catch (IllegalArgumentException x) {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isDirect() {
        return false;
    }

    public java.nio.ByteOrder order() {
        return ByteOrder.nativeOrder();
    }

}
