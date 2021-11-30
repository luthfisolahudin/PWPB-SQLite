package id.luthfisolahudin.smkn4.pwpb.sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.luthfisolahudin.smkn4.pwpb.sqlite.R;
import id.luthfisolahudin.smkn4.pwpb.sqlite.model.Person;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.UserViewHolder> {
    Context context;
    OnUserClickListener onUserClickListener;
    List<Person> personList;

    public RecycleViewAdapter(Context context, OnUserClickListener onUserClickListener, List<Person> personList) {
        this.context = context;
        this.onUserClickListener = onUserClickListener;
        this.personList = personList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Person currentPerson = personList.get(position);
        holder.textName.setText(currentPerson.getName());
        holder.textAge.setText(String.valueOf(currentPerson.getAge()));
        holder.buttonImageEdit.setOnClickListener((View.OnClickListener) v -> onUserClickListener.onUserClick(currentPerson, context.getString(R.string.action_update)));
        holder.buttonImageDelete.setOnClickListener((View.OnClickListener) v -> onUserClickListener.onUserClick(currentPerson, context.getString(R.string.action_delete)));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public interface OnUserClickListener {
        void onUserClick(Person currentPerson, String action);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textAge;
        ImageView buttonImageEdit;
        ImageView buttonImageDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.text_person_name);
            textAge = itemView.findViewById(R.id.text_person_age);
            buttonImageDelete = itemView.findViewById(R.id.button_image_delete);
            buttonImageEdit = itemView.findViewById(R.id.button_image_edit);
        }
    }
}
