package com.android.slidingmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.CorpFinance.AssetTurnover;
import com.android.CorpFinance.CAPMRiskFree;
import com.android.CorpFinance.FinancialAnalysis;
import com.android.CorpFinance.FisherInflation;
import com.android.CorpFinance.ImpactOfDebt;
import com.android.CorpFinance.Payback;
import com.android.CorpFinance.WACC;

public class CorpFinance extends FragmentActivity {

	MainLayout mLayout;
	private ListView lvMenu;
	private String[] lvMenuItems;
	Button btMenu;
	TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLayout = (MainLayout) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);
		setContentView(mLayout);

		lvMenuItems = getResources().getStringArray(R.array.menu_items);
		lvMenu = (ListView) findViewById(R.id.menu_listview);
		lvMenu.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lvMenuItems));
		lvMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onMenuItemClick(parent, view, position, id);
			}

		});

		btMenu = (Button) findViewById(R.id.button_menu);
		btMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show/hide the menu
				toggleMenu(v);
			}
		});

		tvTitle = (TextView) findViewById(R.id.activity_main_content_title);

		FragmentManager fm = CorpFinance.this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		FutureValue fragment = new FutureValue();
		ft.add(R.id.activity_main_content_fragment, fragment);
		ft.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void toggleMenu(View v) {
		mLayout.toggleMenu();
	}

	private void onMenuItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		String selectedItem = lvMenuItems[position];
		String currentItem = tvTitle.getText().toString();
		if (selectedItem.compareTo(currentItem) == 0) {
			mLayout.toggleMenu();
			return;
		}

		FragmentManager fm = CorpFinance.this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;

		if (selectedItem.compareTo("Present/Future Value") == 0) {
			fragment = new FutureValue();
		} else if (selectedItem.compareTo("Compounding Interest") == 0) {
			fragment = new CompoundingInterest();
		} else if (selectedItem.compareTo("Net Worth") == 0) {
			fragment = new NetWorth();
		}else if (selectedItem.compareTo("Payback") == 0) {
			fragment = new Payback();
		}else if (selectedItem.compareTo("Financial Analysis") == 0) {
			fragment = new FinancialAnalysis();
		}else if (selectedItem.compareTo("CAPM Risk Free") == 0) {
			fragment = new CAPMRiskFree();
		}else if (selectedItem.compareTo("Impact Of Debt(leverage)") == 0) {
			fragment = new ImpactOfDebt();
		}else if (selectedItem.compareTo("Asset Turnover") == 0) {
			fragment = new AssetTurnover();
		}else if (selectedItem.compareTo("WACC") == 0) {
			fragment = new WACC();
		}else if (selectedItem.compareTo("Fisher and Inflation") == 0) {
			fragment = new FisherInflation();
		}

		if (fragment != null) {
			ft.replace(R.id.activity_main_content_fragment, fragment);
			ft.commit();
			tvTitle.setText(selectedItem);
		}
		mLayout.toggleMenu();
	}

	@Override
	public void onBackPressed() {
		if (mLayout.isMenuShown()) {
			mLayout.toggleMenu();
		} else {
			super.onBackPressed();
		}
	}
}
