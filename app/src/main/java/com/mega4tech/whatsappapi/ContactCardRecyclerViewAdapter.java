package com.mega4tech.whatsappapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mega4tech.whatsappapilibrary.model.WContact;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ContactCardRecyclerViewAdapter extends RecyclerView.Adapter<ContactCardRecyclerViewAdapter.ViewHolder> {
    private RecycleItemClickListener mItemClickListener;
    private List<WContact> mObjects = new LinkedList<>();
    HashMap<WContact, Integer> mDictionary;
    private Context mContext;
    private LayoutInflater layoutInflater;
    String[] mContacts;
    boolean[] mSelectedContacts;

    public ContactCardRecyclerViewAdapter(Context context,List<WContact> items, String[] contacts, boolean[] selectedContacts, HashMap<WContact, Integer> dictionary) {
        this.mObjects = items;
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        mContacts = contacts;
        mSelectedContacts = selectedContacts;
        mDictionary = dictionary;
    }


    @Override
    public int getItemCount() {
        return this.mObjects.size();
    }

    @Override
    public void onBindViewHolder(ContactCardRecyclerViewAdapter.ViewHolder holder, int position) {

        WContact item = this.mObjects.get(position);
        Log.d("ABDULL55", item.toString());

        holder.contactNameTv.setText(item.getName());
        holder.contactRemoveIv.setTag(item);
        holder.contactRemoveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WContact contact = (WContact) v.getTag();
                if(mDictionary.containsKey(contact))
                    mSelectedContacts[mDictionary.get(contact)] = false;
                mObjects.remove(contact);
                mDictionary.remove(contact);
                notifyDataSetChanged();
            }
        });
        Log.d("ABDULL!", item.toString());

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.contact_card, parent, false);
        return new ContactCardRecyclerViewAdapter.ViewHolder(sView, mItemClickListener);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecycleItemClickListener mItemClickListener;
        private TextView contactNameTv;
        private ImageView contactRemoveIv;


        public ViewHolder(View view, RecycleItemClickListener itemClickListener) {
            super(view);
            contactNameTv = (TextView) view.findViewById(R.id.contact_name_tv);
            contactRemoveIv = (ImageView) view.findViewById(R.id.contact_remove_iv);
            mItemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public void setOnItemClickListener(RecycleItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface RecycleItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setmObjects(List<WContact> mObjects) {
        this.mObjects = mObjects;
        notifyDataSetChanged();

    }
}
