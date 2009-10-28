package controllers;

import models.*;
import play.*;
import play.libs.*;
import play.libs.OpenID.*;
import play.mvc.*;

public class Application extends Controller
{

    @Before(unless = { "login", "authenticate" })
    static void checkAuthenticated()
    {
        if (!session.contains("user"))
        {
            login();
        }
    }

    public static void index()
    {
//        String user = session.get("user");
        User user = current();
        render(user);
    }

    public static void login()
    {
        render();
    }

    public static void logout()
    {
        session.remove("user");
        index();
    }

    public static void authenticate(String openId)
    {
        Logger.debug("authenticating " + openId);
        if (OpenID.isAuthenticationResponse())
        {
            UserInfo verifiedUser = OpenID.getVerifiedID();
            if (verifiedUser == null)
            {
                flash.put("error", "Oops. Authentication has failed");
                login();
            }
            User user = User.findByOpenId(verifiedUser.id);
            session.put("user", user.id);
            index();
        }
        else
        {
            OpenID.id(openId).verify(); // will redirect the user
        }
    }

    static User current()
    {
        User current = null;
        String idString = session.get("user");
        if (idString != null)
            current = User.findById(Long.parseLong(idString));
        return current;
    }
}
