package com.example.termproject2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainManageLayout extends GridLayout {
    ImageButton editimage;
    EditText editname, editinfo, editprice;
    TextView menuId;
    Button editMenu, deleteMenu;

    public MainManageLayout(Context context) {
        super(context);
        initView();
    }

    public MainManageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public MainManageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);

    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.mainmanage, this, false);
        addView(v);
        menuId = (TextView) findViewById(R.id.menuId);
        editimage = (ImageButton) findViewById(R.id.editimage);
        editname = (EditText) findViewById(R.id.editname);
        editinfo = (EditText) findViewById(R.id.editinfo);
        editprice = (EditText) findViewById(R.id.editprice);
        editMenu = (Button) findViewById(R.id.editMenu);
        deleteMenu = (Button) findViewById(R.id.deleteMenu);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MainManageLayout);
        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MainManageLayout, defStyle, 0);
        setTypeArray(typedArray);
    }


    private void setTypeArray(TypedArray typedArray) {
        int menuimage_resID = typedArray.getResourceId(R.styleable.MainManageLayout_editimage, R.drawable.chicken_chk);
        editimage.setImageResource(menuimage_resID);

        String menuname_resID = typedArray.getString(R.styleable.MainManageLayout_editname);
        editname.setText(menuname_resID);

        String menuinfo_resID = typedArray.getString(R.styleable.MainManageLayout_editinfo);
        editinfo.setText(menuinfo_resID);

        String price_resID = typedArray.getString(R.styleable.MainManageLayout_editprice);
        editprice.setText(price_resID);

        typedArray.recycle();
    }

    void setMenuId(int text_menuid) {
        menuId.setText(String.valueOf(text_menuid));
    }

    void setMenuimage(int menuimage_resID) {
        editimage.setImageResource(menuimage_resID);
    }

    void setMenuname(String text_menuname) {
        editname.setText(text_menuname);
    }

    void setMenuinfo(String text_menuinfo) {
        editinfo.setText(text_menuinfo);
    }

    void setPrice(String text_price) {
        editprice.setText(text_price);
    }

    String getMenuId() {
        return menuId.getText().toString();
    }

    String getMenuname() {
        return editname.getText().toString();
    }

    String getMenuinfo() {
        return editinfo.getText().toString();
    }

    String getPrice() {
        return editprice.getText().toString();
    }

    ImageButton getiButton()

    {
        return editimage;
    }

    Button geteButton() {
        return editMenu;
    }

    Button getdButton() {
        return deleteMenu;
    }
}
