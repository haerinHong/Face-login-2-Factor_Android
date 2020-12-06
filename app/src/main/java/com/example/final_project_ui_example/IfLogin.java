package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class IfLogin extends AppCompatActivity {
    TextView tvWelcome;
    TextView tvFeelingResultYellow;
    TextView tvFaceSummery;
    TextView tvFeelingSummery;
    TextView tvFeeling1;
    TextView tvFeeling2;
    TextView tvFeeling3;
    ImageView ivFeeling1;
    ImageView ivFeeling2;
    ImageView ivFeeling3;
    String feel1;
    String feel2;
    String feel3;
    int feel1_state;
    int feel2_state;
    int feel3_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvFeelingResultYellow = (TextView) findViewById(R.id.tvFeelingResultYellow);
        tvFaceSummery = (TextView) findViewById(R.id.tvFaceSummery);
        tvFeelingSummery = (TextView) findViewById(R.id.tvFeelingSummery);
        tvFeeling1 = (TextView) findViewById(R.id.tvFeeling1);
        tvFeeling2 = (TextView) findViewById(R.id.tvFeeling2);
        tvFeeling3 = (TextView) findViewById(R.id.tvFeeling3);

        ivFeeling1 = (ImageView) findViewById(R.id.ivFeeling1);
        ivFeeling2 = (ImageView) findViewById(R.id.ivFeeling2);
        ivFeeling3 = (ImageView) findViewById(R.id.ivFeeling3);

        Intent iflogin = getIntent();
        String real_fake = iflogin.getStringExtra("real_fake");
        String user_name = iflogin.getStringExtra("name");
        feel1 = iflogin.getStringExtra("feel1");
        feel2 =  iflogin.getStringExtra("feel2");
        feel3 = iflogin.getStringExtra("feel3");
        feel1_state = iflogin.getIntExtra("feel1_state", 70);
        feel2_state = iflogin.getIntExtra("feel2_state", 20);
        feel3_state = iflogin.getIntExtra("feel3_state", 10);

         settingNames(user_name);
         settingHighestFeeling(feel1, user_name);
         settingFeelings(feel1, feel1_state, feel2, feel2_state, feel3, feel3_state);

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

    public void settingNames(String user_name) {
         String welcome = user_name +"님, 환영합니다.";
         String feeling = user_name + "님의 감정 분석 결과";
         String faceSummery = user_name + "님의 얼굴 분석 결과";

         tvWelcome.setText(welcome);
        tvFeelingResultYellow.setText(feeling);
         tvFaceSummery.setText(faceSummery);
    }

    public void settingFeelings(String ff1, int mes1, String ff2, int mes2, String ff3, int mes3) {
         String[] feelingstring = new String[] {feel1, feel2, feel3};

         int[] mes = new int[] {feel1_state, feel2_state, feel3_state};
        Log.d("IfLogin", "feelingstring " + feel1 + " " + feel2 + " "+ feel3);
        Log.d("IfLogin", "settingFeelings " + ff1 + " " + ff2 + " "+ ff3);
        Log.d("IfLogin", "mes " + mes[0] + " " + mes[1] + " "+ mes[2]);


         for (int i = 0; i < feelingstring.length; i++) {
             String f = feelingstring[i];
             Log.d("IfLogin", "feels[i] " + feelingstring[i]);
             int feel_code = -1;
             if ( f.equals("happy")) { feel_code = 1;}
             else if(f.equals("sad")) { feel_code = 2;}
             else if(  f.equals("neutral")) { feel_code = 3;}
             else if( f.equals("fearful")) { feel_code = 4;}
             else if( f.equals("disgusted")) { feel_code = 5;}
             else if( f.equals("angry")) { feel_code = 6;}
             else if(f.equals("surprised")) { feel_code = 7;}
             else {
//             감정이 감지되지 않은 경우 neutral 기본
                 feel_code = 3;
             }
             switch (feel_code) {
                 case 1 :
//                     해당 경로에 해당하는 사진으로 바꾸고 % 조절한다.
                    if (i == 0) {
                        ivFeeling1.setImageResource(R.drawable.happy);
                        tvFeeling1.setText("행복 " + Integer.toString(mes[0]) + "%");
                    } else if (i == 1) {
                        ivFeeling2.setImageResource(R.drawable.happy);
                        tvFeeling2.setText("행복  " + Integer.toString(mes[1]) + "%");
                    } else {
                        ivFeeling3.setImageResource(R.drawable.happy);
                        tvFeeling3.setText("행복  " +Integer.toString(mes[2]) + "%");
                    }
                     break;
                 case 2 :
                     if (i == 0) {
                         ivFeeling1.setImageResource(R.drawable.sad);
                         tvFeeling1.setText("슬픔 " +Integer.toString(mes[0]) + "%");
                     } else if (i == 1) {
                         ivFeeling2.setImageResource(R.drawable.sad);
                         tvFeeling2.setText("슬픔 " +Integer.toString(mes[1]) + "%");
                     } else {
                         ivFeeling3.setImageResource(R.drawable.sad);
                         tvFeeling3.setText("슬픔 " +Integer.toString(mes[2]) + "%");
                     }
                     break;
                 case 3 :
                     if (i == 0) {
                         ivFeeling1.setImageResource(R.drawable.neutral);
                         tvFeeling1.setText("중립 " +Integer.toString(mes[0]) + "%");
                     } else if (i == 1) {
                         ivFeeling2.setImageResource(R.drawable.neutral);
                         tvFeeling2.setText("중립  " +Integer.toString(mes[1]) + "%");
                     } else {
                         ivFeeling3.setImageResource(R.drawable.neutral);
                         tvFeeling3.setText("중립  " +Integer.toString(mes[2]) + "%");
                     }
                     break;

                 case 4 :
                     if (i == 0) {
                         ivFeeling1.setImageResource(R.drawable.fearful);
                         tvFeeling1.setText("두려움 " +Integer.toString(mes[0]) + "%");
                     } else if (i == 1) {
                         ivFeeling2.setImageResource(R.drawable.fearful);
                         tvFeeling2.setText("두려움 " +Integer.toString(mes[1]) + "%");
                     } else {
                         ivFeeling3.setImageResource(R.drawable.fearful);
                         tvFeeling3.setText("두려움 " +Integer.toString(mes[2]) + "%");
                     }
                     break;

                 case 5 :
                     if (i == 0) {
                         ivFeeling1.setImageResource(R.drawable.disgusted);
                         tvFeeling1.setText("역겨움 " +Integer.toString(mes[0]) + "%");
                     } else if (i == 1) {
                         ivFeeling2.setImageResource(R.drawable.disgusted);
                         tvFeeling2.setText("역겨움 " +Integer.toString(mes[1]) + "%");
                     } else {
                         ivFeeling3.setImageResource(R.drawable.disgusted);
                         tvFeeling3.setText("역겨움 " +Integer.toString(mes[2]) + "%");
                     }
                     break;

                 case 6 :
                     if (i == 0) {
                         ivFeeling1.setImageResource(R.drawable.angry);
                         tvFeeling1.setText("화남 " +Integer.toString(mes[0]) + "%");
                     } else if (i == 1) {
                         ivFeeling2.setImageResource(R.drawable.angry);
                         tvFeeling2.setText("화남 " +Integer.toString(mes[1]) + "%");
                     } else {
                         ivFeeling3.setImageResource(R.drawable.angry);
                         tvFeeling3.setText("화남 " +Integer.toString(mes[2]) + "%");;
                     }
                     break;

                 case 7 :
                     if (i == 0) {
                         ivFeeling1.setImageResource(R.drawable.surprised);
                         tvFeeling1.setText("놀람 " +Integer.toString(mes[0]) + "%");
                     } else if (i == 1) {
                         ivFeeling2.setImageResource(R.drawable.surprised);
                         tvFeeling2.setText("놀람 " +Integer.toString(mes[1]) + "%");
                     } else {
                         ivFeeling3.setImageResource(R.drawable.surprised);
                         tvFeeling3.setText("놀람  " +Integer.toString(mes[2] ) + "%");
                     }
                     break;
             }

         }
    }
    public void settingHighestFeeling(String feeling, String name) {
         String feelingresult = name + "님 ";
         int feel_code = -1;
         if (feel1 != null && feel1.equals("happy")) { feel_code = 1;}
         else if(feel1 != null && feel1.equals("sad")) { feel_code = 2;}
         else if(feel1 != null && feel1.equals("neutral")) { feel_code = 3;}
         else if(feel1 != null && feel1.equals("fearful")) { feel_code = 4;}
         else if(feel1 != null && feel1.equals("disgusted")) { feel_code = 5;}
         else if(feel1 != null && feel1.equals("angry")) { feel_code = 6;}
         else if(feel1 != null && feel1.equals("surprised")) { feel_code = 7;}
         else {
//             감정이 감지되지 않은 경우 neutral 기본
             feel_code = 3;
         }
        switch (feel_code) {
            case 1:
                feelingresult += "항상 밝은 모습으로 도원결의를 찾아주셔서 감사합니다.\n 오늘 하루도 행복한 하루가 되길 도원결의가 응원합니다.";
                tvFeelingSummery.setText(feelingresult);
                break;
            case 2:
                feelingresult = "무슨 일 있으세요? 도원결의가 " + name + "님의 슬픔을 감지했습니다.";
                tvFeelingSummery.setText(feelingresult);
                break;
            case 3:
                feelingresult = "많이 지치셨나요? 오늘따라 힘이 없어보이네요.\n"+name+"님이 기운찬 하루를 보낼 있도록 도원결의가 응원합니다.";
                tvFeelingSummery.setText(feelingresult);
                break;

            case 4:
                feelingresult = "무슨 일 있으세요? 도원결의가 " + name + "님의 두려움을 감지했습니다.";
                tvFeelingSummery.setText(feelingresult);
                break;

            case 5:
                feelingresult = "무슨 일 있으세요? 도원결의가 " + name + "님의 역겨움을 감지했습니다.";
                tvFeelingSummery.setText(feelingresult);
                break;

            case 6:
                feelingresult = "무슨 일 있으세요? 도원결의가 " + name + "님의 분노를 감지했습니다.";
                tvFeelingSummery.setText(feelingresult);
                break;

            case 7:
                feelingresult = "무슨 일 있으세요? 도원결의가 " + name + "님의 놀람을 감지했습니다.";
                tvFeelingSummery.setText(feelingresult);
                break;
        }
    }
}