package com.eror.fxclient.controller;


import com.eror.fxclient.config.StageManager;
import com.eror.fxclient.model.Role;
import com.eror.fxclient.model.User;
import com.eror.fxclient.view.FxmlView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController implements Initializable {
    private Map authMap;
    private String token;
    private String auth;
    private String bearer;
    private String tokenWithBearer;

    @FXML
    private Button company;

    @FXML
    private Button btnLogout;

    @FXML
    private Label userId;

    @FXML
    private Label usersName;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker dob;

    @FXML
    private RadioButton rbMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private ComboBox<Role> cbRole;
    Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory =
            new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                @Override
                public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
                    final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                        final Button btnEdit = new Button();
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                // Here we select collumn from table to get selected User
                                btnEdit.setOnAction(e -> {
                                    User user = getTableView().getItems().get(getIndex());
                                    updateUser(user);
                                });

                                btnEdit.setStyle("-fx-background-color: transparent;");
                                ImageView iv = new ImageView();
                                iv.setImage(imgEdit);
                                iv.setPreserveRatio(true);
                                iv.setSmooth(true);
                                iv.setCache(true);
                                btnEdit.setGraphic(iv);

                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void updateUser(User user) {
                            userId.setText(Long.toString(user.getId()));
                            firstName.setText(user.getName());
                            lastName.setText(user.getSurname());


                            cbRole.getSelectionModel().select(user.getRole());
                        }
                    };
                    return cell;
                }
            };
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button reset;
    @FXML
    private Button saveUser;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Long> colUserId;
    @FXML
    private TableColumn<User, String> colFirstName;
    @FXML
    private TableColumn<User, String> colLastName;
    @FXML
    private TableColumn<User, LocalDate> colDOB;
    @FXML
    private TableColumn<User, String> colGender;
    @FXML
    private TableColumn<User, String> colRole;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, Boolean> colEdit;
    @FXML
    private MenuItem deleteUsers;

    //	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private RoleService roleService;
//	@Autowired
//	private CompanyService companyService;
    @Lazy
    @Autowired
    private StageManager stageManager;
    private ObservableList<User> userList = FXCollections.observableArrayList();
    //	private ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");
    private ObservableList<Role> roles = FXCollections.observableArrayList();

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Logout and go to the login page
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void saveUser(ActionEvent event) throws IOException {

        if (validate("First Name", getFirstName(), "[a-zA-Z]+") &&
                validate("Last Name", getLastName(), "[a-zA-Z]+"))
//                &&
//                emptyValidation("DOB", dob.getEditor().getText().isEmpty()) &&
//                emptyValidation("Role", getRole() == null))
        {

            if (userId.getText() == null || userId.getText() == "") {
                if (validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+") &&
                        emptyValidation("Password", getPassword().isEmpty())) {

                    User user = new User();
                    user.setName(getFirstName());
                    user.setSurname(getLastName());
                    user.setUsername(getFirstName());
//                    user.setRole(getRole());
                    user.setEmail(getEmail());
                    user.setPassword(getPassword());
                    String msg = saveUser(user);
                    assert msg != null;
                    saveAlert(msg);
//        			User newUser = userService.save(user);

                }

            } else {
//    			User user = userService.find(Long.parseLong(userId.getText()));
//    			user.setFirstName(getFirstName());
//    			user.setLastName(getLastName());
//    			user.setDob(getDob());
//    			user.setGender(getGender());
//    			user.setRole(getRole());
//    			User updatedUser =  userService.update(user);
//    			updateAlert(updatedUser);
            }
//    		setUsersName();
            clearFields();
            loadUserDetails();
        }


    }

    private String saveUser(User user) throws IOException {
        try {
            String url = "http://localhost:8082/api/auth/signup";
            RestTemplate restTemplate = new RestTemplate();
// create headers
            HttpHeaders headers = new HttpHeaders();
// set `content-type` header
            headers.setContentType(MediaType.APPLICATION_JSON);
// set `accept` header
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenWithBearer);
// request body parameters

// build the request
            HttpEntity<User> entity = new HttpEntity<>(user, headers);
// send POST request
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            String msg = new String();
// check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Request Successful");
                System.out.println(response.getBody());
                msg = response.getBody();

            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());

            }
            return msg;
        } catch (Exception ex) {
            System.out.println("Request Failed");
            System.out.println(ex);
            return null;
        }
    }

    @FXML
    private void deleteUsers(ActionEvent event) {
        List<User> users = userTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

//		if(action.get() == ButtonType.OK) userService.deleteInBatch(users);

        loadUserDetails();
    }

    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        lastName.clear();
        dob.getEditor().clear();
        rbMale.setSelected(true);
        rbFemale.setSelected(false);
        cbRole.getSelectionModel().clearSelection();
        email.clear();
        password.clear();
    }

    private void saveAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getName() + " " + user.getSurname() + " has been created and \n" + " id is " + user.getId() + ".");
        alert.showAndWait();
    }

    private void saveAlert(String msg) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user has been created successfully.");
        alert.showAndWait();
    }

    private void updateAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getName() + " " + user.getSurname() + " has been updated.");
        alert.showAndWait();
    }

    private String getGenderTitle(String gender) {
        return (gender.equals("Male")) ? "his" : "her";
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public LocalDate getDob() {
        return dob.getValue();
    }

    public String getGender() {
        return rbMale.isSelected() ? "Male" : "Female";
    }

    public Role getRole() {
        return cbRole.getSelectionModel().getSelectedItem();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthMap(stageManager.getUser());
        Map jsonMap = authMap;
        System.out.println(jsonMap);
        String usernameJson = jsonMap.get("username").toString();
        System.out.println("username is: " + usernameJson);
        String bearer = jsonMap.get("tokenType").toString();
        System.out.println(bearer);
        token = jsonMap.get("accessToken").toString();
        System.out.println("Token is: " + token);
        tokenWithBearer = bearer + " " + token;
        System.out.println(tokenWithBearer);
        ArrayList authorityList = ((ArrayList) jsonMap.get("authorities"));
        Map authMap = (Map) authorityList.get(0);
        auth = (String) authMap.get("authority");
        System.out.println("Authority is: " + auth);


//		setUsersName();
//		roles.addAll(roleService.findAll());
//		List<Company> companies=companyService.findAll();
        cbRole.setItems(roles);

        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadUserDetails();
    }

//	private void setUsersName() {
//		usersName.setText(getUser().getFirstName()+" "+getUser().getLastName());
//	}

    private void setAuthMap(Map user) {
        System.out.println(user.toString());
        authMap = user;
    }

    /*
     *  Set All userTable column properties
     */
    private void setColumnProperties() {
//		Override date format in table
//		 colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
//
//			 String pattern = "dd/MM/yyyy";
//			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
//		     @Override
//		     public String toString(LocalDate date) {
//		         if (date != null) {
//		             return dateFormatter.format(date);
//		         } else {
//		             return "";
//		         }
//		     }
//
//		     @Override
//		     public LocalDate fromString(String string) {
//		         if (string != null && !string.isEmpty()) {
//		             return LocalDate.parse(string, dateFormatter);
//		         } else {
//		             return null;
//		         }
//		     }
//		 }));


        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEdit.setCellFactory(cellFactory);
    }

    /*
     *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        userList.clear();
//		userList.addAll(userService.findAll());
        userTable.setItems(userList);
    }

    /*
     * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) alert.setContentText("Please Select " + field);
        else {
            if (empty) alert.setContentText("Please Enter " + field);
            else alert.setContentText("Please Enter Valid " + field);
        }
        alert.showAndWait();
    }

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
}
