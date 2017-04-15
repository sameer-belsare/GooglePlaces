package sameer.belsare.googleplaces.placedetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sameer.belsare on 15/4/17.
 */

public interface GetPhotosApi {
    @GET("/maps/api/place/nearbysearch/json")
    Call<GetPlacesResponse> getPlaceDetails(@Query("location") String location,
                                           @Query("radius") int radius,
                                           @Query("key") String key);
}
