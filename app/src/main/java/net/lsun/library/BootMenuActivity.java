package net.lsun.library;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class BootMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        SharedPreferences prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        String bootKey = prefs.getString("bootKey", "");
        prefsEdit.apply();

        Toast.makeText(this, "bootKey="+bootKey, Toast.LENGTH_SHORT).show();

        switch (bootKey) {
            case "":
                setRoot();
                break;
            case "login":
                login();
                break;
            case "root":
                Root();
                break;
            case "admin":
                Admin();
                break;
        }
    }

    //跳到注册系统管理员
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

    private void Root() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        BootMenuActivity.this.finish();
    }

    private void Admin() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        BootMenuActivity.this.finish();
    }
}
