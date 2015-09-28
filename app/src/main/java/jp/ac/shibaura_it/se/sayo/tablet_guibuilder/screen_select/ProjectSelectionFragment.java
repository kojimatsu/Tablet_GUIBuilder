package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


/**
 * Created by matsu on 2014/11/17.
 */
public class ProjectSelectionFragment extends DialogFragment {

    private static String PROJECTS = "PROJECTS";
    private OnProjectClickListener onProjectClickListener = null;

    protected interface OnProjectClickListener{
        public void onPositiveClick(String projectPath);
    }

    protected static ProjectSelectionFragment newInstance() {
        ProjectSelectionFragment fragment = new ProjectSelectionFragment();
        //String[] projects = DropBoxConnection.getProjects();
        Bundle bundle = new Bundle();
        //bundle.putStringArray(PROJECTS, projects);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onProjectClickListener = (OnProjectClickListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] projects = getArguments().getStringArray(PROJECTS);
        final String[] selectItem = new String[1];
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("プロジェクトを選択して下さい")
                .setSingleChoiceItems(projects, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        selectItem[0] = new String(projects[whichButton]);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
//                        DropBoxConnection.setProjectDbxFileInfo(selectItem[0]);
//                        onProjectClickListener.onPositiveClick(DropBoxConnection.getShareInfoPath());
                    }
                })
                .create();
        return dialog;
    }
}
