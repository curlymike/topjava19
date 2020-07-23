package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();

    {
        // add one user here
        // UPD: user being added in SpringMain
        // .create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        // Is null ok in case user no found?
        // Or would an Exception be more appropriate?
        // Same with getByEmail() method.
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        log.info("Debuggin InMemoryUserRepositoryImpl: getAll() [log]: " + repository.size() + "(size)");
        // (список пользователей возвращать отсортированным по имени)
        return repository.entrySet().stream()
            .map(Map.Entry::getValue)
            .sorted(Comparator.comparing(AbstractNamedEntity::getName).thenComparingInt(AbstractBaseEntity::getId))
            .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        // Is null ok in case user no found?
        // Or would an Exception be more appropriate?
        return repository.entrySet().stream().filter((es) -> es.getValue().getEmail().equals(email)).findFirst().get().getValue();
    }

    public int size() {
        return repository.size();
    }
}
