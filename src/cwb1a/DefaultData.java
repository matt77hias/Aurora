package cwb1a;

import java.util.*;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * Hard coded information (preset achievements,  preset courses) for the application.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class DefaultData {
	// Takes control that the methods of this class are used just once
	private static boolean executed = false;
	
	/**
	 * The constructor is private, because not allowed to create objects from this class.
	 */
	private DefaultData() {
	}
	
	/**
	 * Method that creates a default data in the database. This are preset courses and activities.
	 */
	public static void createDefaultData() {
		if(!executed){
			createCourses();
			createDivisionStructure();
		}
		executed = true;		
	}
	
	/**
	 * Adds preset courses to the database.
	 */
	private static void createCourses() {
		DivisionRegistry.getInstance().add(new Course("Analyse 1", "Paul Dierckx en Stefan Vandewalle", "H01A0b", 6));
		DivisionRegistry.getInstance().add(new Course("Toegepaste algebra", "Joos Vandewalle", "H01A4a", 5));
		DivisionRegistry.getInstance().add(new Course("Probleemoplossen en Ontwerpen 1", "geen info beschikbaar", "H01B9a", 4));
		DivisionRegistry.getInstance().add(new Course("Algemene en technische scheikunde", "Claude Creemers", "H01A8a", 7));
		DivisionRegistry.getInstance().add(new Course("Toegepaste mechanica deel 1", "Joos Vandersloten en Dirk Vandepitte", "H01B0A", 5));
		DivisionRegistry.getInstance().add(new Course("Methodiek van de informatica","Philip Dutre", "H01B6b", 6));
		DivisionRegistry.getInstance().add(new Course("Analyse 2","Paul Dierckx en Stefan Vandewalle", "H01A2a", 5));
		DivisionRegistry.getInstance().add(new Course("Algemene natuurkunde", "geen info beschikbaar", "H01B2a", 7));
		DivisionRegistry.getInstance().add(new Course("Thermodynamica", "Patrick Wollants en Tine Baelmans", "H01B4a", 3));
		DivisionRegistry.getInstance().add(new Course("Inleiding tot de materiaalkunde", "Bert Verlinden en Bart Blanpain", "H01D0a", 3));
		DivisionRegistry.getInstance().add(new Course("Elektrische netwerken", "Wim Dehaene", "H01Z2a", 3));
		DivisionRegistry.getInstance().add(new Course("Probleemoplossen en Ontwerpen 2", "geen info beschikbaar", "H01C2a", 3));
		DivisionRegistry.getInstance().add(new Course("Numerieke Wiskunde","Dirk Roose en Karl van Meerbergen","H01D8a", 4));
		DivisionRegistry.getInstance().add(new Course("Kansrekenen en Statistiek", "Jozef Van Dyck", "H01A6a", 3));
		DivisionRegistry.getInstance().add(new Course("Organische Scheikunde","Mario De Smet", "H01C6a", 3));
		DivisionRegistry.getInstance().add(new Course("Toegepaste Mechanica Deel 2","Jos Vandersloten", "H01C8a", 5));
		DivisionRegistry.getInstance().add(new Course("Economie", "Geert Dhaene", "H01D7a", 3));
		DivisionRegistry.getInstance().add(new Course("Analyse 3", "Paul Dierckx, Lutgarde Beernaert", "H01W0a", 3));
		DivisionRegistry.getInstance().add(new Course("Informatie-overdracht en -verwerking", "Bart Nauwelaers", "H01D2a", 5));
		DivisionRegistry.getInstance().add(new Course("Probleemoplossen en Ontwerpen 3", "geen informatie beschikbaar", "H01D4b", 4));
		DivisionRegistry.getInstance().add(new Course("Bedrijfskunde", "D. Cattrysse", "H01F2A", 6));
		DivisionRegistry.getInstance().add(new Course("Religie, zingeving en levensbeschouwing", "J. De Tavernier", "A04D5A", 3));
		DivisionRegistry.getInstance().add(new Course("Probabilistisch ontwerpen","J. Van Dyck","H01F5A",3));
		DivisionRegistry.getInstance().add(new Course("Hydraulica","E. Toorman","H08T9A",6));
		DivisionRegistry.getInstance().add(new Course("Sterkteleer 2","G. Lombaert en G. De Roeck","H08W1A",3));
		DivisionRegistry.getInstance().add(new Course("Constructiematerialen","J. Van Humbeeck","H9XA1A",2));
		DivisionRegistry.getInstance().add(new Course("Bouwfysica","H. Janssen","H01I0A",6));
		DivisionRegistry.getInstance().add(new Course("Sterkteleer 3/Bouwmechanica","Lombaert en G. De Roeck","H01I0A",6));
		DivisionRegistry.getInstance().add(new Course("Verkeerskunde","B. Immers en A. Beeldens","H01I6A",3));
		DivisionRegistry.getInstance().add(new Course("PenO: Bouwkunde 1","geen informatie beschikbaar","H01Q8B",3));
		DivisionRegistry.getInstance().add(new Course("PenO: Bouwkunde 2","geen informatie beschikbaar","H01R0C",6));
		DivisionRegistry.getInstance().add(new Course("Beton, deel 1","L. Vandewalle","H03P7A",3));
		DivisionRegistry.getInstance().add(new Course("Ontwerp en berekening van staalconstructies","geen informatie beschikbaar","H08W2A",3));
		DivisionRegistry.getInstance().add(new Course("Toegepaste discrete algebra","J. Vandewalle en B. Preneel","H01G5A",3));
		DivisionRegistry.getInstance().add(new Course("Gegevensbanken","B. Berendt, E. Duval en K. Verbert ","H01O9A",6));
		DivisionRegistry.getInstance().add(new Course("Objectgericht programmeren","E. Steegmans","H01P1A",6));
		DivisionRegistry.getInstance().add(new Course("Computerarchitectuur en systeemsoftware","Y. Berbers","H01P5A",6));
		DivisionRegistry.getInstance().add(new Course("Artificiële intelligentie","M. Bruynooghe en D. De Schreye ","H06U1A",6));
		DivisionRegistry.getInstance().add(new Course("PenO: Computerwetenschappen","H. Blockeel, E. Duval en R. Vandebril","H01Q3C",9));
		DivisionRegistry.getInstance().add(new Course("Computernetwerken","C. Huygens","G0Q43A",6));
		DivisionRegistry.getInstance().add(new Course("Numerieke modellering en benadering","M. Van Barel en S. Vandewalle","H01P3A",6));
	}
	
	/**
	 * Adds subdivisions and a subdivision hierarchy to the database.
	 */
	private static void createDivisionStructure() {
		Subdivision general = new Subdivision("General", "General");
		Subdivision main1 = new Subdivision("Katholieke Universiteit Leuven","KUL");
		Subdivision main2 = new Subdivision("Universiteit Antwerpen","UA");
		Subdivision fac1 = new Subdivision("Faculteit IngenieursWetenschappen","IngWet");
		Subdivision fac2 = new Subdivision("Faculteit Wetenschappen", "Wet");
		Subdivision fac3 = new Subdivision("Faculteit Bio-Ingenieurswetenschappen", "BioIngWet");
		Subdivision fac4 = new Subdivision("Faculteit Economie en Bedrijfswetenschappen","EcoBedr");
		Subdivision fac5 = new Subdivision("Faculteit Rechtsgeleerdheid","Recht");
		Subdivision y1 = new Subdivision("Bachelor 1","Ba1IngWet");
		Subdivision y2 = new Subdivision("Bachelor 2","Ba2IngWet");
		Subdivision y3 = new Subdivision("Bachelor 3","Ba3IngWet");
		Subdivision y4 = new Subdivision("Master 1","Ma1IngWet");
		Subdivision y5 = new Subdivision("Master 2","Ma2IngWet");
		Subdivision y6 = new Subdivision("Master na Master","ManamaIngWet");
		Subdivision div1 = new Subdivision("Computerwetenschappen-Elektrotechniek","CWELEC");
		Subdivision div2 = new Subdivision("Elektrotechniek-Computerwetenschappen","ELECCW");
		Subdivision div3 = new Subdivision("Computerwetenschappen-Werktuigkunde","CWWTK");
		Subdivision div4 = new Subdivision("Werktuigkunde-Computerwetenschappen","WTKCW");
		Subdivision div5 = new Subdivision("Chemische Ingenieurstechnieken-Materiaalkunde","CHEMTK");
		Subdivision div6 = new Subdivision("Materiaalkunde - Chemische Ingenieurstechnieken","MTKCHE");
		Subdivision div7 = new Subdivision("Werktuigkunde - Materiaalkunde","WTKMTK");
		Subdivision group1 = new Subdivision("CWEL1","1CWEL1");
		Subdivision group2 = new Subdivision("CWEL2","1CWEL2");
		div1.add(group1.getId());
		div1.add(group2.getId());
		y2.add(div1.getId());
		y2.add(div2.getId());
		y2.add(div3.getId());
		y2.add(div4.getId());
		y2.add(div5.getId());
		y2.add(div6.getId());
		y2.add(div7.getId());
		fac1.add(y1.getId());
		fac1.add(y2.getId());
		fac1.add(y3.getId());
		fac1.add(y4.getId());
		fac1.add(y5.getId());
		fac1.add(y6.getId());
		main1.add(fac1.getId());
		main1.add(fac2.getId());
		main1.add(fac3.getId());
		main1.add(fac4.getId());
		main1.add(fac5.getId());
		general.add(main1.getId());
		general.add(main2.getId());
		DivisionRegistry.getInstance().add(main1);
		DivisionRegistry.getInstance().add(main2);
		DivisionRegistry.getInstance().add(fac1);
		DivisionRegistry.getInstance().add(fac2);
		DivisionRegistry.getInstance().add(fac3);
		DivisionRegistry.getInstance().add(fac4);
		DivisionRegistry.getInstance().add(fac5);
		DivisionRegistry.getInstance().add(y1);
		DivisionRegistry.getInstance().add(y2);
		DivisionRegistry.getInstance().add(y3);
		DivisionRegistry.getInstance().add(y4);
		DivisionRegistry.getInstance().add(y5);
		DivisionRegistry.getInstance().add(y6);
		DivisionRegistry.getInstance().add(div1);
		DivisionRegistry.getInstance().add(div2);
		DivisionRegistry.getInstance().add(div3);
		DivisionRegistry.getInstance().add(div4);
		DivisionRegistry.getInstance().add(div5);
		DivisionRegistry.getInstance().add(div6);
		DivisionRegistry.getInstance().add(div7);
		DivisionRegistry.getInstance().add(group1);
		DivisionRegistry.getInstance().add(group2);
		DivisionRegistry.getInstance().add(general);
	}
}