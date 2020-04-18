package com.example.yourchemist.Chemist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.yourchemist.R;

public class ChemistInfo extends AppCompatActivity {

    EditText chemistNameEt, licenseEt, document2Et, phoneEt, emailEt, townEt, areaEt,
              adressEt, areaCodeEt, shopDetailsEt;
    Button saveBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemist_info);
        chemistNameEt =findViewById(R.id.name_chemist_et);
        licenseEt = findViewById(R.id.license_et);
        document2Et = findViewById(R.id.document2_et);
        phoneEt = findViewById(R.id.phone_number_et);
        emailEt = findViewById(R.id.email_et);
        townEt = findViewById(R.id.town_et);
        areaCodeEt = findViewById(R.id.area_code_et);
        areaEt = findViewById(R.id.area_et);
        adressEt = findViewById(R.id.adress_et);
        shopDetailsEt = findViewById(R.id.shop_details_et);
        saveBt = findViewById(R.id.save_chemist);

    }
}
