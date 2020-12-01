package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class IfLogin extends AppCompatActivity {
    TextView tvWelcome;
    TextView tvFeelingResultSummery;
    TextView tvFaceSummery;
    TextView tvFeeling1;
    TextView tvFeeling2;
    TextView tvFeeling3;
    ImageView ivFeeling1;
    ImageView ivFeeling2;
    ImageView ivFeeling3;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvFeelingResultSummery = (TextView) findViewById(R.id.tvFeelingResultYellow);
        tvFaceSummery = (TextView) findViewById(R.id.tvFaceSummery);
        tvFeeling1 = (TextView) findViewById(R.id.tvFeeling1);
        tvFeeling2 = (TextView) findViewById(R.id.tvFeeling2);
        tvFeeling3 = (TextView) findViewById(R.id.tvFeeling3);

        ivFeeling1 = (ImageView) findViewById(R.id.ivFeeling1);
        ivFeeling2 = (ImageView) findViewById(R.id.ivFeeling2);
        ivFeeling3 = (ImageView) findViewById(R.id.ivFeeling3);


         GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

    public void settingNames(String name) {
         String welcome = name +"님, 환영합니다.";
         String feeling = name + "님의 감정 분석 결과";
         String faceSummery = name + "님의 얼굴 분석 결과";

         tvWelcome.setText(welcome);
         tvFeelingResultSummery.setText(feeling);
         tvFaceSummery.setText(faceSummery);
    }

    public void settingFeelings(String feel1, int mes1, String feel2, int mes2, String feel3, int mes3) {
         String[] feels = new String[] {feel1, feel2, feel3};
         int[] mes = new int[] {mes1, mes2, mes3};

         for (int i = 0; i < feels.length; i++) {
             String f = feels[i];
             switch (f) {
                 case "happy" :
//                     해당 경로에 해당하는 사진으로 바꾸고 % 조절한다.
                     break;
                 case "sad" :

                     break;
                 case "neutral" :

                     break;

                 case "fearful" :

                     break;

                 case "disgusted" :

                     break;

                 case "angry" :

                     break;

                 case "surprised" :

                     break;


             }

         }
    }
    public void settingHighestFeeling(String feeling) {
        switch (feeling) {
            case "happy":
                tvFeelingResultSummery.setText("youarehappy");
                break;
            case "sad":

                break;
            case "neutral":

                break;

            case "fearful":

                break;

            case "disgusted":

                break;

            case "angry":

                break;

            case "surprised":

                break;
        }
    }
}