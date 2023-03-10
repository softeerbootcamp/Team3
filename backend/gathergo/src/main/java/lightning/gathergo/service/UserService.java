package lightning.gathergo.service;

import lightning.gathergo.dto.LoginDto;
import lightning.gathergo.model.Article;
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
        if(user.getUuid() == null || user.getUuid().isBlank())
            user.setUuid(String.valueOf(UUID.randomUUID()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            // TODO: Exception 타입에 따라 달리 처리
            throw new DuplicateKeyException(e.getMessage());
        }
    }

    public Optional<User> findUserByUserUuid(String uuid){
        return userRepository.findUserByUserUuid(uuid);
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
    public Optional<User> findUserById(Integer Id) {
        return userRepository.findById(Id);
    }

    public void deleteUserByUserId(String userId) {
        userRepository.deleteUserByUserId(userId);
    }

    public boolean updateIntroduction(String uuid, String introduction) {
        int affectedRows = userRepository.updateIntroductionByUuid(uuid, introduction);

        return affectedRows == 1;
    }

    public User loginUser(LoginDto.LoginInput loginDto) {
        if(loginDto.getUserId().isBlank())
            return null;

        Optional<User> user = findUserByUserId(loginDto.getUserId());

        // 아이디에 관련된 유저 정보가 없거나, 비밀번호가 일치하지 않으면
        if(user.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword()))
            return null;

        return user.get();
    }

    public List<Article> getParticipatingArticlesById(Integer userId){
        return userRepository.findParticipatingArticlesById(userId);
    }

    public List<Article> getHostingArticlesById(Integer userId){
        return userRepository.findHostingArticlesById(userId);
    }

}
