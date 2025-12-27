package gcashapp;

import java.sql.*;

public class Transactions {

    Connection con = DBConnection.getConnection();

    // View ALL transactions
    public void viewAll(){
        try{
            String sql = "SELECT * FROM transaction";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== ALL TRANSACTIONS =====");
            while(rs.next()){
                display(rs);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // View ALL transactions of specific USER
    public void viewUserAll(int userId){
        try{
            String sql = "SELECT * FROM transaction WHERE account_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== YOUR TRANSACTIONS =====");
            while(rs.next()){
                display(rs);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // View specific transaction by TRANSACTION ID
    public void viewTransaction(int transId){
        try{
            String sql = "SELECT * FROM transaction WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, transId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                System.out.println("\n===== TRANSACTION DETAILS =====");
                display(rs);
            }else{
                System.out.println("Transaction not found.");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Display format
   private void display(ResultSet rs) throws Exception{

    String getSender = """
        SELECT number FROM users WHERE id=?
    """;
    PreparedStatement ps1 = con.prepareStatement(getSender);
    ps1.setInt(1, rs.getInt("transferFromID"));
    ResultSet r1 = ps1.executeQuery();
    String fromNum = r1.next() ? r1.getString("number") : "SYSTEM";

    String getReceiver = """
        SELECT number FROM users WHERE id=?
    """;
    PreparedStatement ps2 = con.prepareStatement(getReceiver);
    ps2.setInt(1, rs.getInt("transferToID"));
    ResultSet r2 = ps2.executeQuery();
    String toNum = r2.next() ? r2.getString("number") : "SYSTEM";

    System.out.println("------------------------------");
    System.out.println("Transaction ID : " + rs.getInt("id"));
    System.out.println("Amount         : ₱" + rs.getDouble("amount"));
    System.out.println("Name           : " + rs.getString("name"));
    System.out.println("From Number    : " + fromNum);
    System.out.println("To Number      : " + toNum);
    System.out.println("Date           : " + rs.getTimestamp("date"));
}

}
