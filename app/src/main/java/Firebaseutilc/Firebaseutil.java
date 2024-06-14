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
        return currentUserId() != null;
    }

    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("clients").document(currentUserId());
    }

    public static CollectionReference allCollectionReference(){
        return FirebaseFirestore.getInstance().collection("clients");
    }

    public interface UserNameCallback {
        void onCallback(String userName);
    }

    public static void getCurrentUserName(UserNameCallback callback) {
        currentUserDetails().get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                callback.onCallback(documentSnapshot.getString("username"));
            } else {
                callback.onCallback("Default User"); // Handle default username if not found
            }
        }).addOnFailureListener(e -> {
            callback.onCallback("Default User"); // Handle default username if there's an error
        });
    }
}
