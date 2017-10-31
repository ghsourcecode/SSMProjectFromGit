package com.decom.chunks.economic;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Administrator on 2017/10/10.
 * 此类实现计算复利功能
 * 计算思路是：
 * 初始投入一定数额A，以后按年利率C产生收益，每月投入M，之每月投入的仍按年利率计算，则在Y年后，总额是多少。
 */
public class CompoundInterest {
    private double initialInvest = 400000;              //初始投入
    private double yearRate = 0.12;            //年化利率
    private double investPerMonth = 6000;            //每月投入固定数额
    private int investYears = 5;                  //复利年数

    public CompoundInterest() {
    }

    public CompoundInterest(double initialInvest, double yearRate, double investPerMonth, int investYears){
        this.initialInvest = initialInvest;
        this.yearRate = yearRate;
        this.investPerMonth = investPerMonth;
        this.investYears = investYears;
    }

    /**
     * 计算方法是：每月投入固定数额后，按年利率计算，
     * 则第1个月产生的收益： investPerMonth * yearRate/12 * (12 - 1)
     * 第2个月产生的收益：investPerMonth * yearRate/12 * (12 - 2)
     * ……
     * 第12个月产生的收益：investPerMonth * yearRate/12 * (12 - 12)
     *
     * 此种计算方式不正确，没有考虑当月投入产生收益之后，从下个月之后各月，仍然产生收益
     * @return
     */
    private double getProfitSumEveryYearPerMonthInvest() {
        return this.investPerMonth * this.yearRate / 12 * (12*(0 + 11) / 2);
    }

    /**
     * 该方法计算方式为：
     * 第1个月投入m，月末总额为M1 = m;
     * 第2个月投入m, 则月末总额为M2 = M1 * C / 12 + m;
     * 第3个月投入m，则月末总额为M3 = M2 * C / 12 + m;
     * ……
     * 则第12月投入m，则12月份总额为M12 = M11 * C / 12 + m;
     * @return
     */
    private double getInvestAndProfitSumEveryYear(){
        int months = 12;
        double monthInvest = this.investPerMonth;
        double rate = this.yearRate;

        double lastMonthSum = 0;
        for (int i = 0; i < months; i++){
            lastMonthSum += lastMonthSum * rate / 12 + monthInvest;
        }

        return lastMonthSum;
    }

    /**
     * 按年计算总收益
     */
    public void calcTotalProfit() {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(true);
        Currency currency = Currency.getInstance(Locale.CHINA);

        double lastMonthTotal = this.getInitialInvest();
        for(int i = 0; i < this.investYears; i++){
            for(int j = 0; j < 12; j++){
                lastMonthTotal += (lastMonthTotal * this.yearRate / 12 + this.investPerMonth);
            }

            System.out.println("第" + (i + 1) + "年底收益总额为：" + currency.getSymbol() + df.format(lastMonthTotal) + " 元");
        }
    }

    /**
     * 按月计算总收益
     */
    public void getMonthTotol(){

    }


    public double getInitialInvest() {
        return initialInvest;
    }

    public void setInitialInvest(double initialInvest) {
        this.initialInvest = initialInvest;
    }

    public double getYearRate() {
        return yearRate;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }

    public double getInvestPerMonth() {
        return investPerMonth;
    }

    public void setInvestPerMonth(double investPerMonth) {
        this.investPerMonth = investPerMonth;
    }

    public int getInvestYears() {
        return investYears;
    }

    public void setInvestYears(int investYears) {
        this.investYears = investYears;
    }

    public static void main(String[] args) {
        CompoundInterest investCalc = new CompoundInterest();
        investCalc.setInitialInvest(400000);
        investCalc.setInvestPerMonth(8000);
        investCalc.setYearRate(0.2);
        investCalc.setInvestYears(5);

        double yearSum1 = investCalc.getInvestPerMonth() * 12 + investCalc.getProfitSumEveryYearPerMonthInvest();
        double yearSum2 =  investCalc.getInvestAndProfitSumEveryYear();

        String pattern1 = "##.##";//按四舍五入保留2位小数，小数点后保留有效位数
        String pattern2 = "#0.00";//按四舍五入保留2位小数，位数不够，用0补齐
        DecimalFormat df = new DecimalFormat();
        df.applyLocalizedPattern(pattern1);
        String p = df.format(yearSum1);
        System.out.println("按四舍五入保留2位小数，仅保留有效位数，结果为：" + p);
        df.applyPattern(pattern2);
        p = df.format(yearSum1);
        System.out.println("按四舍五入保留2位小数，位数不够用0补齐，结果为：" + p);
        System.out.println();
        System.out.println("按年化利率计算每个月产生的收益，再加上一年总投入，则年底收入总额：" + yearSum1);
        System.out.println("以上个月的累计总额，计算至年底时，收入总额：" + yearSum2);
        System.out.println("2种计算方法相差数额：" + (yearSum2 - yearSum1));

        System.out.println("计算每年总收益：");
        System.out.println("初始投入：" + investCalc.getInitialInvest());
        System.out.println("年化利率：" + investCalc.getYearRate());
        System.out.println("投资期限：" + investCalc.getInvestYears());
        System.out.println("每月投入金额：" + investCalc.getInvestPerMonth());
        investCalc.calcTotalProfit();
    }

}
