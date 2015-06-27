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

package com.bignerdranch.android.criminalintent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

/**
 * Created by Stefano Cappa on 27/06/15.
 */
public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.CrimeHolder> {

    private final MultiSelector mMultiSelector;
    private final OnClickInterface clickListener;

    public CrimeAdapter (MultiSelector mMultiSelector, OnClickInterface clickListener ) {
        this.mMultiSelector = mMultiSelector;
        this.clickListener = clickListener;
    }

    public interface OnClickInterface {
        void longClickOnItem(CrimeAdapter.CrimeHolder crimeHolder);
        void clickOnItem(CrimeAdapter.CrimeHolder crimeHolder);
    }

    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_crime, parent, false);
        return new CrimeHolder(view);
    }

    @Override
    public void onBindViewHolder(CrimeHolder holder, int pos) {
        Crime crime = CrimeList.getInstance().getCrimeList().get(pos);
        holder.bindCrime(crime);
        Log.d("CrimeAdapter", "binding crime" + crime + "at position" + pos);
    }

    @Override
    public int getItemCount() {
        return CrimeList.getInstance().getCrimeList().size();
    }



    public class CrimeHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView mDateTextView;
        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView, mMultiSelector);

            mDateTextView = (TextView) itemView.findViewById(R.id.crime_list_item_dateTextView);
            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mDateTextView.setText(crime.getDate().toString());
        }

        public Crime getCrime() {
            return mCrime;
        }

        @Override
        public void onClick(View v) {
            clickListener.clickOnItem(this);
        }


        @Override
        public boolean onLongClick(View v) {
            clickListener.longClickOnItem(this);
            return true;
        }


    }
}
