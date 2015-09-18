package com.android.CorpFinance;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.slidingmenu.FinanceCalculations;
import com.android.slidingmenu.R;

public class WACC extends Fragment {

	public Button buttonCalculate;
	public Button buttonReset;
	public static EditText eMaturityInYears;
	public static EditText eNumOfBonds;
	public static EditText ePriceBonds;
	public static EditText eYieldCurveRate;
	public static EditText ePricePreffStock;
	public static EditText eDividendPrefStock;
	public static EditText eNumOfPrefStock;
	public static EditText eCommonPriceStock;
	public static EditText eGrowthRateInDividends;
	public static EditText eDividendInOneYear;
	public static EditText eNumOfCommonShares;
	public static EditText eTaxRate;
	public static TextView tvMessage;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	public static double dMaturityInYears = 0;
	public static double dNumOfBonds =0;
	public static double dPriceBonds =0;
	public static double dYieldCurveRate =0;
	public static double dPricePreffStock =0;
	public static double dDividendPrefStock =0;
	public static double dNumOfPrefStock =0;
	public static double dCommonPriceStock =0;
	public static double dGrowthRateInDividends =0;
	public static double dDividendInOneYear =0;
	public static double dNumOfCommonShares =0;
	public static double dTaxRate =0;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.wacc_layout, null);
	
		eMaturityInYears = (EditText)view.findViewById(R.id.editTextDebtMaturity);
		eNumOfBonds = (EditText)view.findViewById(R.id.editTextNumOfBonds);
		ePriceBonds = (EditText)view.findViewById(R.id.editTextPriceBonds);
		eYieldCurveRate = (EditText)view.findViewById(R.id.editTextYieldCurve);
		ePricePreffStock = (EditText)view.findViewById(R.id.EditTextPreferredStock);
		eDividendPrefStock = (EditText)view.findViewById(R.id.EditTextDividendPreferredStock);
		eNumOfPrefStock = (EditText)view.findViewById(R.id.EditTextNumSharesPreferred);
		eCommonPriceStock = (EditText)view.findViewById(R.id.EditTextCommonPrice);
		eGrowthRateInDividends = (EditText)view.findViewById(R.id.EditTextGrowthRate);
		eDividendInOneYear = (EditText)view.findViewById(R.id.EditTextDividendOneYear);
		eNumOfCommonShares = (EditText)view.findViewById(R.id.EditTextNumOfCommonShares);
		eTaxRate = (EditText)view.findViewById(R.id.EditTextTaxRate);
    	buttonCalculate = (Button)view.findViewById(R.id.buttonCalculate);
    	buttonReset = (Button)view.findViewById(R.id.buttonReset);
    	tvMessage = (TextView)view.findViewById(R.id.textView);
   	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DecimalFormat df = new DecimalFormat("#.00");
				
				eMaturityInYears.setText(df.format(15.00)) ;
				eNumOfBonds.setText(df.format(19000)) ;
				ePriceBonds.setText(df.format(1000)) ;
				eYieldCurveRate.setText(df.format(13.40));
				ePricePreffStock.setText(df.format(34.63));
				eDividendPrefStock.setText(df.format(5.40)) ;
				eNumOfPrefStock.setText(df.format(30300)) ;
				eCommonPriceStock.setText(df.format(27.76));
				eGrowthRateInDividends.setText(df.format(3.00));
				eDividendInOneYear.setText(df.format(4.11)) ;
				eNumOfCommonShares.setText(df.format(307000));
				eTaxRate.setText(df.format(39.00));
				tvMessage.setText("");
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					dMaturityInYears = Double.parseDouble(eMaturityInYears.getText().toString());
					dNumOfBonds = Double.parseDouble(eNumOfBonds.getText().toString());
					dPriceBonds = Double.parseDouble(ePriceBonds.getText().toString());
					dYieldCurveRate = Double.parseDouble(eYieldCurveRate.getText().toString());
					dPricePreffStock = Double.parseDouble(ePricePreffStock.getText().toString());
					dDividendPrefStock = Double.parseDouble(eDividendPrefStock.getText().toString());
					dNumOfPrefStock = Double.parseDouble(eNumOfPrefStock.getText().toString());
					dCommonPriceStock = Double.parseDouble(eCommonPriceStock.getText().toString());
					dGrowthRateInDividends = Double.parseDouble(eGrowthRateInDividends.getText().toString());
					dDividendInOneYear = Double.parseDouble(eDividendInOneYear.getText().toString());
					dNumOfCommonShares = Double.parseDouble(eNumOfCommonShares.getText().toString());
					dTaxRate = Double.parseDouble(eTaxRate.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.0000000");
					
					
					double dWACC = getWACC();
					
					String message = "WACC is  " + 
									 df.format(dWACC).toString();
					tvMessage.setText(message);
				}
			}
		});
    	
    	return view;
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(eMaturityInYears.getText().toString()) ) {
			eMaturityInYears.setText("Enter Numbers."); error = true;
		}
//		if( !oFinanceCalcs.isNumeric(eSalariesRent.getText().toString()) ) {
//			eSalariesRent.setText("Enter Numbers."); error = true;
//		}
//		if( !oFinanceCalcs.isNumeric(eDepreciation.getText().toString()) ) {
//			eDepreciation.setText("Enter Numbers."); error = true;
//		}
//		if( !oFinanceCalcs.isNumeric(eEBIT.getText().toString()) ) {
//			eEBIT.setText("Enter Numbers."); error = true;
//		}
//		if( !oFinanceCalcs.isNumeric(eInventoryRatio.getText().toString()) ) {
//			eInventoryRatio.setText("Enter Numbers."); error = true;
//		}
		
		return error;
	}
	
	public double getRate1() {
		return (1- (dTaxRate/100) ) * (dYieldCurveRate/100);
	}
	
	public double getWeight1() {
		return dNumOfBonds * dPriceBonds;
	}
	
	public double getRate2() {
		return dDividendPrefStock / dPricePreffStock;
	}
	
	public double getWeight2() {
		return dNumOfPrefStock * dPricePreffStock;
	}
	
	public double getRate3() {
		return (dDividendInOneYear/dCommonPriceStock) + (dGrowthRateInDividends/100);
	}
	
	public double getWeight3() {
		return dNumOfCommonShares * dCommonPriceStock;
	}
	
	public double getTotalWeight() {
		return getWeight1() + getWeight2() + getWeight3();
	}
	
	public double getWACC() {
		return ( (getWeight1()*getRate1()) + 
				 (getWeight2()*getRate2()) +
				 (getWeight3()*getRate3()) )
						/
				 (getTotalWeight());
	}
	
}
