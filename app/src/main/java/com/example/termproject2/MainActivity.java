package com.example.termproject2;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
    Button addMenu, order, cancel, orderOpen, manageOpen;
    Intent intentOrder, intentManage;
    Spinner setQuantity;
    EditText dlgEdtPassword;

    private int REQUEST_CODE = 1;
    int totalPrice = 0;

    String addOption;

    private LinearLayout container;
    TextView orderPrice;
    View dialogView;
    private DatabaseHelperMain dbHelperMain;
    private DatabaseHelperSub dbHelperSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecMain = tabHost.newTabSpec("MAINMENU").setIndicator("메인메뉴");
        tabSpecMain.setContent(R.id.tabMainMenu);
        tabHost.addTab(tabSpecMain);

        TabHost.TabSpec tabSpecSub = tabHost.newTabSpec("SUBMENU").setIndicator("서브메뉴");
        tabSpecSub.setContent(R.id.tabSubMenu);
        tabHost.addTab(tabSpecSub);

        tabHost.setCurrentTab(0);

        intentOrder = new Intent(getApplicationContext(), OrderActivity.class);
        intentManage = new Intent(getApplicationContext(), ManageActivity.class);

        LinearLayout con = (LinearLayout) findViewById(R.id.tabMainMenu);
        orderPrice = (TextView) findViewById(R.id.orderPrice);

        final TextView toppingPrice = new TextView(this);
        toppingPrice.setText("토핑은 각 1000원이 추가됩니다.");
        con.addView(toppingPrice);

        dbHelperMain = new DatabaseHelperMain(this);
        Cursor cursor = dbHelperMain.readRecordOrderByID();
        if (cursor.getCount() == 0) {
            dbHelperMain.insertRecord("@drawable/egg_croquette_curry", "계란 크로켓 카레",
                    "계란 후라이와 크로켓이 토핑으로 포함된 카레입니다.", 8000);
            dbHelperMain.insertRecord("@drawable/egg_garlic_curry", "계란 마늘 카레",
                    "계란 후라이와 마늘 후레이크가 토핑으로 포함된 카레입니다.", 8000);
            dbHelperMain.insertRecord("@drawable/egg_pumpkin_curry", "계란 단호박 카레",
                    "계란 후라이와 단호박이 토핑으로 포함된 카레입니다.", 8000);
        }

        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseMain.Curry._ID));
            String itemimage = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMain.Curry.MENUIMAGE));
            final String itemname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMain.Curry.MENUNAME));
            String iteminfo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMain.Curry.MENUINFO));
            final int itemprice = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseMain.Curry.PRICE));

            final MainMenuLayout mLayout = new MainMenuLayout(getApplicationContext());

            mLayout.setMenuimage(getResources().getIdentifier(itemimage, "drawable", this.getPackageName()));
            mLayout.setMenuname(itemname);
            mLayout.setMenuinfo(iteminfo);
            mLayout.setPrice(String.valueOf(itemprice));
            addMenu = mLayout.getButton();
            setQuantity = mLayout.getSpinner();

            ArrayAdapter quantityAdapter = ArrayAdapter.createFromResource(this, R.array.test, android.R.layout.simple_spinner_dropdown_item);
            quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setQuantity.setAdapter(quantityAdapter);

            addMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String quantity = mLayout.getQuantity();
                    addOption = mLayout.getOption();

                    totalPrice += itemprice * Integer.parseInt(quantity) + mLayout.getOptionPrice();
                    orderPrice.setText(totalPrice + " 원");
                    textview(itemname + " " + quantity + "\n" + addOption);
                }
            });
            con.addView(mLayout);
        }

        LinearLayout emptyLayout = new LinearLayout(this);
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams emptyPram = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        emptyLayout.setLayoutParams(emptyPram);
        con.addView(emptyLayout);

        dbHelperSub = new DatabaseHelperSub(this);
        Cursor cursorSub = dbHelperSub.readRecordOrderByID();
        if (cursorSub.getCount() == 0) {
            dbHelperSub.insertRecord("@drawable/rice", "밥 추가", 1000);
            dbHelperSub.insertRecord("@drawable/curry", "카레 추가", 2000);
            dbHelperSub.insertRecord("@drawable/coke", "콜라", 2000);
            dbHelperSub.insertRecord("@drawable/cider", "사이다", 2000);
        }

        while (cursorSub.moveToNext()) {
            int subitemId = cursorSub.getInt(cursorSub.getColumnIndexOrThrow(DatabaseSub.SubMenu._ID));
            String subitemimage = cursorSub.getString(cursorSub.getColumnIndexOrThrow(DatabaseSub.SubMenu.MENUIMAGE));
            final String subitemname = cursorSub.getString(cursorSub.getColumnIndexOrThrow(DatabaseSub.SubMenu.MENUNAME));
            final int subitemprice = cursorSub.getInt(cursorSub.getColumnIndexOrThrow(DatabaseSub.SubMenu.PRICE));

            final SubMenuLayout sLayout = new SubMenuLayout(getApplicationContext());

            sLayout.setMenuimage(getResources().getIdentifier(subitemimage, "drawable", this.getPackageName()));
            sLayout.setMenuname(subitemname);
            sLayout.setPrice(String.valueOf(subitemprice));
            addMenu = sLayout.getButton();
            setQuantity = sLayout.getSpinner();

            ArrayAdapter quantityAdapter = ArrayAdapter.createFromResource(this, R.array.test, android.R.layout.simple_spinner_dropdown_item);
            quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setQuantity.setAdapter(quantityAdapter);

            addMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String quantity = sLayout.getQuantity();

                    totalPrice += subitemprice * Integer.parseInt(quantity);
                    orderPrice.setText(totalPrice + " 원");
                    textview(subitemname + " " + quantity);
                }
            });

            LinearLayout conSub = (LinearLayout) findViewById(R.id.tabSubMenu);
            conSub.addView(sLayout);
        }

//        addMenu = (Button) findViewById(R.id.addMenu);
        order = (Button) findViewById(R.id.order);
        cancel = (Button) findViewById(R.id.cancel);
        orderOpen = (Button) findViewById(R.id.orderOpen);
        manageOpen = (Button) findViewById(R.id.manageOpen);

//        addMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView menuName = (TextView) findViewById(R.id.menuname);
//                TextView menuPrice = (TextView) findViewById(R.id.price);
//
//                String setName = menuName.getText().toString();
//                String setPrice = menuPrice.getText().toString();
//                textview(setName + "\n" + setPrice);
//
//            }
//        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout orderContent = (LinearLayout) findViewById(R.id.content);
                int childCount = orderContent.getChildCount();

                for (int i = 1; i < childCount; i++) {
                    TextView orderView = (TextView) orderContent.getChildAt(i);
                    String setOrder = orderView.getText().toString();

                    intentOrder.putExtra("orderList" + i, setOrder);
                }
                intentOrder.putExtra("orderPrice", totalPrice);
                intentOrder.putExtra("orderCount", childCount);

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm:ss");
                String formatDate = sdfNow.format(date);
                intentOrder.putExtra("orderTime", formatDate);
                Toast.makeText(getApplicationContext(), formatDate + " 주문이 완료되었습니다.", Toast.LENGTH_LONG).show();

                SlidingDrawer orderList = (SlidingDrawer) findViewById(R.id.orderList);
                orderList.animateClose();

                orderOpen.setEnabled(true);
                orderContent.removeViews(1, childCount - 1);
                totalPrice = 0;
                orderPrice.setText(totalPrice + " 원");
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout orderContent = (LinearLayout) findViewById(R.id.content);
                int childCount = orderContent.getChildCount();

                orderContent.removeViews(1, childCount - 1);
                totalPrice = 0;
                orderPrice.setText(totalPrice + " 원");
            }
        });


        orderOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intentOrder, REQUEST_CODE);
            }
        });

        manageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (View) View.inflate(MainActivity.this, R.layout.managedialog, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("관리자 권한 확인");
                dlg.setIcon(R.drawable.ic_launcher_foreground);
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dlgEdtPassword = (EditText) dialogView.findViewById((R.id.dlgEdtPW));
                        String password = dlgEdtPassword.getText().toString();
                        if (password.equals("1718")) {
                            startActivityForResult(intentManage, REQUEST_CODE);
                        } else {
                            Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dlg.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            finish();
            startActivity(getIntent());
        }
    }

    public void textview(String pack) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(15 * dm.density);

        //부모 뷰
        container = (LinearLayout) findViewById(R.id.content);

        //TextView 생성
        TextView view1 = new TextView(this);
        view1.setText(pack);
        view1.setTextSize(15);
        view1.setTextColor(Color.BLACK);
        view1.setBackgroundColor(Color.rgb(253, 214, 146));

        //layout_width, layout_height, gravity 설정
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.topMargin = size;
        view1.setLayoutParams(lp);

        //부모 뷰에 추가
        container.addView(view1);
    }

}
