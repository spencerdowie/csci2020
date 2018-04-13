package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.apache.commons.validator.routines.EmailValidator;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class Controller {
    @FXML private Label emailValidLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private DatePicker dobField;

    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String dob;

    public void register(ActionEvent e)
    {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy - MM - dd");

        username = usernameField.getText();
        password = passwordField.getText();
        fullName = fullNameField.getText();
        email = emailField.getText();
        phone = phoneField.getText();
        phone = phone.substring(0, 3)+"-"+phone.substring(3,6)+"-"+phone.substring(6,10);
        dob = dateFormatter.format(dobField.getValue());

        System.out.println(username);
        System.out.println(password);
        System.out.println(fullName);
        System.out.println(email);
        System.out.println(phone);
        System.out.println(dob);

        if(EmailValidator.getInstance().isValid(email))
        {
            emailValidLabel.setVisible(false);
        }
        else
        {
            emailValidLabel.setVisible(true);
        }
    }
}
