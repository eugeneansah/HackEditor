package com.hackathon.hackmsit.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hackathon.hackmsit.R;
import com.hackathon.hackmsit.activities.MainActivity;
import com.hackathon.hackmsit.data.NoteManager;
import com.hackathon.hackmsit.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NotePlainEditorFragment extends Fragment {

    private EditText mTitleEditText, mContentEditText;
    private Note mCurrentNote = null;
    Bundle args;
    private TextInputLayout inputLayoutTitle, inputLayoutCode;
    private CoordinatorLayout mCoordinatorLayout;
    private String[] keys = {"{", "}", "(", ")", ";"};
    private int spacing;

    public NotePlainEditorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        args = getArguments();
        getCurrentNote();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_note_plain_editor, container, false);
        mTitleEditText = (EditText) mRootView.findViewById(R.id.edit_text_title);
        mContentEditText = (EditText) mRootView.findViewById(R.id.edit_text_note);
        inputLayoutTitle = (TextInputLayout) mRootView.findViewById(R.id.input_layout_title);
        inputLayoutCode = (TextInputLayout) mRootView.findViewById(R.id.input_layout_code);
        mCoordinatorLayout = (CoordinatorLayout) mRootView.findViewById(R.id.coordinatorLayout);
        spacing = 0;

        ViewPager viewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) mRootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int numTab = tab.getPosition();
                if (mContentEditText.hasFocus()) {
                    int cursorPos = mContentEditText.getSelectionStart();
                    String content = mContentEditText.getText().toString();
                    content = new StringBuffer(content).insert(cursorPos, keys[numTab]).toString();
                    mContentEditText.setText(content);
                    mContentEditText.setSelection(cursorPos + 1);
                } else {
                    String content = mContentEditText.getText().toString() + keys[numTab];
                    mContentEditText.setText(content);
                    mContentEditText.setSelection(content.length());
                }

                //Indentation method
                if (keys[numTab].equals("{")) {
                    spacing += 8;
                    Log.d("spacing", String.valueOf(spacing));
                } else if (keys[numTab].equals("}") && spacing >= 4) {
                    spacing -= 8;
                    Log.d("spacing", String.valueOf(spacing));
                    int cursorPos = mContentEditText.getSelectionStart();
                    String content = mContentEditText.getText().toString();
                    String spaces = new String(new char[spacing]).replace("\0", " ");
                    content = new StringBuffer(content).insert(cursorPos - 1, "\n" + spaces).toString();
                    mContentEditText.setText(content);
                    mContentEditText.setSelection(cursorPos + spacing + 1);
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

        mContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (before == 0 && count == 1 && s.charAt(start) == '\n') {
                    String spaces = new String(new char[spacing]).replace("\0", " ");
                    int cursorPos = mContentEditText.getSelectionStart();
                    String content = mContentEditText.getText().toString();
                    content = new StringBuffer(content).insert(cursorPos, spaces).toString();
                    mContentEditText.setText(content);
                    mContentEditText.setSelection(cursorPos + spacing);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return mRootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCurrentNote != null) {
            populateFields();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_note_edit_plain, menu);
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
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                if (saveNote()) {
                    makeSnackbar(mCurrentNote != null ? "Code updated" : "Code saved");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeSnackbar(String message) {
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static NotePlainEditorFragment newInstance(long id) {
        NotePlainEditorFragment fragment = new NotePlainEditorFragment();

        if (id > 0) {
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    private void getCurrentNote() {
        if (args != null && args.containsKey("id")) {
            long id = args.getLong("id", 0);
            if (id > 0) {
                mCurrentNote = NoteManager.newInstance(getActivity()).getNote(id);
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

        String content = mContentEditText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            mContentEditText.setError("Code is required");
            return false;
        } else {
            inputLayoutCode.setErrorEnabled(false);
        }
        if (mCurrentNote != null) {
            mCurrentNote.setContent(content);
            mCurrentNote.setTitle(title);
            NoteManager.newInstance(getActivity()).update(mCurrentNote);
        } else {
            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);
            NoteManager.newInstance(getActivity()).create(note);
        }
        return true;
    }

    private void populateFields() {
        mTitleEditText.setText(mCurrentNote.getTitle());
        mContentEditText.setText(mCurrentNote.getContent());
    }

    public void promptForDelete() {
        final String titleOfNoteTobeDeleted = mCurrentNote.getTitle();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Confirm Action");
        alertDialog.setMessage("Delete " + titleOfNoteTobeDeleted + "?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NoteManager.newInstance(getActivity()).delete(mCurrentNote);

                SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences("pref", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("deleted", "1");
                editor.apply();
                //makeSnackbar("Code deleted");
                //startActivity(new Intent(getActivity(), MainActivity.class));
                Intent intent = new Intent(getActivity(), MainActivity.class);
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
}
