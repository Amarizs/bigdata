/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

package util.jar;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import sun.misc.JavaUtilJarAccess;

class JavaUtilJarAccessImpl implements JavaUtilJarAccess {
    public boolean jarFileHasClassPathAttribute(java.util.jar.JarFile jar) throws IOException {
        return jar.hasClassPathAttribute();
    }

    public CodeSource[] getCodeSources(java.util.jar.JarFile jar, URL url) {
        return jar.getCodeSources(url);
    }

    public CodeSource getCodeSource(java.util.jar.JarFile jar, URL url, String name) {
        return jar.getCodeSource(url, name);
    }

    public Enumeration<String> entryNames(java.util.jar.JarFile jar, CodeSource[] cs) {
        return jar.entryNames(cs);
    }

    public Enumeration<JarEntry> entries2(java.util.jar.JarFile jar) {
        return jar.entries2();
    }

    public void setEagerValidation(java.util.jar.JarFile jar, boolean eager) {
        jar.setEagerValidation(eager);
    }

    public List<Object> getManifestDigests(JarFile jar) {
        return jar.getManifestDigests();
    }
}
