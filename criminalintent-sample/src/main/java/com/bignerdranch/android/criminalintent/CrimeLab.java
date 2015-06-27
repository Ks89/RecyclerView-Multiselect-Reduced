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

import android.content.Context;

import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

//    private CriminalIntentJSONSerializer mSerializer;

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
//        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
//
//        try {
//            mCrimes = mSerializer.loadCrimes();
//        } catch (Exception e) {
//            mCrimes = new ArrayList<Crime>();
//            Log.e(TAG, "Error loading crimes: ", e);
//        }
    }

    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : CrimeList.getInstance().getCrimeList()) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addCrime(Crime c) {
        CrimeList.getInstance().getCrimeList().add(c);
        saveCrimes();
    }

    public List<Crime> getCrimes() {
        return CrimeList.getInstance().getCrimeList();
    }

    public void deleteCrime(Crime c) {
        CrimeList.getInstance().getCrimeList().remove(c);
        saveCrimes();
    }

    public boolean saveCrimes() {
//        try {
//            mSerializer.saveCrimes(mCrimes);
//            Log.d(TAG, "crimes saved to file");
            return true;
//        } catch (Exception e) {
//            Log.e(TAG, "Error saving crimes: " + e);
//            return false;
//        }
    }
}

