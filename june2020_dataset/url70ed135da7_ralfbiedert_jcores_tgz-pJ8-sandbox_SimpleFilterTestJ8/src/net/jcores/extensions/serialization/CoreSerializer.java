/*
 * CoreSerializer.java
 * 
 * Copyright (c) 2010, Ralf Biedert All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of the author nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package net.jcores.extensions.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.jcores.jre.CommonCore;
import net.jcores.jre.cores.CoreObject;
import net.jcores.jre.options.MessageType;

import com.thoughtworks.xstream.XStream;

/**
 * @author Handles serialization
 * @deprecated
 */
@Deprecated
public class CoreSerializer extends CoreObject<Object> {
    /** */
    private static final long serialVersionUID = -5771931497851862694L;

    /**
     * @param supercore
     * @param t
     */
    public CoreSerializer(CommonCore supercore, Object... t) {
        super(supercore, t);
    }

    /**
     * Stores all objects into the given file.
     * 
     * @param destination
     */
    public void store(String destination) {
        try {
            final XStream xstream = new XStream();
            final OutputStream os = new FileOutputStream(destination);
            xstream.toXML(this.adapter, os);
        } catch (FileNotFoundException e) {
            this.commonCore.report(MessageType.EXCEPTION, "File not found " + destination);
        }
    }

    /**
     * @return . 
     */
    public CoreSerializer load() {
        if (size() > 1) {
            this.commonCore.report(MessageType.MISUSE, "We only support loading from one source right now, sorry!");
        }

        final Object source = get(new Object());

        if (!(source instanceof String)) {
            this.commonCore.report(MessageType.MISUSE, "No valid source provided within this core. Must be of type String.");
            return new CoreSerializer(this.commonCore, new Object[0]);
        }

        // Now try to load the file
        try {
            final XStream xstream = new XStream();
            final FileInputStream fis = new FileInputStream((String) source);

            Object deser = xstream.fromXML(fis);

            fis.close();

            return new CoreSerializer(this.commonCore, (Object[]) deser);
        } catch (FileNotFoundException e) {
            this.commonCore.report(MessageType.EXCEPTION, "File not found " + source);
        } catch (IOException e) {
            this.commonCore.report(MessageType.EXCEPTION,"Error closing " + source);
        }

        return new CoreSerializer(this.commonCore, new Object[0]);
    }
}
