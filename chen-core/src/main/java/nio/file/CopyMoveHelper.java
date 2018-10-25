/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.*;
import java.io.InputStream;
import java.io.IOException;

/**
 * Helper class to support copying or moving files when the source and target
 * are associated with different providers.
 */

class CopyMoveHelper {
    private CopyMoveHelper() { }

    /**
     * Parses the arguments for a file copy operation.
     */
    private static class CopyOptions {
        boolean replaceExisting = false;
        boolean copyAttributes = false;
        boolean followLinks = true;

        private CopyOptions() { }

        static CopyOptions parse(java.nio.file.CopyOption... options) {
            CopyOptions result = new CopyOptions();
            for (java.nio.file.CopyOption option: options) {
                if (option == StandardCopyOption.REPLACE_EXISTING) {
                    result.replaceExisting = true;
                    continue;
                }
                if (option == java.nio.file.LinkOption.NOFOLLOW_LINKS) {
                    result.followLinks = false;
                    continue;
                }
                if (option == StandardCopyOption.COPY_ATTRIBUTES) {
                    result.copyAttributes = true;
                    continue;
                }
                if (option == null)
                    throw new NullPointerException();
                throw new UnsupportedOperationException("'" + option +
                    "' is not a recognized copy option");
            }
            return result;
        }
    }

    /**
     * Converts the given array of options for moving a file to options suitable
     * for copying the file when a move is implemented as copy + delete.
     */
    private static java.nio.file.CopyOption[] convertMoveToCopyOptions(java.nio.file.CopyOption... options)
        throws AtomicMoveNotSupportedException
    {
        int len = options.length;
        java.nio.file.CopyOption[] newOptions = new java.nio.file.CopyOption[len+2];
        for (int i=0; i<len; i++) {
            java.nio.file.CopyOption option = options[i];
            if (option == StandardCopyOption.ATOMIC_MOVE) {
                throw new AtomicMoveNotSupportedException(null, null,
                    "Atomic move between providers is not supported");
            }
            newOptions[i] = option;
        }
        newOptions[len] = java.nio.file.LinkOption.NOFOLLOW_LINKS;
        newOptions[len+1] = StandardCopyOption.COPY_ATTRIBUTES;
        return newOptions;
    }

    /**
     * Simple copy for use when source and target are associated with different
     * providers
     */
    static void copyToForeignTarget(java.nio.file.Path source, java.nio.file.Path target,
                                    java.nio.file.CopyOption... options)
        throws IOException
    {
        CopyOptions opts = CopyOptions.parse(options);
        java.nio.file.LinkOption[] linkOptions = (opts.followLinks) ? new java.nio.file.LinkOption[0] :
            new java.nio.file.LinkOption[] { LinkOption.NOFOLLOW_LINKS };

        // attributes of source file
        BasicFileAttributes attrs = java.nio.file.Files.readAttributes(source,
                                                         BasicFileAttributes.class,
                                                         linkOptions);
        if (attrs.isSymbolicLink())
            throw new IOException("Copying of symbolic links not supported");

        // delete target if it exists and REPLACE_EXISTING is specified
        if (opts.replaceExisting) {
            java.nio.file.Files.deleteIfExists(target);
        } else if (java.nio.file.Files.exists(target))
            throw new FileAlreadyExistsException(target.toString());

        // create directory or copy file
        if (attrs.isDirectory()) {
            java.nio.file.Files.createDirectory(target);
        } else {
            try (InputStream in = java.nio.file.Files.newInputStream(source)) {
                java.nio.file.Files.copy(in, target);
            }
        }

        // copy basic attributes to target
        if (opts.copyAttributes) {
            BasicFileAttributeView view =
                java.nio.file.Files.getFileAttributeView(target, BasicFileAttributeView.class);
            try {
                view.setTimes(attrs.lastModifiedTime(),
                              attrs.lastAccessTime(),
                              attrs.creationTime());
            } catch (Throwable x) {
                // rollback
                try {
                    java.nio.file.Files.delete(target);
                } catch (Throwable suppressed) {
                    x.addSuppressed(suppressed);
                }
                throw x;
            }
        }
    }

    /**
     * Simple move implements as copy+delete for use when source and target are
     * associated with different providers
     */
    static void moveToForeignTarget(java.nio.file.Path source, Path target,
                                    CopyOption... options) throws IOException
    {
        copyToForeignTarget(source, target, convertMoveToCopyOptions(options));
        Files.delete(source);
    }
}
