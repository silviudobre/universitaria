package be.kdg.programming5.service.jparepository;

import be.kdg.programming5.domain.enums.UserRole;
import be.kdg.programming5.exceptions.UserNotFoundException;
import be.kdg.programming5.presentation.api.dtos.UserDto;
import be.kdg.programming5.repository.jparepository.UserRepository;
import be.kdg.programming5.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        var user = userRepository.findByUsername(username);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public boolean isTeacherUser(long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User with ID {} was not found!",userId)));
        return user.getRole().equals(UserRole.TEACHER);
    }
}
