package gcashapp;

import java.sql.*;

public class CashTransfer {

    Connection con = DBConnection.getConnection();

    public boolean cashTransfer(int fromUserId, int toUserId, double amount){

        if(fromUserId == toUserId){
            System.out.println("You cannot transfer to your own account.");
            return false;
        }

        if(amount <= 0){
            System.out.println("Invalid transfer amount.");
            return false;
        }

        try{
            con.setAutoCommit(false); // start transaction

            // Check sender balance
            String getSenderBal = "SELECT amount FROM balance WHERE user_id=?";
            PreparedStatement ps1 = con.prepareStatement(getSenderBal);
            ps1.setInt(1, fromUserId);
            ResultSet rs1 = ps1.executeQuery();

            if(!rs1.next()){
                System.out.println("Sender account not found.");
                return false;
            }

            double senderBal = rs1.getDouble("amount");

            if(senderBal < amount){
                System.out.println("Insufficient balance.");
                return false;
            }

            // Check receiver existence
            String checkReceiver = "SELECT id FROM users WHERE id=?";
            PreparedStatement ps2 = con.prepareStatement(checkReceiver);
            ps2.setInt(1, toUserId);
            ResultSet rs2 = ps2.executeQuery();

            if(!rs2.next()){
                System.out.println("Receiver account not found.");
                return false;
            }

            // Deduct sender
            String deduct = "UPDATE balance SET amount=amount-? WHERE user_id=?";
            PreparedStatement ps3 = con.prepareStatement(deduct);
            ps3.setDouble(1, amount);
            ps3.setInt(2, fromUserId);
            ps3.executeUpdate();

            // Add receiver
            String add = "UPDATE balance SET amount=amount+? WHERE user_id=?";
            PreparedStatement ps4 = con.prepareStatement(add);
            ps4.setDouble(1, amount);
            ps4.setInt(2, toUserId);
            ps4.executeUpdate();

            // Insert transaction record
            String trans = "INSERT INTO transaction(amount,name,account_id,transferToID,transferFromID) VALUES(?,?,?,?,?)";
            PreparedStatement ps5 = con.prepareStatement(trans);
            ps5.setDouble(1, amount);
            ps5.setString(2, "Transfer");
            ps5.setInt(3, fromUserId);
            ps5.setInt(4, toUserId);
            ps5.setInt(5, fromUserId);
            ps5.executeUpdate();

            con.commit();
            System.out.println("Transfer successful!");
            return true;

        }catch(Exception e){
            try{ con.rollback(); }catch(Exception ex){}
            e.printStackTrace();
        }
        return false;
    }
    public boolean cashTransferByNumber(int fromUserId, String toNumber, double amount){

    if(amount <= 0){
        System.out.println("Invalid transfer amount.");
        return false;
    }

    try{
        // Hanapin ang receiver user id
        String findUser = "SELECT id FROM users WHERE number=?";
        PreparedStatement ps = con.prepareStatement(findUser);
        ps.setString(1, toNumber);
        ResultSet rs = ps.executeQuery();

        if(!rs.next()){
            System.out.println("Receiver account not found.");
            return false;
        }

        int toUserId = rs.getInt("id");
        return cashTransfer(fromUserId, toUserId, amount);

    }catch(Exception e){
        e.printStackTrace();
    }
    return false;
}

}
