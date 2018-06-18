package net.lsun.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        EditText user = findViewById(R.id.login_edit_userName);
        EditText pwd = findViewById(R.id.login_edit_pwd);
        CheckBox rmPwd = findViewById(R.id.login_chk_rm_pwd);
        Button login = findViewById(R.id.login_btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Root();
            }
        });
    }

    private void Root() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void Admin() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}
