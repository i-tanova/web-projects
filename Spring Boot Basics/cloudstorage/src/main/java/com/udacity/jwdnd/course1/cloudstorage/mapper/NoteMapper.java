package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> getNotes(int userId);

    @Insert("INSERT INTO notes (notetitle,  notedescription, userid) VALUES(#{noteTitle}, #{notedescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Delete("DELETE FROM notes WHERE noteid = #{noteId}")
    void deleteNote(int noteId);

    @Select("SELECT * FROM notes WHERE noteid = #{noteId}")
    Note getNoteById(int noteId);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{notedescription} WHERE noteid = #{noteId}")
    public void updateNote(Note note);
}
