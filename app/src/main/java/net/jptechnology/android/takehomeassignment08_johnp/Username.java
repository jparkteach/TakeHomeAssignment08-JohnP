package net.jptechnology.android.takehomeassignment08_johnp;

public class Username {
    private String username;
    private String password;
    private boolean isLegalAge;

    public Username() {
    } // Empty constructor is needed to read from Firebase

    public Username(String username, String password, boolean isLegalAge) {
        this.username = username;
        this.password = password;
        this.isLegalAge = isLegalAge;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLegalAge() {
        return isLegalAge;
    }
}