package school.oose.dea.services;

import org.junit.jupiter.api.BeforeEach;
import school.oose.dea.controllers.dto.LoginRequest;
import school.oose.dea.datasources.dao.LoginDAO;
import school.oose.dea.models.LoginModel;

import static org.mockito.Mockito.mock;

public class LoginServiceImplTest
{
    private static final String USERNAME = "Rutger";
    private static final String PASSWORD = "asdf";
    private static final String TOKEN = "asdf";

    private LoginServiceImpl loginServiceImpl;
    private LoginDAO loginDAOMock;

    private LoginRequest request;
    private LoginModel loginModel;

    @BeforeEach
    void setup()
    {
        loginDAOMock = mock(LoginDAO.class);
        loginServiceImpl = new LoginServiceImpl();
        loginServiceImpl.setLoginDAO(loginDAOMock);

        request = new LoginRequest();
        request.setUser(USERNAME);
        request.setPassword(PASSWORD);

        loginModel = new LoginModel();
        loginModel.setToken(TOKEN);
        loginModel.setUser(USERNAME);
    }
}
