package com.codefellows.demo.infrastructure;

import com.codefellows.demo.Model.ApplicationUser;
import com.codefellows.demo.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByApplicationUserIn(Set<ApplicationUser> applicationUserList);
}
