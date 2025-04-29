package com.example.termproject2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;


public class ManageActivity extends AppCompatActivity {
    private LinearLayout container;
    private DatabaseHelperMain dbHelper;
    ImageButton editImage;
    Button editMenu, deleteMenu, newMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        setTitle("관리자 화면");

        dbHelper = new DatabaseHelperMain(this);
        final Cursor cursor = dbHelper.readRecordOrderByID();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseMain.Curry._ID));
            String itemimage = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMain.Curry.MENUIMAGE));
            final String itemname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMain.Curry.MENUNAME));
            String iteminfo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMain.Curry.MENUINFO));
            final int itemprice = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseMain.Curry.PRICE));

            final MainManageLayout mLayout = new MainManageLayout(getApplicationContext());

            mLayout.setMenuId(itemId);
            mLayout.setMenuimage(getResources().getIdentifier(itemimage, "drawable", this.getPackageName()));
            mLayout.setMenuname(itemname);
            mLayout.setMenuinfo(iteminfo);
            mLayout.setPrice(String.valueOf(itemprice));
            editImage=mLayout.getiButton();
            editMenu = mLayout.geteButton();
            deleteMenu = mLayout.getdButton();

            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                }});

            editMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String editId = mLayout.getMenuId();
                    String editName = mLayout.getMenuname();
                    String editInfo = mLayout.getMenuinfo();
                    String editPrice = mLayout.getPrice();

                    dbHelper.updateRecord(Integer.parseInt(editId), editName, editInfo, Integer.parseInt(editPrice));
                    finish();
                    startActivity(getIntent());
                }
            });
            deleteMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String editId = mLayout.getMenuId();

                    dbHelper.deleteRecord(Integer.parseInt(editId));
                    finish();
                    startActivity(getIntent());
                }
            });

            LinearLayout con = (LinearLayout) findViewById(R.id.tabMainManage);
            con.addView(mLayout);
        }

        {
            final MainManageLayout mLayout = new MainManageLayout(getApplicationContext());
            editMenu = mLayout.geteButton();
            newMenu = mLayout.getdButton();

            editMenu.setText("");
            editMenu.setEnabled(false);
            newMenu.setText("추가");

            editImage=mLayout.getiButton();

            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                }});


            newMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //String editImage = mLayout.getMenuImage();
                    String editName = mLayout.getMenuname();
                    String editInfo = mLayout.getMenuinfo();
                    String editPrice = mLayout.getPrice();

                    dbHelper.insertRecord("@drawable/garlic_curry", editName,
                            editInfo, Integer.parseInt(editPrice));
                    finish();
                    startActivity(getIntent());
                }
            });

            LinearLayout con = (LinearLayout) findViewById(R.id.tabMainManage);
            con.addView(mLayout);
        }


        Button btnReturn = (Button) findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);

                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

    }
    Uri photoURI;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    photoURI = data.getData();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    editImage.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
