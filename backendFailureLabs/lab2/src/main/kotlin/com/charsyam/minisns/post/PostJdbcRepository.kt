package com.charsyam.minisns.post

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp

/**
 * save() 루프와 비교하기 위한 JDBC 배치 INSERT.
 * JDBC URL 의 rewriteBatchedStatements=true 와 함께 쓰면 드라이버가
 * 배치를 multi-value INSERT 한 문장으로 재작성한다.
 */
@Repository
class PostJdbcRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun batchInsert(posts: List<Post>, batchSize: Int = BATCH_SIZE) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO posts (title, content, likes, comments, created_at) VALUES (?, ?, ?, ?, ?)",
            posts,
            batchSize,
        ) { ps, post ->
            ps.setString(1, post.title)
            ps.setString(2, post.content)
            ps.setLong(3, post.likes)
            ps.setLong(4, post.comments)
            ps.setTimestamp(5, Timestamp.valueOf(post.createdAt))
        }
    }

    companion object {
        private const val BATCH_SIZE = 1000
    }
}
