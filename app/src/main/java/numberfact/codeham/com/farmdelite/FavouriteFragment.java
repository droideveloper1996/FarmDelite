package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * Created by Abhishek on 21/10/2017.
 */

public class FavouriteFragment extends Fragment {

    FirebaseRemoteConfig firebaseRemoteConfig;
    Context mCtx;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_fragment, container, false);
        textView = (TextView) view.findViewById(R.id.wishText);
        mCtx = getContext();




        return view;


    }

    private void displayWelcomeMessage() {

        String welcomeMessage = firebaseRemoteConfig.getString("message");
        Toast.makeText(mCtx,welcomeMessage,Toast.LENGTH_LONG).show();
        textView.setText(welcomeMessage);

    }

    @Override
    public void onResume() {

        super.onResume();

                firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                long val = 3600;
                firebaseRemoteConfig.fetch(val).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mCtx, "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show();

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            firebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(mCtx, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        displayWelcomeMessage();
                    }
                });

                // Run mFirebaseRemoteConfig.fetch(timeout) here, and it works
            }


}
