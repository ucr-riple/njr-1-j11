/*
 * Core.java
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
package sandbox.oldversions.net.jcores.v1;

import java.util.Collection;
import java.util.List;

import sandbox.oldversions.net.jcores.v1.cores.CoreClass;
import sandbox.oldversions.net.jcores.v1.cores.CoreCollection;
import sandbox.oldversions.net.jcores.v1.cores.CoreList;
import sandbox.oldversions.net.jcores.v1.cores.CoreObject;
import sandbox.oldversions.net.jcores.v1.cores.CoreString;


/**
 * @author Ralf Biedert
 *
 */
public class Core {
    /** The core */
    public final static Core $ = new Core(); 
    
    /**
     * @param <T> 
     * @param clazz
     * @return .
     */
    public static <T> CoreClass<T> $(Class<T> clazz) {
        return null;
    }
    
    public static <T extends String> CoreString $(T object) {
        return null;
    }

    public static <T extends Object> CoreObject<T> $(T object) {
        return null;
    }
    
    public static <X, T extends Collection<X>> CoreCollection<X> $(T object) {
        return null;
    }
    
    
    public static <X, T extends List<X>> CoreList<X> $(T object) {
        return null;
    }
    
    public static CoreList<Object> $(Object ... object) {
        return null;
    }
    
    public static void extend() {
        
    }
}
