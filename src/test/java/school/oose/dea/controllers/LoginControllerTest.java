package school.oose.dea.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.controllers.dto.LoginRequest;
import school.oose.dea.models.LoginModel;
import school.oose.dea.services.LoginService;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class LoginControllerTest
{
    private static final String USERNAME = "Rutger";
    private static final String PASSWORD = "asdf";
    private static final String TOKEN = "asdf";

    private LoginService loginServiceMock;
    private LoginController loginController;
    private LoginRequest request;
    private LoginModel loginModel;

    @BeforeEach
    void setup()
    {
        loginServiceMock = mock(LoginService.class);
        loginController = new LoginController();
        loginController.setLoginService(loginServiceMock);

        request = new LoginRequest();
        request.setUser(USERNAME);
        request.setPassword(PASSWORD);

        loginModel = new LoginModel();
        loginModel.setToken(TOKEN);
        loginModel.setUser(USERNAME);
    }

    @Test
    void testCorrectLoginStatusOk()
    {
        //Setup
        when(loginServiceMock.verifyLogin(request)).thenReturn(loginModel);

        //Test
        Response response = loginController.login(request);

        //Assert
       assertEquals(200, response.getStatus());
    }

    @Test
    void testIncorrectLoginStatusUnauthorized()
    {
        //Setup
        when(loginServiceMock.verifyLogin(request)).thenReturn(null);

        //Test
        Response response = loginController.login(request);

        //Assert
        assertEquals(401, response.getStatus());
    }

    @Test
    void testControllerDelegatesToService()
    {
        //Setup
        when(loginServiceMock.verifyLogin(request)).thenReturn(loginModel);

        //Test
        loginController.login(request);

        //Assert
        verify(loginServiceMock).verifyLogin(request);
    }
}
