package school.oose.dea.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.models.LoginRequestModel;
import school.oose.dea.datasources.dao.LoginDAO;
import school.oose.dea.models.LoginModel;

import static org.mockito.Mockito.*;

public class LoginServiceImplTest
{
    private static final String USERNAME = "Rutger";
    private static final String PASSWORD = "asdf";
    private static final String TOKEN = "asdf";

    private LoginServiceImpl loginServiceImpl;
    private LoginDAO loginDAOMock;

    private LoginRequestModel request;
    private LoginModel loginModel;

    @BeforeEach
    void setup()
    {
        loginDAOMock = mock(LoginDAO.class);
        loginServiceImpl = new LoginServiceImpl();
        loginServiceImpl.setLoginDAO(loginDAOMock);

        request = new LoginRequestModel();
        request.setUser(USERNAME);
        request.setPassword(PASSWORD);

        loginModel = new LoginModel();
        loginModel.setToken(TOKEN);
        loginModel.setUser(USERNAME);
    }

    @Test
    void testServiceProperlyUsesLoginDAO()
    {
        //Setup
        when(loginDAOMock.getLoginInfo(USERNAME, PASSWORD)).thenReturn(loginModel);

        //Test
        loginServiceImpl.verifyLogin(request);

        //Assert
        verify(loginDAOMock).getLoginInfo(USERNAME, PASSWORD);
    }
}
