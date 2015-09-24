package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by matsu on 2014/11/07.
 */
public class OutLineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.outline_fragment, container, false);
    }
}

