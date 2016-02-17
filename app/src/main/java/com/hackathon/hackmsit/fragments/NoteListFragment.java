package com.hackathon.hackmsit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackathon.hackmsit.R;
import com.hackathon.hackmsit.activities.NotePlainEditorActivity;
import com.hackathon.hackmsit.adapter.NoteListAdapter;
import com.hackathon.hackmsit.data.NoteManager;
import com.hackathon.hackmsit.models.Note;

import java.util.List;

public class NoteListFragment extends Fragment {

    private View mRootView;
    private List<Note> mNotes;
    private TextView tvDefault;

    public NoteListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_note_list, container, false);
        tvDefault = (TextView) mRootView.findViewById(R.id.tv_default);
        tvDefault.setVisibility(View.GONE);
        setupList();
        return mRootView;
    }

    private void setupList() {
        RecyclerView mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.note_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        final GestureDetector mGestureDetector =
                new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildLayoutPosition(child);
                    Note selectedNote = mNotes.get(position);
                    Intent editorIntent = new Intent(getActivity(), NotePlainEditorActivity.class);
                    editorIntent.putExtra("id", selectedNote.getId());
                    editorIntent.putExtra("name", selectedNote.getTitle());
                    editorIntent.putExtra("ext", selectedNote.getExt());
                    startActivity(editorIntent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mNotes = NoteManager.newInstance(getActivity()).getAllNotes();
        if (mNotes.isEmpty()) {
            tvDefault.setVisibility(View.VISIBLE);
        }
        NoteListAdapter mAdapter = new NoteListAdapter(mNotes, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }


}
