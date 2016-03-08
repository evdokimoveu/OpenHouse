package com.evdokimoveu.openhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class SendMail extends AppCompatActivity {

    private EditText subject;
    private EditText emailText;
    private List<String> emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        Emails emails = getIntent().getParcelableExtra(Emails.class.getCanonicalName());
        emailAddress = emails.getEmails();
        subject = (EditText) findViewById(R.id.themeEditText);
        emailText = (EditText) findViewById(R.id.messageEitText);
    }

    public void sendMail(View view){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, emailAddress.toArray(new String[emailAddress.size()]));
        email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        email.putExtra(Intent.EXTRA_TEXT, emailText.getText().toString());
        try {
            startActivity(Intent.createChooser(email, "Send mail"));
        } catch (Exception ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void backIoMain(View view){
        finish();
    }
}
