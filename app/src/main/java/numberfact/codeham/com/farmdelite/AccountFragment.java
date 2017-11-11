package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Abhishek on 21/10/2017.
 */

public class AccountFragment extends Fragment {

    private Context mCtx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_fragment, container, false);
        ListView listView = (ListView) v.findViewById(R.id.list_view);
        mCtx = getContext();
        ArrayList<AccountClass> arrayList = new ArrayList<>();
        arrayList.add(new AccountClass("My Orders", R.drawable.ic_shopping_basket_black_24dp));
        arrayList.add(new AccountClass("Wallet", R.drawable.ic_account_balance_wallet_black_24dp));
        arrayList.add(new AccountClass("Address", R.drawable.ic_playlist_add_check_black_24dp));

        arrayList.add(new AccountClass("Help Centre", -1));
        arrayList.add(new AccountClass("My Rewards", -1));
        arrayList.add(new AccountClass("Rate the App", -1));
        arrayList.add(new AccountClass("Send Feedback", -1));

        arrayList.add(new AccountClass("Account Setting", -1));
        arrayList.add(new AccountClass("Legal", -1));
        arrayList.add(new AccountClass("Sign out", -1));


        AccountsAdapter accountsAdapter = new AccountsAdapter(getContext(), arrayList);
        listView.setAdapter(accountsAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:

                        startActivity(new Intent(mCtx, WalletActivity.class));

                }
            }
        });

        return v;
    }
}
