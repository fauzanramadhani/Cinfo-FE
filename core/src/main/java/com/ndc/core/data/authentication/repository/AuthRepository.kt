package com.ndc.core.data.authentication.repository

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
) {
    private val auth = Firebase.auth

    fun register(
        email: String,
        password: String
    ) = auth.createUserWithEmailAndPassword(email, password)

    fun loginBasic(
        email: String,
        password: String
    ) = auth.signInWithEmailAndPassword(email, password)

    fun handleSignInWithGoogle(
        data: Intent
    ): Task<AuthResult> {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
        val idToken = account.idToken
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        return auth.signInWithCredential(credential)
    }

    fun getFirebaseUser(): FirebaseUser? = auth.currentUser

    fun logout() {
        auth.signOut()
        googleSignInClient.signOut()
    }
}