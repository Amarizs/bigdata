/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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

package io;

import java.io.FileSystem;
import java.io.WinNTFileSystem;

/**
 *
 * @since 1.8
 */
class DefaultFileSystem {

    /**
     * Return the FileSystem object for Windows platform.
     */
    public static java.io.FileSystem getFileSystem() {
        return new java.io.WinNTFileSystem();
    }
}
