package com.decom.chunks.economic;

public class CalcRatioResult {
	/**
	 * 算出新上市股票在多少天的涨停之后的总市值
	 * 
	 * @param price  发行时价格
	 * @param number 股票份数
	 * @param days	 涨停天数
	 * 
	 * @return
	 */
	public static void getNewStockProfitAfterManyDaysLimit(double price, int number, int days){
		if(price < 0 || days < 0){
			throw new IllegalArgumentException("参数不能为负！");
		}
		
		double cost = price * number; //成本
		
		double ratio = Math.pow(1.1, days - 1) * 1.44;//涨停days天之后的倍率，第1天涨幅为0.44
		double firstDayTotalValue = cost ;//上市第一天涨停之后听总市值

		double firstDayPrice = price; //上市价格
		double currentPrice = firstDayPrice * ratio;//涨停days天之后的价格
		double increaseOfOriginalPrice = currentPrice - price;	//涨幅，相比上市价格
		double priceDifference = 0;
		if(days < 2) {
			priceDifference = price * 0.44;
		}
		else {
			double ratioDifference = Math.pow(1.1, days - 2) * 1.44;// days - 1 的超前增涨率
			double priorDayPrice = firstDayPrice * ratioDifference;
			
			priceDifference = currentPrice - priorDayPrice;
		}
		
		
		double totalMarketCapitalization = firstDayTotalValue * ratio;//涨停days天之后的总市值
		
		String current = String.format("%.2f", currentPrice);
		String increase = String.format("%.2f", increaseOfOriginalPrice);
		String priorDayIncrease = String.format("%.2f", priceDifference);
		String total = String.format("%.2f", totalMarketCapitalization);
		String profit = String.format("%.2f", Double.valueOf(total) - cost);
		
		System.out.println("在 " + days + " 天的涨停后，价格为：" + current + " ,相比上市价格涨幅为：" + increase + " 相比前一天涨幅为：" + priorDayIncrease);
		System.out.println("总市值为：" + total);
		System.out.println("总盈利为： " + profit);
		
	}
	
	

	
	
	public static void main(String[] args) {
		
		double price = 8.82;
		int number = 1000;
		int days = 10;
		
		System.out.println("发行价格为 " + price + " 元的 " + number + " 份股票");
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 1);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 2);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 3);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 4);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 5);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 6);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 7);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 8);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 9);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 10);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 11);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 12);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 13);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 14);
		CalcRatioResult.getNewStockProfitAfterManyDaysLimit(price, number, 15);
	}
	
}
