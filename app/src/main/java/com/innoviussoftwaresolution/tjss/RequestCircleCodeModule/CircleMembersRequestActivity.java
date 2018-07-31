package com.innoviussoftwaresolution.tjss.RequestCircleCodeModule;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bugsnag.android.Bugsnag;
import com.google.gson.GsonBuilder;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Callback;
import retrofit2.Response;

public class CircleMembersRequestActivity extends AppCompatActivity implements PermissionsDetailListDialog.PermissionsDialogCallback {
    List<String> isdList, phoneNoList;
    private RecyclerView rvContactList;
    private ContactsModel model;
    private RequestCodeAdapter adapter;
    private StringBuffer phoneNumber = new StringBuffer();
    private CircleDetailsModel detailsModel;
    private ArrayList<CircleDetailsModel> circleDetailsList;
    private static final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_members_request);
        Bugsnag.init(this);
        rvContactList = (RecyclerView) findViewById(R.id.rvContactList);
        isdList = new ArrayList<>();
        phoneNoList = new ArrayList<>();
        getIsdCode();
        //getSupportActionBar().show();
        //Toast.makeText(this, ""+isdList.size(), Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(CircleMembersRequestActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CircleMembersRequestActivity.this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CODE);
            return;
        }
        fetchContacts();
        android.util.Log.d("contactlist", "" + phoneNoList);
        android.util.Log.d("isdlist", "" + isdList);
        filterContacts();
        android.util.Log.d("contactlist", "" + phoneNoList);
        android.util.Log.d("isdlist", "" + isdList);
    }

    private void filterContacts() {
        for (int j = 0; j < phoneNoList.size(); j++) {
            for (int i = 0; i < isdList.size(); i++) {
                if (phoneNoList.get(j).toString().contains(isdList.get(i).toString())) {
                    phoneNoList.set(j, phoneNoList.get(j).replace(isdList.get(i).toString().trim(), ""));
                    phoneNumber.append(phoneNoList.get(j).trim() + ",");
                }
            }
        }
        sendContacts(phoneNumber);
    }

    private void getIsdCode() {
        Set<String> stringSet = getApplication().getSharedPreferences("Isdcodes", 0).getStringSet("isdlist", null);
        isdList = new ArrayList<>(stringSet);
//        isdList = (List<String>) getApplication().getSharedPreferences("Isdcodes",0).getStringSet("isdlist",null);
    }

    private void fetchContacts() {


        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {

            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));


                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNoList.add(phoneNo);
//                        String code = phoneNo.substring("+91")
//                        contactNumbers.append(code + ",");
//                        model.setContactNumber(contactNumbers);
                    }

                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }



    }

    private void sendContacts(StringBuffer phone) {
        model = new ContactsModel();
        model.setContactNumber(phone);
        android.util.Log.d("stringBuffer", "" + phone);
        detailsModel = new CircleDetailsModel();
        circleDetailsList = new ArrayList<>();
        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        retrofit2.Call<ContactResponseModel> call = services.postAllContacts(model);
        call.enqueue(new Callback<ContactResponseModel>() {
            @Override
            public void onResponse(retrofit2.Call<ContactResponseModel> call, Response<ContactResponseModel> response) {
                android.util.Log.e("Response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if (response != null) {
                    if (response.body() != null) {
                        circleDetailsList.addAll(response.body().getCircleDetails());
                        android.util.Log.d("contactlist", "" + circleDetailsList);
                        adapter=new RequestCodeAdapter(CircleMembersRequestActivity.this,circleDetailsList);
                        rvContactList.setLayoutManager(new LinearLayoutManager(CircleMembersRequestActivity.this));
                        rvContactList.setAdapter(adapter);


                    } else {
                       showAlertBox("Alert","No circle members found in your contacts.");
                    }
                }


            }

            @Override
            public void onFailure(retrofit2.Call<ContactResponseModel> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
                Toast.makeText(CircleMembersRequestActivity.this, "Failed to fetch circle details", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onPermissionGranted() {

    }

    private void showAlertBox(String title,String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                CircleMembersRequestActivity.this).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CircleMembersRequestActivity.this.finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }
}
