package mx.itson.edu.practica9

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient:   GoogleSignInClient
    val RC_SIGN_IN = 343
    val LOG_OUT = 243

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        val signInButton: Button = findViewById(R.id.sign_in_button)

        signInButton.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent , RC_SIGN_IN)
        }

    }

    override fun onStart(){
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

        if(requestCode == LOG_OUT){
            signOut()
        }
    }

    private fun signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this , OnCompleteListener<Void?> {
            Toast.makeText(this , "Sesion Terminada" , Toast.LENGTH_SHORT).show()
        })
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try{
            val account = completedTask.getResult(ApiException::class.java)

            updateUI(account)
        }catch(e: ApiException){
            Log.w("test_signin" , "signInResult:fail code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(acct: GoogleSignInAccount?){
        if(acct != null){
            val intent = Intent(this , Principal::class.java)
            intent.putExtra("id" , acct.id)
            intent.putExtra("name" , acct.displayName)
            intent.putExtra("email" , acct.email)
            startActivityForResult(intent , LOG_OUT)
        }
    }
}