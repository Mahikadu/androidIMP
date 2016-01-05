//package com.mahi.androidimp;
//
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.StatusLine;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.facebook.Session;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
//import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
//import com.mobiwebcode.pgfh.Vo.AdvtpostVo;
//import com.mobiwebcode.pgfh.Vo.AnnouncementpostVo;
//import com.mobiwebcode.pgfh.Vo.CommentVo;
//import com.mobiwebcode.pgfh.Vo.FoodpostVo;
//import com.mobiwebcode.pgfh.Vo.MainpostVo;
//import com.mobiwebcode.pgfh.Vo.PictureVo;
//
//public class HomeScreen extends Activity {
//	public static ArrayList<MainpostVo> mainfeedpostlist = new ArrayList<MainpostVo>();
//	public ArrayList<FoodpostVo> foodpostlist = new ArrayList<FoodpostVo>();
//	public ArrayList<AdvtpostVo> advtpostlist = new ArrayList<AdvtpostVo>();
//	public ArrayList<AnnouncementpostVo> annoucpostlist = new ArrayList<AnnouncementpostVo>();
//	public ArrayList<MainpostVo> currentFeedList = new ArrayList<MainpostVo>();
//	public static String searchKeyword = "", categoryKeyword = "";
//	public ArrayList<PictureVo> picturelist = new ArrayList<PictureVo>();
//	public ArrayList<CommentVo> commentlist = new ArrayList<CommentVo>();
//
//	public static PictureVo pVo = null;
//	public static FoodpostVo fpVo = null;
//	public static AdvtpostVo apVo = null;
//	public static AnnouncementpostVo acpVo = null;
//	public static MainpostVo mpVo = null;
//
//	String responseString = "";
//	public static ArrayList<String> Categorylist = new ArrayList<String>();
//
//	XMLParser parser = new XMLParser();
//	String xml = "";
//	NodeList nl;
//
//	Document doc;
//	String FEEDS_URL = "http://pfh.com.my/pgfh/feedlist.php?currentcount=";
//	String FEEDS_CATEGORY_URL = "http://pfh.com.my/pgfh/feedsearch_category.php?currentcount=";
//	String FEEDS_SEARCH_URL = "http://pfh.com.my/pgfh/feedsearch.php?currentcount=";
//	private ProgressDialog mProgressDialog;
//	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
//
//	Constants cont = null;
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
//	PullToRefreshScrollView mPullRefreshScrollView;
//
//	Button post, search, category;
//	EditText searchEditText;
//
//	// String categorylist[] = { " Biggener ", " Easy ", " Medium ", " Hard ",
//	// " Very Hard ", " Expert " };
//
//	String selectedcategory;
//
//	protected Dialog onCreateDialog(int id) {
//		switch (id) {
//		case DIALOG_DOWNLOAD_PROGRESS1:
//			mProgressDialog = new ProgressDialog(this);
//			mProgressDialog.setMessage("Processing request, Please wait ...");
//			mProgressDialog.setCancelable(false);
//			mProgressDialog.show();
//			return mProgressDialog;
//
//		default:
//			return null;
//		}
//	}// end of createdialog.................................
//
//	void categorySearch(String category) {
//		currentFeedList = new ArrayList<MainpostVo>();
//		for (int count = 0; count < mainfeedpostlist.size(); count++) {
//			MainpostVo msVo = mainfeedpostlist.get(count);
//			if (msVo.feedtype.equals("foodpost")) {
//				FoodpostVo fd = msVo.fdVo;
//				if (fd.category.toLowerCase().contains(category.toLowerCase()))
//					currentFeedList.add(msVo);
//			}
//		}
//
//		layoutviewfeedlist();
//	}
//
//	void keywordSearch(String searchKeyword) {
//		currentFeedList = new ArrayList<MainpostVo>();
//		for (int count = 0; count < mainfeedpostlist.size(); count++) {
//			MainpostVo msVo = mainfeedpostlist.get(count);
//			if (msVo.feedtype.equals("foodpost")) {
//				FoodpostVo fd = msVo.fdVo;
//				if (fd.restaurantname.toLowerCase().contains(
//						searchKeyword.toLowerCase()))
//					currentFeedList.add(msVo);
//				else if (fd.description.toLowerCase().contains(
//						searchKeyword.toLowerCase()))
//					currentFeedList.add(msVo);
//			} else if (msVo.feedtype.equals("advtpost")) {
//				AdvtpostVo ad = msVo.adVo;
//				if (ad.outlet.toLowerCase().contains(
//						searchKeyword.toLowerCase()))
//					currentFeedList.add(msVo);
//				else if (ad.description.toLowerCase().contains(
//						searchKeyword.toLowerCase()))
//					currentFeedList.add(msVo);
//			} else if (msVo.feedtype.equals("announcementpost")) {
//				AnnouncementpostVo annd = msVo.annVo;
//				if (annd.title.toLowerCase().contains(
//						searchKeyword.toLowerCase()))
//					currentFeedList.add(msVo);
//				else if (annd.description.toLowerCase().contains(
//						searchKeyword.toLowerCase()))
//					currentFeedList.add(msVo);
//			}
//		}
//
//		layoutviewfeedlist();
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.homescreen);
//		// category Intent
//
//		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
//		mPullRefreshScrollView
//				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
//
//					@Override
//					public void onRefresh(
//							PullToRefreshBase<ScrollView> refreshView) {
//						new myTaskAllFeedsList_Call().execute("");
//					}
//				});
//
//		cont = Constants.getinstance(HomeScreen.this);
//		searchEditText = (EditText) findViewById(R.id.search);
//		searchEditText.setText(searchKeyword);
//
//		post = (Button) findViewById(R.id.postbtn);
//		post.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent post = new Intent(HomeScreen.this, NewPostActivity.class);
//				startActivity(post);
//			}
//		});
//
//		category = (Button) findViewById(R.id.categoriesbtn);
//		category.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (cont.isOnline()) {
//					showCategoryDialogue();
//				} else {
//					final Dialog dialog = new Dialog(HomeScreen.this);
//					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//					dialog.setContentView(R.layout.nointernetconnection);
//
//					Button dialogButton = (Button) dialog
//							.findViewById(R.id.dialogButtonOK);
//
//					dialogButton.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							dialog.dismiss();
//						}
//					});
//
//					dialog.show();
//
//				}
//			}
//		});
//
//		search = (Button) findViewById(R.id.searchbtn);
//		search.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				searchKeyword = searchEditText.getText().toString();
//				mainfeedpostlist.clear();
//				new myTaskAllFeedsList_Call().execute("");
//			}
//		});
//
//		searchEditText
//				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//					@Override
//					public boolean onEditorAction(TextView v, int actionId,
//							KeyEvent event) {
//						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//							mainfeedpostlist.clear();
//							new myTaskAllFeedsList_Call().execute("");
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
//				searchKeyword = "";
//				searchEditText.setText("");
//				mainfeedpostlist.clear();
//				new myTaskAllFeedsList_Call().execute("");
//				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
//			}
//		});
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
//				Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
//				startActivity(intent);
//			}
//		});
//		Button buttontoutismwebsite = (Button) findViewById(R.id.btn_tourismwebsite);
//
//		buttontoutismwebsite.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				menuBtn.performClick();
//				Intent intent = new Intent(HomeScreen.this,
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
//				Intent myintent = new Intent(HomeScreen.this,
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
//				Intent intent = new Intent(HomeScreen.this, LoginActivity.class);
//				startActivity(intent);
//			}
//
//		});
//
//		if (cont.isOnline()) {
//			new myTask_categorylist_call().execute();
//		} else {
//
//			final Dialog dialog = new Dialog(HomeScreen.this);
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
//	}// end of oncreate...............................
//
//	// DownloadJSON AsyncTask for categorylist
//	class myTask_categorylist_call extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpResponse response;
//
//			try {
//
//				response = httpclient.execute(new HttpGet(
//						"http://pfh.com.my/pgfh/getcategorylist.php"));
//				StatusLine statusLine = response.getStatusLine();
//
//				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
//					ByteArrayOutputStream out = new ByteArrayOutputStream();
//					response.getEntity().writeTo(out);
//					responseString = out.toString();
//
//					out.close();
//
//				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			return null;
//
//		}
//
//		@Override
//		protected void onPostExecute(Void args) {
//			String[] categoryarray = responseString.split("a1b2c3");
//
//			Categorylist = new ArrayList<String>(Arrays.asList(categoryarray));
//
//			if (mProgressDialog != null)
//				mProgressDialog.dismiss();
//			mainfeedpostlist.clear();
//			new myTaskAllFeedsList_Call().execute();
//		}
//	}
//
//	public void showCategoryDialogue() {
//
//		final Dialog dialog = new Dialog(HomeScreen.this);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(R.layout.category_dialog);
//
//		LinearLayout MainLL = (LinearLayout) dialog
//				.findViewById(R.id.mainlinearlayout);
//		// int counter = 1;
//		// final int size = 5;
//
//		for (int i = 0; i < Categorylist.size(); i++) {
//
//			final Button categorylistbtn = new Button(HomeScreen.this);
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//					LayoutParams.MATCH_PARENT, 150);
//
//			params.setMargins(0, 1, 0, 1);
//
//			categorylistbtn.setLayoutParams(params);
//			categorylistbtn.setText(Categorylist.get(i));
//			categorylistbtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//			categorylistbtn.setBackgroundColor(Color.parseColor("#000000"));
//			categorylistbtn.setTextColor(Color.parseColor("#ffffff"));
//
//			MainLL.addView(categorylistbtn);
//
//			categorylistbtn.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					categorylistbtn.setBackgroundColor(Color
//							.parseColor("#d3d3d3"));
//					mainfeedpostlist.clear();
//					categoryKeyword = categorylistbtn.getText().toString()
//							.replaceAll(" ", "%20");
//					searchKeyword = "";
//					new myTaskAllFeedsList_Call().execute("");
//					dialog.dismiss();
//				}
//			});
//		}// end of categorylist for loop................
//
//		ImageView dialogButton = (ImageView) dialog.findViewById(R.id.closebtn);
//		dialogButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
//		dialog.show();
//
//	}// end of category dialog..............................................
//
//	class myTaskAllFeedsList_Call extends AsyncTask<String, Void, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
//		}
//
//		@Override
//		protected String doInBackground(String... aurl) {
//			try {
//
//				if (searchEditText.getText().toString().equals("")) {
//					if (categoryKeyword.equals("All")
//							|| categoryKeyword.equals(""))
//						xml = parser.getXmlFromUrl(FEEDS_URL
//								+ mainfeedpostlist.size());
//					else
//						xml = parser.getXmlFromUrl(FEEDS_CATEGORY_URL
//								+ mainfeedpostlist.size() + "&category="
//								+ categoryKeyword);
//				} else {
//					xml = parser.getXmlFromUrl(FEEDS_SEARCH_URL
//							+ mainfeedpostlist.size() + "&searchstring="
//							+ searchEditText.getText().toString());
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return "";
//		}
//
//		@Override
//		protected void onPostExecute(String str_resp) {
//			doc = parser.getDomElement(xml); // getting
//
//			if (doc != null) {
//				nl = doc.getElementsByTagName("feed");
//				// mainfeedpostlist.clear();
//				// looping through all item nodes <item>
//
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e = (Element) nl.item(i);
//					mpVo = new MainpostVo();
//					mpVo.feedtype = parser.getValue(e, "feedtype");
//
//					if (mpVo.feedtype.equals("foodpost")) {
//						foodpostlist.clear();
//						fpVo = new FoodpostVo();
//
//						fpVo.foodpostid = parser.getValue(e, "foodpostid");
//						fpVo.restaurantname = parser.getValue(e,
//								"restaurantname");
//						fpVo.price = parser.getValue(e, "price");
//						fpVo.category = parser.getValue(e, "category");
//						fpVo.address = parser.getValue(e, "address");
//						fpVo.lattitude = parser.getValue(e, "lattitude");
//						fpVo.longitude = parser.getValue(e, "longitude");
//						fpVo.description = parser.getValue(e, "description");
//						fpVo.tasterating = parser.getValue(e, "tasterating");
//						fpVo.pricerating = parser.getValue(e, "pricerating");
//						fpVo.contact = parser.getValue(e, "contact");
//						fpVo.from_operatinghrs = parser.getValue(e,
//								"from_operatinghrs");
//						fpVo.to_operatinghrs = parser.getValue(e,
//								"to_operatinghrs");
//						fpVo.hygienerating = parser
//								.getValue(e, "hygienerating");
//						fpVo.servicerating = parser
//								.getValue(e, "servicerating");
//						fpVo.feedpostdate = cont.findDifference(parser
//								.getValue(e, "feedpostdate"));
//
//						NodeList nl_picture_fp = e.getChildNodes().item(16)
//								.getChildNodes();
//						// mainfeedpostlist.clear();
//						// looping through all item nodes <item>
//						for (int p = 0; p < nl_picture_fp.getLength(); p++) {
//							Element a = (Element) nl_picture_fp.item(p);
//							pVo = new PictureVo();
//							pVo.pictureid = parser.getValue(a, "pictureid");
//							pVo.picturename = parser.getValue(a, "picturename");
//
//							fpVo.picturelist.add(pVo);
//						}
//						NodeList nl_comment_fp = e.getChildNodes().item(17)
//								.getChildNodes();
//						// mainfeedpostlist.clear()
//						// looping through all item nodes <item>
//						for (int c = 0; c < nl_comment_fp.getLength(); c++) {
//							Element b = (Element) nl_comment_fp.item(c);
//							CommentVo cVo = new CommentVo();
//							cVo.commentid = parser.getValue(b, "commentid");
//							cVo.commenttext = parser.getValue(b, "commenttext");
//							// cVo.commentpicture = parser.getValue(b,
//							// "commentpicture");
//							cVo.commentusername = parser.getValue(b,
//									"commentusername");
//							cVo.commentdate = cont.findDifference(parser
//									.getValue(b, "commentdate"));
//
//							fpVo.commentlist.add(cVo);
//						}
//
//					} else if (mpVo.feedtype.equals("advtpost")) {
//
//						apVo = new AdvtpostVo();
//						apVo.advtpostid = parser.getValue(e, "advtpostid");
//						apVo.outlet = parser.getValue(e, "outlet");
//						apVo.location = parser.getValue(e, "location");
//						apVo.lattitude = parser.getValue(e, "lattitude");
//						apVo.longitude = parser.getValue(e, "longitude");
//						apVo.contact = parser.getValue(e, "contact");
//						apVo.description = parser.getValue(e, "description");
//						apVo.feedpostdate = cont.findDifference(parser
//								.getValue(e, "feedpostdate"));
//
//						advtpostlist.add(apVo);
//
//						NodeList nl_picture_fp = e.getChildNodes().item(9)
//								.getChildNodes();
//						// mainfeedpostlist.clear();
//						// looping through all item nodes <item>
//						for (int p = 0; p < nl_picture_fp.getLength(); p++) {
//							Element a = (Element) nl_picture_fp.item(p);
//							pVo = new PictureVo();
//							pVo.pictureid = parser.getValue(a, "pictureid");
//							pVo.picturename = parser.getValue(a, "picturename");
//
//							apVo.picturelist.add(pVo);
//						}
//
//						NodeList nl_comment_ap = e.getChildNodes().item(10)
//								.getChildNodes();
//						// mainfeedpostlist.clear()
//						// looping through all item nodes <item>
//						for (int c = 0; c < nl_comment_ap.getLength(); c++) {
//							Element b = (Element) nl_comment_ap.item(c);
//							CommentVo cVo = new CommentVo();
//							cVo.commentid = parser.getValue(b, "commentid");
//							cVo.commenttext = parser.getValue(b, "commenttext");
//							// cVo.commentpicture = parser.getValue(b,
//							// "commentpicture");
//							cVo.commentusername = parser.getValue(b,
//									"commentusername");
//							cVo.commentdate = cont.findDifference(parser
//									.getValue(b, "commentdate"));
//
//							apVo.commentlist.add(cVo);
//						}
//
//					} else {
//
//						acpVo = new AnnouncementpostVo();
//						acpVo.announcementpostid = parser.getValue(e,
//								"announcementpostid");
//						acpVo.title = parser.getValue(e, "title");
//						acpVo.description = parser.getValue(e, "description");
//						acpVo.announcementpostdate = cont.findDifference(parser
//								.getValue(e, "announcementpostdate"));
//
//						annoucpostlist.add(acpVo);
//
//						NodeList nl_picture_acp = e.getChildNodes().item(5)
//								.getChildNodes();
//						// mainfeedpostlist.clear();
//						// looping through all item nodes <item>
//						for (int p = 0; p < nl_picture_acp.getLength(); p++) {
//							Element a = (Element) nl_picture_acp.item(p);
//							pVo = new PictureVo();
//							pVo.pictureid = parser.getValue(a, "pictureid");
//							pVo.picturename = parser.getValue(a, "picturename");
//
//							acpVo.picturelist.add(pVo);
//						}
//
//					}
//
//					if (mpVo.feedtype.equals("foodpost")) {
//						mpVo.fdVo = fpVo;
//					} else if (mpVo.feedtype.equals("advtpost")) {
//						mpVo.adVo = apVo;
//					} else {
//						mpVo.annVo = acpVo;
//					}
//
//					mainfeedpostlist.add(mpVo);
//
//				}// end of mainfeed for loop.........
//
//			}
//			currentFeedList = mainfeedpostlist;
//			layoutviewfeedlist();
//			mPullRefreshScrollView.onRefreshComplete();
//			if (mProgressDialog != null)
//				mProgressDialog.dismiss();
//
//		}// end of post execute...........
//	}// close Asyntask method................................................
//
//	void layoutviewfeedlist() {
//		try {
//			LinearLayout mainfeedlistlinearlayout = (LinearLayout) findViewById(R.id.mainfeedlistlinearlayout);
//			mainfeedlistlinearlayout.removeAllViews();
//			for (int i = 0; i < currentFeedList.size(); i++) {
//				final MainpostVo mVo = currentFeedList.get(i);
//
//				// Main layout
//				LinearLayout main = new LinearLayout(HomeScreen.this);
//				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//				main.setWeightSum(1f);
//				params.setMargins(0, 5, 0, 1);
//				main.setOrientation(LinearLayout.HORIZONTAL);
//				main.setLayoutParams(params);
//
//				// line view layout
//				LinearLayout lineviewlayout = new LinearLayout(HomeScreen.this);
//				LinearLayout.LayoutParams lineview = new LinearLayout.LayoutParams(
//						LayoutParams.MATCH_PARENT, 2);
//				TextView line = new TextView(HomeScreen.this);
//				line.setLayoutParams(lineview);
//				lineview.setMargins(3, 0, 3, 0);
//				line.setBackgroundColor(Color.GRAY);
//				lineviewlayout.addView(line);
//
//				// Imageview layout
//				LinearLayout imagelayout = new LinearLayout(HomeScreen.this);
//				LinearLayout.LayoutParams image = new LinearLayout.LayoutParams(
//						0, 160);
//				image.weight = 0.22f;
//				image.setMargins(5, 2, 1, 2);
//				imagelayout.setOrientation(LinearLayout.VERTICAL);
//				imagelayout.setLayoutParams(image);
//
//				// All Textview layout
//				LinearLayout textviewlayout = new LinearLayout(HomeScreen.this);
//				LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
//						0, LayoutParams.WRAP_CONTENT);
//				text.weight = 0.78f;
//				textviewlayout.setWeightSum(1f);
//				text.setMargins(5, 0, 5, 0);
//				textviewlayout.setOrientation(LinearLayout.VERTICAL);
//				textviewlayout.setLayoutParams(text);
//
//				// *********************************************************************
//				// Name layout
//				LinearLayout nametextlayout = new LinearLayout(HomeScreen.this);
//				TextView restoname = new TextView(HomeScreen.this);
//				LinearLayout.LayoutParams username = new LinearLayout.LayoutParams(
//						LayoutParams.MATCH_PARENT, 0);
//				username.weight = 0.25f;
//				nametextlayout.setOrientation(LinearLayout.HORIZONTAL);
//				nametextlayout.setLayoutParams(username);
//				restoname.setLayoutParams(username);
//				nametextlayout.addView(restoname);
//
//				// Description layout
//				LinearLayout descriptionlayout = new LinearLayout(
//						HomeScreen.this);
//				TextView discription = new TextView(HomeScreen.this);
//				LinearLayout.LayoutParams description = new LinearLayout.LayoutParams(
//						LayoutParams.MATCH_PARENT, 0);
//				description.weight = 0.75f;
//				descriptionlayout.setOrientation(LinearLayout.HORIZONTAL);
//				descriptionlayout.setLayoutParams(description);
//				discription.setLayoutParams(description);
//				descriptionlayout.addView(discription);
//				// *************************************************************************
//				RelativeLayout datelayout = new RelativeLayout(HomeScreen.this);
//
//				RelativeLayout.LayoutParams dateimage = new RelativeLayout.LayoutParams(
//						RelativeLayout.LayoutParams.MATCH_PARENT, 70);
//				dateimage.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//
//				ImageView photo = new ImageView(HomeScreen.this);
//				RelativeLayout.LayoutParams lpImage = new RelativeLayout.LayoutParams(
//						70, 70);
//				photo.setId(i + 1000);
//				lpImage.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//				lpImage.setMargins(0, 0, 5, 3);
//				photo.setLayoutParams(lpImage);
//
//				TextView date = new TextView(HomeScreen.this);
//				RelativeLayout.LayoutParams lpTextView = new RelativeLayout.LayoutParams(
//						RelativeLayout.LayoutParams.WRAP_CONTENT,
//						RelativeLayout.LayoutParams.WRAP_CONTENT);
//				lpTextView.addRule(RelativeLayout.LEFT_OF, i + 1000);
//				lpTextView.addRule(RelativeLayout.CENTER_VERTICAL);
//				lpTextView.setMargins(0, 0, 5, 0);
//				date.setLayoutParams(lpTextView);
//
//				datelayout.setLayoutParams(dateimage);
//				datelayout.addView(date);
//				datelayout.addView(photo);
//
//				if (mVo.feedtype.equals("foodpost")) {
//					fpVo = mVo.fdVo;
//					RelativeLayout.LayoutParams pick = new RelativeLayout.LayoutParams(
//							RelativeLayout.LayoutParams.MATCH_PARENT,
//							RelativeLayout.LayoutParams.MATCH_PARENT);
//					ImageView picture = new ImageView(HomeScreen.this);
//					picture.setBackgroundResource(R.drawable.pgfhlogo);
//					RelativeLayout progressimage = new RelativeLayout(
//							HomeScreen.this);
//					progressimage.setGravity(Gravity.CENTER);
//					progressimage.setLayoutParams(pick);
//					picture.setLayoutParams(pick);
//
//					picture.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							onfeeditemclick(mVo);
//						}
//					});
//					RelativeLayout.LayoutParams process = new RelativeLayout.LayoutParams(
//							LayoutParams.WRAP_CONTENT,
//							LayoutParams.WRAP_CONTENT);
//					ProgressBar mprogress = new ProgressBar(HomeScreen.this,
//							null, android.R.attr.progressBarStyleSmall);
//					mprogress.setVisibility(View.VISIBLE);
//					mprogress.setLayoutParams(process);
//					mprogress.setIndeterminate(true);
//					process.addRule(RelativeLayout.CENTER_IN_PARENT);
//					progressimage.addView(mprogress);
//					progressimage.addView(picture);
//					imagelayout.addView(progressimage);
//
//					if (fpVo.picturelist.size() > 0) {
//						PictureVo fpiVo = null;
//						fpiVo = fpVo.picturelist.get(0);
//						// picture.setSingleFotoImageURL(fpiVo.picturename,
//						// mprogress, picture);
//						cont.imageloader.DisplayImage(fpiVo.picturename,
//								picture, mprogress);
//					}
//
//					// end of picturelist for loop.......................
//
//					restoname.setText(fpVo.restaurantname);
//					discription.setText(fpVo.description);
//					date.setText(fpVo.feedpostdate);
//					photo.setImageResource(R.drawable.fooddome);
//
//					date.setTextSize(10);
//
//					restoname.setTextColor(Color.parseColor("#00C5CD"));
//					discription.setTextColor(Color.parseColor("#000000"));
//					date.setTextColor(Color.parseColor("#000000"));
//
//					discription.setMaxLines(3);
//					discription.setMinLines(3);
//				} else if (mVo.feedtype.equals("advtpost")) {
//					apVo = mVo.adVo;
//
//					PictureVo adpVo = null;
//					RelativeLayout progressimage = null;
//					progressimage = new RelativeLayout(HomeScreen.this);
//
//					RelativeLayout.LayoutParams pick = new RelativeLayout.LayoutParams(
//							RelativeLayout.LayoutParams.MATCH_PARENT,
//							RelativeLayout.LayoutParams.MATCH_PARENT);
//					progressimage.setLayoutParams(pick);
//					progressimage.setGravity(Gravity.CENTER);
//					ImageView picture = new ImageView(HomeScreen.this);
//					picture.setBackgroundResource(R.drawable.pgfhlogo);
//					picture.setLayoutParams(pick);
//
//					progressimage = new RelativeLayout(HomeScreen.this);
//					picture.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							onfeeditemclick(mVo);
//						}
//					});
//					RelativeLayout.LayoutParams process = new RelativeLayout.LayoutParams(
//							LayoutParams.WRAP_CONTENT,
//							LayoutParams.WRAP_CONTENT);
//					ProgressBar mprogress = new ProgressBar(HomeScreen.this,
//							null, android.R.attr.progressBarStyleSmall);
//					mprogress.setVisibility(View.VISIBLE);
//					mprogress.setLayoutParams(process);
//					mprogress.setIndeterminate(true);
//					process.addRule(RelativeLayout.CENTER_IN_PARENT);
//
//					progressimage.addView(picture);
//					progressimage.addView(mprogress);
//					imagelayout.addView(progressimage);
//					if (apVo.picturelist.size() > 0) {
//						adpVo = apVo.picturelist.get(0);
//						// picture.setSingleFotoImageURL(adpVo.picturename,
//						// mprogress, picture);
//						cont.imageloader.DisplayImage(adpVo.picturename,
//								picture, mprogress);
//					}
//
//					restoname.setText(apVo.outlet);
//					discription.setText(apVo.description);
//					date.setText(apVo.feedpostdate);
//					photo.setImageResource(R.drawable.fooddome);
//
//					date.setTextSize(10);
//
//					restoname.setTextColor(Color.parseColor("#00C5CD"));
//					discription.setTextColor(Color.parseColor("#000000"));
//					date.setTextColor(Color.parseColor("#000000"));
//
//					discription.setMaxLines(3);
//					discription.setMinLines(3);
//					main.setBackgroundColor(Color.parseColor("#81F7F3"));
//				} else {
//					acpVo = mVo.annVo;
//					PictureVo anfiVo = null;
//					RelativeLayout progressimage = null;
//					progressimage = new RelativeLayout(HomeScreen.this);
//
//					ImageView picture = new ImageView(HomeScreen.this);
//					picture.setBackgroundResource(R.drawable.pgfhlogo);
//					progressimage = new RelativeLayout(HomeScreen.this);
//					RelativeLayout.LayoutParams pick = new RelativeLayout.LayoutParams(
//							RelativeLayout.LayoutParams.MATCH_PARENT,
//							RelativeLayout.LayoutParams.MATCH_PARENT);
//
//					progressimage.setLayoutParams(pick);
//					progressimage.setGravity(Gravity.CENTER);
//					picture.setLayoutParams(pick);
//
//					picture.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							onfeeditemclick(mVo);
//						}
//					});
//					RelativeLayout.LayoutParams process = new RelativeLayout.LayoutParams(
//							LayoutParams.WRAP_CONTENT,
//							LayoutParams.WRAP_CONTENT);
//					ProgressBar mprogress = new ProgressBar(HomeScreen.this,
//							null, android.R.attr.progressBarStyleSmall);
//					mprogress.setVisibility(View.VISIBLE);
//					mprogress.setLayoutParams(process);
//					mprogress.setIndeterminate(true);
//					process.addRule(RelativeLayout.CENTER_IN_PARENT);
//					progressimage.addView(picture);
//					progressimage.addView(mprogress);
//					imagelayout.addView(progressimage);
//
//					if (acpVo.picturelist.size() > 0) {
//						anfiVo = acpVo.picturelist.get(0);
//						// picture.setSingleFotoImageURL(anfiVo.picturename,
//						// mprogress, picture);
//						cont.imageloader.DisplayImage(anfiVo.picturename,
//								picture, mprogress);
//					}
//
//					// picture.setSingleFotoImageURL(anfiVo.picturename,
//					// mprogress, picture);
//
//					restoname.setText(acpVo.title);
//					discription.setText(acpVo.description);
//					date.setText(acpVo.announcementpostdate);
//					photo.setImageResource(R.drawable.fooddome);
//
//					date.setTextSize(10);
//
//					restoname.setTextColor(Color.parseColor("#00C5CD"));
//					discription.setTextColor(Color.parseColor("#000000"));
//					date.setTextColor(Color.parseColor("#000000"));
//					
//					main.setBackgroundColor(Color.parseColor("#FFFF00"));
//
//				}
//
//				textviewlayout.addView(nametextlayout);
//				textviewlayout.addView(descriptionlayout);
//				textviewlayout.addView(datelayout);
//				main.addView(imagelayout);
//				main.addView(textviewlayout);
//
//				mainfeedlistlinearlayout.addView(main);
//				mainfeedlistlinearlayout.addView(lineviewlayout);
//
//				// Textview onclicklistener
//				restoname.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						onfeeditemclick(mVo);
//					}
//				});
//
//				discription.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						onfeeditemclick(mVo);
//					}
//				});
//
//				date.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						onfeeditemclick(mVo);
//					}
//				});
//
//				photo.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						onfeeditemclick(mVo);
//					}
//				});
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	} // end of layoutview...............................
//
//	void onfeeditemclick(MainpostVo mVo) {
//
//		if (mVo.feedtype.equals("foodpost")) {
//			Intent feed = new Intent(HomeScreen.this, PostDetailsActivity.class);
//			startActivity(feed);
//
//			PostDetailsActivity.fpVo = mVo.fdVo;
//
//		} else if (mVo.feedtype.equals("advtpost")) {
//
//			Intent advt = new Intent(HomeScreen.this,
//					AdvertisingpostActivity.class);
//			startActivity(advt);
//
//			AdvertisingpostActivity.apVo = mVo.adVo;
//		}
//	}// end of feeditemclick..............................
//
//	@Override
//	public void onBackPressed() {
//		// Write your code here
//		Intent intent = new Intent(Intent.ACTION_MAIN);
//		intent.addCategory(Intent.CATEGORY_HOME);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
//
//	}
//}
