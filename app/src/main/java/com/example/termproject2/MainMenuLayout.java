package com.example.termproject2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainMenuLayout extends GridLayout {
    ImageView menuimage;
    TextView menuname, menuinfo, menuprice;
    Button addMenu;
    Spinner setQuantity;
    CheckBox check_box1, check_box2, check_box3;
    RatingBar spicyBar;

    public MainMenuLayout(Context context) {
        super(context);
        initView();
    }

    public MainMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public MainMenuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);

    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.mainmenu, this, false);
        addView(v);

        menuimage = (ImageView) findViewById(R.id.menuimage);
        menuname = (TextView) findViewById(R.id.menuname);
        menuinfo = (TextView) findViewById(R.id.menuinfo);
        menuprice = (TextView) findViewById(R.id.price);
        setQuantity = (Spinner) findViewById(R.id.setQuantity);
        addMenu = (Button) findViewById(R.id.addMenu);
        check_box1 = (CheckBox) findViewById(R.id.check_box1);
        check_box2 = (CheckBox) findViewById(R.id.check_box2);
        check_box3 = (CheckBox) findViewById(R.id.check_box3);
        spicyBar = (RatingBar) findViewById(R.id.spicy);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MainMenuLayout);
        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MainMenuLayout, defStyle, 0);
        setTypeArray(typedArray);
    }


    private void setTypeArray(TypedArray typedArray) {
        int menuimage_resID = typedArray.getResourceId(R.styleable.MainMenuLayout_menuimage, R.drawable.chicken_chk);
        menuimage.setImageResource(menuimage_resID);

        String menuname_resID = typedArray.getString(R.styleable.MainMenuLayout_menuname);
        menuname.setText(menuname_resID);

        String menuinfo_resID = typedArray.getString(R.styleable.MainMenuLayout_menuinfo);
        menuinfo.setText(menuinfo_resID);

        String price_resID = typedArray.getString(R.styleable.MainMenuLayout_menuprice);
        menuprice.setText(price_resID);

        typedArray.recycle();
    }

    void setMenuimage(int menuimage_resID) {
        menuimage.setImageResource(menuimage_resID);
    }

    void setMenuname(String text_menuname) {
        menuname.setText(text_menuname);
    }

    void setMenuinfo(String text_menuinfo) {
        menuinfo.setText(text_menuinfo);
    }

    void setPrice(String text_price) {
        menuprice.setText(text_price);
    }

    Button getButton() {
        return addMenu;
    }

    Spinner getSpinner(){return setQuantity;}

    String getQuantity(){
        return setQuantity.getSelectedItem().toString();
    }
    String getOption() {
        String topping = "토핑: ";
        String spicy = "맵기: ";
        if (check_box1.isChecked()) {
            topping += "치킨 ";
        }
        if (check_box2.isChecked()) {
            topping += "소세지 ";
        }
        if (check_box3.isChecked()) {
            topping += "새우튀김 ";
        }
        if (spicyBar.getRating() == 1) {
            spicy += "1단계";
        } else if (spicyBar.getRating() == 2) {
            spicy += "2단계";
        } else {
            spicy += "3단계";
        }
        return topping + "/ " + spicy;
    }
    int getOptionPrice() {
        int optionPrice = 0;
        if (check_box1.isChecked()) {
            optionPrice += 1000;
        }
        if (check_box2.isChecked()) {
            optionPrice += 1000;
        }
        if (check_box3.isChecked()) {
            optionPrice += 1000;
        }
        return optionPrice;
    }

}
