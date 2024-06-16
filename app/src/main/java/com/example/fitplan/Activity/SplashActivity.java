package com.example.fitplan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitplan.R;
import com.example.fitplan.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    Animation topAnim, bottomAnim;
    private static int SPLASH_SCREEN = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        ImageView imgViewLogo = findViewById(R.id.imgViewLogo);
        TextView txtViewAppName = findViewById(R.id.txtViewAppName);
        TextView txtViewTagLine = findViewById(R.id.txtViewTagLine);

        imgViewLogo.setAnimation(topAnim);
        txtViewAppName.setAnimation(bottomAnim);
        txtViewTagLine.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(imgViewLogo, "logo_image");
                pairs[1] = new Pair<View, String>(txtViewAppName, "app_name");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);
    }
}