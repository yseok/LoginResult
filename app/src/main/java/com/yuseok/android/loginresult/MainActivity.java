package com.yuseok.android.loginresult;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import static com.yuseok.android.loginresult.R.id.editEmail;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // 자동로그인소스
    private CallbackManager callbackManager;
    private final String TAG = "MainActivity";


    // Facebook Login Button
//    LoginButton faceLogin;
    Button faceLogin;

    // 이메일
    EditText editId, editPw;
    Button btnLogin, btnSignup;


    String id;

    // 자동 로그인 체크박스
    CheckBox autoLogin;
    // 로그인 체크 예/아니오 확인
    Boolean loginChecked;

    // 로그인 값 저장
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String sfValue;
    String sfPw;
    String sfFace;

    String str, str2, str3;

    JSONObject object;

    View v;

    String faceResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = (EditText) findViewById(editEmail);
        editPw = (EditText) findViewById(R.id.editPw);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        autoLogin = (CheckBox) findViewById(R.id.checkBox);

        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        pref = getSharedPreferences(sfValue, 0);
        str = pref.getString("Id", ""); // 키값으로 꺼냄
        str2 = pref.getString("Pw", "");
        str3 = pref.getString("Face", "");
        editId.setText(str);// editId에 반영함
        editPw.setText(str2);

//        faceLogin.setText(str3);

        if (pref.getString("Id", "").equals("w") && pref.getString("Pw", "").equals("11")) {
            Toast.makeText(MainActivity.this, "같습니다.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, InappActivity.class);
            startActivity(i);

//            finish();
        } else if (!pref.getString("Id", "").equals("w") && pref.getString("Pw", "").equals("11")) {

            Toast.makeText(MainActivity.this, "로그인을 해주세요.", Toast.LENGTH_SHORT).show();

            if (pref.getString("Face", "").equals("-1")) {
//            intent = new Intent(MainActivity.this, InappActivity.class);
//            startActivity(intent);
                Log.i("결과값이 맞나", faceResult + "");
                Toast.makeText(MainActivity.this, "페이스북 로그인이 없습니다", Toast.LENGTH_SHORT).show();
//                faceLogin(object);

            }

        }
    }


        // 페이스북 로그인
//        callbackManager = CallbackManager.Factory.create();

//        faceLogin = (LoginButton) findViewById(loginButton);

        /*
        //1차 로그인 버튼
        faceLogin.setReadPermissions(Arrays.asList("public_profile", "email"));
        faceLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                // 사용자 정보 획득
                final GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.v("result", object.toString());
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + object.toString());
                            Log.i("TAG", "AccessToken: " + loginResult.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            id = object.toString();
                            Log.i("id의 값은====", id);
                        }

                        faceLogin(object);

                    }
                });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr", error.toString());
            }

        });
    }
    */
//
//    private void Login() {
//        mFacebook.authorize2(this, new String[] { "publish_stream, user_photos, email" }, new AuthorizeListener());
//        }
//
//    private void Logout() {
//
//       try {
//           mFacebook.logout(this);
//       } catch (Exception e) {
//        }
//     }




//    private void setAppPreferences(Activity context, String key, String value) {
//        SharedPreferences pref = null;
//        pref = context.getSharedPreferences("FacebookExample", 0);
//        SharedPreferences.Editor prefEditor = pref.edit();
//        Log.d("LOG", "key = " + key + " // value = " + value);
//        prefEditor.putString(key, value);
//
//        prefEditor.commit();
//    }
//
//    private String getAppPreferences(Activity context, String key) {
//        String returnValue = null;
//        SharedPreferences pref = null;
//        pref = context.getSharedPreferences("FaceBookExample", 0);
//
//        returnValue = pref.getString(key, "");
//
//        return returnValue;
//
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //패이스북 로그인 결과를 콜백에 담는다.
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.i("resultCode=========", resultCode+"");

//        faceResult = resultCode+"";
//        Log.i("다른 결과값=========", faceResult);

    }

    public void facebookLoginOnClick(View v){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + object.toString());
                            Log.i("TAG", "AccessToken: " + loginResult.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            faceLogin(object);
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // SharedPreferences 에 설정값(특별히 기억해야할 사용자 값)을 저장하기
        SharedPreferences sf = getSharedPreferences(sfValue, 0);
        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
        String str = editId.getText().toString(); // 사용자가 입력한 값
        String str2 = editPw.getText().toString();
//        String str3 = faceResult.toString();
//        Log.i("faceLogin값은====", faceResult.toString() );
        editor.putString("Id", str); // 입력
        editor.putString("Pw", str2); // 입력
        editor.putString("Face", str3);
//        Log.i("str3값은====", str3);
        editor.commit(); // 파일에 최종 반영함
    }


    // 로그인과 회원가입을 눌렀을 경우
    Intent intent;
    @Override
    public void onClick(View v) {
        // InappActivity로 넘겨주기
        switch (v.getId()) {
            case R.id.btnLogin:
                    Intent i = new Intent(MainActivity.this, InappActivity.class);
                    startActivity(i);
                    break;
            // 회원가입창으로 넘겨주기
            case R.id.btnSignup:
                intent = new Intent(getApplicationContext(),RegistActivity.class);
                startActivity(intent);
        }

    }

    public void faceLogin(JSONObject object) {

        intent = new Intent(MainActivity.this, InappActivity.class);
        intent.putExtra("user", object.toString());
//                        intent.putExtra("id", resultId.toString());
        startActivityForResult(intent, RESULT_OK);

    }



}