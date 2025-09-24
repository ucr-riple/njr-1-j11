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

package com.sdicons.scripty.parser;

import com.sdicons.scripty.cmdlib.CmdUtil;
import com.sdicons.scripty.parser.CommandException;
import com.sdicons.scripty.parser.ICommand;
import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.parser.IEval;
import com.sdicons.scripty.spec.args.ArgSpecException;
import com.sdicons.scripty.spec.args.IArgList;
import com.sdicons.scripty.spec.map.ArgListMapping;
import com.sdicons.scripty.spec.map.ArgMappingException;
import com.sdicons.scripty.spec.map.IResultMapping;
import com.sdicons.scripty.spec.map.ResultMappingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodCommand
implements ICommand
{
    private IArgList argList;
    private Object instance;
    private ArgListMapping argMapping;
    private IResultMapping resultMapping;
    private Method method;

    public MethodCommand(Method aMethod)
    {
        argList = null;
        method = aMethod;
        instance = null;
        argMapping = null;
        resultMapping = null;
    }

    public MethodCommand(Object aInstance, Method aMethod, IArgList aArgList, ArgListMapping aArgListMapping, IResultMapping aResultMapping)
    {
        argList = aArgList;
        method = aMethod;
        instance = aInstance;
        argMapping = aArgListMapping;
        resultMapping = aResultMapping;
    }

    public Object execute(IEval aEval, IContext aCtx, Object[] aArgs)
    throws CommandException
    {
        try
        {
            Object[] lArgs = aArgs;

            if (argList != null)
            {
                lArgs = argList.guard(aArgs, aCtx);
            }
            
            if(argMapping != null)
            {
                lArgs = argMapping.map(aEval, aCtx, lArgs);
            }
            
            Object lResult = method.invoke(instance, lArgs);

            if(resultMapping != null)
            {
                resultMapping.map(lResult, aCtx);
            }

            return lResult;
        }
        catch (IllegalAccessException e)
        {
            throw new CommandException(String.format("Command '%s' illegal access exception.\n%s", aArgs[0], CmdUtil.concatExceptionMessages(e)));
        }
        catch (InvocationTargetException e)
        {
            // The internal invocation threw an exception, we have to fetch the original exception.
            // We don't add our own message at this level, this handler should be transparent because it does
            // not add extra information.
            Throwable lOrigExc = e.getTargetException();
            throw new CommandException(CmdUtil.concatExceptionMessages(lOrigExc));
        }
        catch (ArgMappingException e)
        {
            throw new CommandException(String.format("Command '%s' parameter mapping exception.\n%s", aArgs[0], CmdUtil.concatExceptionMessages(e)));
        }
        catch (ArgSpecException e)
        {
            final String lMsg = String.format("Command '%s' was invoked with incorrect arguments.\n%s", aArgs[0], CmdUtil.concatExceptionMessages(e));
            throw new CommandException(lMsg);
        }
        catch(IllegalArgumentException e)
        {
            final Class lClass = method.getDeclaringClass();
            final String lMsg = String.format("Command '%s' internal error. One of the parameters in method '%s' in class '%s' has a wrong type.", aArgs[0], method.getName(), lClass.getName());
            throw new CommandException(lMsg);
        }
        catch (ResultMappingException e)
        {
            throw new CommandException(String.format("Command '%s' the result could not be bound to the context.\n%s", aArgs[0], CmdUtil.concatExceptionMessages(e)));
        }
    }
}
