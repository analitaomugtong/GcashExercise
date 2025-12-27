package gcashapp;

import java.sql.*;

public class Cashin {

    Connection con = DBConnection.getConnection();

    public boolean cashIn(int userId, String name, double amount){

        try{
            // 1. Get current balance
            String getBal = "SELECT amount FROM balance WHERE user_id=?";
            PreparedStatement ps1 = con.prepareStatement(getBal);
            ps1.setInt(1, userId);
            ResultSet rs = ps1.executeQuery();

            double currentBal = 0;
            if(rs.next()){
                currentBal = rs.getDouble("amount");
            }

            // 2. Update balance
            double newBal = currentBal + amount;
            String updateBal = "UPDATE balance SET amount=? WHERE user_id=?";
            PreparedStatement ps2 = con.prepareStatement(updateBal);
            ps2.setDouble(1, newBal);
            ps2.setInt(2, userId);
            ps2.executeUpdate();

            // 3. Insert transaction record
            String trans = "INSERT INTO transaction(amount,name,account_id,transferToID,transferFromID) VALUES(?,?,?,?,?)";
            PreparedStatement ps3 = con.prepareStatement(trans);
            ps3.setDouble(1, amount);
            ps3.setString(2, name);
            ps3.setInt(3, userId);
            ps3.setInt(4, userId);
            ps3.setInt(5, 0);
            ps3.executeUpdate();

            System.out.println("Cash-in successful! New Balance: ₱" + newBal);
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
