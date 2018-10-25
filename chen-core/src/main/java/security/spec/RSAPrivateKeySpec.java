/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * This class specifies an RSA private key.
 *
 * @author Jan Luehe
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see java.security.spec.KeySpec
 * @see PKCS8EncodedKeySpec
 * @see RSAPublicKeySpec
 * @see RSAPrivateCrtKeySpec
 */

public class RSAPrivateKeySpec implements KeySpec {

    private BigInteger modulus;
    private BigInteger privateExponent;

    /**
     * Creates a new RSAPrivateKeySpec.
     *
     * @param modulus the modulus
     * @param privateExponent the private exponent
     */
    public RSAPrivateKeySpec(BigInteger modulus, BigInteger privateExponent) {
        this.modulus = modulus;
        this.privateExponent = privateExponent;
    }

    /**
     * Returns the modulus.
     *
     * @return the modulus
     */
    public BigInteger getModulus() {
        return this.modulus;
    }

    /**
     * Returns the private exponent.
     *
     * @return the private exponent
     */
    public BigInteger getPrivateExponent() {
        return this.privateExponent;
    }
}
