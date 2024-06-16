package Firebaseutilc;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Firebaseutil {
    // Get the current user ID
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    // Check if a user is logged in
    public static boolean isLoggedIn() {
        return currentUserId() != null;
    }

    // Get a reference to the current user's details document in Firestore
    public static DocumentReference currentUserDetails() {
        return FirebaseFirestore.getInstance().collection("clients").document(currentUserId());
    }

    // Get a reference to the "clients" collection in Firestore
    public static CollectionReference allCollectionReference() {
        return FirebaseFirestore.getInstance().collection("clients");
    }

    // Get the current user's username asynchronously


    // Callback interface to retrieve the username asynchronously
    public interface FirebaseUserNameCallback {
        void onCallback(String userName);
    }

    // Get the current user's phone number asynchronously
    public static void getCurrentUserName(FirebaseUserNameCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("clients").document(currentUserId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String userName = documentSnapshot.getString("username");
                        if (userName != null && !userName.isEmpty()) {
                            callback.onCallback(userName);
                        } else {
                            callback.onCallback("Default User");
                        }
                    } else {
                        callback.onCallback("Default User");
                    }
                })
                .addOnFailureListener(e -> {
                    callback.onCallback("Default User");
                });
    }

    public static void getCurrentUserPhone(UserPhoneCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("clients").document(currentUserId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String phoneNumber = documentSnapshot.getString("phonenumber");
                        if (phoneNumber != null && !phoneNumber.isEmpty()) {
                            callback.onCallback(phoneNumber);
                        } else {
                            callback.onCallback("");
                        }
                    } else {
                        callback.onCallback("");
                    }
                })
                .addOnFailureListener(e -> {
                    callback.onCallback("");
                });
    }


    // Callback interface to retrieve the phone number asynchronously
    public interface UserPhoneCallback {
        void onCallback(String phoneNumber);
    }
}
