/*
 * Scripty Programming Language
 * Copyright (C) 2010-2012 Bruno Ranschaert, S.D.I.-Consulting BVBA
 * http://www.sdi-consulting.be
 * mailto://info@sdi-consulting.be
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.sdicons.scripty.spec.map;

import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.parser.IEval;
import com.sdicons.scripty.annot.ScriptyBindingParam;
import com.sdicons.scripty.annot.ScriptyParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class ArgMappingBuilderUtil
{
    public static ArgListMapping buildArgMapping(Method aMethod, Map<String, IArgMapping> aMappings)
    throws ArgMappingException
    {
        Class<?>[] lParamTypes = aMethod.getParameterTypes();
        Annotation[][] lParamAnnotations = aMethod.getParameterAnnotations();
        
        ArgListMapping lArgListMapping = new ArgListMapping();
        
        for(int i = 0; i < lParamTypes.length; i++)
        {
            IArgMapping lMapping = buildMapping(lParamTypes[i], lParamAnnotations[i], aMappings);
            lArgListMapping.addArgMapping(lMapping);
        }
        return lArgListMapping;
    }
    
    private static IArgMapping buildMapping(Class<?> aParamClass, Annotation[] aParamAnnotations, Map<String, IArgMapping> aMappings)
    throws ArgMappingException
    {
        if(aParamAnnotations.length <= 0)
        {
            // If there are not parameter annotations, we check if
            // the parameter types is one of the well known types.

            if(aParamClass.isAssignableFrom(IEval.class))
            {
                return new EvalMapping();
            }
            else if(aParamClass.isAssignableFrom(IContext.class))
            {
                return new ContextMapping();
            }
            else if(aParamClass.isArray() && aParamClass.getComponentType().isAssignableFrom(Object.class))
            {
                return new CompleteMapping();
            }
        }
        else
        {
            ScriptyParam lScriptyParamAnnot = null;
            ScriptyBindingParam lScriptyBindingAnnot = null;

            for(Annotation lAnnot: aParamAnnotations)
            {
                if(lAnnot instanceof ScriptyParam) lScriptyParamAnnot = (ScriptyParam) lAnnot;
                else if(lAnnot instanceof ScriptyBindingParam) lScriptyBindingAnnot = (ScriptyBindingParam) lAnnot;
            }
            
            if(lScriptyParamAnnot != null && lScriptyBindingAnnot != null)
            {
                throw new ArgMappingException("... there can only be one scripty param annotation ...");
            }
            else if (lScriptyParamAnnot != null)
            {
                String lParamName = lScriptyParamAnnot.value();
                if(aMappings != null && aMappings.containsKey(lParamName)) return aMappings.get(lParamName);
                else throw new ArgMappingException("... arg name not found ...");
            }
            else if(lScriptyBindingAnnot != null)
            {
                String lBindingName = lScriptyBindingAnnot.value();
                boolean lExcIfNull = lScriptyBindingAnnot.unboundException();
                return new BindingMapping(lBindingName, lExcIfNull);
            }
        }
        
        // Default is null value.
        return new NullMapping();
    }
}
