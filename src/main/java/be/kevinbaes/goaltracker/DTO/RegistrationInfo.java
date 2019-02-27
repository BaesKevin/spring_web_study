package be.kevinbaes.goaltracker.DTO;

import be.kevinbaes.goaltracker.validation.annotation.StrongPassword;

import javax.validation.constraints.NotBlank;

public class RegistrationInfo {
    @NotBlank(message = "Username can't be empty")
    private String username;
    @NotBlank(message = "Password can't be empty")
    @StrongPassword(message = "Password is not strong enough")
    private String password;
    @NotBlank(message = "Password repeat can't be empty")
    private String passwordRepeat;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
