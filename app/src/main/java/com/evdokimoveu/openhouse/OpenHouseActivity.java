package com.evdokimoveu.openhouse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class OpenHouseActivity extends AppCompatActivity {

    private List<Realtor> realtors;
    private AdapterItem adapterItem;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_house);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        calendar = Calendar.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        addRealtorsFromDB();
        createListView(realtors);
    }

    public void addRealtorClick(View view){
        Intent intent = new Intent(OpenHouseActivity.this, AddRealtor.class);
        startActivity(intent);
    }

    /**
     * Button click listener "Send Mail"
     * @param view
     */
    public void sendEmailClick(View view){
        List<String> emailAddress = new ArrayList<>(adapterItem.getEmails());
        if(emailAddress.size() > 0){
            Emails emails = new Emails(emailAddress);
            Intent intent = new Intent(OpenHouseActivity.this, SendMail.class);
            intent.putExtra(Emails.class.getCanonicalName(), emails);
            startActivity(intent);
        }
        else{
            Toast.makeText(OpenHouseActivity.this, "No realtor selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void addRealtorsFromDB(){
        realtors = new ArrayList<>();
        DBRealtors dbRealtors = new DBRealtors(this, DBRealtors.DATA_BASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = dbRealtors.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.query(DBRealtors.TABLE_REALTOR, new String[]{
                DBRealtors.DB_NAME_FIELD,
                DBRealtors.DB_PHONE_FIELD,
                DBRealtors.DB_EMAIL_FIELD,
                DBRealtors.DB_SWITCH,
                DBRealtors.DB_DATE,
                DBRealtors.DB_ID,
        }, null, null, null, null, null);

        while(cursor.moveToNext()){
            //Realtor fields
            String name = cursor.getString(cursor.getColumnIndex(DBRealtors.DB_NAME_FIELD));
            String phone = cursor.getString(cursor.getColumnIndex(DBRealtors.DB_PHONE_FIELD));
            String email = cursor.getString(cursor.getColumnIndex(DBRealtors.DB_EMAIL_FIELD));
            String sw = cursor.getString(cursor.getColumnIndex(DBRealtors.DB_SWITCH));
            String date = cursor.getString(cursor.getColumnIndex(DBRealtors.DB_DATE));
            int id = cursor.getInt(cursor.getColumnIndex(DBRealtors.DB_ID));

            Realtor realtor = new Realtor(R.drawable.realtor, name, sw, false, email, phone, date, id);
            realtors.add(realtor);
        }
        cursor.close();
        sqLiteDatabase.close();
    }

    public void showDatePickerDialog(View view){
        createListView(realtors);
        new DatePickerDialog(OpenHouseActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String newDate = format.format(c.getTime());
            List<Realtor> filteredRealtors = new ArrayList<>();
            for(Realtor r: realtors){
                if(r.getDate().contains(newDate)){
                    Realtor realtor = new Realtor(
                            r.getImageId(),
                            r.getName(),
                            r.getSwitchId(),
                            r.isSelected(),
                            r.getEmail(),
                            r.getPhone(),
                            r.getDate(),
                            r.getId());
                    filteredRealtors.add(realtor);
                }
            }
            createListView(filteredRealtors);
        }
    };

    private void createListView(List<Realtor> realtorsList){
        adapterItem = new AdapterItem(realtorsList, this);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapterItem);
    }
}
