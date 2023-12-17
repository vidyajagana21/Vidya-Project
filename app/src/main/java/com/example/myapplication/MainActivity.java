
package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText Name, signupEmail, signupPassword, Profession, Location, Phone;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        Name = findViewById(R.id.Name_Txt);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        Profession = findViewById(R.id.Profession_Txt);
        Location = findViewById(R.id.Location_Txt);
        Phone = findViewById(R.id.phone_Txt);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String name = Name.getText().toString().trim();
                String prof = Profession.getText().toString().trim();
                String loc = Location.getText().toString().trim();
                String phno = Phone.getText().toString().trim();

                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                    return;
                } else if (!isValidEmail(user)) {
                    signupEmail.setError("Invalid Email Address. Please enter a valid email address.");
                    return;
                }
                if (pass.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                    return;
                } else if (!isValidPassword(pass)) {
                    signupPassword.setError("Invalid Password. It must contain at least 8 characters, including one uppercase letter, one lowercase letter, and one digit.");
                    return;
                }
                if (name.isEmpty()) {
                    Name.setError("Name cannot be empty");
                    return;
                }
                if (prof.isEmpty()) {
                    Profession.setError("Profession cannot be empty.");
                    return;
                } else if (!isValidProfession(prof)) {
                    Profession.setError("Invalid Profession. Please enter a valid profession.");
                    return;
                }

                if (loc.isEmpty()) {
                    Location.setError("Location cannot be empty.");
                    return;
                } else if (!isValidLocation(loc)) {
                    Location.setError("Invalid Location. Please enter a valid location.");
                    return;
                }

                if (phno.isEmpty()) {
                    Phone.setError("Phone Number cannot be empty.");
                    return;
                } else if (!isValidPhone(phno)) {
                    Phone.setError("Invalid Phone Number. It must be 10 digits.");
                    return;
                }
                auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, login.class));
                        } else {
                            Toast.makeText(MainActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // Validate name
    private boolean isValidName(String name) {
        String namePattern = "^[A-Za-z ]+$";
        return name.matches(namePattern);
    }
    // Validate profession
    private boolean isValidProfession(String profession) {
        String professionPattern = "^[A-Za-z0-9 -]+$";
        return profession.matches(professionPattern);
    }
    // Validate location
    private boolean isValidLocation(String location) {
        // You can customize the pattern based on your requirements
        // Here, it allows letters, digits, spaces, and some special characters
        String locationPattern = "^[A-Za-z0-9 .,()-]+$";
        return location.matches(locationPattern);
    }
    // Validate phone number
    private boolean isValidPhone(String phone) {
        // Assumes a 10-digit phone number
        String phonePattern = "^[0-9]{10}$";
        return phone.matches(phonePattern);
    }
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(passwordPattern);
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailPattern);
    }
}
