package com.example.bram.huelampen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentIP extends AppCompatDialogFragment {

    EditText ip;
    EditText port;
    private FragmentListener fragmentListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_simulation_ip, null);

        builder.setView(view)
                .setTitle("ip")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String textIp = ip.getText().toString();
                String textPort = port.getText().toString();
                fragmentListener.applyIP(textIp,textPort);

            }
        });
        ip = view.findViewById(R.id.connect_IP_textField);
        port = view.findViewById(R.id.connect_poortNummer_TextField);


    return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (FragmentListener)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface FragmentListener{
        void applyIP(String ip, String Port);
    }
}
