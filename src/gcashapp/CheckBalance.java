package gcashapp;

import java.sql.*;

public class CheckBalance {

    Connection con = DBConnection.getConnection();

    public double getBalance(int userId){
        try{
            String sql = "SELECT amount FROM balance WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("amount");
            } else {
                System.out.println("No balance record found.");
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
