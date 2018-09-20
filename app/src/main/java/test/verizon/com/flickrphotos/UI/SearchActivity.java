package test.verizon.com.flickrphotos.UI;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.List;

import test.verizon.com.flickrphotos.Data.PhotoObject;
import test.verizon.com.flickrphotos.R;
import test.verizon.com.flickrphotos.UI.Design.ItemDecorator;
import test.verizon.com.flickrphotos.Utils.JsonRequestHelper;
import test.verizon.com.flickrphotos.Utils.VolleyResponseListener;

import android.databinding.DataBindingUtil;
import test.verizon.com.flickrphotos.databinding.SearchMainBinding;

public class SearchActivity extends AppCompatActivity{
    private PageLoadAdapter mAdapter;
    private final int safeSearch = 1;
    private final String TAG = SearchActivity.class.getName();
    List<PhotoObject> mPhotos = new ArrayList<>();
    SearchMainBinding binding;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        binding = DataBindingUtil.setContentView(this, R.layout.search_main);

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAdapter != null) {
                    mAdapter.updateData(true);
                }
                if(TextUtils.isEmpty(binding.searchEditText.getText())){
                   showAlertDialog();
                   return;
                }


                JsonRequestHelper sendRequest = new JsonRequestHelper(getApplicationContext());
                sendRequest.setMethod(Request.Method.GET);
                sendRequest.makePhotosUrl(binding.searchEditText.getText().toString().trim(), safeSearch);
                sendRequest.makeJsonObjectRequest(responseListener);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.sampleRecyler.setLayoutManager(mLayoutManager);
        binding.sampleRecyler.setItemAnimator(new DefaultItemAnimator());
        binding.sampleRecyler.addItemDecoration(new ItemDecorator(this , DividerItemDecoration.VERTICAL, 36));
        binding.searchBtn.setBackgroundDrawable(null);

    }

    VolleyResponseListener responseListener = new VolleyResponseListener() {
        @Override
        public void onError(String errorMsg) {
            showAlertDialog();
        }

        @Override
        public void onResponse(List<PhotoObject> response) {

            binding.sampleRecyler.setVisibility(View.VISIBLE);
            mPhotos = response;

            mAdapter = new PageLoadAdapter(getApplicationContext(), mPhotos);
            binding.sampleRecyler.setAdapter(mAdapter);
        }

        @Override
        public void onImageReceived(Bitmap image) {
            mAdapter.updateData(false);
        }
    };

    private void showAlertDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(SearchActivity.this).create();
        alertDialog.setTitle("Wrong Input");
        alertDialog.setMessage("Please enter Correct Inputs");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
