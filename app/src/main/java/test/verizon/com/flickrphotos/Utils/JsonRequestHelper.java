package test.verizon.com.flickrphotos.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import test.verizon.com.flickrphotos.Data.PhotoObject;

public class JsonRequestHelper {

    private String mUrl;
    private int mMethod;
    private final String API_KEY = "698e1302856e60e61b87e5dd9d14f458";
    private String TOTAL_PHOTOS = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=";
    private String SINGLE_PHOTO_URL = "https://farm";
    private Context mContext;
    private VolleyResponseListener mListener ;
    private final String TAG = JsonRequestHelper.class.getCanonicalName();

    public JsonRequestHelper(Context context) {
        mContext = context;
    }

   public void setMethod(int method){
       mMethod = method;
   }
    public void makePhotosUrl(String text, int safeSearch){
        mUrl = TOTAL_PHOTOS + API_KEY + "&text=" + text +"&safe_search="+safeSearch + "&format=json&nojsoncallback=1";
    }

    public void makeSinglePhotoUrl(int farmId, String serverId, String id, String secret, String format, String size){
        mUrl = SINGLE_PHOTO_URL + farmId + ".staticflickr.com/" + serverId +"/" + id +"_" + secret + "_"+ size + "." + format;
    }

    public void makeJsonObjectRequest(final VolleyResponseListener responseCallback){

        mListener = responseCallback;
        if(mUrl == null || mListener == null){
            responseCallback.onError("The URL or callback is not set! Please set the appropriate url for sending request");
            return;
        }
        Log.d(TAG, "The url being called is : " + mUrl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (mMethod, mUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(mListener != null) {
                            Log.d(TAG, "Response received is : " +response);
                            List<PhotoObject> photos = parseAllPhotos(response);
                            if(photos.size() == 0) {
                                mListener.onError("Invalid input");
                                return;
                            }
                            mListener.onResponse(photos);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mListener != null) {
                            mListener.onError(error.getMessage());
                        }
                    }
                });

        VolleyHelper.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
    }

    public List<PhotoObject> parseAllPhotos(JSONObject response){

        List<PhotoObject> allPhotos = new ArrayList<>();
        try{

            JSONObject wholeJson = response.getJSONObject("photos");
            if(wholeJson != null) {
                JSONArray pics = wholeJson.getJSONArray("photo");

                for (int i = 0; i < pics.length(); i++) {
                    JSONObject photo = pics.getJSONObject(i);
                    String title = photo.getString("title");
                    String id = photo.getString("id");
                    String owner = photo.getString("owner");
                    String server = photo.getString("server");
                    String secret = photo.getString("secret");
                    int farm = photo.getInt("farm");
                    int isPublic = photo.getInt("ispublic");
                    int isFriend = photo.getInt("isfriend");
                    int isFamily = photo.getInt("isfamily");

                    PhotoObject dummyPhoto = new PhotoObject(id, owner, server, farm, title, isPublic, isFriend, isFamily, secret);

                    allPhotos.add(dummyPhoto);

                    setMethod(Request.Method.GET);
                    makeSinglePhotoUrl(farm, server, id, secret,"jpg", "s");
                    makeImageRequest(dummyPhoto);
                }
            }
        } catch (JSONException e){
            Log.e(TAG,"The json exception occured is as follows: " + e.getMessage());
        }
        return allPhotos;
    }


    public void makeImageRequestWithSize(final VolleyResponseListener listener){
        if(mUrl == null){
            return;
        }
        mListener = listener;
        Log.d(TAG, "The url being called is : " + mUrl);
        ImageRequest imageRequest = new ImageRequest(mUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        if(mListener != null){
                            mListener.onImageReceived(response);
                        }
                    }
                },0, 0,null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        VolleyHelper.getInstance(mContext).addToRequestQueue(imageRequest);
    }

    private void makeImageRequest(final PhotoObject photo){
        if(mUrl == null){
            return;
        }
        Log.d(TAG, "The url being called is : " + mUrl);
        ImageRequest imageRequest = new ImageRequest(mUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        photo.setImage(response);
                        if(mListener != null){
                            mListener.onImageReceived(response);
                        }
                    }
                },0, 0,null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        VolleyHelper.getInstance(mContext).addToRequestQueue(imageRequest);
    }

}
