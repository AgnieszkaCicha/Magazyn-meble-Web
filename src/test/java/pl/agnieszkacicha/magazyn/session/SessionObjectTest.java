package pl.agnieszkacicha.magazyn.session;

import org.junit.Assert;
import org.junit.Test;
import pl.agnieszkacicha.magazyn.model.User;

public class SessionObjectTest {

    @Test
    public void isLoggedUserTest() {
        SessionObject sessionObject = new SessionObject();
        User user = new User(1, "asdfasd", "asdfasdf", "asdfasdf", "asdfasdf", User.Role.USER);

        Assert.assertFalse(sessionObject.isLogged());
        sessionObject.setUser(user);
        Assert.assertTrue(sessionObject.isLogged());
    }

    @Test
    public void getInfoTest() {
        SessionObject sessionObject = new SessionObject();
        String info = "Info";
        String infoExpectedResult = "Info";

        Assert.assertNull(sessionObject.getInfo());
        sessionObject.setInfo(info);
        Assert.assertEquals(infoExpectedResult, sessionObject.getInfo());
        Assert.assertNull(sessionObject.getInfo());
    }
}
