/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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

/* We use APIs that access a so-called Windows "Environment Block",
 * which looks like an array of jchars like this:
 *
 * FOO=BAR\u0000 ... GORP=QUUX\u0000\u0000
 *
 * This data structure has a number of peculiarities we must contend with:
 * (see: http://windowssdk.msdn.microsoft.com/en-us/library/ms682009.aspx)
 * - The NUL jchar separators, and a double NUL jchar terminator.
 *   It appears that the Windows implementation requires double NUL
 *   termination even if the environment is empty.  We should always
 *   generate environments with double NUL termination, while accepting
 *   empty environments consisting of a single NUL.
 * - on Windows9x, this is actually an array of 8-bit chars, not jchars,
 *   encoded in the system default encoding.
 * - The block must be sorted by Unicode value, case-insensitively,
 *   as if folded to upper case.
 * - There are magic environment variables maintained by Windows
 *   that start with a `=' (!) character.  These are used for
 *   Windows drive current directory (e.g. "=C:=C:\WINNT") or the
 *   exit code of the last command (e.g. "=ExitCode=0000001").
 *
 * Since Java and non-9x Windows speak the same character set, and
 * even the same encoding, we don't have to deal with unreliable
 * conversion to byte streams.  Just add a few NUL terminators.
 *
 * System.getenv(String) is case-insensitive, while System.getenv()
 * returns a map that is case-sensitive, which is consistent with
 * native Windows APIs.
 *
 * The non-private methods in this class are not for general use even
 * within this package.  Instead, they are the system-dependent parts
 * of the system-independent method of the same name.  Don't even
 * think of using this class unless your method's name appears below.
 *
 * @author Martin Buchholz
 * @since 1.5
 */

package lang;

import java.io.*;
import java.util.*;

final class ProcessEnvironment extends HashMap<java.lang.String, java.lang.String>
{

    private static final long serialVersionUID = -8017839552603542824L;

    private static java.lang.String validateName(java.lang.String name) {
        // An initial `=' indicates a magic Windows variable name -- OK
        if (name.indexOf('=', 1)   != -1 ||
            name.indexOf('\u0000') != -1)
            throw new IllegalArgumentException
                ("Invalid environment variable name: \"" + name + "\"");
        return name;
    }

    private static java.lang.String validateValue(java.lang.String value) {
        if (value.indexOf('\u0000') != -1)
            throw new IllegalArgumentException
                ("Invalid environment variable value: \"" + value + "\"");
        return value;
    }

    private static java.lang.String nonNullString(java.lang.Object o) {
        if (o == null)
            throw new NullPointerException();
        return (java.lang.String) o;
    }

    public java.lang.String put(java.lang.String key, java.lang.String value) {
        return super.put(validateName(key), validateValue(value));
    }

    public java.lang.String get(java.lang.Object key) {
        return super.get(nonNullString(key));
    }

    public boolean containsKey(java.lang.Object key) {
        return super.containsKey(nonNullString(key));
    }

    public boolean containsValue(java.lang.Object value) {
        return super.containsValue(nonNullString(value));
    }

    public java.lang.String remove(java.lang.Object key) {
        return super.remove(nonNullString(key));
    }

    private static class CheckedEntry
        implements Entry<java.lang.String, java.lang.String>
    {
        private final Entry<java.lang.String, java.lang.String> e;
        public CheckedEntry(Entry<java.lang.String, java.lang.String> e) {this.e = e;}
        public java.lang.String getKey()   { return e.getKey();}
        public java.lang.String getValue() { return e.getValue();}
        public java.lang.String setValue(java.lang.String value) {
            return e.setValue(validateValue(value));
        }
        public java.lang.String toString() { return getKey() + "=" + getValue();}
        public boolean equals(java.lang.Object o) {return e.equals(o);}
        public int hashCode()    {return e.hashCode();}
    }

    private static class CheckedEntrySet
        extends AbstractSet<Entry<java.lang.String, java.lang.String>>
    {
        private final Set<Entry<java.lang.String, java.lang.String>> s;
        public CheckedEntrySet(Set<Entry<java.lang.String, java.lang.String>> s) {this.s = s;}
        public int size()        {return s.size();}
        public boolean isEmpty() {return s.isEmpty();}
        public void clear()      {       s.clear();}
        public Iterator<Entry<java.lang.String, java.lang.String>> iterator() {
            return new Iterator<Entry<java.lang.String, java.lang.String>>() {
                Iterator<Entry<java.lang.String, java.lang.String>> i = s.iterator();
                public boolean hasNext() { return i.hasNext();}
                public Entry<java.lang.String, java.lang.String> next() {
                    return new CheckedEntry(i.next());
                }
                public void remove() { i.remove();}
            };
        }
        private static Entry<java.lang.String, java.lang.String> checkedEntry(java.lang.Object o) {
            @java.lang.SuppressWarnings("unchecked")
            Entry<java.lang.String, java.lang.String> e = (Entry<java.lang.String, java.lang.String>) o;
            nonNullString(e.getKey());
            nonNullString(e.getValue());
            return e;
        }
        public boolean contains(java.lang.Object o) {return s.contains(checkedEntry(o));}
        public boolean remove(java.lang.Object o)   {return s.remove(checkedEntry(o));}
    }

    private static class CheckedValues extends AbstractCollection<java.lang.String> {
        private final Collection<java.lang.String> c;
        public CheckedValues(Collection<java.lang.String> c) {this.c = c;}
        public int size()                  {return c.size();}
        public boolean isEmpty()           {return c.isEmpty();}
        public void clear()                {       c.clear();}
        public Iterator<java.lang.String> iterator() {return c.iterator();}
        public boolean contains(java.lang.Object o)  {return c.contains(nonNullString(o));}
        public boolean remove(java.lang.Object o)    {return c.remove(nonNullString(o));}
    }

    private static class CheckedKeySet extends AbstractSet<java.lang.String> {
        private final Set<java.lang.String> s;
        public CheckedKeySet(Set<java.lang.String> s) {this.s = s;}
        public int size()                  {return s.size();}
        public boolean isEmpty()           {return s.isEmpty();}
        public void clear()                {       s.clear();}
        public Iterator<java.lang.String> iterator() {return s.iterator();}
        public boolean contains(java.lang.Object o)  {return s.contains(nonNullString(o));}
        public boolean remove(java.lang.Object o)    {return s.remove(nonNullString(o));}
    }

    public Set<java.lang.String> keySet() {
        return new CheckedKeySet(super.keySet());
    }

    public Collection<java.lang.String> values() {
        return new CheckedValues(super.values());
    }

    public Set<Entry<java.lang.String, java.lang.String>> entrySet() {
        return new CheckedEntrySet(super.entrySet());
    }


    private static final class NameComparator
        implements Comparator<java.lang.String> {
        public int compare(java.lang.String s1, java.lang.String s2) {
            // We can't use String.compareToIgnoreCase since it
            // canonicalizes to lower case, while Windows
            // canonicalizes to upper case!  For example, "_" should
            // sort *after* "Z", not before.
            int n1 = s1.length();
            int n2 = s2.length();
            int min = java.lang.Math.min(n1, n2);
            for (int i = 0; i < min; i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);
                if (c1 != c2) {
                    c1 = java.lang.Character.toUpperCase(c1);
                    c2 = java.lang.Character.toUpperCase(c2);
                    if (c1 != c2)
                        // No overflow because of numeric promotion
                        return c1 - c2;
                }
            }
            return n1 - n2;
        }
    }

    private static final class EntryComparator
        implements Comparator<Entry<java.lang.String, java.lang.String>> {
        public int compare(Entry<java.lang.String, java.lang.String> e1,
                           Entry<java.lang.String, java.lang.String> e2) {
            return nameComparator.compare(e1.getKey(), e2.getKey());
        }
    }

    // Allow `=' as first char in name, e.g. =C:=C:\DIR
    static final int MIN_NAME_LENGTH = 1;

    private static final NameComparator nameComparator;
    private static final EntryComparator entryComparator;
    private static final ProcessEnvironment theEnvironment;
    private static final Map<java.lang.String, java.lang.String> theUnmodifiableEnvironment;
    private static final Map<java.lang.String, java.lang.String> theCaseInsensitiveEnvironment;

    static {
        nameComparator  = new NameComparator();
        entryComparator = new EntryComparator();
        theEnvironment  = new ProcessEnvironment();
        theUnmodifiableEnvironment
            = Collections.unmodifiableMap(theEnvironment);

        java.lang.String envblock = environmentBlock();
        int beg, end, eql;
        for (beg = 0;
             ((end = envblock.indexOf('\u0000', beg  )) != -1 &&
              // An initial `=' indicates a magic Windows variable name -- OK
              (eql = envblock.indexOf('='     , beg+1)) != -1);
             beg = end + 1) {
            // Ignore corrupted environment strings.
            if (eql < end)
                theEnvironment.put(envblock.substring(beg, eql),
                                   envblock.substring(eql+1,end));
        }

        theCaseInsensitiveEnvironment = new TreeMap<>(nameComparator);
        theCaseInsensitiveEnvironment.putAll(theEnvironment);
    }

    private ProcessEnvironment() {
        super();
    }

    private ProcessEnvironment(int capacity) {
        super(capacity);
    }

    // Only for use by System.getenv(String)
    static java.lang.String getenv(java.lang.String name) {
        // The original implementation used a native call to _wgetenv,
        // but it turns out that _wgetenv is only consistent with
        // GetEnvironmentStringsW (for non-ASCII) if `wmain' is used
        // instead of `main', even in a process created using
        // CREATE_UNICODE_ENVIRONMENT.  Instead we perform the
        // case-insensitive comparison ourselves.  At least this
        // guarantees that System.getenv().get(String) will be
        // consistent with System.getenv(String).
        return theCaseInsensitiveEnvironment.get(name);
    }

    // Only for use by System.getenv()
    static Map<java.lang.String, java.lang.String> getenv() {
        return theUnmodifiableEnvironment;
    }

    // Only for use by ProcessBuilder.environment()
    @java.lang.SuppressWarnings("unchecked")
    static Map<java.lang.String, java.lang.String> environment() {
        return (Map<java.lang.String, java.lang.String>) theEnvironment.clone();
    }

    // Only for use by ProcessBuilder.environment(String[] envp)
    static Map<java.lang.String, java.lang.String> emptyEnvironment(int capacity) {
        return new ProcessEnvironment(capacity);
    }

    private static native java.lang.String environmentBlock();

    // Only for use by ProcessImpl.start()
    java.lang.String toEnvironmentBlock() {
        // Sort Unicode-case-insensitively by name
        List<Entry<java.lang.String, java.lang.String>> list = new ArrayList<>(entrySet());
        Collections.sort(list, entryComparator);

        java.lang.StringBuilder sb = new java.lang.StringBuilder(size()*30);
        int cmp = -1;

        // Some versions of MSVCRT.DLL require SystemRoot to be set.
        // So, we make sure that it is always set, even if not provided
        // by the caller.
        final java.lang.String SYSTEMROOT = "SystemRoot";

        for (Entry<java.lang.String, java.lang.String> e : list) {
            java.lang.String key = e.getKey();
            java.lang.String value = e.getValue();
            if (cmp < 0 && (cmp = nameComparator.compare(key, SYSTEMROOT)) > 0) {
                // Not set, so add it here
                addToEnvIfSet(sb, SYSTEMROOT);
            }
            addToEnv(sb, key, value);
        }
        if (cmp < 0) {
            // Got to end of list and still not found
            addToEnvIfSet(sb, SYSTEMROOT);
        }
        if (sb.length() == 0) {
            // Environment was empty and SystemRoot not set in parent
            sb.append('\u0000');
        }
        // Block is double NUL terminated
        sb.append('\u0000');
        return sb.toString();
    }

    // add the environment variable to the child, if it exists in parent
    private static void addToEnvIfSet(java.lang.StringBuilder sb, java.lang.String name) {
        java.lang.String s = getenv(name);
        if (s != null)
            addToEnv(sb, name, s);
    }

    private static void addToEnv(java.lang.StringBuilder sb, java.lang.String name, java.lang.String val) {
        sb.append(name).append('=').append(val).append('\u0000');
    }

    static java.lang.String toEnvironmentBlock(Map<java.lang.String, java.lang.String> map) {
        return map == null ? null :
            ((ProcessEnvironment)map).toEnvironmentBlock();
    }
}
