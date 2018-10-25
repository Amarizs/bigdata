/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

package security;

import java.security.Permission;
import java.security.PermissionCollection;
import java.security.UnresolvedPermission;
import java.util.*;
import java.io.ObjectStreamField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * A UnresolvedPermissionCollection stores a collection
 * of UnresolvedPermission permissions.
 *
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.UnresolvedPermission
 *
 *
 * @author Roland Schemers
 *
 * @serial include
 */

final class UnresolvedPermissionCollection
extends PermissionCollection
implements java.io.Serializable
{
    /**
     * Key is permission type, value is a list of the UnresolvedPermissions
     * of the same type.
     * Not serialized; see serialization section at end of class.
     */
    private transient Map<String, List<java.security.UnresolvedPermission>> perms;

    /**
     * Create an empty UnresolvedPermissionCollection object.
     *
     */
    public UnresolvedPermissionCollection() {
        perms = new HashMap<String, List<java.security.UnresolvedPermission>>(11);
    }

    /**
     * Adds a permission to this UnresolvedPermissionCollection.
     * The key for the hash is the unresolved permission's type (class) name.
     *
     * @param permission the Permission object to add.
     */

    public void add(java.security.Permission permission)
    {
        if (! (permission instanceof java.security.UnresolvedPermission))
            throw new IllegalArgumentException("invalid permission: "+
                                               permission);
        java.security.UnresolvedPermission up = (java.security.UnresolvedPermission) permission;

        List<java.security.UnresolvedPermission> v;
        synchronized (this) {
            v = perms.get(up.getName());
            if (v == null) {
                v = new ArrayList<java.security.UnresolvedPermission>();
                perms.put(up.getName(), v);
            }
        }
        synchronized (v) {
            v.add(up);
        }
    }

    /**
     * get any unresolved permissions of the same type as p,
     * and return the List containing them.
     */
    List<java.security.UnresolvedPermission> getUnresolvedPermissions(java.security.Permission p) {
        synchronized (this) {
            return perms.get(p.getClass().getName());
        }
    }

    /**
     * always returns false for unresolved permissions
     *
     */
    public boolean implies(java.security.Permission permission)
    {
        return false;
    }

    /**
     * Returns an enumeration of all the UnresolvedPermission lists in the
     * container.
     *
     * @return an enumeration of all the UnresolvedPermission objects.
     */

    public Enumeration<java.security.Permission> elements() {
        List<Permission> results =
            new ArrayList<>(); // where results are stored

        // Get iterator of Map values (which are lists of permissions)
        synchronized (this) {
            for (List<java.security.UnresolvedPermission> l : perms.values()) {
                synchronized (l) {
                    results.addAll(l);
                }
            }
        }

        return Collections.enumeration(results);
    }

    private static final long serialVersionUID = -7176153071733132400L;

    // Need to maintain serialization interoperability with earlier releases,
    // which had the serializable field:
    // private Hashtable permissions; // keyed on type

    /**
     * @serialField permissions java.util.Hashtable
     *     A table of the UnresolvedPermissions keyed on type, value is Vector
     *     of permissions
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("permissions", Hashtable.class),
    };

    /**
     * @serialData Default field.
     */
    /*
     * Writes the contents of the perms field out as a Hashtable
     * in which the values are Vectors for
     * serialization compatibility with earlier releases.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Don't call out.defaultWriteObject()

        // Copy perms into a Hashtable
        Hashtable<String, Vector<java.security.UnresolvedPermission>> permissions =
            new Hashtable<>(perms.size()*2);

        // Convert each entry (List) into a Vector
        synchronized (this) {
            Set<Map.Entry<String, List<java.security.UnresolvedPermission>>> set = perms.entrySet();
            for (Map.Entry<String, List<java.security.UnresolvedPermission>> e : set) {
                // Convert list into Vector
                List<java.security.UnresolvedPermission> list = e.getValue();
                Vector<java.security.UnresolvedPermission> vec = new Vector<>(list.size());
                synchronized (list) {
                    vec.addAll(list);
                }

                // Add to Hashtable being serialized
                permissions.put(e.getKey(), vec);
            }
        }

        // Write out serializable fields
        ObjectOutputStream.PutField pfields = out.putFields();
        pfields.put("permissions", permissions);
        out.writeFields();
    }

    /*
     * Reads in a Hashtable in which the values are Vectors of
     * UnresolvedPermissions and saves them in the perms field.
     */
    private void readObject(ObjectInputStream in) throws IOException,
    ClassNotFoundException {
        // Don't call defaultReadObject()

        // Read in serialized fields
        ObjectInputStream.GetField gfields = in.readFields();

        // Get permissions
        @SuppressWarnings("unchecked")
        // writeObject writes a Hashtable<String, Vector<UnresolvedPermission>>
        // for the permissions key, so this cast is safe, unless the data is corrupt.
        Hashtable<String, Vector<java.security.UnresolvedPermission>> permissions =
                (Hashtable<String, Vector<java.security.UnresolvedPermission>>)
                gfields.get("permissions", null);
        perms = new HashMap<String, List<java.security.UnresolvedPermission>>(permissions.size()*2);

        // Convert each entry (Vector) into a List
        Set<Map.Entry<String, Vector<java.security.UnresolvedPermission>>> set = permissions.entrySet();
        for (Map.Entry<String, Vector<java.security.UnresolvedPermission>> e : set) {
            // Convert Vector into ArrayList
            Vector<java.security.UnresolvedPermission> vec = e.getValue();
            List<UnresolvedPermission> list = new ArrayList<>(vec.size());
            list.addAll(vec);

            // Add to Hashtable being serialized
            perms.put(e.getKey(), list);
        }
    }
}
