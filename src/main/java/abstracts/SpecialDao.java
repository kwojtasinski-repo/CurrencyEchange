package abstracts;

import java.util.List;
import java.util.Map;

public interface SpecialDao {
	<T> T getByNamedQuery(String queryString, Map<String,Object> parameters);
	<T> T getByNativeQuery(String queryString, Map<String,Object> parameters);
	<T> List<T> getAllByNamedQuery(String queryString, Map<String,Object> parameters);
	<T> List<T> getAllByNativeQuery(String queryString, Map<String,Object> parameters);
}
