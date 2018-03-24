package com.mishin870.xenoseus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Привет!", Snackbar.LENGTH_LONG)
						.setAction("Действие", null).show();
			}
		});
		
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}
	
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings)
			return true;
		
		return super.onOptionsItemSelected(item);
	}
	
	public void setLayout(int id) {
		View C = findViewById(R.id.C);
		ViewGroup parent = (ViewGroup) C.getParent();
		int index = parent.indexOfChild(C);
		parent.removeView(C);
		C = getLayoutInflater().inflate(optionId, parent, false);
		parent.addView(C, index);
	}
	
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.shops:
				Toast.makeText(getApplicationContext(), "Shops!", Toast.LENGTH_SHORT).show();
				break;
			case R.id.seasons:
				Toast.makeText(getApplicationContext(), "Seasons!", Toast.LENGTH_SHORT).show();
				break;
			case R.id.operations:
				Toast.makeText(getApplicationContext(), "Operations!", Toast.LENGTH_SHORT).show();
				break;
			case R.id.products:
				Toast.makeText(getApplicationContext(), "Products!", Toast.LENGTH_SHORT).show();
				break;
			case R.id.graphic:
				Toast.makeText(getApplicationContext(), "Graphic!", Toast.LENGTH_SHORT).show();
				break;
			case R.id.productsProfit:
				Toast.makeText(getApplicationContext(), "Complex graphic!", Toast.LENGTH_SHORT).show();
				break;
		}
		
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
