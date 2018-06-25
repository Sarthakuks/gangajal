package gangajal.app.project.uks.gangajal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import gangajal.app.project.uks.gangajal.R;
import gangajal.app.project.uks.gangajal.Utils.Connect;
import gangajal.app.project.uks.gangajal.Utils.DialogUtil;
import gangajal.app.project.uks.gangajal.Utils.Tags;
import gangajal.app.project.uks.gangajal.Utils.UrlUtils;

public class OrderActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    private Button btnOrder;
    private ImageView ivBack;
    private Spinner spinner;
    private TextView tvHeader, tvPleadge, tvTerm;
    private EditText etName, etContact, etAddress, etEmail;
    private String[] name, zoneId;
    private CheckBox cbPledge, cbTerm;
    private ArrayList<String> name1, zoneId1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        findViews();
        initialization();
        handleListeners();
        callZoneListApi();

    }

    private void initialization() {
        tvHeader.setText(getString(R.string.order));
      /*  String[] stringYear = {"Select Zone","North Delhi", "South Delhi", "East Delhi", "West Delhi", "Central Delhi"};
        setSpinnerValues(spinner, stringYear);*/
    }

    private void handleListeners() {
        btnOrder.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        tvPleadge.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
        cbPledge.setOnCheckedChangeListener(this);
        cbTerm.setOnCheckedChangeListener(this);
    }

    private void findViews() {
        btnOrder = findViewById(R.id.btn_order);
        tvHeader = findViewById(R.id.tv_header);
        ivBack = findViewById(R.id.iv_back);
        etName = findViewById(R.id.et_name);
        etContact = findViewById(R.id.et_contact_no);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        spinner = findViewById(R.id.spinner);
        tvPleadge = findViewById(R.id.tv_pledge);
        tvTerm = findViewById(R.id.tv_term);
        cbPledge = findViewById(R.id.cb_pledge);
        cbTerm = findViewById(R.id.cb_term);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_order:
                if (validate()) {
                    callAddOrderApi();
                }
                break;
            case R.id.tv_pledge:
                Intent intent = new Intent(OrderActivity.this, PledgeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Tags.TITLE, getString(R.string.pledge));
                bundle.putString(Tags.WEB_URL, UrlUtils.PLEDGE);
                intent.putExtras(bundle);
                OrderActivity.this.startActivity(intent);

                break;
            case R.id.tv_term:
                intent = new Intent(OrderActivity.this, WebViewActivity.class);
                bundle = new Bundle();
                bundle.putString(Tags.TITLE, getString(R.string.term_and_condition));
                bundle.putString(Tags.WEB_URL, UrlUtils.TERM);
                intent.putExtras(bundle);
                OrderActivity.this.startActivity(intent);
                break;

        }
    }

    public void setSpinnerValues(Spinner spinner, String[] stringArray) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void callAddOrderApi() {
        final ProgressDialog dialog = DialogUtil.getProgressDialog(OrderActivity.this);
        dialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", etName.getText().toString());
        hashMap.put("phone", etContact.getText().toString());
        hashMap.put("email", etEmail.getText().toString());
        hashMap.put("address", etAddress.getText().toString());
        hashMap.put("zone", spinner.getSelectedItem().toString());
        Connect.getInstance(OrderActivity.this).doNetworkRequest(Connect.POST, UrlUtils.ADD_ORDER, hashMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                try {
                    DialogUtil.hideProgressDialog(dialog);
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String status = jsonObject.optString("status");
                    if (status.equals("true")) {
                        JSONObject jsonObjectResponce = jsonObject.optJSONObject("response");
                        if (jsonObjectResponce.optString("plegde").equals("new")) {
                            Toast.makeText(OrderActivity.this, jsonObjectResponce.getString("msg"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(OrderActivity.this, PledgeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(Tags.TITLE, getString(R.string.pledge));
                            bundle.putString(Tags.WEB_URL, UrlUtils.PLEDGE);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(OrderActivity.this, jsonObjectResponce.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        JSONObject jsonObjectResponce = jsonObject.optJSONObject("response");
                        Toast.makeText(OrderActivity.this, jsonObjectResponce.getString("msg"), Toast.LENGTH_LONG).show();
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


    private void callZoneListApi() {
        final ProgressDialog dialog = DialogUtil.getProgressDialog(OrderActivity.this);
        dialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        Connect.getInstance(OrderActivity.this).doNetworkRequest(Connect.POST, UrlUtils.ZONE_LIST, hashMap, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    DialogUtil.hideProgressDialog(dialog);
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String status = jsonObject.optString("status");
                    if (status.equals("true")) {
                        JSONArray jsonObjectResponce = jsonObject.optJSONArray("response");
                        JSONObject jsonObj;
                        if (jsonObjectResponce != null) {
                            zoneId1 = new ArrayList<>();
                            name1 = new ArrayList<>();
                            zoneId = new String[jsonObjectResponce.length()];
                            name = new String[jsonObjectResponce.length()];
                            for (int j = 0; j < jsonObjectResponce.length(); j++) {
                                jsonObj = jsonObjectResponce.optJSONObject(j);
                                if (jsonObj != null) {
                                    zoneId[j] = jsonObj.optString("zone_id");
                                    name[j] = jsonObj.optString("name");
                                    name1.add(jsonObj.optString("name"));
                                    zoneId1.add(jsonObj.optString("zone_id"));
                                }
                            }
                            setSpinnerValues(spinner, name);
                        }


                    } else {
                        Toast.makeText(OrderActivity.this, jsonObject.optString("msg"), Toast.LENGTH_LONG).show();
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
        if (!cbPledge.isChecked()) {
            Toast.makeText(OrderActivity.this, "Please Pledge for CLean Ganga", Toast.LENGTH_LONG).show();
            return false;
        } else if (etName.getText().toString().isEmpty()) {
            etName.setError("Please Enter Name");
            etName.requestFocus();
            return false;
        } else if (etContact.getText().toString().isEmpty()) {
            etContact.setError("Please Enter Number");
            etContact.requestFocus();
            return false;
        } else if (etContact.getText().toString().contains(" ")) {
            etContact.setError("No Space Allowed ");
            etContact.requestFocus();
            return false;
        } else if (etContact.getText().toString().trim().length() != 10) {
            etContact.setError("Please Enter Valid Mobile Number");
            etContact.requestFocus();
            return false;
        } else if (etEmail.getText().toString().isEmpty() || etEmail.getText().toString().equals("") || !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError("Please Enter Valid EmailId");
            etEmail.requestFocus();
            return false;
        } else if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("Please Enter Address");
            etAddress.requestFocus();
            return false;
        } else if (spinner != null && spinner.getSelectedItem().toString().equals("Select Zone")) {
            Toast.makeText(OrderActivity.this, "Please Select Zone", Toast.LENGTH_LONG).show();
            return false;
        } else if (!cbTerm.isChecked()) {
            Toast.makeText(OrderActivity.this, "Please Accept Term And Conditions", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
