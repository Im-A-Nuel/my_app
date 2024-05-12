package org.appmanajemenvoucher.amv01;

import java.io.*;

public class SessionManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SESSION_FILE = "session.ser";

    private static volatile SessionManager instance;
    private boolean isLoggedIn;
    private String username;
    private String name;

    private SessionManager() {
        isLoggedIn = false;
    }

    public static SessionManager getInstance() {
        if(instance == null){
            synchronized (SessionManager.class){
                if(instance == null){
                    instance = new SessionManager();
                    instance.createSessionFile();
                }
            }
        }
        return instance;
    }

    public void createSessionFile() {
        File file = new File(SESSION_FILE);
        if(!file.exists()){
            saveSession();
        }else{
            loadSession();
        }
    }

    private void loadSession() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))){
            SessionManager sessionManager = (SessionManager) ois.readObject();
            this.isLoggedIn = sessionManager.isLoggedIn;
            this.username = sessionManager.username;
            this.name = sessionManager.name;
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Error loading session " + e.getMessage());
        }
    }

    private void saveSession() {
        try(ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(SESSION_FILE))){
            oos.writeObject(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void login(String username, String name) {
        this.isLoggedIn = true;
        this.username = username;
        this.name = name;
        saveSession();
    }

    public void logout() {
        isLoggedIn = false;
        // Clear username and name on logout
        this.username = null;
        this.name = null;
        saveSession();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
