package com.sfac.hk.resource.service;

import com.github.pagehelper.PageInfo;
import com.sfac.hk.common.vo.Result;
import com.sfac.hk.common.vo.Search;
import com.sfac.hk.resource.entity.Film;

/**
 * FilmService
 */
public interface FilmService {

    Result<Film> insertFilm(Film film);

    Result<Film> updateFilm(Film film);

    Result<Object> deleteFilmById(int id);

    Film getFilmById(int id);

    PageInfo<Film> getFilmsBySearch(Search search);
}
