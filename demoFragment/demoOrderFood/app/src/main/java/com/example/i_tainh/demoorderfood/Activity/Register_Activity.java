package com.example.i_tainh.demoorderfood.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.i_tainh.demoorderfood.DAO.NhanVienDAO;
import com.example.i_tainh.demoorderfood.R;
import com.example.i_tainh.demoorderfood.entity.NhanVien;
import com.example.i_tainh.demoorderfood.FragmentApp.DatePickerFragment;

public class Register_Activity extends AppCompatActivity  implements View.OnFocusChangeListener {

    EditText txtUsername, txtPassword, txtDob, txtCmnd;
    Button btnOk, btnCancel;
    RadioGroup rdGender;
    String sex;
    NhanVienDAO nhanVienDAO;
    NhanVien nhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        wiget();


        txtDob.setOnFocusChangeListener(this);

    }

    public void wiget(){
        txtUsername  = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtCmnd = (EditText) findViewById(R.id.txtcmnd);
        txtDob = (EditText) findViewById(R.id.txtDob);
        rdGender =  (RadioGroup) findViewById(R.id.radio_sex);

    }




    public void onClickLoginHandel(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnOk:
                String userName = txtUsername.getText().toString();
                String pass = txtPassword.getText().toString();

                switch (rdGender.getCheckedRadioButtonId()){
                    case R.id.rd_male :
                        sex = "Nam";
                        break;
                    case R.id.rd_female:
                        sex = "Nữ";
                }

                String dob = txtDob.getText().toString();
                String cmnd = txtCmnd.getText().toString();

                if(userName.isEmpty() && "".equals(userName)){
                    Toast.makeText(Register_Activity.this,R.string.nhap_ten_dang_nhap,Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty() && "".equals(pass)){
                    Toast.makeText(Register_Activity.this,R.string.nhap_mk,Toast.LENGTH_SHORT).show();
                }
                else{
                    nhanVien = new NhanVien();
                    nhanVienDAO = new NhanVienDAO(this);
                    nhanVien.setTenDN(userName);
                    nhanVien.setCmnd(cmnd);
                    nhanVien.setMatKhau(pass);
                    nhanVien.setGioiTinh(sex);
                    nhanVien.setNgaySinh(dob);
                    nhanVienDAO.themNhanVien(nhanVien);


                }


                break;

            case R.id.btnCancle:
                finish();
                break;
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id  =  v.getId();
        switch (id){
            case R.id.txtDob:
                if(hasFocus){
                    DatePickerFragment  datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(), "Ngay sinh");
                }
                break;
        }
    }
}
