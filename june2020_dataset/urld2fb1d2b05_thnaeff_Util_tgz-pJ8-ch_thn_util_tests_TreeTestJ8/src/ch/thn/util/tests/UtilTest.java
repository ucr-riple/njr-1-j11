/**
 *    Copyright 2013 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.util.tests;


import java.util.Arrays;
import java.util.LinkedList;

import ch.thn.util.NumberUtil;
import ch.thn.util.ReflectionUtil;
import ch.thn.util.ReflectionUtil.MethodProperties;
import ch.thn.util.StringUtil;
import ch.thn.util.TimeDateUtil;
import ch.thn.util.valuerange.ValueRange;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class UtilTest {


	public static void main(String[] args) {

		ValueRange gradient = new ValueRange(0, 10, 5, 3, 3);
		System.out.println(gradient.getValues());


		MethodProperties p = ReflectionUtil.parseMethodProperties("public static boolean testMethod (String)");
		System.out.println("methodName='" + p.getMethodName() + "'");
		if (p.hasInvalidTypes()) {
			LinkedList<String> invalid = p.getInvalidTypes();
			for (String s : invalid) {
				System.out.println("invalidType='" + s + "'");
			}
		}

		LinkedList<Class<?>> parameterTypes = p.getParameterTypes();
		for (Class<?> c : parameterTypes) {
			System.out.println("parameterType='" + c + "'");
		}

		System.out.println("returnType='" + p.getReturnType() + "'");



		System.out.println(StringUtil.buildRangeString("0-9"));
		System.out.println(StringUtil.buildRangesString("[!-/:-@[-`{-~]"));
		System.out.println(StringUtil.replaceStringRanges("hallo[0-99-00-99-0]how[9-1]are[#-\\]you"));

		Number n[] = NumberUtil.generateRandomNumbers(5, 2, 24.1).toArray(new Number[5]);
		Arrays.sort(n);
		System.out.println(Arrays.toString(n));


		System.out.println(NumberUtil.roundDecimals(3.154243, 4));

		System.out.println(NumberUtil.createDecimalFormat(5, 2, true, false, true, false, true).format(-54.12345));

		System.out.println(NumberUtil.formatNumber(2389.2255, true));

		System.out.println(TimeDateUtil.getTimeFromMillis(System.currentTimeMillis(), ":", true, true, true, false, false));
		System.out.println(TimeDateUtil.getDateFromMillis(System.currentTimeMillis(), ".", true, true, true, false, false, false));

		System.out.println(TimeDateUtil.millisToTime(34345000, "d ", "h ", "m ", "s ", "ms", false));

		//		MultiKeyHashMap<String, String> mthm = new MultiKeyHashMap<String, String>();
		//
		//		mthm.addKeyMapping("id", "indi_id");
		//		mthm.addKeyMapping("id2", "indi_id");
		//		mthm.addKeyMapping("fam", "fam_id");
		//		mthm.addKeyMapping("test", "test1");
		//		mthm.addKeyMapping("a", "b");
		//
		//		mthm.put("indi_id", "value:indi_id");
		//		mthm.put("fam", "value:fam");
		//
		//		System.out.println(mthm);
		//		System.out.println(mthm.containsKey("fam"));
		//		System.out.println(mthm.get("fam"));
		//
		//
		//
		//		MultiValueHashMap<String, String> mvhm = new MultiValueHashMap<>();
		//		mvhm.put("key1", "value1");
		//		mvhm.put("key2", "value2");
		//		mvhm.put("key1", "value3");
		//		mvhm.put("key1", "value4");
		//		mvhm.put("key2", "value5");
		//		mvhm.put("key3", "value6");
		//		System.out.println("mvhm > " + mvhm);
		//
		//		MultiValueLinkedHashMap<String, String> mvlhm = new MultiValueLinkedIndexHashMap<String, String>();
		//		mvlhm.put("key1", "value1");
		//		mvlhm.put("key2", "value2");
		//		mvlhm.put("key1", "value3");
		//		mvlhm.put("key1", "value4");
		//		mvlhm.put("key2", "value5");
		//		mvlhm.put("key3", "value6");
		//		System.out.println("mvlhm > " + mvlhm);
		//
		//		MultiValueLinkedIndexHashMap<String, String> mvlihm = new MultiValueLinkedIndexHashMap<String, String>();
		//		mvlihm.put("key1", "value6");
		//		mvlihm.put("key2", "value5");
		//		mvlihm.put("key3", "value4");
		//		mvlihm.put("key1", "value1");
		//		mvlihm.put("key2", "value2");
		//		mvlihm.put("key1", "value3");
		//
		////		System.out.println("mvlihm > " + mvlihm);
		//		mvlihm.put("key3", "value7", 10);
		////		mvlihm.remove(3);
		////		mvlihm.remove("key2");
		////		mvlihm.remove("key2");
		////		System.out.println("mvlihm > " + mvlihm.get("key3", 0));
		////		mvlihm.remove("key3", 1);
		//		System.out.println("mvlihm > " + mvlihm);
		//
		//		System.out.println("---");
		//
		//		mvlihm.sortByKey(new TestKeyComparator());
		//		System.out.println("mvlihm > " + mvlihm);
		//
		//		mvlihm.sortByValue(new TestValueComparator());
		//		System.out.println("mvlihm > " + mvlihm);

		System.out.println(NumberUtil.formatNumber(10, 5, 0, true, true));


	}

}
