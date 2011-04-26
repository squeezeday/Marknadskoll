package com.squeezeday.marknadskoll;

public class Stock {
	public String url;
	public String name;
	public double latest;
	public double change;
	public double change_percent;
	public double buy;
	public double sell;
	public double highest;
	public double lowest;
	public double time;
	
	public Stock(String url,String name,double latest,double change,double change_percent,
			double buy,double sell,double highest,double lowest,double time) {
		this.url = url;
		this.name = name;
		this.latest = latest;
		this.change = change;
		this.change_percent = change_percent;
		this.buy = buy;
	}
}
