package com.zgzx.metaphysics.widgets.sign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 签到控件
 */
public class SignView extends LinearLayout {

    private TextView tvYear;
    private SignGridView gvDate;
    private DateAdapter adapterDate;

    public SignView(Context context) {
        super(context);
       init();
    }

    public SignView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       init();
    }


    public void init(){

        init(DateUtil.YEAR,DateUtil.MONTH, LocalConfigStore.mContents);
    }

    public void init(int year, int month, List<String> content){
        View view = View.inflate(getContext(), R.layout.layout_signdate,this);
        tvYear = view.findViewById(R.id.tvYear);
        gvDate = view.findViewById(R.id.gvDate);
       // tvYear.setText(year+"-"+month);
        adapterDate = new DateAdapter(getContext(),year,month,content);
        gvDate.setAdapter(adapterDate);

    }

    public void signIn(DateAdapter.OnSignListener onSignedSuccess){
        adapterDate.signIn(onSignedSuccess);
    }

//    public boolean isSign(){
//        return adapterDate.isSign();
//    }


}
