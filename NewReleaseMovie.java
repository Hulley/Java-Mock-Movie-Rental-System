public class NewReleaseMovie extends RentalMovie
{
   private String reserverID;

   NewReleaseMovie(String movieID, String title)
   {
      //construct movie object with price = 6
      super(movieID,title,6);
      this.reserverID = "not on reserve";
   }

   public void setReserverID(String memberID)
   {
      this.reserverID = memberID;
   }

   public boolean reserveMovie(String memberID)
   {
      if (!isAvailable() && reserverID.equals("not on reserve"))
      {
         this.reserverID = memberID;
         return true;
      }
      else
         return false;
   }

   public void borrowMovie(String memberID) throws MovieException
   {
      if (reserverID.equals("not on reserve")|| reserverID.equals(memberID))
      {
         try
         {
            super.borrowMovie(memberID);
            this.reserverID = "not on reserve";
         }
         catch (MovieException me)
         {
            throw me;
         }
      }
      else
         throw new MovieException("Error! - Movie is reserved for other member");

   }

   public double returnMovie(int daysBorrowed)
   {
      // reject return of movie if it is not currently on loan
      if (isAvailable())
      {
         return Double.NaN;
      }
      else
      {
         // movie has been borrowed so reset loan status and borrower ID
         setAvailability(true);
         setBorrowerID("not on loan");

         // determine whether the movie was returned late
         int daysLate = daysBorrowed - 2;

         if (daysLate > 0)
         {
            // movie was returned late, so return fine
            return daysLate * 10.0;
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
      super.printDetails();
      System.out.printf("%15s%s\n", "Reserver ID:", reserverID);
   }

   public String getDetails()
   {
      return (super.getDetails() + "|" + reserverID);
   }

}
