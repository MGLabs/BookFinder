package com.mglabs.whowroteit_mio;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText mSearchEditText;
    private TextView mTitle;
    private TextView mAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = findViewById(R.id.title_txt);
        mAuthor = findViewById(R.id.author_txt);
        mSearchEditText = findViewById(R.id.search_editText);
    }

    public void searchBooks(View view) {
        // Get the search string from the input field
        String queryString = mSearchEditText.getText().toString();

        //new FetchBookTask(mTitle, mAuthor).execute(queryString);
        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", queryString);
        getSupportLoaderManager().restartLoader(0, queryBundle, this);
    }


    //LOADER
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String queryString = "";
        if (bundle == null) {
            queryString = bundle.getString("queryString");
        }
        return new FetchBookTaskLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            //Initialize the variables used for the parsing loop
            int i = 0;
            String title = null;
            String authors = null;

            //Iterate through the itemsArray array, checking each book for
            // title and author information. With each loop, test to see if both an
            // author and a title are found, and if so, exit the loop
            while (i < itemsArray.length() && (title == null && authors == null)) {
                //Get the current item information
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Move to the next item. The loop ends at the first match in the response.
                i++;
            }
            //If both are found, display the results
            if (title != null && authors != null) {
                mTitle.setText(title);
                mAuthor.setText(authors);
            } else {
                mTitle.setText(R.string.no_results);
                mAuthor.setText("");
            }

        } catch (JSONException e) {
            // If onPostExecute does not receive a proper JSON string, print the error to the log.
            // Update the UI to show failed results.
            mTitle.setText(R.string.no_results);
            mAuthor.setText("");
            e.printStackTrace();
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
