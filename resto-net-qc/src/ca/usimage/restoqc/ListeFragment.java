package ca.usimage.restoqc;




import android.app.Activity;
import android.app.ListFragment;


import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.app.LoaderManager;
import android.view.View.OnClickListener;


import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.ListView;



public class ListeFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>  {
	private ListItemSelectListener listeSelectListener;
	private ListItemMapListener listeMapListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	

		try {
			listeSelectListener = (ListItemSelectListener) activity;
		
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " doit implementer ListItemSelectListener");
		}
		try {
			listeMapListener = (ListItemMapListener) activity;
		
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " doit implementer ListMapListener");
		}
	}

	
	


	
	public MyCursorAdapter adapter;  // extends simplecursoradaptor


	

	
	private class OnItemClickListener implements OnClickListener{       
	    private int mPosition;
	    private long mId;
	    OnItemClickListener(int position, long id){
	        mPosition = position;
	        mId = id;
	    }
	    @Override
	    public void onClick(View arg0) {
	    	// this links the click event to the main activity via the interface  ListItemSelectListener
	    	// to call the details fragment
	    	listeSelectListener.onItemSelected(mPosition, mId);
       
	    }       
	}
	
	
	public class MyCursorAdapter extends SimpleCursorAdapter implements OnClickListener{
	
		public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flag) {
	        super(context, layout, c, from, to, flag);
	    }
 
	    @Override
	    public void bindView(View view, Context context, Cursor cursor) {
	        super.bindView(view, context, cursor);
	     
	        final Cursor c = cursor;
	        int pos;
	        
	        pos = c.getPosition();
	        
	        final Long ID =  getItemId(pos);
	        
	        String info =c.getString(c.getColumnIndex(RestoDatabase.COL_INFO));
	       
	        ImageView exploitant = (ImageView) view.findViewById(R.id.Exploitant);
	       if ((info != null) && (info.indexOf("cess�") > 0)){
	        		exploitant.setVisibility(View.VISIBLE);
	        		exploitant.setImageResource(R.drawable.closed);
	        	}
	         
//	        else if (info.indexOf("changement d") > 0){
//	        	exploitant.setVisibility(View.VISIBLE);
//	        	exploitant.setImageResource(R.drawable.new_owner);
//	        }
	        
	        else {
	        	exploitant.setVisibility(View.INVISIBLE);
	        }
	    
	       
            ImageButton mapButton = (ImageButton)view.findViewById(R.id.ImageButton01);
	        mapButton.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View arg0) {
	               listeMapListener.onItemMapSelected(ID);

	            }
	        });
	        
	        // this allows a list item to be clickable, separately from the map button, to call up the details fragment

	        view.setOnClickListener(new OnItemClickListener( pos, ID));

	    }
   
		@Override
		public void onClick(View v) {
			

		}   
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
	 		
		
	    String[] uiBindFrom = { RestoDatabase.COL_ETAB, RestoDatabase.COL_INFO, RestoDatabase.COL_MONTANT, RestoDatabase.COL_ADR, RestoDatabase.COL_DATE_JUGE };
	    int[] uiBindTo = { R.id.Etablissement, R.id.Exploitant, R.id.Montant,R.id.Adresse, R.id.Date};
	    adapter = new MyCursorAdapter(
	            getActivity(), R.layout.rownew,
	            null, uiBindFrom, uiBindTo,
	            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	    setListAdapter(adapter);
		}
		
	
	

	    

    @Override
public void onResume()
{
    super.onResume();
  
  
  
}
	
	
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      return null;
	    	
	    
	   
	
	}


	
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

	    adapter.swapCursor(cursor);
	}

	
	public void onLoaderReset(Loader<Cursor> loader) {
	    adapter.swapCursor(null);
	}

	

	
	
}
