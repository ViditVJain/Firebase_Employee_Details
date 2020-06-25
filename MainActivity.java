package com.example.example4;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity
{
    private ListView myList;
    private FloatingActionButton fabutton;
    DatabaseHelper db;
    TextView t1,t2,t13;
    String s1,s2,s3,s4,s11,s12,s13,s14;
    EditText e11,e12,e14;
    List<Employee> dataList;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(MainActivity.this);

        myList = (ListView) findViewById(R.id.MyList);
        myList.setItemsCanFocus(true);

        readDataFromDatabase();
        myAdapter = new MyAdapter(MainActivity.this,dataList);
        myList.setAdapter(myAdapter);

        fabutton = (FloatingActionButton) findViewById(R.id.fb);
        fabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog dialog1 = new Dialog(MainActivity.this);
                dialog1.setContentView(R.layout.customdialog);

                Button dialogButton1 = (Button) dialog1.findViewById(R.id.submit);
                dialogButton1.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        s1 = ((EditText)dialog1.findViewById(R.id.forname)).getText().toString();
                        s2 = ((EditText)dialog1.findViewById(R.id.foraddress)).getText().toString();
                        s3 = ((EditText)dialog1.findViewById(R.id.foremail)).getText().toString();
                        s4 = ((EditText)dialog1.findViewById(R.id.fornumber)).getText().toString();
                        if(s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Boolean chkemail = db.chkemail(s3);
                            if(chkemail==true)
                            {
                                Boolean insert = db.insert(s1,s2,s3,s4);
                                if(insert==true)
                                {
                                    Toast.makeText(getApplicationContext(), "Registration Successful" , Toast.LENGTH_SHORT).show();
                                    readDataFromDatabase();
                                    myAdapter = new MyAdapter(MainActivity.this,dataList);
                                    myList.setAdapter(myAdapter);
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Email Already Exists" , Toast.LENGTH_SHORT).show();
                            }
                            dialog1.dismiss();
                        }
                    }
                });
                dialog1.show();
            }
        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog2 = new Dialog(MainActivity.this);
                dialog2.setContentView(R.layout.listcustomdialog);

                Button dialogButton2 = (Button) dialog2.findViewById(R.id.save);

                e11 = (EditText) dialog2.findViewById(R.id.fornames);
                e11.setText(dataList.get(position).getName());
                e12 = (EditText) dialog2.findViewById(R.id.foraddresses);
                e12.setText(dataList.get(position).getAddress());
                t13 = (TextView) dialog2.findViewById(R.id.foremails);
                t13.setText(dataList.get(position).getEmail());
                e14 = (EditText) dialog2.findViewById(R.id.fornumbers);
                e14.setText(dataList.get(position).getPnumber());
                dialogButton2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        s11 = ((EditText)dialog2.findViewById(R.id.fornames)).getText().toString();
                        s12 = ((EditText)dialog2.findViewById(R.id.foraddresses)).getText().toString();
                        s13 = ((TextView)dialog2.findViewById(R.id.foremails)).getText().toString();
                        s14 = ((EditText)dialog2.findViewById(R.id.fornumbers)).getText().toString();

                        dataList.get(position).setName(s11);
                        dataList.get(position).setAddress(s12);

                        db.updateEmployee(s11,s12,s13,s14);
                        s1=s11;
                        s2=s12;
                        s3=s13;
                        s4=s14;

                        dialog2.dismiss();
                    }
                });
                dialog2.show();
            }
        });

        myList.setLongClickable(true);
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position , long id)
            {
                final Dialog dialog3 = new Dialog(MainActivity.this);
                dialog3.setContentView(R.layout.customdialogdelete);
                Button Deletion = (Button) dialog3.findViewById(R.id.delete);
                Deletion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s13=dataList.get(position).getEmail();

                        Boolean delete = db.deleteEmployee(s13);
                        if(delete==true)
                        {
                            Toast.makeText(getApplicationContext(), "Deletion Successful" , Toast.LENGTH_SHORT).show();
                            dataList.remove(position);
                            myAdapter.notifyDataSetChanged();
                        }
                        dialog3.dismiss();
                    }
                });
                dialog3.show();
                return  true;
            }
        });
    }

    public void readDataFromDatabase(){
        dataList = db.getAllEmployees();
    }

    public class MyAdapter extends BaseAdapter{
        Context mContext;
        List<Employee> myDataList;
        LayoutInflater inflter;

        public MyAdapter(Context mContext, List<Employee> myDataList) {
            this.mContext = mContext;
            this.myDataList = myDataList;
            inflter = (LayoutInflater.from(mContext));
        }

        @Override
        public int getCount() { return myDataList.size(); }

        @Override
        public Object getItem(int position) { return myDataList.get(position); }

        @Override
        public long getItemId(int position) { return 0; }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = inflter.inflate(R.layout.item, null);
            t1 = (TextView) view.findViewById(R.id.textView1);
            t2 = (TextView) view.findViewById(R.id.textView2);
            t1.setText(myDataList.get(position).getName());
            t2.setText(myDataList.get(position).getAddress());

            return view;
        }
    }

}
