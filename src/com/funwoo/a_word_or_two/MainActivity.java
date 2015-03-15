package com.funwoo.a_word_or_two;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.funwoo.a_word_or_two.R;
import com.funwoo.a_word_or_two.classes.DataExchange;
import com.funwoo.a_word_or_two.classes.Feedback;
import com.funwoo.a_word_or_two.classes.LBMessage;
import com.funwoo.a_word_or_two.classes.LocalUser;
import com.funwoo.a_word_or_two.classes.LoginUser;
import com.funwoo.a_word_or_two.classes.RegisterUser;
import com.funwoo.a_word_or_two.classes.UserNameClass;
import com.funwoo.a_word_or_two.classes.LBMessage.t_userType;
import com.funwoo.a_word_or_two.message.ClientMsg;
import com.funwoo.a_word_or_two.message.MarkerManager;
import com.funwoo.a_word_or_two.message.UIMessage;
import com.funwoo.a_word_or_two.utils.JsonUtils;
import com.funwoo.a_word_or_two.utils.ToastUtil;

import android.R.bool;
import android.R.integer;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.location.Location;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements 
OnMarkerClickListener, OnMapLoadedListener, InfoWindowAdapter,OnCheckedChangeListener,OnClickListener,
AMapLocationListener,LocationSource {


	private MapView mapView;
	private AMap aMap;
	private EditText edt_words;
	private ImageButton send;
	//	private Handler handler;
	//	private Double geoLat;
	//	private Double geoLng;
	private ImageButton change_portrait;
	private ImageButton sharebButton;
	private ImageButton feedbackButton;

	private Boolean   is_portrait_First = false;
	private static Boolean showpassword_ischecked = false;

	private LocationManagerProxy aMapLocManager = null;
	private AMapLocation aMapLocation;// 用于判断定位超时
	private OnLocationChangedListener mListener;

	private RadioGroup zoomRadioGroup;
	private UiSettings mUiSettings;
	private ScrollView sc;
	private boolean scVisiable;
	private RelativeLayout feedbackRelativeLayout;
	private RelativeLayout relativeLayout;
	private EditText usernameEditText;
	private  EditText passwordEditText;
	private TextView usernameTextView;
	private TextView passwordTextView;
	private TextView btn_login;
	private TextView btn_tourist;
	private TextView btn_register;

	private Button submit_btn ;
	private Button cancel_btn;
	private EditText feedEditText;

	private static int username_click_num=1;
	private static int password_click_num=1;

	private ImageButton checknameButton;
	private ImageButton showPasswordButton;
	private ImageButton settingsButton;
	//jcy vars
	private MarkerManager markerManager;
	private LocalUser localUser;
	private ClientMsg clientMsg;
	private MessageHandler messageHandler;
	private DataExchange de;
	private boolean isLogin = false;
	private boolean isWaiting = false;
	private long mExitTime;
	
	//----------------------new sound---------
	SoundPool sp;
	HashMap<Integer,Integer> spMap;
	private int soundIndex = -1;
	private boolean soundAvaliable = false;
	int[] soundArray = {5,3,3,4,2,2,1,2,3,4,5,5,5,5,3,3,4,2,2,1,3,5,5,3,2,2,2,2,2,3,4,3,3,3,3,3,4,5,5,3,3,4,2,2,1,3,5,5,1};
	//---------------------sound over--------

	//portrait
	int[] portraitPic ={R.drawable.portrait_0,R.drawable.portrait_1,R.drawable.portrait_2,R.drawable.portrait_3,R.drawable.portrait_4,
			R.drawable.portrait_5,R.drawable.portrait_6,R.drawable.portrait_7,R.drawable.portrait_8,R.drawable.portrait_9,
			R.drawable.portrait_10,R.drawable.portrait_11,R.drawable.portrait_12,R.drawable.portrait_13,R.drawable.portrait_14,
			R.drawable.portrait_15,R.drawable.portrait_16,R.drawable.portrait_17,R.drawable.portrait_18,R.drawable.portrait_19,
			R.drawable.portrait_20,R.drawable.portrait_21,R.drawable.portrait_22,R.drawable.portrait_23,R.drawable.portrait_24,
			R.drawable.portrait_25,R.drawable.portrait_26,R.drawable.portrait_27,R.drawable.portrait_28,
			R.drawable.portrait_29,R.drawable.portrait_30,R.drawable.portrait_31,R.drawable.portrait_32,
			R.drawable.portrait_33,R.drawable.portrait_34,R.drawable.portrait_35,R.drawable.portrait_36,
			R.drawable.portrait_37,R.drawable.portrait_38,R.drawable.portrait_39,R.drawable.portrait_40,
			R.drawable.portrait_41,R.drawable.portrait_42,R.drawable.portrait_43,R.drawable.portrait_44,
			R.drawable.portrait_45,R.drawable.portrait_46,R.drawable.portrait_47,R.drawable.portrait_48,
			R.drawable.portrait_49,R.drawable.portrait_50,R.drawable.portrait_51,R.drawable.portrait_52,
			R.drawable.portrait_53,R.drawable.portrait_54
	};


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basicmap_activity);
		//		handler = new Handler();
		localUser = new LocalUser();
		relativeLayout =(RelativeLayout) findViewById(R.id.login_div);
		relativeLayout.setVisibility(View.INVISIBLE);

		feedbackRelativeLayout = (RelativeLayout)findViewById(R.id.feedback_layout);
		submit_btn = (Button)findViewById(R.id.submit_btn);
		submit_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isWaiting)
				{
					isWaiting = true;
					String feedbackStr = feedEditText.getText().toString();
					if(feedbackStr.equals(""))
					{
						Toast.makeText(MainActivity.this, "用户反馈不能为空", Toast.LENGTH_SHORT).show();
						isWaiting = false;
						return;
					}
					final Feedback fbmsg = new Feedback(localUser.userID,localUser.location,feedbackStr);
					new Thread(new Runnable(){
						@Override
						public void run() {
							de.SendFeedback(fbmsg);
						}
					}).start();
				}
			}
		});
		cancel_btn = (Button)findViewById(R.id.cancel_btn);
		cancel_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				feedbackRelativeLayout.setVisibility(View.INVISIBLE);
				feedEditText.setText("");
				edt_words.setEnabled(true);
				send.setEnabled(true);
				settingsButton.setEnabled(true);
			}
		});
		feedEditText= (EditText)findViewById(R.id.feedback_edt);

		//		relativeLayout.setAlpha((float)0.6);
		sc = (ScrollView)findViewById(R.id.scrollview1);
		sc.setAlpha((float)0.4);
		sc.setVisibility(View.INVISIBLE);
		scVisiable = false;
		aMapLocManager = LocationManagerProxy.getInstance(this);

		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位e数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */

		aMapLocManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 2000, 10, this);
		onLocationChanged(aMapLocation);

		edt_words = (EditText) findViewById(R.id.edtxt_words);

		send = (ImageButton) findViewById(R.id.btn_send);
		send.setOnClickListener(new sendListener());
		mapView = (MapView) findViewById(R.id.map);
		//		onLocationChanged(aMapLocation);
		mapView.onCreate(savedInstanceState);// 必须要写
		init();

		usernameTextView =(TextView)findViewById(R.id.username);
		usernameTextView.setOnClickListener(new username_password_text());
		passwordTextView =(TextView)findViewById(R.id.password);
		passwordTextView.setOnClickListener(new username_password_text());

		usernameEditText = (EditText)findViewById(R.id.txt_username);
		passwordEditText = (EditText)findViewById(R.id.txt_password);

		checknameButton = (ImageButton)findViewById(R.id.check_user_name);
		checknameButton.setOnClickListener(new checkusernameListener());
		checknameButton.setEnabled(false);
		settingsButton = (ImageButton)findViewById(R.id.settings);
		settingsButton.setOnClickListener(new settingsListener());

		showPasswordButton=(ImageButton)findViewById(R.id.show_password);
		showPasswordButton.setOnClickListener(new showpasswordListener());
		btn_login = (TextView)findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new loginListener());

		btn_register = (TextView)findViewById(R.id.btn_register);	
		btn_register.setOnClickListener(new registerListener());	 
		btn_tourist = (TextView)findViewById(R.id.btn_tourist);
		btn_tourist.setOnClickListener(new touristListener());
		//jcy code
		markerManager = MarkerManager.SharedMarkerManager();
		Looper looper = Looper.myLooper();
		messageHandler = new MessageHandler(looper);
		de = new DataExchange(messageHandler);

		CheckBox zoomToggle = (CheckBox) findViewById(R.id.zoom_toggle);
		zoomToggle.setOnClickListener(this);

		CheckBox compassToggle = (CheckBox) findViewById(R.id.compass_toggle);
		compassToggle.setOnClickListener(this);
	
		CheckBox scrollToggle = (CheckBox) findViewById(R.id.scroll_toggle);
		scrollToggle.setOnClickListener(this);
		CheckBox zoom_gesturesToggle = (CheckBox) findViewById(R.id.zoom_gestures_toggle);
		zoom_gesturesToggle.setOnClickListener(this);
		CheckBox tiltToggle = (CheckBox) findViewById(R.id.tilt_toggle);
		tiltToggle.setOnClickListener(this);
		CheckBox rotateToggle = (CheckBox) findViewById(R.id.rotate_toggle);
		rotateToggle.setOnClickListener(this);
		change_portrait = (ImageButton)findViewById(R.id.change_portrait);
		change_portrait.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChangeBG();
			}
		});
		sharebButton = (ImageButton)findViewById(R.id.share);
		sharebButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aMap.setLocationSource(MainActivity.this);
				onLocationChanged(aMapLocation);
				aMap.setMyLocationEnabled(true);
				onMapLoaded();
				
			}
		});
		feedbackButton = (ImageButton)findViewById(R.id.feedback);
		feedbackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				feedbackRelativeLayout.setVisibility(View.VISIBLE);
				feedEditText.requestFocus();
				edt_words.setEnabled(false);
				send.setEnabled(false);
				settingsButton.setEnabled(false);
			}
		});

		ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
		imageButton.setOnClickListener(new Button.OnClickListener(){  
			public void onClick(View v) {  
				if(!isLogin)
				{
					Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
					return ;
				}
				if (!is_portrait_First) {
					Animation myAnimation_Alpha = AnimationUtils.loadAnimation(MainActivity.this,
							R.anim.my_scale_action);
					sharebButton.setVisibility(View.VISIBLE);
					sharebButton.startAnimation(myAnimation_Alpha);
					change_portrait.setVisibility(View.VISIBLE);
					change_portrait.startAnimation(myAnimation_Alpha);
					feedbackButton.setVisibility(View.VISIBLE);
					feedbackButton.startAnimation(myAnimation_Alpha);

				}else{
					sharebButton.setVisibility(View.INVISIBLE);
					change_portrait.setVisibility(View.INVISIBLE);
					feedbackButton.setVisibility(View.INVISIBLE);
				}
				is_portrait_First = !is_portrait_First;

			}  
		});

		InitSoundPool();
		ReadLocalData();
		if(isLogin)
		{
			edt_words.setEnabled(true);
		}
		else
		{
			edt_words.setEnabled(false);
			relativeLayout.setVisibility(View.VISIBLE);
			passwordEditText.setHint("密码思密达~");
			usernameEditText.setHint("账号么么哒~");
			usernameEditText.setFocusable(true);
			usernameEditText.setFocusableInTouchMode(true);
			usernameEditText.requestFocus();
		}
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
			mUiSettings
			.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
		}

		aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器	
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式

	}
	private class username_password_text implements OnClickListener{

		@Override
		public void onClick(View v) {
			if (v.getId()==usernameTextView.getId()) {
				username_click_num++;
				String[] username={"昵称  :","名号  :","账号  :"};
				usernameTextView.setText(username[username_click_num%3]);

			}
			if (v.getId()==passwordTextView.getId()) {
				password_click_num++;
				String[] password={"口令  :","密码  :","暗号  :"};
				passwordTextView.setText(password[password_click_num%3]);
			}

		}

	}
	private class settingsListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(isLogin){
				if(scVisiable)
				{
					sc.setVisibility(View.INVISIBLE);
					scVisiable = !scVisiable;
				}
				else 
				{
					sc.setVisibility(View.VISIBLE);
					scVisiable = !scVisiable;
				}
			}
		}

	}
	private class touristListener implements
	OnClickListener {
		@Override
		public void onClick(View v) {
			checknameButton.setVisibility(View.VISIBLE);
			showPasswordButton.setVisibility(View.VISIBLE);
			if (btn_tourist.getText().toString().equals("游客")) {
				if(!isWaiting)
				{
					isWaiting = true;
					localUser.userName = "游客";
					localUser.userType = t_userType.ut_visitor;
					edt_words.setEnabled(true);
					new Thread(new Runnable(){
						@Override
						public void run() {
							de.VisitorLogin();
						}
					}).start();
				}
			}
			if(btn_tourist.getText().toString().equals("取消")){

				btn_login.setVisibility(View.VISIBLE);
				checknameButton.setEnabled(false);
				btn_tourist.setText("游客");
				btn_register.setText("注册");
				usernameEditText.setText("");
				passwordEditText.setText("");
				passwordEditText.setHint("密码思密达~");
				usernameEditText.setHint("账号么么哒~");
			}
		}

	}

	private class loginListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(!isWaiting)
			{
				isWaiting = true;
				String userName = usernameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				if(userName.equals("") || password.equals(""))
				{
					Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
					isWaiting = false;
					return ;
				}
				localUser.userName = userName;
				localUser.userType = t_userType.ut_commonUserUnknow;
				final LoginUser lu = new LoginUser(userName,password);
				new Thread(new Runnable(){
					@Override
					public void run() {
						de.Login(lu);
					}
				}).start();
			}
		}

	}

	private class registerListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			checknameButton.setEnabled(true);
			if (btn_register.getText().toString().equals("注册")) {


				showPasswordButton.setVisibility(View.VISIBLE);
				btn_login.setVisibility(View.INVISIBLE);
				btn_tourist.setText("取消");	
				btn_register.setText("提交");
				usernameEditText.setText("");
				passwordEditText.setText("");

				usernameEditText.setHint("15字符以内");
				passwordEditText.setHint("6-20个字符");
			}
			else
			{
				if(!isWaiting)
				{
					isWaiting = true;
					if(usernameEditText.getText().toString().getBytes().length == 0)
					{
						Toast.makeText(MainActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
						isWaiting = false;
					}
					else if (usernameEditText.getText().toString().getBytes().length > 15)
					{
						Toast.makeText(MainActivity.this, "用户名长度应在15个字符(5个汉字)以内", Toast.LENGTH_SHORT).show();
						isWaiting = false;
					}
					else if(passwordEditText.getText().toString().getBytes().length < 6 || passwordEditText.getText().toString().getBytes().length > 20) {
						Toast.makeText(MainActivity.this, "亲，密码必须为6-20位哦", Toast.LENGTH_SHORT).show();
						isWaiting = false;
					}else{
						localUser.userName = usernameEditText.getText().toString();
						localUser.userType = t_userType.ut_commonUserUnknow;
						final RegisterUser ru = new RegisterUser(usernameEditText.getText().toString(),
								passwordEditText.getText().toString());
						new Thread(new Runnable(){
							@Override
							public void run() {
								de.Register(ru);
							}
						}).start();
						isLogin=true;
						edt_words.setEnabled(true);
					}
				}
			}



		}

	}

	private class showpasswordListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (showpassword_ischecked) {
				passwordEditText.setTransformationMethod(
						PasswordTransformationMethod.getInstance());
				showpassword_ischecked=!showpassword_ischecked;
			}else{
				passwordEditText.setTransformationMethod(
						HideReturnsTransformationMethod.getInstance());
				showpassword_ischecked=!showpassword_ischecked;
			}

		}

	}
	private class checkusernameListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			checknameButton.setEnabled(true);
			if(!isWaiting)
			{
				isWaiting = true;
				if(usernameEditText.getText().toString().getBytes().length == 0 || 
						usernameEditText.getText().toString().getBytes().length	>15)
				{
					Toast.makeText(MainActivity.this, "该用户名不可用", Toast.LENGTH_SHORT).show();
					isWaiting = false;
					return ;
				}
				final UserNameClass name = new UserNameClass(usernameEditText.getText().toString());
				new Thread(new Runnable(){
					@Override
					public void run() {
						de.CheckUserName(name);
					}
				}).start();

			}
		}
	}

	private class sendListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if(isLogin)
			{
				String str = edt_words.getText().toString();
				edt_words.setEms(10);
				int len = str.getBytes().length;
				if (len==0) {
					Toast.makeText(MainActivity.this, "发送的消息不能为空", Toast.LENGTH_SHORT).show();
				}
				else if (t_userType.ut_visitor == localUser.userType && len > 30) 
				{
					Toast.makeText(MainActivity.this, "游客消息长度限制在30个字符(10个汉字)以内,想要发更多？快去注册吧亲~", Toast.LENGTH_SHORT).show();
				}
				else if ( len > 60) 
				{
					Toast.makeText(MainActivity.this, "消息长度请限制在60个字符(20个汉字)以内", Toast.LENGTH_SHORT).show();
				}
				else {
					SendMessage(str);
				}

			}
			else
			{
				Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
				
			}
			edt_words.setText("");
			//			InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			//			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


		}
	}



	public void render(Marker marker, View view) {
		int portraitType = (int)marker.getObject();
		((ImageView) view.findViewById(R.id.badge))
		.setImageResource(portraitPic[portraitType]);
		//		switch(portraitType)
		//		{
		//		case 0 : 
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic0);
		//			break;
		//		case 1 : 
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic1_30);
		//			break;
		//		case 2 :
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic2_30);
		//			break;
		//		case 3 : 
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic3_30);
		//			break;
		//		case 4 : 
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic4_30);
		//			break;
		//		case 5 : 
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic5_30);
		//			break;
		//		case 6 : 
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic6_30);
		//			break;
		//		case 7 : 
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic7_30);
		//			break;
		//		case 8 : 
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.pic8_30);
		//			break;
		//		default:
		//			((ImageView) view.findViewById(R.id.badge))
		//			.setImageResource(R.drawable.badge_sa);
		//		}

		String title = marker.getTitle();
		TextView titleUi = ((TextView) view.findViewById(R.id.title));
		if (title != null) {
			SpannableString titleText = new SpannableString(title);
			titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
					titleText.length(), 0);
			titleUi.setTextSize(15);
			titleUi.setText(titleText);


		} else {
			titleUi.setText("");
		}
		String snippet = marker.getSnippet();
		TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
		if (snippet != null) {
			SpannableString snippetText = new SpannableString(snippet);
			snippetText.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
					snippetText.length(), 0);
			snippetUi.setTextSize(15);
			snippetUi.setText(snippetText);
		} else {
			snippetUi.setText("");
		}
	}

	@Override
	public View getInfoContents(Marker marker) {
		Log.i("infocontents", "getinfocontents");
		View infoContent = getLayoutInflater().inflate(
				R.layout.custom_info_contents, null);
		render(marker, infoContent);
		return infoContent;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		View infoWindow = getLayoutInflater().inflate(
				R.layout.custom_info_window, null);

		render(marker, infoWindow);
		return infoWindow;
	}

	public void onMapLoaded() {
		// 设置所有maker显示在当前可视区域地图中
		//		List<Marker> markerList = aMap.getMapScreenMarkers();

		//		LatLngBounds boundss = new LatLngBounds.Builder().include(
		//				new LatLng(39.90403, 116.407525)).build();

		LatLngBounds bounds = new LatLngBounds.Builder().include(
				new LatLng(localUser.location.latitude, localUser.location.longitude)).build();
		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		Log.i("onMarkerClick", "----------onmarkerclick---------");
		marker.showInfoWindow();
		return true;
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		mapView.onResume();
		if (clientMsg == null) {
			clientMsg = new ClientMsg(this,messageHandler);
			clientMsg.start();
			clientMsg.SetUserID(localUser.userID);
		}
		InitPortrait();

	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		WriteLocalData();
		if (clientMsg != null) { 
			clientMsg.interrupt();
			clientMsg = null; 
		}
		super.onStop();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		mapView.onDestroy();
		ReleaseSoundPool();
		if (clientMsg != null) { 
			clientMsg.interrupt();
			clientMsg = null; 
		}
		super.onDestroy();
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		deactivate();
		if (location != null) {
			if (mListener != null) {
				mListener.onLocationChanged(location);// 显示系统小蓝点
			}
			this.aMapLocation = location;// 判断超时机制
			//			geoLat = location.getLatitude();
			//			geoLng = location.getLongitude();
			localUser.location.latitude = location.getLatitude();
			localUser.location.longitude = location.getLongitude();
			Log.i("...............", ""+localUser.location.latitude+"   "+localUser.location.longitude);
			String cityCode = "";
			String desc = "";
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				cityCode = locBundle.getString("citycode");
				desc = locBundle.getString("desc");
			}
			//			String str = ("定位成功:(" + localUser.location.longitude + "," + localUser.location.latitdue + ")"
			//					+ "\n精    度    :" + location.getAccuracy() + "米"
			//					+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
			//					+ AMapUtil.convertToTime(location.getTime()) + "\n城市编码:"
			//					+ cityCode + "\n位置描述:" + desc + "\n省:"
			//					+ location.getProvince() + "\n市:" + location.getCity()
			//					+ "\n区(县):" + location.getDistrict() + "\n区域编码:" + location
			//					.getAdCode());
			//			System.out.println("....."+str);
		}
	}


	// ----------------------------jcy's code -------------------------------------------
	@SuppressLint("NewApi")
	private void ChangeDefaultPortrait()
	{
		ImageButton ib = (ImageButton)findViewById(R.id.imageButton);
		ib.setBackground(getResources().getDrawable(portraitPic[0]));
	}

	@SuppressLint("NewApi")
	public void InitPortrait()
	{
		ImageButton ib = (ImageButton)findViewById(R.id.imageButton);
		if(!isLogin)
		{
			ib.setBackground(getResources().getDrawable(portraitPic[0]));
		}
		else {
			ib.setBackground(getResources().getDrawable(portraitPic[localUser.portraitType]));
		}
	}
	
	@SuppressLint("NewApi")
	public void ChangeBG()
	{
		if(!isLogin)
		{
			return;
		}
		if(localUser.userType == t_userType.ut_visitor)
		{
			localUser.portraitType = 0;
			Toast.makeText(this, "游客不能切换头像，快去注册吧亲~", Toast.LENGTH_SHORT).show();
			return ;
		}

		ImageButton ib = (ImageButton)findViewById(R.id.imageButton);
		localUser.portraitType ++;
		localUser.portraitType %= localUser.maxPortraitType;
		ib.setBackground(getResources().getDrawable(portraitPic[localUser.portraitType]));
	}

	
	private void InitMarkers(HashSet<String> msgJsons)
	{
		Iterator it=msgJsons.iterator(); 
		while(it.hasNext())
		{
			String str = (String)it.next();
			try {
				LBMessage msg = JsonUtils.GetClassFromJSON(str, LBMessage.class);
				ProcessMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ReadLocalData()
	{
		SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
		isLogin = sharedPreferences.getBoolean("logined", false);
		if(isLogin)
		{
			localUser.userName = sharedPreferences.getString("userName", "");
			localUser.userID = sharedPreferences.getInt("userID", 0);
			soundAvaliable = sharedPreferences.getBoolean("soundAvaliable", false);
			localUser.userType = t_userType.ut_commonUserUnknow;
			localUser.portraitType = sharedPreferences.getInt("userPortraitType", 0);
		}
		localUser.location.longitude = sharedPreferences.getFloat("longitude", (float)(localUser.startLocation.longitude));
		localUser.location.latitude = sharedPreferences.getFloat("latitude", (float)(localUser.startLocation.latitude));
		
		InitMarkers((HashSet<String>)(sharedPreferences.getStringSet("localMarkers", new HashSet<String>())));
	}

	private void WriteLocalData()

	{
		SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		if(isLogin && t_userType.ut_visitor != localUser.userType)
		{
			editor.putBoolean("logined", true);
			editor.putString("userName", localUser.userName);
			editor.putInt("userID", localUser.userID);
			editor.putInt("userPortraitType", localUser.portraitType);
			editor.putBoolean("soundAvaliable", soundAvaliable);
			
		}
		else {
			editor.putBoolean("logined", false);
		}
		editor.putFloat("longitude", (float)(localUser.location.longitude));
		editor.putFloat("latitude", (float)(localUser.location.latitude));
		editor.putStringSet("localMarkers", markerManager.GetLocalMarkers());
		editor.commit();

	}
	private Marker AddMarker(String userName, String content, LatLng location, t_userType userType, int portraitType)
	{
		Marker marker = null;
		int color = new Random().nextInt(10);
		switch(color)
		{
		case 0 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
							.perspective(true));
			break;

		case 1 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
							.perspective(true));
			break;

		case 2 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
							.perspective(true));
			break;

		case 3 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
							.perspective(true));
			break;

		case 4 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
							.perspective(true));
			break;

		case 5 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
							.perspective(true));
			break;
		case 6 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED))
							.perspective(true));
			break;
		case 7 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
							.perspective(true));
			break;
		case 8 :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
							.perspective(true));
			break;
		default :
			marker = aMap.addMarker(new MarkerOptions().snippet(content)
					.position(location)
					.title(userName)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
							.perspective(true));
		}
		marker.setObject(portraitType);
		marker.showInfoWindow();
		return marker;
	}

	private void RemoveMarker(ArrayList<Marker> markers) {
		int number = markers.size();
		for(int i=0; i<number; i++)
		{
			markers.get(i).remove();
		}
	}






	private void ProcessMessage(LBMessage msg) {
		//1. 地图上显示
		Marker marker = AddMarker(msg.GetUserName(), msg.GetMessage(), new LatLng(msg.GetLocation().latitude,
				msg.GetLocation().longitude), msg.GetUserType(), msg.GetPortraitType());

		
		//2. 加入管理器
		final ArrayList<Marker> markers = markerManager.AddMarkerToManager(marker, msg);
		
		//3. paly sounds
		if(soundAvaliable)
		{
			PlaySound(GetSoundNumber());
		}
		
		//4. delete repeat
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (markers.size()>0)
				{
					for(int i =0; i<markers.size(); i++)
					{
						markers.get(i).remove();
					}
				}
			}
		}).start();
	}


	private void SendMessage(String message)
	{
		//suprise
		if(message.equals("只言片语：放我走"))
		{
			isLogin = false;
			WriteLocalData();
			localUser.portraitType = 0;
			localUser.userType = t_userType.ut_visitor;
			ChangeDefaultPortrait();
			relativeLayout.setVisibility(View.VISIBLE);
			return ;
		}else if(message.equals("只言片语：我在哪"))
		{
			onMapLoaded();
			return ;
		}
		else if(message.equals("只言片语：空消息"))
		{
			SendMessage("");
			return ;
		}
		else if(message.equals("只言片语：消息提醒"))
		{
			OnSoundSwitch();
			return ;
		}
		else if(message.equals("只言片语：清屏"))
		{
			final Marker myMarker = AddMarker(localUser.userName, "清屏", new LatLng(localUser.location.latitude,
					localUser.location.longitude), t_userType.ut_myself, localUser.portraitType);
			myMarker.hideInfoWindow();
			myMarker.remove();
			
			final ArrayList<Marker> markers = markerManager.ClearScreen();
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (markers.size()>0)
					{
						for(int i =0; i<markers.size(); i++)
						{
							markers.get(i).remove();
						}
					}
					
					
				}
			}).start();
			
			WriteLocalData();
			return ;
		}

		if(!isWaiting)
		{
			isWaiting = true;
			//send_1 add message to map
			Marker marker = AddMarker(localUser.userName, message, new LatLng(localUser.location.latitude,
					localUser.location.longitude), t_userType.ut_myself, localUser.portraitType);
			
			final LBMessage msg = new LBMessage(localUser.userType, localUser.userID, localUser.userName, message, localUser.location,localUser.portraitType,new Date().getTime());
			
			//send_2 add maker to manager
			final ArrayList<Marker> markers = markerManager.AddMarkerToManager(marker, msg);
			

			//send_3 delete repeat and send to server
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (markers.size()>0)
					{
						for(int i =0; i<markers.size(); i++)
						{
							markers.get(i).remove();
						}
					}
					de.SendMessage(msg);
				}
			}).start();
		}
	}

	private void ShowSendMessageResult(int code) {
		//send_5 get response
		if(200 == code)
		{
			Toast.makeText(MainActivity.this, "消息发送成功 ", Toast.LENGTH_SHORT).show();
		} else if(1003 == code) {
			Toast.makeText(MainActivity.this, "消息发送被服务器拒绝", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(MainActivity.this, "消息发送失败，网络错误(" + code + ")", Toast.LENGTH_SHORT).show();
		}
		isWaiting = false;
	}

	private void ShowNameCheckResult(int code) {
		if(200 == code)
		{
			Toast.makeText(MainActivity.this, "该用户名可用", Toast.LENGTH_SHORT).show();
		} else if(1003 == code) {
			Toast.makeText(MainActivity.this, "该用户名不可用", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(MainActivity.this, "检测用户名失败，网络错误(" + code + ")", Toast.LENGTH_SHORT).show();
		}
		isWaiting = false;
	}

	private void ShowRegisterResult(int code) {
		if(code > 10000)
		{
			Toast.makeText(MainActivity.this, "注册成功，您获得账号: "+ code, Toast.LENGTH_SHORT).show();
			localUser.userID = code;
			clientMsg.SetUserID(code);
			isLogin = true;
			checknameButton.setVisibility(View.INVISIBLE);
			showPasswordButton.setVisibility(View.INVISIBLE);
			btn_login.setVisibility(View.VISIBLE);
			btn_tourist.setText("游客");
			btn_register.setText("注册");
			usernameEditText.setText("");
			passwordEditText.setText("");
			passwordEditText.setHint("密码思密达~");
			usernameEditText.setHint("账号么么哒~");
			relativeLayout.setVisibility(View.INVISIBLE); 

		}
		else if(1003 == code)
		{
			Toast.makeText(MainActivity.this, "用户名不可用，换个名字试试吧", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(MainActivity.this, "注册失败，网络错误(" + code + ")", Toast.LENGTH_SHORT).show();
		}
		isWaiting = false;
	}

	private void ShowLoginResult(int code) {
		if(code > 9999)
		{
			Toast.makeText(MainActivity.this, "登录成功，欢迎您", Toast.LENGTH_SHORT).show();
			localUser.userID = code;
			clientMsg.SetUserID(code);
			isLogin = true;
			edt_words.setEnabled(true);
			relativeLayout.setVisibility(View.INVISIBLE); 
			//			//extra
			//			Toast.makeText(MainActivity.this, "您的ID：" + code , Toast.LENGTH_SHORT).show();
		}
		else if(1003 == code)
		{
			Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(MainActivity.this, "登录失败，网络错误(" + code + ")", Toast.LENGTH_SHORT).show();
		}
		isWaiting = false;
	}

	private void ShowVisitorLoginResult(int code) {
		if(code > 1000000)
		{
			Toast.makeText(MainActivity.this, "游客登录成功，欢迎您", Toast.LENGTH_SHORT).show();
			localUser.userID = code;
			localUser.userName = "游客";
			localUser.userType = t_userType.ut_visitor;
			clientMsg.SetUserID(code);
			isLogin = true;
			relativeLayout.setVisibility(View.INVISIBLE);
			//extra
			//			Toast.makeText(MainActivity.this, "您的ID：" + code , Toast.LENGTH_SHORT).show();
		}
		else if(1003 == code)
		{
			Toast.makeText(MainActivity.this, "游客无法登录，服务器繁忙", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(MainActivity.this, "游客登陆失败，网络错误(" + code + ")", Toast.LENGTH_SHORT).show();
		}
		isWaiting = false;
	}

	private void ShowFeedbackResult(int code) {
		if(code == 200)
		{
			feedEditText.setText("");
			edt_words.setEnabled(true);
			send.setEnabled(true);
			settingsButton.setEnabled(true);
			Toast.makeText(MainActivity.this, "用户反馈成功", Toast.LENGTH_SHORT).show();
			feedbackRelativeLayout.setVisibility(View.INVISIBLE);
		}
		else if(1003 == code)
		{
			Toast.makeText(MainActivity.this, "用户反馈失败", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(MainActivity.this, "用户反馈失败，网络错误(" + code + ")", Toast.LENGTH_SHORT).show();
		}
		isWaiting = false;
	}

	private class MessageHandler extends Handler {
		public MessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			UIMessage uimsg = (UIMessage)msg.obj;
			switch(uimsg.messageType)
			{
			case uimt_login :
				ShowLoginResult((int)uimsg.content);
				break;
			case uimt_nameCheck :
				ShowNameCheckResult((int)uimsg.content);
				break;
			case uimt_register :
				ShowRegisterResult((int)uimsg.content);
				break;
			case uimt_sendMessage :
				ShowSendMessageResult((int)uimsg.content);
				break;
			case uimt_vistorLogin :
				ShowVisitorLoginResult((int)uimsg.content);
				break;
			case uimt_receiveMessage :
				ProcessMessage((LBMessage)uimsg.content);
				break;
			case uimt_removeMessage :
				RemoveMarker((ArrayList<Marker>)uimsg.content);
				break;
			case uimt_sendFeedback :
				ShowFeedbackResult((int)uimsg.content);
				break;
			}
		}


	}

	//-----------------------------new sound----------------------

	private void InitSoundPool()
	{
		sp=new SoundPool(1, AudioManager.STREAM_MUSIC, 0); 
		spMap=new HashMap<Integer,Integer>(); 
		spMap.put(1, sp.load(this, R.raw.s_1, 1)); 
		spMap.put(2, sp.load(this, R.raw.s_2, 1));
		spMap.put(3, sp.load(this, R.raw.s_3, 1)); 
		spMap.put(4, sp.load(this, R.raw.s_4, 1)); 
		spMap.put(5, sp.load(this, R.raw.s_5, 1)); 
		spMap.put(6, sp.load(this, R.raw.s_6, 1)); 
		spMap.put(7, sp.load(this, R.raw.s_7, 1)); 
		spMap.put(8, sp.load(this, R.raw.s_8, 1)); 
	}

	private void PlaySound(int sound)
	{
		AudioManager am=(AudioManager)this.getSystemService(this.AUDIO_SERVICE);
		float audioMaxVolumn=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float audioCurrentVolumn=am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumnRatio=audioCurrentVolumn/audioMaxVolumn; 
		sp.play(spMap.get(sound), volumnRatio, volumnRatio, 1, 0, 1); 
	} 

	private int GetSoundNumber()
	{
		soundIndex++;
		soundIndex %= soundArray.length; 
		return soundArray[soundIndex];
	}

	private void ReleaseSoundPool()
	{
		sp.release();
	}

	private void OnSoundSwitch()
	{
		if(soundAvaliable)
		{
			soundAvaliable = false;
			Toast.makeText(MainActivity.this, "消息提醒已关闭", Toast.LENGTH_SHORT).show();
			soundIndex = -1;
		}
		else {
			soundAvaliable = true;
			Toast.makeText(MainActivity.this, "消息提醒已开启", Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (aMap != null) {

		}

	}
	//
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		/**
		 * 一像素代表多少米
		 */
		//		case R.id.buttonScale:
		//			float scale = aMap.getScalePerPixel();
		//			ToastUtil.show(MainActivity.this, "每像素代表" + scale + "米");
		//			break;
		/**
		 * 设置地图默认的比例尺是否显示
		 */
//		case R.id.scale_toggle:
//			mUiSettings.setScaleControlsEnabled(((CheckBox) view).isChecked());
//
//			break;
			/**
			 * 设置地图默认的缩放按钮是否显示
			 */
		case R.id.zoom_toggle:
			mUiSettings.setZoomControlsEnabled(((CheckBox) view).isChecked());

			break;
			/**
			 * 设置地图默认的指南针是否显示
			 */
		case R.id.compass_toggle:
			mUiSettings.setCompassEnabled(((CheckBox) view).isChecked());
			break;
			/**
			 * 设置地图默认的定位按钮是否显示
			 */
//		case R.id.mylocation_toggle:
//			aMap.setLocationSource(this);// 设置定位监听
//			mUiSettings.setMyLocationButtonEnabled(((CheckBox) view)
//					.isChecked()); // 是否显示默认的定位按钮
//			aMap.setMyLocationEnabled(((CheckBox) view).isChecked());// 是否可触发定位并显示定位层
//			break;
			/**
			 * 设置地图是否可以手势滑动
			 */
		case R.id.scroll_toggle:
			mUiSettings.setScrollGesturesEnabled(((CheckBox) view).isChecked());
			break;
			/**
			 * 设置地图是否可以手势缩放大小
			 */
		case R.id.zoom_gestures_toggle:
			mUiSettings.setZoomGesturesEnabled(((CheckBox) view).isChecked());
			break;
			/**
			 * 设置地图是否可以倾斜
			 */
		case R.id.tilt_toggle:
			mUiSettings.setTiltGesturesEnabled(((CheckBox) view).isChecked());
			break;
			/**
			 * 设置地图是否可以旋转
			 */
		case R.id.rotate_toggle:
			mUiSettings.setRotateGesturesEnabled(((CheckBox) view).isChecked());
			break;
		default:
			break;
		}
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (aMapLocManager == null) {
			aMapLocManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);//
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
			 */
			// Location API定位采用GPS和网络混合定位方式，时间最短是2000毫秒
			aMapLocManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destory();
		}
		aMapLocManager = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			// if(isLogin){
			if (scVisiable) {
				sc.setVisibility(View.INVISIBLE);
				scVisiable = !scVisiable;
			} else {
				sc.setVisibility(View.VISIBLE);
				scVisiable = !scVisiable;
			}
		}
		// }
		return super.onKeyDown(keyCode, event);
	}

}
