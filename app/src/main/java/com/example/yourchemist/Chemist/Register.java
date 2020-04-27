package com.example.yourchemist.Chemist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yourchemist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.text.TextUtils.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    private EditText mEmail, mPassword, mConfirmPassword;
    private Button mRegister;
    public static final String TAG ="Register fragment ";

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((ChemistAuth)getActivity()).setActionBarTitle("Register");
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mEmail =  view.findViewById(R.id.input_email);
        mPassword =  view.findViewById(R.id.input_password);
        mConfirmPassword =  view.findViewById(R.id.input_confirm_password);
        mRegister =  view.findViewById(R.id.btn_register);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())){


                    //check if passwords match
                    if(doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())){

                        //Initiate registration task
                        registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString());
                    }else{
                        Toast.makeText(getActivity(), "Passwords do not Match", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(getActivity(), "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }

    public void registerNewEmail(final String email, String password){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){

                            //sendVerificationEmail();
                            setInfo();

                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Unable to Register",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    //This method will be implement later
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(), "Couldn't  Send Verification Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    private void setInfo(){

        Intent intent = new Intent(getActivity(), ChemistInfo.class);
        intent.putExtra("sender", 2);
        startActivity(intent);
        getActivity().finish();
    }
}
