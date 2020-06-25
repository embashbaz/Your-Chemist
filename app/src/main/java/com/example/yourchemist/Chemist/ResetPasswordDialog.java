package com.example.yourchemist.Chemist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.yourchemist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.text.TextUtils.isEmpty;

public class ResetPasswordDialog extends AppCompatDialogFragment {

    EditText emailEt;
    Activity activity;

    public ResetPasswordDialog(Activity activity) {
       this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.resey_password, null);
        emailEt = view.findViewById(R.id.reset_password_email);
        builder.setView(view)
                .setTitle("Reset Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Send Reset Link", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!isEmpty(emailEt.getText().toString()))
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailEt.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(activity, "Password Reset Link Sent to Email",
                                            Toast.LENGTH_SHORT).show();
                                }else{

                                    Toast.makeText(activity, "No User is Associated with that Email",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                else
                    Toast.makeText(activity, "Email field can not be empty",
                            Toast.LENGTH_SHORT).show();

            }
        });


        return builder.create();
    }
}
