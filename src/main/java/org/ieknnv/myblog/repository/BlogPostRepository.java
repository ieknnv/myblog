package org.ieknnv.myblog.repository;

import org.ieknnv.myblog.model.BlogPost;

public interface BlogPostRepository {

    void savePost(BlogPost post);
}
