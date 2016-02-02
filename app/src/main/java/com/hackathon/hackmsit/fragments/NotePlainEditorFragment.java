package com.hackathon.hackmsit.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.hackathon.hackmsit.R;
import com.hackathon.hackmsit.activities.MainActivity;
import com.hackathon.hackmsit.data.NoteManager;
import com.hackathon.hackmsit.models.Note;

public class NotePlainEditorFragment extends Fragment {

    private View mRootView;
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private Note mCurrentNote = null;
    Bundle args;

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

        mRootView = inflater.inflate(R.layout.fragment_note_plain_editor, container, false);
        mTitleEditText = (EditText) mRootView.findViewById(R.id.edit_text_title);
        mContentEditText = (EditText) mRootView.findViewById(R.id.edit_text_note);

        return mRootView;
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
                    makeToast("Cannot delete note that has not been saved");
                }
                break;
            case R.id.action_save:
                //save note
                if (saveNote()) {
                    makeToast(mCurrentNote != null ? "Note updated" : "Note saved");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
        String title = mTitleEditText.getText().toString();
        if (TextUtils.isEmpty(title)) {
            mTitleEditText.setError("Title is required");
            return false;
        }
        String content = mContentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            mContentEditText.setError("Content is required");
            return false;
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
        alertDialog.setTitle("Delete " + titleOfNoteTobeDeleted + " ?");
        alertDialog.setMessage("Are you sure you want to delete the note " + titleOfNoteTobeDeleted + "?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NoteManager.newInstance(getActivity()).delete(mCurrentNote);
                makeToast(titleOfNoteTobeDeleted + " deleted");
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
