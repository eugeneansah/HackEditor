package com.hackathon.hackmsit.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.hackathon.hackmsit.R;
import com.hackathon.hackmsit.data.NoteManager;
import com.hackathon.hackmsit.fragments.Test;
import com.hackathon.hackmsit.models.Note;
import com.hackathon.hackmsit.utilities.Constants;
import com.hackathon.hackmsit.utilities.SpaceTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotePlainEditorActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    TextWatcher tt = null;
    private MultiAutoCompleteTextView mCodeEditText;
    private Note mCurrentNote = null;
    Bundle args;
    private TextInputLayout inputLayoutTitle;
    private CoordinatorLayout mCoordinatorLayout;
    private String[] keys = {"{", "}", "(", ")", ";"};

    private int spacing, currPosition;
    private SpannableStringBuilder bs;

    public NotePlainEditorActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("aakash", "onCeate was called ");
        setContentView(R.layout.activity_note_plain_editor);

        args = getIntent().getExtras();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*setHasOptionsMenu(true);
        args = getArguments();*/
        getCurrentNote();

        mTitleEditText = (EditText) findViewById(R.id.edit_text_title);
        //mContentEditText = (EditText) mRootView.findViewById(R.id.edit_text_code);
        mCodeEditText = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        inputLayoutTitle = (TextInputLayout) findViewById(R.id.input_layout_title);
        //inputLayoutCode = (TextInputLayout) mRootView.findViewById(R.id.input_layout_code);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        spacing = 0;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(NotePlainEditorActivity.this, android.R.layout.simple_list_item_1, Constants.keyWords);
        mCodeEditText.setAdapter(adapter);
        mCodeEditText.setThreshold(2);
        mCodeEditText.setTokenizer(new SpaceTokenizer());
        mCodeEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long position) {

            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int numTab = tab.getPosition();
                if (mCodeEditText.hasFocus()) {
                    int cursorPos = mCodeEditText.getSelectionStart();
                    String content = mCodeEditText.getText().toString();
                    content = new StringBuffer(content).insert(cursorPos, keys[numTab]).toString();
                    mCodeEditText.setText(content);
                    mCodeEditText.setSelection(cursorPos + 1);
                } else {
                    String content = mCodeEditText.getText().toString() + keys[numTab];
                    mCodeEditText.setText(content);
                    mCodeEditText.setSelection(content.length());
                }

                //Indentation method
                if (keys[numTab].equals("{")) {
                    spacing += 4;
                    Log.d("spacing", String.valueOf(spacing));
                } else if (keys[numTab].equals("}") && spacing >= 4) {
                    spacing -= 4;
                    Log.d("spacing", String.valueOf(spacing));
                    int cursorPos = mCodeEditText.getSelectionStart();
                    String content = mCodeEditText.getText().toString();
                    String spaces = new String(new char[spacing]).replace("\0", " ");
                    content = new StringBuffer(content).insert(cursorPos - 1, "\n" + spaces).toString();
                    mCodeEditText.setText(content);
                    mCodeEditText.setSelection(cursorPos + 1 + spacing);
                    //Log.d("ishaan",""+mCodeEditText.getText().length());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });

        tt = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (before == 0 && count == 1 && s.charAt(start) == '\n') {
                    if (spacing != 0) {
                        String spaces = new String(new char[spacing]).replace("\0", " ");
                        int cursorPos = mCodeEditText.getSelectionStart();
                        //int cursorPos = mCodeEditText.getText().length();
                        String content = mCodeEditText.getText().toString();
                        content = new StringBuffer(content).insert(cursorPos, spaces).toString();
                        mCodeEditText.setText(content);
                        mCodeEditText.setSelection(cursorPos + spacing - 1);
                    }
                } else {
                    int cursorPos = mCodeEditText.getSelectionStart();
                    mCodeEditText.removeTextChangedListener(tt);
                    bs = matchText(mCodeEditText.getText().toString(), mCodeEditText.getSelectionStart());
                    mCodeEditText.setText(bs);
                    mCodeEditText.setSelection(cursorPos);
                    mCodeEditText.addTextChangedListener(tt);
                }

                /*currPosition = mCodeEditText.getSelectionStart();
                mCodeEditText.removeTextChangedListener(tt);
                //Log.d("ishaan", s.toString());
                bs = matchtext(s.toString(), currPosition);
                mCodeEditText.setText(bs);
                mCodeEditText.addTextChangedListener(tt);*/

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

                //Log.d("ishaan",bs);
                //CodeEditText.setSelection(currPosition);

            }
        };

        mCodeEditText.addTextChangedListener(tt);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        for (String key : keys) {
            adapter.addFragment(new Test(), key);
        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /*@Override
    public void onDetach() {
        super.onDetach();
    }*/

    @Override
    public void onResume() {
        super.onResume();
        if (mCurrentNote != null) {
            populateFields();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Log.d("aakash", "onSaveInstanceState was called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Log.d("aakash", "onRestoreInstanceState was called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit_plain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                //delete note
                if (mCurrentNote != null) {
                    promptForDelete();
                } else {
                    makeSnackbar("Error deleting code, please save it first");
                }
                break;
            case R.id.action_save:
                //save note
                View view = this.getCurrentFocus();
                if (view != null) {
                    ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                if (saveNote()) {
                    makeSnackbar(mCurrentNote != null ? "Code updated" : "Code saved");
                }
                break;
            case R.id.action_compile:
                //compile code
                view = this.getCurrentFocus();
                if (view != null) {
                    ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                if (saveNote()) {
                    //makeSnackbar(mCurrentNote != null ? "Code updated" : "Code saved");
                    startActivity(new Intent(this, CompileActivity.class));
                }
                //startActivity(new Intent(this, CompileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    private void makeSnackbar(String message) {
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    private void getCurrentNote() {
        if (args != null && args.containsKey("id")) {
            long id = args.getLong("id", 0);
            if (id > 0) {
                mCurrentNote = NoteManager.newInstance(this).getNote(id);
            }
        }
    }

    private boolean saveNote() {
        String title = mTitleEditText.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            inputLayoutTitle.setError("Title is required");
            return false;
        } else {
            inputLayoutTitle.setErrorEnabled(false);
        }

        String content = mCodeEditText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            mCodeEditText.setError("Code is required");
            return false;
        } /*else {
            mCodeEditText.er(false);
        }*/
        if (mCurrentNote != null) {
            mCurrentNote.setContent(content);
            mCurrentNote.setTitle(title);
            NoteManager.newInstance(this).update(mCurrentNote);
        } else {
            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);
            NoteManager.newInstance(this).create(note);
        }
        return true;
    }

    private void populateFields() {
        mTitleEditText.setText(mCurrentNote.getTitle());
        mCodeEditText.setText(mCurrentNote.getContent());
    }

    public void promptForDelete() {
        final String titleOfNoteTobeDeleted = mCurrentNote.getTitle();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm Action");
        alertDialog.setMessage("Delete " + titleOfNoteTobeDeleted + "?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NoteManager.newInstance(NotePlainEditorActivity.this).delete(mCurrentNote);

                SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("deleted", "1");
                editor.apply();
                //makeSnackbar("Code deleted");
                //startActivity(new Intent(getActivity(), MainActivity.class));
                Intent intent = new Intent(NotePlainEditorActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    SpannableStringBuilder matchText(String s, int pos) {

        //Pattern p =Pattern.compile(check[0]);
        SpannableStringBuilder sb = new SpannableStringBuilder(s);
        for (int i = 0; i < Constants.keyWords.length; i++) {
            Pattern p = Pattern.compile(Constants.keyWords[i], Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(s);
            while (m.find()) {
                //String word = m.group();
                //String word1 = notes.substring(m.start(), m.end());
                sb.setSpan(new ForegroundColorSpan(Color.rgb(255, 0, 0)), m.start(), m.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }

        /*Spannable abc = new SpannableString(s);
        //String a = s;
        for (int i = 0; i < Constants.keyWords.length; i++) {
            if (pos - Constants.keyWords[i].length() >= 0) {
                int j = s.indexOf(Constants.keyWords[i]);
                if (j != -1) {
                    if ((s.subSequence(j, pos)).equals(Constants.keyWords[i]))
                        abc.setSpan(new ForegroundColorSpan(Color.BLUE), j, pos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    //a = a.replaceAll(Constants.keyWords[i], "<font color=\"#c5c5c5\">" + Constants.keyWords[i] + "</font>");
                    //a = s.replaceAll(";", "<font color=\"#c5c5c5\">" + ";" + "</font>");
                }
            }
        }*/
        //abc = setSpan(a);
        return sb;

    }

    private void setColor(MultiAutoCompleteTextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, MultiAutoCompleteTextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}