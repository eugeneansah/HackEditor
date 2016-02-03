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
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

public class NoteListFragment extends Fragment {

    private FloatingActionButton mFab;
    private View mRootView;
    private List<Note> mNotes;
    private RecyclerView mRecyclerView;
    private NoteListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView tvDefault;

    public NoteListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and hold the reference
        //in mRootView
        mRootView = inflater.inflate(R.layout.fragment_note_list, container, false);

        /*mFab = (FloatingActionButton) mRootView.findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NoteEditorActivity.class));
            }
        });*/

        tvDefault = (TextView) mRootView.findViewById(R.id.tv_default);
        tvDefault.setVisibility(View.GONE);

        setupList();
        return mRootView;
    }

    private void setupList() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.note_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
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
        mAdapter = new NoteListAdapter(mNotes, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }


}
