package com.mishin870.xenoseus;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mishin870.xenoseus.gif.MovieGifView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private static final int TABLE_HEADER_COLOR = Color.parseColor("#607D8B");
	private static final int TABLE_ROW_COLOR_1 = Color.parseColor("#C0DDEB");
	private static final int TABLE_ROW_COLOR_2 = Color.parseColor("#B0CDDB");
	private static final int TABLE_ROW_PADDING = 10;
	private static final String SITE_ADDR = "http://192.168.43.214:8080/";
	
	private static final int STATE_NONE = -1;
	private static final int STATE_SHOP = 0;
	private static final int STATE_SEASON = 1;
	private static final int STATE_PRODUCT = 2;
	private static final int STATE_OPERATION = 3;
	private static final int STATE_GRAPH = 4;
	private static final int STATE_PRODUCT_GRAPH = 5;
	/**
	 * Текущее состояние state-машины
	 */
	private int state = STATE_NONE;
	
	public static MainActivity instance;
	
	private boolean isWaitingPagination = false;
	private int paginationPage = 0;
	
	private View tableView;
	private View graphView;
	private ScrollView tableScroll;
	private MovieGifView preloader;
	
	public MainActivity() {
		instance = this;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		tableView = findViewById(R.id.table);
		graphView = findViewById(R.id.graphicView);
		
		tableScroll = findViewById(R.id.tableScroll);
		tableScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
			@Override
			public void onScrollChanged() {
				if (!isWaitingPagination && tableScroll.getChildAt(0).getBottom() <= (tableScroll.getHeight() + tableScroll.getScrollY() + 200)) {
					paginate();
				}
			}
		});
		
		preloader = new MovieGifView(this, R.drawable.preloader);
		
		setTable(false);
		setGraphic(false);
		setPreloader(false);
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		/*FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Привет!", Snackbar.LENGTH_LONG)
						.setAction("Действие", null).show();
			}
		});*/
		
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar,
				R.string.navigation_drawer_open, R.string.navigation_drawer_close
		);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		drawer.addView(preloader, 0);
		
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
	
	/**
	 * Попытаться загрузить следующую страницу пагинации
	 */
	private void paginate() {
		if (paginationPage == -1 || state == STATE_NONE)
			return;
		setPreloader(true);
		isWaitingPagination = true;
		paginationPage++;
		switch (state) {
			case STATE_SHOP:
				new JsonGETAsyncTask(
						SITE_ADDR + "shops/" + paginationPage,
						new JsonGETAsyncTask.ICommand() {
							@Override
							public void execute(Object object) {
								JSONArray array = (JSONArray) object;
								if (array.length() == 0)
									paginationPage = -1;
								appendTableOnUiThread(array);
								setPreloader(false);
							}
						}
				).start();
				break;
			case STATE_SEASON:
				new JsonGETAsyncTask(
						SITE_ADDR + "seasons/" + paginationPage,
						new JsonGETAsyncTask.ICommand() {
							@Override
							public void execute(Object object) {
								JSONArray array = (JSONArray) object;
								if (array.length() == 0)
									paginationPage = -1;
								appendTableOnUiThread(array);
								setPreloader(false);
							}
						}
				).start();
				break;
			case STATE_OPERATION:
				new JsonGETAsyncTask(
						SITE_ADDR + "operations/" + paginationPage,
						new JsonGETAsyncTask.ICommand() {
							@Override
							public void execute(Object object) {
								JSONArray array = (JSONArray) object;
								if (array.length() == 0)
									paginationPage = -1;
								appendTableOnUiThread(array);
								setPreloader(false);
							}
						}
				).start();
				break;
			case STATE_PRODUCT:
				new JsonGETAsyncTask(
						SITE_ADDR + "products/" + paginationPage,
						new JsonGETAsyncTask.ICommand() {
							@Override
							public void execute(Object object) {
								JSONArray array = (JSONArray) object;
								if (array.length() == 0)
									paginationPage = -1;
								appendTableOnUiThread(array);
								setPreloader(false);
							}
						}
				).start();
				break;
		}
	}
	
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		
		setTable(false);
		setGraphic(false);
		setPreloader(true);
		
		switch (item.getItemId()) {
			case R.id.shops:
				state = STATE_SHOP;
				new JsonGETAsyncTask(
					SITE_ADDR + "shops/0",
					new JsonGETAsyncTask.ICommand() {
						@Override
						public void execute(Object object) {
							JSONArray array = (JSONArray) object;
							paginationPage = array.length() == 0 ? -1 : 0;
							fillTableOnUiThread(array, "id", "Имя");
							setPreloader(false);
						}
					}
				).start();
				break;
			case R.id.seasons:
				state = STATE_SEASON;
				new JsonGETAsyncTask(
					SITE_ADDR + "seasons/0",
					new JsonGETAsyncTask.ICommand() {
						@Override
						public void execute(Object object) {
							JSONArray array = (JSONArray) object;
							paginationPage = array.length() == 0 ? -1 : 0;
							fillTableOnUiThread(array, "id", "Имя");
							setPreloader(false);
						}
					}
				).start();
				break;
			case R.id.operations:
				state = STATE_OPERATION;
				new JsonGETAsyncTask(
					SITE_ADDR + "operations/0",
					new JsonGETAsyncTask.ICommand() {
						@Override
						public void execute(Object object) {
							JSONArray array = (JSONArray) object;
							paginationPage = array.length() == 0 ? -1 : 0;
							fillTableOnUiThread(array, "id", "Заказ", "Дата", "Магазин", "Артикул", "Цвет", "Размер", "Сезон", "Количество", "Сумма");
							setPreloader(false);
						}
					}
				).start();
				break;
			case R.id.products:
				state = STATE_PRODUCT;
				new JsonGETAsyncTask(
					SITE_ADDR + "products/0",
					new JsonGETAsyncTask.ICommand() {
						@Override
						public void execute(Object object) {
							JSONArray array = (JSONArray) object;
							paginationPage = array.length() == 0 ? -1 : 0;
							fillTableOnUiThread(array, "id", "Сезон", "Артикул", "Цвет", "Размер", "Штрих-код");
							setPreloader(false);
						}
					}
				).start();
				break;
			case R.id.graphic:
				state = STATE_GRAPH;
				setPreloader(false);
				break;
			case R.id.productsProfit:
				state = STATE_PRODUCT_GRAPH;
				setPreloader(false);
				break;
		}
		
		return true;
	}
	
	/**
	 * Добавить записи в таблицу
	 * @param array массив записей
	 */
	private void appendTableOnUiThread(final JSONArray array) {
		MainActivity.this.runOnUiThread(
				new Runnable() {
					@Override
					public void run() {
						try {
							setTable(true);
							appendTable(array);
							isWaitingPagination = false;
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}
				}
		);
	}
	
	/**
	 * Запустить заполнение таблицы в основном потоке
	 * @param array массив данных
	 * @param headers заголовки хедера
	 */
	private void fillTableOnUiThread(final JSONArray array, final String... headers) {
		MainActivity.this.runOnUiThread(
			new Runnable() {
				@Override
				public void run() {
					try {
						setTable(true);
						fillTable(array, headers);
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			}
		);
	}
	
	/**
	 * Добавить записи в таблицу
	 * @param array массив
	 */
	private void appendTable(JSONArray array) throws JSONException {
		LinearLayout tableLayout = (LinearLayout) tableView;
		
		boolean isFirst = true;
		for (int i = 0; i < array.length(); i++) {
			TableRow row = new TableRow(this);
			TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(layoutParams);
			JSONObject object = array.getJSONObject(i);
			Iterator<String> keysIter = object.keys();
			while (keysIter.hasNext()) {
				String key = keysIter.next();
				TextView rowLabel = new TextView(this);
				rowLabel.setText(String.valueOf(object.get(key)));
				rowLabel.setTextSize(20);
				row.setPadding(TABLE_ROW_PADDING * 2, TABLE_ROW_PADDING, TABLE_ROW_PADDING * 2, TABLE_ROW_PADDING);
				row.addView(rowLabel);
			}
			
			row.setBackgroundColor(isFirst ? TABLE_ROW_COLOR_1 : TABLE_ROW_COLOR_2);
			isFirst = !isFirst;
			
			tableLayout.addView(row);
		}
	}
	
	/**
	 * Заполнить таблицу с помощью JSONArray
	 * @param array массив
	 */
	private void fillTable(JSONArray array, String... headers) throws JSONException {
		LinearLayout tableLayout = (LinearLayout) tableView;
		tableLayout.removeAllViews();
		
		TableRow header = new TableRow(this);
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
		header.setLayoutParams(layoutParams);
		header.setBackgroundColor(TABLE_HEADER_COLOR);
		for (int i = 0; i < headers.length; i++) {
			TextView headerLabel = new TextView(this);
			headerLabel.setText(headers[i]);
			headerLabel.setTextSize(20);
			headerLabel.setPadding(TABLE_ROW_PADDING * 2, TABLE_ROW_PADDING, TABLE_ROW_PADDING * 2, TABLE_ROW_PADDING);
			header.addView(headerLabel);
		}
		tableLayout.addView(header);
		
		boolean isFirst = true;
		for (int i = 0; i < array.length(); i++) {
			TableRow row = new TableRow(this);
			layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(layoutParams);
			JSONObject object = array.getJSONObject(i);
			Iterator<String> keysIter = object.keys();
			while (keysIter.hasNext()) {
				String key = keysIter.next();
				TextView rowLabel = new TextView(this);
				rowLabel.setText(String.valueOf(object.get(key)));
				rowLabel.setTextSize(20);
				row.setPadding(TABLE_ROW_PADDING * 2, TABLE_ROW_PADDING, TABLE_ROW_PADDING * 2, TABLE_ROW_PADDING);
				row.addView(rowLabel);
			}
			
			row.setBackgroundColor(isFirst ? TABLE_ROW_COLOR_1 : TABLE_ROW_COLOR_2);
			isFirst = !isFirst;
			
			tableLayout.addView(row);
		}
	}
	
	/**
	 * Установить видимость View графика
	 * @param state видимость
	 */
	private void setGraphic(boolean state) {
		graphView.setVisibility(state ? View.VISIBLE : View.GONE);
	}
	
	/**
	 * Установить видимость View таблицы
	 * @param state видимость
	 */
	private void setTable(boolean state) {
		tableView.setVisibility(state ? View.VISIBLE : View.GONE);
	}
	
	/**
	 * Установить видимость прелоадера
	 * @param state видимость
	 */
	private void setPreloader(boolean state) {
		if (state) {
			Point size = new Point();
			getWindowManager().getDefaultDisplay().getSize(size);
			preloader.x = (size.x - preloader.getWidth()) / 2;
			preloader.y = (size.y - preloader.getHeight()) / 2;
			
			preloader.setVisibility(View.VISIBLE);
			preloader.bringToFront();
		} else {
			preloader.setVisibility(View.GONE);
		}
	}
}
