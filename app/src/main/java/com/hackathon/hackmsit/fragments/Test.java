package com.hackathon.hackmsit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//placeholder fragment

public class Test extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
        *
        *
        *
        *
        *
        * tt=new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (before == 0 && count == 1 && s.charAt(start) == '\n') {
                    String spaces = new String(new char[spacing]).replace("\0", " ");
                    int cursorPos = mCodeEditText.getSelectionStart();
                    String content = mCodeEditText.getText().toString();
                    content = new StringBuffer(content).insert(cursorPos, spaces).toString();
                    mCodeEditText.setText(content);
                    mCodeEditText.setSelection(cursorPos + spacing);


                }
                mCodeEditText.removeTextChangedListener(tt);
                Log.d("ishaan", s.toString());
                bs=matchtext(s.toString());
                mCodeEditText.setText(bs);
                mCodeEditText.addTextChangedListener(tt);

                //mCodeEditText.setText(bs);
                //mCodeEditText.setText(matchtext(mCodeEditText.getText().toString()));

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // bs = matchtext(mCodeEditText.getText().toString());
                //mCodeEditText.setText(bs);
                //Log.d("ishaan",bs);
                mCodeEditText.setSelection(s.length());

            }
        };

        mCodeEditText.addTextChangedListener(tt);




        Spanned matchtext(String s)
    {
        //Pattern p =Pattern.compile(check[0]);

        String a=s;
        for(int i=0;i<Constants.keyWords.length;i++) {
            a = a.replaceAll(Constants.keyWords[i], "<font color=\"#c5c5c5\">" + Constants.keyWords[i] + "</font>");
            //a = s.replaceAll(";", "<font color=\"#c5c5c5\">" + ";" + "</font>");
            Log.d("ishaan","here");
        }
        Spanned ab = Html.fromHtml(a);
        return ab;

    }
        * */


        return null;
    }
}
