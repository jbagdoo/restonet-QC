package ca.usimage.restoqc;

import android.content.Context;
import ca.usimage.restoqc.AndroidSaxParser;




public abstract class RestoParserFactory {

//	static String feedUrl = "http://ville.montreal.qc.ca/pls/portal/portalcon.contrevenants_recherche?p_mot_recherche=,tous.2011";
//	static String feedUrl = "http://depot.ville.montreal.qc.ca/inspection-aliments-contrevenants/data.xml";
//	static String feedUrl =  "http://donnees.ville.montreal.qc.ca/storage/f/2014-01-19T15%3A54%3A11.508Z/inspection-aliments-contrevenants.xml";
//	static String feedUrl = "http://jbagdoo.crabdance.com";
	 
	public static RestoParser getParser() {
		
				return new AndroidSaxParser();
		
		
		
		}
	}


