package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abhishek on 28/10/2017.
 */

public class AccountsAdapter extends ArrayAdapter<AccountClass> {


    public AccountsAdapter(Context context, ArrayList<AccountClass> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        AccountClass accountClass = getItem(position);

        if (convertView == null
                ) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.account_list_item, parent, false);
        } else {

        }
        view.setPadding(20, 20, 20, 20);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_account_list);
        TextView textView = (TextView) view.findViewById(R.id.textview_account_list);
        imageView.setVisibility(View.VISIBLE);
        if (accountClass.getmImagearr() == -1) {
            imageView.setVisibility(View.GONE);
            view.setPadding(4, 4, 4, 4);

        } else {
            imageView.setImageResource(accountClass.getmImagearr());
        }
        textView.setText(accountClass.getStr());


        return view;

    }


}

