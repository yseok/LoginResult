package com.yuseok.android.loginresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import static com.yuseok.android.loginresult.R.id.editEmail;
import static com.yuseok.android.loginresult.R.id.loginButton;

public class MainActivity extends AppCompatActivity {

    // 자동로그인소스
    private CallbackManager callbackManager;
    private final String TAG = "MainActivity";

    // Facebook Login Button
    LoginButton faceLogin;


    EditText editId, editPw;
    Button btnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = (EditText) findViewById(editEmail);
        editPw = (EditText) findViewById(R.id.editPw);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        callbackManager = CallbackManager.Factory.create();

        faceLogin = (LoginButton) findViewById(loginButton);

        faceLogin.setReadPermissions(Arrays.asList("public_profile", "email"));
        faceLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // 사용자 정보 획득
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {


                        Log.v("result",object.toString());
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + object.toString());
                            Log.i("TAG", "AccessToken: " + loginResult.getAccessToken().getToken());
//                            setResult(RESULT_OK);
                            JSONObject jsonObject = new JSONObject();
                            String resultId = jsonObject.getString("id");
                            String resultPassword = jsonObject.getString("password");
//                            tv_parsing.setText(resultId+"\n"+resultPassword);



                        }

                        Intent intent=new Intent(MainActivity.this,InappActivity.class);
//                        String[] userString = new String[] {object.toString()};
                        intent.putExtra("user", object.toString());
                        startActivityForResult(intent, RESULT_OK);
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
                Log.e("LoginErr",error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //패이스북 로그인 결과를 콜백에 담는다.
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


//    // 페이스북 로그인
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public void facebookLoginOnClick(View v) {
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//
//        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email"));
//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//
//            @Override
//            public void onSuccess(final LoginResult result) {
//
//                GraphRequest request;
//                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//
//                    @Override
//                    public void onCompleted(JSONObject user, GraphResponse response) {
//                        if (response.getError() != null) {
//
//                        } else {
//                            Log.i("TAG", "user: " + user.toString());
//                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
//                            setResult(RESULT_OK);
//
//                            Intent intent=new Intent(MainActivity.this,InappActivity.class);
//                            String[] userString = new String[] {user.toString()};
//                            intent.putExtra("user", userString);
//                            startActivity(intent);
//                        }
//                    }
//                });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,email,gender,birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.e("test", "Error: " + error);
//                //finish();
//            }
//
//            @Override
//            public void onCancel() {
//                //finish();
//            }
//        });
//
//        LoginManager.getInstance().logOut();
//    }
}
