package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {
    private Facebook facebook;
    private ConnectionRepository connection;
    private FacebookConnectionFactory facebookConnectionFactory;

    @Value("${spring.social.facebook.appId}")
    String fbAppId;
    @Value("${spring.social.facebook.appSecret}")
    String fbAppSecret;

    public HelloController(Facebook facebook, ConnectionRepository connection) {
        this.facebook = facebook;
        this.connection = connection;
    }

    @GetMapping
    public String helloFacebook(Model model){
        if(connection.findConnections(Facebook.class) == null){
            return "redirect:/connect/facebook";
        }

        /*Connection<Facebook> connection = facebookConnectionFactory.createConnection(
                new ConnectionData("facebook","738140579","","","","","","",null));
        Facebook facebook = connection.getApi();
        String [] fields = { "id", "email",  "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, fields);*/



        try {
            model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
            PagedList<Post> feed = facebook.feedOperations().getFeed();
            model.addAttribute("feed", feed);
        }catch (Exception e){

        }

        return "hello";
    }
}
