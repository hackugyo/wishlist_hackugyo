/*
 * Copyright 2012 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kevinsawicki.wishlist;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

/**
 * Utilities for working with an {@link EditText}
 */
public class EditTextUtils {

    /**
     * A {@link Runnable} that returns a boolean
     */
    public static interface BooleanRunnable {

        /**
         * Runnable that returns true when run, false when not run
         *
         * @return true if run, false otherwise
         */
        boolean run();
    }

    /**
     * Bind given runnable to be invoked when the
     *
     * @param editText
     * @param runnable
     * @return edit text
     */
    public static EditText onDone(final EditText editText,
                                  final BooleanRunnable runnable) {
        if ((editText.getInputType() & TYPE_TEXT_FLAG_MULTI_LINE) == 0)
            editText.setOnKeyListener(new OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode != KEYCODE_ENTER)
                        return false;
                    if (event == null)
                        return false;
                    if (event.getAction() != ACTION_DOWN)
                        return false;

                    return runnable.run();
                }
            });

        editText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE)
                    return runnable.run();
                else
                    return false;
            }
        });

        return editText;
    }

    /**
     * キーボードを閉じます
     * 
     * @see <a href="http://stackoverflow.com/a/17789187/2338047">参考ページ</a>
     * @param context
     * @param view
     */
    public static void closeKeyboard(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return;
        }

        if (context instanceof Activity) {
            // ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            View v = ((Activity) context).getCurrentFocus();
            if (v == null) {
                v = new View(((Activity) context));
            }
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**
     * {@link EditText}の入力制限をかけます．<br>
     * {@link EditText#setInputType(int)}では，最初に表示するキーボードの種類しか制限できません．<br>
     * 
     * @return {@link EditText#setFilters(InputFilter[])}に渡してください．
     */
    public static InputFilter[] createNumericInputFilter() {
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, //
                    int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("^[0-9]+$")) {
                    return source;
                } else {
                    return "";
                }
            }
        };
        return new InputFilter[] { inputFilter };
    }

    /**
     * {@link EditText}の入力制限をかけます．<br>
     * {@link EditText#setInputType(int)}では，最初に表示するキーボードの種類しか制限できません．<br>
     * 
     * @return {@link EditText#setFilters(InputFilter[])}に渡してください．
     */
    public static InputFilter[] createAlphaNumericInputFilter() {
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, //
                    int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("^[0-9a-zA-Z]+$")) {
                    return source;
                } else {
                    return "";
                }
            }
        };
        return new InputFilter[] { inputFilter };
    }
}
