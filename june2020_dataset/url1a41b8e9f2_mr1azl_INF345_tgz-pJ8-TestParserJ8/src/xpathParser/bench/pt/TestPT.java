package xpathParser.bench.pt;

import java.util.HashMap;
import java.util.Map;

public class TestPT {

	private static Map<String,String> map=new HashMap<>();

	public static Map<String, String> getMap() {
		map.clear();
		
		map.put("Q1 ","/site/regions/*/item");
		map.put("Q2 ","/site/closed_auctions/closed_auction/annotation/description/parlist/listitem/text/keyword");
		map.put("Q3 ","//keyword");
		map.put("Q4 ","/descendant-or-self::listitem/descendant-or-self::keyword");
		map.put("Q5 ","/site/regions/*/item[parent::namerica or parent::samerica]");
		map.put("Q6 ","//keyword/ancestor::listitem");
		map.put("Q7 ","//keyword/ancestor-or-self::mail");
		map.put("Q8 ","/site/open_auctions/open_auction[bidder[personref/@person='person0']/following-sibling::bidder[personref/@person='person1']]");
		map.put("Q9 ","/site/open_auctions/open_auction[@id='open_auction0']/bidder/preceding-sibling::bidder");
		map.put("Q10"," /site/regions/*/item[@id='item0']/following::item");
		map.put("Q11"," /site/open_auctions/open_auction/bidder[personref/@person='person1']/preceding::bidder[personref/@person='person0']");
		map.put("Q12"," //item[@featured='yes']");
		map.put("Q13"," //*[@id]");
		map.put("Q14"," //person[namespace::xml]");
		map.put("Q15"," //increase[. > 20]");
		map.put("Q16"," //xml:*");
		map.put("Q17"," /node()");
		map.put("Q18"," /comment()");
		map.put("Q19"," /processing-instruction()");
		map.put("Q20"," /processing-instruction('robots')");
		map.put("Q21"," /site/regions/*/item[@id='item0']/description//keyword/text()");
		map.put("Q22"," /site/regions/namerica/item | /site/regions/samerica/item");
		map.put("Q23"," /site/people/person[address and (phone or homepage)]");
		map.put("Q24"," /site/people/person[not(homepage)]");
		map.put("Q25"," id('person0')/name");
		map.put("Q26"," id(/site/people/person[@id='person1']/watches/watch/@open_auction)");
		map.put("Q27"," id(id(/site/people/person[@id='person1']/watches/watch/@open_auction)/seller/@person)");
		map.put("Q28"," id(/site/closed_auctions/closed_auction[buyer/@person='person4']/itemref/@item)[parent::namerica or parent::samerica]");
		map.put("Q29"," id(/site/closed_auctions/closed_auction[id(seller/@person)/name='Alassane Hogan']/itemref/@item)");
		map.put("Q30"," /site/open_auctions/open_auction/bidder[position()=1 and position()=last()]");
		map.put("Q31"," /site/open_auctions/open_auction[count(bidder)>5]");
		map.put("Q32"," //*[local-name()='item']");
		map.put("Q33"," //*[name()='svg:item']");
		map.put("Q34"," //*[boolean(namespace-uri())]");
		map.put("Q35"," //*[lang('it')]");
		map.put("Q36"," /site/regions/*/item[contains(description,'gold')]");
		map.put("Q37"," /site/people/person[starts-with(name,'Ed')]");
		map.put("Q38"," /site/regions/*/item/mailbox/mail[substring-before(date,'/')='10']");
		map.put("Q39"," /site/regions/*/item/mailbox/mail[substring-before(substring-after(date,'/'),'/')='09']");
		map.put("Q40"," /site/regions/*/item/mailbox/mail[substring-after(substring-after(date,'/'),'/')='1998']");
		map.put("Q41"," /site/regions/*/item/mailbox/mail[substring(date,7,2)='20']");
		map.put("Q42"," /site/regions/*/item[string-length(normalize-space(string(description))) > 1000]");
		map.put("Q43"," /site/people/person[string-length(translate(concat(address/street,address/city,address/country,address/zipcode),\" \",\"\")) > 30]");
		map.put("Q44"," /site/open_auctions/open_auction[floor(sum(bidder/increase)) >= 70]");
		map.put("Q45"," /site/open_auctions/open_auction[ceiling(sum(bidder/increase)) <= 70]");
		map.put("Q46"," /site/open_auctions/open_auction[round((number(current) - number(initial)) div count(bidder)) > 8]");
		map.put("Q47"," /site/people/person[boolean(emailaddress) = true() and not(boolean(homepage)) = false()]");
		map.put("A6 ","//*[namespace::svg]");
		map.put("A7 ","//*[namespace::* = 'http://www.w3.org/2000/svg']");
		map.put("A8 ","//svg:*");
		map.put("A9 ","//*[boolean(namespace-uri())]");
		map.put("A10"," //*[local-name()='ellipse']");
		map.put("A11"," //*[name()='svg:ellipse']");
		map.put("A12"," //*[lang('it')]");
		map.put("A1 ","/comment()");
		map.put("A2 ","/processing-instruction('robots')");
		map.put("A3 ","//*[namespace::xlink]");
		map.put("A4 ","//*[namespace::* = 'http://www.w3.org/1999/xlink']");
		map.put("A5 ","//xlink:*");


		return map;
	}

	public static void setMap(Map<String, String> map) {
		TestPT.map = map;
	}
	
}
