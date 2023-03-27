package com.sfac.hk.resource.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.hk.common.vo.Result;
import com.sfac.hk.common.vo.Search;
import com.sfac.hk.resource.entity.Film;
import com.sfac.hk.resource.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Description FilmController
 * @Author JiangHu
 * @Date 2023/2/18 14:48
 */
@RestController
@RequestMapping("/api/resource")
public class FilmController {

    @Autowired
    private FilmService filmService;

    /**
     * 127.0.0.1/api/resource/film ---- post
     */
    @PostMapping(value = "/film", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Film> insertFilm(@RequestBody Film film) {
        return filmService.insertFilm(film);
    }

    /**
     * 127.0.0.1/api/resource/film ---- put
     */
    @PutMapping(value = "/film", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Film> updateFilm(@RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    /**
     * 127.0.0.1/api/resource/film/1 ---- delete
     */
    @DeleteMapping(value = "/film/{id}")
    public Result<Object> deleteFilmById(@PathVariable int id) {
        return filmService.deleteFilmById(id);
    }

    /**
     * 127.0.0.1/api/resource/film/1 ---- get
     */
    @GetMapping(value = "/film/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    /**
     * 127.0.0.1/api/resource/films ---- post
     */
    @PostMapping(value="/films", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<Film> getFilmsBySearch(@RequestBody Search search) {
        return filmService.getFilmsBySearch(search);
    }
}
