/****************************************************************************
 * Copyright (c) 2013, the Konoha project authors. All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***************************************************************************/

package org.KonohaScript;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class KClass {
	Konoha KonohaContext;
	KPackage Package;
	int ClassFlag;
	public String ShortClassName;
	KClass BaseClass;
	KClass SuperClass;
	KParam ClassParam;
	KClass SearchSimilarClass;
	ArrayList<KMethod> ClassMethodList;
	KClass SearchSuperMethodClass;
	Object DefaultNullValue;
	Object LocalSpec;

	// public ArrayList<String> FieldNames;
	// public ArrayList<KClass> FieldTypes;

	public static final ArrayList<KMethod> EmptyMethodList = new ArrayList<KMethod>();

	public KClass(Konoha KonohaContext, KPackage Package, int ClassFlag,
			String ClassName, Object Spec) {
		this.KonohaContext = KonohaContext;
		this.ClassFlag = ClassFlag;
		this.Package = Package;
		this.ShortClassName = ClassName;
		this.SuperClass = null;
		this.BaseClass = this;
		this.ClassMethodList = EmptyMethodList;
		this.LocalSpec = Spec;
	}

	// Java Implementation Only
	Class<?> HostedClassInfo = null;

	public KClass(Konoha KonohaContext, Class<?> ClassInfo) {
		this(KonohaContext, null, 0, ClassInfo.getSimpleName(), null);
		this.HostedClassInfo = ClassInfo;
		// this.ClassFlag = ClassFlag;
		if (ClassInfo != Object.class) {
			this.SuperClass = KonohaContext.LookupTypeInfo(ClassInfo
					.getSuperclass());
		}
	}

	// public KClass(Konoha KonohaContext, String ClassName) throws
	// ClassNotFoundException {
	// this(KonohaContext, null, 0, ClassName, null);
	// Class<?> ClassInfo = Class.forName(ClassName);
	// this.HostedClassInfo = ClassInfo;
	// this.ShortClassName = ClassInfo.getSimpleName();
	// // this.ClassFlag = ClassFlag;
	// if(ClassInfo != Object.class) {
	// this.SuperClass =
	// KonohaContext.LookupTypeInfo(ClassInfo.getSuperclass().getName());
	// }
	// }

	static KMethod ConvertMethod(Konoha KonohaContext, Method Method) {
		KClass ThisType = KonohaContext.LookupTypeInfo(Method.getClass());
		Class<?>[] ParamTypes = Method.getParameterTypes();
		KClass[] ParamData = new KClass[ParamTypes.length + 1];
		ParamData[0] = KonohaContext.LookupTypeInfo(Method.getReturnType());
		for (int i = 0; i < ParamTypes.length; i++) {
			ParamData[i + 1] = KonohaContext.LookupTypeInfo(ParamTypes[i]);
		}
		KParam Param = new KParam(ParamData.length, ParamData);
		KMethod Mtd = new KMethod(0, ThisType, Method.getName(), Param, Method);
		ThisType.AddMethod(Mtd);
		return Mtd;
	}

	int CreateMethods(String MethodName) {
		int Count = 0;
		Method[] Methods = HostedClassInfo.getMethods();
		for (int i = 0; i < Methods.length; i++) {
			if (MethodName.equals(Methods[i].getName())) {
				ConvertMethod(KonohaContext, Methods[i]);
				Count = Count + 1;
			}
		}
		return Count;
	}

	public static KClass VoidType = null;
	public static KClass ObjectType = null;
	public static KClass BooleanType = null;
	public static KClass IntType = null;
	public static KClass StringType = null;

	public boolean Accept(KClass TypeInfo) {
		if (this == TypeInfo)
			return true;
		return false;
	}

	public void AddMethod(KMethod Method) {
		if (ClassMethodList == EmptyMethodList) {
			ClassMethodList = new ArrayList<KMethod>();
		}
		ClassMethodList.add(Method);
	}

	public void DefineMethod(int MethodFlag, String MethodName, KParam Param,
			Object Callee, String LocalName) {
		KMethod Method = new KMethod(MethodFlag, this, MethodName, Param,
				KFunc.LookupMethod(Callee, LocalName));
		if (ClassMethodList == EmptyMethodList) {
			ClassMethodList = new ArrayList<KMethod>();
		}
		ClassMethodList.add(Method);
	}

	public KMethod LookupMethod(String MethodName, int ParamSize) {
		for (int i = 0; i < ClassMethodList.size(); i++) {
			KMethod Method = ClassMethodList.get(i);
			if (Method.Match(MethodName, ParamSize)) {
				return Method;
			}
		}
		if (SearchSuperMethodClass != null) {
			KMethod Method = SearchSuperMethodClass.LookupMethod(MethodName,
					ParamSize);
			if (Method != null) {
				return Method;
			}
		}
		if (HostedClassInfo != null) {
			if (CreateMethods(MethodName) > 0) {
				return LookupMethod(MethodName, ParamSize);
			}
		}
		return null;
	}

	// public KMethod LookupMethod(String symbol, int ParamSize) {
	// return null;
	// }

}
