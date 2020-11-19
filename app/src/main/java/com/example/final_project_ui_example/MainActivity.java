package com.example.final_project_ui_example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public final static int LOGIN_ACTIVITY = 100;
    public final static int START_ACTIVITY = 200 ;
    public final static int REGISTER_ACTIVITY = 300;
    public final static int START_ACTIVITY2 = 400 ;
    private MyDBHelper myDBHelper;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat nowTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    // nowDate 변수에 값을 저장한다.
    String now_Time = nowTime.format(date);

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLetslogin :
//                Intent login = new Intent(MainActivity.this, LoginActivity.class);
//                 startActivityForResult(login, LOGIN_ACTIVITY);

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("개인정보 수집 동의서"); //제목
                dlg.setMessage("◆수집항목◆ : 성명, 전화번호, 얼굴 정보 \n ◆수집 및 이용 목적◆\n - Facein 인증 진행 \n - 사용자 사진의 스푸핑 및 본인 식별 \n - 사용자 자원 관리 \n" +
                        "◆수집 기간◆\n" +
                        "위 개인정보는 수집‧이용에 관한 동의일로부터 보유목적 달성 시 또는 정보주체가 개인정보 삭제를 요청할 경우 지체 없이 파기합니다.\n" +
                        "단, 사업 종료일 후에는 향후 정부지원사업 신청 시의 이력관리만을 위하여 보유‧이용되며 기간은 " +
                        "1년입니다.(공공기록물 관리에 관한 법률 시행령)\n" +
                        "◆동의를 거부할 권리 및 동의를 거부할 경우의 불이익◆\n" +
                        "위 개인정보 중 필수항목의 수집‧이용에 관한 동의는 인증 수행을 위해 필수적이므로 이에 동의하셔야 이후 절차를 진행할 수 있습니다. \n " +
                        "다만 동의하지 않으시는 경우 본 어플 인증이 불가합니다."); // 메시지
                dlg.setIcon(R.drawable.document); // 아이콘 설정
//                버튼 클릭시 동작
                dlg.setPositiveButton("동의",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(MainActivity.this,now_Time+"\n 동의하셨습니다.", Toast.LENGTH_SHORT).show();

                        Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivityForResult(register, REGISTER_ACTIVITY);
                    }
                });
                dlg.setNegativeButton("거절", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"거절하셨습니다. \n Facein 회원가입이 어렵습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();


                break;
            case R.id.btnStart:
                Intent Start = new Intent(MainActivity.this, PhotoActivity.class);
                startActivityForResult(Start, START_ACTIVITY);
                break;
//            case R.id.btn_if_login:
//                Intent iflogin = new Intent(MainActivity.this, IfLogin.class);
//                startActivityForResult(iflogin, START_ACTIVITY2);
//                break;
        }
    }

    void dialogs() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_ACTIVITY) {
            switch (resultCode) {
                case 1000:
                    String name = data.getExtras().getString("name");
                    String phone = data.getExtras().getString("phone");
                    Log.d("ONAVTIVITY", "name "+ name+ " phone "+ phone);
                    Toast.makeText(MainActivity.this, "추가된 사용자의\n 이름 : "+ name + "\n 전화번호 : "+ phone, Toast.LENGTH_LONG).show();
            }
        }
    }
}