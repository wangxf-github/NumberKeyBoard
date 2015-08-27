package com.apicloud.numkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import com.uzmap.pkg.uzcore.UZResourcesIDFinder;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;
import java.io.PrintStream;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class DefineKeyboardUtil
{
  private Context mContext;
  private EditText mEditText;
  private Keyboard keyboard_number;
  private StockKeyboardView mKeyboardView;
  private String keyValues = "";
  private UZModuleContext uzModuleContext;
  private View view;
  private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener()
  {
    public void swipeUp()
    {
    }

    public void swipeRight()
    {
    }

    public void swipeLeft()
    {
    }

    public void swipeDown()
    {
    }

    public void onText(CharSequence text)
    {
    }

    public void onRelease(int primaryCode)
    {
    }

    public void onPress(int primaryCode)
    {
    }

    public void onKey(int primaryCode, int[] keyCodes)
    {
      Editable editable = DefineKeyboardUtil.this.mEditText.getText();
      int start = DefineKeyboardUtil.this.mEditText.getSelectionStart();

      switch (primaryCode) {
      case -3:
        Log.e("__", "1111111111111111111111");
        DefineKeyboardUtil.this.keyValues = editable.toString();
        if (DefineKeyboardUtil.this.keyValues.length() == 6) {
          DefineKeyboardUtil.this.getKeyNumber(DefineKeyboardUtil.this.uzModuleContext);
          DefineKeyboardUtil.this.hideKeyboard();
        } else {
          DefineKeyboardUtil.this.shake();
        }
        break;
      case -5:
        if ((editable != null) && (editable.length() > 0) && 
          (start > 0)) {
          editable.delete(start - 1, start);
        }

        break;
      case -4:
      default:
        editable.insert(start, Character.toString((char)primaryCode));
      }
    }
  };

  public boolean isShowKeyboard = false;

  public DefineKeyboardUtil(Context mContext, EditText mEditText, StockKeyboardView mKeyboardView, View view, UZModuleContext moduleContext)
  {
    this.mContext = mContext;
    this.mEditText = mEditText;
    this.mKeyboardView = mKeyboardView;
    this.view = view;
    this.uzModuleContext = moduleContext;
    int symbolsID = UZResourcesIDFinder.getResXmlID("symbols");
    this.keyboard_number = new Keyboard(mContext, symbolsID);

    this.mKeyboardView.setOnKeyboardActionListener(this.listener);
  }

  public void clearEditTextContent()
  {
    if (this.mEditText != null) {
      Editable editable = this.mEditText.getText();
      int start = this.mEditText.getSelectionStart();
      if (start > 0) {
        editable.clear();
        this.keyValues = null;
      }
    }
  }

  public void showKeyboard()
  {
    clearEditTextContent();
    randomNumKey();
    int visibility = this.mKeyboardView.getVisibility();
    if ((visibility == 8) || (visibility == 4)) {
      this.mKeyboardView.setKeyboard(this.keyboard_number);
      this.mKeyboardView.setVisibility(0);
    }
    this.isShowKeyboard = true;
    Log.e("--", "comming");
 
  }

  public String hideKeyboard()
  {
    String str = this.mEditText.getText().toString();
    int visibility = this.view.getVisibility();
    if (visibility == 0) {
      this.view.setVisibility(4);
      clearEditTextContent();
      return str;
    }
    return str;
  }

  private void randomNumKey()
  {
    List keyList = this.keyboard_number.getKeys();
    int size = keyList.size();
    for (int i = 0; i < size; i++) {
      int random_a = (int)(Math.random() * size);
      int random_b = (int)(Math.random() * size);
      int code = ((Keyboard.Key)keyList.get(random_a)).codes[0];
      int codes = ((Keyboard.Key)keyList.get(random_b)).codes[0];
      System.out.println(code + "   " + codes);
      if ((code != -5) && (code != -3) && (codes != -5) && (codes != -3))
      {
        CharSequence label = ((Keyboard.Key)keyList.get(random_a)).label;

        ((Keyboard.Key)keyList.get(random_a)).codes[0] = ((Keyboard.Key)keyList.get(random_b)).codes[0];
        ((Keyboard.Key)keyList.get(random_a)).label = ((Keyboard.Key)keyList.get(random_b)).label;

        ((Keyboard.Key)keyList.get(random_b)).codes[0] = code;
        ((Keyboard.Key)keyList.get(random_b)).label = label;
      }
    }
  }

  public void getKeyNumber(UZModuleContext moduleContext)
  {
    if ((this.keyValues != null) && (this.keyValues != "")) {
      JSONObject ret = new JSONObject();
      try {
        ret.put("keyboard", this.keyValues);
        Log.e("++++++", "-----------");
        moduleContext.success(ret, true);
      } catch (JSONException e) {
        e.printStackTrace();
        try {
          ret.put("error", e.getMessage());
        }
        catch (JSONException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  public void shake() {
    int shakeID = UZResourcesIDFinder.getResAnimID("shake");
    Animation animation = AnimationUtils.loadAnimation(this.mContext, shakeID);
    this.mEditText.startAnimation(animation);
  }
}