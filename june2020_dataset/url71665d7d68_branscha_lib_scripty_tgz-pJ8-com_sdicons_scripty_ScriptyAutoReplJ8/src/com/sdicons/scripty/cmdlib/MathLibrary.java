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

package com.sdicons.scripty.cmdlib;

import com.sdicons.scripty.parser.CommandException;
import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.spec.type.BigDecimalType;
import com.sdicons.scripty.spec.type.TypeSpecException;
import com.sdicons.scripty.annot.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

@ScriptyNamedArgLists(
        std={
                @ScriptyStdArgList(name="2numbers", fixed={@ScriptyArg(name="arg1", type="BigDecimal"), @ScriptyArg(name="arg2", type="BigDecimal")}),
                @ScriptyStdArgList(name="1object", fixed={@ScriptyArg(name="arg", type="Any")}),
                @ScriptyStdArgList(name="1number", fixed={@ScriptyArg(name="arg", type="BigDecimal")})
        },
        var={
                @ScriptyVarArgList(name="numbers*", fixed={}, named={}, vararg=@ScriptyArg(name="numbers", type="BigDecimal")),
                @ScriptyVarArgList(name="numbers+", fixed={}, named={}, vararg=@ScriptyArg(name="numbers", type="BigDecimal"), minLength = 1)}
)
@ScriptyLibrary(type=ScriptyLibraryType.AUTO)
public class MathLibrary
{
    private MathContext mathCtx = MathContext.DECIMAL64;
    
    @ScriptyCommand(name="+")
    @ScriptyRefArgList(ref = "numbers*")
    public BigDecimal add(@ScriptyParam("numbers") Object[] aNumbers)
    {
        BigDecimal lSum = BigDecimal.ZERO;
        for(Object lDec : aNumbers)
        {
            lSum = lSum.add((BigDecimal) lDec, mathCtx);
        }
        return lSum;
    }

    @ScriptyCommand(name="-")
    @ScriptyRefArgList(ref = "numbers+")
    public BigDecimal sub(@ScriptyParam("numbers") Object[] aNumbers)
    {
        BigDecimal lSum = (BigDecimal) aNumbers[0];
        for(int i = 1; i < aNumbers.length; i++)
        {
            lSum = lSum.subtract((BigDecimal) aNumbers[i], mathCtx);
        }
        return lSum;
    }

    @ScriptyCommand(name="/")
    @ScriptyRefArgList(ref = "numbers+")
    public BigDecimal div(@ScriptyParam("numbers") Object[] aNumbers)
    {
        BigDecimal lResult = (BigDecimal) aNumbers[0];
        for(int i = 1; i < aNumbers.length; i++)
        {
            lResult = lResult.divide((BigDecimal)aNumbers[i],mathCtx);
        }
        return lResult;
    }

    @ScriptyCommand(name="*")
    @ScriptyRefArgList(ref = "numbers+")
    public BigDecimal mult(@ScriptyParam("numbers") Object[] aNumbers)
    {
        BigDecimal lResult = (BigDecimal) aNumbers[0];
        for(int i = 1; i < aNumbers.length; i++)
        {
            lResult = lResult.multiply((BigDecimal) aNumbers[i], mathCtx);
        }
        return lResult;
    }

    @ScriptyCommand(name="^")
    @ScriptyRefArgList(ref="2numbers")
    public BigDecimal pow(@ScriptyParam("arg1") BigDecimal aNr, @ScriptyParam("arg2") BigDecimal aPow)
    throws CommandException
    {
        try
        {
            return aNr.pow(aPow.intValueExact(),mathCtx);
        }
        catch (ArithmeticException e)
        {
            throw new CommandException("Expected an integer as second argument, the power has to be an integer.");
        }
    }

    @ScriptyCommand(name="rem")
    @ScriptyRefArgList(ref="2numbers")
    public BigDecimal rem(@ScriptyParam("arg1") BigDecimal aNr, @ScriptyParam("arg2") BigDecimal aRem)
    throws CommandException
    {
        return aNr.remainder(aRem, mathCtx);
    }

    @ScriptyCommand(name="number?")
    @ScriptyRefArgList(ref="1object")
    public boolean isNumber(@ScriptyParam("arg") Object aArg, IContext aCtx)
    {
        try
        {
            new BigDecimalType().guard(aArg, aCtx);
        }
        catch (TypeSpecException e)
        {
           return false;
        }
        return true;
    }

    @ScriptyCommand(name="abs")
    @ScriptyRefArgList(ref="1number")
    public BigDecimal abs(@ScriptyParam("arg") BigDecimal aArg)
    {
        return aArg.abs();
    }

    @ScriptyCommand(name="fin")
    @ScriptyRefArgList(ref="1number")
    public BigDecimal fin(@ScriptyParam("arg") BigDecimal aArg)
    {
        return aArg.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @ScriptyCommand(name="float->int")
    @ScriptyRefArgList(ref="1number")
    public BigInteger toInt(@ScriptyParam("arg") BigDecimal aArg)
    {
        return aArg.toBigInteger();
    }

    @ScriptyCommand(name="zero?")
    @ScriptyRefArgList(ref="1number")
    public boolean isZero(@ScriptyParam("arg") BigDecimal aArg)
    {
        return BigDecimal.ZERO.equals(aArg);
    }

    @ScriptyCommand(name="<")
    @ScriptyRefArgList(ref="2numbers")
    public boolean isLT(@ScriptyParam("arg1") BigDecimal aArg1, @ScriptyParam("arg2") BigDecimal aArg2)
    {
        return aArg1.compareTo(aArg2) < 0;
    }

    @ScriptyCommand(name="<~")
    @ScriptyRefArgList(ref="2numbers")
    public boolean isLE(@ScriptyParam("arg1") BigDecimal aArg1, @ScriptyParam("arg2") BigDecimal aArg2)
    {
        return aArg1.compareTo(aArg2) <= 0;
    }

    @ScriptyCommand(name=">")
    @ScriptyRefArgList(ref="2numbers")
    public boolean isGT(@ScriptyParam("arg1") BigDecimal aArg1, @ScriptyParam("arg2") BigDecimal aArg2)
    {
        return aArg1.compareTo(aArg2) > 0;
    }

    @ScriptyCommand(name=">~")
    @ScriptyRefArgList(ref="2numbers")
    public boolean isGE(@ScriptyParam("arg1") BigDecimal aArg1, @ScriptyParam("arg2") BigDecimal aArg2)
    {
        return aArg1.compareTo(aArg2) >= 0;
    }

    @ScriptyCommand(name="~")
    @ScriptyRefArgList(ref="2numbers")
    public boolean isEQ(@ScriptyParam("arg1") BigDecimal aArg1, @ScriptyParam("arg2") BigDecimal aArg2)
    {
        return aArg1.compareTo(aArg2) == 0;
    }
}
