/**
 * The MIT License (MIT)

 Copyright (c) 2015 Stefano Cappa

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

/**
 * Created by https://github.com/bignerdranch/recyclerview-multiselect
 * modified by Stefano Cappa on 27/06/15.
 */

package com.bignerdranch.android.criminalintent;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;

public class CrimeListFragment extends Fragment  implements
        CrimeAdapter.OnClickInterface {

    private static final String TAG = "crimeListFragment";
    private RecyclerView mRecyclerView;
    private MultiSelector mMultiSelector = new MultiSelector();

    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId()==  R.id.menu_item_delete_crime){
                    // Need to finish the action mode before doing the following,
                    // not after. No idea why, but it crashes.
                    actionMode.finish();

                    for (int i = CrimeList.getInstance().getCrimeList().size(); i >= 0; i--) {
                        if (mMultiSelector.isSelected(i, 0)) {
                            Crime crime = CrimeList.getInstance().getCrimeList().get(i);
                            CrimeList.getInstance().getCrimeList().remove(crime);
                            mRecyclerView.getAdapter().notifyItemRemoved(i);
                        }
                    }

                    mMultiSelector.clearSelections();
                    return true;

            }
            return false;
        }
    };

    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        mSubtitleVisible = false;
    }

    /**
     * Note: since the fragment is retained. the bundle passed in after state is restored is null.
     * THe only way to pass parcelable objects is through the activities onsavedInstanceState and appropiate startup lifecycle
     * However after having second thoughts, since the fragment is retained then all the states and instance variables are
     * retained as well. no need to make the selection states percelable therefore just check for the selectionstate
     * from the multiselector
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        if (mMultiSelector != null) {
            Bundle bundle = savedInstanceState;
            if (bundle != null) {
                mMultiSelector.restoreSelectionStates(bundle.getBundle(TAG));
            }

            if (mMultiSelector.isSelectable()) {
                if (mDeleteMode != null) {
                    mDeleteMode.setClearOnPrepare(false);
                    ((AppCompatActivity) getActivity()).startSupportActionMode(mDeleteMode);
                }

            }
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(TAG, mMultiSelector.saveSelectionStates());
        super.onSaveInstanceState(outState);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recyclerview, parent, false);

        if (mSubtitleVisible) {
            getActionBar().setSubtitle(R.string.subtitle);
        }

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new CrimeAdapter(mMultiSelector,this));

        return v;
    }

    private void selectCrime(Crime c) {
        Log.d(TAG, "Crime list item clicked");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==  R.id.menu_item_new_crime) {
            final Crime crime = new Crime();
            CrimeList.getInstance().getCrimeList().add(crime);

            mRecyclerView.getAdapter().notifyItemInserted(CrimeList.getInstance().getCrimeList().indexOf(crime));

            // NOTE: Left this code in for commentary. I believe this is what you would do
            // to wait until the new crime is added, then animate the selection of the new crime.
            // It does not work, though: the listener will be called immediately,
            // because no animations have been queued yet.
            mRecyclerView.getItemAnimator().isRunning(
                    new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
                    @Override
                    public void onAnimationsFinished() {
                        selectCrime(crime);
                    }
                });
            return true;
        } else if(item.getItemId()== R.id.menu_item_show_subtitle) {
                ActionBar actionBar = getActionBar();
                if (actionBar.getSubtitle() == null) {
                    actionBar.setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    actionBar.setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    protected ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void longClickOnItem(CrimeAdapter.CrimeHolder crimeHolder) {
        ((AppCompatActivity) getActivity()).startSupportActionMode(mDeleteMode);
        mMultiSelector.setSelected(crimeHolder, true);
    }

    @Override
    public void clickOnItem(CrimeAdapter.CrimeHolder crimeHolder) {
        if (!mMultiSelector.tapSelection(crimeHolder)) {
            selectCrime(crimeHolder.getCrime());
        }
    }
}

