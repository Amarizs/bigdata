/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

package nio.file;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An interface that is implemented by objects that perform match operations on
 * paths.
 *
 * @since 1.7
 *
 * @see FileSystem#getPathMatcher
 * @see Files#newDirectoryStream(java.nio.file.Path,String)
 */
@FunctionalInterface
public interface PathMatcher {
    /**
     * Tells if given path matches this matcher's pattern.
     *
     * @param   path
     *          the path to match
     *
     * @return  {@code true} if, and only if, the path matches this
     *          matcher's pattern
     */
    boolean matches(Path path);
}
