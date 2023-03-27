package com.sfac.hk.resource.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.hk.common.vo.Result;
import com.sfac.hk.common.vo.Search;
import com.sfac.hk.resource.dao.FilmDao;
import com.sfac.hk.resource.entity.Film;
import com.sfac.hk.resource.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

/**
 * @Description FilmServiceImpl
 * @Author JiangHu
 * @Date 2023/2/18 14:42
 */
@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmDao filmDao;

    @Override
    @Transactional
    public Result<Film> insertFilm(Film film) {
        Film temp = filmDao.getFilmByName(film.getName());
        if (temp != null) {
            return Result.failed("电影名重复。");
        }

        film.setCreateDate(LocalDateTime.now());
        film.setUpdateTime(LocalDateTime.now());
        filmDao.insertFilm(film);

        return Result.ok(film);
    }

    @Override
    @Transactional
    public Result<Film> updateFilm(Film film) {
        Film temp = filmDao.getFilmByName(film.getName());
        if (temp != null && temp.getId() != film.getId()) {
            return Result.failed("电影名重复。");
        }

        film.setUpdateTime(LocalDateTime.now());
        filmDao.updateFilm(film);

        return Result.ok(film);
    }

    @Override
    @Transactional
    public Result<Object> deleteFilmById(int id) {
        filmDao.deleteFilmById(id);
        return Result.ok();
    }

    @Override
    public Film getFilmById(int id) {
        return filmDao.getFilmById(id);
    }

    @Override
    public PageInfo<Film> getFilmsBySearch(Search search) {
        search.initSearch();
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(filmDao.getFilmsBySearch(search))
                .orElse(Collections.emptyList()));
    }
}
