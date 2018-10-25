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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * An object that configures how to copy or move a file.
 *
 * <p> Objects of this type may be used with the {@link
 * java.nio.file.Files#copy(java.nio.file.Path, java.nio.file.Path,CopyOption[]) Files.copy(Path,Path,CopyOption...)},
 * {@link java.nio.file.Files#copy(java.io.InputStream, Path,CopyOption[])
 * Files.copy(InputStream,Path,CopyOption...)} and {@link Files#move
 * Files.move(Path,Path,CopyOption...)} methods to configure how a file is
 * copied or moved.
 *
 * <p> The {@link StandardCopyOption} enumeration type defines the
 * <i>standard</i> options.
 *
 * @since 1.7
 */

public interface CopyOption {
}
