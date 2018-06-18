package net.lsun.library;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

public class BootMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        SharedPreferences prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.apply();
        String setRoot = prefs.getString("setRoot", "");

        if (setRoot.equals("")) {
            prefsEdit.apply();
            setRoot();
        } else {
            login();
        }

    }

    //跳到设置系统管理员
    private void setRoot() {
        Intent intent = new Intent(this, SetRootActivity.class);
        startActivity(intent);
        BootMenuActivity.this.finish();
    }


    //跳到登陆页
    private void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        BootMenuActivity.this.finish();
    }
}
