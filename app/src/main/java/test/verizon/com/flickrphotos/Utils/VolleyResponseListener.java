package test.verizon.com.flickrphotos.Utils;

import android.graphics.Bitmap;

import java.util.List;

import test.verizon.com.flickrphotos.Data.PhotoObject;

public interface VolleyResponseListener {
    void onError(String errorMsg);
    void onResponse(List<PhotoObject> photos);
    void onImageReceived(Bitmap image);
}
