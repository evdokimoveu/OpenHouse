package com.evdokimoveu.openhouse;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.Principal;
import java.util.List;

/**
 * Created by Администратор on 16.02.16.
 */
public class Emails implements Parcelable {

    private List<String> emails;

    public Emails(List<String> emails) {
        this.emails = emails;
    }

    protected Emails(Parcel in) {
        emails = in.createStringArrayList();
    }

    public static final Creator<Emails> CREATOR = new Creator<Emails>() {
        @Override
        public Emails createFromParcel(Parcel in) {
            return new Emails(in);
        }

        @Override
        public Emails[] newArray(int size) {
            return new Emails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(emails);
    }

    public List<String> getEmails() {
        return emails;
    }
}
