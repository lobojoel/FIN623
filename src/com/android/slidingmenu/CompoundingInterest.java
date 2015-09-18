package com.android.slidingmenu;

import java.text.DecimalFormat;

import com.android.slidingmenu.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class CompoundingInterest extends Fragment {

	private static Button buttonCalculate;
	private static Button buttonReset;
	private static EditText eReturnOnInvestment;
	private static TextView tvReturnOnInvestment;
	private static EditText ePrincipalAmount;
	private static TextView tvPrincipalAmount;
	private static CheckBox cbInvestEveryyear;
	private static EditText eNoOfYears;
	private static TextView tvNoOfYears;
	private static EditText eAnnualRate;
	private static TextView tvAnnualRate;
	private static EditText eAvgInflationRate;
	private static TextView tvAvgInflationRate;
	private static TextView tvMessage;

	private static RadioButton rbReturnOnInvestment;
	private static RadioButton rbAnnualRate;
	private static RadioButton rbNoOfYears;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	private static double lPrincipalAmount =0;
	private static boolean investEveryYear = false;
	private static double dNoOfYears=0;
	private static double lAnnualRate=0;
	private static double lReturnOnInvestment=0;
	private static double lAvgInflationRate=0;
	
	private static boolean bReturnOnInvestment = true;
	private static boolean bAnnualRate = false;
	private static boolean bNoOfYears= false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout2, null);
				
		init(view);    	
    	
    	cbInvestEveryyear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    	       @Override
    	       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    	    	   if(cbInvestEveryyear.isChecked()) {
    	    		   investEveryYear = true;
    	    	   }
    	    	   else {
    	    		   investEveryYear = false;
    	    	   }
    	       }
    	   }
    	);  
    	
    	// Radio Buttons Event Listener
    	rbReturnOnInvestment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bReturnOnInvestment = true;
				bAnnualRate = false;
				bNoOfYears = false;
				
				rbSelected(bReturnOnInvestment,bAnnualRate,bNoOfYears);
			}
		});
    	
    	rbAnnualRate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bReturnOnInvestment = false;
				bAnnualRate = true;
				bNoOfYears = false;
				
				rbSelected(bReturnOnInvestment,bAnnualRate,bNoOfYears);
			}
		});
    	
    	rbNoOfYears.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bReturnOnInvestment = false;
				bAnnualRate = false;
				bNoOfYears = true;
				
				rbSelected(bReturnOnInvestment,bAnnualRate,bNoOfYears);
			}
		});
    	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reset();
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					
					lPrincipalAmount = Double.parseDouble(ePrincipalAmount.getText().toString());
					investEveryYear = cbInvestEveryyear.isChecked();
					dNoOfYears = Double.parseDouble(eNoOfYears.getText().toString());
					lAnnualRate = Double.parseDouble(eAnnualRate.getText().toString());
					lReturnOnInvestment = Double.parseDouble(eReturnOnInvestment.getText().toString());
					lAvgInflationRate = Double.parseDouble(eAvgInflationRate.getText().toString());
					String messageToDisplay = "";
					
					DecimalFormat df = new DecimalFormat("#.000");
					
					if(bReturnOnInvestment) {
						lReturnOnInvestment = oFinanceCalcs.calculateROI(lPrincipalAmount,lAnnualRate,dNoOfYears,investEveryYear);
						eReturnOnInvestment.setText(df.format(lReturnOnInvestment).toString());
						
						double tempPresentValue = oFinanceCalcs.calculatePresentValue(lReturnOnInvestment, lAvgInflationRate, dNoOfYears);
						messageToDisplay = "The present value for " + 
											df.format(lReturnOnInvestment).toString() +
											" after " + eNoOfYears.getText().toString() + 
											" years is " +  df.format(tempPresentValue).toString();
						tvMessage.setText(messageToDisplay);
						
						showOutputText(eReturnOnInvestment, tvReturnOnInvestment);
						hideRadioButtons();
					}
					
					if(bNoOfYears) {
						dNoOfYears = oFinanceCalcs.calculateNoOfYears(lPrincipalAmount, lAnnualRate, lReturnOnInvestment, investEveryYear);
						eNoOfYears.setText(df.format(dNoOfYears).toString());
						
						messageToDisplay = "You will need " + df.format(dNoOfYears) + " years to get a " + lAnnualRate + " return on your investment of " + 
											lPrincipalAmount;
						tvMessage.setText(messageToDisplay);
						
						showOutputText(eNoOfYears, tvNoOfYears);
						hideRadioButtons();
					}
					
					if( bAnnualRate ) {
						lAnnualRate = oFinanceCalcs.calculateAnnualRate(lPrincipalAmount, lReturnOnInvestment, dNoOfYears, investEveryYear)*100;
						eAnnualRate.setText(df.format(lAnnualRate).toString());
						
						messageToDisplay = "We will need an annual rate of " + df.format(lAnnualRate) + "% for " + dNoOfYears + " years to get a return of " +
											lReturnOnInvestment;
						tvMessage.setText(messageToDisplay);
						
						showOutputText(eAnnualRate, tvAnnualRate);
						hideRadioButtons();						
					}
				}
			}
		});
    	
    	return view;
	}
	
	public static void init(View view) {
    	eReturnOnInvestment = (EditText)view.findViewById(R.id.editTextReturnOnInvestment);
    	tvReturnOnInvestment = (TextView)view.findViewById(R.id.textViewReturnOnInvestment);
    	
		ePrincipalAmount = (EditText)view.findViewById(R.id.editTextPrincipalAmnt);
    	tvPrincipalAmount = (TextView)view.findViewById(R.id.textViewPrincipalAmnt);
		cbInvestEveryyear = (CheckBox)view.findViewById(R.id.checkBoxInvestAnnually);
    	
		eNoOfYears = (EditText)view.findViewById(R.id.editTextNoOfYears);
    	tvNoOfYears = (TextView)view.findViewById(R.id.textViewNoOfYears);
    	
    	eAnnualRate = (EditText)view.findViewById(R.id.editTextAnnualRate);
    	tvAnnualRate = (TextView)view.findViewById(R.id.textViewAnnualRate);
    	
    	eAvgInflationRate = (EditText)view.findViewById(R.id.editTextInflationRate);
    	tvAvgInflationRate = (TextView)view.findViewById(R.id.textViewAvgInflation);
    	
    	buttonCalculate = (Button)view.findViewById(R.id.bCalculateROI);
    	buttonReset = (Button)view.findViewById(R.id.bResetROI);
    	tvMessage = (TextView)view.findViewById(R.id.textViewMessageROI);
    	
    	rbReturnOnInvestment = (RadioButton)view.findViewById(R.id.radioButtonCalROI);
    	rbNoOfYears = (RadioButton)view.findViewById(R.id.radioButtonNoOfYears);
    	rbAnnualRate = (RadioButton)view.findViewById(R.id.radioButtonAnnualRate);
    	
    	rbReturnOnInvestment.setChecked(true);
    	rbNoOfYears.setChecked(false);
    	rbAnnualRate.setChecked(false);
    	
    	hideOutputText(eReturnOnInvestment, tvReturnOnInvestment);
    	
	}
	
	public static void hideOutputText (EditText givenET, TextView givenTV) {
		givenET.setVisibility(View.GONE);
		givenTV.setVisibility(View.GONE);
	}
	
	public static void showOutputText (EditText givenET, TextView givenTV) {
		givenET.setVisibility(View.VISIBLE);
		givenTV.setVisibility(View.VISIBLE);
	}
	
	public static void reset() {
		double valueOutput = 0;
		double indiaAnnualRate = 10.00;
		int numOfYears = 10;
		DecimalFormat df = new DecimalFormat("#.00");
		double dAvgInflationRate = 3.5;
		
		ePrincipalAmount.setText(df.format(valueOutput)) ;
		cbInvestEveryyear.setChecked(false);
		eNoOfYears.setText(Double.toString(numOfYears)) ;
		eAnnualRate.setText(df.format(indiaAnnualRate)) ;
		eReturnOnInvestment.setText(df.format(valueOutput)) ;
		eAvgInflationRate.setText(df.format(dAvgInflationRate));
		tvMessage.setText("");
		
		bReturnOnInvestment = true;
		bAnnualRate = false;
		bNoOfYears = false;
		
		rbSelected(bReturnOnInvestment, bAnnualRate, bNoOfYears);
		showRadioButtons();
	}
	
	public static void hideRadioButtons() {
		rbAnnualRate.setVisibility(View.GONE);
		rbNoOfYears.setVisibility(View.GONE);
		rbReturnOnInvestment.setVisibility(View.GONE);
	}
	
	public static void showRadioButtons() {
		rbAnnualRate.setVisibility(View.VISIBLE);
		rbNoOfYears.setVisibility(View.VISIBLE);
		rbReturnOnInvestment.setVisibility(View.VISIBLE);
		
	}
	
	public static void rbSelected( boolean givenROI, boolean givenAnnualRate, 
								   boolean givenNoOfYears) {
		if( givenROI ) {
			rbReturnOnInvestment.setChecked(true);
			rbAnnualRate.setChecked(false);
			rbNoOfYears.setChecked(false);
		}
		
		if( givenAnnualRate ) {
			rbReturnOnInvestment.setChecked(false);
			rbAnnualRate.setChecked(true);
			rbNoOfYears.setChecked(false);
		}
		
		if( givenNoOfYears ) {
			rbReturnOnInvestment.setChecked(false);
			rbAnnualRate.setChecked(false);
			rbNoOfYears.setChecked(true);
		}
	
		displayForRBSelected(bReturnOnInvestment,bAnnualRate,bNoOfYears);
		
	}
	
	public static void displayForRBSelected ( boolean givenROI, boolean givenAnnualRate, 
											  boolean givenNoOfYears) {
		if( givenROI ) {
			hideOutputText(eReturnOnInvestment, tvReturnOnInvestment);
			showOutputText(eAnnualRate, tvAnnualRate);
			showOutputText(eNoOfYears, tvNoOfYears);
			showOutputText(ePrincipalAmount, tvPrincipalAmount);
		}
		
		if( givenAnnualRate ) {
			showOutputText(eReturnOnInvestment, tvReturnOnInvestment);
			hideOutputText(eAnnualRate, tvAnnualRate);
			showOutputText(eNoOfYears, tvNoOfYears);
			showOutputText(ePrincipalAmount, tvPrincipalAmount);
		}
		
		if( givenNoOfYears ) {
			showOutputText(eReturnOnInvestment, tvReturnOnInvestment);
			showOutputText(eAnnualRate, tvAnnualRate);
			hideOutputText(eNoOfYears, tvNoOfYears);
			showOutputText(ePrincipalAmount, tvPrincipalAmount);
		}
		
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(ePrincipalAmount.getText().toString()) ) {
			ePrincipalAmount.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eAnnualRate.getText().toString()) ) {
			eAnnualRate.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eNoOfYears.getText().toString()) ) {
			eNoOfYears.setText("Enter Numbers."); error = true;
		}
		
		return error;
	}
}
