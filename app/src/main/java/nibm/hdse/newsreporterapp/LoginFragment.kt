package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val spinnerRole = view.findViewById<Spinner>(R.id.spinnerRole)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        // Set up role spinner
        val roles = arrayOf("Reporter", "Editor")
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, roles)
        spinnerRole.adapter = adapter

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val selectedRole = spinnerRole.selectedItem.toString()

            if (email.isEmpty()) {
                etEmail.error = "Email is required"
                etEmail.requestFocus()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Enter a valid email"
                etEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Password is required"
                etPassword.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6) {
                etPassword.error = "Password must be at least 6 characters"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        getUserRoleAndNavigate(userId, selectedRole)
                    } else {
                        val exception = task.exception
                        when (exception) {
                            is FirebaseAuthInvalidUserException -> {
                                Toast.makeText(requireContext(), "No account found with this email", Toast.LENGTH_SHORT).show()
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(requireContext(), "Login failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        }
        return view
    }

    private fun getUserRoleAndNavigate(userId: String?, selectedRole: String) {
        if (userId == null) {
            Toast.makeText(requireContext(), "Error: User ID is null", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance("https://news-reporter-app-58bdb-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

        database.child(userId).get()
            .addOnSuccessListener { snapshot ->
                val role = snapshot.child("role").value.toString()

                if (role != selectedRole) {
                    Toast.makeText(requireContext(), "Cannot log in as $selectedRole. You are registered as $role.", Toast.LENGTH_LONG).show()
                } else {
                    navigateToHomeDashboard(role)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch user role", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToHomeDashboard(role: String) {
        val intent = Intent(requireContext(), HomeDashboardActivity::class.java)
        intent.putExtra("USER_ROLE", role)
        startActivity(intent)
        requireActivity().finish()
    }
}
