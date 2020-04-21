package com.example.yourchemist.Chemist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yourchemist.R;
import com.google.firebase.auth.FirebaseAuth;

import static android.text.TextUtils.isEmpty;

public class ChemistInfo extends AppCompatActivity {

    EditText chemistNameEt, licenseEt, document2Et, phoneEt, emailEt, townEt, areaEt,
              adressEt, areaCodeEt, shopDetailsEt;
    Button saveBt;

    String chemistName, license, document2, phone, email, town, area,
            adress, areaCode, shopDetails;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(this, ChemistAuth.class);
            Toast.makeText(this, "please login again", Toast.LENGTH_SHORT );
            startActivity(intent);
            finish();
        }

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

        uid = FirebaseAuth.getInstance().getUid();



    }

    private void getData(){
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            chemistName = chemistNameEt.getText().toString();
            license = licenseEt.getText().toString();
            document2 = document2Et.getText().toString();
            phone = phoneEt.getText().toString();
            town = townEt.getText().toString();
            areaCode = areaCodeEt.getText().toString();
            area = areaEt.getText().toString();
            adress = adressEt.getText().toString();
            shopDetails = shopDetailsEt.getText().toString();






    }

    private void setData(){
        if(!isEmpty(email) && !isEmpty(chemistName) && !isEmpty(license) && !isEmpty(phone) && !isEmpty(town)
                && !isEmpty(areaCode) && !isEmpty(area) && !isEmpty(adress)){



        }else{
            Toast.makeText(this, "Please make sure you fill all the field" +
                    "2nd document and more details field are optional", Toast.LENGTH_LONG);
        }
    }

    private void goToChemistActivity(){

    }
}
