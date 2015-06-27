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
 * Created by Stefano Cappa on 27/06/15.
 */

package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefano Cappa on 22/06/15.
 */
public class CrimeList {

    private final List<Crime> crimeList;

    public List<Crime> getCrimeList() {
        return crimeList;
    }

    private static final CrimeList instance = new CrimeList();

    /**
     * Method to get the instance of this class.
     *
     * @return instance of this class.
     */
    public static CrimeList getInstance() {
        return instance;
    }

    /**
     * Private constructor, because is a singleton class.
     */
    private CrimeList() {
        crimeList = new ArrayList<>();
    }
}