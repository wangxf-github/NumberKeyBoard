package com.apicloud.numkeyboardmoudle;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apicloud.numkeyboard.DefineKeyboardUtil;
import com.apicloud.numkeyboard.StockKeyboardView;
import com.uzmap.pkg.uzcore.UZResourcesIDFinder;
import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.annotation.UzJavascriptMethod;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

public class NumKeyBoard extends UZModule
{
  static final int ACTIVITY_REQUEST_CODE_A = 100;
  private View view;
  private DefineKeyboardUtil mDefineKeyboardUtil;
  private EditText edit_number_keyboard;
  private UZWebView uzWebView;
  private TextView textView;

  public NumKeyBoard(UZWebView webView)
  {
    super(webView);
    this.uzWebView = webView;
  }

  @UzJavascriptMethod
  public void jsmethod_addView(UZModuleContext moduleContext)
  {
    removeView();
    Log.e("===", "sadfsd");
   // getContext().getWindow().setSoftInputMode(3);
    hideKeyBoard();
    String title = moduleContext.optString("title");	
    int layoutId = UZResourcesIDFinder.getResLayoutID("activity_define_keyboard");
    this.view = View.inflate(this.mContext, layoutId, null);
    int titleID = UZResourcesIDFinder.getResIdID("title");
	textView = (TextView) view.findViewById(titleID);
	textView.setText(title);
    int numKeyID = UZResourcesIDFinder.getResIdID("edit_number_keyboard");
    this.edit_number_keyboard = ((EditText)this.view.findViewById(numKeyID));
    edit_number_keyboard.setTransformationMethod(MPasswordTransformationMethod.getInstance());
    int numKeyLayID = UZResourcesIDFinder.getResIdID("keyboard_view");
    this.mDefineKeyboardUtil = new DefineKeyboardUtil(
      this.mContext, this.edit_number_keyboard, 
      (StockKeyboardView)this.view.findViewById(numKeyLayID), this.view, moduleContext);
    this.mDefineKeyboardUtil.showKeyboard();
    hideKeyBoard();

    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1, -2);
    rlp.setMarginEnd(0);
    addViewToCutWindow(this.view, rlp);
  }

  public void removeView() {
    removeViewFromCurWindow(this.view);
  }

  public void hideKeyBoard()
  {
    InputMethodManager imm = (InputMethodManager)this.mContext
      .getSystemService("input_method");

    imm.hideSoftInputFromWindow(this.uzWebView.getWindowToken(), 0);
  }

  public void addViewToCutWindow(View view, RelativeLayout.LayoutParams params)
  {
    this.uzWebView.addView(view, params);
  }

  public void jsmethod_removeView(UZModuleContext moduleContext)
  {
    removeViewFromCurWindow(this.view);
    this.mDefineKeyboardUtil.isShowKeyboard = false;
  }


}