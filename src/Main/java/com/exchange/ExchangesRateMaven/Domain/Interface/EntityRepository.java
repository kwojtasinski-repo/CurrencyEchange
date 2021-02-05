package com.exchange.ExchangesRateMaven.Domain.Interface;

import java.util.List;

public interface EntityRepository<T> {
	Long add(T obj);
	T getById(Long id);
	List<T> getAll();
	void update(T obj);
	void delete(T obj);
}
