package be.kdg.programming5.service.interfaces;

import be.kdg.programming5.presentation.api.dtos.UserDto;

public interface UserService {
    UserDto getUserByUsername(String username);

    boolean isTeacherUser(long userId);
}
