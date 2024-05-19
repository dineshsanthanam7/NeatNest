package Firebaseutilc;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Firebaseutil {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();

    }
    public static boolean isloggedin(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }

    public static DocumentReference currentUserDetails(){

        return FirebaseFirestore.getInstance().collection("clients").document(currentUserId());
    }
    public static CollectionReference allCollectionReference(){
        return FirebaseFirestore.getInstance().collection("clients");
    }
}