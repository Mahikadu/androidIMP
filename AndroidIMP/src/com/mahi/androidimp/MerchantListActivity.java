//package com.mahi.androidimp;
//
//import java.util.ArrayList;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//
//import com.facebook.Session;
//import com.mobiwebcode.pgfh.HomeScreen.myTaskAllFeedsList_Call;
//import com.mobiwebcode.pgfh.Vo.AdvtpostVo;
//import com.mobiwebcode.pgfh.Vo.AnnouncementpostVo;
//import com.mobiwebcode.pgfh.Vo.FoodpostVo;
//import com.mobiwebcode.pgfh.Vo.MainpostVo;
//import com.mobiwebcode.pgfh.Vo.MerchantVO;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.util.TypedValue;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.TextView;
//
//public class MerchantListActivity extends Activity {
//	XMLParser parser = new XMLParser();
//	String xml = "";
//	NodeList nl;
//	Document doc;
//	// XMLParser parser = new XMLParser();
//	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
//	private ProgressDialog mProgressDialog;
//	// ArrayList<MerchantVO> merchantList = new ArrayList<MerchantVO>();
//	String MERCHANT_LIST_URL = "http://pfh.com.my/pgfh/merchantlist.php";
//	LinearLayout merchantLinearLayout;
//
//	ArrayList<MerchantVO> merchantList = new ArrayList<MerchantVO>();
//	public ArrayList<MerchantVO> currentFeedList = new ArrayList<MerchantVO>();
//
//	FrameLayout mainFrameLyout;
//	FrameLayout.LayoutParams menuPanelParameters;
//	FrameLayout.LayoutParams slidingPanelParameters;
//	RelativeLayout mainRelativeLayout;
//	LinearLayout.LayoutParams headerPanelParameters;
//	LinearLayout.LayoutParams listViewParameters;
//	Button menuBtn, clearable_button_clear;
//	private boolean isExpanded;
//	private DisplayMetrics metrics;
//	private RelativeLayout slidingPanel;
//	int panelWidth = 0;
//
//	EditText searchEditText;
//	public static String searchKeyword = "";
//
//	Constants cont = null;
//
//	protected Dialog onCreateDialog(int id) {
//		switch (id) {
//		case DIALOG_DOWNLOAD_PROGRESS1:
//			mProgressDialog = new ProgressDialog(this);
//			mProgressDialog.setMessage("Loading, please wait ...");
//			mProgressDialog.setCancelable(false);
//			mProgressDialog.show();
//			return mProgressDialog;
//
//		default:
//			return null;
//		}
//	}
//
//	class myTask_fetch_merchant_call extends AsyncTask<String, Void, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
//		}
//
//		protected String doInBackground(String... aurl) {
//			try {
//
//				xml = parser.getXmlFromUrl(MERCHANT_LIST_URL);
//				doc = parser.getDomElement(xml); // getting
//				if (doc != null) {
//					nl = doc.getElementsByTagName("merchant");
//					merchantList.clear();
//					// looping through all item nodes <item>
//					for (int i = 0; i < nl.getLength(); i++) {
//						Element e = (Element) nl.item(i);
//						MerchantVO mvo = new MerchantVO();
//						mvo.merchantid = parser.getValue(e, "merchantid");
//						mvo.merchantname = parser.getValue(e, "merchantname");
//						mvo.merchantdescription = parser.getValue(e,
//								"merchantdescription");
//						merchantList.add(mvo);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return "";
//		}
//
//		protected void onPostExecute(String str_resp) {
//			try {
//				currentFeedList = merchantList;
//				fillUI();
//				if (mProgressDialog != null)
//					mProgressDialog.dismiss();
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//	}
//
//	void fillUI() {
//		try {
//			merchantLinearLayout = (LinearLayout) findViewById(R.id.merchantLinearLayout);
//			merchantLinearLayout.removeAllViews();
//			for (int count = 0; count < currentFeedList.size(); count++) {
//				MerchantVO merchantVO = currentFeedList.get(count);
//				LinearLayout merchantInfoLinearLayout = new LinearLayout(
//						MerchantListActivity.this);
//				merchantInfoLinearLayout.setOrientation(LinearLayout.VERTICAL);
//				LayoutParams merchantInfoLinearLayout_LayoutParams = new LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//				merchantInfoLinearLayout_LayoutParams.setMargins(5, 5, 5, 5);
//				merchantInfoLinearLayout
//						.setLayoutParams(merchantInfoLinearLayout_LayoutParams);
//
//				// Merchant name textview
//				TextView merchantnameTextView = new TextView(
//						MerchantListActivity.this);
//				merchantnameTextView.setTypeface(null, Typeface.BOLD);
//				merchantnameTextView.setTextColor(Color.parseColor("#00C5CD"));
//				merchantnameTextView.setText(merchantVO.merchantname);
//				LayoutParams merchantnameTextView_LayoutParams = new LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//				merchantnameTextView_LayoutParams.setMargins(5, 0, 0, 5);
//				merchantnameTextView
//						.setLayoutParams(merchantnameTextView_LayoutParams);
//
//				// merchant description text view
//				TextView merchantdescriptionTextView = new TextView(
//						MerchantListActivity.this);
//				merchantdescriptionTextView.setTypeface(null, Typeface.BOLD);
//				merchantdescriptionTextView.setTextColor(Color.BLACK);
//				merchantdescriptionTextView
//						.setText(merchantVO.merchantdescription);
//				LayoutParams merchantdescriptionTextView_LayoutParams = new LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//				merchantdescriptionTextView_LayoutParams.setMargins(5, 0, 0, 5);
//				merchantdescriptionTextView
//						.setLayoutParams(merchantdescriptionTextView_LayoutParams);
//
//				merchantInfoLinearLayout.addView(merchantnameTextView);
//				merchantInfoLinearLayout.addView(merchantdescriptionTextView);
//
//				merchantLinearLayout.addView(merchantInfoLinearLayout);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//	}
//
//	void keywordSearch(String searchKeyword) {
//		currentFeedList = new ArrayList<MerchantVO>();
//		for (int count = 0; count < merchantList.size(); count++) {
//			MerchantVO mVo = merchantList.get(count);
//
//			if (mVo.merchantname.toLowerCase().contains(
//					searchKeyword.toLowerCase()))
//				currentFeedList.add(mVo);
//			else if (mVo.merchantdescription.toLowerCase().contains(
//					searchKeyword.toLowerCase()))
//				currentFeedList.add(mVo);
//
//		}
//		fillUI();
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.merchantlist);
//
//		cont = Constants.getinstance(MerchantListActivity.this);
//
//		searchEditText = (EditText) findViewById(R.id.searchkey);
//		searchEditText.setText("");
//		MerchantListActivity.this.getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//		searchEditText
//				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//					@Override
//					public boolean onEditorAction(TextView v, int actionId,
//							KeyEvent event) {
//						if (actionId == EditorInfo.IME_ACTION_SEARCH)
//							searchKeyword = searchEditText.getText().toString();
//						if (!searchEditText.getText().toString().equals("")) {
//							keywordSearch(searchEditText.getText().toString());
//						} else {
//							currentFeedList = merchantList;
//							fillUI();
//						}
//						return false;
//					}
//				});
//
//		clearable_button_clear = (Button) findViewById(R.id.clearable_button_clear);
//		clearable_button_clear.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				searchEditText.setText("");
//				currentFeedList = merchantList;
//				fillUI();
//				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
//			}
//		});
//		if (cont.isOnline()) {
//			new myTask_fetch_merchant_call().execute("");
//		} else {
//			final Dialog dialog = new Dialog(MerchantListActivity.this);
//			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.setContentView(R.layout.nointernetconnection);
//
//			Button dialogButton = (Button) dialog
//					.findViewById(R.id.dialogButtonOK);
//
//			dialogButton.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					dialog.dismiss();
//				}
//			});
//
//			dialog.show();
//
//		}
//
//		metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		panelWidth = (int) ((metrics.widthPixels) * 0.75);
//		slidingPanel = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
//		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
//				.getLayoutParams();
//		slidingPanelParameters.width = metrics.widthPixels;
//		slidingPanel.setLayoutParams(slidingPanelParameters);
//
//		menuBtn = (Button) findViewById(R.id.menubtn);
//		menuBtn.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				if (!isExpanded) {
//					isExpanded = true;
//
//					// Expand
//					new ExpandAnimation(slidingPanel, panelWidth,
//							Animation.RELATIVE_TO_SELF, 0.0f,
//							Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
//				} else {
//					isExpanded = false;
//
//					// Collapse
//					new CollapseAnimation(slidingPanel, panelWidth,
//							TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
//							TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f,
//							0, 0.0f);
//
//				}
//			}
//		});
//		Button buttonhome = (Button) findViewById(R.id.btn_home);
//
//		buttonhome.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				menuBtn.performClick();
//				Intent intent = new Intent(MerchantListActivity.this,
//						HomeScreen.class);
//				startActivity(intent);
//			}
//		});
//		Button buttontoutismwebsite = (Button) findViewById(R.id.btn_tourismwebsite);
//
//		buttontoutismwebsite.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				menuBtn.performClick();
//				Intent intent = new Intent(MerchantListActivity.this,
//						Tourismwebsite.class);
//				startActivity(intent);
//			}
//		});
//
//		Button buttonmerchantlist = (Button) findViewById(R.id.btn_merchantlist);
//
//		buttonmerchantlist.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				menuBtn.performClick();
//				Intent myintent = new Intent(MerchantListActivity.this,
//						MerchantListActivity.class);
//				startActivity(myintent);
//
//			}
//		});
//
//		Button logoutButton = (Button) findViewById(R.id.btn_logout);
//		logoutButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (Session.getActiveSession() != null)
//					Session.getActiveSession().closeAndClearTokenInformation();
//
//				SharedPreferences sharedPreferences = getSharedPreferences(
//						"userdetails", MODE_PRIVATE);
//				SharedPreferences.Editor editor = sharedPreferences.edit();
//				editor.putString("userid", "");
//				editor.putString("login", "");
//
//				editor.commit();
//
//				Intent intent = new Intent(MerchantListActivity.this,
//						LoginActivity.class);
//				startActivity(intent);
//			}
//
//		});
//	}
//
//	public void onBackPressed() {
//		Intent intent = new Intent(this, HomeScreen.class);
//		startActivity(intent);
//	}
//
//}
