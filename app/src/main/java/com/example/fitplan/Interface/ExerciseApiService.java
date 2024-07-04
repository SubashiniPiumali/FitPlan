package com.example.fitplan.Interface;

import com.example.fitplan.Model.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ExerciseApiService {
    @GET("exercises")
    Call<List<Exercise>> getExercises(
            @Header("x-rapidapi-host") String host,
            @Header("x-rapidapi-key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
