package demo.lh.com.musicplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.NormalTextViewHolder> {
    private Context context;
    private String[] mTitles;

    public MusicAdapter(Context context) {
        this.context = context;
        mTitles = context.getResources().getStringArray(R.array.music);
    }

    @NonNull
    @Override
    public MusicAdapter.NormalTextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
        NormalTextViewHolder holder = new NormalTextViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.NormalTextViewHolder normalTextViewHolder, int i) {
        normalTextViewHolder.item_position.setText(i+"");
        normalTextViewHolder.music_name.setText(mTitles[i]);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    public class NormalTextViewHolder extends RecyclerView.ViewHolder {
        TextView music_name;
        TextView item_position;
        public NormalTextViewHolder(@NonNull View itemView) {
            super(itemView);
            music_name = itemView.findViewById(R.id.music_name);
            item_position = itemView.findViewById(R.id.item_postion);
        }
    }
}
