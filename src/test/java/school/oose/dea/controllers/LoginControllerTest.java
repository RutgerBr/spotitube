package school.oose.dea.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.models.LoginModel;
import school.oose.dea.models.LoginRequestModel;
import school.oose.dea.services.LoginService;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LoginControllerTest
{
    private static final String USERNAME = "Rutger";
    private static final String PASSWORD = "asdf";
    private static final String TOKEN = "1f888836-1e49-4e3a-8c5b-c827c0efdbc0";

    private LoginService loginServiceMock;
    private LoginController loginController;
    private LoginRequestModel request;
    private LoginModel loginModel;

    @BeforeEach
    void setup()
    {
        loginServiceMock = mock(LoginService.class);
        loginController = new LoginController();
        loginController.setLoginService(loginServiceMock);

        request = new LoginRequestModel();
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
        String loginExceptionMessage = "Wrong username or password";
        when(loginServiceMock.verifyLogin(request)).thenReturn(null);

        //Test
        LoginException loginException = assertThrows(LoginException.class, () ->
                loginController.login(request));
        //Assert
        assertEquals(loginExceptionMessage, loginException.getMessage());
    }

    @Test
    void testControllerDelegatesToLoginService()
    {
        //Setup
        when(loginServiceMock.verifyLogin(request)).thenReturn(loginModel);

        //Test
        loginController.login(request);

        //Assert
        verify(loginServiceMock).verifyLogin(request);
    }
}
