package com.squeezeday.marknadskoll;

import java.text.DecimalFormat;

public class Indicator {
	public String title;
	public double value;
	public String unit;
	
	public Indicator(String title, double value, String unit) {
		this.title = title;
		this.value = value;
		this.unit = unit;
	}
	
	public String valueToString() {
		StringBuilder sb = new StringBuilder();
		if (value > 0 && unit == "%")
			sb.append("+");
		DecimalFormat df = new DecimalFormat("0.00");
		sb.append(df.format(value));
		sb.append(unit);
		return sb.toString().replace(".", ",");
	}
	
}
