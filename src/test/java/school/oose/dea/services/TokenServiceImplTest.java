package school.oose.dea.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.datasources.dao.LoginDAO;

import static org.mockito.Mockito.*;

public class TokenServiceImplTest
{
    private static final String TOKEN = "1f888836-1e49-4e3a-8c5b-c827c0efdbc0";

    private TokenServiceImpl tokenServiceImpl;
    private LoginDAO loginDAOMock;

    @BeforeEach
    void setup()
    {
        loginDAOMock = mock(LoginDAO.class);
        tokenServiceImpl = new TokenServiceImpl();
        tokenServiceImpl.setLoginDao(loginDAOMock);
    }

    @Test
    void testisTokenValidProperlyDelegatesToLoginDAO()
    {
        //Setup
        when(loginDAOMock.getUserByToken(TOKEN)).thenReturn(null);

        //Test
        tokenServiceImpl.isTokenValid(TOKEN);

        //Assert
        verify(loginDAOMock).getUserByToken(TOKEN);
    }
}
