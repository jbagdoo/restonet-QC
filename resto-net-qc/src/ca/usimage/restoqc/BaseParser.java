package ca.usimage.restoqc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.xml.sax.InputSource;

import android.content.Context;
import android.util.Xml;

public abstract class BaseParser   implements RestoParser {

	// names of the XML tags
	static final String CONTREVENANT = "contrevenant";
	static final String PROPRIETAIRE = "proprietaire";
	static final String CATEGORIE = "categorie";
	static final String ETABLISSEMENT = "etablissement";
	static final String ADRESSE = "adresse";
	static final String VILLE = "ville";
	static final String DESCRIPTION = "description";
	static final String DATE_INFRACTION = "date_infraction";
	static final String DATE_JUGEMENT = "date_jugement";
	static final String MONTANT = "montant";
	
	
	
  Context mC;
	
	protected BaseParser(Context context){
		this.mC = context;
	
	}

	
	protected InputStream getInputStream() {
		try {
			InputStream is = this.mC.getAssets().open("inspection-aliments-contrevenants.xml");
			
		
			return is;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
