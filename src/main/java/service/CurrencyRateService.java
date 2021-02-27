package service;

import java.util.List;
import java.util.Optional;

import abstracts.Dao;
import abstracts.ServiceEntity;
import entity.CurrencyRate;

public class CurrencyRateService implements ServiceEntity<CurrencyRate>{
	private Dao<CurrencyRate> dao;
	
	public CurrencyRateService(Dao<CurrencyRate> dao) {
		this.dao = dao;
	}

	@Override
	public CurrencyRate get(Long id) {
		Optional<CurrencyRate> rate = dao.get(id);
		return rate.isPresent() ? rate.get() : null;
	}

	@Override
	public List<CurrencyRate> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(CurrencyRate rate) {
		dao.save(rate);
	}

	@Override
	public void update(CurrencyRate rate) {
		dao.update(rate);
	}

	@Override
	public void delete(CurrencyRate rate) {
		dao.delete(rate);
	}
}
