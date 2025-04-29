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

public class SubMenuLayout extends GridLayout {
    ImageView menuimage;
    TextView menuname, menuprice;
    Button addMenu;
    Spinner setQuantity;
    CheckBox check_box1, check_box2, check_box3;
    RatingBar spicyBar;

    public SubMenuLayout(Context context) {
        super(context);
        initView();
    }

    public SubMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SubMenuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);

    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.submenu, this, false);
        addView(v);

        menuimage = (ImageView) findViewById(R.id.menuimage);
        menuname = (TextView) findViewById(R.id.menuname);
        menuprice = (TextView) findViewById(R.id.price);
        setQuantity = (Spinner) findViewById(R.id.setQuantity);
        addMenu = (Button) findViewById(R.id.addMenu);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SubMenuLayout);
        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SubMenuLayout, defStyle, 0);
        setTypeArray(typedArray);
    }


    private void setTypeArray(TypedArray typedArray) {
        int menuimage_resID = typedArray.getResourceId(R.styleable.SubMenuLayout_subimage, R.drawable.chicken_chk);
        menuimage.setImageResource(menuimage_resID);

        String menuname_resID = typedArray.getString(R.styleable.SubMenuLayout_subname);
        menuname.setText(menuname_resID);

        String price_resID = typedArray.getString(R.styleable.SubMenuLayout_subprice);
        menuprice.setText(price_resID);

        typedArray.recycle();
    }

    void setMenuimage(int menuimage_resID) {
        menuimage.setImageResource(menuimage_resID);
    }

    void setMenuname(String text_menuname) {
        menuname.setText(text_menuname);
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

}
