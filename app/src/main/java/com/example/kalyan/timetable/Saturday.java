package com.example.kalyan.timetable;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by KALYAN on 25-08-2017.
 */

public class Saturday extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    Cursor mCursor;
    final int LOADER_CODE = 5;
    View view = null;
    ListView list5 = null;
    String sqlArray[];
    String tSQL[];
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.saturday_layout,container,false);
        list5 = (ListView) view.findViewById(R.id.list_view5);
        registerForContextMenu(list5);
        sqlArray = getResources().getStringArray(R.array.TimeSQL);
        getActivity().getSupportLoaderManager().initLoader(LOADER_CODE,null,this);

        list5.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(MainActivity.getContext(),EditorActivity.class);
                intent.putExtra("subject",((TextView)view.findViewById(R.id.subject)).getText().toString());
                intent.putExtra("room",((TextView)view.findViewById(R.id.room)).getText().toString());
                intent.putExtra("position",position);
                startActivity(intent);
                return true;
            }
        });
        setView(mCursor);
        return view;
    }


    public void setView(Cursor cursor){
        tSQL = MainActivity.getContext().getResources().getStringArray(R.array.TimeSQL);

        if( cursor !=  null  && cursor.moveToFirst() ) {
            MyAdapter adapter = new MyAdapter(MainActivity.getContext(),cursor,tSQL);
            list5.setAdapter(adapter);
        }

    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String selectQuery = "SELECT  * FROM " + Contract.Entry.TABLE_NAME + " WHERE "
                + Contract.Entry.COLUMN_DAY + " = " + "\"saturday\"";

        Helper helper = (new Helper(MainActivity.getContext()));
        try {
            mCursor = helper.getReadableDatabase().rawQuery(selectQuery, null);
        }catch (SQLiteException e){

        }

        return new CursorLoader(getContext(),Contract.Entry.CONTENT_URI,
                null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
