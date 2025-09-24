/*
 * Copyright 2015-2016 the original author or authors
 *
 * This software is licensed under the Apache License, Version 2.0,
 * the GNU Lesser General Public License version 2 or later ("LGPL")
 * and the WTFPL.
 * You may choose either license to govern your use of this software only
 * upon the condition that you accept all of the terms of either
 * the Apache License 2.0, the LGPL 2.1+ or the WTFPL.
 */
package de.measite.minidns.record;

import de.measite.minidns.DNSSECConstants.DigestAlgorithm;
import de.measite.minidns.DNSSECConstants.SignatureAlgorithm;
import de.measite.minidns.Record;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * DLV record payload.
 *
 * According to RFC4431, DLV has exactly the same format as DS records.
 */
public class DLV extends DS {
    public static DLV parse (DataInputStream dis, int length) throws IOException {
        DS ds = DS.parse(dis, length);
        return new DLV(ds.keyTag, ds.algorithm, ds.digestType, ds.digest);
    }

    public DLV(int keyTag, byte algorithm, byte digestType, byte[] digest) {
        super(keyTag, algorithm, digestType, digest);
    }

    public DLV(int keyTag, SignatureAlgorithm algorithm, DigestAlgorithm digestType, byte[] digest) {
        super(keyTag, algorithm, digestType, digest);
    }

    @Override
    public Record.TYPE getType() {
        return Record.TYPE.DLV;
    }
}
