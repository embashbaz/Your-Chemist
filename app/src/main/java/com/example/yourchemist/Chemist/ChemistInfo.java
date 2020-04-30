package com.example.yourchemist.Chemist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yourchemist.AdapterAndModel.Chemist;
import com.example.yourchemist.AdapterAndModel.SpinnerValue;
import com.example.yourchemist.MainActivity;
import com.example.yourchemist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.text.TextUtils.isEmpty;

public class ChemistInfo extends AppCompatActivity {

    EditText chemistNameEt, licenseEt, document2Et, phoneEt, emailEt, townEt, areaEt,
              adressEt, areaCodeEt, shopDetailsEt;
    Spinner countrySpinner;
    Button saveBt;


    String chemistName, license, document2, phone, email, town, area,country,
            adress, areaCode, shopDetails;

    String uid;
    FirebaseFirestore db;
    int code = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        uid = user.getUid();
        code = getIntent().getIntExtra("sender", 1);
        db = FirebaseFirestore.getInstance();


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
        countrySpinner = findViewById(R.id.country_spinner);
        saveBt = findViewById(R.id.save_chemist);

        if(uid != null && code == 4){
            getDataFromDb();
        }
        else if(uid == null || code == 1){
            Intent intent = new Intent(this, ChemistAuth.class);
            Toast.makeText(this, "please login again", Toast.LENGTH_SHORT ).show();
            startActivity(intent);
            finish();
        }
        else  if(uid != null && code == 2){
            saveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData();
                }
            });
        }
        else if(uid != null && code == 3){
            getDataFromDb();
            saveBt.setText("Update");
            saveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData();
                }
            });
        }



        SpinnerValue values = new SpinnerValue();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, values.spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);



    }

    private void getDataFromDb() {
         db.collection("pharmacies").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    //DocumentSnapshot doc = task.getResult();
                    if(task.getResult().exists()) {
                        goToChemistActivity();
                    }else {

                        saveBt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                setData();
                            }
                        });

                    }
                }else {

                }

            }
        });

    }

    private void showData(Chemist chemist){
        chemistNameEt.setText(chemist.getName());
        licenseEt.setText(chemist.getLicense());
        document2Et.setText(chemist.getDocument());
        phoneEt.setText(chemist.getPhone());
        emailEt.setText(chemist.getEmail());
        townEt.setText(chemist.getTown());
        areaCodeEt.setText(chemist.getAreaCode());
        areaEt.setText(chemist.getArea());
        adressEt.setText(chemist.getAdress());
        shopDetailsEt.setText(chemist.getDetails());

    }


    private void getData(){
            emailEt.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            chemistName = chemistNameEt.getText().toString().trim().toLowerCase();
            license = licenseEt.getText().toString();
            document2 = document2Et.getText().toString();
            phone = phoneEt.getText().toString();
            town = townEt.getText().toString().trim().toLowerCase();
            areaCode = areaCodeEt.getText().toString();
            area = areaEt.getText().toString();
            adress = adressEt.getText().toString();
            shopDetails = shopDetailsEt.getText().toString();
            country = countrySpinner.getSelectedItem().toString();

    }

    private void setData(){
        getData();
        if(!isEmpty(email) && !isEmpty(chemistName) && !isEmpty(license) && !isEmpty(phone) && !isEmpty(town)
                && !isEmpty(areaCode) && !isEmpty(area) && !isEmpty(adress) && !isEmpty(country)){

            Chemist mChemist = new Chemist(chemistName, license, document2,phone, email,
                    country,town, area,adress, areaCode, shopDetails,uid);
            db.collection("pharmacies").document(uid).set(mChemist).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Something went wrong, try again later, or check " +
                            "your internet connection ", Toast.LENGTH_SHORT).show();
                }
            });
            goToChemistActivity();


        }else{
            Toast.makeText(this, "Please make sure you fill all the field, " +
                    "2nd document and more details field are optional", Toast.LENGTH_LONG).show();
        }
    }

    private void goToChemistActivity(){
        Intent intent = new Intent(this, ChemistActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chemist_info_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_info:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

        }



        return super.onOptionsItemSelected(item);
    }
}
