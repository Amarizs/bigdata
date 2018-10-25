/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.file.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Defines the options as to how symbolic links are handled.
 *
 * @since 1.7
 */

public enum LinkOption implements OpenOption, CopyOption {
    /**
     * Do not follow symbolic links.
     *
     * @see java.nio.file.Files#getFileAttributeView(Path,Class,LinkOption[])
     * @see Files#copy
     * @see SecureDirectoryStream#newByteChannel
     */
    NOFOLLOW_LINKS;
}
