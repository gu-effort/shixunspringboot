package com.sfac.hk.resource.dao;

import com.sfac.hk.common.vo.Search;
import com.sfac.hk.resource.entity.Film;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FilmDao
 */
@Repository
@Mapper
public interface FilmDao {

    @Insert("insert into resource_film(name, release_time, score, director, script_writer, " +
            "region, company, star, plot, cover, create_date, update_time) " +
            "values(#{name}, #{releaseTime}, #{score}, #{director}, #{scriptWriter}, " +
            "#{region}, #{company}, #{star}, #{plot}, #{cover}, #{createDate}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertFilm(Film film);

    @Select("select * from resource_film where name=#{name} limit 1")
    Film getFilmByName(String name);

    @Update("update resource_film set name=#{name}, release_time=#{releaseTime}, score=#{score}, " +
            "director=#{director}, script_writer=#{scriptWriter}, region=#{region}, star=#{star}, " +
            "plot=#{plot}, company=#{company}, cover=#{cover}, update_time=#{updateTime} " +
            "where id = #{id}")
    void updateFilm(Film film);

    @Delete("delete from resource_film where id = #{id}")
    void deleteFilmById(int id);

    @Select("select * from resource_film where id = #{id}")
    Film getFilmById(int id);

    @Select("<script>"
            + "select * from resource_film "
            + "<where> "
            + "<if test='keyword != \"\" and keyword != null'>"
            + " and (name like '%${keyword}%' or star like '%${keyword}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " order by ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Film> getFilmsBySearch(Search search);
}
