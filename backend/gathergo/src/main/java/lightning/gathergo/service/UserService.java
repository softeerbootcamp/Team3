package lightning.gathergo.service;

import lightning.gathergo.dto.LoginDto;
import lightning.gathergo.model.User;
import lightning.gathergo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) {
        user.setUuid(String.valueOf(UUID.randomUUID()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            // TODO: Exception 타입에 따라 달리 처리
            throw new DuplicateKeyException(e.getMessage());
        }
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

    public User loginUser(LoginDto.LoginInput loginDto) {
        Optional<User> user = findUserByUserId(loginDto.getUserId());

        if(user.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword()))
            return null;

        return user.get();
    }
}