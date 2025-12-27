package gcashapp;

import java.sql.*;
import java.security.MessageDigest;

public class UserAuthentication {

    Connection con = DBConnection.getConnection();

    // Encrypt PIN
    private String hashPin(String pin) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(pin.getBytes());
        StringBuilder sb = new StringBuilder();
        for(byte b: bytes){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // REGISTER
    public boolean register(String name, String email, String number, String pin) {
     if(name.isEmpty() || email.isEmpty() || number.isEmpty()
   || !pin.matches("\\d{4}")){
    System.out.println("PIN must be exactly 4 digits!");
    return false;
}

        try{
            String sql = "INSERT INTO users(name,email,number,pin) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,number);
            ps.setString(4, hashPin(pin));
            ps.executeUpdate();
            System.out.println("Registration successful!");
            return true;
        }catch(Exception e){
            System.out.println("User already exists!");
        }
        return false;
    }

    // LOGIN
    public int login(String number, String pin){
        try{
            String sql = "SELECT id,pin FROM users WHERE number=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                if(rs.getString("pin").equals(hashPin(pin))){
                    System.out.println("Login successful!");
                    return rs.getInt("id");
                }else{
                    System.out.println("Incorrect PIN!");
                }
            }else{
                System.out.println("Account not found!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    // CHANGE PIN
    public boolean changePin(int id, String newPin){
        if(newPin.length()!=4){
            System.out.println("PIN must be 4 digits!");
            return false;
        }

        try{
            String sql="UPDATE users SET pin=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, hashPin(newPin));
            ps.setInt(2,id);
            ps.executeUpdate();
            System.out.println("PIN changed successfully!");
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // LOGOUT
    public void logout(){
        System.out.println("User logged out successfully.");
    }
}
