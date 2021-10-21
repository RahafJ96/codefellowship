package com.codefellows.demo.Controller;

import com.codefellows.demo.Model.ApplicationUser;
import com.codefellows.demo.Model.Post;
import com.codefellows.demo.infrastructure.ApplicationUserRepository;
import com.codefellows.demo.infrastructure.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/posts")
    public RedirectView createPost(Principal p, Model m, String body){
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());

        if(user != null){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Post post = new Post(body, timestamp, user);
            postRepository.save(post);
        }

        m.addAttribute("principal", p.getName());
        m.addAttribute("applicationUser", user);
        return new RedirectView("/myprofile");
    }
    @GetMapping("/feed")
    public String getFeedPage(Principal p, Model m) {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
        Set<ApplicationUser> following = applicationUser.getFollowing();
        List<Post> posts = postRepository.findByApplicationUserIn(following);

        m.addAttribute("posts",posts);
        m.addAttribute("appUser",applicationUser);
        m.addAttribute("principal", p.getName());
        return "feed";
    }

    @GetMapping("/post")
    public String getPostPage() {
        return "postpage";
    }
}
