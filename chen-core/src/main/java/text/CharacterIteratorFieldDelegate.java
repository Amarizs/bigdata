/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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
package text;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.Format;
import java.util.ArrayList;

/**
 * CharacterIteratorFieldDelegate combines the notifications from a Format
 * into a resulting <code>AttributedCharacterIterator</code>. The resulting
 * <code>AttributedCharacterIterator</code> can be retrieved by way of
 * the <code>getIterator</code> method.
 *
 */
class CharacterIteratorFieldDelegate implements java.text.Format.FieldDelegate {
    /**
     * Array of AttributeStrings. Whenever <code>formatted</code> is invoked
     * for a region > size, a new instance of AttributedString is added to
     * attributedStrings. Subsequent invocations of <code>formatted</code>
     * for existing regions result in invoking addAttribute on the existing
     * AttributedStrings.
     */
    private ArrayList<java.text.AttributedString> attributedStrings;
    /**
     * Running count of the number of characters that have
     * been encountered.
     */
    private int size;


    CharacterIteratorFieldDelegate() {
        attributedStrings = new ArrayList<>();
    }

    public void formatted(java.text.Format.Field attr, Object value, int start, int end,
                          StringBuffer buffer) {
        if (start != end) {
            if (start < size) {
                // Adjust attributes of existing runs
                int index = size;
                int asIndex = attributedStrings.size() - 1;

                while (start < index) {
                    java.text.AttributedString as = attributedStrings.
                                           get(asIndex--);
                    int newIndex = index - as.length();
                    int aStart = Math.max(0, start - newIndex);

                    as.addAttribute(attr, value, aStart, Math.min(
                                    end - start, as.length() - aStart) +
                                    aStart);
                    index = newIndex;
                }
            }
            if (size < start) {
                // Pad attributes
                attributedStrings.add(new java.text.AttributedString(
                                          buffer.substring(size, start)));
                size = start;
            }
            if (size < end) {
                // Add new string
                int aStart = Math.max(start, size);
                java.text.AttributedString string = new java.text.AttributedString(
                                   buffer.substring(aStart, end));

                string.addAttribute(attr, value);
                attributedStrings.add(string);
                size = end;
            }
        }
    }

    public void formatted(int fieldID, Format.Field attr, Object value,
                          int start, int end, StringBuffer buffer) {
        formatted(attr, value, start, end, buffer);
    }

    /**
     * Returns an <code>AttributedCharacterIterator</code> that can be used
     * to iterate over the resulting formatted String.
     *
     * @pararm string Result of formatting.
     */
    public java.text.AttributedCharacterIterator getIterator(String string) {
        // Add the last AttributedCharacterIterator if necessary
        // assert(size <= string.length());
        if (string.length() > size) {
            attributedStrings.add(new java.text.AttributedString(
                                  string.substring(size)));
            size = string.length();
        }
        int iCount = attributedStrings.size();
        java.text.AttributedCharacterIterator iterators[] = new
                AttributedCharacterIterator[iCount];

        for (int counter = 0; counter < iCount; counter++) {
            iterators[counter] = attributedStrings.
                                  get(counter).getIterator();
        }
        return new AttributedString(iterators).getIterator();
    }
}
