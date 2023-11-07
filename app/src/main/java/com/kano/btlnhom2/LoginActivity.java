package com.kano.btlnhom2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kano.btlnhom2.DAO.QuanLyDAO;
import com.kano.btlnhom2.DTO.QuanLy;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText edt_UserName,edt_Password;
    CheckBox chk_remember;
    Button btn_login;
    TextView tv_register;
    String strUser,strPass;
    int temp = 0;
    QuanLyDAO dao;

    List<QuanLy> list;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);
        edt_Password = findViewById(R.id.edt_Password);
        edt_UserName = findViewById(R.id.edt_User_Name);
        chk_remember = findViewById(R.id.chk_login_remember);
        dao = new QuanLyDAO(this);
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edt_UserName.setText(preferences.getString("USERNAME",""));
        edt_Password.setText(preferences.getString("PASSWORD",""));
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
        strUser = edt_UserName.getText().toString().trim();
        strPass = edt_Password.getText().toString().trim();
        if (strUser.isEmpty()){
            edt_UserName.setError("Tên đăng nhập không được để trống");
            temp++;
        }else{
            edt_UserName.setError("");
        }
        if(strPass.isEmpty()){
            edt_Password.setError("Mât khẩu không được để trống");
            temp++;
        }else{
            edt_Password.setError("");
        }
        if (temp ==0){
            if (dao.checkLogin(strUser,strPass) > 0){
                edt_UserName.setError("");
                edt_Password.setError("");
                Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser,strPass,chk_remember.isChecked());

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("user",strUser);
                intent.putExtra("pass",strPass);
                startActivity(intent);
                finish();
            }else{
                edt_UserName.setError("Tên đăng nhập không đúng");
                edt_Password.setError("Mật khẩu không đúng");
            }
        }else {
            temp =0;
        }
    }

    @SuppressLint("ApplySharedPref")
    public void rememberUser(String u, String p, boolean status){
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