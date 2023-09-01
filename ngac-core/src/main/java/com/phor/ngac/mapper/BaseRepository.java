package com.phor.ngac.mapper;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {
    Optional<T> findById(ID id);

    <S extends T> S save(S entity);
}
