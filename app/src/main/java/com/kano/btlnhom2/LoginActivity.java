package com.kano.btlnhom2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knhotel.DAO.QuanLyDAO;
import com.example.knhotel.DTO.QuanLy;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout til_username,til_password;
    TextInputEditText ed_username,ed_password;
    CheckBox chk_remember;
    Button btn_login;
    TextView tv_register;
    String strUser,strPass;
    int temp = 0;
    QuanLyDAO dao;

    List<QuanLy> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);
        til_username = findViewById(R.id.til_username_login);
        til_password = findViewById(R.id.til_password_login);
        ed_username = findViewById(R.id.ed_username_login);
        ed_password = findViewById(R.id.ed_pass_login);
        chk_remember = findViewById(R.id.chk_login_remember);
        dao = new QuanLyDAO(this);
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        ed_username.setText(preferences.getString("USERNAME",""));
        ed_password.setText(preferences.getString("PASSWORD",""));
        chk_remember.setChecked(preferences.getBoolean("REMEMBER",false));

        list = new ArrayList<>();
        list = dao.getAll();
        if (list.size() == 0){
            dao.insertadmin();
        }

        tv_register.setOnClickListener(v -> {
            Intent intent1 = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent1);
        });

        btn_login.setOnClickListener(v -> {
            checkLogin();
        });
    }

    public void checkLogin(){
        strUser = ed_username.getText().toString().trim();
        strPass = ed_password.getText().toString().trim();
        if (strUser.isEmpty()){
            til_username.setError("Tên đăng nhập không được để trống");
            temp++;
        }else{
            til_username.setError("");
        }
        if(strPass.isEmpty()){
            til_password.setError("Mât khẩu không được để trống");
            temp++;
        }else{
            til_password.setError("");
        }
        if (temp ==0){
            if (dao.checkLogin(strUser,strPass) > 0){
                til_username.setError("");
                til_password.setError("");
                Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser,strPass,chk_remember.isChecked());

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("user",strUser);
                intent.putExtra("pass",strPass);
                startActivity(intent);
                finish();
            }else{
                til_username.setError("Tên đăng nhập không đúng");
                til_password.setError("Mật khẩu không đúng");
            }
        }else {
            temp =0;
        }
    }

    public void rememberUser(String u,String p,boolean status){
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            editor.clear();
        }else{
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }
}