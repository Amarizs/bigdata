/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package lang;

/** The CharacterData class encapsulates the large tables found in
    Java.lang.Character. */

class CharacterDataUndefined extends java.lang.CharacterData {

    int getProperties(int ch) {
        return 0;
    }

    int getType(int ch) {
	return java.lang.Character.UNASSIGNED;
    }

    boolean isJavaIdentifierStart(int ch) {
		return false;
    }

    boolean isJavaIdentifierPart(int ch) {
		return false;
    }

    boolean isUnicodeIdentifierStart(int ch) {
		return false;
    }

    boolean isUnicodeIdentifierPart(int ch) {
		return false;
    }

    boolean isIdentifierIgnorable(int ch) {
		return false;
    }

    int toLowerCase(int ch) {
		return ch;
    }

    int toUpperCase(int ch) {
		return ch;
    }

    int toTitleCase(int ch) {
		return ch;
    }

    int digit(int ch, int radix) {
		return -1;
    }

    int getNumericValue(int ch) {
		return -1;
    }

    boolean isWhitespace(int ch) {
		return false;
    }

    byte getDirectionality(int ch) {
		return java.lang.Character.DIRECTIONALITY_UNDEFINED;
    }

    boolean isMirrored(int ch) {
		return false;
    }

    static final java.lang.CharacterData instance = new CharacterDataUndefined();
    private CharacterDataUndefined() {};
}
