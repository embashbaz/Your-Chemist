package com.example.yourchemist.Chemist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.yourchemist.AdapterAndModel.FirebaseUtil;
import com.example.yourchemist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import static android.text.TextUtils.isEmpty;

public class ResentVerificationEmailDialog extends AppCompatDialogFragment {

    private FirebaseUtil firebaseUtil = new FirebaseUtil();
    private EditText emailEt, passwordEt;
    private Activity activity;

    public ResentVerificationEmailDialog(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.resend_email_verification, null);
        emailEt = view.findViewById(R.id.confirm_email);
        passwordEt = view.findViewById(R.id.confirm_password);
        builder.setView(view)
                .setTitle("Resend verification Email")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!isEmpty(emailEt.getText().toString())
                        && !isEmpty(emailEt.getText().toString()))
                    authenticate(emailEt.getText().toString(), passwordEt.getText().toString());
                else
                    Toast.makeText(activity, "Please fill all the fields", Toast.LENGTH_LONG).show();
            }
        });


        return builder.create();
    }

    private void authenticate(String email, String password){
        AuthCredential authCredential = EmailAuthProvider
                .getCredential(email, password);
        FirebaseAuth.getInstance().signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseUtil.sendVerificationEmail(activity);
                            FirebaseAuth.getInstance().signOut();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Invalid Credentials. \nReset your password and try again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
