package com.df.noticesystem;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoticeMapper {

    @Select("SELECT * FROM notice ORDER BY id DESC")
    List<Notice> selectAll();

    @Select("SELECT * FROM notice WHERE id = ${id}")
    Notice selectById(@Param("id") String id);

    @Select("SELECT * FROM notice WHERE title LIKE '%${title}%'")
    List<Notice> selectByTitle(@Param("title") String title);

    @Insert("INSERT INTO notice(title, content, author) VALUES('${title}', '${content}', '${author}')")
    int insert(@Param("title") String title, @Param("content") String content, @Param("author") String author);

    @Update("UPDATE notice SET title='${title}', content='${content}' WHERE id=${id}")
    int update(@Param("id") String id, @Param("title") String title, @Param("content") String content);

    @Delete("DELETE FROM notice WHERE id=${id}")
    int delete(@Param("id") String id);

    @Delete("DELETE FROM notice WHERE id IN (${ids})")
    int deleteBatch(@Param("ids") String ids);
}