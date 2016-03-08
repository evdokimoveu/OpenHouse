package com.evdokimoveu.openhouse;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddRealtor extends AppCompatActivity {


    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private SwitchCompat sw;

    private DBRealtors dbRealtors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_realtor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        dbRealtors = new DBRealtors(this, DBRealtors.DATA_BASE_NAME, null, 1);

        nameEditText = (EditText)findViewById(R.id.nameEdit);
        phoneEditText = (EditText)findViewById(R.id.phoneEdit);
        emailEditText = (EditText)findViewById(R.id.emailEdit);
        sw = (SwitchCompat)findViewById(R.id.switchQ);

    }

    public void saveRealtor(View view){
        SQLiteDatabase sqLiteDatabase = dbRealtors.getWritableDatabase();
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String switchText;
        if(sw.isChecked()){
            switchText = "YES";
        }
        else{
            switchText = "NO";
        }

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy, hh:mm", Locale.getDefault());
        String currentDate = format.format(new Date());

        if (!name.equals("") && !phone.equals("") && !email.equals("")){
            ContentValues content = new ContentValues();
            content.put(DBRealtors.DB_NAME_FIELD, name);
            content.put(DBRealtors.DB_PHONE_FIELD, phone);
            content.put(DBRealtors.DB_EMAIL_FIELD, email);
            content.put(DBRealtors.DB_SWITCH, switchText);
            content.put(DBRealtors.DB_DATE, currentDate);
            sqLiteDatabase.insert(DBRealtors.TABLE_REALTOR, null, content);

            nameEditText.setText(null);
            phoneEditText.setText(null);
            emailEditText.setText(null);

        } else {
            Toast.makeText(AddRealtor.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabase.close();
    }

    public void backIoMain(View view){
        finish();
    }


}
