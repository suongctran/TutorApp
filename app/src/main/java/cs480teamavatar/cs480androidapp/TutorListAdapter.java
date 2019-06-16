package cs480teamavatar.cs480androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Intent;

class TutorListAdapter extends ArrayAdapter<String> {
    private static final String URL = "jdbc:mysql://db.zer0-one.net/tutorWeb";
    private static final String USER = "TeamAvatar";
    private static final String PASS = "Ie5Jaxae";
    private ArrayList<String> tutorsEmail;
    private Bundle extras;
    private String packType;
    private String currentEmail;

    TutorListAdapter(Context context, ArrayList<String> tutorsName, ArrayList<String> tutorsEmail,
                     String type, String email) {
        super(context, R.layout.tutor_list_adapter, tutorsName);
        this.tutorsEmail = tutorsEmail;
        this.packType = type;
        this.currentEmail = email;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View customView = listInflater.inflate(R.layout.tutor_list_adapter, parent, false);
        String name = getItem(position);
        TextView tutorText = (TextView) customView.findViewById(R.id.list_frag_text_view);
        tutorText.setText(name);
        final String email = tutorsEmail.get(position);
        Button add = (Button) customView.findViewById(R.id.add_button);
        if (packType.charAt(0) == 't') {
            add.setVisibility(View.INVISIBLE);
        }
        else {
            add.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), SetAppointment.class);
                    Bundle extras = new Bundle();
                    extras.putString("studentEmail", currentEmail);
                    extras.putString("tutorEmail", email);
                    intent.putExtras(extras);
                    view.getContext().startActivity(intent);
                }
            });
        }
        Button viewProfile = (Button) customView.findViewById(R.id.list_vp_button);
        viewProfile.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewProfile.class);
                intent.putExtra("email", email);
                view.getContext().startActivity(intent);
            }
        });
        return customView;
    }
}
