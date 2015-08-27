package com.apicloud.numkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * 
 * @author tr
 * @time 2014-3-7
 * @description 自定义keyboardView控件，重写onLongPress()实现长按删除按键功能
 */
public class StockKeyboardView extends KeyboardView{

	public StockKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public StockKeyboardView(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);   
    }
	@Override
	protected boolean onLongPress(Key popupKey) {
		// TODO Auto-generated method stub
		
		if(popupKey.codes[0] == Keyboard.KEYCODE_DELETE){
		
			//可使用OnKeyboardActionListener中的各种方法实现该功能
		getOnKeyboardActionListener().onKey(Keyboard.KEYCODE_DELETE, null);  
			
		}
		return super.onLongPress(popupKey);
	}   
	

}
