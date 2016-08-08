public class RentalMovie
{
   private String movieID;
   private String title;
   private int rentalFee;
   private boolean available;
   private String borrowerID;

   public RentalMovie(String movieID, String title, int rentalFee)
   {
      this.movieID = movieID;
      this.title = title;
      this.rentalFee = rentalFee;

      // initialise movie availability status to true to reflect the movie being
      // "available" initially
      this.available = true;

      // initialise borrower ID to default value
      this.borrowerID = "not on loan";
   }

   /*
    * Accessors (getters)
    *
    * Use these as needed in latter stages of the assignment.
    */

   public String getMovieID()
   {
      return movieID;
   }


   public boolean isAvailable()
   {
      return available;
   }

   public void setBorrowerID(String memberID)
   {
      this.borrowerID = memberID;
   }

   public void setAvailability(boolean isAvailable)
   {
      this.available = isAvailable;
   }

   public void borrowMovie(String memberID) throws MovieException
   {
      // reject borrowing of movie if it is not available
      if (available == false)
      {
         throw new MovieException("Error! - Movie is currently unavailable");
      }
      else
      {
         // movie has been borrowed so update loan status and borrower ID
         this.available = false;
         this.borrowerID = memberID;
      }
   }

   public double returnMovie(int daysBorrowed)
   {
      // reject return of movie if it is not currently on loan
      if (available == true)
      {
         return Double.NaN;
      }
      else
      {
         // movie has been borrowed so reset loan status and borrower ID
         this.available = true;
         this.borrowerID = "not on loan";

         // determine whether the movie was returned late
         int daysLate = daysBorrowed - 7;

         if (daysLate > 0)
         {
            // movie was returned late, so return fine
            return daysLate * 2.0;
         }
         else
         {
            // movie was returned on time - no fine applies
            return 0.0;
         }
      }
   }

   public void printDetails()
   {
      System.out.printf("%15s%s\n", "Movie ID:", movieID);
      System.out.printf("%15s%s\n", "Title:", title);
      System.out.printf("%15s$%d\n", "Rental Fee:", rentalFee);
      System.out.printf("%15s%b\n", "Available:", available);
      System.out.printf("%15s%s\n", "Borrower ID:", borrowerID);
   }

   public String getDetails()
   {
      return (movieID + "|" +
              title + "|" +
              rentalFee + "|" +
              available + "|" +
              borrowerID);
   }

}
