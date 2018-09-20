package test.verizon.com.flickrphotos.UI;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.List;

import test.verizon.com.flickrphotos.Data.PhotoObject;
import test.verizon.com.flickrphotos.R;
import test.verizon.com.flickrphotos.UI.Design.CircleImageView;
import test.verizon.com.flickrphotos.Utils.Constants;
import test.verizon.com.flickrphotos.Utils.JsonRequestHelper;
import test.verizon.com.flickrphotos.Utils.VolleyResponseListener;

//import test.verizon.com.flickrphotos.databinding.ActivityImageBinding;


public class ImageActivity extends AppCompatActivity implements VolleyResponseListener{

    JsonRequestHelper helper;
  //  ActivityImageBinding mBinding;
    RelativeLayout mLayout;
    CircleImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mBinding = DataBindingUtil.*/setContentView(R.layout.activity_image);


        setContentView(R.layout.activity_image);
        helper = new JsonRequestHelper(this);
        mImageView = (CircleImageView) findViewById(R.id.image);
        mLayout = (RelativeLayout) findViewById(R.id.base);

        String id = getIntent().getStringExtra(Constants.ID);
        int farmId = getIntent().getIntExtra(Constants.FARM,-1);
        String serverId = getIntent().getStringExtra(Constants.SERVER);
        String secret = getIntent().getStringExtra(Constants.SECRET);
        String size = getIntent().getStringExtra(Constants.IMAGE_SIZE);
        String format = getIntent().getStringExtra(Constants.FORMAT);

        helper.makeSinglePhotoUrl(farmId, serverId, id, secret, format, size);
        helper.makeImageRequestWithSize(this);

    }

    @Override
    public void onImageReceived(Bitmap image) {
        Drawable dr = new BitmapDrawable(image);
        /*mBinding.base.*/mLayout.setBackground(dr);
       /* mBinding.image.*/mImageView.setImageBitmap(image);
    }

    @Override
    public void onResponse(List<PhotoObject> photos) {

    }

    @Override
    public void onError(String errorMsg) {

    }
}
