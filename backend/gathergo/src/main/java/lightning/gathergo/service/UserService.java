package lightning.gathergo.service;

import lightning.gathergo.model.User;
import lightning.gathergo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByUserId(String userId) {
        return userRepository.findUserByUserId(userId);
    }

    public void deleteUserByUserId(String userId) {
        userRepository.deleteUserByUserId(userId);
    }
}