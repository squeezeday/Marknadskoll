package com.squeezeday.marknadskoll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.protocol.HttpContext;

public class SixHelper {
	
	
	public static final String URL_INDICATORS = "http://fp.solutions.six.se/generalstockinfo/common/indicator.page";
	public static final String REGEX_INDICATOR_EXCHANGE = "<div class=\"index-column\">\\s*<span class=\"Nm\">([a-zA-Z\\- ]+)</span><br />\\s*<span class=\"PcCh\"><img[^>]*> <span class=\"(positive|negative|neutral)\">([0-9,\\-+]+)%</span></span>\\s*</div>";
	public static final String REGEX_INDICATOR_CURRENCY = "<div>\\s*<span class=\"Nm\">([A-Za-z ]+)</span><br />\\s*<span class=\"Ccy\"><img[^>]*> <span[^>]*>([0-9, ]+) </span></span>\\s*</div>";
	public static final String REGEX_INDICATOR_OIL = "<div>\\s*<span class=\"Nm\">(Olja \\(USD/fat\\))</span><br />\\s*<span class=\"X\"><span[^>]*>([0-9,]+)</span></span>\\s*<span class=\"PcCh\"><span[^>]*>([0-9,\\-]+)%</span></span>\\s*</div>";
	
	public static final String URL_STOCKLIST = "http://fp.solutions.six.se/generalstockinfo/common/stock_list.page";
	public static final String REGEX_STOCK = "<tr[^>]*>\\s*<td class=\"Nm\"><a class=\"link\" href=\"([^\"]*)\">([^>]+)</a></td>\\s*<td class=\"Pd\">([0-9,]+)</td>\\s*<td class=\"Ch\"><span[^>]*>([0-9, -]+)</span></td>\\s*<td class=\"PcCh\"><span[^>]*>([0-9, -]+)</span></td>\\s*<td class=\"B\">154,70</td>\\s*<td class=\"A\">([0-9, -]+)</td>\\s*<td class=\"PdHi\">([0-9, -]+)</td>\\s*<td class=\"PdLo\">([0-9, -]+)</td>\\s*<td class=\"Time\">([0-9:]+)</td>\\s*</tr>";
	
	public static Indicator[] getIndicators() throws IOException, HttpException, Exception {
		ArrayList<Indicator> ret = new ArrayList<Indicator>();
	
		String data = HttpHelper.get(URL_INDICATORS);
		
		Pattern p;
		Matcher m;
		StringBuilder sb;
		int found = 0;
		
		p = Pattern.compile(REGEX_INDICATOR_EXCHANGE, Pattern.MULTILINE);
		m = p.matcher(data);
		while (m.find()) {
			double d = Double.parseDouble(m.group(3).replace(",", "."));
			ret.add(new Indicator(m.group(1), d, "%"));
			found++;
		}
		
		p = Pattern.compile(REGEX_INDICATOR_CURRENCY, Pattern.MULTILINE);
		m = p.matcher(data);
		while (m.find()) {
			double d = Double.parseDouble(m.group(2).replace(",", "."));
			ret.add(new Indicator(m.group(1), d, "kr"));
			found++;
		}
		
		p = Pattern.compile(REGEX_INDICATOR_OIL, Pattern.MULTILINE);
		m = p.matcher(data);
		while (m.find()) {
			double d = Double.parseDouble(m.group(2).replace(",", "."));
			ret.add(new Indicator("Olja $/f", d, ""));
			found++;
		}
		
		if (found == 0) {
			throw new Exception("No data found");
		}
			
		
		Indicator[] sret = new Indicator[ret.size()];
		ret.toArray(sret);
		return sret;
	}
	
	public static Indicator[] getIndicatorsDummy() throws Exception {
		
		ArrayList<Indicator> ret = new ArrayList<Indicator>();
		
		
		ret.add(new Indicator("Stockholm", 0.32, "%"));
		ret.add(new Indicator("Nasdaq", -0.50, "%"));
		ret.add(new Indicator("Dow Jones", -0.20, "%"));
		ret.add(new Indicator("Nikkei", 0.80, "%"));
		ret.add(new Indicator("USD", 6.66, "kr"));
		ret.add(new Indicator("Euro", 8.80, "kr"));
		ret.add(new Indicator("Olja $/fat", 115.59, ""));
		Indicator[] sret = new Indicator[ret.size()];
		ret.toArray(sret);
		return sret;
	}
	
	public static Stock[] getStocks() throws Exception {
		ArrayList<Stock> ret = new ArrayList<Stock>();
		
		String data = HttpHelper.get(URL_INDICATORS);
		
		Pattern p;
		Matcher m;
		StringBuilder sb;
		
		p = Pattern.compile(REGEX_INDICATOR_EXCHANGE, Pattern.MULTILINE);
		m = p.matcher(data);
		while (m.find()) {
			double d = Double.parseDouble(m.group(3).replace(",", "."));
			//ret.add(new Stock());
		}

		Stock[] sret = new Stock[ret.size()];
		ret.toArray(sret);
		return sret;
	}
}
