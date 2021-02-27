package service;

import java.util.List;
import java.util.Optional;

import abstracts.Dao;
import abstracts.ServiceEntity;
import entity.Currency;

public class CurrencyService implements ServiceEntity<Currency>{
	private Dao<Currency> dao;
	
	public CurrencyService(Dao<Currency> dao) {
		this.dao = dao;
	}

	@Override
	public Currency get(Long id) {
		// TODO Auto-generated method stub
		Optional<Currency> currency = dao.get(id);
		return currency.isPresent() ? currency.get() : null;
	}

	@Override
	public List<Currency> getAll() {
		// TODO Auto-generated method stub
		return dao.getAll();
	}

	@Override
	public void save(Currency currency) {
		dao.save(currency);
	}

	@Override
	public void update(Currency currency) {
		dao.update(currency);
	}

	@Override
	public void delete(Currency currency) {
		dao.delete(currency);
	}
}