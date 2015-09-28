package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select.Screen;

/**
 * Created by matsu on 2014/12/01.
 */
public class EditControlFragment extends Fragment {

    public EditControlFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_control_fragment, container, false);
    }

    /**
     *
     * @param useCasePath プロジェクト名/ユースケース名
     */
    public void setFileName(String useCasePath) {
        setSize();
        ScreenFragment screenFragment = ScreenFragment.newInstance(useCasePath);
        // ActivityにFragmentを登録する。
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // Layout位置先の指定
        fragmentTransaction.add(R.id.edit_screen, screenFragment);
    }

    /**
     * スマホ画面fragmentのサイズ設定
     */
    private void setSize() {
        RelativeLayout screen = (RelativeLayout) getActivity().findViewById(
                R.id.edit_screen);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                240, 400);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        screen.setLayoutParams(params);

        //動的にする場合
//        double scale = 0.9;
//        int width = (int) (root.getWidth() * scale);
//        int height = (int) (root.getHeight() * scale);
//        if (width != screen.getWidth()) {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                    width, height);
//            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//            params.addRule(RelativeLayout.CENTER_VERTICAL);
//            screen.setLayoutParams(params);
//        }
    }

    public void notice(int viewId) {

    }
}