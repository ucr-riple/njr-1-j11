/*
 * AbstractInputType.java
 * 
 * Copyright (c) 2011, Ralf Biedert All rights reserved.
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
package net.jcores.script.input;

/**
 * Base class of all input types.
 * 
 * @author Ralf Biedert
 * @since 1.0
 */
public abstract class AbstractInputType {
    /** Hidden constructor */
    protected AbstractInputType() {}
    
    /** The short description. */
    protected String shortDescription = null;

    /** The long description. */
    protected String longDescription = null;
    
    /** The default value */
    protected String defaultValue = null;

    
    /**
     * Sets the description for this input type.
     * 
     * @param shortDesc The short description, should only be one sentence.
     * @param longDesc The long description with detailed explanations.
     * @return This object.
     */
    public AbstractInputType description(String shortDesc, String longDesc) {
        this.shortDescription = shortDesc;
        this.longDescription = longDesc;
        return this;
    }
    
    
    /**
     * Sets the description for this input type.
     * 
     * @param shortDesc The short description, should only be one sentence.
     * @return This object.
     */
    public AbstractInputType description(String shortDesc) {
        return description(shortDesc, null);
    }
    
    
    /**
     * Sets the default for this option, that is being used if the option was not specified.
     * 
     * @param label The default value for this object. 
     * @return This InputAlternatives object.
     */
    public AbstractInputType dfault(String label) {
        this.defaultValue = label;
        return this;
    }
}
