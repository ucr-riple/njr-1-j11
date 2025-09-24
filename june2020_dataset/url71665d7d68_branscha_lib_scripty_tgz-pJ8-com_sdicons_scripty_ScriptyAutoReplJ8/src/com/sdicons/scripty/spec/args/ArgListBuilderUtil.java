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

package com.sdicons.scripty.spec.args;

import com.sdicons.scripty.parser.CommandException;
import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.ExtensionException;
import com.sdicons.scripty.annot.ScriptyArg;
import com.sdicons.scripty.annot.ScriptyCommand;
import com.sdicons.scripty.annot.ScriptyStdArgList;
import com.sdicons.scripty.annot.ScriptyVarArgList;
import com.sdicons.scripty.repl.ReplEngine;
import com.sdicons.scripty.repl.ReplEngineException;
import com.sdicons.scripty.spec.map.IArgMapping;
import com.sdicons.scripty.spec.map.IndexedMapping;
import com.sdicons.scripty.spec.map.PartialMapping;
import com.sdicons.scripty.spec.type.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgListBuilderUtil
{
    private static ReplEngine typeProcessor = new ReplEngine();
    
    public static class TypeCommands
    {
        private static ITypeSpec SPEC_TYPE = new InstanceType(TypeTuple.class, false);
        
        private static TypeTuple INTEGER_TUPLE = new TypeTuple( new IntegerType(), null);
        private static TypeTuple BIGDECIMAL_TUPLE = new TypeTuple(new BigDecimalType(), null);
        private static TypeTuple BIGINTEGER_TUPLE = new TypeTuple(new BigIntegerType(), null);
        private static TypeTuple BOOLEAN_TUPLE = new TypeTuple(new BooleanType(), null);
        private static TypeTuple BYTE_TUPLE = new TypeTuple(new ByteType(), null);
        private static TypeTuple DOUBLE_TUPLE = new TypeTuple(new DoubleType(), null);
        private static TypeTuple FLOAT_TUPLE = new TypeTuple(new FloatType(), null);
        private static TypeTuple LONG_TUPLE = new TypeTuple(new LongType(), null);
        private static TypeTuple SHORT_TUPLE = new TypeTuple(new ShortType(), null);
        private static TypeTuple STRING_TUPLE = new TypeTuple(new StringType(), null);
        
        private static NamedArg NAMED_MINLEN = new NamedArg("minLength", new IntegerRangeType(-1, Integer.MAX_VALUE), -1, true);
        private static NamedArg NAMED_MAXLEN = new NamedArg("maxLength", new IntegerRangeType(-1, Integer.MAX_VALUE), -1, true);
        private static NamedArg NAMED_MIN = new NamedArg("min", new IntegerRangeType(Integer.MIN_VALUE, Integer.MAX_VALUE), Integer.MIN_VALUE, true);
        private static NamedArg NAMED_MAX = new NamedArg("max", new IntegerRangeType(Integer.MIN_VALUE, Integer.MAX_VALUE), Integer.MAX_VALUE, true);
        private static NamedArg NAMED_NULLALLOWED = new NamedArg("nullAllowed", new BooleanType(), false, true);

        private static FixedArg FIXED_TYPE = new FixedArg(SPEC_TYPE);
        private static FixedArg FIXED_CLASSNAME = new FixedArg(new StringType());
        private static VarArg VAR_STRING = new VarArg(new StringType());

        private static StdArgList NOARGS = new StdArgList(new FixedArg[]{}, new OptionalArg[]{}, new NamedArg[]{});

        @ScriptyCommand(name="BigDecimal")
        public static TypeTuple bigDecimalType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return BIGDECIMAL_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        @ScriptyCommand(name="Integer")
        public static TypeTuple integerType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return INTEGER_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }
        
        @ScriptyCommand(name="BigInteger")
        public static TypeTuple bigIntegerType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return BIGINTEGER_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }
        
        @ScriptyCommand(name="Boolean")
        public static TypeTuple booleanType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return BOOLEAN_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }
        
        @ScriptyCommand(name="Byte")
        public static TypeTuple byteType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return BYTE_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }
        
        // type="ListOf (Integer) minLength=0 maxLength=20"
        private static StdArgList LISTOF_ARGLIST = new StdArgList(new FixedArg[]{FIXED_TYPE}, new OptionalArg[]{}, new NamedArg[]{NAMED_MINLEN, NAMED_MAXLEN});
        
        @ScriptyCommand(name="ListOf")
        public static TypeTuple listOfType(IContext aCtx, Object[] aArgs)
        throws CommandException
        {
            try
            {
                aArgs = LISTOF_ARGLIST.guard(aArgs, aCtx);

                TypeTuple lTuple = (TypeTuple) aArgs[1];
                Object lTypeCandidate = lTuple.getX();
                Map<String, IArgMapping> lMappings = lTuple.getY();

                if(lTypeCandidate == null || !(lTypeCandidate instanceof ITypeSpec))
                {
                    throw new CommandException("Invalid type expression ListOf(???) for the elements of the list.");
                }

                ITypeSpec lSpec = (ITypeSpec) lTypeCandidate;
                int lMin = (Integer) aArgs[2];
                int lMax = (Integer) aArgs[3];

                return new TypeTuple(new CheckedListType(lSpec, lMin, lMax), lMappings);
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        @ScriptyCommand(name="Double")
        public static TypeTuple doubleType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return DOUBLE_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        @ScriptyCommand(name="Float")
        public static TypeTuple floatType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return FLOAT_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        // type="InstanceOrBinding (Integer)"
        private static StdArgList INSTANCEORBINDING_ARGLIST = new StdArgList(new FixedArg[]{FIXED_TYPE}, new OptionalArg[]{}, new NamedArg[]{});

        @ScriptyCommand(name="InstanceOrBinding")
        public static TypeTuple instanceOrBindingType(IContext aCtx, Object[] aArgs)
        throws CommandException
        {
            try
            {
                aArgs = INSTANCEORBINDING_ARGLIST.guard(aArgs, aCtx);

                TypeTuple lTuple = (TypeTuple) aArgs[1];
                Object lTypeCandidate = lTuple.getX();
                Map<String, IArgMapping> lMappings = lTuple.getY();

                if(lTypeCandidate == null || !(lTypeCandidate instanceof ITypeSpec))
                {
                    throw new CommandException("Invalid type expression InstanceOrBinding(???) for the instance type.");
                }

                ITypeSpec lSpec = (ITypeSpec) lTypeCandidate;

                return new TypeTuple(new InstanceOrBinding(lSpec), lMappings);
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        // type="Instance com.sdicons.MyClass nullAllowed=true"
        private static StdArgList INSTANCE_ARGLIST = new StdArgList(new FixedArg[]{FIXED_CLASSNAME}, new OptionalArg[]{}, new NamedArg[]{NAMED_NULLALLOWED});

        @ScriptyCommand(name="Instance")
        public static TypeTuple instanceType(IContext aCtx, Object[] aArgs)
        throws CommandException
        {
            try
            {
                aArgs = INSTANCE_ARGLIST.guard(aArgs, aCtx);
                String lClassName = (String) aArgs[1];
                boolean lNullAllowed = (Boolean) aArgs[2];

                try
                {
                    Class lClass = Class.forName(lClassName);
                    return new TypeTuple(new InstanceType(lClass, lNullAllowed), null);                    
                } 
                catch (ClassNotFoundException e)
                {
                    String lMsg = String.format("Could not find class '%s' for type Instance [CLASS].", lClassName);
                    throw new CommandException(lMsg);
                }
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        // type="Any nullAllowed=true"
        private static StdArgList ANY_ARGLIST = new StdArgList(new FixedArg[]{}, new OptionalArg[]{}, new NamedArg[]{NAMED_NULLALLOWED});

        @ScriptyCommand(name="Any")
        public static TypeTuple anyType(IContext aCtx, Object[] aArgs)
        throws CommandException
        {
            try
            {
                aArgs = ANY_ARGLIST.guard(aArgs, aCtx);
                boolean lNullAllowed = (Boolean) aArgs[1];
                return new TypeTuple(new AnyType(lNullAllowed), null);
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        // type="IngegerRange min=0 max=10"
        private static StdArgList INTEGERRANGE_ARGLIST = new StdArgList(new FixedArg[]{}, new OptionalArg[]{}, new NamedArg[]{NAMED_MIN, NAMED_MAX});

        @ScriptyCommand(name="IntegerRange")
        public static TypeTuple integerRangeType(IContext aCtx, Object[] aArgs)
        throws CommandException
        {
            try
            {
                aArgs = INTEGERRANGE_ARGLIST.guard(aArgs, aCtx);
                int lMin = (Integer) aArgs[1];
                int lMax = (Integer) aArgs[2];
                return new TypeTuple(new IntegerRangeType(lMin, lMax), null);
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        @ScriptyCommand(name="Long")
        public static TypeTuple longType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return LONG_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        @ScriptyCommand(name="Short")
        public static TypeTuple shortType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return SHORT_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        @ScriptyCommand(name="String")
        public static TypeTuple stringType(Object[] aArgs, IContext aCtx)
        throws CommandException
        {
            try
            {
                NOARGS.guard(aArgs, aCtx);
                return STRING_TUPLE;
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        // type="OneOf (Integer) (Long) (BigDecimal)"
        private static VarArgList ONEOF_ARGLIST = new VarArgList(new FixedArg[]{}, new VarArg(SPEC_TYPE), 2, -1, new NamedArg[]{});

        @ScriptyCommand(name="OneOf")
        public static TypeTuple oneOfType(IContext aCtx, Object[] aArgs)
        throws CommandException
        {
            try
            {
                ONEOF_ARGLIST.guard(aArgs, aCtx);
                Map<String, IArgMapping> lMappings = new HashMap<String, IArgMapping>();
                ITypeSpec[] lSpecs = new ITypeSpec[aArgs.length-1];
                for(int i = 1; i < aArgs.length; i++)
                {
                    TypeTuple lTuple = (TypeTuple) aArgs[i];
                    lSpecs[i-1] = lTuple.getX();
                    if(lTuple.getY() != null) lMappings.putAll(lTuple.getY());
                }
                return new TypeTuple(new OrType(lSpecs), lMappings);
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        // type="Custom com.sdicons.MySpec"
        private static StdArgList CUSTOM_ARGLIST = new StdArgList(new FixedArg[]{FIXED_CLASSNAME}, new OptionalArg[]{}, new NamedArg[]{});

        @ScriptyCommand(name="Custom")
        public static TypeTuple customSpec(IContext aCtx, Object[] aArgs)
        throws CommandException
        {
            try
            {
                aArgs = CUSTOM_ARGLIST.guard(aArgs, aCtx);
                String lClassName = (String) aArgs[1];

                try
                {
                    Class lClass = Class.forName(lClassName);
                    if(ITypeSpec.class.isAssignableFrom(lClass))
                    {
                        ITypeSpec lSpec = (ITypeSpec) lClass.newInstance();
                        return new TypeTuple(lSpec, null);
                    }
                    else
                    {
                        String lMsg = String.format("The class '%s' does not seem to implement ITypeSpec.", lClassName);
                        throw new CommandException(lMsg);
                    }
                }
                catch (ClassNotFoundException e)
                {
                    String lMsg = String.format("Could not find class '%s'.", lClassName);
                    throw new CommandException(lMsg);
                }
                catch (InstantiationException e)
                {
                    String lMsg = String.format("Could not instantiate the custom typespec '%s'.", lClassName);
                    throw new CommandException(lMsg);
                }
                catch (IllegalAccessException e)
                {
                    String lMsg = String.format("Could not instantiate the custom typespec '%s'.", lClassName);
                    throw new CommandException(lMsg);
                }
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }

        // A series of string constants.
        // type="Enum Uno Duo Tres"
        private static VarArgList ENUM_ARGLIST = new VarArgList(new FixedArg[]{}, VAR_STRING, 1, -1, new NamedArg[]{});
        
        @ScriptyCommand(name="Enum")
        public static TypeTuple enumType(IContext aCtx, Object[] aArgs)
        throws CommandException
        {
            try
            {
                aArgs = ENUM_ARGLIST.guard(aArgs, aCtx);
                List<String> lValues = new ArrayList<String>(aArgs.length - 1);
                for (int i = 1; i < aArgs.length; i++)
                {
                    lValues.add((String) aArgs[i]);
                }
                return new TypeTuple(new EnumType(lValues), null);
            }
            catch (ArgSpecException e)
            {
                throw new CommandException(e.getMessage());
            }
        }
    }
    
    static
    {
        try
        {
            typeProcessor.addLibraryClasses(TypeCommands.class);
        } 
        catch (ExtensionException e)
        {
            throw new Error("Internal Scripty error, could not initialize the internal type system.");
        }
    }
    
    public static class Tuple<X, Y>
    {
        private X x;
        private Y y;

        public Tuple(X x, Y y)
        {
            this.x = x;
            this.y = y;
        }

        public X getX()
        {
            return this.x;
        }

        public Y getY()
        {
            return this.y;
        }
    }
    
    private static class TypeTuple extends Tuple<ITypeSpec, Map<String, IArgMapping>>
    {
        private TypeTuple(ITypeSpec x, Map<String, IArgMapping> y)
        {
            super(x, y);
        }
    }

    public static class ArgListTuple extends Tuple<IArgList, Map<String, IArgMapping>>
    {
        public ArgListTuple(IArgList x, Map<String, IArgMapping> y)
        {
            super(x, y);
        }
    }
    
    public static ArgListTuple buildArgList(ScriptyStdArgList aStdArgList)
    throws ArgSpecException
    {
        // We will collect the mappings we encounter in this map.
        Map<String, IArgMapping> lMappings = new HashMap<String, IArgMapping>();
        
        ScriptyArg[] lFixedArgs = aStdArgList.fixed();
        FixedArg[] lFixedSpecs = new FixedArg[lFixedArgs.length];
        
        ScriptyArg[] lOptionalArgs = aStdArgList.optional();
        OptionalArg[] lOptionalSpecs = new OptionalArg[lOptionalArgs.length];
        
        ScriptyArg[] lNamedArgs = aStdArgList.named();
        NamedArg[] lNamedSpecs = new NamedArg[lNamedArgs.length];

        int i = 0;
        
        for (ScriptyArg lArg : lFixedArgs)
        {
            String lArgName = lArg.name();
            String lArgType = lArg.type();
            // Default value is ignored for fixed args.
            // Optional flag is ignored for fixed args.
            
            try
            {
                TypeTuple lTypeInfo = (TypeTuple) typeProcessor.startNonInteractive(lArgType);
                ITypeSpec lTypeSpec = lTypeInfo.getX();
                Map<String, IArgMapping> lTypeMappings = lTypeInfo.getY();
                
                lFixedSpecs[i] = new FixedArg(lTypeSpec);
                if(lTypeMappings != null) lMappings.putAll(lTypeMappings);
                // Offset with 1, the mappings should skip element 0 which is the name of the command.
                lMappings.put(lArgName, new IndexedMapping(i +1));

                i++;
            } 
            catch (ReplEngineException e)
            {
                throw new ArgSpecException(String.format(String.format("Badly formed type expression '%s' encountered.\n%s", lArgType, e.getMessage())));
            }
        }
        
        int j = 0;
        
        for(ScriptyArg lArg: lOptionalArgs)
        {
            String lArgName = lArg.name();
            String lArgType = lArg.type();
            String lArgValue = lArg.value();
            // Optional flag is not needed, optional args are always optional.

            if("{null}".equals(lArgValue)) lArgValue = null;
            
            try
            {
                TypeTuple lTypeInfo = (TypeTuple) typeProcessor.startNonInteractive(lArgType);
                ITypeSpec lTypeSpec = lTypeInfo.getX();
                Map<String, IArgMapping> lTypeMappings = lTypeInfo.getY();

                lOptionalSpecs[j] = new OptionalArg(lTypeSpec, lArgValue);
                if(lTypeMappings != null) lMappings.putAll(lTypeMappings);
                // Offset with 1, the mappings should skip element 0 which is the name of the command.
                lMappings.put(lArgName, new IndexedMapping(i + 1));

                i++;
                j++;
            }
            catch (ReplEngineException e)
            {
                throw new ArgSpecException(String.format(String.format("Badly formed type expression '%s' encountered.\n%s", lArgType, e.getMessage())));
            }
        }

        int k = 0;

        for(ScriptyArg lArg : lNamedArgs)
        {
            String lArgName = lArg.name();
            String lArgType = lArg.type();
            String lArgValue = lArg.value();
            boolean lArgOptional = lArg.optional();
            
            if("{null}".equals(lArgValue)) lArgValue = null;

            try
            {
                TypeTuple lTypeInfo = (TypeTuple) typeProcessor.startNonInteractive(lArgType);
                ITypeSpec lTypeSpec = lTypeInfo.getX();
                Map<String, IArgMapping> lTypeMappings = lTypeInfo.getY();

                lNamedSpecs[k] = new NamedArg(lArgName, lTypeSpec, lArgValue, lArgOptional);
                if(lTypeMappings != null) lMappings.putAll(lTypeMappings);
                // Offset with 1, the mappings should skip element 0 which is the name of the command.
                lMappings.put(lArgName, new IndexedMapping(i + 1));

                i++;
                k++;
            }
            catch (ReplEngineException e)
            {
                throw new ArgSpecException(String.format(String.format("Badly formed type expression '%s' encountered.\n%s", lArgType, e.getMessage())));
            }
        }

        IArgList lStdArgList = new StdArgList(lFixedSpecs, lOptionalSpecs, lNamedSpecs);
        return new ArgListTuple(lStdArgList, lMappings);
    }

    public static ArgListTuple buildArgList(ScriptyVarArgList aVarArgList)
    throws ArgSpecException
    {
        // We will collect the mappings we encounter in this map.
        Map<String, IArgMapping> lMappings = new HashMap<String, IArgMapping>();

        ScriptyArg[] lFixedArgs = aVarArgList.fixed();
        FixedArg[] lFixedSpecs = new FixedArg[lFixedArgs.length];

        ScriptyArg[] lNamedArgs = aVarArgList.named();
        NamedArg[] lNamedSpecs = new NamedArg[lNamedArgs.length];
        
        ScriptyArg lVarArg = aVarArgList.vararg();
        VarArg lVarArgSpec;
        int lVarArgMinLength = aVarArgList.minLength();
        int lVarArgMaxLength = aVarArgList.maxLength();

        int i = 0;

        for (ScriptyArg lArg : lFixedArgs)
        {
            String lArgName = lArg.name();
            String lArgType = lArg.type();
            // Default value is ignored for fixed args.
            // Optional flag is ignored for fixed args.

            try
            {
                TypeTuple lTypeInfo = (TypeTuple) typeProcessor.startNonInteractive(lArgType);
                ITypeSpec lTypeSpec = lTypeInfo.getX();
                Map<String, IArgMapping> lTypeMappings = lTypeInfo.getY();

                lFixedSpecs[i] = new FixedArg(lTypeSpec);
                if(lTypeMappings != null) lMappings.putAll(lTypeMappings);
                // Offset with 1, the mappings should skip element 0 which is the name of the command.
                lMappings.put(lArgName, new IndexedMapping(i +1));

                i++;
            }
            catch (ReplEngineException e)
            {
                throw new ArgSpecException(String.format(String.format("Badly formed type expression '%s' encountered.\n%s", lArgType, e.getMessage())));
            }
        }

        int k = 0;

        for(ScriptyArg lArg : lNamedArgs)
        {
            String lArgName = lArg.name();
            String lArgType = lArg.type();
            String lArgValue = lArg.value();
            boolean lArgOptional = lArg.optional();

            try
            {
                TypeTuple lTypeInfo = (TypeTuple) typeProcessor.startNonInteractive(lArgType);
                ITypeSpec lTypeSpec = lTypeInfo.getX();
                Map<String, IArgMapping> lTypeMappings = lTypeInfo.getY();

                lNamedSpecs[k] = new NamedArg(lArgName, lTypeSpec, lArgValue, lArgOptional);
                if(lTypeMappings != null) lMappings.putAll(lTypeMappings);
                // Offset with 1, the mappings should skip element 0 which is the name of the command.
                lMappings.put(lArgName, new IndexedMapping(i + 1));

                i++;
                k++;
            }
            catch (ReplEngineException e)
            {
                throw new ArgSpecException(String.format(String.format("Badly formed type expression '%s' encountered.\n%s", lArgType, e.getMessage())));
            }
        }
        

        {
            String lArgName = lVarArg.name();
            String lArgType = lVarArg.type();

            try
            {
                TypeTuple lTypeInfo = (TypeTuple) typeProcessor.startNonInteractive(lArgType);
                ITypeSpec lTypeSpec = lTypeInfo.getX();
                Map<String, IArgMapping> lTypeMappings = lTypeInfo.getY();

                lVarArgSpec = new VarArg(lTypeSpec);
                if(lTypeMappings != null) lMappings.putAll(lTypeMappings);
                // Offset with 1, the mappings should skip element 0 which is the name of the command.
                lMappings.put(lArgName, new PartialMapping(i + 1, -1));
            }
            catch (ReplEngineException e)
            {
                throw new ArgSpecException(String.format(String.format("Badly formed type expression '%s' encountered.\n%s", lArgType, e.getMessage())));
            }

        }

        IArgList lStdArgList = new VarArgList(lFixedSpecs, lVarArgSpec, lVarArgMinLength, lVarArgMaxLength, lNamedSpecs);
        return new ArgListTuple(lStdArgList, lMappings);
    }
}

