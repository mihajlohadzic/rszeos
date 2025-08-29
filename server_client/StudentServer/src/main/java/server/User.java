/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author Mihajlo
 */
public class User {
    private String username;
    private String password;
    private String role;
    
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role.toLowerCase();
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    public String getRole() { 
        return role; 
    }

    @Override
    public String toString(){
        return username + " (" + role + ")";
    }
}
