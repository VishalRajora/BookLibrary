package com.example.booklibrary;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fltButn;
    AlertDialog alertDialog;
   public static myDatabaseClass myDatabaseClass;
 //   ArrayList < String > book__id, book__title, book__author, book__pages;
    public  static ArrayList<ModelClass>dataHoldr ;
    public static  customAdapter customAdapter ;
    RelativeLayout forsnackbar;
    static ImageView empty_image;
    static TextView empty_textView;
    SearchView mySearch;
   static CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        //both are must be initilize
        myDatabaseClass = new myDatabaseClass ( MainActivity.this );
        dataHoldr = new ArrayList <> (  );

        coordinatorLayout = findViewById ( R.id.corrrdinator_layout );

        forsnackbar = findViewById ( R.id.main_relative );
        mySearch = findViewById ( R.id.mySearch );

     mySearch.setOnQueryTextListener ( new SearchView.OnQueryTextListener () {
         @Override
         public boolean onQueryTextSubmit(String query) {
             return false;
         }

         @Override
         public boolean onQueryTextChange(String newText) {
             newText = newText.toLowerCase ();
             List<ModelClass>newList =  new ArrayList <> (  );
             for(ModelClass modelClass : dataHoldr)
             {
                 String bookTitle = modelClass.getBookTitle ().toLowerCase ();
                 if(bookTitle.contains ( newText ))
                 {
                     newList.add ( modelClass );
                 }
             }
             customAdapter.findSearch(newList);
             return true;
         }
     } );
//
//        //for emptyIamge View
        empty_image  = findViewById ( R.id.empty_imageView );
        empty_textView =findViewById ( R.id.empty_textView );


        //recyclerView
        recyclerView = findViewById ( R.id.recyclerView );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( MainActivity.this ));


        showDataToUi ();
        customAdapter = new customAdapter (  this,dataHoldr );
        recyclerView.setAdapter ( customAdapter );

//        book__id = new ArrayList <> ();
//        book__title = new ArrayList <> ();
//        book__author = new ArrayList <> ();
//        book__pages = new ArrayList <> ();


        //floatingAction button
        fltButn = findViewById ( R.id.addFloating );
        fltButn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                fabButtonClick ();
                showDataToUi ();
            }
        } );


    }

    private void fabButtonClick() {

        MaterialAlertDialogBuilder myDialog = new MaterialAlertDialogBuilder ( this );
        LayoutInflater layoutInflater = LayoutInflater.from ( this );
        View view = layoutInflater.inflate ( R.layout.add_data , null );
        myDialog.setView ( view );
        alertDialog = myDialog.create ();
        alertDialog.setCancelable ( true );


        final TextInputLayout title,author, pages;;
        Button addButton;
        title = view.findViewById ( R.id.Title );
        author = view.findViewById ( R.id.Auther );
        pages = view.findViewById ( R.id.pages );
        addButton = view.findViewById ( R.id.addButton );
        addButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                String bookTitle = title.getEditText ().getText ().toString ().trim ();
                String bookAuthor = author.getEditText ().getText ().toString ().trim ();
                String bookPages =  pages.getEditText ().getText ().toString ().trim () ;

                if(bookTitle.equals ( "" ))
                {
                    title.setError ( "Required Field" );
                }
                else if(bookAuthor.equals ( "" ))
                {
                    author.setError ( "Required Field" );
                }
                else if(bookPages.equals ( "") )
                {
                    pages.setError (  "Required Field");
                }
                else {
                myDatabaseClass myDatabaseClass = new myDatabaseClass ( MainActivity.this );
                myDatabaseClass.addBook ( bookTitle , bookAuthor , bookPages );
                alertDialog.dismiss ();
                Snackbar.make (MainActivity.coordinatorLayout,"Add Book",Snackbar.LENGTH_SHORT ).show ();
                showDataToUi (); //its a must cozz we update data when we inserted new data
            }
        } });
        alertDialog.show ();
        //.getWindow().setLayout(1200, 900); - this work when we use a simple AlerrtDialog

    }

    public static void showDataToUi() {
       dataHoldr.clear (); // its a must to declare this
        Cursor cursor = myDatabaseClass.readAllData ();

        if (cursor.getCount () == 0) {
            empty_textView.setVisibility ( View.VISIBLE );
            empty_image.setVisibility ( View.VISIBLE );

        } else {
            while ((cursor.moveToNext ())) {
                //if u dnt have modelCalss then we can fetch like this
//                book__id.add ( cursor.getString ( 0 ) ); //here add() function is from ArrayList
//                book__title.add ( cursor.getString ( 1 ) );
//                book__author.add ( cursor.getString ( 2 ) );
//                book__pages.add ( cursor.getString ( 3 ) );
               String id = cursor.getString ( 0 );
                String title=cursor.getString ( 1 );
                String author = cursor.getString ( 2 );
                String pages=cursor.getString ( 3 );

                //if u have a ModelClass
                ModelClass modelClass = new ModelClass ( id,title,author,pages );
                dataHoldr.add ( modelClass );
            }

            empty_textView.setVisibility ( View.GONE );
            empty_image.setVisibility ( View.GONE );
        }
       // customAdapter.notifyDataSetChanged (); //this is in method and its a must to update data
    }
}