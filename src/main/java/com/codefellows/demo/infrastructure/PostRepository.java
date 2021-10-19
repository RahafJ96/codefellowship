package com.codefellows.demo.infrastructure;

import com.codefellows.demo.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
