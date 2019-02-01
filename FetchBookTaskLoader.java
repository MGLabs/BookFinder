package com.mglabs.whowroteit_mio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.TextView;

/*
AsyncTaskLoader loads data in background and reassociates the background task with the activity
even after a configuration change so that the results are still displayed correctly.
 */
public class FetchBookTaskLoader extends AsyncTaskLoader<String> {

    private TextView mTitle;
    private TextView mAuthor;
    private String mQueryString;

    public FetchBookTaskLoader(@NonNull Context context, String queryString) {
        super(context);
        this.mQueryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
