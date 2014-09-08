package ca.usimage.restoqc;

import java.io.IOException;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class RestoProvider extends ContentProvider {
	
    private RestoDatabase mDB;
    public static final String TABLE_RESTO = "resto";
    private static final String AUTHORITY = "ca.usimage.restoqc.RestoProvider";
    public static final int RESTOS = 100;
    public static final int RESTO_GROUPBY = 110;
    public static final int RESTO_GROUPBY_PLUS = 111;
    private static final String RESTOS_BASE_PATH = "restos";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + RESTOS_BASE_PATH);
    public static final Uri CONTENT_URI_GROUPBY = Uri.parse("content://" + AUTHORITY
            + "/" + RESTOS_BASE_PATH + "/GROUPBY");
    public static final Uri CONTENT_URI_GROUPBY_PLUS = Uri.parse("content://" + AUTHORITY
            + "/" + RESTOS_BASE_PATH + "/GROUPBY");
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/mt-resto";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/mt-resto";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, RESTOS_BASE_PATH, RESTOS);
        sURIMatcher.addURI(AUTHORITY, RESTOS_BASE_PATH + "/GROUPBY", RESTO_GROUPBY);
        sURIMatcher.addURI(AUTHORITY, RESTOS_BASE_PATH + "/GROUPBY", RESTO_GROUPBY_PLUS);
    }
    
    @Override
    public boolean onCreate() {
        mDB = new RestoDatabase(getContext());
//        try {
        	 
   //     	mDB.createDatabase();
 
//	} catch (IOException ioe) {
// 
// 		throw new Error("Unable to create database");
// 
// 	}
 
 	try {
 
 		mDB.openDataBase();
 
 	}catch(SQLException sqle){
 
 		throw sqle;
 
 	}

        return true;
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(RestoDatabase.TABLE_RESTO);
        int uriType = sURIMatcher.match(uri);
        String groupBy = null;
        switch (uriType) {
        case RESTO_GROUPBY:
             groupBy = "etablissement";
            break;
        case RESTO_GROUPBY_PLUS:
            groupBy = "etablissement, adresse";
           break;
        case RESTOS:
            // no filter
            break;
        default:
            throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(mDB.getReadableDatabase(),
                projection, selection, selectionArgs, groupBy, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
    
    
    
    @Override
    public int delete(Uri uri,  String selection,
            String[] selectionArgs) {

        
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsAffected = 0;
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        
        case RESTOS:
    
            sqlDB.beginTransaction();
     	   try {
     		  
     			  sqlDB.execSQL("DROP TABLE IF EXISTS " + "Resto");
     			
     		
//     	        rowsAffected = sqlDB.delete(RestoDatabase.TABLE_RESTO,
//                        selection, selectionArgs);
     	     sqlDB.setTransactionSuccessful();
     	   } finally {
     	     sqlDB.endTransaction();
     	   
     	   }        
            break;
     
        default:
            throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }

   

    
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		  SQLiteDatabase sqlDB = mDB.getWritableDatabase();
	       
	        int uriType = sURIMatcher.match(uri);
	        switch (uriType) {
	        
	        case RESTOS:
	        	
	        	break;
	       
	           
	     
	        default:
	            throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
	        }
	            long id =   sqlDB.insert(TABLE_RESTO, null, values); 
	            getContext().getContentResolver().notifyChange(uri, null);
	            return Uri.parse(CONTENT_URI + "/" + id);
	        }
	        
	        
	     
	

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
