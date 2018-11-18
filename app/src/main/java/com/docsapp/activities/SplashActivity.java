package com.docsapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.docsapp.R;
import com.ks.myutils.utils.DebugUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRootLayout = findViewById(R.id.root_layout);

        startApp();
    }

    private void startApp() {
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openMainActivity();
                    finish();
                }
            }, 1500);
        } catch (Exception ex) {
            DebugUtils.log(ex, getLocalClassName(), "init in Splash");
            ex.printStackTrace();
        }
    }


}
