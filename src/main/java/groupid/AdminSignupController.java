package groupid;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminSignupController {


    @FXML
    private TextField userName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField email;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private ComboBox<String> country;

    @FXML
    private TextField password;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Button button;

    // List of all countries
    private ObservableList<String> countries = FXCollections.observableArrayList(
            "Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "Brunei",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Colombia",
            "Comoros",
            "Congo (Congo-Brazzaville)",
            "Costa Rica",
            "Croatia",
            "Cuba",
            "Cyprus",
            "Czechia (Czech Republic)",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "East Timor (Timor-Leste)",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Eswatini (fmr. Swaziland)",
            "Ethiopia",
            "Fiji",
            "Finland",
            "France",
            "Gabon",
            "Gambia",
            "Georgia",
            "Germany",
            "Ghana",
            "Greece",
            "Grenada",
            "Guatemala",
            "Guinea",
            "Guinea-Bissau",
            "Guyana",
            "Haiti",
            "Honduras",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Iran",
            "Iraq",
            "Ireland",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Korea, North",
            "Korea, South",
            "Kosovo",
            "Kuwait",
            "Kyrgyzstan",
            "Laos",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Marshall Islands",
            "Mauritania",
            "Mauritius",
            "Mexico",
            "Micronesia",
            "Moldova",
            "Monaco",
            "Mongolia",
            "Montenegro",
            "Morocco",
            "Mozambique",
            "Myanmar (formerly Burma)",
            "Namibia",
            "Nauru",
            "Nepal",
            "Netherlands",
            "New Zealand",
            "Nicaragua",
            "Niger",
            "Nigeria",
            "North Macedonia",
            "Norway",
            "Oman",
            "Pakistan",
            "Palau",
            "Palestine State",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Poland",
            "Portugal",
            "Qatar",
            "Romania",
            "Russia",
            "Rwanda",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Vincent and the Grenadines",
            "Samoa",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "South Africa",
            "South Sudan",
            "Spain",
            "Sri Lanka",
            "Sudan",
            "Suriname",
            "Sweden",
            "Switzerland",
            "Syria",
            "Taiwan",
            "Tajikistan",
            "Tanzania",
            "Thailand",
            "Togo",
            "Tonga",
            "Trinidad and Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates",
            "United Kingdom",
            "United States of America",
            "Uruguay",
            "Uzbekistan",
            "Vanuatu",
            "Vatican City",
            "Venezuela",
            "Vietnam",
            "Yemen",
            "Zambia",
            "Zimbabwe"
    );

    @FXML
    public void initialize() {
        gender.getItems().addAll("Male", "Female");
        gender.setValue("Male");

        country.setItems(countries);

        // Make the country ComboBox searchable
        FilteredList<String> filteredCountries = new FilteredList<>(countries, p -> true);
        country.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            filteredCountries.setPredicate(country -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return country.toLowerCase().contains(newValue.toLowerCase());
            });
        });

        // Bind the filtered list to the ComboBox
        country.setItems(filteredCountries);
    }

    @FXML
    private void handleSignup() {
        // Validate that all fields are filled
        boolean isValid = validateFields();

        if (!isValid) {
            showErrorAlert("Empty Fields", "All fields are required. Please fill out all fields.");
            return;
        }

        // Validate phone number (must contain only numbers)
        if (!isValidPhoneNumber(phoneNumber.getText())) {
            showErrorAlert("Invalid Phone Number", "The phone number must contain only numbers.");
            phoneNumber.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            return;
        } else {
            phoneNumber.setStyle("");
        }

        // Validate email (must be in a valid format)
        if (!isValidEmail(email.getText())) {
            showErrorAlert("Invalid Email", "Please enter a valid email address.");
            email.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            return;
        } else {
            email.setStyle("");
        }

        // Validate password and confirm password
        if (!password.getText().equals(confirmPassword.getText())) {
            showErrorAlert("Password Mismatch", "The password and confirm password fields do not match.");
            return;
        }

        // If all fields are filled and passwords match, proceed with signup logic
        String selectedGender = gender.getValue();
        String selectedCountry = country.getValue();

        // Print or process the selected values
        System.out.println("Last Name: " + userName.getText());
        System.out.println("Phone Number: " + phoneNumber.getText());
        System.out.println("Email: " + email.getText());
        System.out.println("Date of Birth: " + dateOfBirth.getValue());
        System.out.println("Selected Gender: " + selectedGender);
        System.out.println("Selected Country: " + selectedCountry);
        System.out.println("Password: " + password.getText());

        // Add your signup logic here
    }

    // Helper method to validate all fields
    private boolean validateFields() {
        boolean isValid = true;

        // Check first name
        if (userName.getText().isEmpty()) {
            userName.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else {
            userName.setStyle("");
        }

        // Check phone number
        if (phoneNumber.getText().isEmpty()) {
            phoneNumber.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else {
            phoneNumber.setStyle("");
        }

        // Check email
        if (email.getText().isEmpty()) {
            email.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else {
            email.setStyle("");
        }

        // Check date of birth
        if (dateOfBirth.getValue() == null) {
            dateOfBirth.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else {
            dateOfBirth.setStyle("");
        }

        // Check gender
        if (gender.getValue() == null) {
            gender.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else {
            gender.setStyle("");
        }

        // Check country
        if (country.getValue() == null) {
            country.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else {
            country.setStyle("");
        }

        // Check password
        if (password.getText().isEmpty()) {
            password.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else {
            password.setStyle("");
        }

        // Check confirm password
        if (confirmPassword.getText().isEmpty()) {
            confirmPassword.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px;");
            isValid = false;
        } else {
            confirmPassword.setStyle("");
        }

        return isValid;
    }

    // Helper method to validate phone number (must contain only numbers)
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Regex to match only numbers
        String regex = "^[0-9]+$";
        return phoneNumber.matches(regex);
    }

    // Helper method to validate email (must be in a valid format)
    private boolean isValidEmail(String email) {
        // Regex for a basic email validation
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    // Helper method to show an error alert
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}