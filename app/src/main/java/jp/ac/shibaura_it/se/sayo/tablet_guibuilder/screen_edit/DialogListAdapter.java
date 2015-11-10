package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import java.util.ArrayList;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by 浩司 on 2015/11/10.
 */
public class DialogListAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layoutInflater = null;
    private ArrayList<String> list;


    public DialogListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    private static long id = 0;
    @Override
    public long getItemId(int position) {
        return id++;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.dialog_listview_line,parent,false);

        ((RadioButton)convertView.findViewById(R.id.listview_line_radioButton)).setText(list.get(position));

        return convertView;
    }
}
