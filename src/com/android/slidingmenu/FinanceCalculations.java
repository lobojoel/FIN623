package com.android.slidingmenu;

public class FinanceCalculations {

	 public double calculatePresentValue(
			 double dFutureValue, double dInflationRate,double nNumOfYears) {
			return (dFutureValue/Math.pow((1+(dInflationRate/100)), nNumOfYears));
	}
		
	public double calculateFutureValue(
			 double dPresentValue, double dInflationRate,double nNumOfYears) {
		return (dPresentValue*Math.pow((1+(dInflationRate/100)), nNumOfYears));
	}
	
	public double calculateNetWorth (double givenIncome, double givenExpense, double givenLoans) {
		return givenIncome - (givenExpense + givenLoans);
	} 
	
	public double calculateROI(double dPrincipalAmount, double dAnnualRate, double noOfYears, boolean investEveryYear) {
		double dROI = 0;
		double tempPrincipalAmnt = .0;
		
		tempPrincipalAmnt = dPrincipalAmount;
		
		for(int count=0; count<noOfYears; count++) {
			if(investEveryYear && count>0 ) {
				tempPrincipalAmnt = dROI + dPrincipalAmount; 
			}
			
			dROI = (tempPrincipalAmnt * (1+(dAnnualRate/100)) );
			
			if(!investEveryYear) {
				tempPrincipalAmnt = dROI;
			}
		}
		
		return dROI;
	}
	
	public double calculateNoOfYears(double dPrincipalAmount, double dAnnualRate, double dROI, boolean investEveryYear) {
		double dNoOfYears = 0;
		
		dNoOfYears = ( Math.log(dROI/dPrincipalAmount) )/ (Math.log(1+dAnnualRate/100));
		
		return dNoOfYears;
	}
	
	public double calculateAnnualRate(double dPrincipalAmount, double dROI, double dNoOfYears, boolean investEveryYear) {
		double dAnnualRate = 0;
		double inverseNoOfYears = 1/dNoOfYears;
		
		dAnnualRate = Math.pow(dROI/dPrincipalAmount,inverseNoOfYears) - 1;
		
		return dAnnualRate;
	}
	
	public double calculatePrincipalAmnt(double dROI, double dAnnualRate, double dNoOfYears, boolean investEveryYear) {
		double dPrincipalAmount = 0;
		
		return dPrincipalAmount;
	}
	
	public boolean isNumeric(String givenInput) {
		try {
			Double.parseDouble(givenInput);
		}
		catch (NumberFormatException nfe) {
			return false;
		}
		
		return true;
	}
		
}
