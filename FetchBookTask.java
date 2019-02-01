package com.mglabs.whowroteit_mio;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class FetchBookTask extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mTitle;
    private WeakReference<TextView> mAuthor;

    //Constructor
    public FetchBookTask(TextView mTitle, TextView mAuthor) {
        this.mTitle = new WeakReference<>(mTitle);
        this.mAuthor = new WeakReference<>(mAuthor);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
