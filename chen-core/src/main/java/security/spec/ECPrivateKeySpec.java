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
package security.spec;

import java.math.BigInteger;
import java.security.spec.ECParameterSpec;
import java.security.spec.KeySpec;

/**
 * This immutable class specifies an elliptic curve private key with
 * its associated parameters.
 *
 * @see java.security.spec.KeySpec
 * @see java.security.spec.ECParameterSpec
 *
 * @author Valerie Peng
 *
 * @since 1.5
 */
public class ECPrivateKeySpec implements KeySpec {

    private BigInteger s;
    private java.security.spec.ECParameterSpec params;

    /**
     * Creates a new ECPrivateKeySpec with the specified
     * parameter values.
     * @param s the private value.
     * @param params the associated elliptic curve domain
     * parameters.
     * @exception NullPointerException if {@code s}
     * or {@code params} is null.
     */
    public ECPrivateKeySpec(BigInteger s, java.security.spec.ECParameterSpec params) {
        if (s == null) {
            throw new NullPointerException("s is null");
        }
        if (params == null) {
            throw new NullPointerException("params is null");
        }
        this.s = s;
        this.params = params;
    }

    /**
     * Returns the private value S.
     * @return the private value S.
     */
    public BigInteger getS() {
        return s;
    }

    /**
     * Returns the associated elliptic curve domain
     * parameters.
     * @return the EC domain parameters.
     */
    public ECParameterSpec getParams() {
        return params;
    }
}
