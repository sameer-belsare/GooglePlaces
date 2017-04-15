package sameer.belsare.googleplaces.placedetails;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sameer.belsare.googleplaces.R;

/**
 * Created by sameer.belsare on 15/4/17.
 */

public class PlaceDetailsPresenter implements PlaceDetailsContract.PlaceDetailsPresenter {

    private static final String BASE_URL = "https://maps.googleapis.com";
    private Fragment mView;
    private String mLatLng;
    private PhotoViewAdaptor mAdaptor;
    private ArrayList<String> mPhotoUris;

    @Override
    public void setViewAndData(PlaceDetailsContract.PlaceDetailsView view, String latLng) {
        mView = (Fragment) view;
        mLatLng = latLng;
        mPhotoUris = new ArrayList<>();
        mAdaptor = new PhotoViewAdaptor((PlaceDetailsFragment) mView, this, mPhotoUris);
    }

    public PhotoViewAdaptor getAdaptor() {
        return mAdaptor;
    }

    @Override
    public void loadPlacePhotos() {
        OkHttpClient.Builder okHttp3ClientBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp3ClientBuilder.build())
                .build();

        GetPhotosApi getPhotosApi = retrofit.create(GetPhotosApi.class);
        Call<GetPlacesResponse> call = getPhotosApi.getPlaceDetails(mLatLng, 500, /*"bank", "cruise", */mView.getString(R.string.api_key));
        call.enqueue(new Callback<GetPlacesResponse>() {
            @Override
            public void onResponse(Call<GetPlacesResponse> call, Response<GetPlacesResponse> response) {
                if (response != null) {
                    Log.d("PlaceDetailsPresenter", response.body().toString());
                    List<GetPlacesResponse.Result> results = response.body().getResults();
                    if (results != null) {
                        mPhotoUris.clear();
                        for (GetPlacesResponse.Result result : results) {
                            List<GetPlacesResponse.Photo> photos = result.getPhotos();
                            if (photos != null) {
                                for (GetPlacesResponse.Photo photo : photos) {
                                    String photoReference = photo.getPhotoReference();
                                    if (!TextUtils.isEmpty(photoReference)) {
                                        addItemToPhotoList("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference +
                                                "&key=" + mView.getString(R.string.api_key));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPlacesResponse> call, Throwable t) {
                Log.d("PlaceDetailsPresenter", t.getMessage());
            }
        });
    }

    private void addItemToPhotoList(final String photoUri) {
        mPhotoUris.add(photoUri);
        //saveImageToFile(photoUri);
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdaptor.notifyDataSetChanged();
            }
        });
    }

    public void saveImageToFile(final String photoUri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap theBitmap = null;
                try {
                    theBitmap = Glide.
                            with(mView.getActivity()).
                            load(photoUri).
                            asBitmap().
                            into(-1, -1).
                            get();
                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    String filename = System.currentTimeMillis() + ".png";
                    File file = new File(extStorageDirectory, filename);
                    if (file.exists()) {
                        file.delete();
                        file = new File(extStorageDirectory, filename);
                    }
                    OutputStream outStream = new FileOutputStream(file);
                    theBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(mView.getActivity(), mView.getString(R.string.photo_downloaded), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void handleItemClick(int position) {
        saveImageToFile(mPhotoUris.get(position));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
