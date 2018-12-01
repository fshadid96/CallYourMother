package com.example.farahshadid.callyourmother;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button topConButton1, topConButton2, topConButton3, topConButton4,
            topConButton5, sugConButton1, sugConButton2, sugConButton3 ;
    private int b1 = 0,b2 = 0,b3 = 0,b4 = 0,
            b5 = 0,s1 = 0,s2 = 0,s3 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);


        //=========================================== Top Contacts

        topConButton1 = v.findViewById(R.id.top_con1);
        // This is will be where we'll call the get top con and set it to teh button, same applies
        // to the other ones
        topConButton1.setText("Hello");
        topConButton1.setOnClickListener(this);

        topConButton2 = v.findViewById(R.id.top_con2);
        topConButton2.setText("You");
        topConButton2.setOnClickListener(this);

        topConButton3 = v.findViewById(R.id.top_con3);
        topConButton3.setText("There");
        topConButton3.setOnClickListener(this);

        topConButton4 = v.findViewById(R.id.top_con4);
        topConButton4.setText("Uhhh");
        topConButton4.setOnClickListener(this);

        topConButton5 = v.findViewById(R.id.top_con5);
        topConButton5.setText("yeah");
        topConButton5.setOnClickListener(this);


        //============================================ Sugggestions
        sugConButton1 = v.findViewById(R.id.sugg_con1);
        sugConButton1.setText("Thank you");
        sugConButton1.setOnClickListener(this);

        sugConButton2 = v.findViewById(R.id.sugg_con2);
        sugConButton2.setText("Based");
        sugConButton2.setOnClickListener(this);

        sugConButton3 = v.findViewById(R.id.sugg_con3);
        sugConButton3.setText("God");
        sugConButton3.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.top_con1:
                if(b1 == 0){
                    topConButton1.setText("22222222");
                    b1 = 1;
                }else{
                    topConButton1.setText("Hello");
                    b1 = 0;
                }
                break;

            case R.id.top_con2:
                if(b2 == 0){
                    topConButton2.setText("111111111");
                    b2 = 1;
                }else{
                    topConButton2.setText("You");
                    b2 = 0;
                }
                break;

            case R.id.top_con3:
                if(b3 == 0){
                    topConButton3.setText("3232323232");
                    b3 = 1;
                }else{
                    topConButton3.setText("There");
                    b3 = 0;
                }
                break;

            case R.id.top_con4:
                if(b4 == 0){
                    topConButton4.setText("535355353");
                    b4 = 1;
                }else{
                    topConButton4.setText("Uhhh");
                    b4 = 0;
                }
                break;

            case R.id.top_con5:
                if(b5 == 0){
                    topConButton5.setText("646646464646");
                    b5 = 1;
                }else{
                    topConButton5.setText("yeah");
                    b5 = 0;
                }
                break;
//==========================================================
            case R.id.sugg_con1:
                if(s1 == 0){
                    sugConButton1.setText("77757577575");
                    s1 = 1;
                }else{
                    sugConButton1.setText("Thank you");
                    s1 = 0;
                }
                break;

            case R.id.sugg_con2:
                if(s2 == 0){
                    sugConButton2.setText("683839857");
                    s2 = 1;
                }else{
                    sugConButton2.setText("Based");
                    s2 = 0;
                }
                break;

            case R.id.sugg_con3:
                if(s3 == 0){
                    sugConButton3.setText("1113445533");
                    s3 = 1;
                }else{
                    sugConButton3.setText("God");
                    s3 = 0;
                }
                break;
        }
    }
}
