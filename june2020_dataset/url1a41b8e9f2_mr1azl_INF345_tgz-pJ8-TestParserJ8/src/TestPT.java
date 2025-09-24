

import java.util.HashMap;
import java.util.Map;

public class TestPT {

	private static Map<String,String> map=new HashMap<>();

	public static Map<String, String> getMap() {
		map.clear();
		
		map.put("A1","//L/*");
		map.put("A2","//L/parent::*");
		map.put("A3","//L/descendant::*");
		map.put("A4","//L/descendant-or-self::*");
		map.put("A5","//L/ancestor::*");
		map.put("A6","//L/ancestor-or-self::*");
		map.put("A7","//L/following-sibling::*");
		map.put("A8","//L/preceding-sibling::*");
		map.put("A9","//L/following::*");
		map.put("A1","//L/preceding::*");
		map.put("A1","//L/self::*");
		map.put("A1","//L/@id/parent::*");
		map.put("P1","//*[L]");
		map.put("P2","//*[parent::L]");
		map.put("P3","//*[descendant::L]");
		map.put("P4","//*[descendant-or-self::L]");
		map.put("P5","//*[ancestor::L]");
		map.put("P6","//*[ancestor-or-self::L]");
		map.put("P7","//*[following-sibling::L]");
		map.put("P8","//*[preceding-sibling::L]");
		map.put("P9","//*[following::L]");
		map.put("P1","//*[preceding::L]");
		map.put("P1","//*[self::L]");
		map.put("P1","//*[@id]");
		map.put("T1","//L/text()");
		map.put("T2","//L/comment()");
		map.put("T3","//L/processing-instruction()");
		map.put("T4","//L/processing-instruction(\"myPI\")");
		map.put("T5","//L/node()");
		map.put("T6","//L/N");
		map.put("T7","//L/*");
		map.put("O1","//*[child::* and preceding::Q]");
		map.put("O2","//*[not(child::*) and preceding::Q]");
		map.put("O3","//*[preceding::L or following::L]");
		map.put("O4","//L/ancestor::* | //L/descendant::*");
		map.put("O5","//*[.=\"happy-go-lucky man\"]");
		map.put("O6","//*[@pre > 12 and @post < 15]");
		map.put("O7","//*[@pre != @post]");
		map.put("O8","//*[((@post * @post + @pre * @pre) div (@post + @pre)) > ((@post - @pre) * (@post - @pre))]");
		map.put("O9","//*[@pre mod 2 = 0]");
		map.put("F1","//*[contains(.,\"plentiful\")]");
		map.put("F2","//*[starts-with(.,\"plentiful\")]");
		map.put("F3","//*[substring(.,1,9) = \"plentiful\"]");
		map.put("F4","//*[substring-after(.,\"oven\") = \"ware\"]");
		map.put("F5","//*[substring-before(.,\"ful\") = \"plenti\"]");
		map.put("F6","//*[string-length(translate(normalize-space(.),\" \",\"\")) > 100]");
		map.put("F7","//*[concat(.,..) = ..]");
		map.put("F8","//*[ceiling(@pre div @post) = 1]");
		map.put("F9","//*[floor(@pre div @post) = 0]");
		map.put("F1","//*[round(@pre div @post) = 0]");
		map.put("F1","//*[name(.) = \"X\"]");
		map.put("F1","//*[lang(\"it\")]");
		map.put("F1","//L/child::*[last()]");
		map.put("F1","//L/descendant::*[4]");
		map.put("F1","//L/ancestor::*[2]");
		map.put("F1","//L/following-sibling::*[1]");
		map.put("F1","//L/preceding-sibling::*[1]");
		map.put("F1","//L/following::*[7]");
		map.put("F1","//L/preceding::*[7]");
		map.put("F2","//*[count(ancestor::*) > 3]");
		map.put("F2","//*[sum(ancestor::*/@pre) < sum(descendant::*/@pre)]");
		map.put("F2","id(\"n1 n26\")");
		map.put("F2","id(id(//*[.=\"happy-go-lucky man\"]/@idrefs)/@idrefs)");
		map.put("F2","//*[number(@pre) < number(@post)]");
		map.put("F2","//*[string(@pre - 1) = \"0\"]");
		map.put("F2","//*[boolean(@id) = true() and boolean(@idrefs) = false()]");
		return map;
	}

	public static void setMap(Map<String, String> map) {
		TestPT.map = map;
	}
	
}
