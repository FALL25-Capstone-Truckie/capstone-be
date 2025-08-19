package capstone_project.service.services.redis;

import java.util.List;

public interface RedisService {
    public void save(String key, Object value);

    public <T> T get(String key, Class<T> clazz) ;

    public <T> List<T> getList(String key, Class<T> clazz) ;

    public void delete(String key) ;

    public boolean exists(String key);

    public void clearCache();
}
