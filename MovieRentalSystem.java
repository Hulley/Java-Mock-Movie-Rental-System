/*
 * class MovieRentalSystem
 *
 * Implements the data storage and functional requirements
 * for a menu-driven program that manages property sales.
 *
 * This is the start-up code for Assignment 3 and you should
 * work off this program - the features described in the
 * specification for Stages 2 and 4 should be implemented in
 * the corresponding helper methods included at the bottom of
 * this class.
 *
 */

import java.util.*;
import java.io.*;

public class MovieRentalSystem
{
   private static final RentalMovie[] movies = new RentalMovie[100];
   private static int movieCount = 0;

   private static final Scanner sc = new Scanner(System.in);

   public static void main(String[] args)
   {
      // At beginning of program, attempt to fill movies list from a database
      startSystem();

      String selection;

      // implementation of the program menu
      do
      {

         // print menu to screen
         System.out.println("*** Movie Rental System Menu ***");
         System.out.println();

         System.out.printf("%-25s%s\n", "Add Rental Movie", "A");
         System.out.printf("%-25s%s\n", "Display Movie List", "B");
         System.out.printf("%-25s%s\n", "Borrow Movie", "C");
         System.out.printf("%-25s%s\n", "Return Movie", "D");
         System.out.printf("%-25s%s\n", "Add New Release Movie", "E");
         System.out.printf("%-25s%s\n", "Reserve Movie", "F");
         System.out.printf("%-25s%s\n", "Exit Program", "X");
         System.out.println();

         // prompt user to enter selection
         System.out.print("Enter selection: ");
         selection = sc.nextLine();

         System.out.println();

         // validate selection input length
         if (selection.length() != 1)
         {
            System.out.println("Error - invalid selection!");
         }
         else
         {

            // process user's selection
            switch (selection.toUpperCase())
            {
               case "A":
                  addRentalMovie();
                  break;

               case "B":
                  displayMovieList();
                  break;

               case "C":
                  borrowMovie();
                  break;

               case "D":
                  returnMovie();
                  break;

               case "E":
                  addNewReleaseMovie();
                  break;

               case "F":
                  reserveMovie();
                  break;

               case "X":
                  exitSystem();
                  System.out.println("Exiting the program...");
                  break;

               default:
                  System.out.println("Error - invalid selection!");
            }
         }
         System.out.println();

      } while (!selection.equalsIgnoreCase("X"));

   }

   private static void addRentalMovie()
   {
      System.out.println("Add RentalMovie option selected");
      System.out.println();

      // prompt user for details for object constructor
      System.out.print("Enter movie ID: ");
      String movieID = sc.nextLine();
      System.out.print("Enter title: ");
      String title = sc.nextLine();
      System.out.print("Enter rental fee: ");
      int rentalFee = sc.nextInt();
      sc.nextLine(); //clear newline from scanner

      // create new rental object in movies array
      movies[movieCount] = new RentalMovie(movieID, title, rentalFee);
      movieCount +=1;
   }


   private static void displayMovieList()
   {
      System.out.println("Display Movie List option selected");
      System.out.println();

      for (int m = 0; m<movieCount; m++)
      {
         movies[m].printDetails();
         System.out.println();
      }

   }

   private static void borrowMovie()
   {
      System.out.println("Borrow Movie option selected");
      System.out.println();

      // prompt user for movie ID
      System.out.print("Enter movie ID: ");
      String movieID = sc.nextLine();

      boolean movieFound = false;
      for (int m=0; m<movieCount; m++)
      {
         if (movies[m].getMovieID().equalsIgnoreCase(movieID))
         {
            // matching movie found
            movieFound = true;
            System.out.print("Enter borrowers member ID: ");
            String memberID = sc.nextLine();

            // attempt to borrow movie
            try
            {
               movies[m].borrowMovie(memberID);
               System.out.println("Borrowing movie successful!");
            }
            catch (MovieException me)
            {
               // if unsuccessful print message
               System.out.println(me.getMessage());
            }
            break;
         }
      }
      if (!movieFound)
         System.out.println("Error - movie not found!");
   }

   private static void returnMovie()
   {
      System.out.println("Return Movie option selected");
      System.out.println();

      System.out.print("Enter movie ID: ");
      String movieID = sc.nextLine();

      boolean movieFound = false;
      for (int m=0; m<movieCount; m++)
      {
         // test whether a matching movie ID exists in movies
         if (movies[m].getMovieID().equalsIgnoreCase(movieID))
         {
            movieFound = true;
            System.out.print("Enter loan duration (days): ");
            int daysBorrowed = sc.nextInt();
            sc.nextLine(); //clear newline from scanner

            // test for successful return of movie
            double returnResult = movies[m].returnMovie(daysBorrowed);
            if (Double.isNaN(returnResult))
            {
               // if unsuccessful print error
               System.out.println("Error - returning movie unsuccessful!");
            }
            else
            {
               // print appropriate fee based on result from returning
               if (returnResult > 0)
                  System.out.println("Return Successful! Late fee: "+returnResult);
               else
                  System.out.println("Return Successful. No late fee");
            }
            break;
         }
      }
      if (!movieFound)
         System.out.println("Error - movie not found!");
   }

   private static void addNewReleaseMovie()
   {
      System.out.println("Add NewReleaseMovie option selected");
      System.out.println();

      // prompt user for details for object constructor
      System.out.print("Enter movie ID: ");
      String movieID = sc.nextLine();
      System.out.print("Enter title: ");
      String title = sc.nextLine();

      // create new rental object in movies array
      movies[movieCount] = new NewReleaseMovie(movieID, title);
      movieCount +=1;
   }

   private static void reserveMovie()
   {
      System.out.println("Reserve Movie option selected");
      System.out.println();

      // prompt user for movie ID
      System.out.print("Enter movie ID: ");
      String movieID = sc.nextLine();

      boolean movieFound = false;
      for (int m=0; m<movieCount; m++)
      {
         if (movies[m].getMovieID().equalsIgnoreCase(movieID))
         {
            // matching movie found
            movieFound = true;
            // test whether movie is a new release
            if (movies[m] instanceof NewReleaseMovie)
            {
               System.out.print("Enter reservers member ID: ");
               String memberID = sc.nextLine();
               if (((NewReleaseMovie) movies[m]).reserveMovie(memberID))
                  System.out.println("Reserving movie successful!");
               else
                  System.out.println("Error - reserving movie unsuccessful!");
                  break;
            }
            else
            {
               System.out.println("Error - only new release movies may be reserved!");
               break;
            }
         }
      }
      if (!movieFound)
         System.out.println("Error - movie not found!");
   }

   //method for pulling movies out of a database at start of program
   public static void startSystem()
   {
      try
      {
         // Pull Movies from database
         Scanner database = new Scanner(new FileReader("Database.txt"));
         while (database.hasNextLine())
         {
            String line = database.nextLine();
            StringTokenizer movieDetailReader = new StringTokenizer(line,"|");
            String movieID = movieDetailReader.nextToken();
            String title = movieDetailReader.nextToken();
            int rentalFee = Integer.parseInt(movieDetailReader.nextToken());
            String reserverID = "";
            boolean isNewReleaseMovie;
            boolean available = Boolean.parseBoolean(movieDetailReader.nextToken());
            String borrowerID = movieDetailReader.nextToken();

            // test that this entry represents a new release
            if (movieDetailReader.hasMoreTokens())
            {
               reserverID = movieDetailReader.nextToken();
               isNewReleaseMovie = true;
            }
            else
            {
               isNewReleaseMovie = false;
            }

            // test for chooosing correct constructor
            if (!isNewReleaseMovie)
            {
               movies[movieCount] = new RentalMovie(movieID,title,rentalFee);
            }
            else
            {
               movies[movieCount] = new NewReleaseMovie(movieID,title);
               ((NewReleaseMovie) movies[movieCount]).setReserverID(reserverID);
            }

            movies[movieCount].setAvailability((boolean) available);
            movies[movieCount].setBorrowerID(borrowerID);
            movieCount +=1;
         }
      }
      catch (FileNotFoundException fnfe)
      {
         System.out.println("Error! - movie database not found");
      }
   }

   //Umethod for writing to a database when exiting
   public static void exitSystem()
   {
      try
      {
      PrintWriter pw = new PrintWriter(new FileWriter("Database.txt"));
      for (int m=0; m<movieCount;m++)
         pw.print(movies[m].getDetails()+"\n");
         pw.close();
      }
      catch (IOException ioe)
      {
         System.out.println("Error! - writing to database failed");
         System.out.println(ioe.getMessage());
      }

   }


}
