package com.hormigo.david.parkingmanager.user.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.Role;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {


    @MockBean
    UserService userService;
    @Value("${local.server.port}")
    private int port;
    private ChromeDriver chromeDriver;

    @BeforeAll
    public static void prepareWebDriver() {

        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");



    }
    @BeforeEach
    public void mockServices(){

        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        chromeDriver = new ChromeDriver(options);
    }

    private String buildUrl(final String endPoint) {
        return "http://localhost:" + port + endPoint;
    }

    @Test 
    public void checkNavigateFromListToCreate() {
        chromeDriver.get(buildUrl("/users"));
        chromeDriver.findElement(By.id("users-button-create")).click();
        assertEquals(buildUrl("/newUser"),chromeDriver.getCurrentUrl());
    }
    @Test
    public void checkUserCreateFormFields() {
        chromeDriver.get(buildUrl("/newUser"));
        final String actualTitle = chromeDriver.getTitle();
        final WebElement emailField = chromeDriver.findElement(By.id("user-create-field-email"));
        final WebElement nameField  = chromeDriver.findElement(By.id("user-create-field-name"));
        final WebElement lastName1Field = chromeDriver.findElement(By.id("user-create-field-lastname1"));
        final WebElement lastName2Field = chromeDriver.findElement(By.id("user-create-field-lastname2"));
        final WebElement roleField = chromeDriver.findElement(By.id("user-create-field-role"));
        assertAll("Comprobar que el formulario tiene todos los campos requeridos",
                () -> {assertEquals("Crear nuevo usuario", actualTitle);},
                () -> {assertNotNull(emailField);},
                () -> {assertTrue(emailField.isEnabled());},
                () -> {assertNotNull(nameField);},
                () -> {assertTrue(nameField.isEnabled());},
                () -> {assertNotNull(lastName1Field);},
                () -> {assertTrue(lastName1Field.isEnabled());},
                () -> {assertNotNull(lastName2Field);},
                () -> {assertTrue(lastName2Field.isEnabled());},
                () -> {assertNotNull(roleField);},
                () -> {assertTrue(roleField.isEnabled());}
                );
  
    }

    @Test
    public void checkUserListFields() {
        final UserService userService = mock(UserService.class);
        final List<User> users = new ArrayList<>();
        users.add(new User("david@correo", "David", "Hormigo", "Ramírez", Role.PROFESSOR));
        when(userService.getAll()).thenReturn(users);
    

        final String url = buildUrl("/users");
        chromeDriver.get(url);
        final String actualTitle = chromeDriver.getTitle();
        final WebElement createButton = chromeDriver.findElement(By.id("users-button-create"));
        final WebElement actualHeading = chromeDriver.findElement(By.id("users-title"));
        final WebElement table = chromeDriver.findElement(By.className("table"));
        final String actualHeadingText = actualHeading.getText();

        assertAll("Comprobar que la pagina de lista de usuarios se muestra",
                () -> {
                    assertNotNull(createButton);
                },
                () -> {
                    assertEquals("Usuarios", actualTitle);
                },
                () -> {
                    assertEquals("Usuarios", actualHeadingText);
                },
                () -> {
                    assertNotNull(table);
                });
    }
    @Test
    public void checkUserIsCreated(){

        when(userService.userExists(any())).thenReturn(false);

        final String url = buildUrl("/newUser");
        chromeDriver.get(url);
        final WebElement emailField = chromeDriver.findElement(By.id("user-create-field-email"));
        final WebElement nameField  = chromeDriver.findElement(By.id("user-create-field-name"));
        final WebElement lastName1Field = chromeDriver.findElement(By.id("user-create-field-lastname1"));
        final WebElement lastName2Field = chromeDriver.findElement(By.id("user-create-field-lastname2"));
        final WebElement roleField = chromeDriver.findElement(By.id("user-create-field-role"));
        emailField.sendKeys("david@correo.com");
        nameField.sendKeys("David");
        lastName1Field.sendKeys("Hormigo");
        lastName2Field.sendKeys("Ramírez");
        final Select select = new Select(roleField);
        select.selectByVisibleText("Profesor");
        final WebElement submitButton = chromeDriver.findElement(By.id("user-create-button-submit"));
        submitButton.click();
        final String expectedUrl = buildUrl("/users");
        assertEquals(expectedUrl,chromeDriver.getCurrentUrl());

    }

    @Test
    public void checkUserIsDuplicated(){
        when(userService.userExists(anyString())).thenReturn(true);
        try {
            doThrow(UserExistsException.class).when(userService).register(any());
        } catch (final UserExistsException e) {
            e.printStackTrace();
        }
  
        //userRepository.findByEmail("david@correo.es")).thenReturn(new User("david@correo.es","David","Homrigo","Ramírez",Role.PROFESSOR));
        final String url = buildUrl("/newUser");
        chromeDriver.get(url);
        final WebElement emailField = chromeDriver.findElement(By.id("user-create-field-email"));
        final WebElement nameField  = chromeDriver.findElement(By.id("user-create-field-name"));
        nameField.sendKeys("Pepe");
        final WebElement lastName1Field = chromeDriver.findElement(By.id("user-create-field-lastname1"));
        lastName1Field.sendKeys("Hormigo");

        final WebElement submitButton = chromeDriver.findElement(By.id("user-create-button-submit"));

        submitButton.click();
        final String expectedUrl = buildUrl("/newUser");
        assertEquals(expectedUrl,chromeDriver.getCurrentUrl());
    }

    @Test
    public void checkIsNotCreatedIfEmailIsEmpty(){
        final String url = buildUrl("/newUser");
        chromeDriver.get(url);
        final WebElement emailField = chromeDriver.findElement(By.id("user-create-field-email"));
        emailField.clear();
        final WebElement nameField  = chromeDriver.findElement(By.id("user-create-field-name"));
        nameField.sendKeys("Pepe");
        final WebElement lastName1Field = chromeDriver.findElement(By.id("user-create-field-lastname1"));
        lastName1Field.sendKeys("Hormigo");

        final WebElement submitButton = chromeDriver.findElement(By.id("user-create-button-submit"));

        submitButton.click();
        final WebElement error = chromeDriver.findElement(By.className("notification"));
        final String text = error.getText();
        assertAll("Comprobamos que se ha mandado el mensaje y no se a navegado",
            ()->{assertEquals("El correo es obligatorio",text);},
            ()->{assertEquals(url,chromeDriver.getCurrentUrl());});

        
    }

    @Test
    public void checkIsNotCreatedIfNameIsEmpty(){
        final String url = buildUrl("/newUser");
        chromeDriver.get(url);
        final WebElement emailField = chromeDriver.findElement(By.id("user-create-field-email"));
        emailField.sendKeys("correo@correo.es");
        final WebElement nameField  = chromeDriver.findElement(By.id("user-create-field-name"));
        nameField.clear();
        final WebElement lastName1Field = chromeDriver.findElement(By.id("user-create-field-lastname1"));
        lastName1Field.sendKeys("Hormigo");

        final WebElement submitButton = chromeDriver.findElement(By.id("user-create-button-submit"));

        submitButton.click();
        final WebElement error = chromeDriver.findElement(By.className("notification"));
        final String text = error.getText();
        assertAll("Comprobamos que se ha mandado el mensaje y no se a navegado",
            ()->{assertEquals("El nombre es obligatorio",text);},
            ()->{assertEquals(url,chromeDriver.getCurrentUrl());});
        
    }
    @Test
    public void checkIsNotCreatedIfLastName1IsEmpty(){
        final String url = buildUrl("/newUser");
        chromeDriver.get(url);
        final WebElement emailField = chromeDriver.findElement(By.id("user-create-field-email"));
        emailField.sendKeys("correo@correo.es");
        final WebElement nameField  = chromeDriver.findElement(By.id("user-create-field-name"));
        nameField.sendKeys("Nombre");
        final WebElement lastName1Field = chromeDriver.findElement(By.id("user-create-field-lastname1"));
        lastName1Field.clear();

        final WebElement submitButton = chromeDriver.findElement(By.id("user-create-button-submit"));

        submitButton.click();
        final WebElement error = chromeDriver.findElement(By.className("notification"));
        final String text = error.getText();
        assertAll("Comprobamos que se ha mandado el mensaje y no se a navegado",
            ()->{assertEquals("El primer apellido es obligatorio",text);},
            ()->{assertEquals(url,chromeDriver.getCurrentUrl());});
        
    }

    @AfterEach
    public void closeDriver(){
        chromeDriver.quit();
    }

}
