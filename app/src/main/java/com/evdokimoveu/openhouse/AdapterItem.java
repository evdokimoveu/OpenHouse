package com.evdokimoveu.openhouse;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AdapterItem extends BaseAdapter {

    private List<Realtor> realtors;
    LayoutInflater layoutInflater;
    private SQLiteDatabase sqLiteDatabase;
    private DBRealtors dbRealtors;

    public AdapterItem(List<Realtor> items, Context context) {
        this.realtors = items;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dbRealtors = new DBRealtors(context, DBRealtors.DATA_BASE_NAME, null, 1);
    }

    @Override
    public int getCount() {
        return realtors.size();
    }

    @Override
    public Object getItem(int position) {
        return realtors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        public ImageView imageView;
        public TextView textName;
        public TextView textPhone;
        public TextView textMail;
        public TextView textDate;
        public SwitchCompat compat;
        public CheckBox checkBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView)view.findViewById(R.id.photoRealtor);
            holder.textName = (TextView)view.findViewById(R.id.nameRealtor);
            holder.textPhone = (TextView)view.findViewById(R.id.phoneRealtor);
            holder.textMail = (TextView)view.findViewById(R.id.emailRealtor);
            holder.textDate = (TextView)view.findViewById(R.id.dateRealtor);
            holder.checkBox = (CheckBox)view.findViewById(R.id.sendMailToRealtor);
            holder.compat = (SwitchCompat)view.findViewById(R.id.isWorkWithRealtor);

            holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Integer getPosition = (Integer) buttonView.getTag();
                    realtors.get(getPosition).setSelected(buttonView.isChecked());
                }
            });

            holder.compat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Integer getPosition = (Integer) buttonView.getTag();
                    ContentValues content = new ContentValues();
                    String switchCompat;
                    if(isChecked){
                        switchCompat = "YES";
                    }
                    else{
                        switchCompat = "NO";
                    }
                    realtors.get(getPosition).getId();
                    sqLiteDatabase = dbRealtors.getWritableDatabase();
                    content.put(DBRealtors.DB_SWITCH, switchCompat);
                    Integer i = realtors.get(getPosition).getId();
                    int ii =  sqLiteDatabase.update(DBRealtors.TABLE_REALTOR, content, "id="+i, null);
                    sqLiteDatabase.close();
                    realtors.get(getPosition).setSwitchId(switchCompat);
                }
            });

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)view.getTag();
        }
        holder.checkBox.setTag(position);
        holder.compat.setTag(position);

        holder.textName.setText(realtors.get(position).getName());
        holder.textPhone.setText(realtors.get(position).getPhone());
        holder.textMail.setText(realtors.get(position).getEmail());
        holder.textDate.setText(realtors.get(position).getDate());
        holder.compat.setChecked(convertStringToBoolean(realtors.get(position).getSwitchId()));
        holder.imageView.setImageResource(realtors.get(position).getImageId());
        holder.checkBox.setChecked(realtors.get(position).isSelected());


        return view;
    }

    /**
     * @return Set<String> of emails
     */
    public Set<String> getEmails() {
        Set<String>emails = new HashSet<>();
        for (Realtor realtor : realtors){
            if (realtor.isSelected()){
                emails.add(realtor.getEmail());
            }
        }
        return emails;
    }

    /**
     * Convert DB value to boolean for SwitchCompat
     * @param str YES or NO
     * @return
     */
    private boolean convertStringToBoolean(String str){
        if(str.equals("YES")){
            return true;
        }
        else{
            return false;
        }
    }
}
