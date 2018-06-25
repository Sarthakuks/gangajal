package gangajal.app.project.uks.gangajal.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import gangajal.app.project.uks.gangajal.R;
import gangajal.app.project.uks.gangajal.Utils.Connect;
import gangajal.app.project.uks.gangajal.Utils.DialogUtil;
import gangajal.app.project.uks.gangajal.Utils.UrlUtils;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private Button btnSubmit;
    private ImageView ivBack;
    private TextView tvHeader;
    private EditText etName, etMobileNo, etComment;
    private RatingBar pbRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        findViews();
        handleListeners();
        initialization();
    }

    private void initialization() {
        tvHeader.setText(getString(R.string.feedback));
    }

    private void handleListeners() {
        btnSubmit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void findViews() {
        btnSubmit = findViewById(R.id.btn_submit);
        ivBack = findViewById(R.id.iv_back);
        tvHeader = findViewById(R.id.tv_header);
        pbRating = findViewById(R.id.pb_rating);
        etMobileNo = findViewById(R.id.et_contact_no);
        etComment = findViewById(R.id.et_comment);
        etName = findViewById(R.id.et_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                if (validate()) {
                    callAddFeedbackApi();
                }
                break;
        }
    }

    private void callAddFeedbackApi() {
        final ProgressDialog dialog = DialogUtil.getProgressDialog(FeedbackActivity.this);
        dialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", etName.getText().toString());
        hashMap.put("phone", etMobileNo.getText().toString());
        hashMap.put("comment", etComment.getText().toString());
        hashMap.put("rating", String.valueOf(pbRating.getRating()));
        Connect.getInstance(FeedbackActivity.this).doNetworkRequest(Connect.POST, UrlUtils.ADD_FEEDBACK, hashMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                try {
                    DialogUtil.hideProgressDialog(dialog);
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String status = jsonObject.optString("status");
                    if (status.equals("true")) {
                        JSONObject jsonObjectResponce = jsonObject.optJSONObject("response");
                        Toast.makeText(FeedbackActivity.this, jsonObjectResponce.getString("msg"), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(FeedbackActivity.this, jsonObject.optString("msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private boolean validate() {
        if (etName.getText().toString().isEmpty()) {
            etName.setError("Please Enter Name");
            etName.requestFocus();
            return false;
        } else if (etMobileNo.getText().toString().isEmpty()) {
            etMobileNo.setError("Please Enter Number");
            etMobileNo.requestFocus();
            return false;
        }else if (etMobileNo.getText().toString().contains(" ")) {
            etMobileNo.setError("No Space Allowed ");
            etMobileNo.requestFocus();
            return false;
        } else if (etMobileNo.getText().toString().trim().length() != 10) {
            etMobileNo.setError("Please Enter Valid Mobile Number");
            etMobileNo.requestFocus();
            return false;
        }  else if (etComment.getText().toString().isEmpty()) {
            etComment.setError("Please Give Feedback");
            etComment.requestFocus();
            return false;
        } else if (pbRating.getRating() == 0.0F) {
            Toast.makeText(FeedbackActivity.this, "Please Give Rating", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;

    }

}
