package com.android.slidingmenu;

import java.text.DecimalFormat;

import com.android.slidingmenu.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class FutureValue extends Fragment {


	private static Button buttonCalculate;
	private static Button buttonReset;
	private static EditText ePresentValue;
	private static TextView tvPresentValue;
	private static EditText eFutureValue;
	private static TextView tvFutureValue;
	private static EditText eInflationRate;
	private static EditText eNoOfYears;
	private static TextView tvNoOfYears;
	private static TextView tvPFV_MessageDisplay;
	
	private static RadioButton rbFutureValue;
	private static RadioButton rbPresentValue;
	private static RadioButton rbNoOfYears;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();	

	public static double lInflationRate =0;
	public static double lNumOfYears=0;
	public static double lFutureValue=0;
	public static double lPresentValue=0;	
	
	private static boolean bFutureValue = true;
	private static boolean bPresentValue = false;
	private static boolean bNoOfYears= false;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout1, null);
        
        init(view);
        
        // Radio Buttons Event Listener
    	rbFutureValue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bFutureValue = true;
				bPresentValue = false;
				bNoOfYears = false;
				
				rbSelected(bFutureValue,bPresentValue,bNoOfYears);
			}
		});
    	
    	rbPresentValue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bFutureValue = false;
				bPresentValue = true;
				bNoOfYears = false;
				
				rbSelected(bFutureValue,bPresentValue,bNoOfYears);
			}
		});
    	
    	rbNoOfYears.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bFutureValue = false;
				bPresentValue = false;
				bNoOfYears = true;
				
				rbSelected(bFutureValue,bPresentValue,bNoOfYears);
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
					lInflationRate = Double.parseDouble(eInflationRate.getText().toString());
					lNumOfYears = Double.parseDouble(eNoOfYears.getText().toString());
					lFutureValue = Double.parseDouble(eFutureValue.getText().toString());
					lPresentValue = Double.parseDouble(ePresentValue.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.000");
					
					if( bPresentValue ) {
						lPresentValue = oFinanceCalcs.calculatePresentValue(lFutureValue,lInflationRate,lNumOfYears);
						ePresentValue.setText(df.format(lPresentValue)) ;
					}
					
					if(bFutureValue) {
						lFutureValue = oFinanceCalcs.calculateFutureValue(lPresentValue,lInflationRate,lNumOfYears);
						eFutureValue.setText(df.format(lFutureValue)) ;
						showOutputText(eFutureValue, tvFutureValue);
					}
				}
			}
		});
		
        return view;
    }

	public static void init(View view) {
		buttonCalculate = (Button)view.findViewById(R.id.bCalculate);
    	buttonReset = (Button)view.findViewById(R.id.bReset);
    	
    	ePresentValue = (EditText)view.findViewById(R.id.editPresentValue);
    	tvPresentValue = (TextView)view.findViewById(R.id.textViewPresentValue);
    	
    	eFutureValue = (EditText)view.findViewById(R.id.editFutureValue);
    	tvFutureValue = (TextView)view.findViewById(R.id.textFutureValue);
    	
    	eInflationRate = (EditText)view.findViewById(R.id.editInflationRate);
    	
    	eNoOfYears = (EditText)view.findViewById(R.id.EditNoOfYears);
    	tvNoOfYears = (TextView)view.findViewById(R.id.textNoOfYears);
    	
    	tvPFV_MessageDisplay = (TextView)view.findViewById(R.id.textViewPFV_Message);
    	
    	rbFutureValue = (RadioButton)view.findViewById(R.id.radioButtonFutureValue);
    	rbPresentValue = (RadioButton)view.findViewById(R.id.radioButtonPresentValue);
    	rbNoOfYears = (RadioButton)view.findViewById(R.id.radioButtonNumOfYears_PFVal);
    	
    	rbFutureValue.setChecked(true);
    	rbPresentValue.setChecked(false);
    	rbNoOfYears.setChecked(false);
    	
    	hideOutputText(eFutureValue, tvFutureValue);
	}
    
	public static void hideOutputText (EditText givenET, TextView givenTV) {
		givenET.setVisibility(View.GONE);
		givenTV.setVisibility(View.GONE);
	}
	
	public static void showOutputText (EditText givenET, TextView givenTV) {
		givenET.setVisibility(View.VISIBLE);
		givenTV.setVisibility(View.VISIBLE);
	}
	
	public static void rbSelected( boolean givenFutureValue, boolean givenPresentValue,  boolean givenNoOfYears) {
		if( givenFutureValue ) {
			rbFutureValue.setChecked(true);
			rbPresentValue.setChecked(false);
			rbNoOfYears.setChecked(false);
		}
		
		if( givenPresentValue ) {
			rbFutureValue.setChecked(false);
			rbPresentValue.setChecked(true);
			rbNoOfYears.setChecked(false);
		}
		
		if( givenNoOfYears ) {
			rbFutureValue.setChecked(false);
			rbPresentValue.setChecked(false);
			rbNoOfYears.setChecked(true);
		}
		
		displayForRBSelected(givenFutureValue,givenPresentValue,bNoOfYears);
	}
	
	public static void reset() {
		double valueOutput = 0;
		double indiaInflationRate = 12.35;
		double numOfYears = 20;
		DecimalFormat df = new DecimalFormat("#.00");
		
		ePresentValue.setText(df.format(valueOutput)) ;
		eFutureValue.setText(df.format(valueOutput)) ;
		eInflationRate.setText(df.format(indiaInflationRate)) ;
		eNoOfYears.setText(df.format(numOfYears)) ;
		tvPFV_MessageDisplay.setText("");
		
		bFutureValue = true;
		bPresentValue = false;
		bNoOfYears = false;
		
		rbSelected(bFutureValue, bPresentValue, bNoOfYears);
		showRadioButtons();
	}
	
	public static void hideRadioButtons() {
		rbFutureValue.setVisibility(View.GONE);
		rbPresentValue.setVisibility(View.GONE);
		rbNoOfYears.setVisibility(View.GONE);
	}
	
	public static void showRadioButtons() {
		rbFutureValue.setVisibility(View.VISIBLE);
		rbPresentValue.setVisibility(View.VISIBLE);
		rbNoOfYears.setVisibility(View.VISIBLE);
	}
	
	public static void displayForRBSelected ( boolean givenFutureValue, boolean givenPresentValue, boolean givenNoOfYears) {
		if( givenFutureValue ) {
			hideOutputText(eFutureValue, tvFutureValue);
			showOutputText(ePresentValue, tvPresentValue);
			showOutputText(eNoOfYears, tvNoOfYears);
		}
		
		if( givenPresentValue ) {
			showOutputText(eFutureValue, tvFutureValue);
			hideOutputText(ePresentValue, tvPresentValue);
			showOutputText(eNoOfYears, tvNoOfYears);
		}
		
		if( givenNoOfYears ) {
			showOutputText(eFutureValue, tvFutureValue);
			showOutputText(ePresentValue, tvPresentValue);
			hideOutputText(eNoOfYears, tvNoOfYears);
		}
	}
	
	public boolean validateData() {
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(ePresentValue.getText().toString()) ) {
			ePresentValue.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eFutureValue.getText().toString()) ) {
			eFutureValue.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eInflationRate.getText().toString()) ) {
			eInflationRate.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eNoOfYears.getText().toString()) ) {
			eNoOfYears.setText("Enter Numbers."); error = true;
		}
		
		return error;
	}

}
