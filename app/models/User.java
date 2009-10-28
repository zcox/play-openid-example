package models;

import javax.persistence.*;

import play.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class User extends Model
{
    private static final long serialVersionUID = -7849149882632244432L;
    @Required
    public String openId;
    @Required
    @Email
    public String email;
    public String firstName;
    public String lastName;
    
    public User(String openId)
    {
        this.openId = openId;
    }

    public static User findByOpenId(String openId)
    {
        Logger.debug("finding by " + openId);
        User user = User.find("openId", openId).first();
        if (user == null)
        {
            Logger.debug("creating new User");
            user = new User(openId);
            user.save();
        }
        return user;
    }
}
