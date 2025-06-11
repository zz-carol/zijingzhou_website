/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
Carol Zhou
*/

// Necessary imports:
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

// Class ArtistBrowser represents a JDBC application that enables navigating
// data about artists, stored in a PostgreSQL database
public class ArtistBrowser {

	PreparedStatement prepStmt;
  ResultSet rs;
  String queryString;

	/* An instance variable representing a connection to the database */
	private Connection connection;

	/**
	 * The constructor loads the JDBC driver. No need to modify this.
	 */
	public ArtistBrowser() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Failed to locate the JDBC driver.");
		}
	}

	/**
	* Establishes a connection to be used for this session, assigning it to
	* the private instance variable 'connection'.
	*
	* @param  url       the url to the database
	* @param  username  the username to connect to the database
	* @param  password  the password to connect to the database
	* @return           true if the connection is successful, false otherwise
	*/
	public boolean connectDB(String url, String username, String password) {
		try {
			this.connection = DriverManager.getConnection(url, username, password);
			return true;
		} catch (SQLException se) {
			System.err.println("SQL Exception: " + se.getMessage());
			return false;
		}
	}

	/**
	* Closes the database connection.
	*
	* @return true if the closing was successful, false otherwise.
	*/
	public boolean disconnectDB() {
		try {
			this.connection.close();
		return true;
		} catch (SQLException se) {
			System.err.println("SQL Exception: " + se.getMessage());
			return false;
		}
	}


	/**
	 * Returns a sorted list of the names of all musicians who were part of a band
	 * at some point between a given start year and an end year (inclusive).
 	 *
	 * Returns an empty list if no musicians match, or if the given timeframe is invalid.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *		Use prepared statements.
	 *
	 * @param startYear
	 * @param endYear
	 * @return  a sorted list of artist names
	 */
	public ArrayList<String> findArtistsInBands(int startYear, int endYear) {

		if (endYear < startYear) {
			System.out.println("Error: endYear must be greater than startYear.");
      return new ArrayList<>();
    }

    if (startYear < 0 || endYear < 0) {
        System.out.println("Error: Years cannot be negative.");
        return new ArrayList<>();
    }

		ArrayList<String> result = new ArrayList<>();

    try {
	    queryString = "SELECT DISTINCT name "
							    + "FROM WasInBand NATURAL JOIN Artist "
							    + "WHERE (start_year >= ? AND end_year <= ?) OR "
									+ "(start_year < ? AND end_year > ?) OR "
									+ "(start_year < ? AND end_year >= ? AND end_year <= ?) OR "
									+ "(start_year >= ? AND start_year <= ? AND end_year > ?)";

			prepStmt = connection.prepareStatement(queryString);

	    prepStmt.setInt(1, startYear);
      prepStmt.setInt(2, endYear);
      prepStmt.setInt(3, startYear);
      prepStmt.setInt(4, endYear);
      prepStmt.setInt(5, startYear);
      prepStmt.setInt(6, startYear);
      prepStmt.setInt(7, endYear);
      prepStmt.setInt(8, startYear);
      prepStmt.setInt(9, endYear);
      prepStmt.setInt(10, endYear);

	    rs = prepStmt.executeQuery();

	    while (rs.next()) {
	    	result.add(rs.getString("name"));
      }
			Collections.sort(result);

			rs.close();
      prepStmt.close();

	  } catch (SQLException se) {
      System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
    }

		return result;
	}


	/**
	 * Returns a sorted list of the names of all musicians and bands
	 * who released at least one album in a given genre.
	 *
	 * Returns an empty list if no such genre exists or no artist matches.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *		Use prepared statements.
	 *
	 * @param genre  the genre to find artists for
	 * @return       a sorted list of artist names
	 */
	public ArrayList<String> findArtistsInGenre(String genre) {
		ArrayList<String> result = new ArrayList<>();

    try{
	    queryString = "SELECT DISTINCT name "
							    + "FROM Artist NATURAL JOIN Role "
							    + "WHERE role IN ('Musician','Band') AND "
									+ "artist_id IN (SELECT artist_id "
									+ "FROM Album NATURAL JOIN Genre "
									+ "WHERE genre = ?)";

			prepStmt = connection.prepareStatement(queryString);

	    prepStmt.setString(1, genre);

	    rs = prepStmt.executeQuery();

	    while (rs.next()) {
	    	result.add(rs.getString("name"));
      }
			Collections.sort(result);

			rs.close();
      prepStmt.close();

	  } catch (SQLException se) {
      System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
    }
    
		return result;
	}


	/**
	 * Returns a sorted list of the names of all collaborators
	 * (either as a main artist or guest) for a given artist.
	 *
	 * Returns an empty list if no such artist exists or the artist
	 * has no collaborators.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *		Use prepared statements.
	 *
	 * @param artist  the name of the artist to find collaborators for
	 * @return        a sorted list of artist names
	 */
	public ArrayList<String> findCollaborators(String artist) {
		ArrayList<String> result = new ArrayList<>();

    try{
	    queryString = "(SELECT a1.name AS name "
									+ "FROM (Collaboration JOIN Artist ON artist1 = artist_id) AS t1 "
									+ "JOIN Artist a1 ON t1.artist2 = a1.artist_id "
									+ "WHERE t1.name = ?) "
									+ "UNION "
									+ "(SELECT t1.name AS name "
									+ "FROM (Collaboration JOIN Artist ON artist1 = artist_id) AS t1 "
									+ "JOIN Artist a1 ON t1.artist2 = a1.artist_id "
									+ "WHERE a1.name = ?)";

			prepStmt = connection.prepareStatement(queryString);

	    prepStmt.setString(1, artist);
	    prepStmt.setString(2, artist);

	    rs = prepStmt.executeQuery();

	    while (rs.next()) {
	    	result.add(rs.getString("name"));
      }
			Collections.sort(result);

			rs.close();
      prepStmt.close();

	  } catch (SQLException se) {
      System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
    }

		return result;
	}


	/**
	 * Returns a sorted list of the names of all songwriters
	 * who wrote songs for a given artist (the given artist is excluded).
	 *
	 * Returns an empty list if no such artist exists or the artist
	 * has no other songwriters other than themself.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param artist  the name of the artist to find the songwriters for
	 * @return        a sorted list of songwriter names
	 */
	public ArrayList<String> findSongwriters(String artist) {
		ArrayList<String> result = new ArrayList<>();

    try{
	    queryString = "(SELECT a1.name AS name "
									+ "FROM (((Song NATURAL JOIN BelongsToAlbum) AS t1 "
									+ "JOIN Album ON t1.album_id = Album.album_id) AS t2 "
									+ "NATURAL JOIN Artist) AS t3 "
									+ "JOIN Artist a1 ON t3.songwriter_id = a1.artist_id "
									+ "WHERE t3.name = ?) "
									+ "EXCEPT "
									+ "(SELECT name "
									+ "FROM Artist "
									+ "WHERE name = ?)";

			prepStmt = connection.prepareStatement(queryString);

	    prepStmt.setString(1, artist);
	    prepStmt.setString(2, artist);

	    rs = prepStmt.executeQuery();

	    while (rs.next()) {
	    	result.add(rs.getString("name"));
      }
			Collections.sort(result);

			rs.close();
      prepStmt.close();

	  } catch (SQLException se) {
      System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
    }

		return result;
	}


	/**
	 * Returns a sorted list of the names of all common acquaintances
	 * for a given pair of artists.
	 *
	 * Returns an empty list if either of the artists does not exist,
	 * or they have no acquaintances.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param artist1  the name of the first artist to find acquaintances for
	 * @param artist2  the name of the second artist to find acquaintances for
	 * @return         a sorted list of artist names
	 */
	public ArrayList<String> findCommonAcquaintances(String artist1, String artist2) {
		ArrayList<String> result = new ArrayList<>();
    ArrayList<String> artist1Acquaintances = new ArrayList<>();
    ArrayList<String> artist2Acquaintances = new ArrayList<>();

    artist1Acquaintances.addAll(findCollaborators(artist1));
    artist1Acquaintances.addAll(findSongwriters(artist1));
      
		artist2Acquaintances.addAll(findCollaborators(artist2));
    artist2Acquaintances.addAll(findSongwriters(artist2));
      
    artist1Acquaintances.retainAll(artist2Acquaintances); // Intersect li1 and li2


    // remove duplicates
    for (String name : artist1Acquaintances) {
      if (!result.contains(name)) {
        result.add(name);
      }
    }

		Collections.sort(result);

		return result;
	}


	/**
	 * Returns true if two artists have a collaboration path connecting
	 * them in the database (see A3 handout for our definition of a path).
	 * For example, artists `Z' and `Usher' are considered connected even though
	 * they have not collaborated directly on any song, because 'Z' collaborated
	 * with `Alicia Keys' who in turn had collaborated with `Usher', therefore there
	 * is a collaboration path connecting `Z' and `Usher'.
	 *
	 * Returns false if there is no collaboration path at all between artist1 and artist2
	 * or if either of them do not exist in the database.
	 *
	 * @return    true iff artist1 and artist2 have a collaboration path connecting them
	 */
	public boolean artistConnectivity(String artist1, String artist2) {
		try {
      // check if both artists exist in the database
      String checkArtistsQuery = "SELECT COUNT(*) "
										      		 + "FROM Artist "
										        	 + "WHERE name in (?, ?)";
      PreparedStatement checkStmt = connection.prepareStatement(checkArtistsQuery);
      checkStmt.setString(1, artist1);
      checkStmt.setString(2, artist2);

      ResultSet checkRS = checkStmt.executeQuery();
      while(checkRS.next()) {
				if (checkRS.getInt(1) < 2) {
				  return false;
      	}
     	}
      checkRS.close();
      checkStmt.close();

      // build a graph of the connection
      queryString = "SELECT t1.name, a1.name "
									+ "FROM (Collaboration JOIN Artist ON artist1 = artist_id) AS t1 "
									+ "JOIN Artist a1 ON t1.artist2 = a1.artist_id";
      
      prepStmt = connection.prepareStatement(queryString);
      rs = prepStmt.executeQuery();

      Map<String, List<String>> graph = new HashMap<>();
      while (rs.next()) {
        String artistA = rs.getString(1);
        String artistB = rs.getString(2);

        graph.putIfAbsent(artistA, new ArrayList<>());
        graph.putIfAbsent(artistB, new ArrayList<>());

        graph.get(artistA).add(artistB);
        graph.get(artistB).add(artistA);
      }

      // BFS to find connectivity
      Queue<String> queue = new LinkedList<>();
      Set<String> visited = new HashSet<>();

      queue.add(artist1);
      visited.add(artist1);

      while (!queue.isEmpty()) {
        String currentArtist = queue.poll();

        if (currentArtist.equals(artist2)) {
          return true;
        }
        
        List<String> neighbors = graph.getOrDefault(currentArtist, Collections.emptyList());
        for (String neighbor : neighbors) {
          if (!visited.contains(neighbor)) {
            visited.add(neighbor);
            queue.add(neighbor);
          }
        }
      }

      rs.close();
      prepStmt.close();

    } catch (SQLException se) {
    	System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
    }

    return false;

	}


	public static void main(String[] args) {

		if( args.length < 2 ){
			System.out.println("Usage: java ArtistBrowser <userName> <password>");
			return;
		}

		String user = args[0];
		String pass = args[1];

		ArtistBrowser a3 = new ArtistBrowser();

		String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=artistdb";
		a3.connectDB(url, user, pass);

		System.err.println("\n----- ArtistsInBands -----");
    ArrayList<String> res = a3.findArtistsInBands(1990,1999);
    for (String s : res) {
      System.err.println(s);
    }

		System.err.println("\n----- ArtistsInGenre -----");
    res = a3.findArtistsInGenre("Rock");
    for (String s : res) {
      System.err.println(s);
    }

		System.err.println("\n----- Collaborators -----");
		res = a3.findCollaborators("Usher");
		for (String s : res) {
		  System.err.println(s);
		}

		System.err.println("\n----- Songwriters -----");
	        res = a3.findSongwriters("Justin Bieber");
		for (String s : res) {
		  System.err.println(s);
		}

		System.err.println("\n----- Common Acquaintances -----");
		res = a3.findCommonAcquaintances("Jaden Smith", "Miley Cyrus");
		for (String s : res) {
		  System.err.println(s);
		}

		System.err.println("\n----- artistConnectivity -----");
		String a1 = "Z", a2 = "Usher";
		boolean areConnected = a3.artistConnectivity(a1, a2);
		System.err.println("Do artists " + a1 + " and " + a2 + " have a collaboration path connecting them? Answer: " + areConnected);
		
		a3.disconnectDB();
	}

}
