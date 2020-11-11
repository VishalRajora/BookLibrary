package com.example.booklibrary;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class customAdapter extends RecyclerView.Adapter < customAdapter.MyViewHolder > implements PopupMenu.OnMenuItemClickListener {

    String update_id, update_title, update_author, update_pages;
    AlertDialog alertDialog;
    //  private ArrayList < String > book__id, book__title, book__author, book__pages;
    ArrayList < ModelClass > dataHolder;
    Animation myAnimation;
    private Context context;


    public customAdapter(Context context , ArrayList < ModelClass > dataHolder) {
        this.context = context;
        this.dataHolder = dataHolder;
    }

    //onCreateViewHolder convert the XML file to View
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from ( context );
        View view = layoutInflater.inflate ( R.layout.custom_adapter , null );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder , final int position) {

        // holder.id.setText ( dataHolder.get ( position ).getBookID () );
        holder.title.setText ( dataHolder.get ( position ).getBookTitle () );
        holder.author.setText ( dataHolder.get ( position ).getBookAuthodName () );
        holder.pages.setText ( dataHolder.get ( position ).getBookPages () );
        holder.myLinerLayout.setOnLongClickListener ( new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {
                longClickMenu ( v );
                update_id = dataHolder.get ( position ).getBookID ();
                update_title = dataHolder.get ( position ).getBookTitle ();
                update_author = dataHolder.get ( position ).getBookAuthodName ();
                update_pages = dataHolder.get ( position ).getBookPages ();
                return true;
            }
        } );

    }

    @Override
    public int getItemCount() {
        return dataHolder.size ();
    }

    private void longClickMenu(View view) // View must be pasing
    {
        PopupMenu popupMenu = new PopupMenu ( view.getContext () , view );
        popupMenu.inflate ( R.menu.popup_menu );
        popupMenu.setOnMenuItemClickListener ( customAdapter.this );
        popupMenu.show ();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId ()) {

            case R.id.edit_item:
                //context.startActivity ( intent );
                editButtonClick ();
                return true;

            case R.id.delete_item:
                myDatabaseClass myDatabaseClass = new myDatabaseClass ( context );
                myDatabaseClass.deleteData ( update_id );
                notifyDataSetChanged ();
                MainActivity.showDataToUi ();
                return true;

            default:
                return false;
        }

    }

    private void editButtonClick() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder ( context );
        LayoutInflater layoutInflater = LayoutInflater.from ( context );
        View view = layoutInflater.inflate ( R.layout.add_data , null );
        myDialog.setView ( view );
        alertDialog = myDialog.create ();
        alertDialog.setCancelable ( true );

        final TextInputLayout updateTitle, updateAuthor, updatePages;
        updateTitle = view.findViewById ( R.id.Title );
        updateAuthor = view.findViewById ( R.id.Auther );
        updatePages = view.findViewById ( R.id.pages );
        updateTitle.getEditText ().setText ( update_title );
        updateAuthor.getEditText ().setText ( update_author );
        updatePages.getEditText ().setText ( update_pages );

        Button updatebutton = view.findViewById ( R.id.addButton );
        updatebutton.setText ( "Update" );
        updatebutton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                myDatabaseClass myDatabaseClass = new myDatabaseClass ( context );
                myDatabaseClass.upadteData ( update_id , updateTitle.getEditText ().getText ().toString () , updateAuthor.getEditText ().getText ().toString () , updatePages.getEditText ().getText ().toString () );
                alertDialog.dismiss ();
                notifyDataSetChanged ();
                MainActivity.showDataToUi ();
            }
        } );
        alertDialog.show ();
    }

    public void findSearch(List < ModelClass > newList) {
        dataHolder = new ArrayList <> ();
        dataHolder.addAll ( newList );
        notifyDataSetChanged ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, title, author, pages;
        RelativeLayout myLinerLayout;

        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );

            //  id = itemView.findViewById ( R.id.item_id );
            title = itemView.findViewById ( R.id.item_title );
            author = itemView.findViewById ( R.id.item_AuthorName );
            pages = itemView.findViewById ( R.id.item_pages );
            myLinerLayout = itemView.findViewById ( R.id.item_linerLayout );

            //animation to layout
            myAnimation = AnimationUtils.loadAnimation ( context , R.anim.recycelr_animation );
            myLinerLayout.setAnimation ( myAnimation );
        }

    }


}

