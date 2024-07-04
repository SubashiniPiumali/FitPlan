package com.example.fitplan.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitplan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BMIFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BMIFragment extends Fragment {
    SeekBar mseekbarforheight;
    TextView mcurrentheight;
    TextView mcurrentweight;
    ImageView mincrementweight,mdecrementweight;
    TextView mbmidisplay,magedisplay,mweightdisplay,mheightdisplay,mbmicategory,mgender, bmidisplay2;
    Button mcalculatebmi;
    RelativeLayout mmale,mfemale;

    int intweight=55;
    int currentprogress;
    String mintprogress="170";
    String typerofuser="0";
    String weight2="55";
    float intbmi;

    float intheight,intweight2;
    String mbmi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_b_m_i, container, false);
        mcurrentweight=view.findViewById(R.id.currentweight);
        mcurrentheight=view.findViewById(R.id.currentheight);
        mincrementweight=view.findViewById(R.id.incremetweight);
        mdecrementweight=view.findViewById(R.id.decrementweight);
        mcalculatebmi=view.findViewById(R.id.calculatebmi);
        mseekbarforheight=view.findViewById(R.id.seekbarforheight);
        bmidisplay2 = view.findViewById(R.id.bmidisplay2);
        mbmicategory = view.findViewById(R.id.bmicategorydispaly);
        mseekbarforheight.setMax(300);
        mseekbarforheight.setProgress(170);
        mseekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentprogress=progress;
                mintprogress=String.valueOf(currentprogress);
                mcurrentheight.setText(mintprogress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mincrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intweight=intweight+1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            }
        });


        mdecrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intweight=intweight-1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            }
        });

        mcalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(mintprogress.equals("0"))
                {
                    Toast.makeText(view.getContext(),"Select Your Height First",Toast.LENGTH_SHORT).show();
                }
                else if(intweight==0|| intweight<0)
                {
                    Toast.makeText(view.getContext(),"Weight Is Incorrect",Toast.LENGTH_SHORT).show();
                }
                else {

                    Bundle result = new Bundle();;
                    result.putString("HEIGHT", mintprogress);
                    result.putString("WEIGHT", weight2);

                    intheight=Float.parseFloat(mintprogress);
                    intweight2=Float.parseFloat(weight2);

                    intheight=intheight/100;
                    intbmi=intweight2/(intheight*intheight);


                    mbmi=Float.toString(intbmi);
                    String formattedmbmi = String.format("%.2f", intbmi);
                    bmidisplay2.setText("BMI: "+formattedmbmi);


                        if(intbmi<16)
                        {
                            mbmicategory.setText("Severe Thinness");
                            mbmicategory.setBackgroundColor(Color.GRAY);

                        }
                        else if(intbmi<16.9 && intbmi>16)
                        {
                            mbmicategory.setText("Moderate Thinness");
                            mbmicategory.setBackgroundColor(getResources().getColor(R.color.halfwarn));

                        }
                        else if(intbmi<18.4 && intbmi>17)
                        {
                            mbmicategory.setText("Mild Thinness");
                            mbmicategory.setBackgroundColor(getResources().getColor(R.color.halfwarn));
                        }
                        else if(intbmi<24.9 && intbmi>18.5 )
                        {
                            mbmicategory.setText("Normal");
                            // mbackground.setBackgroundColor(Color.YELLOW);
                            mbmicategory.setBackgroundColor(Color.YELLOW);
                        }
                        else if(intbmi <29.9 && intbmi>25)
                        {
                            mbmicategory.setText("Overweight");
                            mbmicategory.setBackgroundColor(getResources().getColor(R.color.halfwarn));
                        }
                        else if(intbmi<34.9 && intbmi>30)
                        {
                            mbmicategory.setText("Obese Class I");
                            mbmicategory.setBackgroundColor(getResources().getColor(R.color.halfwarn));
                        }
                        if(intbmi<39.9 && intbmi>35)
                        {
                            mbmicategory.setText("Obese Class II");
                            mbmicategory.setBackgroundColor(getResources().getColor(R.color.halfwarn));
                        }
                        else if(intbmi > 40)
                        {
                            mbmicategory.setText("Obese Class III");
                            mbmicategory.setBackgroundColor(getResources().getColor(R.color.warn));
                        }



                }


            }
        });

        return view;
    }

}