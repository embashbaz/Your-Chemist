package com.example.yourchemist.Chemist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourchemist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.text.TextUtils.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText mEmail, mPassword;
    private Button signIn;
    private TextView register, resetPassword, resendVerificationEmail;
    private NavController navController;
    private  String TAG ="fragement Login";

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((ChemistAuth)getActivity()).setActionBarTitle("Login");
        // Inflate the layout for this fragment
        authneticate();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        signIn =  view.findViewById(R.id.email_sign_in_button);
        register = view.findViewById(R.id.link_register);
        resendVerificationEmail = view.findViewById(R.id.resend_verification_email);
        resetPassword = view.findViewById(R.id.forgot_password);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if the fields are filled out
                if (!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmail.getText().toString(),
                            mPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        authneticate();
                                    }else {
                                       Toast.makeText(getActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show();

                        }
                    });

                }else{
                    Toast.makeText(getActivity(), "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    navController.navigate(R.id.action_login2_to_register);
            }
        });

        resendVerificationEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendVerificationEmailFragment();
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswordFragment();
            }
        });
    }
public void authneticate(){
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if(user != null){
        if(user.isEmailVerified()){
        Intent intent = new Intent(getActivity(), ChemistInfo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("sender", 4);
        startActivity(intent);
        getActivity().finish();
        }else{
            Toast.makeText(getActivity(), "Email is not Verified\nCheck your Inbox", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
        }
    }
}

    private void resendVerificationEmailFragment() {
        ResentVerificationEmailDialog resentVerificationEmailDialog = new ResentVerificationEmailDialog(getActivity());
        resentVerificationEmailDialog.setTargetFragment(this,0);
        resentVerificationEmailDialog.show(getFragmentManager(), "Resend Verification Email");
    }

    private void resetPasswordFragment() {
        ResetPasswordDialog resetPasswordDialog = new ResetPasswordDialog(getActivity());
        resetPasswordDialog.setTargetFragment(this,0);
        resetPasswordDialog.show(getFragmentManager(), "Reset Password");
    }



}
