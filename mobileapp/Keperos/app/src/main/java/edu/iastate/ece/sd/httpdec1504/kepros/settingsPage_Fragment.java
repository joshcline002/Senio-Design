package edu.iastate.ece.sd.httpdec1504.kepros;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wipark on 8/25/15.
 */
public class settingsPage_Fragment extends android.support.v4.app.Fragment {
    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.settingspage_layout, container, false);
        return rootview;
    }
}
