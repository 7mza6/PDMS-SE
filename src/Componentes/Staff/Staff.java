package Componentes.Staff;

import Componentes.Users.User;

public class Staff extends User {
    private String branch;

    public Staff(int Id,String firstName, String lastName, String dateOfBirth, String gender, String address, String celPhone, String telPhone, String email, String username, String password, String role,String branch ) {
        super(Id,firstName, lastName, dateOfBirth, gender, address, celPhone, telPhone, email, username, password, role);
        this.branch = branch;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    // Getters and setters for all inherited fields using super

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public String getDateOfBirth() {
        return super.getDateOfBirth();
    }

    @Override
    public void setDateOfBirth(String dateOfBirth) {
        super.setDateOfBirth(dateOfBirth);
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    @Override
    public void setGender(String gender) {
        super.setGender(gender);
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public String getCelPhone() {
        return super.getCelPhone();
    }

    @Override
    public void setCelPhone(String celPhone) {
        super.setCelPhone(celPhone);
    }

    @Override
    public String getTelPhone() {
        return super.getTelPhone();
    }

    @Override
    public void setTelPhone(String telPhone) {
        super.setTelPhone(telPhone);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getRole() {
        return super.getRole();
    }

    @Override
    public void setRole(String role) {
        super.setRole(role);
    }
}
