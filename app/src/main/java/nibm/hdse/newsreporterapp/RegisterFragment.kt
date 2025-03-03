package nibm.hdse.newsreporterapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.util.Patterns

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        val etFullName = view.findViewById<EditText>(R.id.etFullName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val spinnerRole = view.findViewById<Spinner>(R.id.spinnerRole)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        // Set up role spinner
        val roles = arrayOf("Reporter", "Editor")
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, roles)
        spinnerRole.adapter = adapter

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val role = spinnerRole.selectedItem.toString()

            // Validate fields
            if (fullName.isEmpty()) {
                Toast.makeText(requireContext(), "Full Name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || !isValidEmail(email)) {
                Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(requireContext(), "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (role == "Select Role") {
                Toast.makeText(requireContext(), "Please select a role", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register user in Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        saveUserToDatabase(userId, fullName, email, role)
                    } else {
                        Toast.makeText(requireContext(), "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        return view
    }

    // Function to validate email
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveUserToDatabase(userId: String?, fullName: String, email: String, role: String) {
        if (userId == null) return
        Log.d("Firebase", "Saving user: $fullName, $email, $role")

        val userMap = mapOf(
            "fullName" to fullName,
            "email" to email,
            "role" to role
        )


        val database = FirebaseDatabase.getInstance("https://news-reporter-app-58bdb-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

            .child(userId)
            .setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "User saved successfully")
                    Toast.makeText(requireContext(), "User registered successfully!", Toast.LENGTH_SHORT).show()
                    (requireActivity() as LoginActivity).loadFragment(LoginFragment())
                } else {
                    Log.e("Firebase", "Failed to save user: ${task.exception?.message}")
                    Toast.makeText(requireContext(), "Failed to save user data", Toast.LENGTH_SHORT).show()
                }
            }

    }

}
