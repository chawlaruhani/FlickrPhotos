package test.verizon.com.flickrphotos.UI;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import java.util.List;

import test.verizon.com.flickrphotos.Data.PhotoObject;
import test.verizon.com.flickrphotos.R;
import test.verizon.com.flickrphotos.Utils.Constants;
import test.verizon.com.flickrphotos.databinding.SearchItemLayoutBinding;

public class PageLoadAdapter extends RecyclerView.Adapter<PageLoadAdapter.MyViewHolder>  {

    private List<PhotoObject> photos;
    private Context context;

    public PageLoadAdapter(Context context, List<PhotoObject> photoList){
        this.photos = photoList;
        this.context = context;
    }

    public void updateData(boolean isClear){
        if(isClear) {
            photos.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.search_item_layout, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PhotoObject photo = photos.get(position);

        holder.binding.title.setText(photo.getTitle());
        if(photo.getImage() != null) {
            holder.binding.imageIcon.setImageBitmap(photo.getImage());
        }
        holder.binding.buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(context, holder.binding.buttonOptions);
                popup.inflate(R.menu.menu_main);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String size = "s";
                        switch (item.getItemId()) {
                            case R.id.small:size = "s";
                                break;
                            case R.id.medium: size= "m";
                                break;
                            case R.id.large: size ="q";
                                break;

                        }
                        sendActivityInfo(photo, size);
                        popup.dismiss();
                        return false;
                    }
                });

                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    private void sendActivityInfo(PhotoObject photo, String size){

        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(Constants.IMAGE_SIZE, size);
        intent.putExtra(Constants.FARM, photo.getFarm());
        intent.putExtra(Constants.FORMAT, "jpg");
        intent.putExtra(Constants.ID, photo.getId());
        intent.putExtra(Constants.SECRET, photo.getSecret());
        intent.putExtra(Constants.SERVER, photo.getServer());

        context.startActivity(intent);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private SearchItemLayoutBinding binding;

        public MyViewHolder(SearchItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
         }
    }

}
