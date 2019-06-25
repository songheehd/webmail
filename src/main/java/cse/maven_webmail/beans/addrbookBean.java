/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse.maven_webmail.beans;

/**
 *
 * @author LeeJH
 */
public class addrbookBean {
    private String id;
    private String username;
    private String addrname;
    private String addrmail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddrname() {
        return addrname;
    }

    public void setAddrname(String addrname) {
        this.addrname = addrname;
    }

    public String getAddrmail() {
        return addrmail;
    }

    public void setAddrmail(String addrmail) {
        this.addrmail = addrmail;
    }
    
    public addrbookBean() {
        
    }
    
    public addrbookBean(String id, String username, String addrname, String addrmail) {
        super();
        this.id = id;
        this.username = username;
        this.addrname = addrname;
        this.addrmail = addrmail;
        
    }
    
    @Override
    public String toString() {
        return "addrbookBean [id =" + id + ", username=" + username + ", addrname =" + addrname + ", addrmail =" + addrmail + "]";
    }
    
}
