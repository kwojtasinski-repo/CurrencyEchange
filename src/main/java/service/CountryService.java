package service;

import java.util.List;
import java.util.Optional;

import abstracts.Dao;
import abstracts.ServiceEntity;
import entity.Country;

public class CountryService implements ServiceEntity<Country>{
	private Dao<Country> dao;
	
	public CountryService(Dao<Country> dao) {
		this.dao = dao;
	}

	@Override
	public Country get(Long id) {
		Optional<Country> country = dao.get(id);
		return country.isPresent() ? country.get() : null;
	}

	@Override
	public List<Country> getAll() {
		return dao.getAll();
	}

	@Override
	public void save(Country country) {
		dao.save(country);
	}

	@Override
	public void update(Country country) {
		dao.update(country);
	}

	@Override
	public void delete(Country country) {
		dao.delete(country);
	}
}
