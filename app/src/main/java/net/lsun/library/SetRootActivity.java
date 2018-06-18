package net.lsun.library;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetRootActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_root);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        final EditText setRootUser = findViewById(R.id.set_root_user);
        final EditText setRootPwd = findViewById(R.id.set_root_pwd);
        Button setRootSave = findViewById(R.id.set_root_save);

        SharedPreferences prefs = getSharedPreferences("Prefs",MODE_PRIVATE);
        final SharedPreferences.Editor prefsEdit = prefs.edit();

        setRootSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = setRootUser.getText().toString();
                String pwd = setRootPwd.getText().toString();
                //判断用户是否输入了注册信息
                if (user.equals("") && pwd.equals("")) {
                    Toast("请输入用户名和密码！");
                } else {
                    if (user.equals("")) {
                        Toast("你还没有填写用户名！");
                    } else if (pwd.equals("")) {
                        Toast("你还没有填写密码！");
                    } else {
                        prefsEdit.putString("RootUser", user);
                        prefsEdit.putString("RootPwd", pwd);
                        prefsEdit.putString("setRoot", "1");
                        prefsEdit.apply();
                        Toast("系统管理员注册成功");
                        login();
                    }
                }
            }
        });

    }
    private void Toast(String tips) {
        Toast.makeText(SetRootActivity.this, tips, Toast.LENGTH_SHORT).show();
    }


    //跳到登陆页
    private void login() {
        Intent intent = new Intent(SetRootActivity.this, LoginActivity.class);
        startActivity(intent);
        SetRootActivity.this.finish();
    }
}
