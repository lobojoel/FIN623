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

public class ImpactOfDebt extends Fragment {

	public Button buttonCalculate;
	public Button buttonReset;
	public static EditText eSales;
	public static EditText eCOGS;
	public static EditText eExpenses;
	public static EditText eTaxRate;
	public static EditText eAssets;
	public static EditText eDebt;
	public static EditText eDebt2;
	public static EditText eCouponRate;
	public static TextView tvMessage;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	public static double dSales=0;
	public static double dCOGS=0;
	public static double dExpenses=0;
	public static double dTaxRate=0;
	public static double dAssets=0;
	public static double dDebt=0;
	public static double dDebt2=0;
	public static double dCouponRate=0;	
	
	public static double dNetIncome = 0;
	public static double dEBT = 0;
	public static double dEBIT = 0;
	public static double dGrossProfit = 0;
	public static double dProfitMargin = 0;
	public static double dInterest = 0;
	public static double dTax = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.impact_of_debt, null);
	
		eSales = (EditText)view.findViewById(R.id.editTextSales);
		eCOGS = (EditText)view.findViewById(R.id.editTextCostOfGoodsSold);
		eExpenses = (EditText)view.findViewById(R.id.editTextExpenses);
		eTaxRate = (EditText)view.findViewById(R.id.editTextTaxRate);
		eAssets = (EditText)view.findViewById(R.id.EditTextAssets);
		eDebt = (EditText)view.findViewById(R.id.EditTextDebt);
		eDebt2 = (EditText)view.findViewById(R.id.EditTextDebt2);
		eCouponRate = (EditText)view.findViewById(R.id.EditTextCouponRate);
    	buttonCalculate = (Button)view.findViewById(R.id.buttonCalculate);
    	buttonReset = (Button)view.findViewById(R.id.buttonReset);
    	tvMessage = (TextView)view.findViewById(R.id.textView);
   	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DecimalFormat df = new DecimalFormat("#.00");

				eSales.setText(df.format(1090));
				eCOGS.setText(df.format(867)) ;
				eExpenses.setText(df.format(44)) ;
				eTaxRate.setText(df.format(40.0)) ;
				eAssets.setText(df.format(1400));
				eDebt.setText(df.format(0));
				eDebt2.setText(df.format(220)) ;
				eCouponRate.setText(df.format(10.0)) ;
				tvMessage.setText("");
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					dSales = Double.parseDouble(eSales.getText().toString());
					dCOGS = Double.parseDouble(eCOGS.getText().toString());
					dExpenses = Double.parseDouble(eExpenses.getText().toString());
					dTaxRate = Double.parseDouble(eTaxRate.getText().toString());
					dAssets = Double.parseDouble(eAssets.getText().toString());
					dDebt = Double.parseDouble(eDebt.getText().toString());
					dDebt2 = Double.parseDouble(eDebt2.getText().toString());
					dCouponRate = Double.parseDouble(eCouponRate.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.0000000");
					
					double dProfitMargin1 = getProftimargin();
					double dROE1 = getNetIncome() / getEquity() ;
					
					dDebt = dDebt + dDebt2;
					
					double dProfitMargin2 = getProftimargin();
					double dROE2 = getNetIncome() / getEquity();
					
					
					String message1 = "";
					String message2 = "";
					
					if(dProfitMargin2 > dProfitMargin1) {
						message1 = "Profit Margin increased by " + 
								df.format(
								((dProfitMargin2 - dProfitMargin1) * 100)).toString()
									+ " %";
					}else {
						message1 = "Profit Margin decreased by " + 
								df.format(
								 ((dProfitMargin1 - dProfitMargin2) * 100)).toString()
								+ " %";
					}
					
					if(dROE2 > dROE1) {
						message2 = "ROE increased by " + 
								df.format(
								((dROE2 - dROE1) * 100 )).toString()
									+ " %";
					}else {
						message2 = "ROE decreased by " + 
								df.format(
								((dROE1 - dROE2) * 100 )).toString()
								+ " %";
					}
					tvMessage.setText(message1 + "\r\n" + message2);
				}
			}
		});
    	
    	return view;
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(eCOGS.getText().toString()) ) {
			eCOGS.setText("Enter Numbers."); error = true;
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
	
	public static double getProftimargin() {
		return getNetIncome()/dSales ;
	}
	
	public static double getNetIncome() {
		return getEBT() - getTax();
	}
	
	public static double getEBT() {
		return getEBIT() - getInterest();
	}
	
	public static double getEBIT() {
		return getGrossProfit() - dExpenses;
	}
	
	public static double getGrossProfit() {
		return dSales - dCOGS;
	}
	
	public static double getInterest() {
		return dDebt * (dCouponRate/100);
	}
	
	public static double getTax() {
		return getEBT()* (dTaxRate/100);
	}
	
	public static double getEquity() {
		return dAssets - dDebt;
	}
	
	
}
