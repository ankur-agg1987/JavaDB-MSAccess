package mydatabase;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DBconnectUcanacess {

    public DBconnectUcanacess() {
        JFrame f = new JFrame();
        f.setLayout(new GridLayout(5,2));
        JLabel n = new JLabel("Name");
        JTextField name = new JTextField();
        
        JLabel e = new JLabel("Email");
        JTextField email = new JTextField();
        
        JLabel p = new JLabel("Phone No.");
        JTextField phone = new JTextField();
        
        JButton save = new JButton("Save Record");
        JButton reset = new JButton("Reset Fields");
        
        f.add(n);
        f.add(name);
        f.add(e);
        f.add(email);
        f.add(p);
        f.add(phone);
        f.add(save);
        f.add(reset);
        
        f.setSize(300,300);
        
        f.setVisible(true);
        
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savedata(name.getText().toString(), email.getText().toString(),phone.getText().toString());
            }
        });
        
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.setText("");
                email.setText("");
                phone.setText("");
            }
        });
    }
    
    public static void main(String[] args) {
         
        new DBconnectUcanacess();
    }
    
    public void savedata(String a, String b, String c){
        String databaseURL = "jdbc:ucanaccess://e://Java//Contacts.accdb";
         
        try {
            
            Connection connection = DriverManager.getConnection(databaseURL);
            System.out.println("Driver detected and connection estabilshed");
            
            //create query as string object, ? is referred as placeholder
            String sql = "INSERT INTO Contacts (Full_Name, Email, Phone) VALUES (?, ?, ?)";
             
            // create a prepared statement
            PreparedStatement ps = connection.prepareStatement(sql);
            
            //update the placeholder of prepared statement
            ps.setString(1, a);
            ps.setString(2, b);
            ps.setString(3, c);
            
            // execute the query suing executeUpdate function which return int value of update.
            int row = ps.executeUpdate();
             
            if (row > 0) {
                System.out.println("----------------------Row has been inserted successfully.---------------------");
            }
            
            //Fetching the data from Contacts table
            sql = "SELECT * FROM Contacts";
             
            //create a simple statement
            Statement statement = connection.createStatement();
          
            //execute the query which returns the ResultSet object
            ResultSet result = statement.executeQuery(sql);
             
            //traverse the data from ResultSet object
            System.out.println("\n \n Fetch operation to access all content of record in Contact table");
            
            while (result.next()) {
                int id = result.getInt("Contact_ID");
                String fullname = result.getString("Full_Name");
                String email = result.getString("Email");
                String phone = result.getString("Phone");
                 
                System.out.println(id + ", " + fullname + ", " + email + ", " + phone);
            }
             
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
