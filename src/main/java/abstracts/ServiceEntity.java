package abstracts;

import java.util.List;

public interface ServiceEntity <T> {
	T get(Long id);
	List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(T t);
}
