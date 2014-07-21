package com.ovaflow.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovaflow.app.R;

/**
 * Created by ArthurXu on 20/07/2014.
 */
public class AvatarAdapter extends ArrayAdapter<Integer> {

    private static final Integer[] sprites = {R.drawable.sprite001_0_0, R.drawable.sprite002_0_0};
    private static final String[] spriteNames = {"Eko", "Dogé"};

    public AvatarAdapter(Context context) {
        super(context, R.layout.avatar_list_item, sprites);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.avatar_list_item, null);
        }

        ImageView beatmapPic = (ImageView) view.findViewById(R.id.avatar_item_image);
        TextView beatmapName = (TextView) view.findViewById(R.id.avatar_item_name);

        beatmapPic.setImageResource(sprites[position]);
        beatmapPic.getLayoutParams().width = 200;
        beatmapPic.getLayoutParams().height = 200;
        beatmapName.setText(spriteNames[position]);

        return view;
    }
}
